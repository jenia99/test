package com.racoon_moon.kahootproject;


import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import static com.racoon_moon.kahootproject.R.id.getPhoto;
import static com.racoon_moon.kahootproject.R.id.imageView5;

public class create extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Spinner spinner =(Spinner)  findViewById(R.id.vis);
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
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        bitmap = bitmap;
        imageView.setImageBitmap(bitmap);

        if(bitmap!=null){

            btn.setVisibility (Button.INVISIBLE);

        }
    }







    public void moveToP1(View view)
    {
        Intent intent3 = new Intent(this, Discover.class);
        startActivity(intent3);
        finish();

    }

    public void addQuestions(View view)
    {
        Intent intent3 = new Intent(getApplicationContext(), AddQuestions.class);
        startActivity(intent3);
        finish();

    }



}
