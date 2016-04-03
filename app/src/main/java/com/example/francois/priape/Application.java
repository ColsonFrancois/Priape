package com.example.francois.priape;

import com.example.francois.priape.api.API;

import java.util.TimeZone;

/**
 * Created by Francois on 13/03/2016.
 */
public class Application extends  android.app.Application {

    @Override
    public  void onCreate(){
        super.onCreate();
        TimeZone.getTimeZone("GMT");
        API.init();
    }
}
