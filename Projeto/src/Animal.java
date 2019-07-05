
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Creates a new animal
 * @author Luiz Carlos, Gustavo Rodrigues, Gabriel Henrique
 */
public abstract class Animal {

    private Sex sex;

    // The fox's age.
    private int age;
    // Whether the fox is alive or not.
    private boolean alive;
    // The fox's position
    private Location location;

    private int maxAge;

    private int breedingAge;

    private int maxLitterSize;

    private double breedingProbability;

    private int foodValue;

    private ArrayList<String> preys;

    private int foodSatiety;

    private static final Random rand = new Random();

    /**
     *
     * @param age the age of the animal
     * @param sex the sex of the animal
     * @param maxAge the maximum age that the animal will be able to live (in steps)
     * @param breedingAge the minimum age required for the animal to breed
     * @param maxLitterSize the maximum size of the litter this animal can produce
     * @param breedingProbability the probability of a new animal be born (in decimals)
     * @param foodValue the nutritious value of this animal (will increase saciety by that amount if eaten)
     * @param foodSatiety the saciety of the animal (can be increased by eating others animals)
     */
    public Animal(int age, Sex sex, int maxAge, int breedingAge, int maxLitterSize, double breedingProbability, int foodValue, int foodSatiety) {
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.age = age;
        this.sex = sex;
        this.alive = true;
        this.foodValue = foodValue;
        this.preys = new ArrayList<>();
        this.foodSatiety = foodSatiety;
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    protected void incrementHunger() {
        foodSatiety--;
        if (foodSatiety <= 0) {
            die();
        }
    }

    private void incrementAge() {
        age++;
        if (age > maxAge) {
            die();
        }
    }

    /**
     * Add a prey to the preys list of the animal, the prey can be eaten by this animal
     * @param prey an String naming the animal species that will be prey
     */
    public void addPrey(String prey) {
        this.preys.add(prey);
    }

    /**
     * Check whether the rabbit is alive or not.
     *
     * @return True if the rabbit is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Defines a new location to the animal (like a movement)
     * @param row row coordinate
     * @param col column coordinate
     */
    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     * Set the animal's location.
     * @param location The animal's new location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * An animal can breed if it has reached the breeding age.
	 * @return True if the animal can breed
     */
    private boolean canBreed(Field currentField, Field updatedField) {
        boolean nearItsSimilar = false;
        Iterator adjacentLocations
                = currentField.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Animal animal = currentField.getObjectAt(where);
            if (animal != null) {
                if (this.getSpecies() == animal.getSpecies()) {
                    if (this.sex != animal.getSex()) {
                        nearItsSimilar = true;
                    }
                }
            }

        }
        return age >= breedingAge && nearItsSimilar;
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    private List breed(Field currentField, Field updatedField, List newAnimals) {
        int births = 0;
        if (canBreed(currentField, updatedField) && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }

        for (int b = 0; b < births; b++) {
            Animal newAnimal = newChild();
            newAnimals.add(newAnimal);

            Location loc = updatedField.randomAdjacentLocation(location);
            newAnimal.setLocation(loc);
            updatedField.place(newAnimal, loc);
        }
        return newAnimals;
    }

    /**
     * Pass the time
     * @param currentField
     * @param updatedField
     * @param newAnimals
     * @param newLocation
     */
    protected void runTime(Field currentField, Field updatedField, List newAnimals, Location newLocation) {
        incrementAge();
        if (isAlive()) {
            //newAnimals
            breed(currentField, updatedField, newAnimals);

            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }

        }
    }

    /**
     *
     * @param currentField
     * @param updatedField
     * @param newAnimals
     */
    public abstract void toLive(Field currentField, Field updatedField, List newAnimals);

    /**
     *
     * @return the name of the species
     */
    public abstract String getSpecies();

    /**
     *
     * @return a new child born from this animal
     */
    public abstract Animal newChild();

    /**
     * Just dies
     */
    public void die() {
        alive = false;
    }

    /**
     * 
     * @return the location in which the animal are standing
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Generates a random age lesser than the maximum age
     * @param maxAge Maximum age possible
     * @return An integer representing the age
     */
    public static int randomAge(int maxAge) {
        return rand.nextInt(maxAge);
    }

    /**
     * Generates a random sex
     * @return enum defining the sex
     */
    public static Sex randomSex() {
        return rand.nextInt(100) < 50 ? Sex.MASCULINO : Sex.FEMININO;
    }

    /**
     * Get the sex of the animal
     * @return The animal's sex
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Get nutritious value of the animal, if eaten by someone
     * @return The nutritious value of the animal
     */
    public int getFoodValue() {
        return this.foodValue;
    }

    /**
     * Set what animals can be eaten
     */
    public abstract void setEdibleAnimals();

    /**
     * Tell the animal that it's dead now :(
     */
    public void setEaten() {
        die();
    }

    /**
     * Tell the animal to look for rabbits adjacent to its current location.
     *
     * @param field The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood(Field field, Location location) {
        Iterator adjacentLocations
                = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Animal animal = field.getObjectAt(where);
            if (animal instanceof Animal) {
                for (String s : preys) {
                    if (s.equals(animal.getSpecies())) {
                        if (animal.isAlive()) {
                            animal.setEaten();
                            foodSatiety += animal.getFoodValue();
                            return where;
                        }
                    }
                }

            }
        }
        return null;
    }
}
