package com.instanceit.alhadafpos.Utility;

import android.app.Activity;

import com.instanceit.alhadafpos.CustomFontApp;
import com.instanceit.alhadafpos.VolleyPlus.DefaultRetryPolicy;
import com.instanceit.alhadafpos.VolleyPlus.Request;
import com.instanceit.alhadafpos.VolleyPlus.RequestQueue;
import com.instanceit.alhadafpos.VolleyPlus.Response;
import com.instanceit.alhadafpos.VolleyPlus.error.AuthFailureError;
import com.instanceit.alhadafpos.VolleyPlus.error.VolleyError;
import com.instanceit.alhadafpos.VolleyPlus.request.SimpleMultiPartRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class MultipartVolleyUtils {

    public static void makeVolleyRequest(final Activity activity, final String url, final Map<String, String> parameters, final Map<String, String> fileparameter,
                                         int maxretry, final boolean isshowing_pd, final boolean ishide_pd, final VolleyResponseListener listener) {

        // // // Log.e(activity.getLocalClassName(), "makeVolleyRequest:"+parameters.toString() );

        try {
            if (isshowing_pd) {
                Utility.showProgressDialoug(activity);
            }

            final SimpleMultiPartRequest request = new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {

                    if (ishide_pd) {
                        Utility.hideProgressDialoug();
                    }

                    listener.onResponse(response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.optInt("status");
                        String key = jsonObject.optString("key");
                        String unqkey = jsonObject.optString("unqkey");

                        // Log.e("TAG", "onResponse: * * * " + unqkey);
                        // Log.e("TAG", "onResponse: * * * " + key);

                        if (status == -1) {
                            Utility.maxDeviceLogout(activity,"");
                        }

                        if (key != null && unqkey != null) {
                            if (!key.trim().equals("")) {
                                SessionManagement.savePreferences(activity, AppConstant.KEY, key);
                                Utility.CheckValidUID(activity, key, SessionManagement.getStringValue(activity, AppConstant.UID, ""));
                            }
                            if (!unqkey.trim().equals("")) {
                                SessionManagement.savePreferences(activity, AppConstant.UNQKEY, unqkey);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Utility.hideProgressDialoug();
                    String errorString = VolleyErrorHelper.getMessage(volleyError, activity);
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    if (SessionManagement.getStringValue(activity, AppConstant.UNQKEY, "").equals("")) {
                        headers.put("key", AppConstant.CONSTANT_KEY);
                    } else {
                        headers.put("key", SessionManagement.getStringValue(activity, AppConstant.KEY, ""));
                    }
                    headers.put("uid", SessionManagement.getStringValue(activity, AppConstant.UID, ""));
                    headers.put("unqkey", SessionManagement.getStringValue(activity, AppConstant.UNQKEY, ""));
                    headers.put("iss", AppConstant.ISS);
                    headers.put("platform", AppConstant.PLATFORM); // 2-Android,3-iOS
                    headers.put("languageid", SessionManagement.getStringValue(activity, LabelMaster.SELECTED_LANGUAGE_ID, ""));
                    // // Log.e("TAG", "getHeaders: * * " + headers.toString() + " | " + url);
                    return new HashMap<>(headers);
                }
            };


            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                System.out.printf("%s -> %s%n", entry.getKey(), entry.getValue());
                request.addStringParam(entry.getKey(), entry.getValue());
            }


            if (fileparameter != null) {
                if (fileparameter.size() > 0) {
                    for (Map.Entry<String, String> entry : fileparameter.entrySet()) {
                        System.out.printf("%s -> %s%n", entry.getKey(), entry.getValue());
                        request.addFile(entry.getKey(), entry.getValue());
                    }
                }
            }

            request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, maxretry, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            request.setFixedStreamingMode(true);
            RequestQueue rQueue = CustomFontApp.getInstance().getRequestQueue();
            rQueue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}