package com.example.cookmaster.domain;

public class ShoppingEntry {

    public ShoppingEntry(boolean isBought, String name, int quantity, String units) {
        this.isBought = isBought;
        this.name = name;
        this.quantity = quantity;
        this.units = units;
    }

    public boolean isBought;
    public String name;
    public int quantity;
    public String units;
}
