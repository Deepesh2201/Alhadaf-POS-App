package com.instanceit.alhadafpos;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.instanceit.alhadafpos.VolleyPlus.RequestQueue;
import com.instanceit.alhadafpos.VolleyPlus.toolbox.HurlStack;
import com.instanceit.alhadafpos.VolleyPlus.toolbox.Volley;

import io.michaelrocks.paranoid.Obfuscate;


/**
 * Created by SAGAR on 25/8/2021.
 */
@Obfuscate
public class CustomFontApp extends Application {
    public AppCompatActivity activity;
    public static final String TAG = CustomFontApp.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static CustomFontApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fresco.initialize(this);

        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public synchronized static CustomFontApp getInstance() {
        return sInstance;
    }


    public RequestQueue getRequestQueue() {
        //VolleyLog.DEBUG = false;
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        }

        return mRequestQueue;
    }

    public static boolean wasInBackground;
}
