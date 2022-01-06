package com.example.cookmaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationList implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Activity context;

    public NavigationList(Activity context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                if(context instanceof MainActivity)
                {
                    Toast.makeText(context, "Main", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
                break;
            case R.id.action_recipes:
                if(context instanceof RecipeListActivity)
                {
                    Toast.makeText(context, "List", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(context, RecipeListActivity.class);
                    context.startActivity(intent);
                }
                break;
            case R.id.action_last:
                if(context instanceof RecipeActivity)
                {
                    Toast.makeText(context, "Recipe", Toast.LENGTH_SHORT).show();
                }
                else {
                    int lastRecipeId = context.getSharedPreferences("abs", Context.MODE_PRIVATE).getInt("LastVisitedRecipe", 0);
                    Intent intent = new Intent(context, RecipeActivity.class);
                    intent.putExtra("recipeId", lastRecipeId);
                    context.startActivity(intent);
                }
                break;
            case R.id.my_week:
                Toast.makeText(context, "My week", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_shopping:
                Toast.makeText(context, "Shopping list", Toast.LENGTH_SHORT).show();
                context.setContentView(R.layout.shopping_list);
                break;
        }
        return true;
    }
}
