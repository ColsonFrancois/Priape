package com.example.francois.priape;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.francois.priape.Model.Adress;
import com.example.francois.priape.Model.Comment;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.example.francois.priape.databinding.ActivityProfessionalSelectedBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfessionalSelectedActivity extends AppCompatActivity {
    private ActivityProfessionalSelectedBinding binding;
    private User user = new User();
    private Geocoder geocoder;
    private List<Address> addresses;
    private List<Comment> comments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_professional_selected);


        //Initialise toolbar element
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getParcelableExtra("professional") != null) {
            user = getIntent().getParcelableExtra("professional");
            binding.setUser(user);

            geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {

                addresses = geocoder.getFromLocation(user.getLocation().getLatitude(), user.getLocation().getLongitude(), 1);// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                Log.d("test",Double.toString(user.getLocation().getLatitude()));
                Log.d("test",Double.toString(user.getLocation().getLongitude()));
                Log.d("test",addresses.toString());
                if(addresses != null)
                {
                    Adress adress = new Adress(addresses.get(0).getAddressLine(0), addresses.get(0).getLocality(), addresses.get(0).getCountryName());
                    binding.setAdress(adress);
                }


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("test", e.getMessage());
            }
            API.getComments(user.getObjectId(), new Callback.GetListCallback<Comment>() {
                @Override
                public void success(List<Comment> results) {
                    comments = new ArrayList<Comment>(results);
                    if(results.size() <= 0){
                        binding.professionalSelectedAvg.setText("X");
                        binding.professionalSelectedNoone.setVisibility(View.VISIBLE);
                    }else {
                        double avg = 0;
                        for (Comment comment : results) {
                            avg = avg + comment.getNote();
                        }
                        avg = round(avg / results.size(), 1);
                        binding.professionalSelectedAvg.setText(String.valueOf(avg));
                        binding.gauge3.setValue((int) (avg * 10));
                    }
                }

                @Override
                public void error(String errorCode) {

                }
            });


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
        }  else if (id == R.id.menu_professional_selected_phone) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Integer.toString(user.getPhone())));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }

            startActivity(intent);

        }
        else if(id == R.id.menu_professional_selected_comment)
        {

            Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
            intent.putExtra("professional", user);
            ArrayList<Comment> test = new ArrayList<>(comments);
             intent.putParcelableArrayListExtra("comments", test);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
