package com.instanceit.alhadafpos.Fragments.Report;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.ReportModel;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.Report.Adapter.ReportAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SaleReporFragment extends Fragment {

    MainActivity mainActivity;

    RecyclerView rv_report_listing;
    FloatingActionButton btn_add_service_order, btn_filter;
    SwipeRefreshLayout swi_layout;
    TextView tv_nodata, tv_lbl_status, tv_lbl_tran_id, tv_lbl_order_no, tv_lbl_index, tv_lbl_ord_date, tv_lbl_payment_type,tv_lbl_amount, tv_lbl_member, tv_lbl_ent_by, tv_lbl_more, tv_lbl_opration;


    // variables
    int PageNumber = 1;
    private boolean loadMore = false, isSwipe = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    String categoryid, subcategoryid, itemid, memberid, fromdate, todate, withitemdetail, withfulldetail, str_category, str_subCategory, str_item_name, str_member, fltPaymentTypeId, str_PaymentType_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sale_repor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);

    }


    private void Declaration(View view) {
        rv_report_listing = view.findViewById(R.id.rv_report_listing);
        tv_lbl_status = view.findViewById(R.id.tv_lbl_index);
        tv_lbl_tran_id = view.findViewById(R.id.tv_lbl_tran_id);
        tv_lbl_order_no = view.findViewById(R.id.tv_lbl_order_no);
        tv_lbl_ord_date = view.findViewById(R.id.tv_lbl_ord_date);
        tv_lbl_amount = view.findViewById(R.id.tv_lbl_amount);
        tv_lbl_member = view.findViewById(R.id.tv_lbl_member);
        tv_lbl_ent_by = view.findViewById(R.id.tv_lbl_ent_by);
        tv_lbl_more = view.findViewById(R.id.tv_lbl_more);
        tv_lbl_opration = view.findViewById(R.id.tv_lbl_opration);
        swi_layout = view.findViewById(R.id.swi_layout);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        tv_lbl_payment_type = view.findViewById(R.id.tv_lbl_payment_type);
        tv_lbl_index = view.findViewById(R.id.tv_lbl_index);
        tv_lbl_payment_type = view.findViewById(R.id.tv_lbl_payment_type);
    }


    private void Initialization() {

        //<editor-fold desc="dynamic lbl">
        try {
            tv_lbl_status.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_SERVICE_ORDER_STATUS).getLabel());
            tv_lbl_index.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_INDEX).getLabel());
            tv_lbl_tran_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_TRAN_ID).getLabel());
            tv_lbl_order_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_NO).getLabel());
            tv_lbl_ord_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_DATE).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ORD_AMOUNT).getLabel());
            tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_MEMBER).getLabel());
            tv_lbl_ent_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_ENTRY_BY).getLabel());
            tv_lbl_payment_type.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_PAYMENT_TYPE).getLabel());
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

            categoryid = bundle.getString("categoryid");
            subcategoryid = bundle.getString("subcategoryid");
            itemid = bundle.getString("itemid");
            memberid = bundle.getString("memberid");

            fromdate = bundle.getString("fromdate");
            todate = bundle.getString("todate");

            withitemdetail = bundle.getString("withitemdetail");
            withfulldetail = bundle.getString("withfulldetail");

            fltPaymentTypeId = bundle.getString("fltPaymentTypeId");
            str_PaymentType_name = bundle.getString("str_PaymentType_name");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_report_listing.setLayoutManager(linearLayoutManager);

        CallApiGetReport();

    }

    ArrayList<ReportModel> reportArrayList;
    ArrayList<ReportModel> updatedreportArrayList = new ArrayList<>();

    public void CallApiGetReport() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listsalereportdata");
        params.put("categoryid", categoryid);
        params.put("subcategoryid", "" + subcategoryid);
        params.put("itemid", itemid);
        params.put("memberid", memberid);
        params.put("fromdate", fromdate);
        params.put("todate", todate);
        params.put("withitemdetail", withitemdetail);
        params.put("withfulldetail", withfulldetail);
        params.put("paymenttype", fltPaymentTypeId);

//        Log.e("params", "CallApiGetReport: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.REPORT_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "CallApiGetReport: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            showload = jsonObject.getInt("loadmore");

                            if (status == 1) {

                                if (jsonObject.getInt("issalereportdata") == 1) {

                                    JSONArray OrderHistoryData = jsonObject.getJSONArray("salereportdata");

                                    if (OrderHistoryData != null && OrderHistoryData.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<ReportModel>>() {
                                        }.getType();
                                        reportArrayList = gson.fromJson(OrderHistoryData.toString(), listtype);

                                        //Add arraylist
                                        updatedreportArrayList.addAll(reportArrayList);

                                        ReportAdapter adepter = new ReportAdapter(getActivity(), withitemdetail, withfulldetail, updatedreportArrayList);
                                        rv_report_listing.setAdapter(adepter);
                                        adepter.notifyDataSetChanged();
                                        rv_report_listing.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                        loadMore = true;
                                        rv_report_listing.setVisibility(View.VISIBLE);
                                        tv_nodata.setVisibility(View.GONE);
                                    } else {
                                        rv_report_listing.setVisibility(View.GONE);
                                        tv_nodata.setVisibility(View.VISIBLE);
                                        tv_nodata.setText(message);
                                    }
                                } else {
                                    rv_report_listing.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
                                }

                            } else {
                                rv_report_listing.setVisibility(View.GONE);
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
                    Bundle bundle = new Bundle();
                    bundle.putString("categoryid", categoryid);
                    bundle.putString("subcategoryid", subcategoryid);
                    bundle.putString("itemid", itemid);
                    bundle.putString("memberid", memberid);
                    bundle.putString("fromdate", fromdate);
                    bundle.putString("todate", todate);

                    bundle.putString("withitemdetail", withitemdetail);
                    bundle.putString("withfulldetail", withfulldetail);

                    bundle.putString("str_category", str_category);
                    bundle.putString("str_subCategory", str_subCategory);
                    bundle.putString("str_item_name", str_item_name);
                    bundle.putString("str_member", str_member);

                    bundle.putString("fltPaymentTypeId", fltPaymentTypeId);
                    bundle.putString("str_PaymentType_name", str_PaymentType_name);

                    Fragment fragment = new SaleReporListFragment();
                    fragment.setArguments(bundle);
                    mainActivity.addFragment(fragment);

                    return true;
                }
                return false;
            }
        });
    }


}