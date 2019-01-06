package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    public void buttons(View view) {
        Intent intent = new Intent(this, welcomePage.class);
        startActivity(intent);
        finish();
    }


}
