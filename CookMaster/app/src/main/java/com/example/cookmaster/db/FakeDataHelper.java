package com.example.cookmaster.db;

import android.app.Activity;

import com.example.cookmaster.R;
import com.example.cookmaster.model.Ingredient;
import com.example.cookmaster.model.IngredientRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FakeDataHelper {
    public static void addTestData(DataService dataService, Activity activity) {

        long soupTag = dataService.AddTag("zupa");
        long cheapTag = dataService.AddTag("tani");
        long xdTag = dataService.AddTag("z podwórka somsiada");
        long cakeTag = dataService.AddTag("ciasto");
        long healthyTag = dataService.AddTag("zdrowe");

        Ingredient woda = new Ingredient(0, "woda", 0, 0, 0, 0, 0, "ml");
        dataService.AddAIngredient(woda);

        Ingredient cukier = new Ingredient(0, "cukier", 1, 0, 0, 3.91, 0.004, "g");
        dataService.AddAIngredient(cukier);

        Ingredient maka = new Ingredient(0, "mąka", 0.686, 0.016, 0.11, 3.41, 0.004, "g");
        dataService.AddAIngredient(maka);

        Ingredient kurczak = new Ingredient(0, "pierś kurczaka", 0, 0.102, 0.168, 1.52, 0.02, "g");
        dataService.AddAIngredient(kurczak);

        Ingredient pepper = new Ingredient(0, "pieprz", 0, 0, 0, 0, 0, "szczypy");
        dataService.AddAIngredient(pepper);

        Ingredient salt = new Ingredient(0, "sól", 0, 0, 0, 0, 0.004, "g");
        dataService.AddAIngredient(salt);

        Ingredient chili = new Ingredient(0, "świeże chili", 0.065, 0.005, 0.015, 0.3, 0.02, "g");
        dataService.AddAIngredient(chili);

        Ingredient lettuce = new Ingredient(0, "sałata", 0.029, 0.002, 0.014, 0.16, 0.01, "g");
        dataService.AddAIngredient(lettuce);

        Ingredient apples = new Ingredient(0, "jabłka", 0.14, 0.002, 0.003, 0.52, 0.08, "g");
        dataService.AddAIngredient(lettuce);

        Ingredient chocolate = new Ingredient(0, "czekolada gorzka", 0.611, 0.323, 0.049, 5.46, 0.04, "g");
        dataService.AddAIngredient(lettuce);

        dataService.AddRecipe(
                new Recipe(1, "Taka tam beza", "ciasto", "", 30, activity.getResources().getDrawable(R.drawable.beza_mini)),
                Arrays.asList(
                        new Procedure("Idz do sklepu"),
                        new Procedure("Wybierz beze"),
                        new Procedure("Zaplac w kasie")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100)
                ),
                Arrays.asList(
                    cakeTag,
                    cheapTag
                ));

        dataService.AddRecipe(
                new Recipe(1, "Dobre na sniadanie", "obiad", "", 25, activity.getResources().getDrawable(R.drawable.frittata_mini)),
                Arrays.asList(
                        new Procedure("Idz do kurnika"),
                        new Procedure("Zabierz kurze jajka"),
                        new Procedure("Stłucz na patelni"),
                        new Procedure("Usmaż")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100),
                        new IngredientRecipe(pepper.id, 0, 2),
                        new IngredientRecipe(salt.id, 0, 5)
                ),
                Arrays.asList(
                        cheapTag,
                        xdTag
                ));

        dataService.AddRecipe(
                new Recipe(1, "Zielenina", "salatka", "", 10, activity.getResources().getDrawable(R.drawable.salatka_mini)),
                Arrays.asList(
                        new Procedure("Idz na trawnik w parku"),
                        new Procedure("Skoś zielone kieleckim scyzorykiem"),
                        new Procedure("Wymieszaj w misie"),
                        new Procedure("Polej oliwą")
                ),
                Arrays.asList(
                        new IngredientRecipe(chili.id, 0, 50),
                        new IngredientRecipe(kurczak.id, 0, 100),
                        new IngredientRecipe(lettuce.id, 0, 200),
                        new IngredientRecipe(pepper.id, 0, 2)
                ),
                Arrays.asList(
                        xdTag,
                        healthyTag
                ));

        dataService.AddRecipe(
                new Recipe(1, "Cos z jablek", "ciasto", "", 60, activity.getResources().getDrawable(R.drawable.szarlotka_mini)),
                Arrays.asList(
                        new Procedure("Ukradnij łabłka sąsiadowi"),
                        new Procedure("Przeciśniej przez praskę"),
                        new Procedure("Piecz 20 min")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100),
                        new IngredientRecipe(apples.id, 0, 250)
                ),
                Arrays.asList(
                        cakeTag
                ));

        dataService.AddRecipe(new Recipe(1, "Jesienna zupa", "zupa", "", 40, activity.getResources().getDrawable(R.drawable.zupa_dyniowa_mini)),
                Arrays.asList(
                        new Procedure("Zabierze dynie z Halloween"),
                        new Procedure("Rozgnieć młotkiem"),
                        new Procedure("Gotuj na wolnym ogniu"),
                        new Procedure("Przelej do misy")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100),
                        new IngredientRecipe(pepper.id, 0, 2),
                        new IngredientRecipe(salt.id, 0, 5),
                        new IngredientRecipe(apples.id, 0, 100)
                ),
                Arrays.asList(
                        soupTag,
                        healthyTag
                ));

        dataService.AddRecipe(new Recipe(1, "torcik", "ciasto", "", 180, activity.getResources().getDrawable(R.drawable.tort_piernikowy_mini)),
                Arrays.asList(
                        new Procedure("Kup pierniki na allegro"),
                        new Procedure("Odbierz z paczkomatu"),
                        new Procedure("Poukładaj na talerzu"),
                        new Procedure("Polej lukrem")
                ),
                Arrays.asList(
                        new IngredientRecipe(woda.id, 0, 50),
                        new IngredientRecipe(maka.id, 0, 200),
                        new IngredientRecipe(cukier.id, 0, 100),
                        new IngredientRecipe(chocolate.id, 0, 100)
                ),
                Arrays.asList(
                        cakeTag
                ));
    }
}
