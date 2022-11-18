package com.instanceit.alhadafpos.Fragments.Report;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaleReporListFragment extends Fragment {

    MainActivity mainActivity;

    //views
    TextInputLayout txt_category, txt_sub_category, txt_item, txt_member, txt_payment_type, txt_from_date, txt_to_date;
    EditText edt_category, edt_sub_category, edt_item, edt_member, edt_from_date, edt_to_date, edt_payment_type;
    TextView tv_generate_report;
    CheckBox check_item_detail, check_full_detail;

    //Variables
    int isFullDetail = 0, isItemDetail = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_list_repor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);

    }


    private void Declaration(View view) {
        edt_category = view.findViewById(R.id.edt_category);
        edt_sub_category = view.findViewById(R.id.edt_sub_category);
        edt_item = view.findViewById(R.id.edt_item);
        edt_member = view.findViewById(R.id.edt_member);
        edt_from_date = view.findViewById(R.id.edt_from_date);
        edt_to_date = view.findViewById(R.id.edt_to_date);
        tv_generate_report = view.findViewById(R.id.tv_generate_report);
        check_item_detail = view.findViewById(R.id.check_item_detail);
        check_full_detail = view.findViewById(R.id.check_full_detail);
        edt_payment_type = view.findViewById(R.id.edt_payment_type);
        txt_category = view.findViewById(R.id.txt_category);
        txt_sub_category = view.findViewById(R.id.txt_sub_category);
        txt_item = view.findViewById(R.id.txt_item);
        txt_member = view.findViewById(R.id.txt_member);
        txt_payment_type = view.findViewById(R.id.txt_payment_type);
        txt_from_date = view.findViewById(R.id.txt_from_date);
        txt_to_date = view.findViewById(R.id.txt_to_date);
    }


    private void Initialization() {

        //<editor-fold desc="DYNAMIC LBL">
        try {
            txt_category.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_CATEGORY_TITLE).getLabel());
            txt_sub_category.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SUB_CATEGORY_TITLE).getLabel());
            txt_item.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_ITEM_TITLE).getLabel());
            txt_member.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SELECT_PERSON).getLabel());
            txt_from_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_FLT_FROM_DATE).getLabel());
            txt_to_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_FLT_TO_DATE).getLabel());
            txt_payment_type.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_SELECT_PAYMENT_TYPE).getLabel());
            tv_generate_report.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_GENERATE_REPORT).getLabel());
            check_item_detail.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CHECK_ITEM_DETAIL).getLabel());
            check_full_detail.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CHECK_FULL_DETAILS).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>


        Bundle bundle = getArguments();
        if (bundle != null) {
            str_category = bundle.getString("str_category");
            str_subCategory = bundle.getString("str_subCategory");
            str_item_name = bundle.getString("str_item_name");
            str_member = bundle.getString("str_member");
            edt_from_date.setText(bundle.getString("fromdate"));
            edt_to_date.setText(bundle.getString("todate"));

            fltItemId = bundle.getString("itemid");
            fltSubCatId = bundle.getString("subcategoryid");
            fltcetid = bundle.getString("categoryid");
            fltmemberid = bundle.getString("memberid");
            isFullDetail = Integer.parseInt(bundle.getString("withfulldetail"));
            isItemDetail = Integer.parseInt(bundle.getString("withitemdetail"));

            fltPaymentTypeId = bundle.getString("fltPaymentTypeId");
            str_PaymentType_name = bundle.getString("str_PaymentType_name");

            edt_category.setText(str_category);
            edt_sub_category.setText(str_subCategory);
            edt_member.setText(str_member);
            edt_item.setText(str_item_name);
            edt_payment_type.setText(str_PaymentType_name);

            if (isFullDetail == 1) {
                check_full_detail.setChecked(true);
            } else {
                check_full_detail.setChecked(false);
            }

            if (isItemDetail == 1) {
                check_item_detail.setChecked(true);
            } else {
                check_item_detail.setChecked(false);
            }
        }

        callApiGetCategoryList();
        callApiGetMemberList();
        callApiGetPaymentTypeList();

        edt_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateSelectDialog(edt_to_date);
            }
        });


        edt_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateSelectDialog(edt_from_date);
            }
        });

        tv_generate_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryid", fltcetid);
                bundle.putString("subcategoryid", fltSubCatId);
                bundle.putString("itemid", fltItemId);
                bundle.putString("memberid", fltmemberid);
                bundle.putString("fromdate", edt_from_date.getText().toString());
                bundle.putString("todate", edt_to_date.getText().toString());

                bundle.putString("withitemdetail", "" + isItemDetail);
                bundle.putString("withfulldetail", "" + isFullDetail);

                bundle.putString("str_category", str_category);
                bundle.putString("str_subCategory", str_subCategory);
                bundle.putString("str_item_name", str_item_name);
                bundle.putString("str_member", str_member);

                bundle.putString("fltPaymentTypeId", fltPaymentTypeId);
                bundle.putString("str_PaymentType_name", str_PaymentType_name);

                Fragment fragment = new SaleReporFragment();
                fragment.setArguments(bundle);
                mainActivity.addFragment(fragment);
            }
        });

        check_full_detail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    isFullDetail = 0;
                } else {
                    isFullDetail = 1;
                }
            }
        });

        check_item_detail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    isItemDetail = 0;
                } else {
                    isItemDetail = 1;
                }
            }
        });

    }

    DatePicker datePicker;
    TextView tv_cancel, tv_ok;
    String DeliveryDate = "";

    private void getDateSelectDialog(EditText edtText) {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_datepicker);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        final int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        dialog.setCancelable(false);
        dialog.show();


        datePicker = dialog.findViewById(R.id.datePicker);
        tv_cancel = dialog.findViewById(R.id.tv_cancel);
        tv_ok = dialog.findViewById(R.id.tv_ok);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                String dayofmonth = "";

                int mon = (datePicker.getMonth() + 1);
                String month = "";
                if (day < 10) {

                    dayofmonth = "0" + day;
                } else {
                    dayofmonth = String.valueOf(day);
                }
                if (mon < 10) {

                    month = "0" + mon;
                } else {
                    month = String.valueOf(mon);
                }
                DeliveryDate = datePicker.getYear() + "-" + month + "-" + dayofmonth;
                getTimePickerDialog(edtText);
                dialog.dismiss();
            }
        });


    }

    int hour, minutes;

    private void getTimePickerDialog(EditText edtText) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_timepicker);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        final int height = ViewGroup.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setLayout(width, height);
        dialog.setCancelable(false);
        dialog.show();

        final TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        TextView tv_ok = dialog.findViewById(R.id.tv_ok);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);

//        timePicker.setBackgroundColor(ContextCompat.getColor(getContext(), ThemeUtil.getThemeList().get(mainActivity.mTheme).getPrimaryColor()));

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        timePicker.setIs24HourView(true);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    hour = timePicker.getHour();
                    minutes = timePicker.getMinute();

                    String min = "";
                    if (minutes < 10)
                        min = "0" + minutes;
                    else
                        min = String.valueOf(minutes);

                    String Hour = "";
                    if (hour < 10) {
                        Hour = "0" + hour;
                    } else {
                        Hour = String.valueOf(hour);
                    }

                    //hh:mm:ss
                    String aTime = Hour + ":" + min + ":00";

                    edtText.setText(DeliveryDate + "  " + aTime);
                    dialog.dismiss();
                }
            }
        });
    }


    ArrayList<Model> catArrayList;
    public SearchableSpinnerDialog cetDialog;
    String fltcetid = "", str_category = "";

    private void callApiGetCategoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreportcategory");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.REPORT_URL, params, 1, true, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            int isalloption = jsonObject.getInt("isalloption");

                            if (status == 1) {
                                if (jsonObject.getInt("iscategorydata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("categorydata");

                                    catArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    catArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        catArrayList.add(0, model);
                                    }

                                    if (str_category.isEmpty()) {
                                        edt_category.setText(catArrayList.get(0).getName());
                                        fltcetid = catArrayList.get(0).getId();
                                        str_category = catArrayList.get(0).getName();

                                        str_subCategory = "";
                                        fltSubCatId = "";
                                        edt_sub_category.setText("");
                                    }

                                    subCatArrayList = new ArrayList<>();
                                    callApiGetSubCategoryList();
                                    cetDialog = new SearchableSpinnerDialog(mainActivity, catArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_CATEGORY_TITLE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    cetDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_category.setText(model.getName());
                                            fltcetid = model.getId();
                                            str_category = model.getName();

                                            fltSubCatId = "";
                                            str_subCategory = "";
                                            edt_sub_category.setText("");
                                            subCatArrayList = new ArrayList<>();
                                            callApiGetSubCategoryList();
                                        }
                                    });
                                }
                            }

                            edt_category.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        cetDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    ArrayList<Model> subCatArrayList;
    public SearchableSpinnerDialog subCetDialog;
    String fltSubCatId = "", str_subCategory = "";

    private void callApiGetSubCategoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreportsubcategory");
        params.put("categoryid", fltcetid);

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.REPORT_URL, params, 1, true, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            int isalloption = jsonObject.getInt("isalloption");

                            if (status == 1) {
                                if (jsonObject.getInt("issubcategorydata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("subcategorydata");

                                    subCatArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    subCatArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        subCatArrayList.add(0, model);
                                    }

                                    if (str_subCategory.isEmpty()) {
                                        edt_sub_category.setText(subCatArrayList.get(0).getName());
                                        fltSubCatId = subCatArrayList.get(0).getId();
                                        str_subCategory = subCatArrayList.get(0).getName();

                                        str_item_name = "";
                                        fltItemId = "";
                                        edt_item.setText("");
                                    }

                                    itemArrayList = new ArrayList<>();
                                    callApiGetItemList();

                                    subCetDialog = new SearchableSpinnerDialog(mainActivity, subCatArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SUB_CATEGORY_TITLE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    subCetDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_sub_category.setText(model.getName());
                                            fltSubCatId = model.getId();
                                            str_subCategory = model.getName();

                                            str_item_name = "";
                                            fltItemId = "";
                                            edt_item.setText("");
                                            itemArrayList = new ArrayList<>();
                                            callApiGetItemList();

                                        }
                                    });
                                }
                            }

                            edt_sub_category.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        subCetDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    ArrayList<Model> itemArrayList;
    public SearchableSpinnerDialog itemDialog;
    String fltItemId = "", str_item_name = "";

    private void callApiGetItemList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreportitem");
        params.put("categoryid", fltcetid);
        params.put("subcategoryid", fltSubCatId);

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.REPORT_URL, params, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetItemList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            int isalloption = jsonObject.getInt("isalloption");

                            if (status == 1) {
                                if (jsonObject.getInt("isitemdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("itemdata");

                                    itemArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    itemArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        itemArrayList.add(0, model);
                                    }

                                    if (str_item_name.isEmpty()) {
                                        edt_item.setText(itemArrayList.get(0).getName());
                                        fltItemId = itemArrayList.get(0).getId();
                                        str_item_name = itemArrayList.get(0).getName();
                                    }

                                    itemDialog = new SearchableSpinnerDialog(mainActivity, itemArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_ITEM_TITLE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    itemDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_item.setText(model.getName());
                                            fltItemId = model.getId();
                                            str_item_name = model.getName();
                                        }
                                    });
                                }
                            } else {
                                Utility.hideProgressDialoug();
                            }

                            edt_item.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        itemDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    ArrayList<Model> memberArrayList;
    public SearchableSpinnerDialog memberDialog;
    String fltmemberid = "", str_member = "";

    private void callApiGetMemberList() {

        Map<String, String> params = new HashMap<>();
        params.put("action", "listreportmember");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.REPORT_URL, params, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            int isalloption = jsonObject.getInt("isalloption");

                            if (status == 1) {
                                if (jsonObject.getInt("ismemberdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("memberdata");

                                    memberArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    memberArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        memberArrayList.add(0, model);
                                    }

                                    if (str_member.isEmpty()) {
                                        edt_member.setText(memberArrayList.get(0).getName());
                                        fltmemberid = memberArrayList.get(0).getId();
                                        str_member = memberArrayList.get(0).getName();
                                    }

                                    memberDialog = new SearchableSpinnerDialog(mainActivity, memberArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    memberDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_member.setText(model.getName());
                                            fltmemberid = model.getId();
                                            str_member = model.getName();
                                        }
                                    });
                                }
                            }

                            edt_member.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        memberDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    ArrayList<Model> paymentTypeArrayList;
    public SearchableSpinnerDialog paymentTypeDialog;
    String fltPaymentTypeId = "", str_PaymentType_name = "";

    private void callApiGetPaymentTypeList() {

        Map<String, String> params = new HashMap<>();
        params.put("action", "listreportpaymenttype");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.REPORT_URL, params, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetPaymentTypeList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");
                            int isalloption = jsonObject.getInt("isalloption");

                            if (status == 1) {
                                if (jsonObject.getInt("ispaymenttypedata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("paymenttypedata");

                                    paymentTypeArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    paymentTypeArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        paymentTypeArrayList.add(0, model);
                                    }

                                    if (str_PaymentType_name.isEmpty()) {
                                        edt_payment_type.setText(paymentTypeArrayList.get(0).getName());
                                        fltPaymentTypeId = paymentTypeArrayList.get(0).getId();
                                        str_PaymentType_name = paymentTypeArrayList.get(0).getName();
                                    }

                                    paymentTypeDialog = new SearchableSpinnerDialog(mainActivity, paymentTypeArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_SELECT_PAYMENT_TYPE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    paymentTypeDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_payment_type.setText(model.getName());
                                            fltPaymentTypeId = model.getId();
                                            str_PaymentType_name = model.getName();
                                        }
                                    });
                                }
                            }

                            edt_payment_type.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        paymentTypeDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_SALE_REPORT);
    }

    private void onBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    mainActivity.addFragment(new CreateOrderMainFragment());
                    return true;
                }
                return false;
            }
        });
    }


}