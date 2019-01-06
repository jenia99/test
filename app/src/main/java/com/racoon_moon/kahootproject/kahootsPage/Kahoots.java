package com.racoon_moon.kahootproject.kahootsPage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.racoon_moon.kahootproject.Discover;
import com.racoon_moon.kahootproject.Enterpin;
import com.racoon_moon.kahootproject.R;
import com.racoon_moon.kahootproject.Reports;
import com.racoon_moon.kahootproject.create;

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
