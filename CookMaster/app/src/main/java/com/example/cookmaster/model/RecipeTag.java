package com.example.cookmaster.model;

public class RecipeTag {
    public RecipeTag(int tag_id, int recipe_id){
        this.tag_id = tag_id;
        this.recipe_id = recipe_id;
    }

    public int tag_id;
    public int recipe_id;
}
