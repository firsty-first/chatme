package com.example.chatme;


import android.Manifest;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

public class locationUtils {

    public interface LocationResultListener {
        void onLocationResult(Location location);
    }

    public static void requestLocationUpdates(
            Context context,
            LocationResultListener resultListener,
            long minTimeMillis,
            float minDistanceMeters) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Check if the app has permission to access the device's location
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    resultListener.onLocationResult(location);
                    // Optionally, you can unregister the listener if a single location update is sufficient
                    locationManager.removeUpdates(this);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // Handle location provider status changes (if needed)
                }

                @Override
                public void onProviderEnabled(String provider) {
                    // Called when the location provider is enabled
                }

                @Override
                public void onProviderDisabled(String provider) {
                    // Called when the location provider is disabled
                }
            };

            // Request location updates from the provider (GPS or network)
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTimeMillis,
                    minDistanceMeters,
                    locationListener);

        } else {
            // Request location permission from the user
            // You may want to handle this case more gracefully in a production app
            // For simplicity, we'll assume that the app has the necessary permissions
        }
    }
}
