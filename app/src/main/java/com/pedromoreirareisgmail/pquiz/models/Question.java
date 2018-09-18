package com.pedromoreirareisgmail.pquiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {


    private String categoryId;
    private String answer;
    private String isImageQuestion;
    private String question;

    public Question() {
    }

    public Question(String categoryId, String answer, String isImageQuestion, String question) {
        this.categoryId = categoryId;
        this.answer = answer;
        this.isImageQuestion = isImageQuestion;
        this.question = question;
    }

    protected Question(Parcel in) {
        categoryId = in.readString();
        answer = in.readString();
        isImageQuestion = in.readString();
        question = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(categoryId);
        dest.writeString(answer);
        dest.writeString(isImageQuestion);
        dest.writeString(question);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsImageQuestion() {
        return isImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        this.isImageQuestion = isImageQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
