package com.example.cookmaster.model;

public class Procedure {
    public Procedure(int id, int recipe_id, String description, int order_number){
        this.id = id;
        this.recipe_id = recipe_id;
        this.description = description;
        this.order_number = order_number;
    }

    public int id;
    public int recipe_id;
    public String description;
    public int order_number;
}
