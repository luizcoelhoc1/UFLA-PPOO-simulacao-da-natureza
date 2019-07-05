
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit. Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (static fields).

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
     *
     */
    public static final String SPECIES = "Oryctolagus cuniculus";

    /**
     * Create a new rabbit. A rabbit may be created with age zero (a new born)
     * or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     */
    public Rabbit() {
        super(randomAge(MAX_AGE), randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     *
     * @param age
     */
    public Rabbit(int age) {
        super(age, randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     *
     * @param sex
     */
    public Rabbit(Sex sex) {
        super(randomAge(MAX_AGE), sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     *
     * @param sex
     * @param age
     */
    public Rabbit(Sex sex, int age) {
        super(age, sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, 10000);
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    @Override
    public String getSpecies() {
        return SPECIES;
    }

    /**
     *
     */
    @Override
    public void setEdibleAnimals() {
    }

}
