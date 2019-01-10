package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.racoon_moon.kahootproject.database.KahootsDatabase;
import com.racoon_moon.kahootproject.questions.data.Question;

public class AddQuestions extends AppCompatActivity {

    EditText question, answerA, answerB, answerC, answerD;

    Button answer1, answer2, answer3, answer4;

    Intent intent;

    Question newQuestion = null;

    KahootsDatabase db;

    public static int kahootCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        db = new KahootsDatabase(this);

        question = findViewById(R.id.question);
        answerA = findViewById(R.id.answerA);
        answerB = findViewById(R.id.answerB);
        answerC = findViewById(R.id.answerC);
        answerD = findViewById(R.id.answerD);

        answerA.setVisibility(View.INVISIBLE);
        answerB.setVisibility(View.INVISIBLE);
        answerC.setVisibility(View.INVISIBLE);
        answerD.setVisibility(View.INVISIBLE);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        answer1.setOnClickListener(selectButton);
        answer2.setOnClickListener(selectButton);
        answer3.setOnClickListener(selectButton);
        answer4.setOnClickListener(selectButton);

        question.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                }else{
                    //newQuestion.setQuestion(question.getText().toString());
                }
            }
        });

    }


    public void addQuestion(View view){
        answerA.clearFocus();
        answerB.clearFocus();
        answerC.clearFocus();
        answerD.clearFocus();
        if (question.getText().toString().isEmpty()){
            Toast.makeText(this, "Missing Question", Toast.LENGTH_SHORT).show();
        }else if (answer1.getText().toString().isEmpty() || answer2.getText().toString().isEmpty() ||
                    answer3.getText().toString().isEmpty() || answer4.getText().toString().isEmpty()){
            Toast.makeText(this, "Missing Answers", Toast.LENGTH_SHORT).show();
        }
        newQuestion = new Question(question.getText().toString(), answer1.getText().toString(),
                answer2.getText().toString(), answer3.getText().toString(), answer4.getText().toString());
        db.insertKahoot(newQuestion.getQuestion(), newQuestion.getAnswer1(), newQuestion.getAnswer2(),
                newQuestion.getAnswer3(), newQuestion.getAnswer4());
        question.setText("");
        answer1.setText("");
        answer2.setText("");
        answer3.setText("");
        answer4.setText("");
        Cursor cursor = db.getAll();
        if (cursor.getCount() == 0){
            showMessage("Error", "Nothing to show");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(KahootsDatabase.TABLE_NAME + "\n");
        while (cursor.moveToNext()){
            buffer.append("ID: " + cursor.getString(0) + "\n");
            buffer.append("Question: " + cursor.getString(1) + "\n");
            buffer.append("answer1: " + cursor.getString(2) + "\n");
            buffer.append("answer2: " + cursor.getString(3) + "\n");
            buffer.append("answer3: " + cursor.getString(4) + "\n");
            buffer.append("answer4: " + cursor.getString(5) + "\n\n");
        }
        showMessage("Data", buffer.toString());
//        Question readQuestion = db.readQuestion("1");
//        question.setText(readQuestion.getQuestion());
//        answer1.setText(readQuestion.getAnswer1());
//        answer2.setText(readQuestion.getAnswer2());
//        answer3.setText(readQuestion.getAnswer3());
//        answer4.setText(readQuestion.getAnswer4());
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private View.OnClickListener selectButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button answer = (Button) v;
            if (answer.getTag() == answer1.getTag()){
                answer1.setText("");
                answerA.setVisibility(View.VISIBLE);
                answerA.setEnabled(true);
                answerA.requestFocus();
                answerA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else{
                            answer1.setText(answerA.getText().toString());
                            answerA.setVisibility(View.INVISIBLE);
                            answerA.clearFocus();
                        }
                    }
                });
            }else if (answer.getTag() == answer2.getTag()){
                answer2.setText("");
                answerB.setVisibility(View.VISIBLE);
                answerB.setEnabled(true);
                answerB.requestFocus();
                answerB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else {
                            answer2.setText(answerB.getText().toString());
                            answerB.setVisibility(View.INVISIBLE);
                            answerB.clearFocus();
                        }
                    }
                });
            }else if (answer.getTag() == answer3.getTag()){
                answer3.setText("");
                answerC.setVisibility(View.VISIBLE);
                answerC.setEnabled(true);
                answerC.requestFocus();
                answerC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else {
                            answer3.setText(answerC.getText().toString());
                            answerC.setVisibility(View.INVISIBLE);
                            answerC.clearFocus();
                        }
                    }
                });
            }else if (answer.getTag() == answer4.getTag()){
                answer4.setText("");
                answerD.setVisibility(View.VISIBLE);
                answerD.setEnabled(true);
                answerD.requestFocus();
                answerD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else {
                            answer4.setText(answerD.getText().toString());
                            answerD.setVisibility(View.INVISIBLE);
                            answerD.clearFocus();
                        }
                    }
                });
            }
        }
    };
}
