
import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox. Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Fox extends Animal {
    // Characteristics shared by all foxes (static fields).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The maximum satiety level
    private static final int FULL_LEVEL = 15;
    // The fox food value
    private static final int FOOD_VALUE = 15;
    
    /**
     *
     */
    public static final String SPECIES = "Vulpes vulpes";

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox() {
        super(randomAge(MAX_AGE), randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     * @param age
     */
    public Fox(int age) {
        super(age, randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     * @param sex
     */
    public Fox(Sex sex) {
        super(randomAge(MAX_AGE), sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     * @param sex
     * @param age
     */
    public Fox(Sex sex, int age) {
        super(age, sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     */
    public void constructor() {
        setEdibleAnimals();
    }

    /**
     *
     */
    @Override
    public void setEdibleAnimals() {
        addPrey(Rabbit.SPECIES);
    }

    /**
     *
     * @return
     */
    @Override
    public Animal newChild() {
        return new Fox(0);
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     * @param currentField
     * @param updatedField
     * @param newAnimals
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
     *
     * @return
     */
    @Override
    public String getSpecies() {
        return SPECIES;
    }

}
