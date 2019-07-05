import java.util.List;

/**
 *
 * @author Luiz Carlos, Gustavo Rodrigues, Gabriel Henrique
 */
public class Coyote extends Animal {

    private static final int BREEDING_AGE = 10;

    private static final int MAX_AGE = 150;

    private static final double BREEDING_PROBABILITY = 0.09;

    private static final int MAX_LITTER_SIZE = 3;

    private static final int FULL_LEVEL = 15;

    private static final int FOOD_VALUE = 15;
    
    /**
     * The scientific name of the species
     */
    public static final String SPECIES = "Canis latrans";

    /**
     * Create a new coyote. Both age and sex will be set to random
     */
    public Coyote() {
        super(randomAge(MAX_AGE), randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     * Creates a coyote with defined age and random sex
     * @param age The age of the coyote
     */
    public Coyote(int age) {
        super(age, randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     * Creates a Coyote with random age and defined sex
     * @param sex The sex of the Coyote
     */
    public Coyote(Sex sex) {
        super(randomAge(MAX_AGE), sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

     /**
     * Creates a Coyote with pre-defined age and sex
     * @param sex The sex of the Coyote
     * @param age The age of the Coyote
     */
    public Coyote(Sex sex, int age) {
        super(age, sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     * Auxiliary function to the constructor
     */
    public void constructor() {
        setEdibleAnimals();
    }

     /**
     * Defines what animals can be edible by the coyote
     */
    @Override
    public void setEdibleAnimals() {
        addPrey(Rabbit.SPECIES);
        addPrey(Fox.SPECIES);
    }

    /**
     * Creates a new coyote born from this one
     * @return The new coyote born
     */
    @Override
    public Animal newChild() {
        return new Fox(0);
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     * @param currentField
     * @param newAnimals
     * @param updatedField
     */
    @Override
    public void toLive(Field currentField, Field updatedField, List newAnimals) {
        incrementHunger();

        // Move towards the source of food if found.
        Location newLocation = findFood(currentField, getLocation());
        if (newLocation == null) {  // no food found - move randomly
            newLocation = updatedField.freeAdjacentLocation(getLocation());
        }
        super.runTime(currentField, updatedField, newAnimals, newLocation);
    }

    /**
     * Return the scientific name of the coyote in a string format
     * @return String containing the scientific name of the coyote
     */
    @Override
    public String getSpecies() {
        return SPECIES;
    }

}
