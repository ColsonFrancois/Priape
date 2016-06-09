package com.example.francois.priape;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.example.francois.priape.Adapter.ProfessionalsAdapter;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.databinding.ActivityProfessionalsBinding;

import java.util.List;

public class professionalsActivity extends AppCompatActivity {

    public ActivityProfessionalsBinding binding;
    public ProfessionalsAdapter adapter;
    public List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_professionals);

        //Initialise toolbar element
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.results));
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(getIntent().getParcelableArrayListExtra("listUser") != null){
           users = getIntent().getParcelableArrayListExtra("listUser");
        }

        adapter = new ProfessionalsAdapter(users,this);

        binding.recyclerViewProfessionals.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.recyclerViewProfessionals.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true; }

        return super.onOptionsItemSelected(item);
    }

}
