package com.example.cookmaster.services;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.cookmaster.R;
import com.example.cookmaster.model.Recipe;

import java.util.LinkedList;
import java.util.List;

public class DataService {
    public List<Recipe> GetRecipes(Context ctx){
        List<Recipe> recipes =  new LinkedList<>();

        Drawable img = AppCompatResources.getDrawable(ctx, R.drawable.zupa_dyniowa_mini);
        recipes.add(new Recipe(5, "stare skarpety", "zupa", "3 dni", img));

        img = AppCompatResources.getDrawable(ctx, R.drawable.beza_mini);
        recipes.add(new Recipe(1, "Some basic recipe", "stejk", "w chuj dlugo", img));

        img = AppCompatResources.getDrawable(ctx, R.drawable.szarlotka_mini);
        recipes.add(new Recipe(2, "Kapucha", "zupa", "70 minut", img));

        img = AppCompatResources.getDrawable(ctx, R.drawable.frittata_mini);
        recipes.add(new Recipe(3, "smierdacy ser", "zakaska", "w chuj dlugo", img));

        img = AppCompatResources.getDrawable(ctx, R.drawable.salatka_mini);
        recipes.add(new Recipe(4, "serek śmierdzielek", "zakaska", "w chuj dlugo", img));

        img = AppCompatResources.getDrawable(ctx, R.drawable.tort_piernikowy_mini);
        recipes.add(new Recipe(5, "sałatka owocowa", "sałatka", "20 minut", img));

        return recipes;
    }
}
