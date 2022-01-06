package com.example.cookmaster.domain;

public class RecipeIngredient {
    public RecipeIngredient(int quantity, String name, int carbohydrates, int fats, int proteins, int kcal, int cost){
        this.quantity = quantity;
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        this.kcal = kcal;
        this.cost = cost;
    }

    public int quantity;
    public String name;
    public int carbohydrates;
    public int fats;
    public int proteins;
    public int kcal;
    public int cost;
}
