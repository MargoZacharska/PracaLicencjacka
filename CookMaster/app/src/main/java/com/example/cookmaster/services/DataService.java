package com.example.cookmaster.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.media.Rating;

import com.example.cookmaster.db.LocalDbConnector;
import com.example.cookmaster.model.AnnotationRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataService {

    private SQLiteDatabase Db;

    public DataService(Context context){
        LocalDbConnector test = new LocalDbConnector(context);
        Db = test.getWritableDatabase();
    }

    public void AddRecipe(Recipe recipe, List<Procedure> steps) {
        Bitmap bitmap = ((BitmapDrawable)recipe.image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put("NAME", recipe.name);
        values.put("DESCRIPTION", recipe.description);
        values.put("CATEGORY", recipe.category);
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
    }

    public void AddAnnotation(AnnotationRecipe annotation) {
        ContentValues values = new ContentValues();
        values.put("PROCEDURE_ID", annotation.procedure_id);
        values.put("USER_ID", annotation.user_id);
        values.put("DESCRIPTION", annotation.description);

        Db.insert("ANNOTATION_RECIPE", null, values);
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
                cursor.getString(4),
                d);
    }
}
