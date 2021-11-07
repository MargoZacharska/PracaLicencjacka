package com.example.cookmaster.model;

public class Rating {
    public Rating(int recipe_id, int user_id, int rate, String opinion){
        this.recipe_id = recipe_id;
        this.user_id = user_id;
        this.rate = rate;
        this.opinion = opinion;
    }
    public int recipe_id;
    public int user_id;
    public int rate;
    public String opinion;
}
