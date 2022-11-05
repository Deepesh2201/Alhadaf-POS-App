package com.instanceit.alhadafpos.Fragments.RangeRelase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.instanceit.alhadafpos.Entity.OrderHistoryList;
import com.instanceit.alhadafpos.Entity.RangeRelease;
import com.instanceit.alhadafpos.Entity.Returnorderdetailinfo;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.InventoryReturn.Adapter.CollectInventoryItemAdapter;
import com.instanceit.alhadafpos.Fragments.RangeRelase.Adapter.RangeRelaseListAdapter;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AssignedRangeListFragment extends Fragment {

    MainActivity mainActivity;

    //Views
    RecyclerView rv_listassignedrangemember;
    SwipeRefreshLayout swi_layout;
    TextView tv_nodata, tv_lbl_select_date;
    EditText edt_sel_date;
    String message;
    String store_id = "";
    FloatingActionButton float_filter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_returnable_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);

    }


    private void Declaration(View view) {
        rv_listassignedrangemember = view.findViewById(R.id.rv_listassignedrangemember);
        swi_layout = view.findViewById(R.id.swi_layout);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        edt_sel_date = view.findViewById(R.id.edt_sel_date);
        float_filter = view.findViewById(R.id.float_filter);
    }


    private void Initialization() {
        store_id = SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, "");
        try {
            tv_lbl_select_date.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_DATE).getLabel());
            edt_sel_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_DATE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_listassignedrangemember.setLayoutManager(linearLayoutManager);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String date = df.format(c.getTime());
        edt_sel_date.setText(date);
        CallApiGetRangeReleaseList();

        swi_layout.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE);

        swi_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swi_layout.setRefreshing(false);
                        swi_layout.destroyDrawingCache();
                        swi_layout.clearAnimation();

//                        edt_sel_date.setText(date);
                        fltmemberid = "%";
                        fltrangeid = "%";
                        fltlaneid = "%";
                        fltfromdate = "";
                        flttodate = "";
                        CallApiGetRangeReleaseList();
                    }
                }, 200);
            }
        });

        edt_sel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialogBirthdateDate(getContext(), edt_sel_date);
            }
        });

        float_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFilterDialog();
            }
        });
    }

    public static DatePickerDialog datePicker;
    private static String dateToStr = "";
    public static String date = "";

    public void showDatePickerDialogBirthdateDate(Context context, final EditText editText) {
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
            String selectedDate = editText.getText().toString();
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

        datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = formatDate(year, monthOfYear, dayOfMonth);
                editText.setText(date);
                CallApiGetRangeReleaseList();
            }
        }, yy, mm, dd);

        datePicker.show();


        datePicker.getDatePicker().setMaxDate(new Date().getTime());

        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0B2347"));
        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0B2347"));
    }

    public static String formatDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }


    private void setAdapter() {
        if (rangeReleaseArrayList != null && rangeReleaseArrayList.size() > 0) {
            rangeRelaseListAdapter = new RangeRelaseListAdapter(getActivity(), rangeReleaseArrayList, new RangeRelaseListAdapter.OnclickListener() {
                @Override
                public void onClick(RangeRelease model) {
                    OpenConfirmDialog(model.getRangeassignid());
                }
            });
            rv_listassignedrangemember.setAdapter(rangeRelaseListAdapter);
            rangeRelaseListAdapter.notifyDataSetChanged();

            rv_listassignedrangemember.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        } else {
            tv_nodata.setVisibility(View.VISIBLE);
            rv_listassignedrangemember.setVisibility(View.GONE);
            tv_nodata.setText(message);
        }
    }

    Dialog confirmDialog = null;

    private void OpenConfirmDialog(String rangeassignid) {
        try {

            if (confirmDialog != null && confirmDialog.isShowing())
                return;

            confirmDialog = new Dialog(getActivity());

            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmDialog.setContentView(R.layout.dialog_exit_message_box);
            TextView dialogTitle = confirmDialog.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_MESSAGE_CONFIRM).getLabel() + "?");
            Button btnOk = confirmDialog.findViewById(R.id.btnyes);
            Button btnno = confirmDialog.findViewById(R.id.btnno);
            ImageView iv_gif = confirmDialog.findViewById(R.id.iv_gif);
            LinearLayout ln_dlg_main = confirmDialog.findViewById(R.id.ln_dlg_main);
            RelativeLayout rl_dialog_main = confirmDialog.findViewById(R.id.rl_dialog_main);

            try {
                btnOk.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_BTN_CONFIRM).getLabel());
                btnno.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_PER_BTN_CANCEL).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_lefttoright);
            Glide.with(getActivity()).asGif().load(R.drawable.loader).into(iv_gif);
            iv_gif.startAnimation(animation);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CallApiToReleaseRange(rangeassignid);
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    confirmDialog.dismiss();
                    confirmDialog = null;
                }
            });
            confirmDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<RangeRelease> rangeReleaseArrayList;
    RangeRelaseListAdapter rangeRelaseListAdapter;

    private void CallApiGetRangeReleaseList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listassignedrangemember");
        params.put("fltmemberid", fltmemberid);
        params.put("fltrangeid", fltrangeid);
        params.put("fltlaneid", fltlaneid);
        params.put("fltfromdate", fltfromdate);
        params.put("flttodate", flttodate);
        params.put("storeid", store_id);
//        Log.e("CallApiGetRangeReleaseList", "params: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.OPERATION_FLOW_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("CallApiGetRangeReleaseList", "onResponse: " + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            message = jsonObject.getString("message");
//                            String date = jsonObject.getString("date");

                            if (status == 1) {
//                                edt_sel_date.setText(date);
                                JSONArray jsonArray = jsonObject.getJSONArray("memberdata");

                                if (jsonArray != null && jsonArray.length() != 0) {

                                    rangeReleaseArrayList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<RangeRelease>>() {
                                    }.getType();
                                    rangeReleaseArrayList = gson.fromJson(jsonArray.toString(), listtype);
                                    setAdapter();
                                }
                            } else {
                                tv_nodata.setVisibility(View.VISIBLE);
                                rv_listassignedrangemember.setVisibility(View.GONE);
                                tv_nodata.setText(message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void CallApiToReleaseRange(String rangeassignid) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "releaserangedata");
        params.put("rangeassignid", rangeassignid);
//        Log.e("CallApiToReleaseRange", "onResponse: " + params);

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.OPERATION_FLOW_URL, params, 2, true, true, new VolleyResponseListener() {
            @Override
            public void onResponse(String response) {
//                Log.e("CallApiToReleaseRange", "onResponse: " + response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    int status = jsonObject.getInt("status");
                    String message = jsonObject.getString("message");
                    Utility.initErrorMessagePopupWindow(mainActivity, message);
                    if (status == 1) {
                        try {
                            confirmDialog.dismiss();
                            confirmDialog = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        CallApiGetRangeReleaseList();
                        Utility.initErrorMessagePopupWindow(mainActivity, message);
                    } else {
                        Utility.initErrorMessagePopupWindow(mainActivity, message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //<editor-fold desc="Filter dialog">
    BottomSheetDialog filterDialog = null;
    EditText edt_select_member, edt_from_date, edt_to_date, edt_select_range, edt_select_lane;
    TextInputLayout txt_select_range, txt_select_lane, txt_select_member, txt_from_date, txt_to_date;
    TextView tv_btn_reset, tv_btn_apply;
    ImageView iv_close_filter_dlg;
    String fltfromdate = "", flttodate = "", fltrangeid = "%", fltlaneid = "%", str_member = "", str_lane = "", str_range = "";

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
        edt_select_range = filterDialog.findViewById(R.id.edt_select_range);
        edt_select_lane = filterDialog.findViewById(R.id.edt_select_lane);
        txt_select_range = filterDialog.findViewById(R.id.txt_select_range);
        txt_select_lane = filterDialog.findViewById(R.id.txt_select_lane);

        callApiGetFilterMemberList();
        callApiGetFilterRangeList();

        if (!str_member.isEmpty() || !fltfromdate.isEmpty() || !flttodate.isEmpty() || !str_lane.isEmpty() || !str_range.isEmpty()) {
            edt_select_member.setText(str_member);
            edt_to_date.setText(flttodate);
            edt_from_date.setText(fltfromdate);
            edt_select_lane.setText(str_lane);
            edt_select_range.setText(str_range);
        }

        txt_select_range.setVisibility(View.VISIBLE);
        txt_select_lane.setVisibility(View.VISIBLE);

        try {
            txt_select_member.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel());
            txt_from_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_FROM_DATE).getLabel());
            txt_to_date.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_TO_DATE).getLabel());
            tv_btn_apply.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_CART_BTN_APPLY).getLabel());
            tv_btn_apply.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_FLT_TITLE).getLabel());
            txt_select_range.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_RANGE).getLabel());
            txt_select_lane.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_LANE).getLabel());
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
                fltrangeid = "";
                fltlaneid = "";
                str_lane = "";
                str_range = "";
                str_member = "";
                rangeReleaseArrayList = new ArrayList<>();
                CallApiGetRangeReleaseList();
                filterDialog.dismiss();
                filterDialog = null;
            }
        });

        tv_btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_to_date.getText().toString().isEmpty() && !edt_to_date.getText().toString().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date1 = sdf.parse(edt_from_date.getText().toString());
                        Date date2 = sdf.parse(edt_to_date.getText().toString());
                        if (date2.compareTo(date1) >= 0) {
                            fltfromdate = edt_from_date.getText().toString();
                            flttodate = edt_to_date.getText().toString();
                            rangeReleaseArrayList = new ArrayList<>();
                            CallApiGetRangeReleaseList();
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
                    rangeReleaseArrayList = new ArrayList<>();
                    CallApiGetRangeReleaseList();
                    filterDialog.dismiss();
                    filterDialog = null;
                }

            }
        });

    }

    ArrayList<Model> memberFilterArrayList;
    public SearchableSpinnerDialog spinnerDialog;
    String fltmemberid = "%";

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
                                    }

                                    spinnerDialog = new SearchableSpinnerDialog(mainActivity, memberFilterArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_SELECT_PERSON).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_select_member.setText(model.getName());
                                            fltmemberid = model.getId();
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

    ArrayList<Model> rangeFilterArrayList;
    public SearchableSpinnerDialog RangespinnerDialog;

    private void callApiGetFilterRangeList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listrange");
        params.put("isall", "1");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MASTER_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("israngedata") == 1) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("rangedata");

                                    rangeFilterArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    rangeFilterArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (edt_select_range.getText().toString().isEmpty()) {
                                        edt_select_range.setText(rangeFilterArrayList.get(0).getName());
                                        fltrangeid = rangeFilterArrayList.get(0).getId();
                                        str_range = rangeFilterArrayList.get(0).getName();
                                    }
                                    callApiGetFilterLaneList();

                                    RangespinnerDialog = new SearchableSpinnerDialog(mainActivity, rangeFilterArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_RANGE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    RangespinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_select_range.setText(model.getName());
                                            fltrangeid = model.getId();
                                            str_range = model.getName();
                                            callApiGetFilterLaneList();
                                        }
                                    });
                                }
                            }

                            edt_select_range.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        RangespinnerDialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    ArrayList<Model> laneFilterArrayList;
    public SearchableSpinnerDialog LanespinnerDialog;

    private void callApiGetFilterLaneList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listrangelane");
        params.put("rangeid", fltrangeid);
        params.put("isall", "1");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MASTER_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("israngelanedata") == 1) {

                                    JSONArray jsonArray = jsonObject.getJSONArray("rangelanedata");

                                    laneFilterArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    laneFilterArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    if (edt_select_lane.getText().toString().isEmpty()) {
                                        edt_select_lane.setText(laneFilterArrayList.get(0).getName());
                                        fltlaneid = laneFilterArrayList.get(0).getId();
                                        str_lane = laneFilterArrayList.get(0).getName();
                                    }

                                    LanespinnerDialog = new SearchableSpinnerDialog(mainActivity, laneFilterArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_ERR_SELECT_LANE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                    LanespinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            edt_select_lane.setText(model.getName());
                                            fltlaneid = model.getId();
                                            str_lane = model.getName();
                                        }
                                    });
                                }
                            }

                            edt_select_lane.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        LanespinnerDialog.showSpinerDialog();
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
        mainActivity.navParentItem(AppConstant.TXT_RANGE_RELEASE);
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