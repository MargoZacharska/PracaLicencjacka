package com.example.cookmaster;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cookmaster.model.AnnotationRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;
import com.example.cookmaster.services.RecipeListAdapter;
import com.example.cookmaster.services.RecipeStepsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cookmaster.databinding.ActivityRecipeBinding;

import java.util.List;

public class RecipeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        DataService dataService = new DataService(this);
        int recipeId = getIntent().getIntExtra("recipeId", 0);
        Recipe recipe = dataService.GetRecipe(recipeId);

        if(recipe == null){
            //TODO: implement
        }
        else{
            storeLastViewedRecipe(recipeId);

            ImageView image = (ImageView)findViewById(R.id.single_recipe_image);
            image.setImageDrawable(recipe.image);

            List<Procedure> steps = dataService.GetRecipeSteps(recipeId);
            List<AnnotationRecipe> annotations = dataService.GetAnnotations(recipeId);
            RecipeStepsAdapter adapter = new RecipeStepsAdapter(this, steps, annotations, dataService);
            ExpandableListView recipeList = ((ExpandableListView)findViewById(R.id.recipe_steps));
            recipeList.setAdapter(adapter);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {"aa", "b"});
            ListView ingredientsList = ((ListView)findViewById(R.id.ingredients));
            ingredientsList.setAdapter(adapter2);
        }

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