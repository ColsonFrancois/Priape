package com.example.francois.priape;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.Adapter.CommentsAdapter;
import com.example.francois.priape.Model.Comment;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.databinding.ActivityCommentsBinding;
import com.example.francois.priape.session.Singleton;

import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private ActivityCommentsBinding binding;
    private User user = new User();
    private CommentsAdapter adapter;
    private MaterialDialog materialDialog;
    private MaterialDialog progress;
    private List<Comment> list;

    @Override
    protected void onResume() {
        Log.d("test", "resume");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d("test", "start");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d("test", "pause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d("test", "Restart");
        super.onRestart();
    }

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
/*                Intent intent = new Intent(getApplicationContext(), NewCommentActivity.class);
                intent.putExtra("objectId", user.getObjectId());
                startActivity(intent);*/
                    materialDialog = new MaterialDialog.Builder(CommentsActivity.this)
                        .title("Nouveau")
                        .customView(R.layout.activity_new_fragment, true)
                        .show();
                View view = materialDialog.getCustomView();
                SeekBar seekBar = (SeekBar) view.findViewById(R.id.MAP_SEEKBAR);
                final TextView note = (TextView)view.findViewById(R.id.fragment_note);
                TextView agree = (TextView)view.findViewById(R.id.fragment_agree);
                final TextView message = (TextView)view.findViewById(R.id.fragment_comment);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int result = progress/10;
                        note.setText(String.valueOf(result));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();

                        final Comment comment = new Comment(message.getText().toString(), Singleton.getInstance().getUser().getName(), user.getObjectId(), Double.parseDouble(note.getText().toString()));
                        API.newComment(comment, new Callback.NewCommentCallback() {
                            @Override
                            public void success() {
                                adapter.add(comment);
                            }

                            @Override
                            public void error(String errorCode) {

                            }
                        });

                    }
                });

            }
        });
/*            API.getComments(user.getObjectId(), new Callback.GetListCallback<Comment>() {
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
            });*/

            if( getIntent().getParcelableArrayListExtra("comments") !=null)
            {

                 list = getIntent().getParcelableArrayListExtra("comments");
                adapter = new CommentsAdapter(list, getApplicationContext());
                binding.recyclerViewComments.setAdapter(adapter);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                binding.recyclerViewComments.setLayoutManager(linearLayoutManager);
            }
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){finish();return true;}
        return super.onOptionsItemSelected(item);
    }



}
