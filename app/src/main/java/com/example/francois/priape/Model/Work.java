package com.example.francois.priape.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Francois on 01/05/2016.
 */
public class Work implements Parcelable {


    private String ___class;
    private String name;


    public Work(String name)
    {
        this.name = name;
        this.___class = getClass().getSimpleName();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    protected Work(Parcel in) {
        ___class = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(___class);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Work> CREATOR = new Parcelable.Creator<Work>() {
        @Override
        public Work createFromParcel(Parcel in) {
            return new Work(in);
        }

        @Override
        public Work[] newArray(int size) {
            return new Work[size];
        }
    };
}