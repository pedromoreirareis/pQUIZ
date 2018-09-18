package com.pedromoreirareisgmail.pquiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    private String user;
    private String score;
    private String categoryId;
    private String categoryName;
    private String userCategory;

    public Score() {
    }

    public Score(String userCategory, String user, String score, String categoryId, String categoryName) {
        this.userCategory = userCategory;
        this.user = user;
        this.score = score;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    protected Score(Parcel in) {
        userCategory = in.readString();
        user = in.readString();
        score = in.readString();
        categoryId = in.readString();
        categoryName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userCategory);
        dest.writeString(user);
        dest.writeString(score);
        dest.writeString(categoryId);
        dest.writeString(categoryName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
