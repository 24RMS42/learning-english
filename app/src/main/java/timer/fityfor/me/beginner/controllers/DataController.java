package timer.fityfor.me.beginner.controllers;

import java.util.ArrayList;

import timer.fityfor.me.beginner.entities.Animal;

/**
 * Created by Hovhannisyan.Karo on 23.08.2017.
 */

public class DataController {

    private static DataController dataController = null;
    private int category;
    private ArrayList<Animal> whildAnimals;
    private ArrayList<Animal> aquaticAnimals;
    private ArrayList<Animal> birdAnimals;
    private ArrayList<Animal> domesticAnimals;
    private int level;
    private boolean canTouch = true;

    private DataController() {
    }

    public static DataController getInstance() {
        if (dataController == null) {
            dataController = new DataController();
        }
        return dataController;
    }

    public ArrayList<Animal> getDomesticAnimals() {
        return domesticAnimals;
    }

    public void setDomesticAnimals(ArrayList<Animal> domesticAnimals) {
        this.domesticAnimals = domesticAnimals;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Animal> getBirdAnimals() {
        return birdAnimals;
    }

    public void setBirdAnimals(ArrayList<Animal> birdAnimals) {
        this.birdAnimals = birdAnimals;
    }

    public ArrayList<Animal> getAquaticAnimals() {
        return aquaticAnimals;
    }

    public void setAquaticAnimals(ArrayList<Animal> aquaticAnimals) {
        this.aquaticAnimals = aquaticAnimals;
    }

    public int getCategory() {
        return category;
    }

    public ArrayList<Animal> getWhildAnimals() {
        return whildAnimals;
    }

    public void setWhildAnimals(ArrayList<Animal> whildAnimals) {
        this.whildAnimals = whildAnimals;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void resetDataController() {
        dataController = null;
    }
}