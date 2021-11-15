package com.example.cookmaster.services;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cookmaster.R;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;

import java.util.List;

public class RecipeStepsAdapter extends BaseAdapter {

    private Activity parent;
    private List<Procedure> steps;

    public RecipeStepsAdapter(Activity parent, List<Procedure> steps)
    {
        this.parent = parent;
        this.steps = steps;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) { return steps.get(position);}

    @Override
    public long getItemId(int position) { return steps.get(position).id;}

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        Procedure step = steps.get(position);
        if (convertView == null) {
            convertView = parent.getLayoutInflater().inflate(R.layout.step, container, false);
        }
        ((TextView) (convertView.findViewById(R.id.step_desc))).setText(step.description);
        return convertView;
    }
}
