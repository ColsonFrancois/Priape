package com.example.francois.priape;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText email_editText = (EditText)findViewById(R.id.Register_email);
        final EditText name_editText = (EditText)findViewById(R.id.Register_name);
        final EditText password_editText = (EditText)findViewById(R.id.Register_password);
        Button register_button = (Button)findViewById(R.id.Register_register);

        //Initialise toolbar element
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.register));
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog progress = new MaterialDialog.Builder(RegisterActivity.this)
                        .content(R.string.progress_inscription)
                        .progress(true, 0)
                        .show();
                API.Register(email_editText.getText().toString(), name_editText.getText().toString(),password_editText.getText().toString(), new Callback.RegisterCallback() {
                    @Override
                    public void success() {
                        progress.dismiss();
                        finish();
                        Toast.makeText(getApplicationContext(), getString(R.string.registerSuccess), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void error(String errorCode) {
                        new MaterialDialog.Builder(RegisterActivity.this)
                                .content(getString(R.string.error_others))
                                .positiveText(android.R.string.ok)
                                .show();
                        progress.dismiss();
                    }
                }
                );
            }
        });
    }


    //Toolbar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true; }

            return super.onOptionsItemSelected(item);

    }
}
