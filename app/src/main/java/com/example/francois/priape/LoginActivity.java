package com.example.francois.priape;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.api.Default;
import com.example.francois.priape.databinding.ActivityLoginBinding;
import com.example.francois.priape.session.SessionManager;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    ActivityLoginBinding binding;
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.loginButton.setText("connexion");
        sessionManager = new SessionManager(this);
        if(sessionManager.isLogged())
        {
            final MaterialDialog progress = new MaterialDialog.Builder(LoginActivity.this)
                    .content(R.string.getSession)
                    .progress(true, 0)
                    .show();
            User user = new User();
            API.setCurrentUser(user);
            API.setToken(sessionManager.getToken().toString());
            API.getUser(sessionManager.getToken().toString(), sessionManager.getObjectid().toString(), new Callback.LoginCallback() {
                @Override
                public void success(User user) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);
                    finish();
                    progress.dismiss();
                }

                @Override
                public void error(String errorCode) {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.error_3025), Toast.LENGTH_LONG).show();
                }
            });
          /*  User user = new User();
            API.setCurrentUser(user);
            API.getUser(sessionManager.getToken(), sessionManager.getObjectid(), new Callback.LoginCallback() {
                @Override
                public void success(User user) {

                   *//* Singleton.getInstance().setUser(user);
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intent);*//*

                }

                @Override
                public void error(String errorCode) {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_3025), Toast.LENGTH_LONG).show();
                }
            });*/
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
                                sessionManager.insertUser(user.getUserToken().toString(), user.getObjectId().toString());
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
                               /* if (Integer.parseInt(errorCode) == 3003) {
                                    errorMessage = getString(R.string.error_3003);
                                } else {
                                    String test = "E3003";
                                    errorMessage = getString(R.string.error_others);
                                    Error(errorMessage);
                                }*/
                                errorMessage = "Votre identifiant ou mot de passe est incorrect";
                                Error(errorMessage);

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
                materialDialog  = new MaterialDialog.Builder(LoginActivity.this)
                        .title(getString(R.string.forgetpassword))
                        .customView(R.layout.activity_forget_fragment, true)
                        .show();
                View view = materialDialog.getCustomView();
                final MaterialEditText email = (MaterialEditText) view.findViewById(R.id.forgetfragment_email);
                TextView agree = (TextView)view.findViewById(R.id.forgetfragment_agree);
                agree.setOnClickListener(new TextView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                        final MaterialDialog progress = new MaterialDialog.Builder(LoginActivity.this)
                                .content(R.string.forgetpassword)
                                .progress(true, 0)
                                .show();
                        API.recoveryPassword(email.getText().toString(), new Callback.RecoveryCallback() {
                            @Override
                            public void success() {
                                Toast.makeText(getApplicationContext(), getString(R.string.sendemail), Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }

                            @Override
                            public void error(String errorCode) {
                                Toast.makeText(getApplicationContext(), getString(R.string.error_3025), Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            }
                        });
                    }
                });
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
