package com.example.francois.priape;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.Model.Work;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.session.SessionManager;
import com.example.francois.priape.session.Singleton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
   private Spinner spinnerWorks;
    private SearchView mSearchView;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Button searchButton = (Button)findViewById(R.id.search_search);
        final Spinner spinnerJobs = (Spinner)findViewById(R.id.search_jobs);

        //Initialise toolbar element
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.search));
        sessionManager = new SessionManager(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("job", spinnerJobs.getSelectedItem().toString());
                intent.putExtra("work", spinnerWorks.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        spinnerJobs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            final MaterialDialog progress = new MaterialDialog.Builder(SearchActivity.this)
                    .content(R.string.update)
                    .progress(true, 0)
                    .show();
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String job = spinnerJobs.getSelectedItem().toString();
                API.getJob(job, new Callback.JobCallback() {
                    @Override
                    public void success(List<Work> works) {
                        addItemsOnSpinnerWork(works);
                        progress.dismiss();

                    }

                    @Override
                    public void error(String errorCode) {
                        Toast.makeText(getApplicationContext(), errorCode, Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        new MaterialDialog.Builder(SearchActivity.this)
                                .content(getString(R.string.error_others))
                                .positiveText(android.R.string.ok)
                                .show();

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
        final MenuItem searchItem = menu.findItem(R.id.menu_search_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint(getString(R.string.SearchByName));

       mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               final MaterialDialog progress = new MaterialDialog.Builder(SearchActivity.this)
                       .content(R.string.search_loading)
                       .progress(true, 0)
                       .show();
               API.searchProfessional(query, new Callback.GetListCallback<User>() {
                   @Override
                   public void success(List<User> results) {
                       progress.dismiss();
                       ArrayList<User> list = new ArrayList<User>(results);
                       Intent intent = new Intent(getApplicationContext(), professionalsActivity.class);
                       intent.putParcelableArrayListExtra("listUser", list);
                       startActivity(intent);
                   }

                   @Override
                   public void error(String errorCode) {
                       progress.dismiss();
                       new MaterialDialog.Builder(SearchActivity.this)
                               .content(getString(R.string.error_others))
                               .positiveText(android.R.string.ok)
                               .show();
                   }
               });
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
            /*  When text change*/
               return false;
           }
       });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            API.Logout(new Callback.LougoutCallback() {
                @Override
                public void success() {
                    sessionManager.logout();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void error(String errorCode) {
                    new MaterialDialog.Builder(SearchActivity.this)
                            .content(getString(R.string.error_others))
                            .positiveText(android.R.string.ok)
                            .show();
                }
            });
            return true;
        }
        if(id == R.id.action_delete)
        {
            /*API.deleteUser(Singleton.getInstance().getUser().getObjectId(), new Callback.deleteUser() {
                @Override
                public void success() {
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                }

                @Override
                public void error(String errorCode) {
                    Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();
                }
            });*/
            new MaterialDialog.Builder(this)
                    .title(R.string.deleteSur)
                    .positiveText(android.R.string.ok).onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    deleteUser();

                }
            })
                    .negativeText(android.R.string.cancel)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void addItemsOnSpinnerWork(List<Work> works)
    {
        spinnerWorks = (Spinner)findViewById(R.id.search_works);

        List<String> list = new ArrayList<String>();
        for(Work work : works)
        {
            list.add(work.getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorks.setAdapter(dataAdapter);
    }

    public void deleteUser()
    {
        final MaterialDialog progress = new MaterialDialog.Builder(SearchActivity.this)
                .content(R.string.deleting)
                .progress(true, 0)
                .show();
                sessionManager.logout();
                API.deleteUser(Singleton.getInstance().getUser().getObjectId(), new Callback.deleteUser() {
                    @Override
                    public void success() {
                        sessionManager.logout();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        progress.dismiss();
                    }

                    @Override
                    public void error(String errorCode) {

                    }
                });
            }
}
