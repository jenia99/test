package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.racoon_moon.kahootproject.database.KahootsDatabase;
import com.racoon_moon.kahootproject.questions.data.Question;

import java.util.List;

public class AddQuestions extends AppCompatActivity {

    TextView id;

    EditText question, answerA, answerB, answerC, answerD;

    Button answer1, answer2, answer3, answer4;

    ImageButton correctAnswer1, correctAnswer2, correctAnswer3, correctAnswer4;

    boolean answer1Correct = false;
    boolean answer2Correct = false;
    boolean answer3Correct = false;
    boolean answer4Correct = false;

    Intent intent;

    Question newQuestion = null;

    KahootsDatabase db;

    String quiz_id;

    public static int kahootCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);
        db = new KahootsDatabase(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            quiz_id = bundle.getString("QUIZ_ID");
        }

        id = findViewById(R.id.id);
        id.setText(String.valueOf(db.getNextQuestion(quiz_id)));
        Log.i("CURRENT QUIZ ID", "ID = " + quiz_id);

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

        correctAnswer1 = findViewById(R.id.correctAnswer1);
        correctAnswer2 = findViewById(R.id.correctAnswer2);
        correctAnswer3 = findViewById(R.id.correctAnswer3);
        correctAnswer4 = findViewById(R.id.correctAnswer4);
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
        newQuestion = new Question(id.getText().toString(), question.getText().toString(), answer1.getText().toString(),
                answer2.getText().toString(), answer3.getText().toString(), answer4.getText().toString(), quiz_id,
                answer1Correct, answer2Correct, answer3Correct, answer4Correct);
        db.insertKahoot(newQuestion);
        question.setText("");
        answer1.setText("");
        answer2.setText("");
        answer3.setText("");
        answer4.setText("");
        answerA.setText("");
        answerB.setText("");
        answerC.setText("");
        answerD.setText("");
        List<Question> questions = db.getAllQuestions();
        if (questions.size() == 0){
        showMessage("Error", "Nothing to show");
        return;
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(KahootsDatabase.KAHOOT_TABLE_NAME + "\n");
        for (int i = 0; i < questions.size(); i++){
        buffer.append(questions.get(i).getId() + "\n");
        buffer.append(questions.get(i).getQuestion() + "\n");
        buffer.append(questions.get(i).getAnswer1() + "\n");
        buffer.append(questions.get(i).getAnswer2() + "\n");
        buffer.append(questions.get(i).getAnswer3() + "\n");
        buffer.append(questions.get(i).getAnswer4() + "\n");
        buffer.append(questions.get(i).getQuizId() + "\n\n");
        }
        showMessage("Kahoots", buffer.toString());
        id.setText(String.valueOf(Integer.parseInt(id.getText().toString()) + 1));

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
                correctAnswer1.setVisibility(View.VISIBLE);
                correctAnswer1.animate().translationXBy(200).setDuration(400);
                answer1.animate().translationYBy(-400).setDuration(500);
                answerA.animate().translationYBy(-400).setDuration(500);
                answer1.setClickable(false);
                answer1.setTextSize(32);
                answer1.setText("");
                if (answer1Correct){
                    correctAnswer1.setImageResource(R.drawable.correct_answer);
                }else correctAnswer1.setImageResource(R.drawable.wrong_answer);

                answerA.setVisibility(View.VISIBLE);
                answerA.setEnabled(true);
                answerA.requestFocus();
                correctAnswer1.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                setBackground(1);
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
                answerA.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else{
                            answer1.setText(answerA.getText().toString());
                            correctAnswer1.animate().translationXBy(-200).setDuration(400);
                            correctAnswer1.setVisibility(View.INVISIBLE);
                            answerA.setVisibility(View.INVISIBLE);
                            answer1.animate().translationYBy(400).setDuration(500);
                            answerA.animate().translationYBy(400).setDuration(500);
                            answer1.setClickable(true);
                            answerA.clearFocus();
                            Log.i("ANSWER 1 STATUS", "ANSWER 1 CORRECT = " + answer1Correct);
                        }
                    }
                });
            }else if (answer.getTag() == answer2.getTag()){
                correctAnswer2.setVisibility(View.VISIBLE);
                correctAnswer2.animate().translationXBy(-200).setDuration(400);
                answer2.animate().translationYBy(-400).setDuration(500);
                answerB.animate().translationYBy(-400).setDuration(500);
                answer2.setClickable(false);
                answer2.setTextSize(32);
                answer2.setText("");
                if (answer2Correct){
                    correctAnswer2.setImageResource(R.drawable.correct_answer);
                }else correctAnswer2.setImageResource(R.drawable.wrong_answer);
                answerB.setVisibility(View.VISIBLE);
                answerB.setEnabled(true);
                answerB.requestFocus();
                correctAnswer2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                setBackground(2);
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
                answerB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else {
                            answer2.setText(answerB.getText().toString());
                            correctAnswer2.animate().translationXBy(200).setDuration(400);
                            correctAnswer2.setVisibility(View.INVISIBLE);
                            answerB.setVisibility(View.INVISIBLE);
                            answer2.animate().translationYBy(400).setDuration(500);
                            answerB.animate().translationYBy(400).setDuration(500);
                            answer2.setClickable(true);
                            answerB.clearFocus();
                            Log.i("ANSWER 2 STATUS", "ANSWER 2 CORRECT = " + answer2Correct);
                        }
                    }
                });
            }else if (answer.getTag() == answer3.getTag()){
                correctAnswer3.setVisibility(View.VISIBLE);
                correctAnswer3.animate().translationXBy(200).setDuration(400);
                answer3.animate().translationYBy(-685).setDuration(500);
                answerC.animate().translationYBy(-685).setDuration(500);
                answer3.setClickable(false);
                answer3.setTextSize(32);
                answer3.setText("");
                if (answer3Correct){
                    correctAnswer3.setImageResource(R.drawable.correct_answer);
                }else correctAnswer3.setImageResource(R.drawable.wrong_answer);
                answerC.setVisibility(View.VISIBLE);
                answerC.setEnabled(true);
                answerC.requestFocus();
                correctAnswer3.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                setBackground(3);
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
                answerC.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else {
                            answer3.setText(answerC.getText().toString());
                            correctAnswer3.animate().translationXBy(-200).setDuration(400);
                            correctAnswer3.setVisibility(View.INVISIBLE);
                            answerC.setVisibility(View.INVISIBLE);
                            answer3.animate().translationYBy(685).setDuration(500);
                            answerC.animate().translationYBy(685).setDuration(500);
                            answer3.setClickable(true);
                            answerC.clearFocus();
                            Log.i("ANSWER 3 STATUS", "ANSWER 3 CORRECT = " + answer3Correct);
                        }
                    }
                });
            }else if (answer.getTag() == answer4.getTag()){
                correctAnswer4.setVisibility(View.VISIBLE);
                correctAnswer4.animate().translationXBy(-200).setDuration(400);
                answer4.animate().translationYBy(-685).setDuration(500);
                answerD.animate().translationYBy(-685).setDuration(500);
                answer4.setClickable(false);
                answer3.setTextSize(32);
                answer4.setText("");
                if (answer4Correct){
                    correctAnswer4.setImageResource(R.drawable.correct_answer);
                }else correctAnswer4.setImageResource(R.drawable.wrong_answer);
                answerD.setVisibility(View.VISIBLE);
                answerD.setEnabled(true);
                answerD.requestFocus();
                correctAnswer4.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                setBackground(4);
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });
                answerD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus){

                        }else {
                            answer4.setText(answerD.getText().toString());
                            correctAnswer4.animate().translationXBy(200).setDuration(400);
                            correctAnswer4.setVisibility(View.INVISIBLE);
                            answerD.setVisibility(View.INVISIBLE);
                            answer4.animate().translationYBy(685).setDuration(500);
                            answerD.animate().translationYBy(685).setDuration(500);
                            answer4.setClickable(true);
                            answerD.clearFocus();
                            Log.i("ANSWER 4 STATUS", "ANSWER 4 CORRECT = " + answer4Correct);
                        }
                    }
                });
            }
        }
    };

    private void setBackground(int current){
        Log.i("DISPLAY RECEIVED VALUE", "VALUE = " + current);
        if (current == 1){
            if (answer1Correct){
                correctAnswer1.setImageResource(R.drawable.wrong_answer);
                answer1Correct = false;
            }
            else {
                correctAnswer1.setImageResource(R.drawable.correct_answer);
                answer1Correct = true;
            }
        }else if (current == 2){
            if (answer2Correct){
                correctAnswer2.setImageResource(R.drawable.wrong_answer);
                answer2Correct = false;
            }
            else {
                correctAnswer2.setImageResource(R.drawable.correct_answer);
                answer2Correct = true;
            }
        }else if (current == 3){
            if (answer3Correct){
                correctAnswer3.setImageResource(R.drawable.wrong_answer);
                answer3Correct = false;
            }
            else {
                correctAnswer3.setImageResource(R.drawable.correct_answer);
                answer3Correct = true;
            }
        }else if (current == 4){
            if (answer4Correct){
                correctAnswer4.setImageResource(R.drawable.wrong_answer);
                answer4Correct = false;
            }
            else {
                correctAnswer4.setImageResource(R.drawable.correct_answer);
                answer4Correct = true;
            }
        }

    }

    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), Discover.class);
        startActivity(intent);
        finish();
    }

//    Integer deleteRow = db.deleteQuestion("1");
//    deleteRow = db.deleteQuestion("2");
//    deleteRow = db.deleteQuestion("3");
//    deleteRow = db.deleteQuestion("4");
//    deleteRow = db.deleteQuestion("6");
//    //Toast.makeText(this, "Row Deleted: " + deleteRow, Toast.LENGTH_SHORT).show();
//    Cursor cursor = db.getAll();
//        if (cursor.getCount() == 0){
//        //showMessage("Error", "Nothing to show");
//        //return;
//    }
//    StringBuffer buffer = new StringBuffer();
//        buffer.append(KahootsDatabase.TABLE_NAME + "\n");
//        while (cursor.moveToNext()){
//        buffer.append("ID: " + cursor.getString(0) + "\n");
//        buffer.append("Question: " + cursor.getString(1) + "\n");
//        buffer.append("answer1: " + cursor.getString(2) + "\n");
//        buffer.append("answer2: " + cursor.getString(3) + "\n");
//        buffer.append("answer3: " + cursor.getString(4) + "\n");
//        buffer.append("answer4: " + cursor.getString(5) + "\n\n");
//    }
//    //showMessage("Kahoots", buffer.toString());
//    newQuestion = db.readQuestion("2");
//        if (newQuestion != null) {
//        Toast.makeText(this, newQuestion.getAnswer1(), Toast.LENGTH_SHORT).show();
//        question.setText(newQuestion.getQuestion());
//        answer1.setText(newQuestion.getAnswer1());
//        answer2.setText(newQuestion.getAnswer2());
//        answer3.setText(newQuestion.getAnswer3());
//        answer4.setText(newQuestion.getAnswer4());
//    }else{
//        Toast.makeText(this, "Question does not exists", Toast.LENGTH_SHORT).show();
//    }
}
