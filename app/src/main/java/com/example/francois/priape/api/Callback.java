package com.example.francois.priape.api;

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
}
