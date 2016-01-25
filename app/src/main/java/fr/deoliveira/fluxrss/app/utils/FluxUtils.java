package fr.deoliveira.fluxrss.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Romain on 17/01/2016.
 */
public final class FluxUtils {

    public static final String FIRST_TIME = "firstTime";

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(FIRST_TIME, true);

    }

    public static void setNotFirstLaunch(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(FIRST_TIME, false);
    }

    public static Date getDateFromString(String dateStr) {
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            Log.e("Date format Error", e.getMessage());
            e.printStackTrace();
        }
        return date;
    }

    public static String getStringFromDate(Date date) {
        return df.format(date);
    }
}