package br.com.andcordeiro.movies.system.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

public class PermissionUtils {

    private Context context;

    public PermissionUtils(Context context) {
        this.context = context;
    }

    public void requestPermission(String[] permissions, int requestCode) {
        if(permissions != null && permissions.length > 0){
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    permissions,
                    requestCode);
        }
    }

    public String[] getPermissionsNeeded(ArrayList<String> permissions) {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (!this.isPermissionGranted(permission)) {
                permissionsNeeded.add(permission);
            }
        }
        return permissionsNeeded.toArray(new String[permissionsNeeded.size()]);
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isPermissionGranted(ArrayList<String> permissions) {
        boolean stat = true;
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                stat = false;
            }
        }
        return stat;
    }

    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for(int i = 0; i < permissions.length; i++){
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) return false;
        }
        return true;
    }


    public static class Permissions {

        public static ArrayList<String> networkProcess() {
            ArrayList<String> permissions = new ArrayList<>();
            permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
            permissions.add(Manifest.permission.ACCESS_WIFI_STATE);
            permissions.add(Manifest.permission.INTERNET);

            return permissions;
        }
    }
}
