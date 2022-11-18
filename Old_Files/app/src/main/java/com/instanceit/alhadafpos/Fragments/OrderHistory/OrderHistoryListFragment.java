package com.instanceit.alhadafpos.Fragments.OrderHistory;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.OrderHistoryList;
import com.instanceit.alhadafpos.Entity.Storeorderdetailinfo;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryItemDetailAdapter;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryListAdapter;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryPaymentDetailAdapter;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class OrderHistoryListFragment extends Fragment {

    MainActivity mainActivity;

    //views
    RecyclerView rv_order_history;
    SwipeRefreshLayout swi_layout;
    TextView tv_nodata, tv_lbl_index, tv_lbl_tran_id, tv_lbl_order_no, tv_lbl_ord_date, tv_lbl_amount, tv_lbl_member, tv_lbl_ent_by, tv_lbl_more;
    EditText edt_search;
    ImageView iv_search;

    //    variables
    int PageNumber = 1;
    private boolean loadMore = false, isSwipe = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    String orderId = "";
    String fltfilter = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Declaration(view);
        Initialization();
        onBackPress(view);
    }


    private void Declaration(View view) {
        rv_order_history = view.findViewById(R.id.rv_order_history);
        swi_layout = view.findViewById(R.id.swi_layout);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        tv_lbl_index = view.findViewById(R.id.tv_lbl_index);
        tv_lbl_tran_id = view.findViewById(R.id.tv_lbl_tran_id);
        tv_lbl_order_no = view.findViewById(R.id.tv_lbl_order_no);
        tv_lbl_ord_date = view.findViewById(R.id.tv_lbl_ord_date);
        tv_lbl_amount = view.findViewById(R.id.tv_lbl_amount);
        tv_lbl_member = view.findViewById(R.id.tv_lbl_member);
        tv_lbl_ent_by = view.findViewById(R.id.tv_lbl_ent_by);
        tv_lbl_more = view.findViewById(R.id.tv_lbl_more);
        edt_search = view.findViewById(R.id.edt_search);
        iv_search = view.findViewById(R.id.iv_search);

        orderHistoryListArrayList = new ArrayList<>();
        updatedHistoryArrrayList = new ArrayList<>();

    }

    private void Initialization() {

        //<editor-fold desc="dynamic lbl">
        try {
            tv_lbl_index.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_INDEX).getLabel());
            tv_lbl_tran_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_TRAN_ID).getLabel());
            tv_lbl_order_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_NO).getLabel());
            tv_lbl_ord_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_DATE).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_AMOUNT).getLabel());
            tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MEMBER).getLabel());
            tv_lbl_ent_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ENTRY_BY).getLabel());
            tv_lbl_more.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MORE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        mainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_order_history.setLayoutManager(linearLayoutManager);

        PageNumber = 1;
        callApiOrderHistoryList();

        swi_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swi_layout.setRefreshing(false);
                        shuffleItems();
                    }
                }, 500);
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (String.valueOf(s).trim().equals("")) {

                    fltfilter = String.valueOf(s);
                    PageNumber = 1;
                    recyclerViewState = null;
                    orderHistoryListArrayList = new ArrayList<>();
                    updatedHistoryArrrayList = new ArrayList<>();

                    if (!isSwipe)
                        callApiOrderHistoryList();
                }

            }
        });


        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fltfilter = edt_search.getText().toString();
                PageNumber = 1;
                recyclerViewState = null;
                orderHistoryListArrayList = new ArrayList<>();
                updatedHistoryArrrayList = new ArrayList<>();
                callApiOrderHistoryList();
                Utility.hideKeyboardFrom(getContext(), edt_search);
            }
        });


        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    fltfilter = edt_search.getText().toString();
                    PageNumber = 1;
                    recyclerViewState = null;
                    orderHistoryListArrayList = new ArrayList<>();
                    updatedHistoryArrrayList = new ArrayList<>();
                    callApiOrderHistoryList();
                    Utility.hideKeyboardFrom(getContext(), edt_search);
                    return true;
                }
                return false;
            }
        });


        rv_order_history.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            callApiOrderHistoryList();
                        }
                    }
                }
            }
        });

    }

    private void shuffleItems() {
        isSwipe = true;
        edt_search.setText("");
        PageNumber = 1;
        fltfilter = "";
        recyclerViewState = null;
        orderHistoryListArrayList = new ArrayList<>();
        updatedHistoryArrrayList = new ArrayList<>();
        callApiOrderHistoryList();
    }

    ArrayList<OrderHistoryList> orderHistoryListArrayList;
    ArrayList<OrderHistoryList> updatedHistoryArrrayList;

    public void callApiOrderHistoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststoreorderhistory");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("nextpage", "" + PageNumber);
        params.put("fltsearch", fltfilter);

//        Log.e("params", "callApiOrderHistoryList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ORDER_URL, params, 2, false, false,
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

                                if (jsonObject.getInt("isstoreorderdata") == 1) {

                                    JSONArray OrderHistoryData = jsonObject.getJSONArray("storeorderdata");

                                    if (OrderHistoryData != null && OrderHistoryData.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<OrderHistoryList>>() {
                                        }.getType();
                                        orderHistoryListArrayList = gson.fromJson(OrderHistoryData.toString(), listtype);

                                        //Add arraylist
                                        updatedHistoryArrrayList.addAll(orderHistoryListArrayList);

                                        OrderHistoryListAdapter adepter = new OrderHistoryListAdapter(getActivity(), updatedHistoryArrrayList, new OrderHistoryListAdapter.OnclickListener() {
                                            @Override
                                            public void onClick(OrderHistoryList model) {
                                                orderId = model.getId();
                                                callApiOrderDetail();
                                            }
                                        });
                                        rv_order_history.setAdapter(adepter);
                                        adepter.notifyDataSetChanged();
                                        rv_order_history.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                        loadMore = true;
                                        rv_order_history.setVisibility(View.VISIBLE);
                                        tv_nodata.setVisibility(View.GONE);
                                    } else {
                                        rv_order_history.setVisibility(View.GONE);
                                        tv_nodata.setVisibility(View.VISIBLE);
                                        tv_nodata.setText(message);
                                    }
                                } else {
                                    rv_order_history.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
                                }
                            } else {
                                rv_order_history.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                tv_nodata.setText(message);
                            }
                            isSwipe = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //<editor-fold desc="Call order detail">

    ArrayList<OrderHistoryList> orderHistorydetailArrayList;

    public void callApiOrderDetail() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststoreorderdetail");
        params.put("orderid", orderId);
//        Log.e("callApiOrderDetail", "onResponse: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ORDER_URL, params, 2, true, true, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("callApiOrderDetail", "onResponse: " + response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");

                    if (status == 1) {

                        JSONArray jsonArray = jsonObject.getJSONArray("storeorderdata");
                        if (jsonArray != null && jsonArray.length() != 0) {

                            orderHistorydetailArrayList = new ArrayList<>();
                            Gson gson = new Gson();
                            Type listtype = new TypeToken<ArrayList<OrderHistoryList>>() {
                            }.getType();
                            orderHistorydetailArrayList = gson.fromJson(jsonArray.toString(), listtype);
                            OrderDetailDialog();

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

    Dialog orderDialog = null;
    TextView tv_t_qty, tv_total_payment, tv_t_price, tv_t_discount, tv_t_taxable, tv_t_vat, tv_t_amount, tv_member_name, tv_amount,
            tv_entry_by, tv_trs_id, tv_oder_date, tv_oder_note, tv_change_amount, tv_lbl_dlg_title, tv_lbl_amount_total, tv_lbl_entry_by,
            tv_lbl_orde_date, tv_lbl_order_note, tv_lbl_item_detail_title, tv_lbl_item, tv_lbl_type, tv_lbl_qty, tv_lbl_discount, tv_lbl_taxable,
            tv_lbl_vat, tv_lbl_amount_title, tv_lbl_total, tv_lbl_payment_detail_title, tv_lbl_payment_type, tv_lbl_change_amount, tv_lbl_total_amount, tv_lbl_issue_qty, tv_lbl_remainqty, tv_lbl_opration;
    ImageView iv_close;
    RecyclerView rv_item_detail, rv_payment;

    public void OrderDetailDialog() {

        if (orderDialog == null) {
            orderDialog = new Dialog(getContext());
            orderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            orderDialog.setContentView(R.layout.dialog_order_detail);
            Window window = orderDialog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            orderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            orderDialog.setCancelable(false);
            orderDialog.show();
        }

        TextView tv_lbl_member, tv_lbl_tran_id, tv_lbl_amount, tv_lbl_price;

        tv_member_name = orderDialog.findViewById(R.id.tv_member_name);
        tv_amount = orderDialog.findViewById(R.id.tv_amount);
        tv_entry_by = orderDialog.findViewById(R.id.tv_entry_by);
        tv_trs_id = orderDialog.findViewById(R.id.tv_trs_id);
        tv_oder_date = orderDialog.findViewById(R.id.tv_oder_date);
        tv_oder_note = orderDialog.findViewById(R.id.tv_oder_note);
        tv_t_qty = orderDialog.findViewById(R.id.tv_t_qty);
        tv_t_price = orderDialog.findViewById(R.id.tv_t_price);
        tv_t_discount = orderDialog.findViewById(R.id.tv_t_discount);
        tv_t_taxable = orderDialog.findViewById(R.id.tv_t_taxable);
        tv_t_vat = orderDialog.findViewById(R.id.tv_t_vat);
        tv_t_amount = orderDialog.findViewById(R.id.tv_t_amount);
        iv_close = orderDialog.findViewById(R.id.iv_close);
        tv_total_payment = orderDialog.findViewById(R.id.tv_total_payment);
        rv_item_detail = orderDialog.findViewById(R.id.rv_item_detail);
        rv_payment = orderDialog.findViewById(R.id.rv_payment);
        tv_change_amount = orderDialog.findViewById(R.id.tv_change_amount);
        tv_lbl_dlg_title = orderDialog.findViewById(R.id.tv_lbl_dlg_title);
        tv_lbl_member = orderDialog.findViewById(R.id.tv_lbl_member);
        tv_lbl_amount_total = orderDialog.findViewById(R.id.tv_lbl_amount_total);
        tv_lbl_entry_by = orderDialog.findViewById(R.id.tv_lbl_entry_by);
        tv_lbl_tran_id = orderDialog.findViewById(R.id.tv_lbl_tran_id);
        tv_lbl_orde_date = orderDialog.findViewById(R.id.tv_lbl_orde_date);
        tv_lbl_order_note = orderDialog.findViewById(R.id.tv_lbl_order_note);
        tv_lbl_item_detail_title = orderDialog.findViewById(R.id.tv_lbl_item_detail_title);
        tv_lbl_item = orderDialog.findViewById(R.id.tv_lbl_item);
        tv_lbl_type = orderDialog.findViewById(R.id.tv_lbl_type);
        tv_lbl_qty = orderDialog.findViewById(R.id.tv_lbl_qty);
        tv_lbl_discount = orderDialog.findViewById(R.id.tv_lbl_discount);
        tv_lbl_taxable = orderDialog.findViewById(R.id.tv_lbl_taxable);
        tv_lbl_vat = orderDialog.findViewById(R.id.tv_lbl_vat);
        tv_lbl_amount_title = orderDialog.findViewById(R.id.tv_lbl_amount_title);
        tv_lbl_total = orderDialog.findViewById(R.id.tv_lbl_total);
        tv_lbl_payment_detail_title = orderDialog.findViewById(R.id.tv_lbl_payment_detail_title);
        tv_lbl_payment_type = orderDialog.findViewById(R.id.tv_lbl_payment_type);
        tv_lbl_amount = orderDialog.findViewById(R.id.tv_lbl_amount);
        tv_lbl_change_amount = orderDialog.findViewById(R.id.tv_lbl_change_amount);
        tv_lbl_total_amount = orderDialog.findViewById(R.id.tv_lbl_total_amount);
        tv_lbl_price = orderDialog.findViewById(R.id.tv_lbl_price);
        tv_lbl_issue_qty = orderDialog.findViewById(R.id.tv_lbl_issue_qty);
        tv_lbl_remainqty = orderDialog.findViewById(R.id.tv_lbl_remainqty);
        tv_lbl_opration = orderDialog.findViewById(R.id.tv_lbl_opration);

        try {
            tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_MEMBER).getLabel());
            tv_lbl_amount_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_entry_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ENTRY_BY).getLabel());
            tv_lbl_tran_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_TRAN_ID).getLabel());
            tv_lbl_orde_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_DATE).getLabel());
            tv_lbl_order_note.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ORDER_NOTE).getLabel());
            tv_lbl_item_detail_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ITEM_DETAIL).getLabel());
            tv_lbl_item.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_ITEM).getLabel());
            tv_lbl_type.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_TYPE).getLabel());
            tv_lbl_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_QTY).getLabel());
            tv_lbl_discount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ITEM_DESCOUNT).getLabel());
            tv_lbl_taxable.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ITEM_TAXABLE).getLabel());
            tv_lbl_vat.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_VAT).getLabel());
            tv_lbl_amount_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT).getLabel());
            tv_lbl_payment_detail_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_PAYMENT_DETAIL_TIT).getLabel());
            tv_lbl_payment_type.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_PAYMENT_TYPE).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_change_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_CHANGE_AMOUNT).getLabel());
            tv_lbl_total_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT).getLabel());
            tv_lbl_price.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_PRICE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderDialog.dismiss();
                orderDialog = null;
            }
        });
        tv_lbl_issue_qty.setVisibility(View.GONE);
        tv_lbl_remainqty.setVisibility(View.GONE);
        tv_lbl_opration.setVisibility(View.GONE);

        orderDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    orderDialog.dismiss();
                    orderDialog = null;
                }
                return false;
            }
        });


        tv_lbl_dlg_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_TITLE).getLabel() + " -  " + orderHistorydetailArrayList.get(0).getOrderno());
        tv_member_name.setText(orderHistorydetailArrayList.get(0).getMembername());
        tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(orderHistorydetailArrayList.get(0).getTotalpaid())));
        tv_entry_by.setText(orderHistorydetailArrayList.get(0).getEntrypersonname() + " (" + orderHistorydetailArrayList.get(0).getEntrypersoncontact() + ")");
        tv_trs_id.setText(orderHistorydetailArrayList.get(0).getTransactionid());
        tv_oder_date.setText(orderHistorydetailArrayList.get(0).getOrderdate());
        tv_oder_note.setText(orderHistorydetailArrayList.get(0).getOrdernotes());
        tv_change_amount.setText(orderHistorydetailArrayList.get(0).getTotalchangeamount());

        rv_item_detail.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_payment.setLayoutManager(new LinearLayoutManager(getContext()));

        OrderHistoryItemDetailAdapter adapter = new OrderHistoryItemDetailAdapter(getContext(), orderHistorydetailArrayList.get(0).getStoreorderdetailinfo(), "orderHistory", 0, 0, new OrderHistoryItemDetailAdapter.OnclickListener() {
            @Override
            public void onClick(Storeorderdetailinfo model, int clickMange) {

            }
        });
        rv_item_detail.setAdapter(adapter);

        OrderHistoryPaymentDetailAdapter historyPaymentDetailAdapter = new OrderHistoryPaymentDetailAdapter(getActivity(), orderHistorydetailArrayList.get(0).getStoreorderpaymentdetailinfo());
        rv_payment.setAdapter(historyPaymentDetailAdapter);

        TotalItem();
    }

    private void TotalItem() {

        int qty = 0;
        double price = 0.0;
        double discount = 0.0;
        double taxable = 0.0;
        double vat = 0.0;
        double amount = 0.0;
        double total = 0.0;

        for (int i = 0; i < orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().size(); i++) {

            qty = qty + Integer.parseInt(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getQty());
            price = price + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getPrice());
            discount = discount + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getDiscountamt());
//            discount = discount + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getTotaldiscount());

            taxable = taxable + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getTaxable());
            vat = vat + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getIgsttaxamt());
            amount = amount + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderdetailinfo().get(i).getFinalprice());
        }

        if (tv_t_qty != null && tv_t_price != null && tv_t_discount != null && tv_t_taxable != null && tv_t_vat != null && tv_t_amount != null) {
            tv_t_qty.setText(String.valueOf(qty));
            tv_t_price.setText(String.valueOf(String.format("%.2f", price)));
            tv_t_discount.setText(String.valueOf(String.format("%.2f", discount)));
            tv_t_taxable.setText(String.valueOf(String.format("%.2f", taxable)));
            tv_t_vat.setText(String.valueOf(String.format("%.2f", vat)));
            tv_t_amount.setText(String.valueOf(String.format("%.2f", amount)));
        }

        for (int i = 0; i < orderHistorydetailArrayList.get(0).getStoreorderpaymentdetailinfo().size(); i++) {
            total = total + Double.parseDouble(orderHistorydetailArrayList.get(0).getStoreorderpaymentdetailinfo().get(i).getAmount());
        }
        if (tv_total_payment != null) {
            tv_total_payment.setText(String.format("%.2f", total));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.iv_store.setVisibility(View.VISIBLE);
        mainActivity.iv_search.setVisibility(View.GONE);
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_ORDER_HISTORY);
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