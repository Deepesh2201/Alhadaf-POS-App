package com.instanceit.alhadafpos.Utility;

import android.app.Activity;
import android.util.Log;

import com.instanceit.alhadafpos.VolleyPlus.DefaultRetryPolicy;
import com.instanceit.alhadafpos.VolleyPlus.Request;
import com.instanceit.alhadafpos.VolleyPlus.RequestQueue;
import com.instanceit.alhadafpos.VolleyPlus.Response;
import com.instanceit.alhadafpos.VolleyPlus.error.AuthFailureError;
import com.instanceit.alhadafpos.VolleyPlus.error.VolleyError;
import com.instanceit.alhadafpos.VolleyPlus.request.StringRequest;
import com.instanceit.alhadafpos.VolleyPlus.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class VolleyUtils {

    public static void makeVolleyRequest(final Activity activity, final String url, final Map<String, String> parameters, int maxretry,
                                         final boolean isshowing_pd, final boolean ishide_pd, final VolleyResponseListener listener) {
        // // Log.e(activity.getLocalClassName(), "makeVolleyRequest:"+parameters.toString() );
        try {
            if (isshowing_pd) {
                try {
                    Utility.showProgressDialoug(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            listener.onResponse(response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.optInt("status");
                                String key, unqkey;

                                key = jsonObject.optString("key");
                                unqkey = jsonObject.optString("unqkey");


                                if (status == -1) {
                                    Utility.maxDeviceLogout(activity, "");
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


                            if (ishide_pd) {
                                try {
                                    Utility.hideProgressDialoug();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Utility.hideProgressDialoug();
                            String errorString = VolleyErrorHelper.getMessage(volleyError, activity);
                            if (errorString != null) {
//                                Log.e("tag", volleyError.getMessage().toString());
                                Utility.initErrorMessagePopupWindow(activity, errorString);
                            }
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = new HashMap<>();

                    if (SessionManagement.getStringValue(activity, AppConstant.UNQKEY, "").equals("") || url.equals(AppConstant.LOGIN_URL)) {
                        headers.put("key", AppConstant.CONSTANT_KEY);
                    } else {
                        headers.put("key", SessionManagement.getStringValue(activity, AppConstant.KEY, ""));
                    }
                    headers.put("platform", AppConstant.PLATFORM); // 2-Android,3-iOS, 5-pos

                    headers.put("uid", SessionManagement.getStringValue(activity, AppConstant.UID, ""));
                    headers.put("unqkey", SessionManagement.getStringValue(activity, AppConstant.UNQKEY, ""));
                    headers.put("iss", AppConstant.ISS);
                    headers.put("languageid", SessionManagement.getStringValue(activity, LabelMaster.SELECTED_LANGUAGE_ID, ""));

//                    Log.e("TAG", "getHeaders: * * " + headers.toString());

                    return new HashMap<>(headers);
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return new HashMap<>(parameters);
                }


            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                    maxretry, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(activity);
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}