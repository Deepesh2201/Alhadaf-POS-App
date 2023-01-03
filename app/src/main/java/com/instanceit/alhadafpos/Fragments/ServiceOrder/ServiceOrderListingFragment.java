package com.instanceit.alhadafpos.Fragments.ServiceOrder;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.instanceit.alhadafpos.Utility.Utility.ISCONNECT;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.MyCart;
import com.instanceit.alhadafpos.Entity.NavChild;
import com.instanceit.alhadafpos.Entity.OrderHistoryList;
import com.instanceit.alhadafpos.Entity.PaymentModel;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Entity.ServiceOrderDetail;
import com.instanceit.alhadafpos.Entity.Storeorderdetailinfo;
import com.instanceit.alhadafpos.Entity.Storeorderpaymentdetailinfo;
import com.instanceit.alhadafpos.Entity.UserRights;
import com.instanceit.alhadafpos.Fragments.AddMember.OpenPDFFragment;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryItemDetailAdapter;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryListAdapter;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryPaymentDetailAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.BillItemAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.BillPayTypeAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.HistoryBillPayTypeAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.HistoryOrderItemAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.ServiceOrderListAdapter;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.TaskCallback;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.BitmapProcess;
import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterPos80;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceOrderListingFragment extends Fragment {

    MainActivity mainActivity;

    //Views
    RecyclerView rv_service_order_listing;
    FloatingActionButton btn_add_service_order, btn_filter;
    SwipeRefreshLayout swi_layout;
    TextView tv_nodata, tv_lbl_status, tv_lbl_tran_id, tv_lbl_order_no, tv_lbl_ord_date, tv_lbl_amount, tv_lbl_member, tv_lbl_ent_by, tv_lbl_more, tv_lbl_opration;

    EditText edt_search;
    ImageView iv_search;
    String fltfilter = "";

    //bill print views
    FrameLayout frameLayout_bill, framelayout_list;
    TextView tv_cust_name, tv_bill_Remaining, tv_cust_mobile, tv_salepersonname,tv_netpayableamt,tv_totalbilldiscount,tv_prt_redeeamt, tv_date, tv_bill_taxbale, tv_bill_sgst, tv_bill_cgst, tv_bill_total, tv_bill_discount, tv_companyname,
            tv_gst, tv_fssaino, tv_address, tv_orderno, tv_cashamt, tv_ordertype, tv_header_paytype, tv_parentcompany, tv_order_qty, tv_store_name, tv_ord_no;
    RecyclerView rv_item_bill, rv_bill_paytype;
    View view1, view2;
    LinearLayout ll_cash, ll_ordertype, ll_changeamt, ln_payment_details;
    ImageView iv_logo, iv_prt_companylogo;
    LinearLayout ll_taxsummary18, ll_taxsummary0, ll_taxsummary5, ll_taxsummary12, ll_taxsummary28;
    TextView tv_bill_change, tv_type18, tv_taxable18, tv_taxamt18, tv_netamt18, tv_type0, tv_taxable0, tv_taxamt0, tv_netamt0, tv_type5, tv_taxable5,
            tv_taxamt5, tv_netamt5, tv_type12, tv_taxable12, tv_taxamt12, tv_netamt12, tv_type28, tv_taxable28, tv_taxamt28, tv_netamt28, tv_typetotal, tv_taxabletotal,
            tv_taxamttotal, tv_netamttotal, tv_takeordine, tv_contact_number, tv_prt_gstnumber;

    //    variables
    int PageNumber = 1;
    private boolean loadMore = false, isSwipe = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    String contact_no = "", str_action = "";
    String orderId = "";
    Bundle bundle;
    ArrayList<UserRights> userRights = new ArrayList<>();
    int edit_right = 0, delete_right = 0, print_right = 0;

    public static IMyBinder myBinder;

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
        return inflater.inflate(R.layout.fragment_service_order_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Declaration(view);
        Initialization(view);
        onBackPress(view);
    }


    private void Declaration(View view) {
        rv_service_order_listing = view.findViewById(R.id.rv_service_order_listing);
        btn_add_service_order = view.findViewById(R.id.btn_add_service_order);
        tv_lbl_status = view.findViewById(R.id.tv_lbl_index);
        tv_lbl_tran_id = view.findViewById(R.id.tv_lbl_tran_id);
        tv_lbl_order_no = view.findViewById(R.id.tv_lbl_order_no);
        tv_lbl_ord_date = view.findViewById(R.id.tv_lbl_ord_date);
        tv_lbl_amount = view.findViewById(R.id.tv_lbl_amount);
        tv_lbl_member = view.findViewById(R.id.tv_lbl_member);
        tv_lbl_ent_by = view.findViewById(R.id.tv_lbl_ent_by);
        tv_lbl_more = view.findViewById(R.id.tv_lbl_more);
        tv_lbl_opration = view.findViewById(R.id.tv_lbl_opration);
        btn_filter = view.findViewById(R.id.btn_filter);
        swi_layout = view.findViewById(R.id.swi_layout);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        edt_search = view.findViewById(R.id.edt_search);
        iv_search = view.findViewById(R.id.iv_search);

        frameLayout_bill = view.findViewById(R.id.framelayout_bill);
        framelayout_list = view.findViewById(R.id.framelayout_list);
        tv_salepersonname = view.findViewById(R.id.tv_salepersonname);
        tv_totalbilldiscount = view.findViewById(R.id.tv_totalbilldiscount);
        tv_netpayableamt = view.findViewById(R.id.tv_netpayableamt);
        tv_cust_name = view.findViewById(R.id.tv_cust_name);
        rv_item_bill = view.findViewById(R.id.rv_item_bill);
        tv_cust_mobile = view.findViewById(R.id.tv_cust_mobile);
        tv_date = view.findViewById(R.id.tv_date);
        tv_bill_taxbale = view.findViewById(R.id.tv_bill_taxable);
        tv_bill_cgst = view.findViewById(R.id.tv_bill_cgst);
        tv_bill_sgst = view.findViewById(R.id.tv_bill_sgst);
        tv_bill_total = view.findViewById(R.id.tv_bill_total);
        tv_bill_discount = view.findViewById(R.id.tv_bill_discount);
        tv_gst = view.findViewById(R.id.tv_gst);
        tv_address = view.findViewById(R.id.tv_address);
        tv_orderno = view.findViewById(R.id.tv_orderno);
        tv_cashamt = view.findViewById(R.id.tv_cashamt);
        rv_bill_paytype = view.findViewById(R.id.rv_bill_paytype);
        ll_cash = view.findViewById(R.id.ll_cash);
        ll_ordertype = view.findViewById(R.id.ll_ordertype);
        tv_ordertype = view.findViewById(R.id.tv_ordertype);
        ll_changeamt = view.findViewById(R.id.ll_changeamt);
        tv_header_paytype = view.findViewById(R.id.tv_header_paytype);
        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        tv_bill_Remaining = view.findViewById(R.id.tv_bill_Remaining);
        iv_prt_companylogo = view.findViewById(R.id.iv_prt_companylogo);
        tv_order_qty = view.findViewById(R.id.tv_order_qty);
        tv_bill_change = view.findViewById(R.id.tv_bill_change);
        tv_prt_redeeamt = view.findViewById(R.id.tv_prt_redeeamt);
        tv_contact_number = view.findViewById(R.id.tv_contact_number);
        tv_takeordine = view.findViewById(R.id.tv_takeordine);
        tv_prt_gstnumber = view.findViewById(R.id.tv_prt_gstnumber);
        tv_ord_no = view.findViewById(R.id.tv_ord_no);
        tv_store_name = view.findViewById(R.id.tv_store_name);

        //<editor-fold desc="tax summary">
        ll_taxsummary18 = view.findViewById(R.id.ll_taxsummary18);
        ll_taxsummary28 = view.findViewById(R.id.ll_taxsummary28);
        ll_taxsummary12 = view.findViewById(R.id.ll_taxsummary12);
        ll_taxsummary5 = view.findViewById(R.id.ll_taxsummary5);
        ll_taxsummary0 = view.findViewById(R.id.ll_taxsummary0);

        tv_type18 = view.findViewById(R.id.tv_type18);
        tv_taxable18 = view.findViewById(R.id.tv_taxable18);
        tv_taxamt18 = view.findViewById(R.id.tv_taxamt18);
        tv_netamt18 = view.findViewById(R.id.tv_netamt18);

        tv_type12 = view.findViewById(R.id.tv_type12);
        tv_taxable12 = view.findViewById(R.id.tv_taxable12);
        tv_taxamt12 = view.findViewById(R.id.tv_taxamt12);
        tv_netamt12 = view.findViewById(R.id.tv_netamt12);

        tv_type0 = view.findViewById(R.id.tv_type0);
        tv_taxable0 = view.findViewById(R.id.tv_taxable0);
        tv_taxamt0 = view.findViewById(R.id.tv_taxamt0);
        tv_netamt0 = view.findViewById(R.id.tv_netamt0);


        tv_type5 = view.findViewById(R.id.tv_type5);
        tv_taxable5 = view.findViewById(R.id.tv_taxable5);
        tv_taxamt5 = view.findViewById(R.id.tv_taxamt5);
        tv_netamt5 = view.findViewById(R.id.tv_netamt5);


        tv_type28 = view.findViewById(R.id.tv_type28);
        tv_taxable28 = view.findViewById(R.id.tv_taxable28);
        tv_taxamt28 = view.findViewById(R.id.tv_taxamt28);
        tv_netamt28 = view.findViewById(R.id.tv_netamt28);

        tv_typetotal = view.findViewById(R.id.tv_typetotal);
        tv_taxabletotal = view.findViewById(R.id.tv_taxabletotal);
        tv_taxamttotal = view.findViewById(R.id.tv_taxamttotal);
        tv_netamttotal = view.findViewById(R.id.tv_netamttotal);

        //</editor-fold>
    }

    private void Initialization(View view) {
        framelayout_list.setVisibility(VISIBLE);
        String json = SessionManagement.getStringValue(getContext(), AppConstant.USERLIST, "");

        if (json != null) {

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<UserRights>>() {
            }.getType();

            userRights = gson.fromJson(json, type);
      //   Log.d("UERRIGHTS", "Initialization: " + new Gson().toJson(userRights));
            for (int i = 0; i < userRights.size(); i++) {
                if (userRights.get(i).getPagename().equals(AppConstant.TXT_APPMENU_SERVICE_ORDER)) {
                    edit_right = userRights.get(i).getEditright();
                    delete_right = userRights.get(i).getDelright();
                    print_right = userRights.get(i).getPrintright();
                }
            }
        }


        mainActivity.iv_search.setVisibility(View.GONE);
        mainActivity.iv_add_contact.setVisibility(View.GONE);
        mainActivity.iv_workflow.setVisibility(View.GONE);
        mainActivity.iv_range_booking.setVisibility(View.GONE);
        mainActivity.edt_search.setVisibility(View.GONE);
        mainActivity.iv_workflow.setVisibility(View.GONE);
        bundle = getArguments();
        if (bundle != null) {
            str_action = bundle.getString(AppConstant.ACTION);
            if (str_action.equals(AppConstant.ACTION_LISTING)) {
                if (print_right == 0) {
                    tv_lbl_opration.setVisibility(View.GONE);
                } else {
                    tv_lbl_opration.setVisibility(View.VISIBLE);
                }
                btn_add_service_order.setVisibility(View.GONE);
            } else if (str_action.equals(AppConstant.ACTION_LISTING_RIGHTS)) {
                if (edit_right == 0 && delete_right == 0 && print_right == 0) {
                    tv_lbl_opration.setVisibility(View.GONE);
                } else {
                    tv_lbl_opration.setVisibility(View.VISIBLE);
                }
                btn_add_service_order.setVisibility(View.VISIBLE);
            }
            contact_no = bundle.getString(AppConstant.LAST_ADDED_CUST_CONTACT);
        }
        mainActivity.iv_add_contact.setVisibility(View.GONE);

        //<editor-fold desc="dynamic lbl">
        try {
            tv_lbl_status.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_SERVICE_ORDER_STATUS).getLabel());
            tv_lbl_tran_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_TRAN_ID).getLabel());
            tv_lbl_order_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_NO).getLabel());
            tv_lbl_ord_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_DATE).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_AMOUNT).getLabel());
            tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MEMBER).getLabel());
            tv_lbl_ent_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ENTRY_BY).getLabel());
            tv_lbl_more.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MORE).getLabel());
            tv_lbl_opration.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_SERVICE_ORDER_OPERATION).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_service_order_listing.setLayoutManager(linearLayoutManager);

        btn_add_service_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.LAST_ADDED_CUST_CONTACT, contact_no);
                bundle.putString(AppConstant.SERVICE_ORDER_ACTION, AppConstant.INSERT_SERVICE_ORDER_ACTION);
                Fragment fragment = new ServiceOrderFragment();
                fragment.setArguments(bundle);
                mainActivity.addFragment(fragment);
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFilterDialog();
            }
        });

        PageNumber = 1;
        CallApiGetServiceOrderList();

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


        rv_service_order_listing.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            CallApiGetServiceOrderList();
                        }
                    }
                }
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
                    serviceOrderArrayList = new ArrayList<>();
                    updatedserviceOrderArrayList = new ArrayList<>();

                    if (!isSwipe)
                        CallApiGetServiceOrderList();
                }

            }
        });


        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fltfilter = edt_search.getText().toString();
                PageNumber = 1;
                recyclerViewState = null;
                serviceOrderArrayList = new ArrayList<>();
                updatedserviceOrderArrayList = new ArrayList<>();
                CallApiGetServiceOrderList();
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
                    serviceOrderArrayList = new ArrayList<>();
                    updatedserviceOrderArrayList = new ArrayList<>();
                    CallApiGetServiceOrderList();
                    Utility.hideKeyboardFrom(getContext(), edt_search);
                    return true;
                }
                return false;
            }
        });

    }

    private void shuffleItems() {
        isSwipe = true;
        edt_search.setText("");
        PageNumber = 1;
        fltmemberid = "%";
        fltfromdate = "";
        flttodate = "";
        str_member = "";
        fltfilter = "";
        recyclerViewState = null;
        serviceOrderArrayList = new ArrayList<>();
        updatedserviceOrderArrayList = new ArrayList<>();
        CallApiGetServiceOrderList();
    }

    ArrayList<ServiceOrder> serviceOrderArrayList;
    ArrayList<ServiceOrder> updatedserviceOrderArrayList = new ArrayList<>();
    String fltmemberid = "%", fltfromdate = "", flttodate = "";
    String cmp_logo, cmp_address, cmp_email, cmp_contact, cmp_israngehour, order_no, customerName, CustomerContact_no, date;

    public void CallApiGetServiceOrderList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listserviceorderhistory");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("nextpage", "" + PageNumber);
        params.put("fltmemberid", fltmemberid);
        params.put("fltfromdate", fltfromdate);
        params.put("flttodate", flttodate);
        params.put("fltfilter", fltfilter);

//        Log.e("params", "CallApiGetServiceOrderList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.SERVICE_ORDER_URL, params, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "CallApiGetServiceOrderList: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            showload = jsonObject.getInt("loadmore");

                            if (status == 1) {

                                cmp_logo = jsonObject.getString("cmp_logo");
                                cmp_address = jsonObject.getString("cmp_address");
                                cmp_email = jsonObject.getString("cmp_email");
                                cmp_contact = jsonObject.getString("cmp_contact");
                                cmp_israngehour = jsonObject.getString("cmp_israngehour");
                                date = jsonObject.getString("curr_datetime");

                                if (jsonObject.getInt("isserviceorderdata") == 1) {

                                    JSONArray OrderHistoryData = jsonObject.getJSONArray("serviceorderdata");

                                    if (OrderHistoryData != null && OrderHistoryData.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<ServiceOrder>>() {
                                        }.getType();
                                        serviceOrderArrayList = gson.fromJson(OrderHistoryData.toString(), listtype);

                                        //Add arraylist
                                        updatedserviceOrderArrayList.addAll(serviceOrderArrayList);

                                        ServiceOrderListAdapter adepter = new ServiceOrderListAdapter(getActivity(), str_action, updatedserviceOrderArrayList, edit_right, delete_right, print_right, new ServiceOrderListAdapter.OnclickListener() {
                                            @Override
                                            public void onClick(ServiceOrder model, int clickMangae) {
                                                //Clickmanage : 1 view more /2 :edit / 3 :cancel /4 : print
                                                if (clickMangae == 1) {
                                                    orderId = model.getId();
                                                    callApiServiceOrderDetail(true, false);
                                                } else if (clickMangae == 2) {
                                                    orderId = model.getId();
                                                    contact_no = model.getMembercontact();
                                                    callApiServiceOrderDetail(false, false);

                                                } else if (clickMangae == 3) {
                                                    OpenConfirmDialog("", model.getId(), Utility.languageLabel(getActivity(), LabelMaster.LBL_SRC_ORD_DLT_MSG_ORD).getLabel() + " ?");
                                                } else if (clickMangae == 4) {
                                                    orderId = model.getId();
                                                    order_no = model.getOrderno();
                                                    customerName = model.getMembername();
                                                    CustomerContact_no = model.getMembercontact();

                                                    callApiServiceOrderDetail(false, true);

//                                                    PrintInvoiceA4(model.getInvoicepdfurl());
                                                }
                                            }
                                        });
                                        rv_service_order_listing.setAdapter(adepter);
                                        adepter.notifyDataSetChanged();
                                        rv_service_order_listing.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                        loadMore = true;
                                        rv_service_order_listing.setVisibility(View.VISIBLE);
                                        tv_nodata.setVisibility(View.GONE);
                                    } else {
                                        rv_service_order_listing.setVisibility(View.GONE);
                                        tv_nodata.setVisibility(View.VISIBLE);
                                        tv_nodata.setText(message);
                                    }
                                } else {
                                    rv_service_order_listing.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
                                }

                            } else {
                                rv_service_order_listing.setVisibility(View.GONE);
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


    private void PrintInvoiceA4(String invoicepdfurl) {
        Bundle bundle = new Bundle();
        bundle.putString("URL", invoicepdfurl);
        bundle.putString(AppConstant.ACTION, str_action);
        Fragment fragment = new OpenPDFFragment();
        fragment.setArguments(bundle);
        mainActivity.addFragment(fragment);
    }


    ArrayList<ServiceOrderDetail> serviceOrderDetailArrayList;

    private void callApiServiceOrderDetail(boolean isShowDialog, boolean isPrint) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listserviceorderdetail");
        params.put("orderid", orderId);
//        Log.e("callApiOrderDetail", "onResponse: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.SERVICE_ORDER_URL, params, 2, false, false, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("callApiOrderDetail", "onResponse: " + response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");

                    if (status == 1) {

                        JSONArray jsonArray = jsonObject.getJSONArray("serviceorderdata");
                        if (jsonArray != null && jsonArray.length() != 0) {

                            serviceOrderDetailArrayList = new ArrayList<>();
                            Gson gson = new Gson();
                            Type listtype = new TypeToken<ArrayList<ServiceOrderDetail>>() {
                            }.getType();
                            serviceOrderDetailArrayList = gson.fromJson(jsonArray.toString(), listtype);
                            if (isShowDialog) {
                                OrderDetailDialog();
                            } else if (!isPrint) {
                                Bundle bundle = new Bundle();
                                bundle.putString(AppConstant.LAST_ADDED_CUST_CONTACT, contact_no);
                                bundle.putString(AppConstant.UPDATE_SERVICE_ORDER_DATA, new Gson().toJson(serviceOrderDetailArrayList.get(0)));
                                bundle.putString(AppConstant.SERVICE_ORDER_ACTION, AppConstant.UPDATE_SERVICE_ORDER_ACTION);
                                Fragment fragment = new ServiceOrderFragment();
                                fragment.setArguments(bundle);
                                mainActivity.addFragment(fragment);
                            }

                            if (isPrint) {
                                try {
                                    SetPrintBillValues(cmp_logo, cmp_address, cmp_email, cmp_contact, cmp_israngehour,
                                            serviceOrderDetailArrayList.get(0).getTotalpaid(), String.valueOf(Double.parseDouble(serviceOrderArrayList.get(0).getTotalamount())-Double.parseDouble( serviceOrderArrayList.get(0).getTotalpaid())),
                                            serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo(),
                                            serviceOrderDetailArrayList.get(0).getServiceorderpaymentdetailinfo(), serviceOrderDetailArrayList.get(0).getOfulldate(),
                                            serviceOrderDetailArrayList.get(0).getEntrypersonname(),serviceOrderDetailArrayList.get(0).getTotalbilldiscount(),serviceOrderDetailArrayList.get(0).getStorename());
                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    Log.e("Exception", "onResponse: " + e.toString());
                                }
                            }
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

    Dialog orderDialog = null;
    TextView tv_t_qty,tv_totaldisc, tv_total_payment, tv_t_price, tv_t_discount, tv_t_taxable, tv_t_vat, tv_t_amount, tv_member_name, tv_amount,
            tv_entry_by, tv_trs_id, tv_oder_date, tv_oder_note, tv_change_amount, tv_lbl_dlg_title, tv_lbl_amount_total, tv_lbl_entry_by,
            tv_lbl_orde_date, tv_lbl_order_note, tv_lbl_item_detail_title, tv_lbl_item, tv_lbl_type, tv_lbl_qty, tv_lbl_discount, tv_lbl_taxable,
            tv_lbl_vat, tv_lbl_amount_title, tv_lbl_total, tv_lbl_payment_detail_title, tv_lbl_payment_type, tv_lbl_change_amount, tv_lbl_total_amount,
            tv_order_status, tv_lbl_issue_qty, tv_lbl_remainqty;
    ImageView iv_close;
    RecyclerView rv_item_detail, rv_payment;
    int isEditable;

    OrderHistoryItemDetailAdapter adapter;

    public void OrderDetailDialog() {

        if (orderDialog != null && orderDialog.isShowing()) {
            return;
        }

        if (orderDialog == null) {
            orderDialog = new Dialog(getContext());
            orderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            orderDialog.setContentView(R.layout.dialog_service_order_detail);
            Window window = orderDialog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            orderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            orderDialog.setCancelable(false);
            orderDialog.show();
        }


        TextView tv_lbl_member, tv_lbl_tran_id, tv_lbl_amount, tv_lbl_price, tv_lbl_opration;

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
        tv_totaldisc = orderDialog.findViewById(R.id.tv_totaldisc);
        rv_item_detail = orderDialog.findViewById(R.id.rv_item_detail);
        rv_payment = orderDialog.findViewById(R.id.rv_payment);
        tv_change_amount = orderDialog.findViewById(R.id.tv_change_amount);
        tv_bill_change = orderDialog.findViewById(R.id.tv_bill_change);
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
        tv_order_status = orderDialog.findViewById(R.id.tv_order_status);
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
            tv_lbl_taxable.setText("Total");
            tv_lbl_vat.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_VAT).getLabel());
            tv_lbl_amount_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT).getLabel());
            tv_lbl_payment_detail_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_PAYMENT_DETAIL_TIT).getLabel());
            tv_lbl_payment_type.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_PAYMENT_TYPE).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_change_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_CHANGE_AMOUNT).getLabel());
            tv_lbl_total_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT).getLabel());
            tv_lbl_price.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_PRICE).getLabel());
            tv_lbl_issue_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SERVICE_ISSUE_QTY).getLabel());
            tv_lbl_remainqty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SERVICE_REMAINING_QTY).getLabel());
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


        tv_lbl_dlg_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_TITLE).getLabel() + " -  " + serviceOrderDetailArrayList.get(0).getOrderno());
        tv_member_name.setText(serviceOrderDetailArrayList.get(0).getMembername());
        tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(serviceOrderDetailArrayList.get(0).getTotalpaid())));
        tv_entry_by.setText(serviceOrderDetailArrayList.get(0).getEntrypersonname() + " (" + serviceOrderDetailArrayList.get(0).getEntrypersoncontact() + ")");
        tv_trs_id.setText(serviceOrderDetailArrayList.get(0).getTransactionid());
        tv_oder_date.setText(serviceOrderDetailArrayList.get(0).getOfulldate());
        tv_oder_note.setText(serviceOrderDetailArrayList.get(0).getOrdernotes());

        tv_change_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(serviceOrderDetailArrayList.get(0).getTotalchangeamount())));
        if (!serviceOrderArrayList.get(0).getOrderstatus().isEmpty()) {
            tv_order_status.setText(serviceOrderDetailArrayList.get(0).getOrderstatus());
            tv_order_status.setVisibility(View.VISIBLE);
        } else {
            tv_order_status.setVisibility(View.GONE);
        }

        if (!serviceOrderArrayList.get(0).getOrderstatuscolor().isEmpty()) {
            tv_order_status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(serviceOrderArrayList.get(0).getOrderstatuscolor())));
        }

        rv_item_detail.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_payment.setLayoutManager(new LinearLayoutManager(getContext()));
        if (str_action.equals(AppConstant.ACTION_LISTING)) {
            isEditable = 0;
        } else {
            isEditable = 1;
        }

//        if (delete_right == 1) {
//            tv_lbl_opration.setVisibility(View.VISIBLE);
//        } else {
//            tv_lbl_opration.setVisibility(View.GONE);
//        }

        adapter = new OrderHistoryItemDetailAdapter(getContext(), serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo(), "ServiceOrder", isEditable, delete_right, new OrderHistoryItemDetailAdapter.OnclickListener() {
            @Override
            public void onClick(Storeorderdetailinfo model, int clickMange) {
                //clickmange=3 delete
                if (clickMange == 3) {
                    OpenConfirmDialog(model.getId(), model.getOrderid(), Utility.languageLabel(getActivity(), LabelMaster.LBL_SRC_ORD_DLT_MSG_ITEM).getLabel() + " ?");
                }
            }
        });
        rv_item_detail.setAdapter(adapter);

        OrderHistoryPaymentDetailAdapter historyPaymentDetailAdapter = new OrderHistoryPaymentDetailAdapter(getActivity(), serviceOrderDetailArrayList.get(0).getServiceorderpaymentdetailinfo());
        rv_payment.setAdapter(historyPaymentDetailAdapter);

        TotalItem();
    }

    public static Dialog confirmDialog;

    private void OpenConfirmDialog(String id, String orderid, String message) {
        try {

            if (confirmDialog != null && confirmDialog.isShowing())
                return;

            confirmDialog = new Dialog(getContext());
            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmDialog.setContentView(R.layout.dialog_exit_message_box);
            TextView dialogTitle = confirmDialog.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(message);
            Button btnOk = confirmDialog.findViewById(R.id.btnyes);

            Button btnno = confirmDialog.findViewById(R.id.btnno);
            btnno.setVisibility(GONE);
            ImageView iv_gif = confirmDialog.findViewById(R.id.iv_gif);

            try {
                btnOk.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_EXIT_YES).getLabel());
                btnno.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_EXIT_NO).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_lefttoright);
            Glide.with(getContext()).asGif().load(R.drawable.loader).into(iv_gif);
            iv_gif.startAnimation(animation);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        confirmDialog.dismiss();
                        confirmDialog = null;
                        if (id.equals("")) {
                            CallApiCancelOrder(orderid);
                        } else {
                            CallApiCancelItem(id, orderid);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    confirmDialog.dismiss();
                }
            });
            confirmDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CallApiCancelItem(String id, String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "cancelserviceorderitem");
        params.put("orderid", orderid);
        params.put("id", id);

//        Log.e("params", "CallApiCancelItem: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.SERVICE_ORDER_URL, params, 2, true, true, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("CallApiCancelItem", "onResponse: " + response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");

                    if (status == 1) {
                        Utility.initErrorMessagePopupWindow(getActivity(), message);
                        callApiServiceOrderDetail(true, false);
                        adapter.notifyDataSetChanged();
                        CallApiGetServiceOrderList();
                    } else {
                        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void CallApiCancelOrder(String orderid) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "cancelserviceorder");
        params.put("orderid", orderid);

//        Log.e("params", "CallApiCancelOrder: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.SERVICE_ORDER_URL, params, 2, true, true, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("CallApiCancelOrder", "onResponse: " + response);

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");

                    if (status == 1) {
                        Utility.initErrorMessagePopupWindow(getActivity(), message);
                        PageNumber = 1;
                        fltmemberid = "%";
                        fltfromdate = "";
                        flttodate = "";
                        serviceOrderArrayList = new ArrayList<>();
                        updatedserviceOrderArrayList = new ArrayList<>();
                        CallApiGetServiceOrderList();
                    } else {
                        Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void TotalItem() {
        int qty = 0;
        double price = 0.0;
        double discount = 0.0;
        double taxable = 0.0;
        double vat = 0.0;
        double amount = 0.0;
        double total = 0.0;
        double totalDiscount = 0.0;

        for (int i = 0; i < serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().size(); i++) {

            qty = qty + Integer.parseInt(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).getQty());
            price = price + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).getPrice());
            discount = discount + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).getDiscountamt());
            totalDiscount = totalDiscount + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).gettotalDiscount());
            taxable = taxable + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).getTaxable());
            vat = vat + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).getIgsttaxamt());
            amount = amount + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderdetailinfo().get(i).getFinalprice());
        }

        if (tv_t_qty != null && tv_t_price != null && tv_t_discount != null && tv_t_taxable != null && tv_t_vat != null && tv_t_amount != null) {
            tv_t_qty.setText(String.valueOf(qty));
            tv_t_price.setText(String.valueOf(String.format("%.2f", price)));
            tv_t_discount.setText(String.valueOf(String.format("%.2f", discount)));
            tv_totaldisc.setText(String.valueOf(String.format("%.2f", totalDiscount)));
            tv_t_taxable.setText(String.valueOf(String.format("%.2f", taxable)));
            tv_t_vat.setText(String.valueOf(String.format("%.2f", vat)));
            tv_t_amount.setText(String.valueOf(String.format("%.2f", amount)));
        }

        for (int i = 0; i < serviceOrderDetailArrayList.get(0).getServiceorderpaymentdetailinfo().size(); i++) {
            total = total + Double.parseDouble(serviceOrderDetailArrayList.get(0).getServiceorderpaymentdetailinfo().get(i).getAmount());
        }
        if (tv_total_payment != null) {
            tv_total_payment.setText(String.format("%.2f", total));
        }
        if (tv_totaldisc != null) {
            tv_totaldisc.setText(String.format("%.2f", totalDiscount));
        }

    }

    BottomSheetDialog filterDialog = null;
    EditText edt_select_member, edt_from_date, edt_to_date;
    TextView tv_btn_reset, tv_btn_apply, tv_lbl_filter;
    ImageView iv_close_filter_dlg;
    TextInputLayout txt_select_member, txt_from_date, txt_to_date;
    String str_member = "";

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

        if (!str_member.isEmpty() || !fltfromdate.isEmpty() || !flttodate.isEmpty()) {
            edt_select_member.setText(str_member);
            edt_to_date.setText(flttodate);
            edt_from_date.setText(fltfromdate);
        }

        callApiGetMemberList();

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
                serviceOrderArrayList = new ArrayList<>();
                updatedserviceOrderArrayList = new ArrayList<>();
                CallApiGetServiceOrderList();
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
                            PageNumber = 1;
                            fltfromdate = edt_from_date.getText().toString();
                            flttodate = edt_to_date.getText().toString();
                            serviceOrderArrayList = new ArrayList<>();
                            updatedserviceOrderArrayList = new ArrayList<>();
                            CallApiGetServiceOrderList();
                            filterDialog.dismiss();
                            filterDialog = null;
                        } else {
                            Toast.makeText(getContext(), Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_ERE_TODATE).getLabel(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    PageNumber = 1;
                    fltfromdate = edt_from_date.getText().toString();
                    flttodate = edt_to_date.getText().toString();
                    serviceOrderArrayList = new ArrayList<>();
                    updatedserviceOrderArrayList = new ArrayList<>();
                    CallApiGetServiceOrderList();
                    filterDialog.dismiss();
                    filterDialog = null;
                }
            }
        });

    }


    ArrayList<Model> memberArrayList;
    public SearchableSpinnerDialog memberDialog;

    private void callApiGetMemberList() {

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

                                    memberArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    memberArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (edt_select_member.getText().toString().isEmpty()) {
                                        edt_select_member.setText(memberArrayList.get(0).getName());
                                        fltmemberid = memberArrayList.get(0).getId();
                                        str_member = memberArrayList.get(0).getName();
                                    }

                                    memberDialog = new SearchableSpinnerDialog(mainActivity, memberArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    memberDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
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


    private void SetPrintBillValues(String cmp_logo, String cmp_address, String cmp_email, String cmp_contact,
                                    String cmp_israngehour, String totalpaid, String totalchangeamount, ArrayList<Storeorderdetailinfo> serviceorderdetailinfo,
                                    ArrayList<Storeorderpaymentdetailinfo> serviceorderpaymentdetailinfo, String date, String entrypersonname,String totalbilldiscount,String storename) {
        tv_orderno.setVisibility(GONE);
        Glide.with(getContext()).load(cmp_logo).into(iv_prt_companylogo);
        tv_contact_number.setText("Mobile No : " + cmp_contact + "\n Email : " + cmp_email);

        tv_store_name.setText("Store Name: " + storename);
//        tv_store_name.setText("Store Name: " + SessionManagement.getStringValue(getContext(), AppConstant.STORE_NAME, ""));
        tv_ord_no.setText("Order No: " + order_no);
        tv_cust_name.setText("Customer Name : " + customerName);
        tv_cust_mobile.setText("Mobile No : " + CustomerContact_no);
        tv_address.setText(cmp_address);

        tv_date.setText("Date : " + date);
        tv_salepersonname.setText("Sale Person : " + entrypersonname);
//        tv_salepersonname.setText("Sale Person : " + SessionManagement.getStringValue(getContext(), AppConstant.FULLNAME, ""));

        rv_item_bill.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_bill_paytype.setLayoutManager(new LinearLayoutManager(getContext()));

        int totalQty = 0;
        for (int i = 0; i < serviceorderdetailinfo.size(); i++) {
            totalQty += Integer.parseInt(serviceorderdetailinfo.get(i).getQty());
        }

        tv_order_qty.setText("" + totalQty);
        HistoryOrderItemAdapter billItemAdapter = new HistoryOrderItemAdapter(getContext(), serviceorderdetailinfo);
        rv_item_bill.setAdapter(billItemAdapter);

//        tv_bill_total.setText("656565656565665656565656");
//        tv_bill_total.setText("Qr : " + totalpaid);
        tv_bill_total.setText("Qr : " + totalpaid);
        tv_totalbilldiscount.setText("Qr :"+ String.format("%.2f", Double.parseDouble(totalbilldiscount)));
        tv_netpayableamt.setText("Qr : " + String.format("%.2f",(Double.parseDouble(totalpaid) - Double.parseDouble(totalbilldiscount))));


        if (serviceorderpaymentdetailinfo.size() > 0) {
            rv_bill_paytype.setVisibility(VISIBLE);
            HistoryBillPayTypeAdapter billPayTypeAdapter = new HistoryBillPayTypeAdapter(getContext(), serviceorderpaymentdetailinfo);
            rv_bill_paytype.setAdapter(billPayTypeAdapter);
        }
        //</editor-fold>

        //TAX SUMAARY ARRAY

        ll_taxsummary5.setVisibility(GONE);
        ll_taxsummary0.setVisibility(GONE);
        ll_taxsummary12.setVisibility(GONE);
        ll_taxsummary18.setVisibility(GONE);
        ll_taxsummary28.setVisibility(GONE);
        tv_taxable0.setText("0");
        tv_taxamt0.setText("0");
        tv_netamt0.setText("0");
        tv_taxable12.setText("0");
        tv_taxamt12.setText("0");
        tv_netamt12.setText("0");
        tv_taxable18.setText("0");
        tv_taxamt18.setText("0");
        tv_netamt18.setText("0");
        tv_taxable28.setText("0");
        tv_taxamt0.setText("0");
        tv_netamt28.setText("0");


        tv_header_paytype.setVisibility(VISIBLE);
        tv_prt_redeeamt.setText("Qr : " + String.format("%.2f",(Double.parseDouble(totalpaid) - Double.parseDouble(totalbilldiscount))));
        view1.setVisibility(VISIBLE);
        view2.setVisibility(VISIBLE);

        tv_bill_change.setText("Qr " + String.format("%.2f", Double.parseDouble(serviceOrderDetailArrayList.get(0).getTotalchangeamount())));

//        if (Double.parseDouble(totalchangeamount) > 0.0) {
//            tv_bill_change.setText("Qr: " + String.format("%.2f", Double.parseDouble(totalchangeamount)));
//        } else {
//            tv_bill_change.setText("Qr: " + "0.00");
//
//        }

        tv_takeordine.setVisibility(GONE);
//        final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
//        final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight()+600), false);
//        frameLayout_bill.setVisibility(VISIBLE);
//        framelayout_list.setVisibility(GONE);
        if (ISCONNECT) {
//        final Bitmap bitmap1 = BitmapProcess.compressBmpByYourWidth
//                (BitmapFactory.decodeResource(getResources(), R.drawable.test), 300);

            final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
            final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight() + 600), false);
            new DownloadImageAsynktask(getContext(), bitmap, bitmap2).execute();

        } else {
            Toast.makeText(getContext(), getString(R.string.connect_first), Toast.LENGTH_SHORT).show();
            Utility.OpenPrintingOptionDlg(mainActivity, myBinder);
        }
    }

    public static class DownloadImageAsynktask extends AsyncTask<Void, Void, Void> {
        Context context;
        Bitmap bitmap, bitmap2;


        public DownloadImageAsynktask(Context context, Bitmap bitmap, Bitmap bitmap2) {
            this.context = context;
            this.bitmap = bitmap;
            this.bitmap2 = bitmap2;
        }

        @Override
        protected void onPreExecute() {
//            Log.e("onPreExecute", "onPreExecute: ");
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            myBinder.WriteSendData(new TaskCallback() {
                @Override
                public void OnSucceed() {
                    Toast.makeText(context, "Printing successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFailed() {
                    Toast.makeText(context, "Printing fail", Toast.LENGTH_SHORT).show();
                }
            }, new ProcessData() {
                @Override
                public List<byte[]> processDataBeforeSend() {
                    List<byte[]> list = new ArrayList<>();
                    try {
                        list.add(DataForSendToPrinterPos80.initializePrinter());
                        List<Bitmap> blist = new ArrayList<>();
                        blist = BitmapProcess.cutBitmap(bitmap2.getHeight(), bitmap2);
                        for (int i = 0; i < blist.size(); i++) {
                            list.add(DataForSendToPrinterPos80.printRasterBmp(0, blist.get(i), BitmapToByteData.BmpType.Dithering, BitmapToByteData.AlignType.Center, 576));
                        }
                        list.add(DataForSendToPrinterPos80.printAndFeedLine());
                    } catch (Exception e) {
//                        Log.e("exception", "processDataBeforeSend: " + e.toString());
                    }
                    return list;
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            Log.e("onPostExecute", "onPostExecute: ");

        }
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = null;
        // if (v.getMeasuredHeight() <= 0) {
        v.measure(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.draw(c);
        return b;
        //}
        //return b;
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

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_SERVICE_ORDER);
    }


}