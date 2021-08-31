package com.example.cookmaster.model;

import android.graphics.drawable.Drawable;

public class Recipe {
    public Recipe(int id, String description, String category, String preparationTime, Drawable img){
        this.id = id;
        this.category = category;
        this.title = description;
        this.preparationTime = preparationTime;
        this.image = img;
    }
    public int id;
    public String title;
    public String category;
    public String preparationTime;
    public Drawable image;
}
