
import java.util.ArrayList;
import java.util.Iterator;
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

    private static final Random rand = new Random();

    public Animal(int age, Sex sex, int maxAge, int breedingAge, int maxLitterSize, double breedingProbability) {
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.age = age;
        this.sex = sex;
        this.alive = true;
    }

    protected void incrementAge() {
        age++;
        if (age > maxAge) {
            die();
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
    protected List breed(Field currentField, Field updatedField) {

        Iterator adjacentLocations
                = currentField.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Animal animal = currentField.getObjectAt(where);
            if (this instanceof Fox && animal instanceof Fox) {
            }
            
        }

        int births = 0;
        System.out.println(breedingProbability);
        if (canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }

        List newAnimals = new ArrayList();
        for (int b = 0; b < births; b++) {
            Animal newAnimal = newChild();
            newAnimals.add(newAnimal);

            Location loc = updatedField.randomAdjacentLocation(location);
            newAnimal.setLocation(loc);
            updatedField.place(newAnimal, loc);
        }
        return newAnimals;
    }

    protected void runTime(Field currentField, Field updatedField, List newAnimals, Location newLocation) {
        incrementAge();
        if (isAlive()) {
            //newAnimals
            List<Animal> childs = breed(currentField, updatedField);
            for (Animal a : childs) {
                newAnimals.add(a);
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

    public abstract void toLive(Field currentField, Field updatedField, List newAnimals);

    public abstract Animal newChild();

    public void die() {
        alive = false;
    }

    public Location getLocation() {
        return location;
    }

    public static int randomAge(int maxAge) {
        return rand.nextInt(maxAge);
    }

    public static Sex randomSex() {
        return rand.nextInt(100) < 50 ? Sex.MASCULINO : Sex.FEMININO;
    }

    public Sex getSex() {
        return sex;
    }
}
