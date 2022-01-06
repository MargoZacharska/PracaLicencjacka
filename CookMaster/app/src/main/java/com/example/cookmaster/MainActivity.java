package com.example.cookmaster;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.cookmaster.adapters.IngredientAdapter;
import com.example.cookmaster.adapters.RecipeStepsAdapter;
import com.example.cookmaster.domain.RecipeIngredient;
import com.example.cookmaster.model.AnnotationRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.recip_list_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.last_recipe_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int lastRecipeId = MainActivity.this.getSharedPreferences("abs", Context.MODE_PRIVATE).getInt("LastVisitedRecipe", 0);
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                intent.putExtra("recipeId", lastRecipeId);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
