package com.racoon_moon.kahootproject.questions.data;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.racoon_moon.kahootproject.R;
import com.racoon_moon.kahootproject.questions.data.data.DBHelper;

import java.util.List;

public class QUIZactivity extends AppCompatActivity
{
    List<Question> Qlist;
    int score=0;
    int qid=0;
    Question currentQuestion;
    TextView QTextView;
    Button OPA, OPB, OPC, ANSWER;
    Button Qnext;

    @Override
    public  void onCreate(Bundle saveInstanceState)
    {
        super.onCreate (saveInstanceState);
        setContentView (R.layout.questionier);

        DBHelper db = new DBHelper (this);
        Qlist = db.getAllQuestions ();
        currentQuestion = Qlist.get (qid);
        QTextView = (TextView) findViewById (R.id.textQ);
        OPA = (Button) findViewById (R.id.OPA);
        OPB = (Button) findViewById (R.id.OPB);
        OPC = (Button) findViewById (R.id.OPC);
        Qnext = (Button) findViewById (R.id.btnNext);
        setQuestionView ();
    }
        public void onclick(View v)
        {
            Button ansUser= (Button) findViewById (R.id.correct);
            Button realAns=null;
//           realAns = (Button) findViewById(ansUser.getCheckedButtonId());
//            ansUser.clearCheck();
            Log.d("your_ans", currentQuestion.getASWER ()+ realAns.getText () );
            if(currentQuestion.getASWER ().equals (realAns.getText ()))
            {
                score++;
                Log.d("score", "your scare"+ score);
            }
            if(qid<5)
            {
                currentQuestion = Qlist.get (qid);
                setQuestionView ();
            }
//            else
//            {
//                Intent intent = new Intent (this, ResultActivity.class);
//                Bundle bundle= new Bundle ();
//                bundle.putInt ("score", score);
//                Intent intent1 = intent.putExtra ( bundle );
//                startActivity (intent);
//                finish ();
//            }

        }

    public  boolean onCreateOptionMenu(Menu menu)
    {
        getMenuInflater ().inflate (R.menu.questionier, menu);
        return  true;
    }

    private  void setQuestionView()
    {
        QTextView.setText (currentQuestion.getQUESTION ());
        OPA.setText (currentQuestion.getOPA ());
        OPB.setText (currentQuestion.getOPB ());
        OPC.setText (currentQuestion.getOPC ());
        qid++;
    }
}
