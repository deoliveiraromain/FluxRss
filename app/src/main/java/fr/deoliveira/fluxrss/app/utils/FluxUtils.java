package fr.deoliveira.fluxrss.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

import java.io.*;

/**
 * Created by Romain on 17/01/2016.
 */
public final class FluxUtils {

    public static final String FIRST_TIME = "firstTime";

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
}