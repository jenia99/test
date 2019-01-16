package com.racoon_moon.kahootproject.questions.data;

import android.graphics.Bitmap;

public class Quiz {
    private String id;
    private String name;
    private boolean visible;
    private Bitmap picture;

    public Quiz(){

    }

    public Quiz(String id, String name, Bitmap picture, boolean visible){
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.visible = visible;
    }

    public String getId(){ return id; }

    public String getName(){ return name; }

    public Bitmap getPicture() { return picture; }

    public boolean isVisible() { return visible; }

    public void setVisibility(boolean visible){ this.visible = visible; }

    public void setId(String id){ this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setPicture(Bitmap picture) { this.picture = picture; }
}
