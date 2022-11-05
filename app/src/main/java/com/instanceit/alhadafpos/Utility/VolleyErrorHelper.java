package com.instanceit.alhadafpos.Utility;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.VolleyPlus.NetworkResponse;
import com.instanceit.alhadafpos.VolleyPlus.error.AuthFailureError;
import com.instanceit.alhadafpos.VolleyPlus.error.NetworkError;
import com.instanceit.alhadafpos.VolleyPlus.error.NoConnectionError;
import com.instanceit.alhadafpos.VolleyPlus.error.ServerError;
import com.instanceit.alhadafpos.VolleyPlus.error.TimeoutError;
import com.instanceit.alhadafpos.VolleyPlus.error.VolleyError;

import java.util.HashMap;
import java.util.Map;

import io.michaelrocks.paranoid.Obfuscate;

/**
 * Created by INSTANCE 10 on 14-10-2017.
 */
@Obfuscate


public class VolleyErrorHelper {


    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error
     * @param context
     * @return
     */
    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            ////Log.e("error",""+error);
            return AppConstant.TIME_OUT;
        } else if (isServerProblem(error)) {
            ////Log.e("error",""+error);
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            ////Log.e("error",""+error);

            return AppConstant.INTERNET_CONNECTION_MESSAGE;

        }
        return AppConstant.GENERIC_ERROR;
    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        ////Log.e("error",""+error);
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error) {
        ////Log.e("server error,",error.toString());

        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 409:
                     break;
                case 404:
                    break;
                case 422:
                    break;
                case 401:
                    try {
                        // server might return error like this { "error": "Some error occured" }
                        // Use "Gson" to parse the result
                        HashMap<String, String> result = new Gson().fromJson(new String(response.data),
                                new TypeToken<Map<String, String>>() {
                                }.getType());

                        if (result != null && result.containsKey("error")) {
                            return result.get("error");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return error.getMessage() != null ? error.getMessage() : AppConstant.GENERIC_ERROR;

                default:
                    return AppConstant.GENERIC_ERROR;
            }
        }
        return AppConstant.GENERIC_ERROR;
    }
}
