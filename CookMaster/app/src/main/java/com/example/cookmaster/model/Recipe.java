package com.example.cookmaster.model;

public class Recipe {
    public Recipe(int id, String description, String category, String preparationTime){
        this.id = id;
        this.category = category;
        this.description = description;
        this.preparationTime = preparationTime;
    }
    public int id;
    public String description;
    public String category;
    public String preparationTime;
}
