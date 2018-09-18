package com.pedromoreirareisgmail.pquiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class AnswerToBut implements Parcelable {

    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public AnswerToBut() {
    }

    public AnswerToBut(String answerA, String answerB, String answerC, String answerD) {
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    protected AnswerToBut(Parcel in) {
        answerA = in.readString();
        answerB = in.readString();
        answerC = in.readString();
        answerD = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answerA);
        dest.writeString(answerB);
        dest.writeString(answerC);
        dest.writeString(answerD);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AnswerToBut> CREATOR = new Creator<AnswerToBut>() {
        @Override
        public AnswerToBut createFromParcel(Parcel in) {
            return new AnswerToBut(in);
        }

        @Override
        public AnswerToBut[] newArray(int size) {
            return new AnswerToBut[size];
        }
    };

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }
}
