package com.example.francois.priape.api.utils;

/**
 * Created by Francois on 30/04/2016.
 */
public class RegisterBody {
    private String email;
    private String password;
    private String name;
    private Boolean professional;


    public RegisterBody(String email, String password, String name, Boolean professional) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.professional = professional;
    }
}
