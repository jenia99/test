package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.racoon_moon.kahootproject.database.KahootsDatabase;

public class KahootsV2 extends AppCompatActivity {

    Intent intent;
    ListView listView;

    KahootsDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahoots_v2);
        db = new KahootsDatabase(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        bottomNavigationView.getMenu().findItem(R.id.kahoots).setChecked(true);

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

                return true;
            }
        });

        listView = findViewById(R.id.kahootsList);
        Log.i("TRY TO GET LIST", "TRIENG");

        if (db.getAllQuizzes() != null) {
            Log.i("TRY TO GET LIST", "SUCCESSFULY RECEIVED");
            KahootsListAdapter adapter = new KahootsListAdapter(this, R.layout.quiz_layout, db.getAllQuizzes());
            listView.setAdapter(adapter);
        }
    }

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

    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), Discover.class);
        startActivity(intent);
        finish();
    }
}
