package com.racoon_moon.kahootproject.questions.data;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.racoon_moon.kahootproject.R;

public class ResultActivity extends AppCompatActivity
{
    public  void onCreate(Bundle saveInstanceState)
    {
        super.onCreate (saveInstanceState);
        setContentView (R.layout.activity_result);

        RatingBar bar = (RatingBar) findViewById (R.id.ratingBar1);
        bar.setNumStars (5);
        bar.setStepSize(0.5f);
        TextView textView= (TextView) findViewById (R.id.textResult);
        Bundle bundle= getIntent ().getExtras ();
        int score = bundle.getInt ("score");
        bar.setRating (score);

        switch (score)
        {
            case 0:textView.setText ("your score:0, keep learning");
            break;
            case 1:textView.setText ("your score:20%, study better");
                break;
            case 2:textView.setText ("your score:40%, try learn it better");
                break;
            case 3:textView.setText ("your score:60%, good");
                break;
            case 4:textView.setText ("your score:80%, very good");
                break;
            case 5:textView.setText ("your score:100%, you awesome!");
                break;


        }

        Bundle extras = getIntent().getExtras();
        extras.getString("score");


    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
    getMenuInflater ().inflate (R.menu.activity_result, menu);
    return  true;
    }

    public  boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId ();
        if(id == R.id.action_settings)
        {
            Intent settingIntent = new Intent (this, QUIZactivity.class);
            startActivity (settingIntent);
            return  true;

        }
        return  super.onOptionsItemSelected (item);
    }
}
