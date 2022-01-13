package com.example.cookmaster.domain;

public class RecipeIngredient {
    public RecipeIngredient(int quantity, String name, double carbohydrates, double fats, double proteins, double kcal, double cost, String units){
        this.quantity = quantity;
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        this.kcal = kcal;
        this.cost = cost;
        this.units = units;
    }

    public int quantity;
    public String name;
    public double carbohydrates;
    public double fats;
    public double proteins;
    public double kcal;
    public double cost;
    public String units;
}
