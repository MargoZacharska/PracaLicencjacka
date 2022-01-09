package com.example.cookmaster.db;

import android.app.Activity;

import com.example.cookmaster.R;
import com.example.cookmaster.model.Ingredient;
import com.example.cookmaster.model.IngredientRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;

import java.util.Arrays;

public class FakeDataHelper {
    public static void addTestData(DataService dataService, Activity activity) {

        Ingredient woda = new Ingredient(0, "woda", 0, 0, 0, 0, 0, "ml");
        dataService.AddAIngredient(woda);

        Ingredient cukier = new Ingredient(0, "cukier", 0, 0, 0, 0, 0, "g");
        dataService.AddAIngredient(cukier);

        Ingredient maka = new Ingredient(0, "mąka", 0, 0, 0, 0, 0, "g");
        dataService.AddAIngredient(maka);

        Ingredient kurczak = new Ingredient(0, "pierś kurczaka", 0, 0, 0, 0, 0, "g");
        dataService.AddAIngredient(kurczak);

        Ingredient chili = new Ingredient(0, "świeże chili", 0, 0, 0, 0, 0, "g");
        dataService.AddAIngredient(chili);

        dataService.AddRecipe(
                new Recipe(1, "Taka tam beza", "ciasto", "", "30 min", activity.getResources().getDrawable(R.drawable.beza_mini)),
                Arrays.asList(
                        new Procedure("Idz do sklepu"),
                        new Procedure("Wybierz beze"),
                        new Procedure("Zaplac w kasie")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ));

        dataService.AddRecipe(
                new Recipe(1, "Dobre na sniadanie", "obiad", "", "25 min", activity.getResources().getDrawable(R.drawable.frittata_mini)),
                Arrays.asList(
                        new Procedure("Idz do kurnika"),
                        new Procedure("Zabierz kurze jajka"),
                        new Procedure("Stłucz na patelni"),
                        new Procedure("Usmaż")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ));

        dataService.AddRecipe(
                new Recipe(1, "Zielenina", "salatka", "", "10 min", activity.getResources().getDrawable(R.drawable.salatka_mini)),
                Arrays.asList(
                        new Procedure("Idz na trawnik w parku"),
                        new Procedure("Skoś zielone kieleckim scyzorykiem"),
                        new Procedure("Wymieszaj w misie"),
                        new Procedure("Polej oliwą")
                ),
                Arrays.asList(
                        new IngredientRecipe(chili.id, 0, 50),
                        new IngredientRecipe(kurczak.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ));

        dataService.AddRecipe(
                new Recipe(1, "Cos z jablek", "ciasto", "", "1 h", activity.getResources().getDrawable(R.drawable.szarlotka_mini)),
                Arrays.asList(
                        new Procedure("Ukradnij łabłka sąsiadowi"),
                        new Procedure("Przeciśniej przez praskę"),
                        new Procedure("Piecz 20 min")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ));

        dataService.AddRecipe(new Recipe(1, "Jesienna zupa", "zupa", "", "40 min", activity.getResources().getDrawable(R.drawable.zupa_dyniowa_mini)),
                Arrays.asList(
                        new Procedure("Zabierze dynie z Halloween"),
                        new Procedure("Rozgnieć młotkiem"),
                        new Procedure("Gotuj na wolnym ogniu"),
                        new Procedure("Przelej do misy")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ));

        dataService.AddRecipe(new Recipe(1, "torcik", "ciasto", "", "3 h", activity.getResources().getDrawable(R.drawable.tort_piernikowy_mini)),
                Arrays.asList(
                        new Procedure("Kup pierniki na allegro"),
                        new Procedure("Odbierz z paczkomatu"),
                        new Procedure("Poukładaj na talerzu"),
                        new Procedure("Polej lukrem")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ));
    }
}
