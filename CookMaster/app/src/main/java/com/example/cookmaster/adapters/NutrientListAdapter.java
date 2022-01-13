package com.example.cookmaster.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.domain.Nutrient;

import java.util.List;

public class NutrientListAdapter extends ArrayAdapter<Nutrient> {
    private int resource;
    private List<Nutrient> items;
    private Activity context;

    public NutrientListAdapter(Activity context, int resource, List<Nutrient> items) {
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
        Nutrient ing = items.get(position);
        ((TextView) convertView.findViewById(R.id.entry_name)).setText(ing.name);
        ((TextView) convertView.findViewById(R.id.entry_quantity)).setText("" + ing.value);
        return  convertView;
    }
}
