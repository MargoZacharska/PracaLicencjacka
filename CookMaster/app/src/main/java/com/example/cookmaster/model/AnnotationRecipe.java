package com.example.cookmaster.model;

public class AnnotationRecipe {
    public AnnotationRecipe(int id, int user_id, int procedure_id, String description){
        this.id = id;
        this.user_id = user_id;
        this.procedure_id = procedure_id;
        this.description = description;
    }

    public long id;
    public int user_id;
    public int procedure_id;
    public String description;
}
