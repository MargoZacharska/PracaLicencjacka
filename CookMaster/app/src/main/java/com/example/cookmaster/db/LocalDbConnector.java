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
            + "PREPARATION_TIME INTEGER,"
            + "PHOTO BLOB);\n ";

    private final String UserRecipeTable = "create table USER_RECIPE ("
            + "USER_RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL,"
            + "USER_ID INTEGER NOT NULL, "
            + "FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE(RECIPE_ID) ON DELETE CASCADE) ;\n ";
    //TODO: forign key to user


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
            + "QUANTITY INTEGER,"
            + "FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE(RECIPE_ID) ON DELETE CASCADE,"
            + "FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENT(INGREDIENT_ID) ON DELETE CASCADE);\n ";

    private final String ProcedureTable = "create table PROCEDURE ("
            + "PROCEDURE_ID INTEGER NOT NULL primary key autoincrement,"
            + "RECIPE_ID INTEGER NOT NULL,"
            + "DESCRIPTION TEXT,"
            + "ORDER_NUMBER INTEGER,"
            + "FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE(RECIPE_ID) ON DELETE CASCADE);\n ";

    private final String RatingTable = "create table RATING ("
            + "RECIPE_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "RATE INTEGER,"
            + "OPINION TEXT);\n ";

    private final String AnnotationRecipeTable = "create table ANNOTATION_RECIPE ("
            + "ANNOTATION_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "PROCEDURE_ID INTEGER NOT NULL,"
            + "DESCRIPTION TEXT,"
            + "FOREIGN KEY (PROCEDURE_ID) REFERENCES PROCEDURE(PROCEDURE_ID) ON DELETE CASCADE);\n ";
    //TODO: foreign key to user

    private final String UserIngredientTable = "create table USER_INGREDIENT ("
            + "USER_INGREDIENT_ID INTEGER NOT NULL primary key autoincrement,"
            + "USER_ID INTEGER NOT NULL,"
            + "INGREDIENT_ID INTEGER,"
            + "USER_RECIPE_ID INTEGER,"
            + "IS_BOUGHT BOOLEAN,"
            + "FOREIGN KEY (INGREDIENT_ID) REFERENCES INGREDIENT(INGREDIENT_ID) ON DELETE CASCADE,"
            + "FOREIGN KEY (USER_RECIPE_ID) REFERENCES USER_RECIPE(USER_RECIPE_ID) ON DELETE CASCADE);\n";
    //TODO: foreign key to user

    private final String TagTable = "create table TAG ("
            + "TAG_ID INTEGER NOT NULL primary key autoincrement,"
            + "TAG TEXT);\n ";

    private final String RecipeTagTable = "create table RECIPE_TAG ("
            + "RECIPE_TAG_ID INTEGER NOT NULL primary key autoincrement,"
            + "TAG_ID INTEGER NOT NULL, "
            + "RECIPE_ID INTEGER NOT NULL, "
            + "FOREIGN KEY (RECIPE_ID) REFERENCES RECIPE(RECIPE_ID) ON DELETE CASCADE, "
            + "FOREIGN KEY (TAG_ID) REFERENCES TAG(TAG_ID) ON DELETE CASCADE);\n ";

    public LocalDbConnector(Context context) {
        // superclass constructor
        super(context, "clientDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate database");

       // db.execSQL(UserTable);
        db.execSQL(RecipeTable);
        db.execSQL(UserRecipeTable);
        db.execSQL(IngredientTable);
        db.execSQL(IngredientRecipeTable);
        db.execSQL(ProcedureTable);
        db.execSQL(UserIngredientTable);
        //db.execSQL(RatingTable);
        db.execSQL(AnnotationRecipeTable);
        db.execSQL(TagTable);
        db.execSQL(RecipeTagTable);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

