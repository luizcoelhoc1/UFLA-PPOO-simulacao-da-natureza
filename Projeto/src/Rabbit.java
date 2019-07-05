
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal {

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The Rabbit food value
    private static final int FOOD_VALUE = 15;

    /**
     * The scientific name of the species
     */
    public static final String SPECIES = "Oryctolagus cuniculus";

    /**
     * Create a new rabbit. Both age and sex will be set to random
     */
    public Rabbit() {
        super(randomAge(MAX_AGE), randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     * Creates a Rabbit with defined age and random sex
     * @param age The age of the Rabbit
     */
    public Rabbit(int age) {
        super(age, randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     * Creates a Rabbit with random age and defined sex
     * @param sex The sex of the Rabbit
     */
    public Rabbit(Sex sex) {
        super(randomAge(MAX_AGE), sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     * Creates a Rabbit with pre-defined age and sex
     * @param sex The sex of the Rabbit
     * @param age The age of the Rabbit
     */
    public Rabbit(Sex sex, int age) {
        super(age, sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     * Creates a new rabbit born from this one
     * @return The new rabbit born
     */
    @Override
    public Animal newChild() {
        return new Rabbit(0);
    }

    /**
     * This is what the rabbit does most of the time - it runs around. Sometimes
     * it will breed or die of old age.
     * @param currentField
     * @param updatedField
     * @param newAnimals
     */
    @Override
    public void toLive(Field currentField, Field updatedField, List newAnimals) {
        Location newLocation = updatedField.freeAdjacentLocation(getLocation());
        super.runTime(currentField, updatedField, newAnimals, newLocation);
    }

    /**
     * Return the scientific name of the rabbit in a string format
     * @return String containing the scientific name of the rabbit
     */
    @Override
    public String getSpecies() {
        return SPECIES;
    }

    /**
     * Set what animals can be eaten by the rabbit. It eats nothing.
     */
    @Override
    public void setEdibleAnimals() {
    }

}
