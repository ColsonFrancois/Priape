package com.example.francois.priape.Model;

/**
 * Created by Francois on 18/05/2016.
 */
public class Comment {
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
}
