package com.example.francois.priape;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.Model.Job;
import com.example.francois.priape.Model.Work;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Spinner spinnerWorks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button searchButton = (Button)findViewById(R.id.search_search);
        final Spinner spinnerJobs = (Spinner)findViewById(R.id.search_jobs);

        //Initialise toolbar element
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.search));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                API.GetUsers(spinnerJobs.getSelectedItem().toString(), spinnerWorks.getSelectedItem().toString());
            }
        });
        spinnerJobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String job = spinnerJobs.getSelectedItem().toString();
                API.getJob(job, new Callback.JobCallback() {
                    @Override
                    public void success(Job job) {
                        addItemsOnSpinnerWork(job);

                    }

                    @Override
                    public void error(String errorCode) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            API.Logout(new Callback.LougoutCallback() {
                @Override
                public void success() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void error(String errorCode) {
                    new MaterialDialog.Builder(SearchActivity.this)
                            .content(errorCode)
                            .positiveText(android.R.string.ok)
                            .show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addItemsOnSpinnerWork(Job job)
    {
        spinnerWorks = (Spinner)findViewById(R.id.search_works);
        List<String> list = new ArrayList<String>();
        for(Work work: job.getWorks())
        {
            list.add(work.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorks.setAdapter(dataAdapter);
    }

}
