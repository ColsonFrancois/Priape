package com.example.francois.priape.api.utils;

/**
 * Created by Francois on 03/04/2016.
 */
public class LoginCredentials {
    private String login;
    private String password;

    public LoginCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
