package com.example.cookmaster.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
