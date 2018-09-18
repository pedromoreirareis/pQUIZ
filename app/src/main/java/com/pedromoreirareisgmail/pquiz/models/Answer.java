package com.pedromoreirareisgmail.pquiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Answer implements Parcelable {

    private String categoryId;
    private List<String>  answerList;

    public Answer() {
    }

    public Answer(String categoryId, List<String> answerList) {
        this.categoryId = categoryId;
        this.answerList = answerList;
    }

    protected Answer(Parcel in) {
        categoryId = in.readString();
        answerList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeStringList(answerList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }
}
