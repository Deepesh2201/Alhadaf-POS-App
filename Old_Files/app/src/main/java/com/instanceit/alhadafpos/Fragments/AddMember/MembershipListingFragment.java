package com.instanceit.alhadafpos.Fragments.AddMember;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.instanceit.alhadafpos.Utility.Utility.ISCONNECT;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.instanceit.alhadafpos.Entity.Attributedetail;
import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.Entity.MembershipListing;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.Orderdetailinfo;
import com.instanceit.alhadafpos.Entity.Storeorderdetailinfo;
import com.instanceit.alhadafpos.Entity.UserRights;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.BillAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.BillIMemberShipAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.MembershipDetailAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.MembershipListAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.OrderMembershipItemInfoAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter.OrderHistoryItemDetailAdapter;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.MultipartVolleyUtils;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.Validation;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;
import com.instanceit.alhadafpos.VolleyPlus.Cache;

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

public class MembershipListingFragment extends Fragment {

    MainActivity mainActivity;

    //View
    RecyclerView rv_membership_list;
    TextView tv_nodata;
    FloatingActionButton btn_buy_membership, btn_filter;
    SwipeRefreshLayout swi_layout;
    EditText edt_search;
    ImageView iv_search;
    String fltfilter = "";
    TextView tv_lbl_status, tv_lbl_tran_id, tv_lbl_order_no, tv_lbl_ord_date, tv_lbl_amount, tv_lbl_member, tv_lbl_ent_by, tv_lbl_more, tv_lbl_opration;


    //bill print views
    FrameLayout frameLayout_bill;
    TextView tv_cust_name, tv_bill_Remaining, tv_cust_mobile, tv_salepersonname, tv_date, tv_bill_taxbale, tv_bill_sgst, tv_bill_cgst, tv_bill_total, tv_companyname,
            tv_gst, tv_fssaino, tv_address, tv_orderno, tv_cashamt, tv_ordertype, tv_header_paytype, tv_parentcompany, tv_order_qty, tv_store_name, tv_ord_no;
    RecyclerView rv_item_bill, rv_bill_paytype;
    View view1, view2;
    LinearLayout ll_cash, ll_ordertype, ll_changeamt, ln_payment_details;
    ImageView iv_logo, iv_prt_companylogo;
    LinearLayout ll_taxsummary18, ll_taxsummary0, ll_taxsummary5, ll_taxsummary12, ll_taxsummary28;
    TextView tv_bill_change, tv_type18, tv_taxable18, tv_taxamt18, tv_netamt18, tv_type0, tv_taxable0, tv_taxamt0, tv_netamt0, tv_type5, tv_taxable5,
            tv_taxamt5, tv_netamt5, tv_type12, tv_taxable12, tv_taxamt12, tv_netamt12, tv_type28, tv_taxable28, tv_taxamt28, tv_netamt28, tv_typetotal, tv_taxabletotal,
            tv_taxamttotal, tv_netamttotal, tv_prt_redeeamt, tv_takeordine, tv_contact_number, tv_prt_gstnumber;


    //Variables
    int PageNumber = 1;
    private boolean loadMore = false, isSwipe = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    String member_id;

    ArrayList<UserRights> userRights = new ArrayList<>();
    int cancel_right = 0, add_right = 0, print_right = 0;

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
        return inflater.inflate(R.layout.fragment_membership_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);
    }


    private void Declaration(View view) {
        btn_buy_membership = view.findViewById(R.id.btn_buy_membership);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        rv_membership_list = view.findViewById(R.id.rv_membership_list);
        swi_layout = view.findViewById(R.id.swi_layout);
        edt_search = view.findViewById(R.id.edt_search);
        iv_search = view.findViewById(R.id.iv_search);
        btn_filter = view.findViewById(R.id.btn_filter);
        tv_lbl_status = view.findViewById(R.id.tv_lbl_status);
        tv_lbl_tran_id = view.findViewById(R.id.tv_lbl_tran_id);
        tv_lbl_order_no = view.findViewById(R.id.tv_lbl_order_no);
        tv_lbl_ord_date = view.findViewById(R.id.tv_lbl_ord_date);
        tv_lbl_amount = view.findViewById(R.id.tv_lbl_amount);
        tv_lbl_member = view.findViewById(R.id.tv_lbl_member);
        tv_lbl_ent_by = view.findViewById(R.id.tv_lbl_ent_by);
        tv_lbl_more = view.findViewById(R.id.tv_lbl_more);
//        tv_lbl_opration = view.findViewById(R.id.tv_lbl_opration);

        frameLayout_bill = view.findViewById(R.id.framelayout_bill);
        tv_salepersonname = view.findViewById(R.id.tv_salepersonname);
        tv_cust_name = view.findViewById(R.id.tv_cust_name);
        rv_item_bill = view.findViewById(R.id.rv_item_bill);
        tv_cust_mobile = view.findViewById(R.id.tv_cust_mobile);
        tv_date = view.findViewById(R.id.tv_date);
        tv_bill_taxbale = view.findViewById(R.id.tv_bill_taxable);
        tv_bill_cgst = view.findViewById(R.id.tv_bill_cgst);
        tv_bill_sgst = view.findViewById(R.id.tv_bill_sgst);
        tv_bill_total = view.findViewById(R.id.tv_bill_total);
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

    private void Initialization() {

        //<editor-fold desc="dynamic lbl">
        try {
            tv_lbl_status.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_SERVICE_ORDER_STATUS).getLabel());
            tv_lbl_tran_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_TRAN_ID).getLabel());
            tv_lbl_order_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_INVOICE_NO_LBL).getLabel());
            tv_lbl_ord_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_INVOICE_DATE_LBL).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_AMOUNT).getLabel());
            tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MEMBER).getLabel());
            tv_lbl_ent_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ENTRY_BY).getLabel());
            tv_lbl_more.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MORE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_membership_list.setLayoutManager(linearLayoutManager);

        String json = SessionManagement.getStringValue(getContext(), AppConstant.USERLIST, "");

        if (json != null) {

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<UserRights>>() {
            }.getType();

            userRights = gson.fromJson(json, type);
//            Log.e("UERRIGHTS", "Initialization: " + new Gson().toJson(userRights));
            for (int i = 0; i < userRights.size(); i++) {
                if (userRights.get(i).getPagename().equals(AppConstant.TXT_APPMENU_MEMBERSHIP)) {
                    cancel_right = userRights.get(i).getDelright();
                    add_right = userRights.get(i).getAddright();
                    print_right = userRights.get(i).getPrintright();
                }
            }
            if (add_right == 1) {
                btn_buy_membership.setVisibility(View.VISIBLE);
            } else {
                btn_buy_membership.setVisibility(View.GONE);
            }
        }

        PageNumber = 1;
        CallApiGetMembershipList();

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


        rv_membership_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            CallApiGetMembershipList();
                        }
                    }
                }
            }
        });

        btn_buy_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMemberSelectionDialog();
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
                    membershipArrayList = new ArrayList<>();
                    updatedmembershipArrayList = new ArrayList<>();

                    if (!isSwipe)
                        CallApiGetMembershipList();
                }

            }
        });


        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fltfilter = edt_search.getText().toString();
                PageNumber = 1;
                recyclerViewState = null;
                membershipArrayList = new ArrayList<>();
                updatedmembershipArrayList = new ArrayList<>();
                CallApiGetMembershipList();
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
                    membershipArrayList = new ArrayList<>();
                    updatedmembershipArrayList = new ArrayList<>();
                    CallApiGetMembershipList();
                    Utility.hideKeyboardFrom(getContext(), edt_search);
                    return true;
                }
                return false;
            }
        });

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFilterDialog();
            }
        });

    }

    private void shuffleItems() {
        PageNumber = 1;
        edt_search.setText("");
        PageNumber = 1;
        fltfromdate = "";
        flttodate = "";
        fltfilter = "";
        recyclerViewState = null;
        membershipArrayList = new ArrayList<>();
        updatedmembershipArrayList = new ArrayList<>();
        CallApiGetMembershipList();
    }


    ArrayList<MembershipListing> membershipArrayList;
    ArrayList<MembershipListing> updatedmembershipArrayList = new ArrayList<>();

    public void CallApiGetMembershipList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listorderhistory");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("nextpage", "" + PageNumber);
        params.put("fltsearch", fltfilter);
        params.put("fltfromdate", fltfromdate);
        params.put("flttodate", flttodate);

//        Log.e("params", "CallApiGetMembershipList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_MEMBERSHIP_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "CallApiGetMembershipList: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            showload = jsonObject.getInt("loadmore");

                            if (status == 1) {


                                String cmp_logo = jsonObject.getString("cmp_logo");
                                String cmp_address = jsonObject.getString("cmp_address");
                                String cmp_email = jsonObject.getString("cmp_email");
                                String cmp_contact = jsonObject.getString("cmp_contact");
                                String cmp_israngehour = jsonObject.getString("cmp_israngehour");
                                String date = jsonObject.getString("curr_datetime");

                                if (jsonObject.getInt("isorderhistorydata") == 1) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("orderhistorydata");

                                    if (jsonArray != null && jsonArray.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<MembershipListing>>() {
                                        }.getType();
                                        membershipArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                        //Add arraylist
                                        updatedmembershipArrayList.addAll(membershipArrayList);

                                        MembershipListAdapter membershipListAdapter = new MembershipListAdapter(getActivity(), updatedmembershipArrayList, cancel_right, print_right, new MembershipListAdapter.OnclickListener() {
                                            @Override
                                            public void onClick(MembershipListing model, int clickMange) {
                                                //Clickmage 1=view more /2 = print pdf Open in crome /3= cancel membership invoice
                                                if (clickMange == 1) {
                                                    OrderDetailDialog(model);
                                                } else if (clickMange == 2) {
                                                    SetPrintBillValues(model.getOrderno(), cmp_logo, cmp_address, cmp_email,
                                                            cmp_contact, cmp_israngehour, model.getMembername(),
                                                            model.getMembercontact(), model.getOrderdetailinfo(), date);

//                                                    try {
//                                                    } catch (Exception e) {
//                                                        e.printStackTrace();
//                                                        Log.e("Exception", "onResponse: " + e.toString());
//                                                    }
//                                                    PrintInvoiceA4(model.getInvoicepdfurl());
                                                } else if (clickMange == 3) {
                                                    CancelInvoice(model.getId());
                                                }
                                            }
                                        });

                                        rv_membership_list.setAdapter(membershipListAdapter);
                                        rv_membership_list.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                        loadMore = true;
                                        rv_membership_list.setVisibility(View.VISIBLE);
                                        tv_nodata.setVisibility(View.GONE);
                                    } else {
                                        rv_membership_list.setVisibility(View.GONE);
                                        tv_nodata.setVisibility(View.VISIBLE);
                                        tv_nodata.setText(message);
                                    }
                                } else {
                                    rv_membership_list.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
                                }
                            } else {
                                rv_membership_list.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                tv_nodata.setText(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void CancelInvoice(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "cancelmshiporder");
        params.put("orderid", id);

//        Log.e("params", "CancelInvoice: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_MEMBERSHIP_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "CancelInvoice: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            Utility.initErrorMessagePopupWindow(mainActivity, message);
                            PageNumber = 1;
                            membershipArrayList = new ArrayList<>();
                            updatedmembershipArrayList = new ArrayList<>();
                            CallApiGetMembershipList();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void PrintInvoiceA4(String invoicepdfurl) {

        Bundle bundle = new Bundle();
        bundle.putString("URL", invoicepdfurl);
        bundle.putString(AppConstant.ACTION, "Membership");
        Fragment fragment = new OpenPDFFragment();
        fragment.setArguments(bundle);
        mainActivity.addFragment(fragment);
    }


    //    Dialog orderDialog = null;
    BottomSheetDialog orderDialog = null;
    TextView tv_t_qty, tv_total_payment, tv_t_price, tv_t_discount, tv_t_taxable, tv_t_vat, tv_t_amount, tv_member_name, tv_amount,
            tv_entry_by, tv_trs_id, tv_oder_date, tv_oder_note, tv_change_amount, tv_lbl_dlg_title, tv_lbl_amount_total, tv_lbl_entry_by,
            tv_lbl_orde_date, tv_lbl_order_note, tv_lbl_item_detail_title, tv_lbl_item, tv_lbl_type, tv_lbl_qty, tv_lbl_discount, tv_lbl_taxable,
            tv_lbl_vat, tv_lbl_amount_title, tv_lbl_total, tv_lbl_payment_detail_title, tv_lbl_payment_type, tv_lbl_change_amount, tv_lbl_total_amount,
            tv_lbl_coupon_code, tv_lbl_coupon_amount, tv_order_status, tv_lbl_issue_qty, tv_lbl_remainqty, tv_invoice_no, tv_coupon_amount, tv_coupon_code, tv_lbl_invoice_no;
    ImageView iv_close;
    RecyclerView rv_item_detail, rv_payment;

    public void OrderDetailDialog(MembershipListing membershipModel) {

        if (orderDialog != null && orderDialog.isShowing()) {
            return;
        }

        if (orderDialog == null) {
            orderDialog = new BottomSheetDialog(mainActivity);
            orderDialog.setContentView(R.layout.dialog_membership_order_detail);
            FrameLayout bottomSheet = orderDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            orderDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            orderDialog.setCancelable(true);
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
        tv_order_status = orderDialog.findViewById(R.id.tv_order_status);
        tv_lbl_issue_qty = orderDialog.findViewById(R.id.tv_lbl_issue_qty);
        tv_lbl_remainqty = orderDialog.findViewById(R.id.tv_lbl_remainqty);
        tv_lbl_opration = orderDialog.findViewById(R.id.tv_lbl_opration);
        tv_invoice_no = orderDialog.findViewById(R.id.tv_invoice_no);
        tv_coupon_amount = orderDialog.findViewById(R.id.tv_coupon_amount);
        tv_coupon_code = orderDialog.findViewById(R.id.tv_coupon_code);
        tv_lbl_coupon_code = orderDialog.findViewById(R.id.tv_lbl_coupon_code);
        tv_lbl_coupon_amount = orderDialog.findViewById(R.id.tv_lbl_coupon_amount);
        tv_lbl_invoice_no = orderDialog.findViewById(R.id.tv_lbl_invoice_no);

        try {
            tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_MEMBER).getLabel());
            tv_lbl_amount_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_entry_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ENTRY_BY).getLabel());
            tv_lbl_tran_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_TRAN_ID).getLabel());
            tv_lbl_orde_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_INVOICE_DATE_LBL).getLabel());
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
            tv_lbl_issue_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SERVICE_ISSUE_QTY).getLabel());
            tv_lbl_remainqty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SERVICE_REMAINING_QTY).getLabel());
            tv_lbl_coupon_code.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_COUPON_CODE_LBL).getLabel());
            tv_lbl_coupon_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_COUPON_AMOUNT_LBL).getLabel());
            tv_lbl_invoice_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_INVOICE_NO_LBL).getLabel());
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


        tv_lbl_dlg_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_TITLE).getLabel() + " -  " + membershipModel.getOrderno());
        tv_member_name.setText(membershipModel.getMembername());
        tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(membershipModel.getTotalpaid())));
        tv_entry_by.setText(membershipModel.getEntrypersonname() + " (" + membershipModel.getEntrypersoncontact() + ")");
        tv_trs_id.setText(membershipModel.getTransactionid());
        tv_oder_date.setText(membershipModel.getOfulldate());
        tv_invoice_no.setText(membershipModel.getOrderno());
        tv_coupon_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(membershipModel.getCouponamount())));
        tv_coupon_code.setText(membershipModel.getCouponcode());

        rv_item_detail.setLayoutManager(new LinearLayoutManager(getContext()));

        OrderMembershipItemInfoAdapter adapter = new OrderMembershipItemInfoAdapter(getContext(), membershipModel.getOrderdetailinfo(), new OrderMembershipItemInfoAdapter.OnclickListener() {
            @Override
            public void onClick(Orderdetailinfo model, int clickMange) {
                callApiGetDetailOfPackage(model, membershipModel.getId());
            }
        });
        rv_item_detail.setAdapter(adapter);

        orderDialog.show();

    }


    ArrayList<Attributedetail> attributeDetailArrayList = new ArrayList<>();
    ArrayList<Attributedetail> attributeDetailArrayListNew = new ArrayList<>();

    private void callApiGetDetailOfPackage(Orderdetailinfo orderdetailinfo, String orderID) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listorderhistorydetail");
        params.put("orderid", orderID);
        params.put("orderdetid", orderdetailinfo.getId());

//        Log.e("params", "callApiGetDetailOfPackage: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_MEMBERSHIP_ORDER_URL, params, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetDetailOfPackage: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            if (status == 1) {

                                if (jsonObject.getInt("isattributedetail") == 1) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("attributedetail");

                                    if (jsonArray != null && jsonArray.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<Attributedetail>>() {
                                        }.getType();
                                        attributeDetailArrayList = gson.fromJson(jsonArray.toString(), listtype);
                                    }
                                }
                                if (jsonObject.getInt("iscoursebenefit") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("coursebenefit");

                                    if (jsonArray != null && jsonArray.length() != 0) {

                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<Attributedetail>>() {
                                        }.getType();
                                        attributeDetailArrayListNew = gson.fromJson(jsonArray.toString(), listtype);
                                    }
                                }

                                DialogDetail(attributeDetailArrayList, attributeDetailArrayListNew, orderdetailinfo);

                            } else {
                                Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    BottomSheetDialog filterDialog = null;
    EditText edt_from_date, edt_to_date;
    TextView tv_btn_reset, tv_btn_apply, tv_lbl_filter;
    ImageView iv_close_filter_dlg;
    TextInputLayout txt_from_date, txt_to_date;
    String fltfromdate = "", flttodate = "";

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

        EditText edt_select_member;
        TextInputLayout txt_select_member;

        edt_select_member = filterDialog.findViewById(R.id.edt_select_member);
        edt_from_date = filterDialog.findViewById(R.id.edt_from_date);
        edt_to_date = filterDialog.findViewById(R.id.edt_to_date);
        tv_btn_reset = filterDialog.findViewById(R.id.tv_btn_reset);
        tv_btn_apply = filterDialog.findViewById(R.id.tv_btn_apply);
        iv_close_filter_dlg = filterDialog.findViewById(R.id.iv_close_filter_dlg);
        txt_select_member = filterDialog.findViewById(R.id.txt_select_member);

        if (!fltfromdate.isEmpty() || !flttodate.isEmpty()) {
            edt_to_date.setText(flttodate);
            edt_from_date.setText(fltfromdate);
        }

        edt_select_member.setVisibility(View.GONE);
        txt_select_member.setVisibility(View.GONE);


        try {
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
                edt_from_date.setText("");
                edt_to_date.setText("");
                fltfromdate = edt_from_date.getText().toString();
                flttodate = edt_to_date.getText().toString();
                PageNumber = 1;
                membershipArrayList = new ArrayList<>();
                updatedmembershipArrayList = new ArrayList<>();
                CallApiGetMembershipList();
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
                            membershipArrayList = new ArrayList<>();
                            updatedmembershipArrayList = new ArrayList<>();
                            CallApiGetMembershipList();
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
                    membershipArrayList = new ArrayList<>();
                    updatedmembershipArrayList = new ArrayList<>();
                    CallApiGetMembershipList();
                    filterDialog.dismiss();
                    filterDialog = null;
                }
            }
        });

    }


    //<editor-fold desc="member selection and create new member">

    private Dialog selectMember;
    EditText edt_select_member;

    private void OpenMemberSelectionDialog() {
        try {
            if (selectMember != null && selectMember.isShowing())
                return;

            selectMember = new Dialog(getContext());

            selectMember.requestWindowFeature(Window.FEATURE_NO_TITLE);
            selectMember.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            selectMember.setContentView(R.layout.dialog_select_member);
            selectMember.setCancelable(false);

            Button btnOk = selectMember.findViewById(R.id.btnyes);
            edt_select_member = selectMember.findViewById(R.id.edt_select_member);
            LinearLayout ln_add_new_member = selectMember.findViewById(R.id.ln_add_new_member);
            ImageView iv_close = selectMember.findViewById(R.id.iv_close);
            RelativeLayout rl_dialog_main = selectMember.findViewById(R.id.rl_dialog_main);

            ln_add_new_member.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateNewMemberDialog();
                    selectMember.dismiss();
                    selectMember = null;
                }
            });

            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectMember.dismiss();
                    selectMember = null;
                }
            });


            callApiGetMemberList(edt_select_member);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!edt_select_member.getText().toString().isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("id", member_id);
                        bundle.putString("member_Name", edt_select_member.getText().toString());
                        Fragment addmember = new AddMemberFragment();
                        addmember.setArguments(bundle);
                        mainActivity.addFragment(addmember);
                        selectMember.dismiss();
                        selectMember = null;
                    } else {
                        Toast.makeText(getContext(), "Select Member or Create new Member", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            selectMember.setOnKeyListener(new Dialog.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                        selectMember.dismiss();
                        selectMember = null;
                    }
                    return false;
                }
            });

            selectMember.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ArrayList<Model> memberArrayList;
    public SearchableSpinnerDialog memberDialog;

    private void callApiGetMemberList(EditText editText) {

        Map<String, String> params = new HashMap<>();
        params.put("action", "listordermember");

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MEMBERSHIP_ORDER_URL, params, 1, true, true,
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

                                    memberDialog = new SearchableSpinnerDialog(getActivity(), memberArrayList, "Select Member", R.style.DialogAnimations_SmileWindow);

                                    memberDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            editText.setText(model.getName());
                                            member_id = model.getId();
                                        }
                                    });
                                }
                            }

                            editText.setOnClickListener(new View.OnClickListener() {
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

    private Dialog createMemberDilog;
    EditText edt_first_name, edt_last_name, edt_password, edt_confirm_password, edt_email, edt_phone_no, edt_qatar_id_no, edt_qatar_expir_date,
            edt_birth_date, edt_nationality_select, edt_address, edt_company_name, edt_select_user_type, edt_select_refrence_user, edt_middle_name, edt_passport_no, edt_passport_expir_date;
    TextInputLayout tl_password;
    ImageView ic_back_button;
    TextView tv_lbl_become_member;
    TextInputLayout txt_first_name, txt_middle_name, txt_last_name, txt_email, txt_phone_no, txt_select_user_type, txt_select_reference,
            txt_national_id_no, txt_qatar_expir_date, txt_birth_date, txt_nationality_select, txt_address, txt_company, tl_confirm_password, txt_national_passport_no, txt_passport_expir_date;

    private void CreateNewMemberDialog() {
        try {
            if (createMemberDilog != null && createMemberDilog.isShowing())
                return;

            createMemberDilog = new Dialog(getContext());

            createMemberDilog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            createMemberDilog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            createMemberDilog.setContentView(R.layout.dialog_new_member);
            Window window = createMemberDilog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            createMemberDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            createMemberDilog.setCancelable(false);

            Button btnOk = createMemberDilog.findViewById(R.id.btnyes);
            // edt_select_member = createMemberDilog.findViewById(R.id.edt_select_member);
            LinearLayout ln_add_new_member = createMemberDilog.findViewById(R.id.ln_add_new_member);
            RelativeLayout rl_dialog_main = createMemberDilog.findViewById(R.id.rl_dialog_main);
            Button btn_registration_submit = createMemberDilog.findViewById(R.id.btn_registration_submit);
            edt_first_name = createMemberDilog.findViewById(R.id.edt_first_name);
            edt_last_name = createMemberDilog.findViewById(R.id.edt_last_name);
            edt_password = createMemberDilog.findViewById(R.id.edt_password);
            edt_confirm_password = createMemberDilog.findViewById(R.id.edt_confirm_password);
            edt_email = createMemberDilog.findViewById(R.id.edt_email);
            edt_phone_no = createMemberDilog.findViewById(R.id.edt_phone_no);
            edt_qatar_id_no = createMemberDilog.findViewById(R.id.edt_qatar_id_no);
            edt_qatar_expir_date = createMemberDilog.findViewById(R.id.edt_qatar_expir_date);
            edt_birth_date = createMemberDilog.findViewById(R.id.edt_birth_date);
            edt_nationality_select = createMemberDilog.findViewById(R.id.edt_nationality_select);
            edt_address = createMemberDilog.findViewById(R.id.edt_address);
            edt_company_name = createMemberDilog.findViewById(R.id.edt_company_name);
            tl_password = createMemberDilog.findViewById(R.id.tl_password);
            ic_back_button = createMemberDilog.findViewById(R.id.ic_back_button);
            edt_select_user_type = createMemberDilog.findViewById(R.id.edt_select_user_type);
            edt_select_refrence_user = createMemberDilog.findViewById(R.id.edt_select_refrence_user);
            tv_lbl_become_member = createMemberDilog.findViewById(R.id.tv_lbl_become_member);
            txt_first_name = createMemberDilog.findViewById(R.id.txt_first_name);
            txt_last_name = createMemberDilog.findViewById(R.id.txt_last_name);
            txt_email = createMemberDilog.findViewById(R.id.txt_email);
            txt_phone_no = createMemberDilog.findViewById(R.id.txt_phone_no);
            txt_select_user_type = createMemberDilog.findViewById(R.id.txt_select_user_type);
            txt_select_reference = createMemberDilog.findViewById(R.id.txt_select_reference);
            txt_national_id_no = createMemberDilog.findViewById(R.id.txt_national_id_no);
            txt_qatar_expir_date = createMemberDilog.findViewById(R.id.txt_qatar_expir_date);
            txt_birth_date = createMemberDilog.findViewById(R.id.txt_birth_date);
            txt_nationality_select = createMemberDilog.findViewById(R.id.txt_nationality_select);
            txt_address = createMemberDilog.findViewById(R.id.txt_address);
            txt_company = createMemberDilog.findViewById(R.id.txt_company);
            tl_password = createMemberDilog.findViewById(R.id.tl_password);
            tl_confirm_password = createMemberDilog.findViewById(R.id.tl_confirm_password);
            edt_middle_name = createMemberDilog.findViewById(R.id.edt_middle_name);
            txt_middle_name = createMemberDilog.findViewById(R.id.txt_middle_name);
            txt_national_passport_no = createMemberDilog.findViewById(R.id.txt_national_passport_no);
            txt_passport_expir_date = createMemberDilog.findViewById(R.id.txt_passport_expir_date);
            edt_passport_no = createMemberDilog.findViewById(R.id.edt_qatar_passport_no);
            edt_passport_expir_date = createMemberDilog.findViewById(R.id.edt_passport_expir_date);

            try {
                tv_lbl_become_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_BECOME_MEMBER).getLabel());
                txt_first_name.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_FIRST_NAME).getLabel() + "*");
                txt_last_name.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_LBL_LAST_NAME).getLabel() + "*");
                tl_password.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_PASSWORD).getLabel() + "*");
                tl_confirm_password.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_CNF_PASSWORD).getLabel() + "*");
                txt_email.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_EMAIL).getLabel() + "*");
                txt_phone_no.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_CONTACT).getLabel() + "*");
                txt_national_id_no.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_QATAR_ID).getLabel());
                txt_qatar_expir_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_QATAR_ID_EXPIRY).getLabel());
                txt_birth_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_DOB).getLabel() + "*");
                txt_nationality_select.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_NATIONALITY).getLabel() + "*");
                txt_address.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_ADDRESS).getLabel() + "*");
                txt_company.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_COMPANY).getLabel() + "*");
                txt_middle_name.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_MIDDLE_NAME).getLabel());
                txt_national_passport_no.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_PASSPORT).getLabel());
                txt_passport_expir_date.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_HINT_PASSPORT_EXPIRY).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            edt_qatar_expir_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.hideKeyboardFrom(mainActivity, edt_qatar_expir_date);
                    Utility.showDatePickerDialog(mainActivity, edt_qatar_expir_date);
                }
            });

            edt_passport_expir_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.hideKeyboardFrom(mainActivity, edt_passport_expir_date);
                    Utility.showDatePickerDialog(mainActivity, edt_passport_expir_date);
                }
            });

            callApiGetUserTypeList();

            callApiGetMemberList(edt_select_refrence_user);

            edt_birth_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utility.hideKeyboardFrom(mainActivity, edt_birth_date);
                    Utility.showDatePickerDialogBirthdateDate(mainActivity, edt_birth_date);
                }
            });

            ic_back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createMemberDilog.dismiss();
                    createMemberDilog = null;
                    OpenMemberSelectionDialog();
                }
            });

            btn_registration_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edt_first_name.getText().toString().trim().equals("")) {
                        edt_first_name.requestFocus();
                        Utility.hideKeyboardFrom(mainActivity, edt_first_name);
                        edt_first_name.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_FNAME).getLabel());
                    } else if (edt_last_name.getText().toString().trim().equals("")) {
                        edt_last_name.requestFocus();
                        edt_last_name.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_LNAME).getLabel());
                    } else if (edt_email.getText().toString().trim().equals("")) {
                        edt_email.requestFocus();
                        edt_email.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_EMAIL).getLabel());
                    } else if (!Validation.isValidEmail(mainActivity, edt_email)) {
                        edt_email.requestFocus();
                        edt_email.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_VALID_EMAIL).getLabel());
                    } else if (edt_phone_no.getText().toString().trim().equals("")) {
                        edt_phone_no.requestFocus();
                        edt_phone_no.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_CONTACT).getLabel());
                    } else if (!Validation.isValidPhoneNumber(mainActivity, edt_phone_no)) {
                        edt_phone_no.requestFocus();
                        edt_phone_no.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_VALID_CONTACT).getLabel());
                    } /*else if (edt_qatar_id_no.getText().toString().trim().equals("")) {
                        edt_qatar_id_no.requestFocus();
                        edt_qatar_id_no.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_ENTER_QATAR_ID).getLabel());
                    } else if (edt_qatar_expir_date.getText().toString().trim().equals("")) {
                        edt_qatar_expir_date.requestFocus();
                        edt_qatar_expir_date.setError(Utility.languageLabel(MainActivity.this, LabelMaster.LBL_REG_ERR_SEL_DATE_QATAR_EXPIRE).getLabel());
                    } */ else if (edt_birth_date.getText().toString().trim().equals("")) {
                        edt_birth_date.requestFocus();
                        edt_birth_date.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_SEL_DOB).getLabel());
                    } else if (edt_nationality_select.getText().toString().trim().equals("")) {
                        edt_nationality_select.requestFocus();
                        edt_nationality_select.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_NATIONALITY).getLabel());
                    } else if (tl_password.getVisibility() == View.VISIBLE) {
                        if (edt_password.getText().toString().trim().equals("")) {
                            edt_password.requestFocus();
                            edt_password.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_PASSWORD).getLabel());
                        } else if (edt_confirm_password.getText().toString().trim().equals("")) {
                            edt_confirm_password.requestFocus();
                            edt_confirm_password.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_CNF_PASSWORD).getLabel());
                        } else if (!edt_confirm_password.getText().toString().equals(edt_password.getText().toString())) {
                            edt_confirm_password.requestFocus();
                            edt_confirm_password.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_ENTER_SAME_PASSWORD).getLabel());
                        } else {
                            callApiRegistrationAPI();
                        }
                    } else {
                        callApiRegistrationAPI();
                    }
                }
            });

            createMemberDilog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        createMemberDilog.dismiss();
                        createMemberDilog = null;
                        OpenMemberSelectionDialog();
                    }
                    return false;
                }
            });


            createMemberDilog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ArrayList<Model> userTypeArrayList;
    String user_type_id = "";
    SearchableSpinnerDialog searchableSpinnerDialog;

    private void callApiGetUserTypeList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listmemberusertype");

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MEMBER_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("isutypedata") == 1) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("utypedata");

                                    userTypeArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();

                                    userTypeArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    edt_select_user_type.setText(userTypeArrayList.get(0).getName());
                                    user_type_id = userTypeArrayList.get(0).getId();


                                    searchableSpinnerDialog = new SearchableSpinnerDialog(mainActivity, userTypeArrayList, "Select User Type", R.style.DialogAnimations_SmileWindow);
                                    searchableSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_select_user_type.setText(model.getName());
                                            user_type_id = model.getId();
                                        }
                                    });
                                }
                            }

                            edt_select_user_type.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        searchableSpinnerDialog.showSpinerDialog();
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


    //<editor-fold desc="Registration of member Api Call">
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
//        Log.e("para", "callApiRegistrationAPI: " + map);


        MultipartVolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MEMBER_URL, map, null, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("callApiRegistrationAPI", "onResponse: " + response);
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
                                initErrorMessagePopupWindow(mainActivity, message, status);

                            } else {
                                initErrorMessagePopupWindow(mainActivity, message, status);
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
                        mainActivity.addFragment(addmember);

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
    }


    //<editor-fold desc="package detail dialog">
    public static Dialog DetailsDailog = null;
    public static RecyclerView rv_membership_detail, rv_benrfits;
    public static ImageView btn_close_detail_dialog, iv_icon_img;
    public static TextView tv_membership_type, tv_membership_price, tv_tax, tv_per_month, tv_heading, tv_title_display_att, tv_lbl_name,
            tv_title_course_benefit, tv_lbl_benefit, tv_lbl_duration;
    LinearLayout ln_heading;

    public void DialogDetail(ArrayList<Attributedetail> membershipdetail, ArrayList<Attributedetail> attributeDetailArrayListNew, Orderdetailinfo orderdetailinfo) {

        if (DetailsDailog == null) {
            DetailsDailog = new Dialog(mainActivity);
            DetailsDailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            DetailsDailog.setContentView(R.layout.dialog_invoice_detail_membership);
            Window window = DetailsDailog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            DetailsDailog.setCancelable(false);
            DetailsDailog.show();
        }

        Button btn_buy_membership;

        rv_membership_detail = DetailsDailog.findViewById(R.id.rv_membership_detail);
        btn_close_detail_dialog = DetailsDailog.findViewById(R.id.btn_close_detail_dialog);
        btn_buy_membership = DetailsDailog.findViewById(R.id.btn_buy_membership);
        tv_membership_type = DetailsDailog.findViewById(R.id.tv_membership_type);
        tv_membership_price = DetailsDailog.findViewById(R.id.tv_membership_price);
        iv_icon_img = DetailsDailog.findViewById(R.id.iv_icon_img);
        tv_tax = DetailsDailog.findViewById(R.id.tv_tax);
        tv_per_month = DetailsDailog.findViewById(R.id.tv_per_month);
        ln_heading = DetailsDailog.findViewById(R.id.ln_heading);
        tv_heading = DetailsDailog.findViewById(R.id.tv_heading);
        rv_benrfits = DetailsDailog.findViewById(R.id.rv_benrfits);
        tv_title_display_att = DetailsDailog.findViewById(R.id.tv_title_display_att);
        tv_lbl_name = DetailsDailog.findViewById(R.id.tv_lbl_name);
        tv_title_course_benefit = DetailsDailog.findViewById(R.id.tv_title_course_benefit);
        tv_lbl_benefit = DetailsDailog.findViewById(R.id.tv_lbl_benefit);
        tv_lbl_duration = DetailsDailog.findViewById(R.id.tv_lbl_duration);

        try {
            tv_heading.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_HEADING_LBL).getLabel());
            tv_title_display_att.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_DISPLAY_HEADING_LBL).getLabel());
            tv_title_course_benefit.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_COURSE_HEADING_LBL).getLabel());
            tv_lbl_benefit.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_BENEFITS_LBL).getLabel());
            tv_lbl_duration.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_DURATION_LBL).getLabel());
            tv_lbl_name.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_NAME).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rv_membership_detail.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mainActivity);
        rv_benrfits.setLayoutManager(linearLayoutManager1);


        btn_buy_membership.setVisibility(View.GONE);
        tv_heading.setVisibility(View.VISIBLE);

        tv_membership_type.setText(orderdetailinfo.getItemname());
        tv_membership_price.setText("Qr " + String.format("%.2f", Double.parseDouble(orderdetailinfo.getPrice())));
        btn_buy_membership.setVisibility(View.GONE);
        if (orderdetailinfo.getIgst().equals("0.0")) {
            tv_tax.setVisibility(View.GONE);
        } else {
            tv_tax.setText("( " + orderdetailinfo.getIgst() + " % " + orderdetailinfo.getTaxtypename() + " )");
            tv_tax.setVisibility(View.VISIBLE);
        }


        tv_per_month.setText("/" + orderdetailinfo.getDurationname());

        iv_icon_img.setVisibility(View.GONE);

        MembershipDetailAdapter adapter = new MembershipDetailAdapter(mainActivity, membershipdetail, membershipdetail.size());
        rv_membership_detail.setAdapter(adapter);

        if (attributeDetailArrayListNew.size() > 0) {
            MembershipDetailAdapter adapter1 = new MembershipDetailAdapter(mainActivity, attributeDetailArrayListNew, membershipdetail.size());
            rv_benrfits.setAdapter(adapter1);
        } else {
            rv_benrfits.setVisibility(View.GONE);
        }

        DetailsDailog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    DetailsDailog.dismiss();
                    DetailsDailog = null;
                }
                return false;
            }
        });


        btn_close_detail_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsDailog.dismiss();
                DetailsDailog = null;
            }
        });
    }
    //</editor-fold>

    private void SetPrintBillValues(String orderno, String cmp_logo, String cmp_address, String cmp_email, String cmp_contact, String cmp_israngehour,
                                    String membername, String membercontact, ArrayList<Orderdetailinfo> orderdetailinfo, String date) {
        Glide.with(getContext()).load(cmp_logo).into(iv_prt_companylogo);
        tv_contact_number.setText("Mobile No : " + cmp_contact + "\n Email : " + cmp_email);

        tv_store_name.setText("Store Name : " + SessionManagement.getStringValue(getContext(), AppConstant.STORE_NAME, ""));
        tv_ord_no.setText("Order No: grgrggrg" + orderno);
        tv_cust_name.setText("Customer Name : " + membername);
        tv_cust_mobile.setText("Mobile No : " + membercontact);
        tv_address.setText(cmp_address);

        tv_date.setText("Date : " + date);
        tv_salepersonname.setText("Sale Person : " + SessionManagement.getStringValue(getContext(), AppConstant.FULLNAME, ""));

        rv_item_bill.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_bill_paytype.setLayoutManager(new LinearLayoutManager(getContext()));

        Double totalPrice = 0.0;
        Double totalTax = 0.0;
        for (int i = 0; i < orderdetailinfo.size(); i++) {
            totalPrice += Double.parseDouble(orderdetailinfo.get(i).getFinalprice());
            totalTax += Double.parseDouble(orderdetailinfo.get(i).getIgsttaxamt());
        }

        tv_order_qty.setText(String.format("%.2f", totalTax));
        BillAdapter billItemAdapter = new BillAdapter(getContext(), orderdetailinfo);
        rv_item_bill.setAdapter(billItemAdapter);

        tv_bill_total.setText("Qr : " + totalPrice);

        rv_bill_paytype.setVisibility(GONE);

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


        tv_header_paytype.setVisibility(GONE);
        tv_prt_redeeamt.setVisibility(GONE);
        view1.setVisibility(VISIBLE);
        view2.setVisibility(GONE);

        tv_bill_change.setVisibility(GONE);
        tv_takeordine.setVisibility(GONE);

//        final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
//        final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight() + 600), true);
        if (ISCONNECT) {
            final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
            final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight() + 600), true);
            new DownloadImageAsynktask(getContext(), bitmap, bitmap2).execute();
        } else {
            Utility.OpenPrintingOptionDlg(mainActivity, myBinder);
            Toast.makeText(getContext(), getString(R.string.connect_first), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_MEMBERSHIP);
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