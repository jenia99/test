package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class welcomePage extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //log in with under line
        TextView textView =  findViewById(R.id.here);
        SpannableString content = new SpannableString("Log in here");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

    public void moveToP2(View view) {
        intent = new Intent(getApplicationContext(), loginpage.class);
        startActivity(intent);
        finish();
    }
    public void buttons(View view) {
        intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        finish();
        System.exit(0);
    }
}
