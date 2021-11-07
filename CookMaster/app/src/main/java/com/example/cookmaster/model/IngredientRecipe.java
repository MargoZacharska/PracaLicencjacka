package com.example.cookmaster.model;

public class IngredientRecipe {
    public IngredientRecipe (int ingredient_id, int recipe_id, int quantity){
        this.ingredient_id = ingredient_id;
        this.recipe_id = recipe_id;
        this.quantity = quantity;
    }
    public int ingredient_id;
    public int recipe_id;
    public int quantity;
}
