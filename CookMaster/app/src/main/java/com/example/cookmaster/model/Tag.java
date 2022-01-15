package com.example.cookmaster.model;

public class Tag {
    public Tag(int id, String tag, long recipId){
        this.id = id;
        this.tag = tag;
        this.recipeId = recipId;
    }

    public long recipeId;
    public int id;
    public String tag;
}
