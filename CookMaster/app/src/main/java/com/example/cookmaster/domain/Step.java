package com.example.cookmaster.domain;

import com.example.cookmaster.model.AnnotationRecipe;

import java.lang.annotation.Annotation;
import java.util.List;

public class Step {
    public Step(int id, int recipe_id, String description, List<AnnotationRecipe> annotations){
        this.id = id;
        this.recipe_id = recipe_id;
        this.description = description;
        this.annotations = annotations;
    }

    public int id;
    public int recipe_id;
    public String description;
    public List<AnnotationRecipe> annotations;
}
