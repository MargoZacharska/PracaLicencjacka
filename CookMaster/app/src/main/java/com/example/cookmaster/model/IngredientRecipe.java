package com.example.cookmaster.model;

public class IngredientRecipe {
    public IngredientRecipe (long ingredient_id, long recipe_id, int quantity){
        this.ingredient_id = ingredient_id;
        this.recipe_id = recipe_id;
        this.quantity = quantity;
    }
    public long ingredient_id;
    public long recipe_id;
    public int quantity;
}
