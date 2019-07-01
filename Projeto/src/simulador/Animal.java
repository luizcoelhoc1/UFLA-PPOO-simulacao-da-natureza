
import java.util.List;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aluno
 */
public abstract class Animal {

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

    private static final Random rand = new Random();

    public Animal(boolean randomAge, int maxAge, int breedingAge, int maxLitterSize, double breedingProbability) {
        this.maxLitterSize = maxLitterSize;
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        age = 0;
        alive = true;
        if (randomAge) {
            age = rand.nextInt(maxAge);
        }
    }

    protected void incrementAge() {
        age++;
        if (age > maxAge) {
            alive = false;
        }
    }

    /**
     * Check whether the rabbit is alive or not.
     *
     * @return True if the rabbit is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    public void setLocation(int row, int col) {
        this.location = new Location(row, col);
    }

    /**
     * Set the fox's location.
     *
     * @param location The fox's location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     */
    protected boolean canBreed() {
        return age >= breedingAge;
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    protected void runTime(Field updatedField, List newAnimals, Location newLocation) {
        incrementAge();
        if (isAlive()) {
            int births = breed();
            for (int b = 0; b < births; b++) {
                Animal newAnimal = newChild();
                newAnimals.add(newAnimal);

                Location loc = updatedField.randomAdjacentLocation(location);
                newAnimal.setLocation(loc);
                updatedField.place(newAnimal, loc);
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                alive = false;
            }

        }
    }

    public abstract Animal newChild();

    public void die() {
        alive = false;
    }

    public Location getLocation() {
        return location;
    }
}
