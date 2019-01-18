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
    public static final String COLUMN_IN_QUIZ = "quiz_id";
    public static final String COLUMN_ANSWER1TRUE = "answer1_true";
    public static final String COLUMN_ANSWER2TRUE = "answer2_true";
    public static final String COLUMN_ANSWER3TRUE = "answer3_true";
    public static final String COLUMN_ANSWER4TRUE = "answer4_true";

    public static final String QUIZ_TABLE_NAME = "quizzes";
    public static final String COLUMN_QUIZ_ID = "ID";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_VISIBLE = "Visible";

    public static final String[] QUESTION_COLUMNS = {COLUMN_KAHOOT_ID, COLUMN_QUESTION, COLUMN_ANSWER1,
            COLUMN_ANSWER2, COLUMN_ANSWER3, COLUMN_ANSWER4, COLUMN_IN_QUIZ, COLUMN_ANSWER1TRUE, COLUMN_ANSWER2TRUE,
            COLUMN_ANSWER3TRUE, COLUMN_ANSWER4TRUE};

    SQLiteDatabase db = null;

    public KahootsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + QUIZ_TABLE_NAME + " ("
                + COLUMN_QUIZ_ID + " VARCHAR PRIMARY KEY,"
                + COLUMN_NAME + " VARCHAR,"
                + COLUMN_IMAGE + " BLOB,"
                + COLUMN_VISIBLE + " VARCHAR)");

        db.execSQL("create table if not exists " + KAHOOT_TABLE_NAME + " ("
                + COLUMN_KAHOOT_ID + " VARCHAR,"
                + COLUMN_QUESTION + " VARCHAR,"
                + COLUMN_ANSWER1 + " VARCHAR,"
                + COLUMN_ANSWER2 + " VARCHAR,"
                + COLUMN_ANSWER3 + " VARCHAR,"
                + COLUMN_ANSWER4 + " VARCHAR,"
                + COLUMN_IN_QUIZ + " VARCHAR,"
                + COLUMN_ANSWER1TRUE + " VARCHAR,"
                + COLUMN_ANSWER2TRUE + " VARCHAR,"
                + COLUMN_ANSWER3TRUE + " VARCHAR,"
                + COLUMN_ANSWER4TRUE + " VARCHAR)");
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
        contentValues.put(COLUMN_IN_QUIZ, question.getQuizId());
        if (question.getAnswer1True()){
            contentValues.put(COLUMN_ANSWER1TRUE, "true");
        }else {
            contentValues.put(COLUMN_ANSWER1TRUE, "false");
        }if (question.getAnswer2True()){
            contentValues.put(COLUMN_ANSWER2TRUE, "true");
        }else {
            contentValues.put(COLUMN_ANSWER2TRUE, "false");
        }if (question.getAnswer3True()){
            contentValues.put(COLUMN_ANSWER3TRUE, "true");
        }else {
            contentValues.put(COLUMN_ANSWER3TRUE, "false");
        }if (question.getAnswer4True()){
            contentValues.put(COLUMN_ANSWER4TRUE, "true");
        }else {
            contentValues.put(COLUMN_ANSWER4TRUE, "false");
        }
        long result = db.insert(KAHOOT_TABLE_NAME, null, contentValues);
        if (result == -1){
            Log.i("INSERT KAHOOT", "FAILED");
            return false;
        }else {
            Log.i("INSERT KAHOOT", "INSERTED " + question.getQuizId());
            return true;
        }
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
        }else {
            contentValues.put(COLUMN_IMAGE, "null");
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
            cursor = db.query(KAHOOT_TABLE_NAME, QUESTION_COLUMNS, COLUMN_KAHOOT_ID + " = ?", new String[] { id },
                    null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                question = new Question();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                question.setAnswer1(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER1)));
                question.setAnswer2(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER2)));
                question.setAnswer3(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER3)));
                question.setAnswer4(cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER4)));
                question.setQuizId(cursor.getString(cursor.getColumnIndex(COLUMN_IN_QUIZ)));
                if (cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER1TRUE)).equals("true")){
                    question.setAnswer1True(true);
                }else question.setAnswer1True(false);
                if (cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER2TRUE)).equals("true")){
                    question.setAnswer2True(true);
                }else question.setAnswer2True(false);
                if (cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER3TRUE)).equals("true")){
                    question.setAnswer3True(true);
                }else question.setAnswer3True(false);
                if (cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER4TRUE)).equals("true")){
                    question.setAnswer4True(true);
                }else question.setAnswer4True(false);
            }
        }catch (Throwable t){
            t.printStackTrace();
        }

        if (cursor != null){
            cursor.close();
        }
        return question;
    }

    public boolean deleteQuestion(Question question){
        db = this.getWritableDatabase();
        int delete = db.delete(KAHOOT_TABLE_NAME, COLUMN_KAHOOT_ID + " = ?", new String[] { question.getId() });
        if (delete != 0){
            return true;
        }else {
            return false;

        }
    }

    public boolean deleteQuiz(String id){
        db = this.getWritableDatabase();
        int deletedQuiz = db.delete(QUIZ_TABLE_NAME, COLUMN_QUIZ_ID + " = ?", new String[] { id });
        int deletedQuestions = db.delete(KAHOOT_TABLE_NAME, COLUMN_IN_QUIZ + " = ?", new String[] { id });
        Log.i("DELETED QUIZZES", " = " + deletedQuiz);
        Log.i("DELETED QUESTIONS", " = " + deletedQuestions);
        if (deletedQuiz != 0){
            Log.i("DELETED ROW/S", "DELETED = " + deletedQuiz);
            List<Quiz> quizzes = getAllQuizzes();
            Log.i("UPDATE LIST", "LIST CREATED");
            Log.i("FOR LOOP", "VALUES " + id + ", " + (quizzes.size()-Integer.parseInt(id)) + 1);
            for (int i = Integer.parseInt(id); i < (quizzes.size() - Integer.parseInt(id)) + 1; i++){
                Log.i("PASSING VALUES", "PASSED " + quizzes.get(i).toString() + ", " + String.valueOf(i));
                rearrangeUponDelete(quizzes.get(i).getId(), String.valueOf(i));
            }
            return true;
        }else {
            Log.i("FALSE ON DELETE", "NOTHING TO DELETE");
            return false;
        }

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
                question.setQuizId(cursor.getString(6));
                if (cursor.getString(7).equals("true")){
                    question.setAnswer1True(true);
                }else question.setAnswer1True(false);
                if (cursor.getString(8).equals("true")){
                    question.setAnswer2True(true);
                }else question.setAnswer2True(false);
                if (cursor.getString(9).equals("true")){
                    question.setAnswer3True(true);
                }else question.setAnswer3True(false);
                if (cursor.getString(10).equals("true")){
                    question.setAnswer4True(true);
                }else question.setAnswer4True(false);
                result.add(question);
            }
            cursor.close();
        }catch (Throwable t){
            t.printStackTrace();
        }

        return result;
    }

    public void rearrangeUponDelete(String id, String newId){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(COLUMN_IN_QUIZ, newId);
            Log.i("UPDATE TABLE QUESTIONS", "VALUES " + contentValues.getAsString(COLUMN_IN_QUIZ));
            db.update(KAHOOT_TABLE_NAME, contentValues, COLUMN_IN_QUIZ + " = ?", new String[] { id });
            contentValues.clear();
            contentValues.put(COLUMN_QUIZ_ID, newId);
            Log.i("UPDATE TABLE QUIZ", "VALUES " + contentValues.getAsString(COLUMN_QUIZ_ID));
            db.update(QUIZ_TABLE_NAME, contentValues, COLUMN_QUIZ_ID + " = ?", new String[] { id });
        }catch (Throwable t){
            Log.i("CATCH", "NO MATCHING QUESTIONS");
            t.printStackTrace();
        }

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
                try {
                    if (cursor.getString(2).equals("null")){
                        quiz.setPicture(null);
                    }
                }catch (Throwable t){
                    t.printStackTrace();
                }
                try{
                    image = BitmapFactory.decodeByteArray(cursor.getBlob(2), 0, cursor.getBlob(2).length);
                    quiz.setPicture(image);
                }catch (Throwable t){
                    t.printStackTrace();
                }
                if (cursor.getString(3).equals("everyone")) {
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

    public int getNextQuestion(String quiz_id){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            Log.i("QUIZ ID PASSED", "ID = " + quiz_id);
            cursor = db.query(KAHOOT_TABLE_NAME, QUESTION_COLUMNS, COLUMN_IN_QUIZ + " = ?",
                    new String[]{ quiz_id }, null, null, null);
            Log.i("CURSOR", "CURSOR IS CURRECT " + cursor.getCount());
            if (cursor != null && cursor.getCount() > 0) {
                Log.i("CURSOR STATE", "COUNT " + cursor.getCount());
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    Log.i("VALUES", "= " + cursor.getString(0));
                    Log.i("VALUES", "= " + cursor.getString(1));
                    Log.i("VALUES", "= " + cursor.getString(2));
                    Log.i("VALUES", "= " + cursor.getString(3));
                    Log.i("VALUES", "= " + cursor.getString(4));
                    Log.i("VALUES", "= " + cursor.getString(5));
                    Log.i("VALUES", "= " + cursor.getString(6));
                }
            }
        }catch (Throwable t){
            Log.i("CATCH GET NEXT QUESTION", "NO QUESTION FOUND");
            t.printStackTrace();
        }
        if (cursor != null){cursor.close();}
        if (cursor.getCount() == 0){
            return 1;
        }else {
            return cursor.getCount() + 1;
        }
    }
}