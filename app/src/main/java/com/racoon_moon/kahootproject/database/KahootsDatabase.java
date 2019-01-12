package com.racoon_moon.kahootproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.racoon_moon.kahootproject.AddQuestions;
import com.racoon_moon.kahootproject.questions.data.Question;
import com.racoon_moon.kahootproject.questions.data.Quiz;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class KahootsDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KahootsQuestions";

    public static final String KAHOOT_TABLE_NAME = "questions";
    public static final String COLUMN_KAHOOT_ID = "ID";
    public static final String COLUMN_QUESTION = "Question";
    public static final String COLUMN_ANSWER1 = "Answer1";
    public static final String COLUMN_ANSWER2 = "Answer2";
    public static final String COLUMN_ANSWER3 = "Answer3";
    public static final String COLUMN_ANSWER4 = "Answer4";

    public static final String QUIZ_TABLE_NAME = "quizzes";
    public static final String COLUMN_QUIZ_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_VISIBLE = "Visible";

    public static final String[] QUESTION_COLUMNS = {COLUMN_KAHOOT_ID, COLUMN_QUESTION, COLUMN_ANSWER1,
                                            COLUMN_ANSWER2, COLUMN_ANSWER3, COLUMN_ANSWER4};

    SQLiteDatabase db = null;

    public KahootsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("RUN ON CREATE METHOD", "STARTED");
        db.execSQL("create table if not exists " + QUIZ_TABLE_NAME + " ("
                + COLUMN_QUIZ_ID + " VARCHAR PRIMARY KEY,"
                + COLUMN_NAME + " VARCHAR,"
                + COLUMN_IMAGE + " BLOB,"
                + COLUMN_VISIBLE + " VARCHAR)");
        Log.i("FIRST METHOD", "CREATED QUIZ TABLE");

        db.execSQL("create table if not exists " + KAHOOT_TABLE_NAME + " ("
                + COLUMN_KAHOOT_ID + " VARCHAR PRIMARY KEY,"
                + COLUMN_QUESTION + " VARCHAR,"
                + COLUMN_ANSWER1 + " VARCHAR,"
                + COLUMN_ANSWER2 + " VARCHAR,"
                + COLUMN_ANSWER3 + " VARCHAR,"
                + COLUMN_ANSWER4 + " VARCHAR)");
        Log.i("SECOND METHOD", "CREATE KAHOOT TABLE");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KAHOOT_TABLE_NAME);
    }

    public boolean insertKahoot(Question question){
        AddQuestions.kahootCounter++;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_KAHOOT_ID, question.getId());
        contentValues.put(COLUMN_QUESTION, question.getQuestion());
        contentValues.put(COLUMN_ANSWER1, question.getAnswer1());
        contentValues.put(COLUMN_ANSWER2, question.getAnswer2());
        contentValues.put(COLUMN_ANSWER3, question.getAnswer3());
        contentValues.put(COLUMN_ANSWER4, question.getAnswer4());
        long result = db.insert(KAHOOT_TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }else return true;
    }

    public boolean insertQuiz(Quiz quiz){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUIZ_ID, quiz.getId());
        contentValues.put(COLUMN_NAME, quiz.getName());
        if (quiz.isVisible()){
            contentValues.put(COLUMN_VISIBLE, "everyone");
        }else{
            contentValues.put(COLUMN_VISIBLE, "only me");
        }

        Bitmap bitmap = quiz.getPicture();
        if (bitmap != null){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
            byte[] data = outputStream.toByteArray();
            if (data != null && data.length > 0){
                contentValues.put(COLUMN_IMAGE, data);
            }
        }

        long result = db.insert(QUIZ_TABLE_NAME, null, contentValues);
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

        db.update(KAHOOT_TABLE_NAME, contentValues, COLUMN_KAHOOT_ID + " = ?",
                new String[] {question.getId()});
        return true;
    }

    public Question readQuestion(String id){
        db = this.getReadableDatabase();
        Question question = null;
        Cursor cursor = null;

        try {
            Log.i("TRY", "TRYING");
            cursor = db.query(KAHOOT_TABLE_NAME, QUESTION_COLUMNS, COLUMN_KAHOOT_ID + " = ?", new String[] { id },
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
        return db.delete(KAHOOT_TABLE_NAME, COLUMN_KAHOOT_ID + " = ?", new String[] { id });
    }

    public List<Question> getAllQuestions(){
        db = this.getWritableDatabase();
        List<Question> result = new ArrayList<Question>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + KAHOOT_TABLE_NAME, null);
            while (cursor.moveToNext()){
                Question question = new Question();
                question.setId(cursor.getString(0));
                question.setQuestion(cursor.getString(1));
                question.setAnswer1(cursor.getString(2));
                question.setAnswer2(cursor.getString(3));
                question.setAnswer3(cursor.getString(4));
                question.setAnswer4(cursor.getString(5));
                result.add(question);
            }
            cursor.close();
        }catch (Throwable t){
            t.printStackTrace();
        }

        return result;
    }

    public List<Quiz> getAllQuizzes(){
        db = this.getWritableDatabase();
        List<Quiz> result = new ArrayList<Quiz>();
        Bitmap image;

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + QUIZ_TABLE_NAME, null);
            while (cursor.moveToNext()) {
                Quiz quiz = new Quiz();
                quiz.setId(cursor.getString(0));
                quiz.setName(cursor.getString(1));
                image = BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length);
                quiz.setPicture(image);
                if (cursor.getString(3) == "everyone") {
                    quiz.setVisibility(true);
                } else {
                    quiz.setVisibility(false);
                }
                result.add(quiz);
            }
            cursor.close();
        }catch (Throwable t){
            t.printStackTrace();
        }
        return result;
    }
}