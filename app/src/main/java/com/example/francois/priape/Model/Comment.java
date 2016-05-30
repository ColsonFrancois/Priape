package com.example.francois.priape.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Francois on 18/05/2016.
 */
public class Comment implements Parcelable {
    private String message;
    private String name;
    private String professional;
    private double note;



    public Comment(String message, String name, String professional, double note)
    {
        this.message = message;
        this.name = name;
        this.professional = professional;
        this.note = note;
    }
    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Comment(Parcel in) {
        message = in.readString();
        name = in.readString();
        professional = in.readString();
        note = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(name);
        dest.writeString(professional);
        dest.writeDouble(note);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}