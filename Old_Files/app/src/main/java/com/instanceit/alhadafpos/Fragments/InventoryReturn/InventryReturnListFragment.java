package com.instanceit.alhadafpos.Fragments.InventoryReturn;

import static com.instanceit.alhadafpos.Utility.Utility.formatDate;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Inventory;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.Returnorderdetailinfo;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.InventoryReturn.Adapter.CollectInventoryItemAdapter;
import com.instanceit.alhadafpos.Fragments.InventoryReturn.Adapter.ReturnInventoryListAdapter;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class InventryReturnListFragment extends Fragment {

    MainActivity mainActivity;

    //    Views
    RecyclerView rv_inventery;
    TextView tv_nodata;
    SwipeRefreshLayout swi_layout;
    FloatingActionButton float_collect_inventory;
    FloatingActionButton float_filter;

    //    variables
    int PageNumber = 1;
    private boolean loadMore = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    String orderId = "";
    ArrayList<Inventory> orderHistoryListArrayList;
    ArrayList<Inventory> updatedHistoryArrrayList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventry_return_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Declaration(view);
        Initialization();
        onBackPress(view);
    }


    private void Declaration(View view) {
        rv_inventery = view.findViewById(R.id.rv_inventery);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        swi_layout = view.findViewById(R.id.swi_layout);
        float_collect_inventory = view.findViewById(R.id.float_collect_inventory);
        float_filter = view.findViewById(R.id.float_filter);
    }


    private void Initialization() {
        mainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_inventery.setLayoutManager(linearLayoutManager);

        PageNumber = 1;
        orderHistoryListArrayList = new ArrayList<>();
        updatedHistoryArrrayList = new ArrayList<>();
        callApiReturnInventoryList();

        swi_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swi_layout.setRefreshing(false);

                        PageNumber = 1;
                        fltmemberid = "%";
                        fltfromdate = "";
                        flttodate = "";
                        str_member = "";
                        orderHistoryListArrayList = new ArrayList<>();
                        updatedHistoryArrrayList = new ArrayList<>();
                        callApiReturnInventoryList();
                    }
                }, 500);
            }
        });


        rv_inventery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                    if (loadMore) {
                        loadMore = false;
                        if (showload == 1) {
                            PageNumber++;
                            callApiReturnInventoryList();
                        }
                    }
                }
            }
        });


        float_collect_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectInventoryDialog(true);
            }
        });

        float_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFilterDialog();
            }
        });
    }


    public void callApiReturnInventoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreturnstoreorderhistory");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("nextpage", "" + PageNumber);
        params.put("fltmemberid", fltmemberid);
        params.put("fltfromdate", fltfromdate);
        params.put("flttodate", flttodate);

//        Log.e("params", "callApiOrderHistoryList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.RETURN_ITEM_URL, params, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiOrderHistoryList: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            showload = jsonObject.getInt("loadmore");

                            if (status == 1) {

                                if (jsonObject.getInt("isreturnorderdata") == 1) {

                                    JSONArray OrderHistoryData = jsonObject.getJSONArray("returnorderdata");

                                    if (OrderHistoryData != null && OrderHistoryData.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<Inventory>>() {
                                        }.getType();
                                        orderHistoryListArrayList = gson.fromJson(OrderHistoryData.toString(), listtype);

                                        //Add arraylist
                                        updatedHistoryArrrayList.addAll(orderHistoryListArrayList);

                                        ReturnInventoryListAdapter adepter = new ReturnInventoryListAdapter(getActivity(), updatedHistoryArrrayList,
                                                new ReturnInventoryListAdapter.OnclickListener() {
                                                    @Override
                                                    public void onClick(Inventory model) {
                                                        orderId = model.getId();
                                                        callApiInventoryItemDetail();
                                                    }
                                                });
                                        rv_inventery.setAdapter(adepter);
                                        adepter.notifyDataSetChanged();
                                        rv_inventery.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                        loadMore = true;
                                        rv_inventery.setVisibility(View.VISIBLE);
                                        tv_nodata.setVisibility(View.GONE);
                                    } else {
                                        rv_inventery.setVisibility(View.GONE);
                                        tv_nodata.setVisibility(View.VISIBLE);
                                        tv_nodata.setText(message);
                                    }

                                } else {
                                    rv_inventery.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
                                }
                            } else {
                                rv_inventery.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                tv_nodata.setText(message);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //<editor-fold desc="Call Inventory Collected List">

    ArrayList<Inventory> orderHistorydetailArrayList;

    public void callApiInventoryItemDetail() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreturnorderdetail");
        params.put("orderid", orderId);
//        Log.e("callApiOrderDetail", "onResponse: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.RETURN_ITEM_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("callApiOrderDetail", "onResponse: " + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            if (status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("returnorderdata");
                                if (jsonArray != null && jsonArray.length() != 0) {

                                    orderHistorydetailArrayList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Inventory>>() {
                                    }.getType();
                                    orderHistorydetailArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    CollectInventoryDialog(false);

                                }
                            } else {
                                Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


    //<editor-fold desc="Inventory collect and list item - CollectInventoryDialog">

    BottomSheetDialog inventorycollectDialog = null;

    TextView tv_dlg_membername, tv_btn_dlg_collect, tv_sel_person, tv_sel_date, tv_dlg_nodata, tv_lbl_collect_inventory, tv_lbl_select_person, tv_lbl_select_date;
    ImageView iv_close_dlg;
    TextInputLayout txt_comment;
    RecyclerView rv_collect_item;
    LinearLayout ln_collect_main;
    EditText edt_comment;

    ArrayList<Returnorderdetailinfo> retrunItemArrayList;

    public void CollectInventoryDialog(boolean isCollect) {

        if (inventorycollectDialog != null && inventorycollectDialog.isShowing()) return;

        {
            inventorycollectDialog = new BottomSheetDialog(mainActivity);
            inventorycollectDialog.setContentView(R.layout.dialog_collect_inventory);
            FrameLayout bottomSheet = inventorycollectDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            inventorycollectDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            inventorycollectDialog.setCancelable(true);
            inventorycollectDialog.show();
        }

        tv_dlg_membername = inventorycollectDialog.findViewById(R.id.tv_dlg_membername);
        tv_btn_dlg_collect = inventorycollectDialog.findViewById(R.id.tv_btn_dlg_collect);
        iv_close_dlg = inventorycollectDialog.findViewById(R.id.iv_close_dlg);
        rv_collect_item = inventorycollectDialog.findViewById(R.id.rv_collect_item);
        ln_collect_main = inventorycollectDialog.findViewById(R.id.ln_collect_main);
        tv_sel_person = inventorycollectDialog.findViewById(R.id.tv_sel_person);
        tv_sel_date = inventorycollectDialog.findViewById(R.id.tv_sel_date);
        tv_dlg_nodata = inventorycollectDialog.findViewById(R.id.tv_dlg_nodata);
        tv_lbl_collect_inventory = inventorycollectDialog.findViewById(R.id.tv_lbl_collect_inventory);
        tv_lbl_select_person = inventorycollectDialog.findViewById(R.id.tv_lbl_select_person);
        tv_lbl_select_date = inventorycollectDialog.findViewById(R.id.tv_lbl_select_date);
        edt_comment = inventorycollectDialog.findViewById(R.id.edt_comment);
        txt_comment = inventorycollectDialog.findViewById(R.id.txt_comment);
        edt_comment.clearFocus();

        rv_collect_item.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            tv_lbl_collect_inventory.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_COLLECT_INVENTORY).getLabel());
            tv_lbl_select_person.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SELECT_PERSON).getLabel());
            tv_lbl_select_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_SELECT_DATE).getLabel());
            tv_btn_dlg_collect.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_COLLECT).getLabel());
            if (isCollect) {
                txt_comment.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_HINT_ENTER_COMMENT).getLabel());
            } else {
                txt_comment.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_HINT_COMMENT).getLabel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isCollect) {
            tv_btn_dlg_collect.setVisibility(View.VISIBLE);
            ln_collect_main.setVisibility(View.VISIBLE);
            edt_comment.clearFocus();
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String str_date = dateFormat.format(date);

                tv_sel_date.setText(str_date);

            } catch (Exception e) {
                e.printStackTrace();
            }

            callApiGetMemberList();

        } else {
            tv_btn_dlg_collect.setVisibility(View.GONE);
            ln_collect_main.setVisibility(View.GONE);
            edt_comment.clearFocus();
            edt_comment.setCursorVisible(false);
            edt_comment.setFocusableInTouchMode(false);
            edt_comment.setClickable(false);

            tv_dlg_membername.setText(orderHistorydetailArrayList.get(0).getMembername() + " | " + orderHistorydetailArrayList.get(0).getOfulldate());
            edt_comment.setText(orderHistorydetailArrayList.get(0).getComment());


            retrunItemArrayList = new ArrayList<>();
            CollectInventoryItemAdapter collectInventoryItemAdapter = new CollectInventoryItemAdapter(getContext(),
                    orderHistorydetailArrayList.get(0).getReturnorderdetailinfo(), false, null);
            rv_collect_item.setAdapter(collectInventoryItemAdapter);

        }


        tv_sel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        tv_btn_dlg_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (retrunItemArrayList != null && retrunItemArrayList.size() > 0) {
//                    Log.e("TAG", "onClick: " + new Gson().toJson(retrunItemArrayList));
                    callApiReturnItem();
                } else {
                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_ERR_SELECT_ITEM_FIRST).getLabel(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        iv_close_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventorycollectDialog.dismiss();
            }
        });


        inventorycollectDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    inventorycollectDialog.dismiss();
                }
                return false;
            }
        });
    }
    //</editor-fold>


    //<editor-fold desc="item to be collect API call">
    ArrayList<Returnorderdetailinfo> returnorderdetailinfoArrayList;

    public void callApiItemDetailPersonwise() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreturnableitem");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", str_memberID);
        params.put("date", tv_sel_date.getText().toString());
//        Log.e("callApiItemDetailPersonwise", "onResponse: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.RETURN_ITEM_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("callApiItemDetailPersonwise", "onResponse: " + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            if (status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("returnitemdata");
                                if (jsonArray != null && jsonArray.length() != 0) {

                                    returnorderdetailinfoArrayList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Returnorderdetailinfo>>() {
                                    }.getType();
                                    returnorderdetailinfoArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    retrunItemArrayList = new ArrayList<>();

                                    CollectInventoryItemAdapter collectInventoryItemAdapter = new CollectInventoryItemAdapter(getContext(),
                                            returnorderdetailinfoArrayList, true, new CollectInventoryItemAdapter.OnclickListener() {
                                        @Override
                                        public void onClick(ArrayList<Returnorderdetailinfo> returnorderdetailinfoArrayList) {

                                            retrunItemArrayList = new ArrayList<>();

                                            if (returnorderdetailinfoArrayList.size() > 0) {
                                                for (int i = 0; i < returnorderdetailinfoArrayList.size(); i++) {
                                                    if (returnorderdetailinfoArrayList.get(i).getIsitemadded() == 1) {
                                                        retrunItemArrayList.add(returnorderdetailinfoArrayList.get(i));
                                                    }
                                                }
                                            }
                                        }
                                    });

                                    tv_dlg_nodata.setVisibility(View.GONE);
                                    rv_collect_item.setVisibility(View.VISIBLE);
                                    rv_collect_item.setAdapter(collectInventoryItemAdapter);
                                    tv_dlg_membername.setText(tv_sel_person.getText().toString());
                                }
                            } else {
                                tv_dlg_nodata.setVisibility(View.VISIBLE);
                                rv_collect_item.setVisibility(View.GONE);
                                tv_dlg_nodata.setText(message);
                                Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


    //<editor-fold desc="Member list and date picker dialog">
    ArrayList<Model> memberArrayList;
    SearchableSpinnerDialog memberDialog;
    String str_memberID = "";

    private void callApiGetMemberList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listreturnordermember");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
//        Log.e("params", "callApiGetMemberList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.RETURN_ITEM_URL, params, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("ismemberdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("memberdata");

                                    memberArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();

                                    memberArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    tv_sel_person.setText(memberArrayList.get(0).getName());
                                    str_memberID = memberArrayList.get(0).getId();

                                    callApiItemDetailPersonwise();

                                    memberDialog = new SearchableSpinnerDialog(getActivity(), memberArrayList, Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SELECT_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);
                                    memberDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            tv_sel_person.setText(model.getName());
                                            str_memberID = model.getId();

                                            callApiItemDetailPersonwise();
                                        }
                                    });
                                }
                            }
                            tv_sel_person.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        memberDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public static String date = "";
    private String dateToStr = "";
    public static DatePickerDialog datePicker;

    public void showDatePickerDialog() {

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
            String selectedDate = tv_sel_date.getText().toString();
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

        datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                date = formatDate(year, monthOfYear, dayOfMonth);
                tv_sel_date.setText(date);

                callApiItemDetailPersonwise();
            }
        }, yy, mm, dd);

        datePicker.show();

        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0B2347"));
        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0B2347"));
    }
    //</editor-fold>


    public void callApiReturnItem() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "insertreturnitemdata");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", str_memberID);
        params.put("date", tv_sel_date.getText().toString());
        params.put("itemdata", "" + new Gson().toJson(retrunItemArrayList));
        params.put("comment", "" + edt_comment.getText().toString());
//        Log.e("params", "callApiReturnItem: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.RETURN_ITEM_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Log.e("TAG", "itemdata: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            Utility.initErrorMessagePopupWindow(getActivity(), message);

                            if (status == 1) {

                                try {
                                    inventorycollectDialog.dismiss();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    PageNumber = 1;
                                    orderHistoryListArrayList = new ArrayList<>();
                                    updatedHistoryArrrayList = new ArrayList<>();
                                    callApiReturnInventoryList();
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


    //<editor-fold desc="Filter dialog">
    BottomSheetDialog filterDialog = null;
    EditText edt_select_member, edt_from_date, edt_to_date;
    TextView tv_btn_reset, tv_btn_apply, tv_lbl_filter;
    ImageView iv_close_filter_dlg;
    String fltfromdate = "", flttodate = "", str_member = "";
    TextInputLayout txt_select_member, txt_from_date, txt_to_date;

    private void OpenFilterDialog() {

        if (filterDialog != null && filterDialog.isShowing()) return;

        {
            filterDialog = new BottomSheetDialog(mainActivity);
            filterDialog.setContentView(R.layout.dialog_filter_service_order);
            FrameLayout bottomSheet = filterDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            filterDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            filterDialog.setCancelable(true);
            filterDialog.show();
        }

        edt_select_member = filterDialog.findViewById(R.id.edt_select_member);
        edt_from_date = filterDialog.findViewById(R.id.edt_from_date);
        edt_to_date = filterDialog.findViewById(R.id.edt_to_date);
        tv_btn_reset = filterDialog.findViewById(R.id.tv_btn_reset);
        tv_btn_apply = filterDialog.findViewById(R.id.tv_btn_apply);
        iv_close_filter_dlg = filterDialog.findViewById(R.id.iv_close_filter_dlg);
        tv_lbl_filter = filterDialog.findViewById(R.id.tv_lbl_filter);
        txt_select_member = filterDialog.findViewById(R.id.txt_select_member);
        txt_from_date = filterDialog.findViewById(R.id.txt_from_date);
        txt_to_date = filterDialog.findViewById(R.id.txt_to_date);

        callApiGetFilterMemberList();

        if (!str_member.isEmpty() || !fltfromdate.isEmpty() || !flttodate.isEmpty()) {
            edt_select_member.setText(str_member);
            edt_to_date.setText(flttodate);
            edt_from_date.setText(fltfromdate);
        }

        try {
            txt_select_member.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel());
            txt_from_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_FROM_DATE).getLabel());
            txt_to_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_TO_DATE).getLabel());
            tv_btn_apply.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_CART_BTN_APPLY).getLabel());
            tv_lbl_filter.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_TITLE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        edt_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showDatePickerDialogNoMinDate(getContext(), edt_from_date);
            }
        });

        iv_close_filter_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.dismiss();
                filterDialog = null;
            }
        });

        edt_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showDatePickerDialogNoMinDate(getContext(), edt_to_date);
            }
        });

        tv_btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fltmemberid = "%";
                edt_from_date.setText("");
                edt_to_date.setText("");
                fltfromdate = edt_from_date.getText().toString();
                flttodate = edt_to_date.getText().toString();
                callApiGetMemberList();
                PageNumber = 1;
                orderHistoryListArrayList = new ArrayList<>();
                updatedHistoryArrrayList = new ArrayList<>();
                callApiReturnInventoryList();
                filterDialog.dismiss();
                filterDialog = null;
            }
        });

        tv_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_from_date.getText().toString().isEmpty() && !edt_to_date.getText().toString().isEmpty()) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date1 = sdf.parse(edt_from_date.getText().toString());
                        Date date2 = sdf.parse(edt_to_date.getText().toString());
                        if (date2.compareTo(date1) >= 0) {
                            fltfromdate = edt_from_date.getText().toString();
                            flttodate = edt_to_date.getText().toString();
                            PageNumber = 1;
                            orderHistoryListArrayList = new ArrayList<>();
                            updatedHistoryArrrayList = new ArrayList<>();
                            callApiReturnInventoryList();
                            filterDialog.dismiss();
                            filterDialog = null;
                        } else {
                            Toast.makeText(getContext(), Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_ERE_TODATE).getLabel(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    fltfromdate = edt_from_date.getText().toString();
                    flttodate = edt_to_date.getText().toString();
                    PageNumber = 1;
                    orderHistoryListArrayList = new ArrayList<>();
                    updatedHistoryArrrayList = new ArrayList<>();
                    callApiReturnInventoryList();
                    filterDialog.dismiss();
                    filterDialog = null;
                }
            }
        });

    }

    ArrayList<Model> memberFilterArrayList;
    public SearchableSpinnerDialog spinnerDialog;
    String fltmemberid = "";

    private void callApiGetFilterMemberList() {

        Map<String, String> params = new HashMap<>();
        params.put("action", "listmember");
        params.put("isall", "1");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MEMBER_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("ismemberdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("memberdata");

                                    memberFilterArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    memberFilterArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (edt_select_member.getText().toString().isEmpty()) {
                                        edt_select_member.setText(memberFilterArrayList.get(0).getName());
                                        fltmemberid = memberFilterArrayList.get(0).getId();
                                        str_member = memberFilterArrayList.get(0).getName();
                                    }

                                    spinnerDialog = new SearchableSpinnerDialog(mainActivity, memberFilterArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_select_member.setText(model.getName());
                                            fltmemberid = model.getId();
                                            str_member = model.getName();
                                        }
                                    });
                                }
                            }

                            edt_select_member.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        spinnerDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_INVENTARY_RETURN);
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