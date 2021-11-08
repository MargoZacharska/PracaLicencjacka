package com.example.cookmaster;

import android.app.Activity;
import android.os.Bundle;

import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cookmaster.databinding.ActivityRecipeBinding;

public class RecipeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        DataService dataService = new DataService(this);
        int recipeId = getIntent().getIntExtra("recipeId", 0);
        Recipe recipe = dataService.GetRecipe(recipeId);

        ImageView image = (ImageView)findViewById(R.id.single_recipe_image);
        image.setImageDrawable(recipe.image);
    }
}