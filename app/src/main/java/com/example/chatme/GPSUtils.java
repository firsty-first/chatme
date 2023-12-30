package com.example.chatme;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

public class GPSUtils {

    /**
     * Check if GPS is enabled on the device.
     *
     * @param context The context of the application.
     * @return True if GPS is enabled, false otherwise.
     */
    public static boolean isGPSEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Open the location settings screen to enable GPS.
     *
     * @param context The context of the application.
     */
    public static void openGPSSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }
}
