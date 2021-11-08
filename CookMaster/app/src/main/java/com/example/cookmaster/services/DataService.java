package com.example.cookmaster.services;

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

    public void AddRecipe(Recipe recipe) {
        Bitmap bitmap = ((BitmapDrawable)recipe.image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        Db.execSQL(
                "INSERT INTO recipe (NAME, DESCRIPTION, CATEGORY, PHOTO) VALUES (?,?,?,?)",
                new Object[]{
                        recipe.name,
                        recipe.description,
                        recipe.category,
                        bitMapData
                });
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
