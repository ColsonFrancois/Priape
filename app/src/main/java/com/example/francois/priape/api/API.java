package com.example.francois.priape.api;

import android.content.Context;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Francois on 13/03/2016.
 */
public class API {

    public static void init(Context context){

        Backendless.initApp(context, Default.APPLICATION_ID, Default.SECRET_KEY, Default.VERSION);

    }
    public  static  void login(String username, String password)
    {
        Backendless.UserService.login(username, password, new BackendlessCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser backendlessUser) {

        }
            @Override
            public void handleFault(BackendlessFault backendlessFault)
            {
                Log.d("backendless", "-" + backendlessFault);
            }
        });
    }
}
