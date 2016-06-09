package com.example.francois.priape.api.utils;

import android.util.Log;

import com.example.francois.priape.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Francois on 01/05/2016.
 */
public class UserLogin {
    private String request;
    private User user;
    private String token;

    public UserLogin(String request)
    {
        Log.i("TOKEN", request.toString());
        this.request = request;
        extractUserFromResponseBody();
        extractTokenFromResponseBody();
    }
    public void extractUserFromResponseBody()
    {
        try{
            JSONObject bodyJSON = new JSONObject(request);
            this.user = new User(bodyJSON.getString("__meta"),
                    bodyJSON.getString("objectId"),
                    bodyJSON.getString("ownerId"),
                    bodyJSON.getString("email"),
                    bodyJSON.getInt("phone"),
                    bodyJSON.getString("name")
            );
            Log.d("test", String.valueOf(this.user.getPhone()));

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void extractTokenFromResponseBody()
    {
        try{
            JSONObject bodyJSON = new JSONObject(request);
            this.token = bodyJSON.getString("user-token");
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void setRequest(String request) {
        this.request = request;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRequest() {
        return request;
    }
}
