package com.example.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cookmaster.db.FakeDataHelper;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;
import com.example.cookmaster.adapters.RecipeListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class RecipeListActivity extends Activity {

    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataService = new DataService(this);
        setContentView(R.layout.activity_recipe_list);

        //FakeDataHelper.addTestData(dataService, this);

        List<Recipe> recipes = dataService.GetRecipes();

        RecipeListAdapter adapter = new RecipeListAdapter(this, recipes);
        ListView recipeList = ((ListView)findViewById(R.id.recipe_list_view));
        recipeList.setAdapter(adapter);
        recipeList.setTextFilterEnabled(true);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_recipe_list);
        bottomNavigationView.setSelectedItemId(R.id.action_recipes);
        bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationList(this));

        ((EditText)findViewById(R.id.recipe_search)).addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
    }
}