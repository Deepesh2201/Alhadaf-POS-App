package com.instanceit.alhadafpos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //Views
    TextView tv_btn_signin;
    EditText edt_username, edt_password;
    CheckBox chk_showpass;

    //Variables
    String key, unqkey, uid, utypeid, personname, username, fullname, contact, email, adlogin, yearid, yearname, activeyearid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Declaration();
        Initialization();
    }


    private void Declaration() {
        tv_btn_signin = findViewById(R.id.tv_btn_signin);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        chk_showpass = findViewById(R.id.chk_showpass);
    }


    private void Initialization() {

        //<editor-fold desc="dynamic lbl">
        try {
            edt_username.setHint(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_HINT_USER_NAME).getLabel());
            edt_password.setHint(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_HINT_PASSWORD).getLabel());
            tv_btn_signin.setText(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_BTN_SIGN_IN).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        if (!SessionManagement.getBoolean(LoginActivity.this, AppConstant.ISLOGING, false)) {
            try {
                if (SessionManagement.getStringValue(LoginActivity.this, LabelMaster.SELECTED_LANGUAGE_ID, "").equals("")) {
                    Utility.LanguageSelectorDialog(LoginActivity.this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Utility.callApiGetLanguageLabels(LoginActivity.this, false);
        }

        edt_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view1, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    edt_password.clearFocus();
                    if (edt_username.getText().toString().trim().length() == 0) {
                        edt_username.setError(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_ERR_USER_NAME).getLabel());
                        edt_username.requestFocus();
                    } else if (edt_password.getText().toString().trim().length() == 0) {
                        edt_password.setError(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_ERR_PASSWORD).getLabel());
                        edt_password.requestFocus();
                    } else {
                        callLoginApi();
                    }
                    return true;
                } else
                    return false;
            }
        });


        tv_btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_username.getText().toString().trim().length() == 0) {
                    edt_username.requestFocus();
                    edt_username.setError(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_ERR_USER_NAME).getLabel());
                } else if (edt_password.getText().toString().trim().length() == 0) {
                    edt_password.requestFocus();
                    edt_password.setError(Utility.languageLabel(LoginActivity.this, LabelMaster.LBL_LOGIN_ERR_PASSWORD).getLabel());
                } else {
                    callLoginApi();
                }
            }
        });


        chk_showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }

        });
    }


    private void callLoginApi() {

        Map<String, String> map = new HashMap<>();
        map.put("action", "login");
        map.put("l_username", edt_username.getText().toString());
        map.put("l_password", edt_password.getText().toString());

//        Log.e("params", "callLoginApi: " + map.toString());

        VolleyUtils.makeVolleyRequest(LoginActivity.this, AppConstant.LOGIN_URL, map, 1, true, true, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    int status = jsonObject.getInt("status");

//                    Log.e("response_message", "onResponse: " + response);

                    if (status == 1) {

                        key = jsonObject.getString("guestkey");
                        unqkey = jsonObject.getString("guestunqkey");
                        AppConstant.ISS = jsonObject.getString("iss");
                        uid = jsonObject.getString("uid");
                        utypeid = jsonObject.getString("utypeid");
                        personname = jsonObject.getString("personname");
                        username = jsonObject.getString("username");
                        fullname = jsonObject.getString("fullname");
                        contact = jsonObject.getString("contact");
                        email = jsonObject.getString("email");
                        yearid = jsonObject.getString("yearid");
                        yearname = jsonObject.getString("yearname");
                        activeyearid = jsonObject.getString("activeyearid");

                        SaveData();

                    } else {
                        Utility.initErrorMessagePopupWindow(LoginActivity.this, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void SaveData() {

        SessionManagement.setBoolean(LoginActivity.this, AppConstant.ISLOGING, true);

        try {
            Utility.callApiGetLanguageLabels(LoginActivity.this, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SessionManagement.savePreferences(LoginActivity.this, AppConstant.KEY, key);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.UNQKEY, unqkey);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.UID, uid);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.UTYPEID, utypeid);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.PERSONNAME, personname);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.USERNAME, username);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.FULLNAME, fullname);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.CONTACT, contact);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.EMAIL, email);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.ADLOGIN, email);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.YEARID, yearid);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.YEARNAME, yearname);
        SessionManagement.savePreferences(LoginActivity.this, AppConstant.ACTIVEYEARID, activeyearid);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (SessionManagement.getBoolean(LoginActivity.this, AppConstant.ISLOGING, false)) {
                SessionManagement.setBoolean(LoginActivity.this, AppConstant.ISUPDATENOTNOW, false);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}