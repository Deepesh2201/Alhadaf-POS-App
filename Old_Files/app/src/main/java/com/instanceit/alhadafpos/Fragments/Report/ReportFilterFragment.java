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
import androidx.core.content.ContextCompat;
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

public class ReportFilterFragment extends Fragment {

    MainActivity mainActivity;

    //views
    TextInputLayout txt_store, txt_sale_person, txt_item, txt_member, txt_from_date, txt_to_date;
    EditText edt_store, edt_sale_person, edt_item, edt_from_date, edt_to_date;
    TextView tv_generate_report;
    CheckBox check_free_item_detail;

    //Variables
    int isFreeItem = 0;
    String page = "";
    String Action;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_repor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);

    }


    private void Declaration(View view) {
        edt_store = view.findViewById(R.id.edt_store);
        edt_sale_person = view.findViewById(R.id.edt_sale_person);
        edt_item = view.findViewById(R.id.edt_item);

        edt_from_date = view.findViewById(R.id.edt_from_date);
        edt_to_date = view.findViewById(R.id.edt_to_date);
        tv_generate_report = view.findViewById(R.id.tv_generate_report);
        check_free_item_detail = view.findViewById(R.id.check_free_item_detail);
        txt_store = view.findViewById(R.id.txt_store);
        txt_sale_person = view.findViewById(R.id.txt_sale_person);
        txt_item = view.findViewById(R.id.txt_item);
        txt_member = view.findViewById(R.id.txt_member);
        txt_from_date = view.findViewById(R.id.txt_from_date);
        txt_to_date = view.findViewById(R.id.txt_to_date);
    }


    private void Initialization() {

        //<editor-fold desc="DYNAMIC LBL">
        try {
            txt_store.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_STORE_TITLE_SELECT_STORE).getLabel());
            txt_sale_person.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_SELECT_SALE_PERSON).getLabel());
            txt_item.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_ITEM_TITLE).getLabel());
            txt_member.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SELECT_PERSON).getLabel());
            txt_from_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_FLT_FROM_DATE).getLabel());
            txt_to_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_FLT_TO_DATE).getLabel());
            tv_generate_report.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_GENERATE_REPORT).getLabel());
            check_free_item_detail.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CHECK_FREE_ITEM).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>


        Bundle bundle = getArguments();
        try {
            if (bundle != null) {
                page = bundle.getString("page");
                if (page.equals(AppConstant.TXT_APPMENU_MEMBERSHIP_SALE_REPORT)) {
                    check_free_item_detail.setVisibility(View.GONE);
                    Action = "listmshipsalesummaryreportdata";
                } else {
                    check_free_item_detail.setVisibility(View.VISIBLE);
                    Action = "listitemsalesummaryreportdata";
                }

                if (bundle.getString("back_manage") != null) {
                    str_item_name = bundle.getString("str_item_name");
                    str_store_name = bundle.getString("str_store_name");
                    str_salePerson_Name = bundle.getString("str_salePerson_Name");

                    str_storeID = bundle.getString("storeid");
                    str_salePersoneID = bundle.getString("salepersonid");
                    fltItemId = bundle.getString("itemid");

                    edt_from_date.setText(bundle.getString("fromdate"));
                    edt_to_date.setText(bundle.getString("todate"));
                    isFreeItem = Integer.parseInt(bundle.getString("withfreeitem"));

                    edt_store.setText(str_store_name);
                    edt_sale_person.setText(str_salePerson_Name);
                    edt_item.setText(str_item_name);

                    if (isFreeItem == 1) {
                        check_free_item_detail.setChecked(true);
                    } else {
                        check_free_item_detail.setChecked(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        callApiGetStoreList();


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
                bundle.putString("storeid", str_storeID);
                bundle.putString("salepersonid", str_salePersoneID);
                bundle.putString("itemid", fltItemId);
                bundle.putString("fromdate", edt_from_date.getText().toString());
                bundle.putString("todate", edt_to_date.getText().toString());

                bundle.putString("str_item_name", str_item_name);
                bundle.putString("str_store_name", str_store_name);
                bundle.putString("str_salePerson_Name", str_salePerson_Name);
                bundle.putString("withfreeitem", "" + isFreeItem);

                bundle.putString("Action", Action);
                bundle.putString("page", page);
                Fragment fragment = new SummaryReporFragment();
                fragment.setArguments(bundle);
                mainActivity.addFragment(fragment);
            }
        });

        check_free_item_detail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    isFreeItem = 0;
                } else {
                    isFreeItem = 1;
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

    ArrayList<Model> storeArrayList;
    public SearchableSpinnerDialog storeDialog;
    String str_storeID = "", str_store_name = "";

    private void callApiGetStoreList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststore");

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
                                if (jsonObject.getInt("isstoredata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("storedata");

                                    storeArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    storeArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        storeArrayList.add(0, model);
                                    }

                                    if (str_store_name.isEmpty()) {
                                        edt_store.setText(storeArrayList.get(0).getName());
                                        str_storeID = storeArrayList.get(0).getId();
                                        str_store_name = storeArrayList.get(0).getName();

                                        str_salePerson_Name = "";
                                        str_salePersoneID = "";
                                        edt_sale_person.setText("");
                                    }

                                    salePersonArrayList = new ArrayList<>();
                                    callApiGetSalePersonList();
                                    storeDialog = new SearchableSpinnerDialog(mainActivity, storeArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_STORE_TITLE_SELECT_STORE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    storeDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_store.setText(model.getName());
                                            str_storeID = model.getId();
                                            str_store_name = model.getName();
                                        }
                                    });
                                }
                            }

                            edt_store.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        storeDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    ArrayList<Model> salePersonArrayList;
    public SearchableSpinnerDialog salePersonDialog;
    String str_salePersoneID = "", str_salePerson_Name = "";

    private void callApiGetSalePersonList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreportsaleperson");

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
                                if (jsonObject.getInt("issalepersondata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("salepersondata");

                                    salePersonArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    salePersonArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (isalloption == 1) {
                                        Model model = new Model();
                                        model.setId("%");
                                        model.setName("ALL");
                                        salePersonArrayList.add(0, model);
                                    }


                                    if (str_salePerson_Name.isEmpty()) {
                                        edt_sale_person.setText(salePersonArrayList.get(0).getName());
                                        str_salePersoneID = salePersonArrayList.get(0).getId();
                                        str_salePerson_Name = salePersonArrayList.get(0).getName();

                                        str_item_name = "";
                                        fltItemId = "";
                                        edt_item.setText("");
                                    }

                                    itemArrayList = new ArrayList<>();
                                    callApiGetItemList();

                                    salePersonDialog = new SearchableSpinnerDialog(mainActivity, salePersonArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_SELECT_SALE_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    salePersonDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_sale_person.setText(model.getName());
                                            str_salePersoneID = model.getId();
                                            str_salePerson_Name = model.getName();

                                        }
                                    });
                                }
                            }

                            edt_sale_person.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        salePersonDialog.showSpinerDialog();
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
        params.put("action", "listreportitems");

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
                                if (jsonObject.getInt("isreportitemsdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("reportitemsdata");

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


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.navParentItem(page);
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