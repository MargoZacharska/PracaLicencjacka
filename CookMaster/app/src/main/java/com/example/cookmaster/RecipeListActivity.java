package com.example.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
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

        RecipeListAdapter adapter = new RecipeListAdapter(this, dataService.GetRecipes(this));
        ListView recipeList = ((ListView)findViewById(R.id.recipe_list_view));
        recipeList.setAdapter(adapter);
        recipeList.setTextFilterEnabled(true);

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