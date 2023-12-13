package com.example.chatme;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.lang.String;

public class DateUtils {

    public static String formatdate(long timestampMillis) {
        // Create a Date object from the timestamp
        Date date = new Date(timestampMillis);

        // Create a SimpleDateFormat instance with the desired date and time format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        // Format the date as a string
        return sdf.format(date);
    }
    public static String formatTime(long timestampMillis) {
        // Create a Date object from the timestamp
        Date date = new Date(timestampMillis);

        // Create a SimpleDateFormat instance with the desired time format
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        // Format the time as a string
       // sdf=sdf.substring(0,sdf.length()-1);
        return sdf.format(date);
    }
}
