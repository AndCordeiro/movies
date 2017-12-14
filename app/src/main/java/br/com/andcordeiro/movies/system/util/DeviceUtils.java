package br.com.andcordeiro.movies.system.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
public class DeviceUtils {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}