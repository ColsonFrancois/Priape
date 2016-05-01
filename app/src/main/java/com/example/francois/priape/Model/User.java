package com.example.francois.priape.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Francois on 30/04/2016.
 */
public class User {

    private String ___class;
    private String __meta;
    private String ownerId;
    private String objectId;
    private String email;
    private Job job;
    @SerializedName("user-token")
    private String userToken;


    public User(String __meta, String objectId, String ownerId, String email, Job job)
    {
        this.__meta = __meta;
        this.objectId = objectId;
        this.ownerId = ownerId;
        this.email = email;
        this.job = job;
        this.___class = getClass().getSimpleName();
    }

    public String get___class() {
        return ___class;
    }

    public void set___class(String ___class) {
        this.___class = ___class;
    }

    public String get__meta() {
        return __meta;
    }

    public void set__meta(String __meta) {
        this.__meta = __meta;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
