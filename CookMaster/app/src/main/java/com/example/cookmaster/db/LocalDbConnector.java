package com.example.cookmaster.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class LocalDbConnector extends SQLiteOpenHelper {
    private final String RecipeTable = "create table RECIPE ("
            + "RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "NAME TEXT,"
            + "DESCRIPTION TEXT,"
            + "PHOTO TEXT,"
            + "CATEGORY TEXT,"
            + "PREPARATION_TIME TIME); ";

    private final String UserTable = "create table USER ("
            + "USER_ID INTEGER NOT NULL primary key autoincrement,"
            + "NAME TEXT,"
            + "SURNAME TEXT,"
            + "MAIL TEXT,"
            + "PASSWORD_HASH TEXT,"
            + "SALT TEXT,"
            + "LICENSE BOOL); ";

    private final String IngredientTable = "create table INGREDIENT ("
            + "INGREDIENT_ID INTEGER NOT NULL primary key autoincrement,"
            + "NAME TEXT,"
            + "CARBOHYDRATES INTIGER,"
            + "FATS INTEGER,"
            + "PROTEINS INTEGER,"
            + "KCAL INTEGER,"
            + "COST INTEGER,"
            + "ATTRIBUTE TEXT); ";

    private final String IngredientRecipeTable = "create table INGREDIENT_RECIPE ("
            + "INGREDIENT_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL,"
            + "QUANTITY INTEGER); ";

    private final String ProcedureTable = "create table PROCEDURE ("
            + "PROCEDURE_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL,"
            + "DESCRIPTION TEXT,"
            + "ORDER_NUMBER INTEGER); ";

    private final String RatingTable = "create table RATING ("
            + "RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "RATE INTEGER,"
            + "OPINION TEXT); ";

    private final String AnnotationRecipeTable = "create table ANNOTATION_RECIPE ("
            + "ANNOTATION_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "PROCEDURE_ID INTEGER NOT NULL,"
            + "DESCRIPTION TEXT); ";

    private final String TagTable = "create table TAG ("
            + "TAG_ID INTEGER NOT NULL primary key autoincrement,"
            + "TAG TEXT); ";

    private final String RecipeTagTable = "create table RECIPE_TAG ("
            + "TAG_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL); ";

    public LocalDbConnector(Context context) {
        // superclass constructor
        super(context, "clientDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate database");
        // create a table with fields
        String createTable = UserTable + RecipeTable + IngredientTable + IngredientRecipeTable +
                ProcedureTable + RatingTable + AnnotationRecipeTable + TagTable + RecipeTagTable;
        //db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
