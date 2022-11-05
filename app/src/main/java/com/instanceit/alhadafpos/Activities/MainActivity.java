package com.instanceit.alhadafpos.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.Adapter.Interfaces.NavMenuItemClickInterface;
import com.instanceit.alhadafpos.Activities.Adapter.NavMenuChildAdapter;
import com.instanceit.alhadafpos.Activities.Adapter.NavMenuItemsAdapter;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.NavChild;
import com.instanceit.alhadafpos.Entity.UserRights;
import com.instanceit.alhadafpos.Fragments.AddMember.AddMemberFragment;
import com.instanceit.alhadafpos.Fragments.AddMember.MembershipListingFragment;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.Dashboard.DashboardFragment;
import com.instanceit.alhadafpos.Fragments.InventoryReturn.InventryReturnListFragment;
import com.instanceit.alhadafpos.Fragments.IssueServiceOrder.IssueServiceOrderFragment;
import com.instanceit.alhadafpos.Fragments.OrderHistory.OrderHistoryListFragment;
import com.instanceit.alhadafpos.Fragments.RangeRelase.AssignedRangeListFragment;
import com.instanceit.alhadafpos.Fragments.Report.ReportFilterFragment;
import com.instanceit.alhadafpos.Fragments.Report.SaleReporFragment;
import com.instanceit.alhadafpos.Fragments.Report.SaleReporListFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderListingFragment;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Receiver.ConnectionCheckReceiver;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.MultipartVolleyUtils;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.Validation;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import net.posprinter.posprinterface.IMyBinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mInstance;

    //views

    public ImageView iv_search, iv_store, iv_logout, iv_menu, iv_add_contact, iv_workflow, iv_language, iv_range_booking;
    public TextView tv_title_page;
    public EditText edt_search;

    public DrawerLayout drawer;
    public NavigationView navigationView;
    public ActionBarDrawerToggle toggle;
    public Toolbar toolbar;
    RecyclerView rv_menu_item;

    ArrayList<Model> memberArrayList;
    public SearchableSpinnerDialog memberDialog;

    static ConnectionCheckReceiver mNetworkReceiver;
    NavMenuItemsAdapter navMenuItemsAdapter;
    String member_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNetworkReceiver = new ConnectionCheckReceiver(this);
        registerNetworkBroadcastForNougat();

        mInstance = MainActivity.this;
        Declaration();

        if (Utility.isConnected(MainActivity.this))
            Intialization();

    }


    private void Declaration() {

        iv_store = findViewById(R.id.iv_store);
        iv_logout = findViewById(R.id.iv_logout);
        iv_search = findViewById(R.id.iv_search);
        edt_search = findViewById(R.id.edt_search);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        rv_menu_item = findViewById(R.id.rv_menu_item);
        iv_menu = findViewById(R.id.iv_menu);
        tv_title_page = findViewById(R.id.tv_title_page);
        iv_add_contact = findViewById(R.id.iv_add_contact);
        iv_workflow = findViewById(R.id.iv_workflow);
        iv_language = findViewById(R.id.iv_language);
        iv_range_booking = findViewById(R.id.iv_range_booking);

        edt_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view1, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    edt_search.clearFocus();
                    return true;
                } else
                    return false;
            }
        });
    }


    private void Intialization() {

        SessionManagement.setBoolean(MainActivity.this, AppConstant.ISSELECTEDPRINOP, false);

        if (!Utility.isConnected(MainActivity.this)) {
            dialogNoNetwork();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rv_menu_item.setLayoutManager(linearLayoutManager);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    View focusedView = getCurrentFocus();
                    if (focusedView != null) {
                        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    View focusedView = getCurrentFocus();
                    if (focusedView != null) {
                        inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        callApiGetUserRights();

        addFragment(new DashboardFragment());

        iv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(new DashboardFragment());
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initlogoutMessagePopupWindow(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_LOGOUT_MES).getLabel() + "?");
            }
        });

        iv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.LanguageSelectorDialog(MainActivity.this);
            }
        });

    }


    public void pageTitle(String title) {
        tv_title_page.setText(title);
    }


    private void createMenuList(ArrayList<UserRights> userRightsArrayList) {

        navMenuItemsAdapter = new NavMenuItemsAdapter(this, userRightsArrayList, new NavMenuItemClickInterface() {
            @Override
            public void MenuItemClick(String Txt) {

                getSupportActionBar().show();

                toggle.setHomeAsUpIndicator(R.drawable.ic_menu);

                switch (Txt) {

                    case AppConstant.TXT_APPMENU_STORE:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new DashboardFragment());
                        break;

                    case AppConstant.TXT_RANGE_RELEASE:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new AssignedRangeListFragment());
                        break;

                    case AppConstant.TXT_APPMENU_SERVICE_ORDER:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        edt_search.setVisibility(View.GONE);
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstant.ACTION, AppConstant.ACTION_LISTING);
                        Fragment fragment = new ServiceOrderListingFragment();
                        fragment.setArguments(bundle);
                        addFragment(fragment);
                        break;

                    case AppConstant.TXT_APPMENU_ISSUE_SERVICE_ORDER:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new IssueServiceOrderFragment());
                        break;

                    case AppConstant.TXT_APPMENU_STORE_ORDER:
                        if (SessionManagement.getStringValue(MainActivity.this, AppConstant.STORE_ID, "").trim().equals("")) {
                            Toast.makeText(MainActivity.this, "Please select your store first", Toast.LENGTH_SHORT).show();
                            iv_search.setVisibility(View.GONE);
                            iv_add_contact.setVisibility(View.GONE);
                            iv_workflow.setVisibility(View.GONE);
                            iv_range_booking.setVisibility(View.GONE);
                            addFragment(new DashboardFragment());
                        } else {
                            addFragment(new CreateOrderMainFragment());
                        }
                        break;

                    case AppConstant.TXT_APPMENU_ORDER_HISTORY:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new OrderHistoryListFragment());
                        break;

                    case AppConstant.TXT_APPMENU_MEMBERSHIP:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new MembershipListingFragment());
//                        OpenMemberSelectionDialog();
                        break;

                    case AppConstant.TXT_APPMENU_INVENTARY_RETURN:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new InventryReturnListFragment());
                        break;

                    case AppConstant.TXT_APPMENU_SALE_REPORT:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        addFragment(new SaleReporListFragment());
                        break;

                    case AppConstant.TXT_APPMENU_ITEM_SALE_REPORT:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("page", AppConstant.TXT_APPMENU_ITEM_SALE_REPORT);
                        Fragment fragment2 = new ReportFilterFragment();
                        fragment2.setArguments(bundle1);
                        addFragment(fragment2);
                        break;

                    case AppConstant.TXT_APPMENU_MEMBERSHIP_SALE_REPORT:
                        iv_search.setVisibility(View.GONE);
                        iv_add_contact.setVisibility(View.GONE);
                        iv_workflow.setVisibility(View.GONE);
                        iv_range_booking.setVisibility(View.GONE);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("page", AppConstant.TXT_APPMENU_MEMBERSHIP_SALE_REPORT);
                        Fragment fragment1 = new ReportFilterFragment();
                        fragment1.setArguments(bundle2);
                        addFragment(fragment1);
                        break;

                    case AppConstant.TXT_APPMENU_LOGOUT:
                        initlogoutMessagePopupWindow(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_DLG_MSG_EXIT_APP).getLabel() + " ?");
                        break;

                    default:
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
            }

        });
        rv_menu_item.setAdapter(navMenuItemsAdapter);
    }


//    //<editor-fold desc="member selection and create new member">
//
//    private Dialog selectMember;
//    EditText edt_select_member;
//
//    private void OpenMemberSelectionDialog() {
//        try {
//            if (selectMember != null && selectMember.isShowing())
//                return;
//
//            selectMember = new Dialog(MainActivity.this);
//
//            selectMember.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            selectMember.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            selectMember.setContentView(R.layout.dialog_select_member);
//            selectMember.setCancelable(false);
//
//            Button btnOk = selectMember.findViewById(R.id.btnyes);
//            edt_select_member = selectMember.findViewById(R.id.edt_select_member);
//            LinearLayout ln_add_new_member = selectMember.findViewById(R.id.ln_add_new_member);
//            ImageView iv_close = selectMember.findViewById(R.id.iv_close);
//            RelativeLayout rl_dialog_main = selectMember.findViewById(R.id.rl_dialog_main);
//
//            ln_add_new_member.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CreateNewMemberDialog();
//                    selectMember.dismiss();
//                    selectMember = null;
//                }
//            });
//
//            iv_close.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    selectMember.dismiss();
//                    selectMember = null;
//                }
//            });
//
//
//            callApiGetMemberList(edt_select_member);
//
//            btnOk.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    if (!edt_select_member.getText().toString().isEmpty()) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id", member_id);
////                        Fragment addmember = new AddMemberFragment();
//                        Fragment addmember = new MembershipListingFragment();
//                        addmember.setArguments(bundle);
//                        addFragment(addmember);
//                        selectMember.dismiss();
//                        selectMember = null;
//                    } else {
//                        Toast.makeText(MainActivity.this, "Select Member or Create new Member", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//            selectMember.setOnKeyListener(new Dialog.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                        selectMember.dismiss();
//                        selectMember = null;
//                    }
//                    return false;
//                }
//            });
//
//            selectMember.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    private void callApiGetMemberList(EditText editText) {
//
//        Map<String, String> params = new HashMap<>();
//        params.put("action", "listordermember");
//
//        VolleyUtils.makeVolleyRequest(MainActivity.this, AppConstant.MEMBERSHIP_ORDER_URL, params, 1, true, true,
//                new VolleyResponseListener() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String message = jsonObject.getString("message");
//                            int status = jsonObject.getInt("status");
//
//                            if (status == 1) {
//                                if (jsonObject.getInt("ismemberdata") == 1) {
//                                    JSONArray jsonArray = jsonObject.getJSONArray("memberdata");
//
//                                    memberArrayList = new ArrayList<>();
//
//                                    Gson gson = new Gson();
//                                    Type listtype = new TypeToken<ArrayList<Model>>() {
//                                    }.getType();
//
//                                    memberArrayList = gson.fromJson(jsonArray.toString(), listtype);
//
//                                    memberDialog = new SearchableSpinnerDialog(MainActivity.this, memberArrayList, "Select Member", R.style.DialogAnimations_SmileWindow);
//
//                                    memberDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
//                                        @Override
//                                        public void onClick(Model model, int i) {
//                                            editText.setText(model.getName());
//                                            member_id = model.getId();
//                                        }
//                                    });
//                                }
//                            }
//
//                            editText.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (status == 1) {
//                                        memberDialog.showSpinerDialog();
//                                    }
//                                }
//                            });
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//    }
//
//    private Dialog createMemberDilog;
//    EditText edt_first_name, edt_last_name, edt_password, edt_confirm_password, edt_email, edt_phone_no, edt_qatar_id_no, edt_qatar_expir_date,
//            edt_birth_date, edt_nationality_select, edt_address, edt_company_name, edt_select_user_type, edt_select_refrence_user, edt_middle_name, edt_passport_no, edt_passport_expir_date;
//    TextInputLayout tl_password;
//    ImageView ic_back_button;
//    TextView tv_lbl_become_member;
//    TextInputLayout txt_first_name, txt_middle_name, txt_last_name, txt_email, txt_phone_no, txt_select_user_type, txt_select_reference,
//            txt_national_id_no, txt_qatar_expir_date, txt_birth_date, txt_nationality_select, txt_address, txt_company, tl_confirm_password, txt_national_passport_no, txt_passport_expir_date;
//
//    private void CreateNewMemberDialog() {
//        try {
//            if (createMemberDilog != null && createMemberDilog.isShowing())
//                return;
//
//            createMemberDilog = new Dialog(MainActivity.this);
//
//            createMemberDilog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            createMemberDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            createMemberDilog.setContentView(R.layout.dialog_new_member);
//            Window window = createMemberDilog.getWindow();
//            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            createMemberDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
//            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            createMemberDilog.setCancelable(false);
//
//            Button btnOk = createMemberDilog.findViewById(R.id.btnyes);
//            // edt_select_member = createMemberDilog.findViewById(R.id.edt_select_member);
//            LinearLayout ln_add_new_member = createMemberDilog.findViewById(R.id.ln_add_new_member);
//            RelativeLayout rl_dialog_main = createMemberDilog.findViewById(R.id.rl_dialog_main);
//            Button btn_registration_submit = createMemberDilog.findViewById(R.id.btn_registration_submit);
//            edt_first_name = createMemberDilog.findViewById(R.id.edt_first_name);
//            edt_last_name = createMemberDilog.findViewById(R.id.edt_last_name);
//            edt_password = createMemberDilog.findViewById(R.id.edt_password);
//            edt_confirm_password = createMemberDilog.findViewById(R.id.edt_confirm_password);
//            edt_email = createMemberDilog.findViewById(R.id.edt_email);
//            edt_phone_no = createMemberDilog.findViewById(R.id.edt_phone_no);
//            edt_qatar_id_no = createMemberDilog.findViewById(R.id.edt_qatar_id_no);
//            edt_qatar_expir_date = createMemberDilog.findViewById(R.id.edt_qatar_expir_date);
//            edt_birth_date = createMemberDilog.findViewById(R.id.edt_birth_date);
//            edt_nationality_select = createMemberDilog.findViewById(R.id.edt_nationality_select);
//            edt_address = createMemberDilog.findViewById(R.id.edt_address);
//            edt_company_name = createMemberDilog.findViewById(R.id.edt_company_name);
//            tl_password = createMemberDilog.findViewById(R.id.tl_password);
//            ic_back_button = createMemberDilog.findViewById(R.id.ic_back_button);
//            edt_select_user_type = createMemberDilog.findViewById(R.id.edt_select_user_type);
//            edt_select_refrence_user = createMemberDilog.findViewById(R.id.edt_select_refrence_user);
//            tv_lbl_become_member = createMemberDilog.findViewById(R.id.tv_lbl_become_member);
//            txt_first_name = createMemberDilog.findViewById(R.id.txt_first_name);
//            txt_last_name = createMemberDilog.findViewById(R.id.txt_last_name);
//            txt_email = createMemberDilog.findViewById(R.id.txt_email);
//            txt_phone_no = createMemberDilog.findViewById(R.id.txt_phone_no);
//            txt_select_user_type = createMemberDilog.findViewById(R.id.txt_select_user_type);
//            txt_select_reference = createMemberDilog.findViewById(R.id.txt_select_reference);
//            txt_national_id_no = createMemberDilog.findViewById(R.id.txt_national_id_no);
//            txt_qatar_expir_date = createMemberDilog.findViewById(R.id.txt_qatar_expir_date);
//            txt_birth_date = createMemberDilog.findViewById(R.id.txt_birth_date);
//            txt_nationality_select = createMemberDilog.findViewById(R.id.txt_nationality_select);
//            txt_address = createMemberDilog.findViewById(R.id.txt_address);
//            txt_company = createMemberDilog.findViewById(R.id.txt_company);
//            tl_password = createMemberDilog.findViewById(R.id.tl_password);
//            tl_confirm_password = createMemberDilog.findViewById(R.id.tl_confirm_password);
//            edt_middle_name = createMemberDilog.findViewById(R.id.edt_middle_name);
//            txt_middle_name = createMemberDilog.findViewById(R.id.txt_middle_name);
//            txt_national_passport_no = createMemberDilog.findViewById(R.id.txt_national_passport_no);
//            txt_passport_expir_date = createMemberDilog.findViewById(R.id.txt_passport_expir_date);
//            edt_passport_no = createMemberDilog.findViewById(R.id.edt_qatar_passport_no);
//            edt_passport_expir_date = createMemberDilog.findViewById(R.id.edt_passport_expir_date);
//
//            try {
//                tv_lbl_become_member.setText(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_BECOME_MEMBER).getLabel());
//                txt_first_name.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_FIRST_NAME).getLabel() + "*");
//                txt_last_name.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_LBL_LAST_NAME).getLabel() + "*");
//                tl_password.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_PASSWORD).getLabel() + "*");
//                tl_confirm_password.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_CNF_PASSWORD).getLabel() + "*");
//                txt_email.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_EMAIL).getLabel() + "*");
//                txt_phone_no.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_CONTACT).getLabel() + "*");
//                txt_national_id_no.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_QATAR_ID).getLabel());
//                txt_qatar_expir_date.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_QATAR_ID_EXPIRY).getLabel());
//                txt_birth_date.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_DOB).getLabel() + "*");
//                txt_nationality_select.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_NATIONALITY).getLabel() + "*");
//                txt_address.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_ADDRESS).getLabel() + "*");
//                txt_company.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_COMPANY).getLabel() + "*");
//                txt_middle_name.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_MIDDLE_NAME).getLabel());
//                txt_national_passport_no.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_PASSPORT).getLabel());
//                txt_passport_expir_date.setHint(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_HINT_PASSPORT_EXPIRY).getLabel());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            edt_qatar_expir_date.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Utility.hideKeyboardFrom(MainActivity.this, edt_qatar_expir_date);
//                    Utility.showDatePickerDialog(MainActivity.this, edt_qatar_expir_date);
//                }
//            });
//
//            edt_passport_expir_date.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Utility.hideKeyboardFrom(MainActivity.this, edt_passport_expir_date);
//                    Utility.showDatePickerDialog(MainActivity.this, edt_passport_expir_date);
//                }
//            });
//
//            callApiGetUserTypeList();
//
//            callApiGetMemberList(edt_select_refrence_user);
//
//            edt_birth_date.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Utility.hideKeyboardFrom(MainActivity.this, edt_birth_date);
//                    Utility.showDatePickerDialogBirthdateDate(MainActivity.this, edt_birth_date);
//                }
//            });
//
//            ic_back_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    createMemberDilog.dismiss();
//                    createMemberDilog = null;
//                    OpenMemberSelectionDialog();
//                }
//            });
//
//            btn_registration_submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (edt_first_name.getText().toString().trim().equals("")) {
//                        edt_first_name.requestFocus();
//                        Utility.hideKeyboardFrom(MainActivity.this, edt_first_name);
//                        edt_first_name.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_FNAME).getLabel());
//                    } else if (edt_last_name.getText().toString().trim().equals("")) {
//                        edt_last_name.requestFocus();
//                        edt_last_name.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_LNAME).getLabel());
//                    } else if (edt_email.getText().toString().trim().equals("")) {
//                        edt_email.requestFocus();
//                        edt_email.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_EMAIL).getLabel());
//                    } else if (!Validation.isValidEmail(MainActivity.this, edt_email)) {
//                        edt_email.requestFocus();
//                        edt_email.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_VALID_EMAIL).getLabel());
//                    } else if (edt_phone_no.getText().toString().trim().equals("")) {
//                        edt_phone_no.requestFocus();
//                        edt_phone_no.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_CONTACT).getLabel());
//                    } else if (!Validation.isValidPhoneNumber(MainActivity.this, edt_phone_no)) {
//                        edt_phone_no.requestFocus();
//                        edt_phone_no.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_VALID_CONTACT).getLabel());
//                    } /*else if (edt_qatar_id_no.getText().toString().trim().equals("")) {
//                        edt_qatar_id_no.requestFocus();
//                        edt_qatar_id_no.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_QATAR_ID).getLabel());
//                    } else if (edt_qatar_expir_date.getText().toString().trim().equals("")) {
//                        edt_qatar_expir_date.requestFocus();
//                        edt_qatar_expir_date.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_SEL_DATE_QATAR_EXPIRE).getLabel());
//                    } */ else if (edt_birth_date.getText().toString().trim().equals("")) {
//                        edt_birth_date.requestFocus();
//                        edt_birth_date.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_SEL_DOB).getLabel());
//                    } else if (edt_nationality_select.getText().toString().trim().equals("")) {
//                        edt_nationality_select.requestFocus();
//                        edt_nationality_select.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_NATIONALITY).getLabel());
//                    } else if (tl_password.getVisibility() == View.VISIBLE) {
//                        if (edt_password.getText().toString().trim().equals("")) {
//                            edt_password.requestFocus();
//                            edt_password.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_PASSWORD).getLabel());
//                        } else if (edt_confirm_password.getText().toString().trim().equals("")) {
//                            edt_confirm_password.requestFocus();
//                            edt_confirm_password.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_CNF_PASSWORD).getLabel());
//                        } else if (!edt_confirm_password.getText().toString().equals(edt_password.getText().toString())) {
//                            edt_confirm_password.requestFocus();
//                            edt_confirm_password.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_SAME_PASSWORD).getLabel());
//                        } else {
//                            callApiRegistrationAPI();
//                        }
//                    } else {
//                        callApiRegistrationAPI();
//                    }
//                }
//            });
//
//            createMemberDilog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//                @Override
//                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
//                        createMemberDilog.dismiss();
//                        createMemberDilog = null;
//                        OpenMemberSelectionDialog();
//                    }
//                    return false;
//                }
//            });
//
//
//            createMemberDilog.show();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    ArrayList<Model> userTypeArrayList;
//    String user_type_id = "";
//    SearchableSpinnerDialog searchableSpinnerDialog;
//
//    private void callApiGetUserTypeList() {
//        Map<String, String> params = new HashMap<>();
//        params.put("action", "listmemberusertype");
//
//        VolleyUtils.makeVolleyRequest(MainActivity.this, AppConstant.MEMBER_URL, params, 1, false, false,
//                new VolleyResponseListener() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String message = jsonObject.getString("message");
//                            int status = jsonObject.getInt("status");
//
//                            if (status == 1) {
//                                if (jsonObject.getInt("isutypedata") == 1) {
//
//                                    JSONArray jsonArray = jsonObject.getJSONArray("utypedata");
//
//                                    userTypeArrayList = new ArrayList<>();
//
//                                    Gson gson = new Gson();
//                                    Type listtype = new TypeToken<ArrayList<Model>>() {
//                                    }.getType();
//
//                                    userTypeArrayList = gson.fromJson(jsonArray.toString(), listtype);
//
//                                    edt_select_user_type.setText(userTypeArrayList.get(0).getName());
//                                    user_type_id = userTypeArrayList.get(0).getId();
//
//
//                                    searchableSpinnerDialog = new SearchableSpinnerDialog(MainActivity.this, userTypeArrayList, "Select User Type", R.style.DialogAnimations_SmileWindow);
//                                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
//                                        @Override
//                                        public void onClick(Model model, int i) {
//                                            edt_select_user_type.setText(model.getName());
//                                            user_type_id = model.getId();
//                                        }
//                                    });
//                                }
//                            }
//
//                            edt_select_user_type.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (status == 1) {
//                                        searchableSpinnerDialog.showSpinerDialog();
//                                    }
//                                }
//                            });
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//    }
//    //</editor-fold>


    public void navParentItem(String title) {

        try {
            int parent_pos = 0;
            int child_pos = 0;
            UserRights userRights = new UserRights();
            NavChild navChild = new NavChild();
            Gson gson = new Gson();

            String json = SessionManagement.getStringValue(MainActivity.this, AppConstant.USERLIST, "");

            if (json != null) {

                Type type = new TypeToken<ArrayList<UserRights>>() {
                }.getType();

                ArrayList<UserRights> navParentArrayList = new ArrayList<>();
                ArrayList<NavChild> navChildArrayList = new ArrayList<>();

                navParentArrayList = gson.fromJson(json, type);

                userRights.setAppmenuname(title);

                if (navParentArrayList.contains(userRights)) {

                    parent_pos = navParentArrayList.indexOf(userRights);

                    String store_name = SessionManagement.getStringValue(MainActivity.this, AppConstant.STORE_NAME, "");
                    if (store_name.equals("")) {
                        pageTitle(navParentArrayList.get(parent_pos).getPagename());
                    } else {
                        pageTitle(navParentArrayList.get(parent_pos).getPagename() + "(" + store_name + ")");
                    }

                    NavMenuItemsAdapter.lastCheckedPos = parent_pos;
                    navMenuItemsAdapter.notifyDataSetChanged();

                } else {

                    for (int i = 0; i < navParentArrayList.size(); i++) {

                        if (navParentArrayList.get(i).getChild() != null) {

                            if (navParentArrayList.get(i).getChild().size() > 0) {

                                navChild.setAppmenuname(title);

                                if (navParentArrayList.get(i).getChild().contains(navChild)) {

                                    child_pos = navParentArrayList.get(i).getChild().indexOf(navChild);

                                    String store_name = SessionManagement.getStringValue(MainActivity.this, AppConstant.STORE_NAME, "");
                                    if (store_name.equals("")) {
                                        pageTitle(navParentArrayList.get(parent_pos).getPagename());
                                    } else {
                                        pageTitle(navParentArrayList.get(parent_pos).getPagename() + "(" + store_name + ")");
                                    }

                                    NavMenuItemsAdapter.lastCheckedPos = i;
                                    navMenuItemsAdapter.notifyDataSetChanged();

                                    if (navMenuItemsAdapter.adapter != null) {
                                        NavMenuChildAdapter.lastCheckedPosChild = child_pos;
                                        navMenuItemsAdapter.adapter.notifyDataSetChanged();
                                    }

                                    break;
                                }
                            }

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addFragment(Fragment fragment) {
        edt_search.setVisibility(View.GONE);
        iv_search.setImageResource(R.drawable.ic_search);
        try {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.content_frame, fragment).commitAllowingStateLoss();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //<editor-fold desc="Logout Dialog">
    private Dialog logoutapp;

    public void initlogoutMessagePopupWindow(String message) {
        try {
            // We need to get the instance of the LayoutInflater

            if (logoutapp != null && logoutapp.isShowing())
                return;
            logoutapp = new Dialog(MainActivity.this);

            logoutapp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            logoutapp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            logoutapp.setContentView(R.layout.dialog_exit_message_box);
            TextView dialogTitle = logoutapp.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(message);
            Button btnOk = logoutapp.findViewById(R.id.btnyes);
            Button btnno = logoutapp.findViewById(R.id.btnno);
            LinearLayout ln_dlg_main = logoutapp.findViewById(R.id.ln_dlg_main);
            ImageView iv_gif = logoutapp.findViewById(R.id.iv_gif);
            RelativeLayout rl_dialog_main = logoutapp.findViewById(R.id.rl_dialog_main);

            btnno.setText(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_DLG_LOGOUT_CANCLE).getLabel());
            btnOk.setText(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_DLG_LOGOUT).getLabel());

            iv_gif.setVisibility(View.GONE);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    logoutapp.dismiss();
                    Utility.maxDeviceLogout(MainActivity.this, message);
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    logoutapp.dismiss();
                }
            });

            logoutapp.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>


    //<editor-fold desc="No Network Dialog">
    public void dialog(boolean value, Context context) {
        if (value) {
            try {
                if (dilaogNoNetwork != null) {
                    recreate();
                    dilaogNoNetwork.dismiss();
                    dilaogNoNetwork = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogNoNetwork();
        }
    }


    Dialog dilaogNoNetwork = null;

    public void dialogNoNetwork() {

        if (dilaogNoNetwork == null) {

            dilaogNoNetwork = new Dialog(MainActivity.this);

            dilaogNoNetwork.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dilaogNoNetwork.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dilaogNoNetwork.setContentView(R.layout.dialog_error_message_box);
            dilaogNoNetwork.setCancelable(false);
            TextView dialogTitle = dilaogNoNetwork.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(Html.fromHtml(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_DLG_NO_INTERNET).getLabel()));
            dilaogNoNetwork.show();
        }

        Button btnOk = dilaogNoNetwork.findViewById(R.id.btnOk);
        btnOk.getBackground().setLevel(5);

        btnOk.setText(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_DLG_BTN_RETRY).getLabel());

        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Utility.isConnected(MainActivity.this)) {
                    recreate();
                    dilaogNoNetwork.dismiss();
                    dilaogNoNetwork = null;
                } else {
                    Toast.makeText(MainActivity.this, Utility.languageLabel(MainActivity.this, LabelMaster.LBL_DLG_MESS_RETRY).getLabel(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //</editor-fold>


    private void registerNetworkBroadcastForNougat() {
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    protected void unregisterNetworkChanges() {
        try {
            if (mNetworkReceiver != null) {
                unregisterReceiver(mNetworkReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



   /* //<editor-fold desc="Registration of member Api Call">
    private void callApiRegistrationAPI() {

        HashMap<String, String> map = new HashMap<>();
        map.put("action", "insertmemberdata");
        map.put("r_fname", edt_first_name.getText().toString());
        map.put("r_lname", edt_last_name.getText().toString());
        map.put("r_email", edt_email.getText().toString());
        map.put("r_mobile", edt_phone_no.getText().toString());
        map.put("r_qataridno", edt_qatar_id_no.getText().toString());
        map.put("r_qataridexpiry", edt_qatar_expir_date.getText().toString());
        map.put("r_dob", edt_birth_date.getText().toString());
        map.put("r_nationality", edt_nationality_select.getText().toString());
        map.put("r_address", edt_address.getText().toString());
        map.put("r_companyname", edt_company_name.getText().toString());
        map.put("r_password", edt_password.getText().toString());
        map.put("r_utypeid", user_type_id);
        map.put("r_refmemberid", member_id);
        map.put("r_mname", edt_middle_name.getText().toString());
        map.put("r_passportidno", edt_passport_no.getText().toString());
        map.put("r_passportidexpiry", edt_passport_expir_date.getText().toString());
        Log.e("para", "callApiRegistrationAPI: " + map);


        MultipartVolleyUtils.makeVolleyRequest(MainActivity.this, AppConstant.MEMBER_URL, map, null, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("callApiRegistrationAPI", "onResponse: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            if (status == 1) {

                                edt_first_name.setText("");
                                edt_last_name.setText("");
                                edt_email.setText("");
                                edt_phone_no.setText("");
                                edt_qatar_id_no.setText("");
                                edt_qatar_expir_date.setText("");
                                edt_birth_date.setText("");
                                edt_nationality_select.setText("");
                                edt_address.setText("");
                                edt_company_name.setText("");
                                edt_password.setText("");

                                member_id = jsonObject.getString("memberid");
                                initErrorMessagePopupWindow(MainActivity.this, message, status);

                            } else {
                                initErrorMessagePopupWindow(MainActivity.this, message, status);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
    //</editor-fold>


    Dialog nertworkErrorpp = null;

    public void initErrorMessagePopupWindow(final Activity activity, String message, int status) {
        try {

            // We need to get the instance of the LayoutInflater

            if (nertworkErrorpp == null) {
                nertworkErrorpp = new Dialog(activity);
                nertworkErrorpp.requestWindowFeature(Window.FEATURE_NO_TITLE);
                nertworkErrorpp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                nertworkErrorpp.setContentView(R.layout.dialog_error_message_box);
                nertworkErrorpp.setCancelable(false);
                nertworkErrorpp.show();
            }

            TextView dialogTitle = nertworkErrorpp.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(Html.fromHtml(message));

            Button btnOk = nertworkErrorpp.findViewById(R.id.btnOk);

            btnOk.getBackground().setLevel(5);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (status == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", member_id);
                        Fragment addmember = new AddMemberFragment();
                        addmember.setArguments(bundle);
                        addFragment(addmember);

                        try {
                            createMemberDilog.dismiss();
                            createMemberDilog = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            selectMember.dismiss();
                            selectMember = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    nertworkErrorpp.dismiss();
                    nertworkErrorpp = null;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    JSONArray jsonArray;
    ArrayList<UserRights> user_right_List;

    public void callApiGetUserRights() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "userrights");
        params.put("menutypeid", "3");

//        Log.e("tag", "callApiGetUserRights: " + params);

        VolleyUtils.makeVolleyRequest(MainActivity.this, AppConstant.USERRIGHTS_URL, params, 2, false, false, new VolleyResponseListener() {

            @Override
            public void onResponse(String response) {
//                Log.e("tag", "callApiGetUserRights: " + response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.optString("message");
                    int status = jsonObject.optInt("status");

                    if (status == 1) {

                        user_right_List = new ArrayList<>();

                        if (jsonObject.has("parent")) {

                            jsonArray = jsonObject.getJSONArray("parent");

                            if (jsonArray != null && jsonArray.length() > 0) {

                                user_right_List = new ArrayList<>();
                                Gson gson = new Gson();
                                Type listType = new TypeToken<ArrayList<UserRights>>() {
                                }.getType();

                                user_right_List = gson.fromJson(jsonArray.toString(), listType);
                                String userlist = gson.toJson(user_right_List);

                                SessionManagement.savePreferences(MainActivity.this, AppConstant.USERLIST, userlist);
//                                Log.e("TAG", "onResponse: " + new Gson().toJson(user_right_List));
//                                Log.e("user", "onResponse: " + SessionManagement.getStringValue(MainActivity.this, AppConstant.USERNAME, ""));
                                createMenuList(user_right_List);
                            }
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == PERMISSION_SETTING) {
//            if (!checkRequiredPermissions(MainActivity.this)) {
//                requestNecessaryPermissions();
//            }
//        }

//        List<Fragment> listOfFragments = getSupportFragmentManager().getFragments();
//        if (tapTrick != 0 && tapTrick == 1) { // tapTrick == 1 is for scan ticket
//            for (Fragment fragment : listOfFragments) {
//                if (fragment instanceof DashboardFragment) {
//                    fragment.onActivityResult(requestCode, resultCode, data);
//                }
//            }
//        } else if (tapTrick == 2) { // tapTrick == 1 is for scan weight scale
//            for (Fragment fragment : listOfFragments) {
//                if (fragment instanceof WeightScaleFragment) {
//                    fragment.onActivityResult(requestCode, resultCode, data);
//                }
//            }
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        unregisterNetworkChanges();
        super.onDestroy();
    }

}