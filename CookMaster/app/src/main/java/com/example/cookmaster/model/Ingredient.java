package com.example.cookmaster.model;

public class Ingredient {
    public Ingredient(long id, String name, double carbohydrates, double fats, double proteins, int kcal, double cost){
        this.id = id;
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        this.kcal = kcal;
        this.cost = cost;
    }

    public long id;
    public String name;
    public double carbohydrates;
    public double fats;
    public double proteins;
    public int kcal;
    public double cost;
}
