package com.example.cookmaster.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.domain.RecipeIngredient;
import com.example.cookmaster.domain.SingleIngredient;

import java.util.List;

public class IngredientAdapter extends ArrayAdapter<SingleIngredient> {
private int resource;
private List<SingleIngredient> items;
private Activity context;

    public IngredientAdapter(Activity context, int resource, List<SingleIngredient> items) {
            super(context, resource, items);
            this.resource = resource;
            this.items = items;
            this.context = context;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(resource, null, true);
        }
        SingleIngredient ing = items.get(position);
        ((TextView) convertView.findViewById(R.id.entry_name)).setText(ing.name);
        ((TextView) convertView.findViewById(R.id.entry_quantity)).setText("" + ing.quantity + " " + ing.units);
        return  convertView;
    }
}
