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
                if(!(context instanceof MainActivity)) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
                break;

            case R.id.action_recipes:
                if(!(context instanceof RecipeListActivity)) {
                    Intent intent = new Intent(context, RecipeListActivity.class);
                    context.startActivity(intent);
                }
                break;

            case R.id.action_last:
                if(!(context instanceof RecipeActivity)) {
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
                if(!(context instanceof ShoppingList)) {
                    Intent intent = new Intent(context, ShoppingList.class);
                    context.startActivity(intent);
                }
                break;
        }
        return true;
    }
}
