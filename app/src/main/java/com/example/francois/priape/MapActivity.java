package com.example.francois.priape;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.francois.priape.Model.User;
import com.example.francois.priape.api.API;
import com.example.francois.priape.api.Callback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements LocationListener, GoogleMap.OnMarkerClickListener {

    /*  private GoogleMap googleMap;*/
    private String job;
    private String work;
    private List<User> users;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final MaterialDialog progress = new MaterialDialog.Builder(MapActivity.this)
                .content(R.string.search_loading)
                .progress(true, 0)
                .show();

        //Initialise toolbar element
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.results));
        //Set back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        //Initialize map
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_map);
        googleMap = supportMapFragment.getMap();
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);


        //Get Strings from previous page "SearchActivity"
        if (getIntent().getStringExtra("job") != null) {
            job = getIntent().getStringExtra("job");
            if (getIntent().getStringExtra("work") != null) {
                work = getIntent().getStringExtra("work");

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                Criteria criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, false);
                if(provider != null && !provider.equals(""))
                {
                    Location location = locationManager.getLastKnownLocation(provider);
                    locationManager.requestLocationUpdates(provider, 2000, 1, this);
                    if(location!=null)
                    {
                        onLocationChanged(location);

                        API.GetUsers(location.getLatitude(), location.getLongitude(),job, work, new Callback.GetListCallback<User>() {
                            @Override
                            public void success(List<User> results) {
                                users = new ArrayList<User>(results);
                                if(users.size() > 0)
                                addMarker(results);
                                else
                                Toast.makeText(getApplicationContext(), R.string.notfound, Toast.LENGTH_LONG).show();
                                progress.dismiss();

                            }

                            @Override
                            public void error(String errorCode) {
                                progress.dismiss();
                                Toast.makeText(getApplicationContext(), errorCode, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), R.string.NotFoundLocalisation, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), R.string.NotFoundLocalisation, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    private void addMarker(List<User> users)
    {
        for(User user : users)
        {

            LatLng latLng = new LatLng(user.getLocation().getLatitude(), user.getLocation().getLongitude());
          Marker m =  googleMap.addMarker(new MarkerOptions().position(latLng).title(user.getName()));
            googleMap.setOnMarkerClickListener(this);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true; }
        if(id == R.id.menu_map_list)
        {
            Intent intent = new Intent(getApplicationContext(), professionalsActivity.class);
            intent.putParcelableArrayListExtra("listUser", (ArrayList<User>) users);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_map, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onLocationChanged(Location location) {
       /* Log.i("LatLng", Double.toString(location.getLatitude())+ Double.toString(location.getLongitude()));*/
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        for(User user : users)
        {

            if(user.getName().equals(marker.getTitle()))
            {
                Intent intent = new Intent(getApplicationContext(),ProfessionalSelectedActivity.class);
                intent.putExtra("professional", user);
                startActivity(intent);
            }
        }
        /*intent.putExtra("professional", users.get(position));*/
        return false;
    }
}
