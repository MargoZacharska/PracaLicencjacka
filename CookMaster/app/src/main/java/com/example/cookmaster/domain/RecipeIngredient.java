package com.example.cookmaster.domain;

public class RecipeIngredient {
    public RecipeIngredient(int quantity, String name, int carbohydrates, int fats, int proteins, int kcal, int cost, String units){
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
    public int carbohydrates;
    public int fats;
    public int proteins;
    public int kcal;
    public int cost;
    public String units;
}
