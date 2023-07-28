package com.example.coffeeshop;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    // Function to get current date in "dd/MM/yyyy" format
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        return sdf.format(currentDate);
    }

    // Function to get current time in "HH:mm" format
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date currentDate = new Date();
        return sdf.format(currentDate);
    }
}

