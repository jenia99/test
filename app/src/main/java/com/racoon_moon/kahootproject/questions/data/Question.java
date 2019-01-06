package com.racoon_moon.kahootproject.questions.data;

public class Question
{

    private int ID;
    private String QUESTION;
    private String ASWER;
    private String OPA;
    private String OPB;
    private String OPC;

    public Question()
    {
        ID =0;
        QUESTION="";
        ASWER="";
        OPA="";
        OPB="";
        OPC="";
    }

    public Question(String QUESTION, String ASWER, String OPA, String OPB, String OPC)
    {
        this.QUESTION = QUESTION;
        this.ASWER = ASWER;
        this.OPA = OPA;
        this.OPB = OPB;
        this.OPC = OPC;
    }

    public int getID() {
        return ID;
    }

    public String getQUESTION() {
        return QUESTION;
    }

    public String getASWER() {
        return ASWER;
    }

    public String getOPA() {
        return OPA;
    }

    public String getOPB() {
        return OPB;
    }

    public String getOPC() {
        return OPC;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setQUESTION(String QUESTION) {
        this.QUESTION = QUESTION;
    }

    public void setASWER(String ASWER) {
        this.ASWER = ASWER;
    }

    public void setOPA(String OPA) {
        this.OPA = OPA;
    }

    public void setOPB(String OPB) {
        this.OPB = OPB;
    }

    public void setOPC(String OPC) {
        this.OPC = OPC;
    }
}
