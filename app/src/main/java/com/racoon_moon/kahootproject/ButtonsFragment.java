package com.racoon_moon.kahootproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ButtonsFragment extends Fragment {

    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;

    private View.OnClickListener selectAnswer =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button answer = (Button) v;
                    Log.i("Tag", "Button " + answer.getTag());
                }
            };

    public void selectAnswer1(View view){
        Button answer = (Button) view;
        answer.getTag();
        Log.i("Tag", "Button " + answer.getTag());
    }


    public ButtonsFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.buttons_fragment, container, false);

        answer1 = view.findViewById(R.id.answer3);
        answer2 = view.findViewById(R.id.answer2);
        answer3 = view.findViewById(R.id.answer3);
        answer4 = view.findViewById(R.id.answer4);

        answer1.setOnClickListener(selectAnswer);
        answer2.setOnClickListener(selectAnswer);
        answer3.setOnClickListener(selectAnswer);
        answer4.setOnClickListener(selectAnswer);

        return view;
    }
}
