package com.racoon_moon.kahootproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.racoon_moon.kahootproject.AddQuestions;
import com.racoon_moon.kahootproject.questions.data.Question;

import java.security.PublicKey;

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

    public static final String[] COLUMNS = {COLUMN_ID, COLUMN_QUESTION, COLUMN_ANSWER1,
                                            COLUMN_ANSWER2, COLUMN_ANSWER3, COLUMN_ANSWER4};

    SQLiteDatabase db = null;

    public KahootsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " ( "
                + COLUMN_ID + " VARCHAR PRIMARY KEY, "
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
        db = this.getWritableDatabase();
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

    public boolean updateKahoot(Question question){
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUESTION, question.getQuestion());
        contentValues.put(COLUMN_ANSWER1, question.getAnswer1());
        contentValues.put(COLUMN_ANSWER2, question.getAnswer2());
        contentValues.put(COLUMN_ANSWER3, question.getAnswer3());
        contentValues.put(COLUMN_ANSWER4, question.getAnswer4());

        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?",
                new String[] {question.getId()});
        return true;
    }

    public Question readQuestion(String id){
        db = this.getReadableDatabase();
        Question question = null;
        Cursor cursor = null;

        try {
            Log.i("TRY", "TRYING");
            cursor = db.query(TABLE_NAME, COLUMNS, COLUMN_ID + " = ?", new String[] { id },
                    null, null, null, null);
            Log.i("CURSOR CREATION", "CURSOR CREATED");
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                question = new Question();
                Log.i("CREATE QUESTION OBJECT", "QUESTION CREATED");
                question.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                Log.i("SET QUESTION", "QUESTION: " + cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                question.setAnswer1(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER1)));
                Log.i("SET ANSWER1", "ANSWER1: " + cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER1)));
                question.setAnswer2(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER2)));
                Log.i("SET ANSWER2", "ANSWER2: " + cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER2)));
                question.setAnswer3(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER3)));
                Log.i("SET ANSWER3", "ANSWER3: " + cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER3)));
                question.setAnswer4(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER4)));
                Log.i("SET ANSWER4", "ANSWER4: " + cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER4)));


            }
        }catch (Throwable t){
            Log.i("CATCH", "FAILED TO READ");
            t.printStackTrace();
        }

        if (cursor != null){
            cursor.close();
        }
        return question;
    }

    public Integer deleteQuestion(String id){
        db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { id });
    }

    public Cursor getAll(){
        db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }
}
