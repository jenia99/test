package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.racoon_moon.kahootproject.kahootsPage.Kahoots;

import java.util.Random;

public class Enterpin extends AppCompatActivity
{

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpin);
        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.getMenu().findItem(R.id.enter_pin).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.reports:
                        intent = new Intent(getApplicationContext(), Reports.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.kahoots:
                        intent = new Intent(getApplicationContext(), KahootsV2.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.enter_pin:
                        break;

                    case R.id.create:
                        intent = new Intent(getApplicationContext(), create.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.discover:
                        intent = new Intent(getApplicationContext(), Discover.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return true;
            }
        });
    }
    public void randonColor(View view)
    {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        view.setBackgroundColor(color);

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

    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), Discover.class);
        startActivity(intent);
        finish();
    }
}
