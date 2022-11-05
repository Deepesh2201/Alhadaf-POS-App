package com.instanceit.alhadafpos.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.instanceit.alhadafpos.Activities.MainActivity;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate

public class ConnectionCheckReceiver extends BroadcastReceiver {

    public ConnectionCheckReceiver() {

    }

    MainActivity mainActivity;

    public ConnectionCheckReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //  //////Log.e("onreceive", "onreceive");
        try {

            if (isOnline(context)) {
                mainActivity.dialog(true, context);
            } else {
                mainActivity.dialog(false, context);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                boolean isConnected = activeNetwork.isConnected();
                boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                boolean ismob = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected && isWiFi) {
                    return true;
                } else if (ismob) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}