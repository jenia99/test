package com.racoon_moon.kahootproject.questions.data.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.racoon_moon.kahootproject.questions.data.Question;

import java.util.ArrayList;
import java.util.List;

import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.KEY_ANS;
import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.KEY_ID;
import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.KEY_QUES;
import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.OPA;
import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.OPB;
import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.OPC;
import static com.racoon_moon.kahootproject.questions.data.data.quiz.quizeQuestion.TABLE_QUES;

public class DBHelper extends SQLiteOpenHelper
{
    private static  final int  DATABASE_VERSION = 1;
    private static  final String DATABASE_NAME = "triviaQuiz";

    private SQLiteOpenHelper database;


    public DBHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql= String.format ( "create table if not exists %s(%s Integer primary key autoincrement %s TEXT %s TEXT %s TEXT %s TEXT %s TEXT )", TABLE_QUES, KEY_ID, KEY_QUES, KEY_ANS, OPA, OPB, OPC );
        db.execSQL (sql);
        //addQuestions();

    }


//    private void addQuestions()
//    {
//        Question q1 = new Question("why...","because","or","or","or");
//        addQuestions (q1);
//        Question q2= new Question("why...","becose","or","or","or");
//        addQuestions (q2);
//        Question q3= new Question("why...","becose","or","or","or");
//        addQuestions (q3);
//        Question q4= new Question("why...","becose","or","or","or");
//        addQuestions (q4);
//        Question q5= new Question("why...","becose","or","or","or");
//        addQuestions (q5);
//    }

    public void onUpgrade(SQLiteDatabase db, int oldV, int newV)
    {
        db.execSQL ("DROP TABLE IF EXISTS "+TABLE_QUES);
        onCreate (db);
    }

//    public  void addQuestions(Question ques)
//    {
//        SQLiteDatabase database = null;
//        ContentValues contentValues = new ContentValues ();
//        contentValues.put (KEY_QUES,ques.getQUESTION ());
//        contentValues.put (KEY_ANS,ques.getASWER ());
//        contentValues.put (OPA,ques.getOPA ());
//        contentValues.put (OPB,ques.getOPB ());
//        contentValues.put (OPC,ques.getOPC ());
//        database.insert(TABLE_QUES,null,contentValues);
//    }

//    public List<Question> getAllQuestions()
//    {
//        SQLiteDatabase database;
//        List<Question> Qlist= new ArrayList<> ();
//        String selectQuery = "SELECT * FROM "+ TABLE_QUES;
//        database = this.getReadableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        if(cursor.moveToFirst())
//        {
//            do {
//
//                Question ques= new Question ();
//                ques.setID (cursor.getInt (0));
//                ques.setQUESTION (cursor.getString(1));
//                ques.setASWER (cursor.getString(2));
//                ques.setOPA (cursor.getString(3));
//                ques.setOPB (cursor.getString(4));
//                ques.setOPC (cursor.getString(5));
//                Qlist.add (ques);
//
//            }
//            while(cursor.moveToNext ());
//        }
//        return  Qlist;
//
//    }
     public int rowCount() {
         int row = 0;
         String selectQuery = "SELECT * FROM " + TABLE_QUES;
         SQLiteDatabase database = this.getWritableDatabase ();
         Cursor cursor = database.rawQuery(selectQuery, null);
         row= cursor.getCount ();
         return  row;

     }
}
