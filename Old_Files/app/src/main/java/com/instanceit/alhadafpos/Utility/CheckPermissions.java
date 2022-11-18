package com.instanceit.alhadafpos.Utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

import io.michaelrocks.paranoid.Obfuscate;

/**
 * Created by INSTANCE 10 on 23-06-2017.
 */
@Obfuscate


public class CheckPermissions {

    public static String[] permissions = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    public static int hasPermissions(Context context) {

        int permGranted = 1;

        String[] PERMISSIONS = retrievePermissions(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && PERMISSIONS != null) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    ++permGranted;
                }
            }
        }
        return permGranted;
    }

    public static String[] retrievePermissions(Context context) {
        try {

            return context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("This should have never happened.", e);
        }

    }

    public static boolean checkedpermission(Context context, String permission) {



        if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

//    public static boolean checkRequiredPermissions(Context context) {
//
//        boolean allGranted = false;
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && retrievePermissions(context) != null) {
//            for (String permission : retrievePermissions(context)) {
//
//                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED || permission.equals(("android.permission.FOREGROUND_SERVICE"))) {
//                    allGranted = true;
//
//                } else {
//
//                    allGranted = false;
//                    break;
//                }
//            }
//        }
//        return allGranted;
//    }


    public static String permi[];

    public static String[] nongrantedretrievePermissions(Context context) {

        List<String> permissionslist = new ArrayList<>();

        for (String per : permissions) {
            if (ActivityCompat.checkSelfPermission(context, per) != PackageManager.PERMISSION_GRANTED) {
                if (!permissionslist.contains(per))
                    permissionslist.add(per);
            }
        }
        String permi[] = new String[0];

        if (!permissionslist.isEmpty()) {
            permi = permissionslist.toArray(new String[permissionslist.size()]);
        }

        return permi;

    }

    public static boolean checkRequiredPermissions(Context context) {
        boolean allGranted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && nongrantedretrievePermissions(context) != null) {

            for (String permission : nongrantedretrievePermissions(context)) {

//                   Log.e("Permission", "checkRequiredPermissions: " + permission);

                if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {

                    allGranted = true;
                } else {
                    allGranted = false;
                    break;
                }
            }
        }
        return allGranted;
    }

}
