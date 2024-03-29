
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * A graphical view of the simulation grid. The view displays a colored
 * rectangle for each location representing its contents. It uses a default
 * background color. Colors for each type of species can be defined using the
 * setColor method.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-23
 */
public class SimulatorView extends JFrame {

    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JButton btnPause, btnContinue;
    private JLabel stepLabel, population, runningStatus;
    private FieldView fieldView;

    // A map for storing colors for participants in the simulation
    private HashMap colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    private boolean paused;

    /**
     * Create a view of the given width and height.
     * @param height
     * @param width
     */
    public SimulatorView(int height, int width) {
        paused = false;

        stats = new FieldStats();
        colors = new HashMap();

        setTitle("Fox and Rabbit Simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

        //btn continue instantiation
        this.btnContinue = new JButton("Continue");
        this.btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                continueSimulate(ae);
            }
        });

        //btn pause instantiation        
        this.btnPause = new JButton("Pause");
        this.btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                pausedSimulate(ae);
            }
        });

        this.runningStatus = new JLabel("Running");

        setLocation(100, 50);

        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);

        //add population
        JPanel jp = new JPanel(new GridLayout(3, 1));
        jp.add(population);

        //add running status
        JPanel running = (new JPanel(new FlowLayout()));
        running.add(runningStatus);
        jp.add(running);

        //add buttons bellow label
        JPanel jpButtons = new JPanel(new GridLayout(1, 2));
        jpButtons.add(btnContinue);
        jpButtons.add(btnPause);
        jp.add(jpButtons);
        contents.add(jp, BorderLayout.SOUTH);

        pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void pausedSimulate(java.awt.event.ActionEvent evt) {
        this.paused = true;
        this.runningStatus.setText("Paused");
    }

    private void continueSimulate(java.awt.event.ActionEvent evt) {
        this.paused = false;
        this.runningStatus.setText("Running");
    }

    /**
     *
     * @return True if the simulation is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass
     * @param color
     */
    public void setColor(Class animalClass, Color color) {
        colors.put(animalClass, color);
    }

    /**
     * Define a color to be used for a given class of animal.
     */
    private Color getColor(Animal animal) {
        Class animalClass = animal.getClass();
        Color col = (Color) colors.get(animalClass);

        if (col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            if (animal.getSex() == Sex.MALE) {
                                
                Double red = col.getRed() * 0.5;
                Double green = col.getGreen() * 0.5;
                Double blue = col.getBlue() * 0.5;
                col = new Color(red.intValue(), green.intValue(), blue.intValue());
            }
            return col;
        }
    }

    /**
     * Show the current status of the field.
     *
     * @param stats Status of the field to be represented.
     * @param field
     */
    public void showStatus(int step, Field field) {
        if (!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        fieldView.preparePaint();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Animal animal = field.getObjectAt(row, col);
                if (animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal));
                } else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     *
     * @param field
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is a nested class
     * (a class defined inside a class) which defines a custom component for the
     * user interface. This component displays the field. This is rather
     * advanced GUI stuff - you can ignore this for your project if you like.
     */
    private class FieldView extends JPanel {

        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width) {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                    gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component may be
         * resized, compute the scaling factor again.
         */
        public void preparePaint() {
            if (!size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if (xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if (yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale - 1, yScale - 1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the internal
         * image to screen.
         */
        public void paintComponent(Graphics g) {
            if (fieldImage != null) {
                g.drawImage(fieldImage, 0, 0, null);
            }
        }
    }
}
