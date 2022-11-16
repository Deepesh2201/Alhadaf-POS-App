package com.instanceit.alhadafpos.Utility;

import static com.instanceit.alhadafpos.Utility.AppConstant.PERMISSION_SETTING;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.Adapter.LanguageSelecterAdapter;
import com.instanceit.alhadafpos.Activities.LoginActivity;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Language;
import com.instanceit.alhadafpos.Entity.LanguageLabel;
import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Receiver.DeviceReceiver;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.TaskCallback;
import net.posprinter.utils.PosPrinterDev;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import io.michaelrocks.paranoid.Obfuscate;
//import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
//import uk.co.chrisjenx.calligraphy.TypefaceUtils;


@Obfuscate
public class Utility {

    Context context;
    static String[] permission;

    double latitude;
    double longitude;

    private static final String TAG = Utility.class.getSimpleName();
    public static Dialog authWindow;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int REQUEST_WRITE_STORAGE = 112;
    private static AlertDialog dialogOffline;

    /*public static void alertOffline(Context context) {
        try {
            if (dialogOffline == null || !dialogOffline.isShowing()) {
                dialogOffline = alert(context, "Please check internet connection", "Offline");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }*/
    private static Dialog nertworkErrorpp, exitapp;
    private static Dialog alertDialog;


    public static void scheduleJob(Context context) {

//        Log.e(TAG, "scheduleJob:  ..........start");
//        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(BackgroundJobService.class) // the JobService that will be called
//                .setTag("api_triggerafbrand")
//                .setRecurring(true)
//                .setLifetime(Lifetime.FOREVER)
//                .setTrigger(Trigger.executionWindow(1, 1))
//                .setReplaceCurrent(true)
//                .build();
//        dispatcher.mustSchedule(myJob);
//        Log.e(TAG, "Id " + (dispatcher.schedule(myJob) == SCHEDULE_RESULT_SUCCESS ? "Success" : "Fail"));

    }
  /*  @RequiresApi(api = Build.VERSION_CODES.M)
    public static void scheduleJob(Context context) {

        float time_interval = 60 * Float.parseFloat(SessionManagement.getStringValue(context, AppConstant.BG_SERVICE_TIME, "2"));

        ComponentName serviceComponent = new ComponentName(context, BackgroundJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency((int)(time_interval * 1000)); // wait at least
        builder.setOverrideDeadline((int)((time_interval + 10) * 1000)); // maximum delay
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
*/

    public static boolean isConnected(Context context) {
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showKeyboardFrom(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getIndianRupee(double value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }

    public static double roundTwoDecimals(double d) {
        //  double value=new BigDecimal(d).setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
        double value = Math.round(d);
        return value;
    }

    public static AlertDialog alert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }
        AlertDialog dialogOffline = builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        dialogOffline.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialogOffline.show();
        return dialogOffline;
    }

    public static int getDisplayHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int Height = displayMetrics.heightPixels;
        return Height;
    }


    public static void initExitMessagePopupWindow(final Activity activity, String message) {
        try {
            // We need to get the instance of the LayoutInflater

            if (exitapp != null && exitapp.isShowing())
                return;
            exitapp = new Dialog(activity);

            exitapp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            exitapp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            exitapp.setContentView(R.layout.dialog_exit_message_box);
            TextView dialogTitle = exitapp.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(message);
            Button btnOk = exitapp.findViewById(R.id.btnyes);
            Button btnno = exitapp.findViewById(R.id.btnno);
            ImageView iv_gif = exitapp.findViewById(R.id.iv_gif);
            LinearLayout ln_dlg_main = exitapp.findViewById(R.id.ln_dlg_main);
            RelativeLayout rl_dialog_main = exitapp.findViewById(R.id.rl_dialog_main);

            try {
                btnOk.setText(languageLabel(activity, LabelMaster.LBL_RANGE_BTN_YES).getLabel());
                btnno.setText(languageLabel(activity, LabelMaster.LBL_RANGE_BTN_NO).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_lefttoright);
            Glide.with(activity).asGif().load(R.drawable.loader).into(iv_gif);
            iv_gif.startAnimation(animation);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    exitapp.dismiss();
                    activity.moveTaskToBack(true);
                }
            });
            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    exitapp.dismiss();
                }
            });
            exitapp.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initErrorMessagePopupWindow(final Activity activity, String message) {
        try {
            // We need to get the instance of the LayoutInflater

            if (nertworkErrorpp != null && nertworkErrorpp.isShowing())
                return;
            nertworkErrorpp = new Dialog(activity);

            nertworkErrorpp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            nertworkErrorpp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            nertworkErrorpp.setContentView(R.layout.dialog_error_message_box);
            TextView dialogTitle = nertworkErrorpp.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(Html.fromHtml(message));
            Button btnOk = nertworkErrorpp.findViewById(R.id.btnOk);
            btnOk.getBackground().setLevel(5);

            try {
                btnOk.setText(languageLabel(activity, LabelMaster.LBL_RANGE_BTN_OK).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    nertworkErrorpp.dismiss();
                }
            });
            nertworkErrorpp.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class CustomTextWatcher implements TextWatcher {

        private final EditText mEditText;
        private TextView textView;
        private TextInputLayout mTextInputLayout;

        public CustomTextWatcher(EditText e, TextView textInputLayout) {
            mEditText = e;
            textView = textInputLayout;
        }

        public CustomTextWatcher(EditText e, TextInputLayout textInputLayout) {
            mEditText = e;
            mTextInputLayout = textInputLayout;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                textView.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mTextInputLayout.setErrorEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public static void showProgressDialoug(Activity activity) {

        try {
            if (alertDialog != null && alertDialog.isShowing()) return;

            alertDialog = new Dialog(activity);
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setCancelable(false);
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.setContentView(R.layout.dialog_progrss);
            ImageView imageView = alertDialog.findViewById(R.id.gif);

            Glide.with(activity.getApplicationContext()).asGif().load(R.drawable.loader).into(imageView);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hideProgressDialoug() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissPopUpWindow() {
        if (authWindow != null && authWindow.isShowing()) {
            authWindow.dismiss();
        }
    }

    public static void dismissPopUpWindow(PopupWindow authWindow) {
        if (authWindow != null && authWindow.isShowing()) {
            authWindow.dismiss();
        }
    }

    public static int getDisplayWidht(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getDisplayHight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height;
    }

    public static boolean requestNecessaryPermissions(Activity activity, String[] permission) {

        boolean allGranted = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (String per : permission) {
                if (ActivityCompat.checkSelfPermission(activity, per) == PackageManager.PERMISSION_GRANTED) {
                    allGranted = true;
                } else {
                    allGranted = false;
                    break;
                }
            }
        }
        return allGranted;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public static String date = "";

    public static void showDatePickerDialogNoMinDate(Context context, final TextView editText) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date today = new Date();
        String dateToStr = sdf.format(today);

        if (!dateToStr.equals("")) {
//            Date d = null;
//        try {
//            d = sdf.parse(str_mindate);
//            calendar.setTime(d);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
            try {
                Date dt1 = sdf.parse(dateToStr);
                calendar.setTime(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = formatDate(year, monthOfYear, dayOfMonth);
                editText.setText(date);
            }
        }, yy, mm, dd);

//         long milliseconds = d.getTime();
//        datePicker.getDatePicker().setMinDate(milliseconds);
//        datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePicker.show();
    }


    public static void showDatePickerDialogMinDate(Context context, final TextView editText, String str_mindate) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Date today = new Date();
        String dateToStr = sdf.format(today);

        Date d = null;
        if (!dateToStr.equals("")) {
            try {
                d = sdf.parse(str_mindate);
                calendar.setTime(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Date dt1 = sdf.parse(dateToStr);
                calendar.setTime(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = formatDate(year, monthOfYear, dayOfMonth);
                editText.setText(date);
            }
        }, yy, mm, dd);

        if (d != null) {
            long milliseconds = d.getTime();
            datePicker.getDatePicker().setMinDate(milliseconds);
            datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }
        datePicker.show();
    }

    public static DatePickerDialog datePicker;

    public static void showDatePickerDialogBirthdateDate(Context context, final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        if (!dateToStr.equals("")) {
            Date today = new Date();
            dateToStr = sdf.format(today);
            try {
                Date dt1 = sdf.parse(dateToStr);
                calendar.setTime(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            String selectedDate = editText.getText().toString();
            Date dt1 = null;
            try {
                dt1 = sdf.parse(selectedDate);
                calendar.setTime(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        if (datePicker != null && datePicker.isShowing()) return;

        datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = formatDate(year, monthOfYear, dayOfMonth);
                editText.setText(date);
            }
        }, yy, mm, dd);

        datePicker.show();


        datePicker.getDatePicker().setMaxDate(new Date().getTime());

        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0B2347"));
        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0B2347"));
    }

    private static String dateToStr = "";

    public static void showDatePickerDialog(Context context, final EditText editText) {

        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        if (!dateToStr.equals("")) {
            Date today = new Date();
            dateToStr = sdf.format(today);
            try {
                Date dt1 = sdf.parse(dateToStr);
                calendar.setTime(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            String selectedDate = editText.getText().toString();
            Date dt1 = null;
            try {
                dt1 = sdf.parse(selectedDate);
                calendar.setTime(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);


        if (datePicker != null && datePicker.isShowing()) return;

        datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                date = formatDate(year, monthOfYear, dayOfMonth);
                editText.setText(date);
            }
        }, yy, mm, dd);

        datePicker.show();

        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0B2347"));
        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0B2347"));
    }

    public static String formatDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }


    static public void DialogPermission(final Activity activity) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Allow Permission");
        alertDialog.setMessage("You need to allow previously denied permission(s) to work application smoothly");
        alertDialog.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, PERMISSION_SETTING);
            }
        });

        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                finish();
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public static String getCurrentDate(String format) {
        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat(format, Locale.US);
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }


    //<editor-fold desc="Language Selection Module">
    public static Dialog dialogLanguageSelector;

    public static ImageView iv_dlg_back;
    public static TextView tv_dlg_title, tv_dlg_continue, tv_str_title;
    public static RecyclerView rv_dlg_languages;

    public static void LanguageSelectorDialog(Activity activity) {
        if (dialogLanguageSelector != null && dialogLanguageSelector.isShowing())
            return;
        dialogLanguageSelector = new Dialog(activity, R.style.AppTheme_NoAction);
        dialogLanguageSelector.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLanguageSelector.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
        dialogLanguageSelector.setContentView(R.layout.dialog_language_selector);
        Window window = dialogLanguageSelector.getWindow();
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        dialogLanguageSelector.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        iv_dlg_back = dialogLanguageSelector.findViewById(R.id.iv_back);
        tv_dlg_title = dialogLanguageSelector.findViewById(R.id.tv_toolbar_title);
        rv_dlg_languages = dialogLanguageSelector.findViewById(R.id.rv_dlg_languages);
        tv_dlg_continue = dialogLanguageSelector.findViewById(R.id.tv_dlg_continue);
        tv_str_title = dialogLanguageSelector.findViewById(R.id.tv_str_title);

        rv_dlg_languages.setLayoutManager(new LinearLayoutManager(activity));

        callApiGetLanguageList(activity);

        iv_dlg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLanguageSelector.dismiss();
                dialogLanguageSelector = null;
            }
        });

        dialogLanguageSelector.show();
    }

    public static ArrayList<Language> languageArrayList;

    public static void callApiGetLanguageList(final Activity activity) {

        Map<String, String> map = new HashMap<>();
        map.put("action", "listlanguage");

        VolleyUtils.makeVolleyRequest(activity, AppConstant.LANGUAGE_URL, map, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {

//                        Log.e(TAG, "listlanguage: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            languageArrayList = new ArrayList<>();

                            Gson gson = new Gson();
                            Type listType = new TypeToken<ArrayList<Language>>() {
                            }.getType();

                            if (jsonObject.has("languagedata")) {

                                JSONArray jsonArray = jsonObject.getJSONArray("languagedata");

                                if (jsonArray != null && jsonArray.length() > 0) {
                                    languageArrayList = new ArrayList<>();
                                    languageArrayList = gson.fromJson(jsonArray.toString(), listType);
                                }

                                int lastcheckPos = 0;

                                Language language = new Language();

                                language.setId(SessionManagement.getStringValue(activity, LabelMaster.SELECTED_LANGUAGE_ID, ""));

                                for (int i = 0; i < languageArrayList.size(); i++) {
                                    if (languageArrayList.contains(language)) {
                                        lastcheckPos = languageArrayList.indexOf(language);
                                        break;
                                    }
                                }

                                if (SessionManagement.getStringValue(activity, LabelMaster.SELECTED_LANGUAGE_ID, "").trim().equals("")) {
                                    lastcheckPos = 0;
                                    SessionManagement.savePreferences(activity, LabelMaster.SELECTED_LANGUAGE_ID, languageArrayList.get(lastcheckPos).getId());
                                }

                                LanguageSelecterAdapter languageSelecterAdapter = new LanguageSelecterAdapter(activity, languageArrayList, lastcheckPos);
                                rv_dlg_languages.setAdapter(languageSelecterAdapter);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static void callApiGetLanguageLabels(final Activity activity, final boolean isChangeLang) {

        Map<String, String> map = new HashMap<>();
        map.put("action", "listlanguagewiselabel");
        map.put("languageid", SessionManagement.getStringValue(activity, LabelMaster.SELECTED_LANGUAGE_ID, ""));

        VolleyUtils.makeVolleyRequest(activity, AppConstant.LANGUAGE_URL, map, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {

//                        Log.e(TAG, "languagewiselabel: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");


//                            try {
//                                String languageid = jsonObject.getString("languageid");
//
//                                SessionManagement.savePreferences(activity, LabelMaster.SELECTED_LANGUAGE_ID, languageid);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

                            if (jsonObject.has("languagelabeldata")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("languagelabeldata");

                                SessionManagement.savePreferences(activity, LabelMaster.LANGUAGE_LABEL_LIST, jsonArray.toString());

                                try {
                                    if (isChangeLang) {
                                        activity.recreate();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public static LanguageLabel languageLabel(Activity activity, String text) {
        LanguageLabel languageLabel = null;
        try {
            languageLabel = new LanguageLabel();
            Gson gson = new Gson();
            String json = SessionManagement.getStringValue(activity, LabelMaster.LANGUAGE_LABEL_LIST, "");
            if (json != null) {

                Type type = new TypeToken<ArrayList<LanguageLabel>>() {
                }.getType();

                ArrayList<LanguageLabel> languageLabelArrayList = new ArrayList<>();
                languageLabelArrayList = gson.fromJson(json, type);
                languageLabel.setLabelnameid(text);

                if (languageLabelArrayList.contains(languageLabel)) {
                    int x = languageLabelArrayList.indexOf(languageLabel);
                    languageLabel = languageLabelArrayList.get(x);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return languageLabel;
    }
    //</editor-fold>


    //<editor-fold desc="Logout Manage">
    public static void maxDeviceLogout(final Activity activity, String message) {
        callApiLogout(activity, message);
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

    public static void callApiLogout(final Activity activity, final String message) {

        Map<String, String> params = new HashMap<>();
        params.put("action", "logout");
        params.put("macaddress", getWiFiMAC());
        params.put("os", "a");

        VolleyUtils.makeVolleyRequest(activity, AppConstant.LOGOUT_URL, params, 2, false, false, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("logout", "onResponse: " + response);

                String str_lang_id = SessionManagement.getStringValue(activity, LabelMaster.SELECTED_LANGUAGE_ID, "");
                String json = SessionManagement.getStringValue(activity, LabelMaster.LANGUAGE_LABEL_LIST, "");

//                SessionManagement.logOut(activity);
                SessionManagement.setBoolean(activity, AppConstant.ISLOGING, false);

                SessionManagement.savePreferences(activity, LabelMaster.SELECTED_LANGUAGE_ID, str_lang_id);
                SessionManagement.savePreferences(activity, LabelMaster.LANGUAGE_LABEL_LIST, json);

                Intent intent = new Intent(activity, LoginActivity.class);
                intent.putExtra("logout", message);
                intent.putExtra(AppConstant.ACTION, AppConstant.ACTION_LOGIN);
                activity.startActivity(intent);
                activity.finish();

                NotificationManager notificationManager = (NotificationManager) activity.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
            }
        });
    }
    //</editor-fold>


    //<editor-fold desc="UID compare to base64 decoded UID">
    public static void CheckValidUID(Activity activity, String str_base64, String uid) {
        try {
            String text = "";
            String str_uid = "";

            String[] str_base64_array = str_base64.split("\\.");

            str_base64 = str_base64_array[1];

            byte[] data = Base64.decode(str_base64, Base64.DEFAULT);
            try {
                text = new String(data, StandardCharsets.UTF_8);

                JSONObject jsonObject = new JSONObject(text);
                str_uid = jsonObject.optString("uid");

                if (!str_uid.equals(uid)) {
                    Utility.maxDeviceLogout(activity, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>

    public static final String ESC_POS_PRINT_INTENT_ACTION = "org.escpos.intent.action.PRINT";
    public static final String PRINT_SERVICE = "com.usb.cps";

    public static boolean isIntentPrintServiceAvailable(Activity activity) {
        final PackageManager packageManager = activity.getPackageManager();
        final Intent intent = new Intent(ESC_POS_PRINT_INTENT_ACTION);
        intent.setPackage(PRINT_SERVICE);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.size() > 0;
    }


    public static Dialog PrintingOptionDialog = null;
    public static Spinner sp_option;
    public static TextView tv_address;
    public static EditText et_address;
    public static Button btn_disconnect, btn_connect;
    public static int portType = 0;//0-net，1-bt，2-USB
    public static boolean ISCONNECT = false;

    public static void OpenPrintingOptionDlg(MainActivity context, IMyBinder myBinder) {

        if (PrintingOptionDialog == null) {
            PrintingOptionDialog = new Dialog(context);
            PrintingOptionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            PrintingOptionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            PrintingOptionDialog.setContentView(R.layout.dialog_printing_option_box);
            PrintingOptionDialog.setCancelable(false);
        }

        sp_option = PrintingOptionDialog.findViewById(R.id.sp_option);
        tv_address = PrintingOptionDialog.findViewById(R.id.tv_address);
        et_address = PrintingOptionDialog.findViewById(R.id.et_address);
        btn_disconnect = PrintingOptionDialog.findViewById(R.id.btn_disconnect);
        btn_connect = PrintingOptionDialog.findViewById(R.id.btn_connect);

        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUSB(context);

//                switch (portType) {
//                    case 1:
////                        setBluetooth();
//                        break;
//                    case 2:
//                        setUSB(context);
//                        break;

//                }
            }
        });

        sp_option.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                portType = i;
                switch (i) {
                    case 0:
                        et_address.setVisibility(View.VISIBLE);
                        tv_address.setVisibility(View.GONE);
                        break;
//                    case 1:
//                        et_address.setVisibility(View.GONE);
//                        tv_address.setVisibility(View.VISIBLE);
//                        tv_address.setText("");
//                        break;
                    case 1:
                        et_address.setVisibility(View.GONE);
                        tv_address.setVisibility(View.VISIBLE);
                        tv_address.setText("");
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isValidate()) {
                    switch (portType) {
                        case 0:
                            connectNet(context, myBinder);
                            SessionManagement.savePreferences(context, AppConstant.printer_id, et_address.getText().toString());
                            break;
//                        case 1:
//                            connectBT(context, myBinder);
//                            SessionManagement.savePreferences(context, AppConstant.printer_id, tv_address.getText().toString());
//                            break;
                        case 1:
                            connectUSB(context, myBinder);
                            SessionManagement.savePreferences(context, AppConstant.printer_id, tv_address.getText().toString());
                            break;
                    }
                }
            }
        });


        btn_disconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PrintingOptionDialog.dismiss();
                PrintingOptionDialog = null;
            }
        });

        PrintingOptionDialog.show();
    }

    private static void connectNet(MainActivity context, IMyBinder myBinder) {
        String ip = et_address.getText().toString();
        if (ip != null || ISCONNECT == false) {
            myBinder.ConnectNetPort(ip, 9100, new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ISCONNECT = true;
                    Toast.makeText(context, R.string.con_success, Toast.LENGTH_SHORT).show();
                    SessionManagement.setBoolean(context, AppConstant.ISSELECTEDPRINOP, ISCONNECT);
                    PrintingOptionDialog.dismiss();
                    PrintingOptionDialog = null;
                }

                @Override
                public void OnFailed() {
                    ISCONNECT = false;
                    Toast.makeText(context, R.string.con_failed, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(context, R.string.con_failed, Toast.LENGTH_SHORT).show();
        }
    }


    private static void connectBT(MainActivity context, IMyBinder myBinder) {
        String BtAdress = tv_address.getText().toString().trim();
        if (BtAdress.equals(null) || BtAdress.equals("")) {
            Toast.makeText(context, R.string.con_failed, Toast.LENGTH_SHORT).show();
        } else {
            myBinder.ConnectBtPort(BtAdress, new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ISCONNECT = true;
                    Toast.makeText(context, R.string.con_success, Toast.LENGTH_SHORT).show();
                    SessionManagement.setBoolean(context, AppConstant.ISSELECTEDPRINOP, ISCONNECT);
                    PrintingOptionDialog.dismiss();
                    PrintingOptionDialog = null;
                }

                @Override
                public void OnFailed() {
                    ISCONNECT = false;
                    Toast.makeText(context, R.string.con_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private static void connectUSB(MainActivity context, IMyBinder myBinder) {
        String usbAddress = tv_address.getText().toString().trim();
        if (usbAddress.equals("")) {
            Toast.makeText(context, R.string.discon, Toast.LENGTH_SHORT).show();
        } else {
            myBinder.ConnectUsbPort(context, usbAddress, new TaskCallback() {
                @Override
                public void OnSucceed() {
                    ISCONNECT = true;
                    Toast.makeText(context, R.string.connect, Toast.LENGTH_SHORT).show();
                    SessionManagement.setBoolean(context, AppConstant.ISSELECTEDPRINOP, ISCONNECT);
                    PrintingOptionDialog.dismiss();
                    PrintingOptionDialog = null;
                }

                @Override
                public void OnFailed() {
                    ISCONNECT = false;
                    Toast.makeText(context, R.string.discon, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static boolean isValidate() {
        if (et_address.getVisibility() == View.VISIBLE && et_address.getText().toString().isEmpty()) {
            et_address.requestFocus();
            et_address.setError("Please enter net address");
        } else if (tv_address.getVisibility() == View.VISIBLE && tv_address.getText().toString().isEmpty()) {
            tv_address.requestFocus();
            tv_address.setError("Please select device");
        } else {
            return true;
        }
        return false;
    }


//    private List<String> btList = new ArrayList<>();
//    private ArrayList<String> btFoundList = new ArrayList<>();
//    private ArrayAdapter<String> BtBoudAdapter ,BtfoundAdapter;
//    private View BtDialogView;
//    private ListView BtBoundLv,BtFoundLv;
//    private LinearLayout ll_BtFound;
//    private AlertDialog btdialog;
//    private Button btScan;
//    private DeviceReceiver BtReciever;
//    private BluetoothAdapter bluetoothAdapter;
//
//    public void setBluetooth(){
//        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        if (!bluetoothAdapter.isEnabled()){
//            Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, 1);
//        }else {
//            showblueboothlist();
//
//        }
//    }
//
//    private void showblueboothlist() {
//        if (!bluetoothAdapter.isDiscovering()) {
//            bluetoothAdapter.startDiscovery();
//        }
//        LayoutInflater inflater=LayoutInflater.from(this);
//        BtDialogView=inflater.inflate(R.layout.printer_list, null);
//        BtBoudAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, btList);
//        BtBoundLv= BtDialogView.findViewById(R.id.listView1);
//        btScan= BtDialogView.findViewById(R.id.btn_scan);
//        ll_BtFound= BtDialogView.findViewById(R.id.ll1);
//        BtFoundLv=(ListView) BtDialogView.findViewById(R.id.listView2);
//        BtfoundAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, btFoundList);
//        BtBoundLv.setAdapter(BtBoudAdapter);
//        BtFoundLv.setAdapter(BtfoundAdapter);
//        btdialog=new AlertDialog.Builder(this).setTitle("BLE").setView(BtDialogView).create();
//        btdialog.show();
//
//        BtReciever=new DeviceReceiver(btFoundList,BtfoundAdapter,BtFoundLv);
//
//        //注册蓝牙广播接收者
//        IntentFilter filterStart=new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        IntentFilter filterEnd=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
//        registerReceiver(BtReciever, filterStart);
//        registerReceiver(BtReciever, filterEnd);
//
//        setDlistener();
//        findAvalibleDevice();
//    }
//    private void setDlistener() {
//        btScan.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                ll_BtFound.setVisibility(View.VISIBLE);
//            }
//        });
//
//        //已配对的设备的点击连接
//        BtBoundLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                try {
//                    if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
//                        bluetoothAdapter.cancelDiscovery();
//
//                    }
//
//                    String mac=btList.get(arg2);
//                    mac=mac.substring(mac.length()-17);
////                    String name=msg.substring(0, msg.length()-18);
//                    //lv1.setSelection(arg2);
//                    btdialog.cancel();
//                    adrress.setText(mac);
//                    //Log.i("TAG", "mac="+mac);
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });
//        //未配对的设备，点击，配对，再连接
//        BtFoundLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                try {
//                    if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
//                        bluetoothAdapter.cancelDiscovery();
//
//                    }
//                    String mac;
//                    String msg=btFoundList.get(arg2);
//                    mac=msg.substring(msg.length()-17);
//                    String name=msg.substring(0, msg.length()-18);
//                    //lv2.setSelection(arg2);
//                    btdialog.cancel();
//                    adrress.setText(mac);
//                    Log.i("TAG", "mac="+mac);
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private void findAvalibleDevice() {
//        // TODO Auto-generated method stub
//        //获取可配对蓝牙设备
//        Set<BluetoothDevice> device=bluetoothAdapter.getBondedDevices();
//
//        btList.clear();
//        if(bluetoothAdapter!=null&&bluetoothAdapter.isDiscovering()){
//            BtBoudAdapter.notifyDataSetChanged();
//        }
//        if(device.size()>0){
//            //存在已经配对过的蓝牙设备
//            for(Iterator<BluetoothDevice> it = device.iterator(); it.hasNext();){
//                BluetoothDevice btd=it.next();
//                btList.add(btd.getName()+'\n'+btd.getAddress());
//                BtBoudAdapter.notifyDataSetChanged();
//            }
//        }else{  //不存在已经配对过的蓝牙设备
//            btList.add("No can be matched to use bluetooth");
//            BtBoudAdapter.notifyDataSetChanged();
//        }
//
//    }


    public static View dialogView3;
    public static TextView tv_usb;
    public static List<String> usbList, usblist;
    public static ListView lv_usb;
    public static ArrayAdapter<String> adapter3;

    /*uSB连接 */
    private static void setUSB(MainActivity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        dialogView3 = inflater.inflate(R.layout.usb_link, null);
        tv_usb = (TextView) dialogView3.findViewById(R.id.textView1);
        lv_usb = (ListView) dialogView3.findViewById(R.id.listView1);


        usbList = PosPrinterDev.GetUsbPathNames(context);
        if (usbList == null) {
            usbList = new ArrayList<>();
        }
        usblist = usbList;
        tv_usb.setText("present connected Usb dev:" + usbList.size());
        adapter3 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, usbList);
        lv_usb.setAdapter(adapter3);


        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView3).create();
        dialog.show();

        setUsbLisener(dialog);

    }

    public static String usbDev = "";

    public static void setUsbLisener(final AlertDialog dialog) {

        lv_usb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                usbDev = usbList.get(i);
                tv_address.setText(usbDev);
                dialog.cancel();
//                Log.e("usbDev: ", usbDev);
            }
        });
    }


}
