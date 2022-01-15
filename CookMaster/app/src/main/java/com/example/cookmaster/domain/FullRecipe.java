package com.example.cookmaster.domain;

import android.graphics.drawable.Drawable;

import com.example.cookmaster.model.Tag;

import java.util.List;

public class FullRecipe {

    public FullRecipe(
            int id,
            String name,
            String description,
            String category,
            int preparationTime,
            Drawable image,
            List<SingleIngredient> ingredients,
            int carbohydrates,
            int fats,
            int proteins,
            int kcal,
            int cost,
            List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.preparationTime = preparationTime;
        this.image = image;
        this.ingredients = ingredients;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        this.kcal = kcal;
        this.cost = cost;
        this.tags = tags;
    }

    public int id;
    public String name;
    public String description;
    public String category;
    public int preparationTime;
    public Drawable image;
    public List<SingleIngredient> ingredients;
    public List<Tag> tags;
    public int carbohydrates;
    public int fats;
    public int proteins;
    public int kcal;
    public int cost;

}
