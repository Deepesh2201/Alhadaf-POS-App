package com.instanceit.alhadafpos.Fragments.IssueServiceOrder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.IssueServiceOrder;
import com.instanceit.alhadafpos.Entity.Issueorderdetailinfo;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.IssueServiceOrder.Adapter.IssueServiceOrderAdapter;
import com.instanceit.alhadafpos.Fragments.IssueServiceOrder.Adapter.IssueServiceOrderListAdapter;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IssueServiceOrderFragment extends Fragment {

    MainActivity mainActivity;

    //View
    RecyclerView rv_issue_service_order_list;
    TextView tv_nodata;
    FloatingActionButton btn_filter;
    SwipeRefreshLayout swi_layout;

    //Variables
    int PageNumber = 1;
    private boolean loadMore = false;
    int showload = 0;
    private Parcelable recyclerViewState;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_issue_service_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);
    }


    private void Declaration(View view) {
        btn_filter = view.findViewById(R.id.btn_filter);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        rv_issue_service_order_list = view.findViewById(R.id.rv_issue_service_order_list);
        swi_layout = view.findViewById(R.id.swi_layout);
    }

    private void Initialization() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_issue_service_order_list.setLayoutManager(linearLayoutManager);

        PageNumber = 1;
        CallApiGetIssueServiceOrderList();

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


        rv_issue_service_order_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            CallApiGetIssueServiceOrderList();
                        }
                    }
                }
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
        fltmemberid = "%";
        fltfromdate = "";
        flttodate = "";
        str_member = "";
        issueServiceOrderArrayList = new ArrayList<>();
        updatedissueServiceOrderArrayList = new ArrayList<>();
        CallApiGetIssueServiceOrderList();
    }

    ArrayList<IssueServiceOrder> issueServiceOrderArrayList;
    ArrayList<IssueServiceOrder> updatedissueServiceOrderArrayList = new ArrayList<>();
    String fltmemberid = "%", fltfromdate = "", flttodate = "";
    String memberid = "", membercontact = "";

    public void CallApiGetIssueServiceOrderList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststoreorderissuehistory");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("nextpage", "" + PageNumber);
        params.put("fltmemberid", fltmemberid);
        params.put("fltfromdate", fltfromdate);
        params.put("flttodate", flttodate);

//        Log.e("params", "CallApiGetIssueServiceOrderList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ISSUE_ITEM_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "CallApiGetIssueServiceOrderList: " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            showload = jsonObject.getInt("loadmore");

                            if (status == 1) {

                                if (jsonObject.getInt("isorderissuedata") == 1) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("orderissuedata");

                                    if (jsonArray != null && jsonArray.length() != 0) {


                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<IssueServiceOrder>>() {
                                        }.getType();
                                        issueServiceOrderArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                        //Add arraylist
                                        updatedissueServiceOrderArrayList.addAll(issueServiceOrderArrayList);

                                        IssueServiceOrderListAdapter issueServiceOrderListAdapter = new IssueServiceOrderListAdapter(getActivity(), updatedissueServiceOrderArrayList, new IssueServiceOrderListAdapter.OnclickListener() {
                                            @Override
                                            public void onClick(IssueServiceOrder model, int clickMange) {
                                                memberid = model.getUid();
                                                membercontact = model.getMembercontact();
                                                OpenDetailDialog(model, model.getId());
                                            }
                                        });
                                        rv_issue_service_order_list.setAdapter(issueServiceOrderListAdapter);
                                        rv_issue_service_order_list.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                                        loadMore = true;
                                        rv_issue_service_order_list.setVisibility(View.VISIBLE);
                                        tv_nodata.setVisibility(View.GONE);
                                    } else {
                                        rv_issue_service_order_list.setVisibility(View.GONE);
                                        tv_nodata.setVisibility(View.VISIBLE);
                                        tv_nodata.setText(message);
                                    }
                                } else {
                                    rv_issue_service_order_list.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
                                }
                            } else {
                                rv_issue_service_order_list.setVisibility(View.GONE);
                                tv_nodata.setVisibility(View.VISIBLE);
                                tv_nodata.setText(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    BottomSheetDialog detailDialog = null;
    RecyclerView rv_item_detail;
    TextView tv_lbl_member, tv_member_name, tv_lbl_collect_inventory, tv_lbl_ord_number, tv_ord_number, tv_lbl_contact_no_dlg, tv_contact_no_dlg,
            tv_lbl_orde_date, tv_oder_date, tv_submit;
    ImageView iv_close_dlg;
    TextInputLayout txt_comment;
    EditText edt_comment;


    private void OpenDetailDialog(IssueServiceOrder model, String id) {

        if (detailDialog != null && detailDialog.isShowing()) {
            return;
        }

        if (detailDialog == null) {
            detailDialog = new BottomSheetDialog(mainActivity);
            detailDialog.setContentView(R.layout.dialog_issue_service_order_detail);
            FrameLayout bottomSheet = detailDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            detailDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            detailDialog.setCancelable(true);

        }

        rv_item_detail = detailDialog.findViewById(R.id.rv_item_detail);
        tv_lbl_member = detailDialog.findViewById(R.id.tv_lbl_member);
        tv_member_name = detailDialog.findViewById(R.id.tv_member_name);
        tv_lbl_ord_number = detailDialog.findViewById(R.id.tv_lbl_ord_number);
        tv_ord_number = detailDialog.findViewById(R.id.tv_ord_number);
        tv_lbl_contact_no_dlg = detailDialog.findViewById(R.id.tv_lbl_contact_no_dlg);
        tv_contact_no_dlg = detailDialog.findViewById(R.id.tv_contact_no_dlg);
        tv_lbl_orde_date = detailDialog.findViewById(R.id.tv_lbl_orde_date);
        tv_oder_date = detailDialog.findViewById(R.id.tv_oder_date);
        iv_close_dlg = detailDialog.findViewById(R.id.iv_close_dlg);
        tv_submit = detailDialog.findViewById(R.id.tv_submit);
        tv_lbl_collect_inventory = detailDialog.findViewById(R.id.tv_lbl_collect_inventory);
        txt_comment = detailDialog.findViewById(R.id.txt_comment);
        edt_comment = detailDialog.findViewById(R.id.edt_comment);

        tv_member_name.setText(model.getMembername());
        tv_ord_number.setText(model.getOrderno());
        tv_contact_no_dlg.setText(model.getMembercontact());
        tv_oder_date.setText(model.getOrderdate());

        callApiGetOrderDetail(id);

        try {
            tv_lbl_collect_inventory.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SERVICE_TITLE).getLabel());
            tv_lbl_member.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_MEMBER_NAME).getLabel());
            tv_lbl_ord_number.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_ORDER_HIS_ORD_NO).getLabel());
            tv_lbl_contact_no_dlg.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_CUSTOMER_CONTACT_NO).getLabel());
            tv_lbl_orde_date.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_ORDER_HIS_ORD_DATE).getLabel());
            tv_submit.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SERVICE_ISSUE).getLabel());

        } catch (Exception e) {
            e.printStackTrace();
        }

        iv_close_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailDialog.dismiss();
                detailDialog = null;
            }
        });


        detailDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    detailDialog.dismiss();
                    detailDialog = null;
                }
                return false;
            }
        });


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkedArrayList != null && checkedArrayList.size() > 0) {
//                    Log.e("TAG", "onClick: " + new Gson().toJson(checkedArrayList));
                    callApiUpdateIssueServiceOrder(id);
                } else {
                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_ERR_SELECT_ITEM_FIRST).getLabel(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_item_detail.setLayoutManager(linearLayoutManager);


    }

    ArrayList<IssueServiceOrder> issueServiceOrderDetailArrayList;
    ArrayList<Issueorderdetailinfo> checkedArrayList;

    private void callApiGetOrderDetail(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststoreorderissuedetail");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("orderid", id);

//        Log.e("callApiGetOrderDetail", "params:  " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ISSUE_ITEM_URL, params, 2, false, false, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("callApiGetOrderDetail", "onResponse: " + response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    if (status == 1) {

                        issueServiceOrderDetailArrayList = new ArrayList<>();

                        if (jsonObject.getInt("isorderissuedata") == 1) {

                            JSONArray jsonArray = jsonObject.getJSONArray("orderissuedata");

                            if (jsonArray != null && jsonArray.length() != 0) {
                                Gson gson = new Gson();
                                Type listtype = new TypeToken<ArrayList<IssueServiceOrder>>() {
                                }.getType();
                                issueServiceOrderDetailArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                int rem_qty = 0;

                                for (int i = 0; i < issueServiceOrderDetailArrayList.get(0).getIssueorderdetailinfo().size(); i++) {
                                    if (Integer.parseInt(issueServiceOrderDetailArrayList.get(0).getIssueorderdetailinfo().get(i).getRemainQty()) > 0) {
                                        rem_qty += Integer.parseInt(issueServiceOrderDetailArrayList.get(0).getIssueorderdetailinfo().get(i).getRemainQty());
                                    }
                                }

                                if (rem_qty > 0) {
                                    txt_comment.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_HINT_ENTER_COMMENT).getLabel());
                                    tv_submit.setVisibility(View.VISIBLE);
                                    edt_comment.clearFocus();
                                } else {
                                    tv_submit.setVisibility(View.GONE);
                                    edt_comment.setVisibility(View.GONE);
                                    txt_comment.setVisibility(View.GONE);
                                }

                                checkedArrayList = new ArrayList<>();

                                IssueServiceOrderAdapter issueServiceOrderAdapter = new IssueServiceOrderAdapter(getContext(), issueServiceOrderDetailArrayList.get(0).getIssueorderdetailinfo(), true, new IssueServiceOrderAdapter.OnclickListener() {
                                    @Override
                                    public void onClick(ArrayList<Issueorderdetailinfo> issueorderdetailinfoArrayList) {
                                        checkedArrayList = new ArrayList<>();

                                        if (issueorderdetailinfoArrayList.size() > 0) {
                                            for (int i = 0; i < issueorderdetailinfoArrayList.size(); i++) {
                                                if (issueorderdetailinfoArrayList.get(i).getIsitemadded() == 1) {
                                                    checkedArrayList.add(issueorderdetailinfoArrayList.get(i));
                                                }
                                            }
                                        }
//                                        Log.e("checkarrayList", "onClick: " + new Gson().toJson(checkedArrayList));
                                    }
                                });
                                rv_item_detail.setAdapter(issueServiceOrderAdapter);

                                detailDialog.show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        });
    }


    private void callApiUpdateIssueServiceOrder(String id) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "insertstoreorderdata");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", memberid);
        params.put("membercontact", membercontact);
        params.put("cartitemdata", "" + new Gson().toJson(checkedArrayList));
        params.put("soid", id);
        params.put("ordernote", edt_comment.getText().toString());

//        Log.e("params", "callApiUpdateIssueServiceOrder: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Log.e("TAG", "callApiUpdateIssueServiceOrder: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            Utility.initErrorMessagePopupWindow(getActivity(), message);

                            if (status == 1) {

                                try {
                                    detailDialog.dismiss();
                                    detailDialog = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    PageNumber = 1;
                                    issueServiceOrderArrayList = new ArrayList<>();
                                    updatedissueServiceOrderArrayList = new ArrayList<>();
                                    CallApiGetIssueServiceOrderList();
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
        txt_select_member = filterDialog.findViewById(R.id.txt_select_member);
        txt_from_date = filterDialog.findViewById(R.id.txt_from_date);
        txt_to_date = filterDialog.findViewById(R.id.txt_to_date);
        tv_lbl_filter = filterDialog.findViewById(R.id.tv_lbl_filter);

        try {
            txt_select_member.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel());
            txt_from_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_FROM_DATE).getLabel());
            txt_to_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_TO_DATE).getLabel());
            tv_btn_apply.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_CART_BTN_APPLY).getLabel());
            tv_lbl_filter.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_TITLE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        callApiGetMemberList();
        if (!str_member.isEmpty() || !fltfromdate.isEmpty() || !flttodate.isEmpty()) {
            edt_select_member.setText(str_member);
            edt_to_date.setText(flttodate);
            edt_from_date.setText(fltfromdate);
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
                issueServiceOrderArrayList = new ArrayList<>();
                updatedissueServiceOrderArrayList = new ArrayList<>();
                CallApiGetIssueServiceOrderList();
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
                            issueServiceOrderArrayList = new ArrayList<>();
                            updatedissueServiceOrderArrayList = new ArrayList<>();
                            CallApiGetIssueServiceOrderList();
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
                    issueServiceOrderArrayList = new ArrayList<>();
                    updatedissueServiceOrderArrayList = new ArrayList<>();
                    CallApiGetIssueServiceOrderList();
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
                                        str_member = memberArrayList.get(0).getName();
                                        fltmemberid = memberArrayList.get(0).getId();
                                    }

                                    memberDialog = new SearchableSpinnerDialog(mainActivity, memberArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    memberDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_select_member.setText(model.getName());
                                            str_member = model.getName();
                                            fltmemberid = model.getId();
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


    @Override
    public void onResume() {
        super.onResume();
        mainActivity.navParentItem(AppConstant.TXT_APPMENU_ISSUE_SERVICE_ORDER);
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
