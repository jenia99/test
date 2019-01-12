package com.racoon_moon.kahootproject;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.racoon_moon.kahootproject.database.KahootsDatabase;
import com.racoon_moon.kahootproject.questions.data.Quiz;

import static com.racoon_moon.kahootproject.R.id.getPhoto;
import static com.racoon_moon.kahootproject.R.id.imageView5;

public class create extends AppCompatActivity {

    Intent intent;
    KahootsDatabase db;

    TextView textView;
    ImageView imageView;
    Bitmap bitmap;
    Button btn;
    Quiz quiz;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Log.i("TRY TO CREATE DATABASE", "TRYING");
        db = new KahootsDatabase(this);
        Log.i("TRY YO CREATE DATABASE", "SUCCESS");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        spinner =(Spinner)  findViewById(R.id.vis);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

//        spinner = findViewById(R.id.vis);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

// get photo cover
        imageView = (ImageView) findViewById(imageView5);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeApictures = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takeApictures, 0);
            }
        });

        btn = (Button) findViewById(getPhoto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePatientPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePatientPhoto, 0);

            }

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap picture = (Bitmap) data.getExtras().get("data");
        bitmap = picture;
        imageView.setImageBitmap(bitmap);

        if(bitmap!=null){

            btn.setVisibility (Button.INVISIBLE);

        }
    }


    public void moveToP1(View view)
    {
        intent = new Intent(this, Discover.class);
        startActivity(intent);
        finish();

    }

    public void addQuestions(View view) {
        imageView = findViewById(R.id.imageView5);
        textView = findViewById(R.id.enterTitle);
        spinner.findViewById(R.id.vis);
        boolean isVisible;
        String visibility = spinner.getSelectedItem().toString();
        if (visibility.equals("everyone")){
            isVisible = true;
        }else{
            isVisible = false;
        }
        Bitmap hasPicture = getBitmap();
        if (getBitmap() == null){
//            Bitmap bitmap = Bitmap.createBitmap(50,50, Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas();
//            Paint paint = new Paint();
//            paint.setColor(0xFFFFFFFF);
//            canvas.drawRect(0F, 0F, 50, 50, paint);
//            hasPicture = bitmap;
//            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.insert_photo);
            Log.i("BITMAP", "BITMAP = " + hasPicture);
        }
        quiz = new Quiz(textView.getText().toString(), hasPicture, isVisible);
        db.insertQuiz(quiz);
//        intent = new Intent(getApplicationContext(), AddQuestions.class);
//        startActivity(intent);
//        finish();

    }

    @Override
    public void onBackPressed(){
        intent = new Intent(getApplicationContext(), Discover.class);
        startActivity(intent);
        finish();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

}
