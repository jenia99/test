package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Kahoots extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahoots);

        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();
    }

    public void moveToCreate(View view)
    {
        Intent intent2 = new Intent(this, create.class);
        startActivity(intent2);
        finish();

    }
    public void moveToDiscover(View view)
    {
        Intent intent2 = new Intent(this, Discover.class);
        startActivity(intent2);
        finish();

    }
    public void moveToEnterpin(View view)
    {
        Intent intent2 = new Intent(this, Enterpin.class);
        startActivity(intent2);
        finish();

    }
    public void moveToKahoots(View view)
    {
        Intent intent2 = new Intent(this, Kahoots.class);
        startActivity(intent2);
        finish();

    }
    public void moveToReports(View view)
    {
        Intent intent2 = new Intent(this, Reports.class);
        startActivity(intent2);
        finish();

    }


    //////////////////////
    public void moveFav(View view)
    {
        Intent intent2 = new Intent(this, kahoot_fav.class);
        startActivity(intent2);
        finish();

    }

    public void moveShareKahoot(View view)
    {
        Intent intent2 = new Intent(this, kahootsShare.class);
        startActivity(intent2);
        finish();

    }

}
