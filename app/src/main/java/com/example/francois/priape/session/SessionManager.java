package com.example.francois.priape.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Francois on 18/05/2016.
 */
public class SessionManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "app_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "isLogged";
    private final static String TOKEN = "token";

    private Context context;

    public SessionManager(Context context)
    {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }

    public boolean isLogged(){return prefs.getBoolean(IS_LOGGED, false);}
    public String getToken(){return prefs.getString(TOKEN, null);}

    public void insertUser(String token)
    {
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public void logout(){editor.clear().commit();}
}
