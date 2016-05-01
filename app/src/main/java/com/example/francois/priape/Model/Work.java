package com.example.francois.priape.Model;

/**
 * Created by Francois on 01/05/2016.
 */
public class Work {


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

}
