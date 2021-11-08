package com.example.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;
import com.example.cookmaster.services.RecipeListAdapter;

import java.util.List;

public class RecipeListActivity extends Activity {

    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataService = new DataService(this);
        setContentView(R.layout.activity_recipe_list);




        /*dataService.AddRecipe(new Recipe(1, "Taka tam beza", "ciasto", "", "", getResources().getDrawable(R.drawable.beza_mini)));
        dataService.AddRecipe(new Recipe(1, "Dobre na sniadanie", "obiad", "", "", getResources().getDrawable(R.drawable.frittata_mini)));
        dataService.AddRecipe(new Recipe(1, "Zielenina", "salatka", "", "", getResources().getDrawable(R.drawable.salatka_mini)));
        dataService.AddRecipe(new Recipe(1, "Cos z jablek", "ciasto", "", "", getResources().getDrawable(R.drawable.szarlotka_mini)));
        dataService.AddRecipe(new Recipe(1, "Jesienna zupa", "zupa", "", "", getResources().getDrawable(R.drawable.zupa_dyniowa_mini)));
        dataService.AddRecipe(new Recipe(1, "torcik", "ciasto", "", "", getResources().getDrawable(R.drawable.tort_piernikowy_mini)));*/

        List<Recipe> recipes = dataService.GetRecipes();

        RecipeListAdapter adapter = new RecipeListAdapter(this, recipes);
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