package com.example.francois.priape;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.francois.priape.api.API;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button searchButton = (Button)findViewById(R.id.search);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                API.GetUsers("francois");
            }
        });
    }


}
