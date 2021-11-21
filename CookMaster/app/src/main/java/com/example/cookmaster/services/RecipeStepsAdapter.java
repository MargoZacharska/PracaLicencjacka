package com.example.cookmaster.services;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.cookmaster.R;
import com.example.cookmaster.model.AnnotationRecipe;
import com.example.cookmaster.model.Procedure;
import com.example.cookmaster.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeStepsAdapter extends BaseAdapter {

    private Activity parent;
    private List<Procedure> steps;
    private List<AnnotationRecipe> notes;
    private DataService dataService;

    public RecipeStepsAdapter(Activity parent, List<Procedure> steps, List<AnnotationRecipe> notes, DataService dataService)
    {
        this.parent = parent;
        this.steps = steps;
        this.dataService = dataService;
        this.notes = notes;
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
        final Procedure step = steps.get(position);
        if (convertView == null) {
            convertView = parent.getLayoutInflater().inflate(R.layout.step, container, false);
        }
        final Context view = convertView.getContext();
        List<AnnotationRecipe> annotations = notes.stream().filter(x -> x.procedure_id == step.id).collect(Collectors.toList());

        Button button = (Button)convertView.findViewById(R.id.addNoteButton);
        button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view);
            builder.setTitle("New step annotation");

            final EditText input = new EditText(view);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dataService.AddAnnotation(new AnnotationRecipe(0, 1, step.id, input.getText().toString()));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

        ((TextView) (convertView.findViewById(R.id.step_desc))).setText(step.description);
        TextView noteNumberFields = (TextView) convertView.findViewById(R.id.notesNumber);
        noteNumberFields.setText("" + annotations.size());
        noteNumberFields.setVisibility(View.VISIBLE);

        return convertView;
    }
}
