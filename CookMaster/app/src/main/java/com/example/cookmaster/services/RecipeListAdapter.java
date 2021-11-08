package com.example.cookmaster.services;

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
import com.example.cookmaster.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends BaseAdapter implements Filterable {

    private Activity parent;
    private List<Recipe> allRecipes;
    private List<Recipe> recipes;

    public RecipeListAdapter(Activity activity, List<Recipe> recipes) {
        this.parent = activity;
        this.recipes = recipes;
        this.allRecipes = recipes;
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
        Recipe recipe = this.recipes.get(position);

        ((ImageView) convertView.findViewById(R.id.recipe_image)).setImageDrawable(recipe.image);
        ((TextView) convertView.findViewById(R.id.recipe_title)).setText(recipe.name);
        ((TextView) convertView.findViewById(R.id.recipe_category)).setText(recipe.category);
        ((TextView) convertView.findViewById(R.id.recipe_preparation_time)).setText("Czas przygotowania: " + recipe.preparationTime);

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
                    List<Recipe> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Recipe recipe : allRecipes) {
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
                recipes = (List<Recipe>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}
