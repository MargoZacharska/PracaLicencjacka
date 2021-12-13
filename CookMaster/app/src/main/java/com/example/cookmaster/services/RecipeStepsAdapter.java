package com.example.cookmaster.services;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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

public class RecipeStepsAdapter extends BaseExpandableListAdapter {

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
    public View getGroupView(int position, boolean b, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = parent.getLayoutInflater().inflate(R.layout.step, container, false);
        }
        final Context view = convertView.getContext();
        final Procedure step = steps.get(position);
        List<AnnotationRecipe> annotations = GetAnnotations(position);

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
                    AnnotationRecipe newAnnotation = new AnnotationRecipe(0, 1, step.id, input.getText().toString());
                    dataService.AddAnnotation(newAnnotation);
                    notes.add(newAnnotation);
                    notifyDataSetChanged();
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

    @Override
    public int getGroupCount()  {
        return steps.size();
    }

    @Override
    public int getChildrenCount(int i) { return GetAnnotations(i).size(); }

    @Override
    public Object getGroup(int i) { return steps.get(i); }

    @Override
    public Object getChild(int i, int i1) {
        return GetAnnotations(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return steps.get(i).id;
    }

    @Override
    public long getChildId(int i, int i1) {
        return GetAnnotations(i).get(i1).id;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = parent.getLayoutInflater().inflate(R.layout.annotation, container, false);
        }

        final Context view = convertView.getContext();
        List<AnnotationRecipe> annotations = GetAnnotations(i);
        AnnotationRecipe annotation = annotations.get(i1);

        Button removeButton = (Button)convertView.findViewById(R.id.removeNoteButton);
        removeButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view);
            builder.setTitle("Do you want to remove?");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dataService.RemoveAnnotation(annotation.id);
                    notes.remove(annotation);
                    notifyDataSetChanged();
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

        ((TextView) (convertView.findViewById(R.id.annotation_desc))).setText(annotation.description);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private List<AnnotationRecipe> GetAnnotations(int i){
        final Procedure step = steps.get(i);
        return notes.stream().filter(x -> x.procedure_id == step.id).collect(Collectors.toList());
    }
}
