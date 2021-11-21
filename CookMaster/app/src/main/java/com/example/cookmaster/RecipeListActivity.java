package com.example.cookmaster;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;
import com.example.cookmaster.services.RecipeListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListActivity extends Activity {

    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataService = new DataService(this);
        setContentView(R.layout.activity_recipe_list);

        //addTestData();

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

    private void addTestData() {
        dataService.AddRecipe(
                new Recipe(1, "Taka tam beza", "ciasto", "", "", getResources().getDrawable(R.drawable.beza_mini)),
                Arrays.asList(
                    new Procedure("Idz do skleupu"),
                    new Procedure("Wybierz beze"),
                    new Procedure("Zaplacw kasie")
                ));
        dataService.AddRecipe(
                new Recipe(1, "Dobre na sniadanie", "obiad", "", "", getResources().getDrawable(R.drawable.frittata_mini)),
                Arrays.asList(
                        new Procedure("Idz do kurnika"),
                        new Procedure("Zabierz kurze jajka"),
                        new Procedure("Stłucz na patelni"),
                        new Procedure("Usmaż")
                ));
        dataService.AddRecipe(
                new Recipe(1, "Zielenina", "salatka", "", "", getResources().getDrawable(R.drawable.salatka_mini)),
                Arrays.asList(
                        new Procedure("Idz na trawnik w parku"),
                        new Procedure("Skos zielone scyzorykiem"),
                        new Procedure("Wymieszaj w misie"),
                        new Procedure("Polej oliwą")
                ));
        dataService.AddRecipe(
                new Recipe(1, "Cos z jablek", "ciasto", "", "", getResources().getDrawable(R.drawable.szarlotka_mini)),
                Arrays.asList(
                        new Procedure("Ukradnij łabłka sąsiadwi"),
                        new Procedure("Przeciśniej przez praskę"),
                        new Procedure("Piecz 20 in")
                ));
        dataService.AddRecipe(new Recipe(1, "Jesienna zupa", "zupa", "", "", getResources().getDrawable(R.drawable.zupa_dyniowa_mini)),
                Arrays.asList(
                        new Procedure("ZAbierze dynie z Halloween"),
                        new Procedure("Rozgnieć młotkiem"),
                        new Procedure("Gotuj na wolnym ogniu"),
                        new Procedure("Przelej edo misy")
                ));
        dataService.AddRecipe(new Recipe(1, "torcik", "ciasto", "", "", getResources().getDrawable(R.drawable.tort_piernikowy_mini)),
                Arrays.asList(
                        new Procedure("Kup pierniki na allegro"),
                        new Procedure("Odbierz z paczkomatu"),
                        new Procedure("poukładaj na talerzu"),
                        new Procedure("Polej lukrem")
                ));
    }
}