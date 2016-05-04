package com.example.francois.priape;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private String job;
    private String work;
    private String provider;
    private List<User> users;
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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        //Get Strings from previous page "SearchActivity"
        if (getIntent().getStringExtra("job") != null) {
            job = getIntent().getStringExtra("job");
            if(getIntent().getStringExtra("work") != null){
                work = getIntent().getStringExtra("work");


            API.GetUsers(job, work, new Callback.GetListCallback<User>() {
                @Override
                public void success(List<User> results) {
                        users= new ArrayList<User>(results);
                        addMarker(results);
                        progress.dismiss();
                }

                @Override
                public void error(String errorCode) {
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), errorCode, Toast.LENGTH_LONG).show();
                }
            });
            }
        }
/*            if (provider != null && !provider.equals("")) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 2000, 1, (LocationListener) this);
                if(location != null)
                {
                    Log.i("IN", "IN");
                    onLocationChanged(location);
                }
                else{
                    Toast.makeText(getBaseContext(), "Location introuvable", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Provider null", Toast.LENGTH_LONG).show();
            }*/
    }
    public  void onLocationChanged(Location location)
    {
        Log.i("LatLong", Double.toString(location.getLatitude()) +"," + Double.toString(location.getLongitude()));
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Votre position").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_play_dark)));
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
    public boolean onMarkerClick(Marker marker) {
        User userFound = new User();
        for(User user : users)
        {
            if(user.getName() == marker.getTitle())
            {
                userFound = user;
                Log.i("found", user.getName());
            }
        }

        return false;
    }
}
