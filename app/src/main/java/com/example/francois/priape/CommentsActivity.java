package com.example.francois.priape;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.example.francois.priape.Adapter.CommentsAdapter;
import com.example.francois.priape.Model.Comment;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.databinding.ActivityCommentsBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private User user = new User();
    private CommentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);


        if (getIntent().getParcelableExtra("professional") != null)
        {
            user = getIntent().getParcelableExtra("professional");

            //Initialise toolbar element
            android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            setTitle(user.getName());
            //Set back button
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        binding.commentsFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewCommentActivity.class);
                intent.putExtra("objectId", user.getObjectId());
                startActivity(intent);
            }
        });
            API.getComments(user.getObjectId(), new Callback.GetListCallback<Comment>() {
                @Override
                public void success(List<Comment> results) {
                    List<Comment> list = new ArrayList<Comment>(results);
                       adapter = new CommentsAdapter(results, getApplicationContext());
                    binding.recyclerViewComments.setAdapter(adapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.recyclerViewComments.setLayoutManager(linearLayoutManager);
                }

                @Override
                public void error(String errorCode) {

                }
            });
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){finish();return true;}
        return super.onOptionsItemSelected(item);
    }
}
