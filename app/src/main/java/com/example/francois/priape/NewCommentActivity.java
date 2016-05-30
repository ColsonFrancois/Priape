package com.example.francois.priape;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.francois.priape.Model.Comment;
import com.example.francois.priape.R;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.databinding.ActivityNewCommentBinding;
import com.example.francois.priape.session.Singleton;

public class NewCommentActivity extends AppCompatActivity {
        private ActivityNewCommentBinding binding;
        private String professional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_comment);

        //Initialise toolbar element
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.newComment));
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getStringExtra("objectId") != null)
        {
            professional = getIntent().getStringExtra("objectId");
        }
        binding.newCommentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment(binding.newCommentMessage.getText().toString(), Singleton.getInstance().getUser().getName(), professional,5);
                API.newComment(comment, new Callback.NewCommentCallback() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
