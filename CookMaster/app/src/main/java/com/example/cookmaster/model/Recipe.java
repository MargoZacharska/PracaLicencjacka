package com.example.cookmaster.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Recipe {
    public Recipe(int id, String name, String description, String category, String preparationTime, Drawable img){
        this.id = id;
        this.category = category;
        this.name = name;
        this.preparationTime = preparationTime;
        this.image = img;
        this.description = description;
    }
    public int id;
    public String name;
    public String description;
    public String category;
    public String preparationTime;
    public Drawable image;
}
