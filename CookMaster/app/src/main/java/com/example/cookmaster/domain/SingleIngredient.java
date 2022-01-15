package com.example.cookmaster.domain;

public class SingleIngredient {

    public SingleIngredient(String name, int quantity, String units) {
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    public String name;
    public int quantity;
    public String units;
}
