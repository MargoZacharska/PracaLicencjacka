package com.example.cookmaster.services;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends BaseAdapter {

    private Activity parent;
    private List<Recipe> recipes;

    public RecipeListAdapter(Activity activity, List<Recipe> recipes) {
        this.parent = activity;
        this.recipes = recipes;
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

        ((TextView) convertView.findViewById(R.id.recipe_description)).setText(recipe.description);
        ((TextView) convertView.findViewById(R.id.recipe_category)).setText(recipe.category);
        ((TextView) convertView.findViewById(R.id.recipe_preparation_time)).setText(recipe.preparationTime);

        return convertView;
    }
}
