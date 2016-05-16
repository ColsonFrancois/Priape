package com.example.francois.priape.api.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Francois on 16/05/2016.
 */
public class BackendlessMessage {

    private String fileURL;

    public BackendlessMessage(String fileURL){this.fileURL = fileURL;}

    public String getFileURL(){return fileURL;}

    @Override
    public String toString() {
        return "BackendlessMessage{" +
                "url='" + fileURL + '\'' +
                '}';
    }

    public static BackendlessMessage extractFromResponseBody(String response)
    {
        try {
            JSONObject bodyJSON = new JSONObject(response);

            return  new BackendlessMessage(bodyJSON.getString("fileURL"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new BackendlessMessage("Generic protocol");
    }
}
