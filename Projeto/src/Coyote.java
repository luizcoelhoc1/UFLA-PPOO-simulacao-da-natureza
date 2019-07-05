
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aluno
 */
public class Coyote extends Animal {

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
    public static final String SPECIES = "Canis latrans";

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Coyote() {
        super(randomAge(MAX_AGE), randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     * @param age
     */
    public Coyote(int age) {
        super(age, randomSex(), MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     * @param sex
     */
    public Coyote(Sex sex) {
        super(randomAge(MAX_AGE), sex, MAX_AGE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, FOOD_VALUE, FULL_LEVEL);
        constructor();
    }

    /**
     *
     * @param sex
     * @param age
     */
    public Coyote(Sex sex, int age) {
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
        addPrey(Fox.SPECIES);
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
     *
     * @return
     */
    @Override
    public String getSpecies() {
        return "Vulpes vulpes";
    }

}
