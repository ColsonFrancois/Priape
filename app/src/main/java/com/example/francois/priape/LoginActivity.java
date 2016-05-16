package com.example.francois.priape;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

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


                    API.login(binding.email.getText().toString(), binding.password.getText().toString(), new Callback.LoginCallback() {
                        @Override
                        public void success() {
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
            }
        });


        binding.LoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });



    }

}
