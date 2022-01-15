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
import com.example.cookmaster.db.Repository;
import com.example.cookmaster.domain.FullRecipe;
import com.example.cookmaster.domain.Nutrient;
import com.example.cookmaster.domain.RecipeIngredient;
import com.example.cookmaster.domain.ShoppingEntry;
import com.example.cookmaster.domain.SingleIngredient;
import com.example.cookmaster.domain.Step;
import com.example.cookmaster.model.AnnotationRecipe;
import com.example.cookmaster.model.Ingredient;
import com.example.cookmaster.model.IngredientRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.model.Tag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataService {

    private Repository Db;

    public DataService(Context context){
        Db = new Repository(context);
    }

    public List<FullRecipe> GetAllRecipes(long userId) {
        List<Recipe> recipes = Db.GetRecipes();
        List<RecipeIngredient> ingredients = Db.GetAllIngredients();
        List<Tag> tags = Db.GetAllTags();
        List<Procedure> steps = Db.GetAllRecipeSteps();
        List<AnnotationRecipe> annotations = Db.GetAllAnnotations();
        List<Integer> userRecipes = Db.GetRecipes(userId).stream().map(x -> x.id).collect(Collectors.toList());

        return recipes.stream()
                .map(x -> buildFullRecipe(x, ingredients, tags, steps, annotations, userRecipes))
                .collect(Collectors.toList());
    }

    public FullRecipe GetFullRecipes(int recipeId, long userId) {
        Recipe recipe = Db.GetRecipe(recipeId);
        List<RecipeIngredient> ingredients = Db.GetIngredients(recipeId);
        List<Tag> tags = Db.GetTags(recipeId);
        List<Procedure> steps = Db.GetRecipeSteps(recipeId);
        List<AnnotationRecipe> annotations = Db.GetAnnotations(recipeId);
        List<Integer> userRecipes = Db.GetRecipes(userId).stream().map(x -> x.id).collect(Collectors.toList());

        return buildFullRecipe(recipe, ingredients, tags, steps, annotations, userRecipes);
    }

    private FullRecipe buildFullRecipe(
            Recipe x,
            List<RecipeIngredient> allIngredients,
            List<Tag> allTags,
            List<Procedure> allSteps,
            List<AnnotationRecipe> allAnnotations,
            List<Integer> userRecipes) {
        List<RecipeIngredient> ingredients = allIngredients.stream().filter(ing -> ing.recipeId == ing.recipeId).collect(Collectors.toList());
        List<Tag> tags = allTags.stream().filter(t -> t.recipeId == x.id).collect(Collectors.toList());
        List<Step> steps = allSteps.stream()
                .filter(t -> t.recipe_id == x.id)
                .sorted(Comparator.comparingInt(a -> a.order_number))
                .map(s -> new Step(s.id, s.recipe_id, s.description, allAnnotations.stream().filter(a -> a.procedure_id == s.id).collect(Collectors.toList())))
                .collect(Collectors.toList());

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
                tags,
                steps,
                userRecipes.contains(x.id));
    }

    public void RemoveRecipeFromUser(long recipeId, long userId){
        Db.RemoveRecipeFromUser(recipeId, userId);
    }

    public void AddRecipeToUser(long recipeId, long userId){
        Db.AddRecipeToUser(recipeId, userId);
    }

    public List<Recipe> GetRecipes(long userId) {
        return Db.GetRecipes(userId);
    }

    public List<ShoppingEntry> GetShoppingList(long userId) {
        return Db.GetShoppingList(userId);
    }

    public void AddAnnotation(AnnotationRecipe annotation) {
        Db.AddAnnotation(annotation);
    }

    public void RemoveAnnotation(long annotationId) {
        Db.RemoveAnnotation(annotationId);
    }

    public void MarkIngredientAsBought(long userId, long ingredientId) {
        Db.MarkIngredientAsBought(userId, ingredientId);
    }
}
