package com.racoon_moon.kahootproject.database;

import android.content.Context;

public class DatabaseHandler {
    private static DatabaseHandler instance = null;
    private Context context = null;
    private KahootsDatabase db = null;

    public static DatabaseHandler getInstance(){
        if (instance == null){
            instance = new DatabaseHandler();
        }
        return instance;
    }

    public void openDatabase(Context context){
        this.context = context;
        if (context != null){
            db = new KahootsDatabase(context);
            db.getWritableDatabase();
        }
    }

    public void closeDatabase(){
        if (db != null){
            db.close();
        }
    }
}
