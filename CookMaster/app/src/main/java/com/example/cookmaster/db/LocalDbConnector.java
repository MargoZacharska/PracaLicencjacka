package com.example.cookmaster.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cookmaster.model.Recipe;

import static android.content.ContentValues.TAG;

public class LocalDbConnector extends SQLiteOpenHelper {

    private final String RecipeTable = "create table RECIPE ("
            + "RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "NAME TEXT,"
            + "DESCRIPTION TEXT,"
            + "CATEGORY TEXT,"
            + "PREPARATION_TIME TIME,"
            + "PHOTO BLOB);\n ";

    private final String UserTable = "create table USER ("
            + "USER_ID INTEGER NOT NULL primary key autoincrement,"
            + "NAME TEXT,"
            + "SURNAME TEXT,"
            + "MAIL TEXT,"
            + "PASSWORD_HASH TEXT,"
            + "SALT TEXT,"
            + "LICENSE BOOL);\n ";

    private final String IngredientTable = "create table INGREDIENT ("
            + "INGREDIENT_ID INTEGER NOT NULL primary key autoincrement,"
            + "NAME TEXT,"
            + "CARBOHYDRATES INTIGER,"
            + "FATS INTEGER,"
            + "PROTEINS INTEGER,"
            + "KCAL INTEGER,"
            + "COST INTEGER,"
            + "UNITS TEXT,"
            + "ATTRIBUTE TEXT);\n ";

    private final String IngredientRecipeTable = "create table INGREDIENT_RECIPE ("
            + "INGREDIENT_RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "INGREDIENT_ID INTEGER NOT NULL,"
            + "RECIPE_ID INTEGER NOT NULL,"
            + "QUANTITY INTEGER);\n ";

    private final String ProcedureTable = "create table PROCEDURE ("
            + "PROCEDURE_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL,"
            + "DESCRIPTION TEXT,"
            + "ORDER_NUMBER INTEGER);\n ";

    private final String RatingTable = "create table RATING ("
            + "RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "RATE INTEGER,"
            + "OPINION TEXT);\n ";

    private final String AnnotationRecipeTable = "create table ANNOTATION_RECIPE ("
            + "ANNOTATION_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "PROCEDURE_ID INTEGER NOT NULL,"
            + "DESCRIPTION TEXT);\n ";

    private final String TagTable = "create table TAG ("
            + "TAG_ID INTEGER NOT NULL primary key autoincrement,"
            + "TAG TEXT);\n ";

    private final String RecipeTagTable = "create table RECIPE_TAG ("
            + "TAG_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL);\n ";

    public LocalDbConnector(Context context) {
        // superclass constructor
        super(context, "clientDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate database");

       // db.execSQL(UserTable);
        db.execSQL(RecipeTable);
        db.execSQL(IngredientTable);
        db.execSQL(IngredientRecipeTable);
        db.execSQL(ProcedureTable);
        //db.execSQL(RatingTable);
        db.execSQL(AnnotationRecipeTable);
        //db.execSQL(TagTable);
        //db.execSQL(RecipeTagTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

