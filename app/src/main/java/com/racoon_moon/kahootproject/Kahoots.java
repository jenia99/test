package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Kahoots extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahoots);

        ActionBar actionBar= getSupportActionBar();
        actionBar.hide();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

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
                        break;

                    case R.id.enter_pin:
                        intent = new Intent(getApplicationContext(), Enterpin.class);
                        startActivity(intent);
                        finish();
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

                return false;
            }
        });
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
