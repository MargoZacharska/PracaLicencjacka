package com.example.cookmaster;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.cookmaster.adapters.IngredientAdapter;
import com.example.cookmaster.adapters.NutrientListAdapter;
import com.example.cookmaster.domain.FullRecipe;
import com.example.cookmaster.domain.Nutrient;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.model.Tag;
import com.example.cookmaster.services.DataService;
import com.example.cookmaster.adapters.RecipeStepsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class RecipeActivity extends Activity {

    private boolean isRecipeAdded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        DataService dataService = new DataService(this);
        int recipeId = getIntent().getIntExtra("recipeId", 0);
        FullRecipe recipe = dataService.GetFullRecipes(recipeId);

        if(recipe == null){
            //TODO: implement
        }
        else{
            storeLastViewedRecipe(recipeId);

            ImageView image = (ImageView)findViewById(R.id.single_recipe_image);
            image.setImageDrawable(recipe.image);

            RecipeStepsAdapter stepAdapter = new RecipeStepsAdapter(this, recipe.steps, dataService);
            ExpandableListView recipeList = ((ExpandableListView)findViewById(R.id.recipe_steps));
            recipeList.setAdapter(stepAdapter);

            IngredientAdapter ingredientAdapter = new IngredientAdapter(this, R.layout.ingredient, recipe.ingredients);
            ListView ingredientsList = ((ListView)findViewById(R.id.ingredients));
            ingredientsList.setAdapter(ingredientAdapter);

            List<Recipe> usersRecipes = dataService.GetRecipes(0);
            isRecipeAdded = usersRecipes.stream().anyMatch(x -> x.id == recipeId);

            setButton(dataService, recipeId);
            setTags(recipe);
            setNutrients(recipe);
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_recipe);
        bottomNavigationView.setSelectedItemId(R.id.action_last);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationList(this));
    }

    private void setButton(DataService dataService, int recipeId) {
        Button button = (Button)findViewById(R.id.add_to_shopping_list);
        setButtonStyle(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecipeAdded)
                {
                    dataService.RemoveRecipeFromUser(recipeId, 0);
                }
                else
                {
                    dataService.AddRecipeToUser(recipeId, 0);
                }
                Toast.makeText(
                        RecipeActivity.this,
                        isRecipeAdded? "Przepis usunięty z listy zakupów" : "Przepis dodany do listy zakupów",
                        Toast.LENGTH_SHORT
                ).show();
                isRecipeAdded = !isRecipeAdded;
                setButtonStyle(button);
            }
        });
    }

    private void setTags(FullRecipe recipe) {
        FlowLayout tagList = findViewById(R.id.tags);
        for (Tag t: recipe.tags) {
            TextView textView = new TextView(new ContextThemeWrapper(this, R.style.CustomView), null, 0);
            textView.setText(t.tag);
            tagList.addView(textView);

        }
        TextView recipeCostTextView = (TextView)findViewById(R.id.recipe_cost);
        recipeCostTextView.setText("Koszt: " + recipe.cost + " zł");
    }

    private void setNutrients(FullRecipe recipe) {
        List<Nutrient> nutrients = Arrays.asList(
                new Nutrient("kalorie", recipe.kcal),
                new Nutrient("białko", recipe.proteins),
                new Nutrient("tłuszcz", recipe.fats),
                new Nutrient("węklowodany", recipe.carbohydrates)
        );
        NutrientListAdapter nutrientAdapter = new NutrientListAdapter(this, R.layout.ingredient, nutrients);
        ListView nutrientList = ((ListView)findViewById(R.id.nutrients));
        nutrientList.setAdapter(nutrientAdapter);
    }

    private void setButtonStyle(Button button){
        button.setText(isRecipeAdded? "Usuń z listy zakupów" : "Dodaj do listy zakupów");
        button.setBackgroundColor(isRecipeAdded? Color.rgb(186, 57, 32) : Color.rgb(3, 165, 252));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_recipe);
        bottomNavigationView.setSelectedItemId(R.id.action_last);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationList(this));
    }

    private void storeLastViewedRecipe(int recipeId) {
        SharedPreferences sharedPref = getSharedPreferences("abs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("LastVisitedRecipe", recipeId);
        editor.apply();
    }
}