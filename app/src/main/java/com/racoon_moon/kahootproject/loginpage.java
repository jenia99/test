package com.racoon_moon.kahootproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class loginpage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void moveToDiscover(View view)
    {
        Intent intent2 = new Intent(this, Discover.class);
        startActivity(intent2);
        finish();

    }

    public void GoogleBtn(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.google.com/intl/iw/gmail/about/#"));
        startActivity(intent);
    }
    public void outlookBtn(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://outlook.live.com/owa/"));
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.mybutton){
            intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}