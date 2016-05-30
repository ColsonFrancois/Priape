package com.example.francois.priape;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.api.Default;
import com.example.francois.priape.databinding.ActivityLoginBinding;
import com.example.francois.priape.session.SessionManager;
import com.example.francois.priape.session.Singleton;

public class LoginActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);


        sessionManager = new SessionManager(this);
        sessionManager.logout();
        if(sessionManager.isLogged())
        {
            User user = new User();
            API.setCurrentUser(user);
            API.getUser(sessionManager.getToken(), sessionManager.getObjectid(), new Callback.LoginCallback() {
                @Override
                public void success(User user) {
                    Singleton.getInstance().setUser(user);
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                }

                @Override
                public void error(String errorCode) {

                }
            });
        }



        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.email.getText().toString().isEmpty() || binding.password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_fields), Toast.LENGTH_LONG).show();

                }else{
                    final MaterialDialog progress = new MaterialDialog.Builder(LoginActivity.this)
                            .content(R.string.login_loading)
                            .progress(true, 0)
                            .show();

                    if(Default.haveNetworkConnection(getApplicationContext())) {
                        API.login(binding.email.getText().toString(), binding.password.getText().toString(), new Callback.LoginCallback() {
                            @Override
                            public void success(User user) {
                            /*    sessionManager.insertUser(user.getUserToken(), user.getObjectId());*/
                                progress.dismiss();
                                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void error(String errorCode) {
                                progress.dismiss();
                                Log.i("error", errorCode.toString());
                                String errorMessage;
                                if (Integer.parseInt(errorCode) == 3003) {
                                    errorMessage = getString(R.string.error_3003);
                                } else {
                                    String test = "E3003";
                                    errorMessage = getString(R.string.error_others);
                                    Error(errorMessage);
                                }

                            }
                        });
                    }else{
                            progress.dismiss();
                        Error(getString(R.string.NoInternet));
                    }
                }
            }
        });


        binding.LoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        binding.loginForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, PasswordRecorveryActivity.class);
                    startActivity(intent);
            }
        });



    }
    public void Error(String message)
    {
        // Creating a popup will appear with the error message
        new MaterialDialog.Builder(LoginActivity.this)
                .content(message)
                .positiveText(android.R.string.ok)
                .show();
    }

}
