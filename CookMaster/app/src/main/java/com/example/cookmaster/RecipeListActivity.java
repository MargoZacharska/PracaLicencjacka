package com.example.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookmaster.services.DataService;
import com.example.cookmaster.services.RecipeListAdapter;

public class RecipeListActivity extends Activity {

    private DataService dataService = new DataService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        
        ((ListView)findViewById(R.id.recipe_list_view)).setAdapter(new RecipeListAdapter(this, dataService.GetRecipes()));
    }
}