package com.example.francois.priape.api.utils;

import org.json.JSONObject;

import okhttp3.ResponseBody;

/**
 * Created by Francois on 23/04/2016.
 */
public class BackendlessError {
    private int code;
    private String message;

    public BackendlessError(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){return code;}
    public String getMessage() {return message;}
    public String toString(){
        return "BackendlessError {" +
                "code=" + code +
                ", message='"+ message + '\''+ '}';
    }

//    public static BackendlessError extractFromResponseBody(ResponseBody body)
//    {
//        try{
//            String bodyError = body.toString();
//            JSONObject bodyJSON = new JSONObject(bodyError);
//
//            return new BackendlessError(bodyJSON.getInt("code"), bodyJSON.getString("message"));
//
//        }
//    }
    public static BackendlessError extractFromResponseBody(ResponseBody body)
    {
        try{
            String bodyError = body.string();
            JSONObject bodyJSON = new JSONObject(bodyError);

            return new BackendlessError(bodyJSON.getInt("code"), bodyJSON.getString("message"));

        }catch (Exception e)
        {
          e.printStackTrace();
        }
        return new BackendlessError(-1, "Generic Error");
    }



}
