package com.instanceit.alhadafpos.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import io.michaelrocks.paranoid.Obfuscate;

/**
 * Created by c49 on 27/01/16.
 */
@Obfuscate

public class SessionManagement {
    public static final String TAG = "SessionManagement====>";


    public static boolean createSession(Context context, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(AppConstant.PREFERENCE_KEY_USERINFO, value).commit();
        return true;


    }

    public static boolean chkSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
       // ////Log.e(TAG, " +" + sharedPreferences.getString(AppConstant.PREFERENCE_KEY_USERINFO, null));

        return sharedPreferences.getString(AppConstant.PREFERENCE_KEY_USERINFO, null) != null;
    }

    public static boolean savePreferences(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
        return true;

    }

    public static boolean savePreferences(Context context, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
        return true;

    }

    public static boolean savePreferences(Context context, String key, int value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
        return true;

    }

    public static String getStringValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, value);

    }

    public static long getLongValue(Context context, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, value);

    }

    public static boolean setIntValue(Context context, String key, int value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
        return true;

    }

    public static int getIntValue(Context context, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, value);

    }

    public static boolean setBoolean(Context context, String key, boolean value) {
      //  ////Log.e("set method======>", key + " " + value);
        key = key.trim().toLowerCase();
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
        return true;

    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        key = key.trim().toLowerCase();
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, value);
    }

    public static void logOut(Context context) {
        SharedPreferences settings = context.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();

    }

}
