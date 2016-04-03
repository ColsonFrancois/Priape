package com.example.francois.priape;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText editText_Email = (EditText) findViewById(R.id.email);
        final EditText editText_Password = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                API.login(editText_Email.getText().toString(), editText_Password.getText().toString(), new Callback.LoginCallback() {
                    @Override
                    public void success() {

                    }

                    @Override
                    public void error(String errorCode) {

                    }
                });
            }
        });




    }


}
