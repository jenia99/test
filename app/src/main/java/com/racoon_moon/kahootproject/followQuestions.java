package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


public class followQuestions extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_questions);

        Button answer1 = findViewById(R.id.answer1);
        Button answer2 = findViewById(R.id.answer2);
        Button answer3 = findViewById(R.id.answer3);
        Button answer4 = findViewById(R.id.answer4);

        answer1.setText("As a student");
        answer2.setText("As a teacher");
        answer3.setText("Socially");
        answer4.setText("For work");
    }

    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), welcomePage.class);
        startActivity(intent);
        finish();
    }
}
