package com.racoon_moon.kahootproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.racoon_moon.kahootproject.questions.data.Quiz;

import java.util.List;

public class KahootsListAdapter extends ArrayAdapter<Quiz> {

    private Context context;
    private List<Quiz> quizzes;

    public KahootsListAdapter(Context context, int resource, List<Quiz> quizzes) {
        super(context, resource, quizzes);
        this.context = context;
        this.quizzes = quizzes;
    }

    public View getView(int position, View currentView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.quiz_layout, null);
        Quiz currentQuiz = quizzes.get(position);

        TextView name = view.findViewById(R.id.quizNameTextView);
        name.setText(currentQuiz.getName());

        TextView visibility = view.findViewById(R.id.visibilityTextView);
        boolean visible = currentQuiz.isVisible();
        if (visible){
            visibility.setText("Visible to everyone");
        }else{
            visibility.setText("Private");
        }

        ImageView quizImage = view.findViewById(R.id.imageView);
        if (currentQuiz.getPicture() != null){
            quizImage.setImageBitmap(currentQuiz.getPicture());
        }else {
            quizImage.setImageDrawable(context.getResources().getDrawable(R.drawable.draft));
        }

        return view;
    }
}
