package com.example.francois.priape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
        TextView registerButton = (TextView)findViewById(R.id.Login_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


        final MaterialDialog progress = new MaterialDialog.Builder(LoginActivity.this)
                .content(R.string.login_loading)
                .progress(true, 0)
                .show();


                API.login(editText_Email.getText().toString(), editText_Password.getText().toString(), new Callback.LoginCallback() {
                    @Override
                    public void success() {
                        progress.dismiss();
                        Intent intent = new Intent(getApplicationContext(), Search.class);
                        startActivity(intent);
                        finish();
                    }
                    @Override
                    public void error(String errorCode) {
                        progress.dismiss();
                        Log.i("error", errorCode.toString());
                        String errorMessage;
                        if(Integer.parseInt(errorCode) == 3003)
                        {
                            errorMessage = getString(R.string.error_3003);
                        }
                        else{
                            errorMessage = getString(R.string.error_others);
                        }
                        // Creating a popup will appear with the error message
                        new MaterialDialog.Builder(LoginActivity.this)
                                .content(errorMessage)
                                .positiveText(android.R.string.ok)
                                .show();
                    }
                });
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


}
