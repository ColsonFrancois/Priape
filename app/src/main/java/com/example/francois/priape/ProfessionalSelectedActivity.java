package com.example.francois.priape;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.francois.priape.Model.User;
import com.example.francois.priape.databinding.ActivityProfessionalSelectedBinding;

public class ProfessionalSelectedActivity extends AppCompatActivity {
    private ActivityProfessionalSelectedBinding binding;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_selected);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_professional_selected);

        //Initialise toolbar element
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getParcelableExtra("professional") != null) {
            Log.i("ajajaja", "in");
            user = getIntent().getParcelableExtra("professional");
            Log.i("name", user.getName());
            binding.setUser(user);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_professional_selected, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Toolbar button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.menu_professional_selected_phone) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Integer.toString(user.getPhone())));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                startActivity(intent);
            }

        }

        return super.onOptionsItemSelected(item);

    }

}
