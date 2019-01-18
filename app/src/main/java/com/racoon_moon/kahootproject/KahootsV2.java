package com.racoon_moon.kahootproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.racoon_moon.kahootproject.database.KahootsDatabase;
import com.racoon_moon.kahootproject.questions.data.Question;
import com.racoon_moon.kahootproject.questions.data.Quiz;

import java.util.List;

public class KahootsV2 extends AppCompatActivity {

    Intent intent;
    ListView listView;

    KahootsDatabase db;

    KahootsListAdapter adapter;

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

        retrieveList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getApplicationContext(), AddQuestions.class);
                Log.i("PASSING QUIZ ID", "ID = " + position);
                intent.putExtra("QUIZ_ID", String.valueOf(position));
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteQuiz(String.valueOf(position));
                adapter.notifyDataSetChanged();
                StringBuffer buffer = new StringBuffer();
                List<Quiz> questions = db.getAllQuizzes();
                buffer.append(KahootsDatabase.KAHOOT_TABLE_NAME + "\n");
                for (int i = 0; i < questions.size(); i++) {
                    buffer.append(questions.get(i).getId() + "\n");
                    buffer.append(questions.get(i).getName() + "\n\n");
                }
                showMessage("Quizzes", buffer.toString());
                retrieveList();
                return true;
            }
        });

    }

    public void showMessage(String title,String message){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();
        }

    public void moveFav(View view) {
        Intent intent2 = new Intent(this, kahoot_fav.class);
        startActivity(intent2);
        finish();
    }

    public void moveShareKahoot(View view) {
        Intent intent2 = new Intent(this, kahootsShare.class);
        startActivity(intent2);
        finish();
    }

    private void retrieveList(){
        if (db.getAllQuizzes() != null) {
            Log.i("TRY TO GET LIST", "SUCCESSFULLY RECEIVED");
            adapter = new KahootsListAdapter(this, R.layout.quiz_layout, db.getAllQuizzes());
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), Discover.class);
        startActivity(intent);
        finish();
    }
}
