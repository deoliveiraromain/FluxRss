package fr.deoliveira.fluxrss.app.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Romain on 24/06/2015.
 */
public class ConnectivityManager extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CO_LOST", "CONNECTION LOST");
        Toast toast = Toast.makeText(context, "Attention : La connexion a changée."
                , Toast.LENGTH_LONG);
        toast.show();

    }

}
