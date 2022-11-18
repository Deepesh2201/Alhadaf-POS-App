package com.instanceit.alhadafpos.Fragments.Dashboard;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_AUTO_CREATE;
import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.BuildConfig;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.Dashboard.Adapter.StoreListAdapter;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.CheckIsUpdateReady;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.UrlResponce;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.service.PosprinterService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    MainActivity mainActivity;

    public static IMyBinder myBinder;

    //Views
    RecyclerView rv_company;
    LinearLayout ln_store_list;
    TextView tv_lbl_select_store, tv_login_name;


    //Variables
    String mToken = "";
    int ln_width, ln_height;

    ServiceConnection mSerconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (IMyBinder) service;
//            Log.e("myBinder", "connect");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            Log.e("myBinder", "disconnect");
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        //bind service，get imyBinder
        Intent intent = new Intent(mainActivity, PosprinterService.class);
        mainActivity.bindService(intent, mSerconnection, BIND_AUTO_CREATE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);
    }


    private void Declaration(View view) {
        rv_company = view.findViewById(R.id.rv_company);
        ln_store_list = view.findViewById(R.id.ln_store_list);
        tv_lbl_select_store = view.findViewById(R.id.tv_lbl_select_store);
        tv_login_name = view.findViewById(R.id.tv_login_name);
    }


    private void Initialization() {

//        SessionManagement.setBoolean(getContext(), AppConstant.ISSELECTEDPRINOP, false);

        SessionManagement.savePreferences(getContext(), AppConstant.STORE_NAME, "");
        mainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        rv_company.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mainActivity.edt_search.clearFocus();
        mainActivity.edt_search.setVisibility(View.GONE);
        tv_login_name.setText("Welcome, " + SessionManagement.getStringValue(getContext(), AppConstant.FULLNAME, ""));
        callApiSelectStore();

        try {
            tv_lbl_select_store.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_STORE_TITLE_SELECT_STORE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        appUpdateManager = AppUpdateManagerFactory.create(getContext());


//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
//                if (!task.isSuccessful()) {
//                    return;
//                }
//                mToken = task.getResult();
//                Log.e("mToken", "onComplete: " + mToken);
//                callApiAddDeviceData();
//            }
//        });

    }


    //<editor-fold desc="Call API Select Store">
    public ArrayList<Model> storeArrayList;

    public void callApiSelectStore() {

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "listpersonstore");

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MASTER_URL, params, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Log.e("TAG", "listgender: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                if (jsonObject.has("storedata")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("storedata");

                                    storeArrayList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    storeArrayList = gson.fromJson(jsonArray.toString(), listType);

                                    if (!SessionManagement.getBoolean(mainActivity, AppConstant.ISSELECTEDPRINOP, false)) {
                                        Utility.OpenPrintingOptionDlg(mainActivity, myBinder);
                                    }


                                    ln_store_list.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            ln_width = ln_store_list.getMeasuredWidth();
                                            ln_height = ln_store_list.getMeasuredHeight();

                                            StoreListAdapter storeListAdapter = new StoreListAdapter(getContext(), storeArrayList, ln_width);
                                            rv_company.setAdapter(storeListAdapter);
                                        }
                                    });

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


    //<editor-fold desc="Add Device data api call and update manage">
    String message = "", verName;
    int isforcefully = 0, isforcelogout = 0, verCode;

    private void callApiAddDeviceData() {

        Map<String, String> params = new HashMap<>();
        params.put("action", "adddevicedata");
        params.put("appversion", getVerName());
        params.put("devicemodelname", getDeviceName());
        params.put("macaddress", getWiFiMAC());
        params.put("deviceid", mToken);
        params.put("os", "a");
        params.put("osversion", String.valueOf(Build.VERSION.SDK_INT));

//        Log.e("params", "callApiAddDeviceData: " + params.toString());

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.DEVICE_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Log.e("response", "callApiAddDeviceData: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 3) {
                                isforcefully = jsonObject.getInt("isforcefully");
                                isforcelogout = jsonObject.getInt("isforcelogout");
                                checkUpdate();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public String getVerName() {
        try {
            verName = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            verCode = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verName;
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model.toUpperCase();
        }
        return manufacturer.toUpperCase() + " " + model;
    }


    public static String getWiFiMAC() {

        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }


    private AppUpdateManager appUpdateManager;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 200;

    private void checkUpdate() {
//        Log.e("checkUpdate", "Checking for updates");

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

//                Log.e("AVAILABLE_VERSION_CODE", appUpdateInfo.availableVersionCode() + "");

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                    startUpdateFlow(appUpdateInfo);
                } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                } else {
                    UpdateApp();
                }
            }
        });
    }


    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, mainActivity, FLEXIBLE_APP_UPDATE_REQ_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void UpdateApp() {
        try {
            new CheckIsUpdateReady("https://play.google.com/store/apps/details?id=" + getContext().getPackageName() + "&hl=en", new UrlResponce() {
                @Override
                public void onReceived(String resposeStr) {
                    if (!BuildConfig.VERSION_NAME.equals(resposeStr)) {
                        //perform your task here like show alert dialogue "Need to upgrade app"
                        if (isforcefully == 1) {
                            dialogUpdateNewApp(message, false, isforcelogout);
                        } else {
                            dialogUpdateNewApp(message, true, isforcelogout);
                        }
                    }
                }
            }).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Dialog updateAppDialog;

    public void dialogUpdateNewApp(String message, final boolean isCancelable, final int isforcelogout) {

        updateAppDialog = new Dialog(getContext());
        updateAppDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        updateAppDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        updateAppDialog.setContentView(R.layout.dialog_exit_message_box);
        TextView dialogTitle = updateAppDialog.findViewById(R.id.idTvDialogMsg);
        dialogTitle.setText(message);
        updateAppDialog.setCancelable(false);
        Button btnOk = updateAppDialog.findViewById(R.id.btnyes);
        Button btnno = updateAppDialog.findViewById(R.id.btnno);
        ImageView iv_gif = updateAppDialog.findViewById(R.id.iv_gif);
        btnOk.getBackground().setLevel(6);
        btnno.getBackground().setLevel(9);
        iv_gif.setVisibility(View.GONE);
        btnOk.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_NOT_NOW).getLabel());
        btnno.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_UPDATE).getLabel());
        btnOk.setVisibility(View.GONE);
        if (isCancelable) {
            btnno.getBackground().setLevel(7);
            btnOk.setVisibility(View.VISIBLE);
            if (!SessionManagement.getBoolean(getContext(), AppConstant.ISUPDATENOTNOW, false)) {
                updateAppDialog.show();
            }
        } else {
            updateAppDialog.show();
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAppDialog.dismiss();
                SessionManagement.setBoolean(getContext(), AppConstant.ISUPDATENOTNOW, true);
            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!isCancelable && isforcelogout == 1) {
                    Utility.maxDeviceLogout(getActivity(), "playstore");
                } else {
                    Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName()));
                    startActivity(viewIntent);
                }
            }
        });
    }

    //</editor-fold>


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
            if (resultCode == RESULT_CANCELED) { // Update canceled by user
                if (isforcefully == 1)
                    UpdateApp();
            } else if (resultCode == RESULT_OK) { // Update success!
                mainActivity.recreate();
            } else { // Update Failed!
                if (isforcefully == 1)
                    UpdateApp();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.iv_menu.setVisibility(View.GONE);
        mainActivity.iv_store.setVisibility(View.GONE);
        mainActivity.iv_search.setVisibility(View.GONE);
        mainActivity.iv_search.setVisibility(View.GONE);
        mainActivity.iv_add_contact.setVisibility(View.GONE);
        mainActivity.iv_workflow.setVisibility(View.GONE);
        mainActivity.iv_range_booking.setVisibility(View.GONE);
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_STORE);
        try {
            appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<AppUpdateInfo>() {
                @Override
                public void onSuccess(AppUpdateInfo appUpdateInfo) {
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        try {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, mainActivity, FLEXIBLE_APP_UPDATE_REQ_CODE);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void onBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (mainActivity.drawer.isDrawerOpen(GravityCompat.START)) {
                        mainActivity.drawer.closeDrawer(GravityCompat.START);
                    } else {
                        Utility.initExitMessagePopupWindow(getActivity(), "Do you want to exit application?");
                    }
                    return true;
                }
                return false;
            }
        });
    }
}