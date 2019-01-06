package com.racoon_moon.kahootproject.questions.data.data;

import android.provider.BaseColumns;

public class quize
{
    public static class quizeQuestion implements BaseColumns
    {
        public static  final  String TABLE_QUES="ques";

        //task table column name
        public static  final  String KEY_ID ="id";
        public static  final  String KEY_QUES ="question";
        public static  final  String KEY_ANS ="answer";
        public static  final  String OPA ="option_a";
        public static  final  String OPB ="option_b";
        public static  final  String OPC ="option_c";


    }
}
