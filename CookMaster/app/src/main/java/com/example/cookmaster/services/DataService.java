package com.example.cookmaster.services;

import com.example.cookmaster.model.Recipe;

import java.util.LinkedList;
import java.util.List;

public class DataService {
    public List<Recipe> GetRecipes(){
        List<Recipe> recipes =  new LinkedList<>();
        recipes.add(new Recipe(1, "Some basic recipe", "stejk", "w chuj dlugo"));
        recipes.add(new Recipe(2, "Kapucha", "zupa", "w chuj dlugo"));
        recipes.add(new Recipe(3, "smierdacy ser", "zakaska", "w chuj dlugo"));

        return recipes;
    }
}
