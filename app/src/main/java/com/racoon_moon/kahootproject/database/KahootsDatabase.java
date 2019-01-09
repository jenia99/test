package com.racoon_moon.kahootproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.racoon_moon.kahootproject.AddQuestions;
import com.racoon_moon.kahootproject.questions.data.Question;

public class KahootsDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KahootsQuestions";
    public static final String TABLE_NAME = "questions";

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QUESTION = "Question";
    public static final String COLUMN_ANSWER1 = "Answer1";
    public static final String COLUMN_ANSWER2 = "Answer2";
    public static final String COLUMN_ANSWER3 = "Answer3";
    public static final String COLUMN_ANSWER4 = "Answer4";

    public KahootsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_QUESTION + " VARCHAR,"
                + COLUMN_ANSWER1 + " VARCHAR,"
                + COLUMN_ANSWER2 + " VARCHAR,"
                + COLUMN_ANSWER3 + " VARCHAR,"
                + COLUMN_ANSWER4 + " VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertKahoot(String question, String answer1, String answer2, String answer3, String answer4){
        AddQuestions.kahootCounter++;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUESTION, question);
        contentValues.put(COLUMN_ANSWER1, answer1);
        contentValues.put(COLUMN_ANSWER2, answer2);
        contentValues.put(COLUMN_ANSWER3, answer3);
        contentValues.put(COLUMN_ANSWER4, answer4);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }else return true;
    }

    public int updateKahoot(Question question){
        SQLiteDatabase db = this.getWritableDatabase();
        int ID = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, question.getId());

        ID = db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?",
                new String[] {question.getId()});
        return ID;
    }
}
