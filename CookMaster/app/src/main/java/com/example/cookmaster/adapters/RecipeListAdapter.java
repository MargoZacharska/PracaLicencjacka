package com.example.cookmaster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookmaster.MainActivity;
import com.example.cookmaster.R;
import com.example.cookmaster.RecipeActivity;
import com.example.cookmaster.domain.FullRecipe;
import com.example.cookmaster.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends BaseAdapter implements Filterable {

    private Activity parent;
    private List<FullRecipe> allRecipes;
    private List<FullRecipe> recipes;
    private List<Recipe> recipesAddedToShoppingList;

    public RecipeListAdapter(Activity activity, List<FullRecipe> recipes, List<Recipe> recipesAddedToShoppingList) {
        this.parent = activity;
        this.recipes = recipes;
        this.allRecipes = recipes;
        this.recipesAddedToShoppingList = recipesAddedToShoppingList;
    }

    public void updateAddedRecipes(List<Recipe> addedRecipes)
    {
        if(addedRecipes.size() != recipesAddedToShoppingList.size() || recipesAddedToShoppingList.stream().filter(x -> !addedRecipes.contains(x)).count() > 0) {
            recipesAddedToShoppingList = addedRecipes;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int position) {
        return recipes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recipes.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = parent.getLayoutInflater().inflate(R.layout.recipe_list_layout, container, false);
        }
        FullRecipe recipe = this.recipes.get(position);

        ((ImageView) convertView.findViewById(R.id.recipe_image)).setImageDrawable(recipe.image);
        ((TextView) convertView.findViewById(R.id.recipe_title)).setText(recipe.name);
        ((TextView) convertView.findViewById(R.id.recipe_category)).setText(recipe.category);
        ((TextView) convertView.findViewById(R.id.recipe_preparation_time)).setText("Czas przygotowania: " + recipe.preparationTime + " minut");

        boolean isAddedToShoppingList = recipesAddedToShoppingList.stream().anyMatch(x -> x.id == recipe.id);
        TextView isAddedTextView = (TextView)convertView.findViewById(R.id.recipe_is_added);
        isAddedTextView.setText(isAddedToShoppingList? "Dadany do listy zakupów" : "");
        isAddedTextView.setVisibility(isAddedToShoppingList? View.VISIBLE : View.INVISIBLE);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra("recipeId", recipe.id);

                context.startActivity(intent);
            }
        };
        convertView.setOnClickListener(clickListener);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = allRecipes.size();
                    filterResults.values = allRecipes;

                } else {
                    List<FullRecipe> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (FullRecipe recipe : allRecipes) {
                        if (recipe.name.contains(searchStr) || recipe.category.contains(searchStr)) {
                            resultsModel.add(recipe);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                recipes = (List<FullRecipe>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
