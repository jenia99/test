package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.racoon_moon.kahootproject.kahootsPage.Kahoots;

public class Discover extends AppCompatActivity {

    Intent intent;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.getMenu().findItem(R.id.discover).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.reports:
                        bottomNavigationView.getMenu().findItem(R.id.discover).setChecked(false);
                        intent = new Intent(getApplicationContext(), Reports.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.kahoots:
                        bottomNavigationView.getMenu().findItem(R.id.discover).setChecked(false);
                        intent = new Intent(getApplicationContext(), KahootsV2.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.enter_pin:
                        bottomNavigationView.getMenu().findItem(R.id.discover).setChecked(false);
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
                        break;
                }

                return true;
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
        Intent intent2 = new Intent(this, KahootsV2.class);
        startActivity(intent2);
        finish();

    }
    public void moveToReports(View view)
    {
        Intent intent2 = new Intent(this, Reports.class);
        startActivity(intent2);
        finish();

    }

    public void moveToSearch(View view)
    {
        Intent intent2 = new Intent(this, search.class);
        startActivity(intent2);
        finish();

    }
    public void moveToProfile(View view)
    {
        Intent intent2 = new Intent(this, Profile.class);
        startActivity(intent2);
        finish();

    }

    @Override
    public void onBackPressed(){
        finish();
        System.exit(0);
    }
}
