package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void moveToDiscover(View view)
    {
        Intent intent2 = new Intent(this, Discover.class);
        startActivity(intent2);
        finish();

    }
}
