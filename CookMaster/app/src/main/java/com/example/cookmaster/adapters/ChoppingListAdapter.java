package com.example.cookmaster.adapters;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.ShoppingList;
import com.example.cookmaster.domain.ShoppingEntry;
import com.example.cookmaster.model.Recipe;
import com.example.cookmaster.services.DataService;

import java.util.List;

public class ChoppingListAdapter extends BaseAdapter {

    public ChoppingListAdapter(List<ShoppingEntry> entries, boolean showBought, Activity context, DataService dataService) {
        this.entries = entries;
        this.showBought = showBought;
        this.context = context;
        this.dataService = dataService;
    }

    private List<ShoppingEntry> entries;
    private boolean showBought;
    private Activity context;
    private DataService dataService;

    @Override
    public int getCount() {
        return (int) entries.stream().filter(x -> x.isBought == showBought).count();
    }

    @Override
    public Object getItem(int i) {
        return get(i);
    }

    @Override
    public long getItemId(int i) {
        return get(i).name.hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = context.getLayoutInflater().inflate(R.layout.shopping_entry, viewGroup, false);
        }
        ShoppingEntry entry = get(i);
        ((TextView)view.findViewById(R.id.ingredient_name_shopping)).setText(entry.name);
        ((TextView)view.findViewById(R.id.quantity_shopping)).setText(entry.quantity + " " + entry.units);
        CheckBox checkBox = (CheckBox)view.findViewById(R.id.is_bougth_checkbox);
        checkBox.setChecked(entry.isBought);
        checkBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                entry.isBought = !entry.isBought;
                //dataService TODO: mark as taken in DB
                notifyDataSetChanged();
            }
        });
        return  view;
    }

    private ShoppingEntry get(int idx){
        return entries.stream().filter(x -> x.isBought == showBought).skip(idx).findFirst().get();
    }
}
