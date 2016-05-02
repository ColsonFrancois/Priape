package com.example.francois.priape.api;

import com.example.francois.priape.Model.Job;

import java.util.List;

/**
 * Created by Francois on 13/03/2016.
 */
public abstract class Callback {

    public interface LoginCallback{
        void success();
        void error(String errorCode);
    }
    public interface RegisterCallback{
        void success();
        void error(String errorCode);
    }
    public interface LougoutCallback{
        void success();
        void error(String errorCode);
    }
    public interface JobCallback{
        void success(Job job);
        void error(String errorCode);
    }
public interface GetListCallback<T>{
    void success(List<T> results);
    void error(String errorCode);
}
}
