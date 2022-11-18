package com.instanceit.alhadafpos.Fragments.Report;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.ReportModel;
import com.instanceit.alhadafpos.Entity.SummaryReport;
import com.instanceit.alhadafpos.Fragments.Report.Adapter.ReportAdapter;
import com.instanceit.alhadafpos.Fragments.Report.Adapter.SummaryReportAdapter;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SummaryReporFragment extends Fragment {

    MainActivity mainActivity;

    RecyclerView rv_report_listing;
    FloatingActionButton btn_add_service_order, btn_filter;
    SwipeRefreshLayout swi_layout;
    TextView tv_nodata, tv_lbl_index, tv_lbl_item_name, tv_lbl_sale_qty, tv_lbl_return_qty, tv_lbl_amount, tv_sale_qty, tv_return_qty, tv_amount,tv_lbl_total;
    View view1, view1_line;


    // variables
    int PageNumber = 1;
    private boolean loadMore = false, isSwipe = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    String itemid, fromdate, todate, storeid, salepersonid, withfreeitem, Action, page, str_item_name, str_store_name, str_salePerson_Name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary_repor, container, false);
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
        tv_lbl_index = view.findViewById(R.id.tv_lbl_index);
        tv_lbl_item_name = view.findViewById(R.id.tv_lbl_item_name);
        tv_lbl_sale_qty = view.findViewById(R.id.tv_lbl_sale_qty);
        tv_lbl_return_qty = view.findViewById(R.id.tv_lbl_return_qty);
        tv_lbl_amount = view.findViewById(R.id.tv_lbl_amount);
        swi_layout = view.findViewById(R.id.swi_layout);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        view1 = view.findViewById(R.id.view1);
        view1_line = view.findViewById(R.id.view1_line);
        tv_sale_qty = view.findViewById(R.id.tv_sale_qty);
        tv_return_qty = view.findViewById(R.id.tv_return_qty);
        tv_amount = view.findViewById(R.id.tv_amount);
        tv_lbl_total = view.findViewById(R.id.tv_lbl_total);
    }


    private void Initialization() {

        try {
            tv_lbl_index.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_INDEX).getLabel());
            tv_lbl_item_name.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_ITEM_NAME).getLabel());
            tv_lbl_sale_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_SALE_QTY).getLabel());
            tv_lbl_return_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_RETURN_QTY).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_SALE_AMOUNT).getLabel());
            tv_lbl_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }


        Bundle bundle = getArguments();
        if (bundle != null) {
            storeid = bundle.getString("storeid");
            salepersonid = bundle.getString("salepersonid");
            itemid = bundle.getString("itemid");

            fromdate = bundle.getString("fromdate");
            todate = bundle.getString("todate");
            withfreeitem = bundle.getString("withfreeitem");
            Action = bundle.getString("Action");
            page = bundle.getString("page");

            str_item_name = bundle.getString("str_item_name");
            str_store_name = bundle.getString("str_store_name");
            str_salePerson_Name = bundle.getString("str_salePerson_Name");

            if (Action.equals("listitemsalesummaryreportdata")) {
                tv_lbl_return_qty.setVisibility(View.VISIBLE);
                tv_return_qty.setVisibility(View.VISIBLE);
                view1.setVisibility(View.VISIBLE);
                view1_line.setVisibility(View.VISIBLE);
            } else {
                tv_lbl_return_qty.setVisibility(View.GONE);
                tv_return_qty.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view1_line.setVisibility(View.GONE);
            }
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_report_listing.setLayoutManager(linearLayoutManager);

        CallApiGetReport();

    }

    ArrayList<SummaryReport> reportArrayList;

    public void CallApiGetReport() {
        Map<String, String> params = new HashMap<>();
        params.put("action", Action);
        params.put("storeid", storeid);
        params.put("salepersonid", salepersonid);
        params.put("itemid", itemid);
        params.put("fromdate", fromdate);
        params.put("todate", todate);
        if (Action.equals("listitemsalesummaryreportdata")) {
            params.put("withfreeitem", withfreeitem);
        }

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


                            if (status == 1) {

                                if (jsonObject.getInt("issalesummaryreportdata") == 1) {

                                    JSONArray OrderHistoryData = jsonObject.getJSONArray("salesummaryreportdata");

                                    if (OrderHistoryData != null && OrderHistoryData.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<SummaryReport>>() {
                                        }.getType();
                                        reportArrayList = gson.fromJson(OrderHistoryData.toString(), listtype);

                                        Double sale_qty = 0.00, return_qty = 0.00, amount = 0.00;
                                        for (int i = 0; i < reportArrayList.size(); i++) {
                                            sale_qty += Double.parseDouble(reportArrayList.get(i).getSaleQty());
                                            if (Action.equals("listitemsalesummaryreportdata")) {
                                                return_qty += Double.parseDouble(reportArrayList.get(i).getReturnQty());
                                            }
                                            amount += Double.parseDouble(reportArrayList.get(i).getSaleAmount());
                                        }
                                        tv_sale_qty.setText("" + sale_qty);
                                        tv_return_qty.setText("" + return_qty);
                                        tv_amount.setText("" + String.format("%.2f", amount));

                                        SummaryReportAdapter adepter = new SummaryReportAdapter(getActivity(), Action, reportArrayList);
                                        rv_report_listing.setAdapter(adepter);
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


    private void onBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Bundle bundle = new Bundle();

                    bundle.putString("storeid", storeid);
                    bundle.putString("salepersonid", salepersonid);
                    bundle.putString("itemid", itemid);
                    bundle.putString("fromdate", fromdate);
                    bundle.putString("todate", todate);

                    bundle.putString("str_item_name", str_item_name);
                    bundle.putString("str_store_name", str_store_name);
                    bundle.putString("str_salePerson_Name", str_salePerson_Name);
                    bundle.putString("withfreeitem", withfreeitem);

                    bundle.putString("Action", Action);
                    bundle.putString("page", page);

                    bundle.putString("page", page);
                    bundle.putString("back_manage", "back_manage");
                    Fragment fragment1 = new ReportFilterFragment();
                    fragment1.setArguments(bundle);
                    mainActivity.addFragment(fragment1);
                    return true;
                }
                return false;
            }
        });
    }


}