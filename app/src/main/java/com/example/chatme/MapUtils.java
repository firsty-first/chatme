package com.example.chatme;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MapUtils {

    // Function to open Google Maps either in the app or in a web browser
    public static void openGoogleMaps(Context context, String latitude, String longitude, String label) {
        String uri = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude + "(" + label + ")";
        Uri gmmIntentUri = Uri.parse(uri);

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package to open in Google Maps app

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            // Open in the Google Maps app
            context.startActivity(mapIntent);
        } else {
            // If Google Maps app is not available, open in a web browser
            String mapUrl = "https://www.google.com/maps?q=" + latitude + "," + longitude + "(" + label + ")";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
            context.startActivity(browserIntent);
        }
    }
}
