package com.example.cookmaster.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.example.cookmaster.db.LocalDbConnector;
import com.example.cookmaster.domain.FullRecipe;
import com.example.cookmaster.domain.Nutrient;
import com.example.cookmaster.domain.RecipeIngredient;
import com.example.cookmaster.domain.ShoppingEntry;
import com.example.cookmaster.domain.SingleIngredient;
import com.example.cookmaster.model.AnnotationRecipe;
import com.example.cookmaster.model.Ingredient;
import com.example.cookmaster.model.IngredientRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.model.Tag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataService {

    private SQLiteDatabase Db;

    public DataService(Context context){
        LocalDbConnector test = new LocalDbConnector(context);
        Db = test.getWritableDatabase();
    }

    public List<FullRecipe> GetAllRecipes() {
        List<Recipe> recipes = this.GetRecipes();
        List<RecipeIngredient> ingredients = this.GetAllIngredients();
        List<Tag> tags = this.GetAllTags();

        return recipes.stream().map(x -> buildFullRecipe(x, ingredients, tags)).collect(Collectors.toList());
    }

    public FullRecipe GetFullRecipes(int recipeId) {
        Recipe recipe = this.GetRecipe(recipeId);
        List<RecipeIngredient> ingredients = this.GetIngredients(recipeId);
        List<Tag> tags = this.GetTags(recipeId);

        return buildFullRecipe(recipe, ingredients, tags);
    }

    private FullRecipe buildFullRecipe(Recipe x, List<RecipeIngredient> allIngredients, List<Tag> allTags) {
        List<RecipeIngredient> ingredients = allIngredients.stream().filter(ing -> ing.recipeId == ing.recipeId).collect(Collectors.toList());
        List<Tag> tags = allTags.stream().filter(t -> t.recipeId == t.recipeId).collect(Collectors.toList());

        return new FullRecipe(
                x.id,
                x.name,
                x.description,
                x.category,
                x.preparationTime,
                x.image,
                ingredients.stream().map(ing -> new SingleIngredient(ing.name, ing.quantity, ing.units)).collect(Collectors.toList()),
                (int)ingredients.stream().mapToDouble( (r) -> r.carbohydrates * r.quantity).sum(),
                (int)ingredients.stream().mapToDouble( (r) -> r.fats * r.quantity).sum(),
                (int)ingredients.stream().mapToDouble( (r) -> r.proteins * r.quantity).sum(),
                (int)ingredients.stream().mapToDouble( (r) -> r.kcal * r.quantity).sum(),
                (int)ingredients.stream().mapToDouble( (r) -> r.cost * r.quantity).sum(),
                tags);
    }

    public long AddTag(String text) {
        ContentValues values = new ContentValues();
        values.put("TAG", text);
        return Db.insert("TAG", null, values);
    }

    public List<Tag> GetTags(long recipeId){
        List<Tag> result = new ArrayList<Tag>();

        String sql = "SELECT t.tag_id, t.tag, rt.recipe_ID " +
                "FROM TAG t " +
                "JOIN RECIPE_TAG rt ON(t.TAG_ID = rt.TAG_ID) " +
                "WHERE rt.recipe_ID = ? ;";
        Cursor cursor = Db.rawQuery(sql, new String[]{recipeId + ""});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadTag(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<Tag> GetAllTags(){
        List<Tag> result = new ArrayList<Tag>();

        String sql = "SELECT t.tag_id, t.tag, rt.recipe_ID " +
                "FROM TAG t " +
                "JOIN RECIPE_TAG rt ON(t.TAG_ID = rt.TAG_ID);";
        Cursor cursor = Db.rawQuery(sql, new String[]{});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadTag(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void AddRecipe(Recipe recipe, List<Procedure> steps, List<IngredientRecipe> ingredients, List<Long> tags) {
        Bitmap bitmap = ((BitmapDrawable)recipe.image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put("NAME", recipe.name);
        values.put("DESCRIPTION", recipe.description);
        values.put("CATEGORY", recipe.category);
        values.put("PREPARATION_TIME", recipe.preparationTime);
        values.put("PHOTO", bitMapData);

        long recipeId = Db.insert("recipe", null, values);

        int order = 0;
        for (Procedure p : steps) {
            ContentValues stepValues = new ContentValues();
            stepValues.put("RECIPE_ID", recipeId);
            stepValues.put("DESCRIPTION", p.description);
            stepValues.put("ORDER_NUMBER", order++);
            Db.insert("procedure", null, stepValues);
        }

        for (IngredientRecipe ing : ingredients) {
            ContentValues stepValues = new ContentValues();
            stepValues.put("INGREDIENT_ID", ing.ingredient_id);
            stepValues.put("RECIPE_ID", recipeId);
            stepValues.put("QUANTITY", ing.quantity);
            Db.insert("INGREDIENT_RECIPE", null, stepValues);
        }

        for (Long tagId : tags) {
            ContentValues stepValues = new ContentValues();
            stepValues.put("RECIPE_ID", recipeId);
            stepValues.put("TAG_ID", tagId);
            Db.insert("RECIPE_TAG", null, stepValues);
        }
    }

    public void AddRecipeToUser(long recipeId, long userId) {
        ContentValues values = new ContentValues();
        values.put("RECIPE_ID", recipeId);
        values.put("USER_ID", userId);

        long id = Db.insert("USER_RECIPE", null, values);

        Db.execSQL(
                "INSERT INTO USER_INGREDIENT (USER_ID, INGREDIENT_ID, USER_RECIPE_ID, IS_BOUGHT) " +
                    "SELECT ? AS 'USER_ID', i.INGREDIENT_ID, ? AS 'USER_RECIPE_ID', 0 AS 'IS_BOUGHT' " +
                    "FROM INGREDIENT i JOIN INGREDIENT_RECIPE ir ON(i.INGREDIENT_ID = ir.INGREDIENT_ID) " +
                    "WHERE ir.RECIPE_ID = ?",
                new String[] {userId + "", id + "", recipeId + ""});
    }

    public void RemoveRecipeFromUser(long recipeId, long userId) {
        Db.delete("USER_RECIPE", "USER_ID = ? AND RECIPE_ID = ?", new String[]{ userId + "", recipeId + "" });
    }

    public void MarkIngredientAsBought(long userId, long ingredientId) {
        ContentValues values = new ContentValues();
        values.put("IS_BOUGHT", true);
        Db.update("USER_INGREDIENT", values, "user_id = ? AND INGREDIENT_ID = ?", new String[] {userId + "", ingredientId + ""});
    }

    public List<ShoppingEntry> GetShoppingList(long userId) {
        List<ShoppingEntry> result = new ArrayList<ShoppingEntry>();

        String sql = "SELECT i.NAME, i.UNITS, ui.IS_BOUGHT, SUM(ir.quantity), i.INGREDIENT_ID " +
                "FROM USER_RECIPE ur " +
                   "JOIN INGREDIENT_RECIPE ir ON(ur.RECIPE_ID = ir.RECIPE_ID) " +
                   "JOIN USER_INGREDIENT ui ON(ui.INGREDIENT_ID = ir.INGREDIENT_ID AND ui.USER_RECIPE_ID = ur.USER_RECIPE_ID) " +
                   "JOIN INGREDIENT i ON(ui.INGREDIENT_ID = i.INGREDIENT_ID) " +
                "WHERE ui.user_id = ? " +
                "GROUP BY i.NAME, i.INGREDIENT_ID, i.UNITS, ui.IS_BOUGHT;";
        Cursor cursor = Db.rawQuery(sql, new String[]{userId + ""});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadShoppingEntry(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void AddAIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put("NAME", ingredient.name);
        values.put("CARBOHYDRATES", ingredient.carbohydrates);
        values.put("FATS", ingredient.fats);
        values.put("PROTEINS", ingredient.proteins);
        values.put("KCAL", ingredient.kcal);
        values.put("COST", ingredient.cost);
        values.put("UNITS", ingredient.units);

        long id = Db.insert("INGREDIENT", null, values);
        ingredient.id = id;
    }

    public void AddAnnotation(AnnotationRecipe annotation) {
        ContentValues values = new ContentValues();
        values.put("PROCEDURE_ID", annotation.procedure_id);
        values.put("USER_ID", annotation.user_id);
        values.put("DESCRIPTION", annotation.description);

        long id = Db.insert("ANNOTATION_RECIPE", null, values);
        annotation.id = id;
    }

    public void RemoveAnnotation(long annotationId) {
        long id = Db.delete("ANNOTATION_RECIPE", "ANNOTATION_ID=?", new String[] {"" + annotationId});
    }

    public List<Recipe> GetRecipes() {
        List<Recipe> result = new ArrayList<Recipe>();

        Cursor cursor = Db.rawQuery("SELECT * FROM recipe", new String[]{});
        if (cursor.moveToFirst()) {
            do {
                result.add(ReadRecipe(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<Recipe> GetRecipes(long userId) {
        List<Recipe> result = new ArrayList<Recipe>();

        String sql = "SELECT r.* " +
                "FROM user_recipe ur JOIN recipe r ON(ur.RECIPE_ID = r.RECIPE_ID) " +
                "WHERE ur.user_id = ? ;";
        Cursor cursor = Db.rawQuery(sql, new String[]{userId + ""});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadRecipe(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public Recipe GetRecipe(int id) {
        Cursor cursor = Db.rawQuery("SELECT * FROM recipe WHERE recipe_id = ?", new String[]{id + ""});
        if (cursor.moveToFirst()) {
            return ReadRecipe(cursor);
        }
        return null;
    }

    public List<Procedure> GetRecipeSteps(int recipeId){
        List<Procedure> result = new ArrayList<Procedure>();

        Cursor cursor = Db.rawQuery("SELECT * FROM procedure WHERE recipe_id = ?", new String[]{recipeId + ""});
        if (cursor.moveToFirst()) {
            do {
                result.add(ReadProcedure(cursor));
            } while (cursor.moveToNext());
        }
        result.sort((x, y) -> x.order_number - y.order_number);
        return result;
    }

    public List<AnnotationRecipe> GetAnnotations(int recipeId) {
        List<AnnotationRecipe> result = new ArrayList<AnnotationRecipe>();

        Cursor cursor = Db.rawQuery(
                "SELECT a.* " +
                    "FROM recipe r JOIN procedure p ON(r.RECIPE_ID = p.RECIPE_ID) JOIN ANNOTATION_RECIPE a ON(a.PROCEDURE_ID = p.PROCEDURE_ID) " +
                    "WHERE r.recipe_id = ?;",
                new String[]{recipeId + ""});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadAnnotation(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<RecipeIngredient> GetIngredients(int recipeId) {
        List<RecipeIngredient> result = new ArrayList<RecipeIngredient>();

        Cursor cursor = Db.rawQuery(
                "SELECT i.*, ir.QUANTITY, ir.RECIPE_ID " +
                    "FROM recipe r JOIN INGREDIENT_RECIPE ir ON(r.RECIPE_ID = ir.RECIPE_ID) JOIN INGREDIENT i ON(ir.INGREDIENT_ID = i.INGREDIENT_ID) " +
                    "WHERE r.recipe_id = ?;",
                new String[]{recipeId + ""});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadRecipeIngredient(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<RecipeIngredient> GetAllIngredients(){
        List<RecipeIngredient> result = new ArrayList<RecipeIngredient>();

        Cursor cursor = Db.rawQuery(
                "SELECT i.*, ir.QUANTITY, ir.RECIPE_ID " +
                        "FROM recipe r JOIN INGREDIENT_RECIPE ir ON(r.RECIPE_ID = ir.RECIPE_ID) JOIN INGREDIENT i ON(ir.INGREDIENT_ID = i.INGREDIENT_ID) " +
                        "WHERE r.recipe_id = ?;",
                new String[]{});

        if (cursor.moveToFirst()) {
            do {
                result.add(ReadRecipeIngredient(cursor));
            } while (cursor.moveToNext());
        }
        return result;
    }

    private RecipeIngredient ReadRecipeIngredient(Cursor cursor) {
        return new RecipeIngredient(
                cursor.getInt(9),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3),
                cursor.getDouble(4),
                cursor.getDouble(5),
                cursor.getDouble(6),
                cursor.getString(7),
                cursor.getInt(9));
    }

    private AnnotationRecipe ReadAnnotation(Cursor cursor) {
        return new AnnotationRecipe(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3));
    }

    private Procedure ReadProcedure(Cursor cursor) {
        return new Procedure(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3));
    }

    private Recipe ReadRecipe(Cursor cursor) {
        ByteArrayInputStream stream = new ByteArrayInputStream(cursor.getBlob(5));
        Bitmap bits = BitmapFactory.decodeStream(stream);
        bits = Bitmap.createScaledBitmap(bits, 1000, 1000, false);
        Drawable d = new BitmapDrawable(bits);

        return new Recipe(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                d);
    }

    private ShoppingEntry ReadShoppingEntry(Cursor cursor) {
        return new ShoppingEntry(
                cursor.getShort(2) != 0,
                cursor.getString(0),
                cursor.getInt(3),
                cursor.getString(1),
                cursor.getLong(4));
    }

    private Tag ReadTag(Cursor cursor) {
        return new Tag(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getLong(2));
    }
}
