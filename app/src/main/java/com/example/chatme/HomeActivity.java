package com.example.chatme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatme.Adapter.fragmentAdapter;
import com.example.chatme.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION =2 ;

    ActivityHomeBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onStart() {
        super.onStart();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        //  getSupportActionBar().show();
        binding.viewpager.setAdapter(new fragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewpager);
        // Check for location permission

            // Permission already granted, request location updates
            startLocationUpdates();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuehome, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//      switch (item.getItemId())
//      {
//          case  R.id.setting:
//              break;
//          case R.id.logout:
//              auth.signOut();
//              startActivity(new Intent(HomeActivity.this,signInActivity.class));
//              break;
        //  }
        if (item.getItemId() == R.id.logout) {
            auth.signOut();
            startActivity(new Intent(HomeActivity.this, signInActivity.class));
//
        }
        // else  if (item.getItemId()==R.id.report_issue)

        return super.onOptionsItemSelected(item);
    }

    private void startLocationUpdates() {
        // Check if location services are enabled
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            // Define a listener that responds to location updates
            Log.d("location","null nhi hai");
                    LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Called when a new location is found
                    Log.d("location","listener ke andar");
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    //String myloc=Double.toString(latitude)+","+Double.toString(longitude);
                    createUserNode(latitude,longitude);
                    Log.d("location", "Latitude: " + latitude + ", ongitude: " + longitude);
                    // Get SharedPreferences instance
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

// Get SharedPreferences editor
                    SharedPreferences.Editor editor = sharedPreferences.edit();

// Put the string value
                    editor.putString("latitude", Double.toString(latitude));
                    editor.putString("longitude", Double.toString(longitude));

// Apply changes
                    editor.apply();

                    // You can use the location data as needed
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d("location",Integer.toString(status));
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d("location","provider enabled");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d("location","provider disabled");
                }
            };

            // Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
        }
        else
            Log.d("location","null hai");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start location updates
                startLocationUpdates();
            } else {
                // Permission denied, handle accordingly
                Log.e("permission", "Location permission denied");
            }
        }
    }
    public void createUserNode(double latitude, double longitude) {
        // Get current user ID from FirebaseAuth
        String userId = auth.getCurrentUser().getUid();

        UserModel user = new UserModel();

        // Push the user object to the database
        Map<String, Object> userUpdates = new HashMap<>();

        userUpdates.put("latitude",latitude);
        userUpdates.put("longitude",longitude);


        // Update the specific fields within the user node
        database.getReference().child("user").child(userId).updateChildren(userUpdates);




    }
}