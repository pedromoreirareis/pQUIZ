package com.pedromoreirareisgmail.pquiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ThreeWrongs implements Parcelable{

    private String first;
    private String second;
    private String third;

    public ThreeWrongs() {
    }

    public ThreeWrongs(String first, String second, String third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    protected ThreeWrongs(Parcel in) {
        first = in.readString();
        second = in.readString();
        third = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(first);
        dest.writeString(second);
        dest.writeString(third);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ThreeWrongs> CREATOR = new Creator<ThreeWrongs>() {
        @Override
        public ThreeWrongs createFromParcel(Parcel in) {
            return new ThreeWrongs(in);
        }

        @Override
        public ThreeWrongs[] newArray(int size) {
            return new ThreeWrongs[size];
        }
    };

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }
}
