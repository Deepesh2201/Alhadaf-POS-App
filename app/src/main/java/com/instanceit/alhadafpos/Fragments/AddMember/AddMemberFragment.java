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
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.IBinder;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.FinalCartListModel;
import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.MyCart;
import com.instanceit.alhadafpos.Entity.PaymentModel;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.BillIMemberShipAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.CartListAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.CoursesAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.MembershipCoursesListAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.MembershipDetailAdapter;
import com.instanceit.alhadafpos.Fragments.AddMember.Adapter.PackageCategoryListAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.Dashboard.DashboardFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.BillItemAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.BillPayTypeAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SwipeToDeleteCallback;
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

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddMemberFragment extends Fragment {

    MainActivity mainActivity;

    //Views
    //EditText edt_category, edt_subcat;
    RecyclerView rv_item, rv_cart, rv_category;
    TextView tv_nodata, tv_nodata_cart, tv_sub_amount, tv_cart_switch, tv_taxable_amount, tv_total_tax, tv_cancel_order, tv_exclusive_tax, tv_inclusive_tax, tv_final_total, tv_proceed, tv_lbl_sub_tot, tv_lbl_taxable_amount, tv_lbl_tax_amount, tv_lbl_total;
    RelativeLayout rl_nodata, rl_data, rl_cart_data, rl_exclusive_tax, rl_inclusive_tax;
    LinearLayout ll_maintotal;
    SwipeRefreshLayout swiperefresh;
    ImageView iv_subcat_filter;


    //variables
    SearchableSpinnerDialog searchableSpinnerDialog;
    String cat_id;
    String subcat_id;
    String iscourse;
    String payment_mode_id = "";
    public static int lastCheckedPos = 0;
    //    ArrayList<Item> cartArrayList;
    ArrayList<FinalCartListModel> finalCartArrayList;

    Bundle bundle;
    String member_id, member_name;
    int coupon_status;

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


    //classes
    CartListAdapter cartListAdapter;
    MembershipCoursesListAdapter membershipCoursesListAdapter;


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
        return inflater.inflate(R.layout.fragment_add_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);
    }


    private void Declaration(View view) {
        rv_category = view.findViewById(R.id.rv_category);
        iv_subcat_filter = view.findViewById(R.id.iv_subcat_filter);
        rv_item = view.findViewById(R.id.rv_item);
        rl_nodata = view.findViewById(R.id.rl_nodata);
        tv_nodata = view.findViewById(R.id.tv_nodata);
        rl_data = view.findViewById(R.id.rl_data);
        swiperefresh = view.findViewById(R.id.swiperefresh);
        rl_cart_data = view.findViewById(R.id.rl_cart_data);
        ll_maintotal = view.findViewById(R.id.ll_maintotal);
        tv_nodata_cart = view.findViewById(R.id.tv_nodata_cart);
        rv_cart = view.findViewById(R.id.rv_cart);
        tv_sub_amount = view.findViewById(R.id.tv_sub_amount);
        tv_taxable_amount = view.findViewById(R.id.tv_taxable_amount);
        tv_total_tax = view.findViewById(R.id.tv_total_tax);
        tv_proceed = view.findViewById(R.id.tv_proceed);
        tv_cancel_order = view.findViewById(R.id.tv_cancel_order);
        tv_cart_switch = view.findViewById(R.id.tv_cart_switch);
//        tv_exclusive_tax = view.findViewById(R.id.tv_exclusive_tax);
//        tv_inclusive_tax = view.findViewById(R.id.tv_inclusive_tax);
//        rl_exclusive_tax = view.findViewById(R.id.rl_exclusive_tax);
//        rl_inclusive_tax = view.findViewById(R.id.rl_inclusive_tax);
        tv_final_total = view.findViewById(R.id.tv_final_total);
        tv_lbl_sub_tot = view.findViewById(R.id.tv_lbl_sub_tot);
        tv_lbl_taxable_amount = view.findViewById(R.id.tv_lbl_taxable_amount);
        tv_lbl_tax_amount = view.findViewById(R.id.tv_lbl_tax_amount);
        tv_lbl_total = view.findViewById(R.id.tv_lbl_total);
        tv_proceed = view.findViewById(R.id.tv_proceed);

        frameLayout_bill = view.findViewById(R.id.framelayout_bill);
        tv_salepersonname = view.findViewById(R.id.tv_salepersonname);
        tv_cust_name = view.findViewById(R.id.tv_cust_name);
        rv_item_bill = view.findViewById(R.id.rv_item_bill);
        tv_cust_mobile = view.findViewById(R.id.tv_cust_mobile);
        tv_date = view.findViewById(R.id.tv_date);
        rv_item = view.findViewById(R.id.rv_item);
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

        //<editor-fold desc="DYNAMIC LBL">
        try {
            tv_cart_switch.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ADD_MEM_SWITCH_CART).getLabel());
            tv_lbl_sub_tot.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_SUB_TOT).getLabel());
            tv_lbl_taxable_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TAXABLE_AMOUNT).getLabel());
            tv_lbl_tax_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TAX_AMOUNT).getLabel());
            tv_lbl_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_AMOUNT).getLabel());
            tv_cancel_order.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_BTN_CANCEL_ORDER).getLabel());
            tv_proceed.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_BTN_PROCEED).getLabel());
            tv_nodata_cart.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_EMPTY_CART).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rv_category.setLayoutManager(LayoutManager);

        mainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        SessionManagement.savePreferences(mainActivity, AppConstant.CARTARRAY, "");

        bundle = getArguments();
        if (bundle != null) {
            member_id = bundle.getString("id");
            member_name = bundle.getString("member_Name");
        }

        callApiGetCatList();

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        rv_item.setLayoutManager(linearLayoutManager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_cart.setLayoutManager(layoutManager);

        swiperefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swiperefresh.setRefreshing(false);
                        swiperefresh.destroyDrawingCache();
                        swiperefresh.clearAnimation();

                        callApiGetsubcatList();
                        callApiGetItemList();

                    }
                }, 200);
            }
        });

        CreateCartFinalJson();

        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPaymentMethodDialog();
            }
        });

        tv_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalCartArrayList.get(0).getCartiteminfo() != null && finalCartArrayList.get(0).getCartiteminfo().size() > 0) {
                    initExitMessagePopupWindow(getActivity(), Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_CLEAR_CART).getLabel() + "?");
                } else {
                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_EMPTY_CART).getLabel(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //<editor-fold desc="payment methode dialog">
    public Dialog paymentDialog;
    TextInputLayout til_rf_number;
    EditText edt_order_note, edt_rf_number;
    LinearLayout ln_apply_cpn, ln_btn_apply_code, ln_remove_coupon;
    Button btn_cancel, btn_submit;
    EditText edt_coupon_code;
    public TextView tv_message, tv_remove_code, tv_taxable, tv_cpn_amount, tv_tax_amount, tv_total_amount, tv_select_payment_mode, tv_header;


    private void OpenPaymentMethodDialog() {

        if (paymentDialog != null && paymentDialog.isShowing()) return;

        if (paymentDialog == null) {
            paymentDialog = new Dialog(getContext());
            paymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            paymentDialog.setContentView(R.layout.dialog_paymentmethode_alhadaf);
            Window window = paymentDialog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paymentDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            paymentDialog.setCancelable(false);
            paymentDialog.show();
        }

        TextView tv_lbl_taxable_amount, tv_lbl_cpn_discount, tv_lbl_tax_amount, tv_lbl_total, tv_lbl_apply_cpn;
        til_rf_number = paymentDialog.findViewById(R.id.til_rf_number);
        edt_order_note = paymentDialog.findViewById(R.id.edt_order_note);
        edt_rf_number = paymentDialog.findViewById(R.id.edt_rf_number);
        btn_cancel = paymentDialog.findViewById(R.id.btn_cancel);
        btn_submit = paymentDialog.findViewById(R.id.btn_submit);
        ln_apply_cpn = paymentDialog.findViewById(R.id.ln_apply_cpn);
        ln_btn_apply_code = paymentDialog.findViewById(R.id.ln_btn_apply_code);
        edt_coupon_code = paymentDialog.findViewById(R.id.edt_coupon_code);
        tv_message = paymentDialog.findViewById(R.id.tv_message);
        tv_remove_code = paymentDialog.findViewById(R.id.tv_remove_code);
        ln_remove_coupon = paymentDialog.findViewById(R.id.ln_remove_coupon);
        tv_taxable = paymentDialog.findViewById(R.id.tv_taxable);
        tv_cpn_amount = paymentDialog.findViewById(R.id.tv_cpn_amount);
        tv_tax_amount = paymentDialog.findViewById(R.id.tv_tax_amount);
        tv_total_amount = paymentDialog.findViewById(R.id.tv_total_amount);
        tv_select_payment_mode = paymentDialog.findViewById(R.id.tv_select_payment_mode);
        tv_header = paymentDialog.findViewById(R.id.tv_header);
        tv_lbl_taxable_amount = paymentDialog.findViewById(R.id.tv_lbl_taxable_amount);
        tv_lbl_cpn_discount = paymentDialog.findViewById(R.id.tv_lbl_cpn_discount);
        tv_lbl_tax_amount = paymentDialog.findViewById(R.id.tv_lbl_tax_amount);
        tv_lbl_total = paymentDialog.findViewById(R.id.tv_lbl_total);
        tv_lbl_apply_cpn = paymentDialog.findViewById(R.id.tv_lbl_apply_cpn);
        tv_remove_code = paymentDialog.findViewById(R.id.tv_remove_code);


        //<editor-fold desc="dynamic lbl">
        try {
            tv_header.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE).getLabel());
            tv_lbl_taxable_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TAXABLE_AMOUNT).getLabel());
            tv_lbl_cpn_discount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_CPN_DIS_AMOUNT).getLabel());
            tv_lbl_tax_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TAX_AMOUNT).getLabel());
            tv_lbl_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_AMOUNT).getLabel());
            btn_submit.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_BTN_SUBMIT).getLabel());
            btn_cancel.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_LOGOUT_CANCLE).getLabel());
            edt_coupon_code.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_APPLY_CODE).getLabel());
            tv_lbl_apply_cpn.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_BTN_APPLY).getLabel());
            tv_remove_code.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_BTN_REMOVE_CPN).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        TotalAmount();
        callApiGetpaymentList();
        til_rf_number.setVisibility(View.GONE);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiApplyCoupon(edt_coupon_code.getText().toString().trim(), new Gson().toJson(finalCartArrayList), member_id, 1, true);
                paymentDialog.dismiss();
                paymentDialog = null;
            }
        });

        edt_coupon_code.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    if (edt_coupon_code.getText().toString().isEmpty()) {
                        edt_coupon_code.requestFocus();
                        edt_coupon_code.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_ERR_CPN_CODE).getLabel());
                    } else {
                        callApiApplyCoupon(edt_coupon_code.getText().toString().trim(), new Gson().toJson(finalCartArrayList), member_id, 0, true);
                        if (coupon_status == 1) {
                            ln_remove_coupon.setVisibility(View.VISIBLE);
                            ln_apply_cpn.setVisibility(View.GONE);
                        } else {
                            ln_remove_coupon.setVisibility(View.GONE);
                            ln_apply_cpn.setVisibility(View.VISIBLE);
                        }
                    }
                }
                return false;
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate()) {
                    callApiInsertOrder();
                }
            }
        });

        ln_btn_apply_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_coupon_code.getText().toString().isEmpty()) {
                    edt_coupon_code.requestFocus();
                    edt_coupon_code.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_ERR_CPN_CODE).getLabel());
                } else {
                    callApiApplyCoupon(edt_coupon_code.getText().toString().trim(), new Gson().toJson(finalCartArrayList), member_id, 0, true);
                    if (coupon_status == 1) {
                        ln_remove_coupon.setVisibility(View.VISIBLE);
                        ln_apply_cpn.setVisibility(View.GONE);
                    } else {
                        ln_remove_coupon.setVisibility(View.GONE);
                        ln_apply_cpn.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        tv_remove_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ln_btn_apply_code.setVisibility(View.VISIBLE);
                ln_remove_coupon.setVisibility(View.GONE);
                callApiApplyCoupon(edt_coupon_code.getText().toString().trim(), new Gson().toJson(finalCartArrayList), member_id, 1, true);
            }
        });

        paymentDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    callApiApplyCoupon(edt_coupon_code.getText().toString().trim(), new Gson().toJson(finalCartArrayList), member_id, 1, true);
                    paymentDialog.dismiss();
                    paymentDialog = null;
                }
                return false;
            }
        });
    }

    private boolean isValidate() {
        if (tv_select_payment_mode != null && edt_rf_number != null) {
            if (payment_mode_id.isEmpty()) {
                tv_select_payment_mode.requestFocus();
                tv_select_payment_mode.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_ERR_PAYMENT_MODE).getLabel());
            } else if (payment_mode_id.equals("2") && edt_rf_number.getText().toString().isEmpty()) {
                edt_rf_number.requestFocus();
                edt_rf_number.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_ERR_REFERENCE_NUMBER).getLabel());
            } else {
                return true;
            }
        }
        return false;
    }
    //</editor-fold>


    //<editor-fold desc="Call Api item List , category list and SubCategory">
    ArrayList<MembershipCourseItem> membershipCourseItemArrayList;

    private void callApiGetItemList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listorderitem");
        params.put("categoryid", cat_id);
        params.put("subcategoryid", subcat_id);
        params.put("iscourse", iscourse);
        params.put("id", "");//Pass When Show Mship/Package Detail
//        Log.e("params", "callApiGetItemList: " + params);

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MEMBERSHIP_ORDER_URL, params, 1, false, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetItemList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("isitemdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("itemdata");

                                    membershipCourseItemArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<MembershipCourseItem>>() {
                                    }.getType();

                                    membershipCourseItemArrayList = gson.fromJson(jsonArray.toString(), listtype);


                                    if (iscourse.equals("0")) {

                                        membershipCoursesListAdapter = new MembershipCoursesListAdapter(getActivity(), membershipCourseItemArrayList, new MembershipCoursesListAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(MembershipCourseItem model, int ClickManage) {
                                                // clickManage : 1 for full detail / 0 for add to cart
                                                // type: 1-membership /2- Special Package / 0-courses

                                                if (ClickManage == 1) {
                                                    DialogDetail(mainActivity, model, membershipCourseItemArrayList, 1, true);
                                                }

                                                if (ClickManage == 0) {
                                                    AddToCart(getContext(), model);
                                                }
                                            }
                                        });
                                        rv_item.setAdapter(membershipCoursesListAdapter);
                                        membershipCoursesListAdapter.notifyDataSetChanged();

                                    } else {
                                        CoursesAdapter adepter = new CoursesAdapter(getActivity(), membershipCourseItemArrayList, true, new CoursesAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(MembershipCourseItem model, int ClickManage) {
                                                // clickManage : 1 for full detail / 0 for add to cart
                                                // type: 1-membership /2- Special Package / 0-courses

                                                if (ClickManage == 1) {
                                                    DialogDetail(mainActivity, model, membershipCourseItemArrayList, 1, true);
                                                }
                                                if (ClickManage == 0) {
                                                    AddToCart(getContext(), model);
                                                }
                                            }
                                        });
                                        rv_item.setAdapter(adepter);
                                        adepter.notifyDataSetChanged();
                                    }

                                    //    rl_data.setVisibility(View.VISIBLE);
                                    rv_item.setVisibility(View.VISIBLE);
                                    rl_nodata.setVisibility(View.GONE);

                                } else {
                                    rv_item.setVisibility(View.GONE);
                                    rl_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText(message);
//                                    rl_data.setVisibility(View.GONE);
                                }
                            } else {
                                rv_item.setVisibility(View.GONE);
                                rl_nodata.setVisibility(View.VISIBLE);
                                tv_nodata.setText(message);
//                                rl_data.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    ArrayList<Model> categoryArrayList;

    private void callApiGetCatList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listordercategory");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MEMBERSHIP_ORDER_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetMemberList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("iscategorydata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("categorydata");

                                    categoryArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();

                                    categoryArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    cat_id = categoryArrayList.get(0).getId();
                                    iscourse = categoryArrayList.get(0).getIscourse();
                                    lastCheckedPos = 0;
                                    callApiGetsubcatList();
                                    callApiGetItemList();

                                    PackageCategoryListAdapter packageCategoryListAdapter = new PackageCategoryListAdapter(getActivity(), categoryArrayList, new PackageCategoryListAdapter.subCatClickInterface() {
                                        @Override
                                        public void clickOnSubCat(Model model) {
                                            cat_id = model.getId();
                                            iscourse = model.getIscourse();
                                            callApiGetsubcatList();
                                            callApiGetItemList();
                                        }
                                    });
                                    rv_category.setAdapter(packageCategoryListAdapter);
                                    packageCategoryListAdapter.notifyDataSetChanged();

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    ArrayList<Model> subcategoryArraList;

    private void callApiGetsubcatList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listordersubcategory");
        params.put("categoryid", cat_id);

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MEMBERSHIP_ORDER_URL, params, 1, true, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("response", "callApiGetsubcatList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {
                                if (jsonObject.getInt("issubcategorydata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("subcategorydata");

                                    subcategoryArraList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Model>>() {
                                    }.getType();

                                    subcategoryArraList = gson.fromJson(jsonArray.toString(), listtype);

                                    // edt_subcat.setText(subcategoryArraList.get(0).getName());
                                    subcat_id = subcategoryArraList.get(0).getId();
                                    callApiGetItemList();

                                    SearchableSpinnerDialog spinnerDialog = new SearchableSpinnerDialog(mainActivity, subcategoryArraList, Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SUB_CATEGORY_TITLE).getLabel(), R.style.DialogAnimations_SmileWindow);
                                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                        @Override
                                        public void onClick(Model model, int i) {
                                            subcat_id = model.getId();
                                            // edt_subcat.setText(model.getName());
                                            callApiGetItemList();
                                        }
                                    });


                                    iv_subcat_filter.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            spinnerDialog.showSpinerDialog();
                                        }
                                    });
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


    //<editor-fold desc="Set Adapter">
    public void SetCartAdapter() {
        if (finalCartArrayList.get(0).getCartiteminfo() != null && finalCartArrayList.get(0).getCartiteminfo().size() > 0) {
            int couponapply = finalCartArrayList.get(0).getCouponapply();
            cartListAdapter = new CartListAdapter(getActivity(), finalCartArrayList.get(0).getCartiteminfo(), AddMemberFragment.this);
            rv_cart.setAdapter(cartListAdapter);
            cartListAdapter.notifyDataSetChanged();
            enableSwipeToDeleteAndUndo();
            TotalAmount();
            tv_nodata_cart.setVisibility(View.GONE);
            rl_cart_data.setVisibility(View.VISIBLE);
            ll_maintotal.setVisibility(View.VISIBLE);
        } else {
            tv_nodata_cart.setVisibility(View.VISIBLE);
            rl_cart_data.setVisibility(View.GONE);
            ll_maintotal.setVisibility(View.GONE);
        }
    }
    //</editor-fold>


    //<editor-fold desc="package detail dialog">

    public static Dialog DetailsDailog = null;
    public static RecyclerView rv_membership_detail;
    public static ImageView btn_close_detail_dialog, iv_icon_img;
    public static Button btn_buy_membership;
    public static TextView tv_membership_type, tv_membership_price, tv_tax, tv_per_month;

    public void DialogDetail(MainActivity mainActivity, MembershipCourseItem membershipdetail, ArrayList<MembershipCourseItem> membershipMainArrayList, int Type, Boolean IsbtnShow) {


        if (DetailsDailog == null) {
            DetailsDailog = new Dialog(mainActivity);
            DetailsDailog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            DetailsDailog.setContentView(R.layout.dialog_full_detail_membership);
            Window window = DetailsDailog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            DetailsDailog.setCancelable(false);
            DetailsDailog.show();
        }

        rv_membership_detail = DetailsDailog.findViewById(R.id.rv_membership_detail);
        btn_close_detail_dialog = DetailsDailog.findViewById(R.id.btn_close_detail_dialog);
        btn_buy_membership = DetailsDailog.findViewById(R.id.btn_buy_membership);
        tv_membership_type = DetailsDailog.findViewById(R.id.tv_membership_type);
        tv_membership_price = DetailsDailog.findViewById(R.id.tv_membership_price);
        iv_icon_img = DetailsDailog.findViewById(R.id.iv_icon_img);
        tv_tax = DetailsDailog.findViewById(R.id.tv_tax);
        tv_per_month = DetailsDailog.findViewById(R.id.tv_per_month);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rv_membership_detail.setLayoutManager(linearLayoutManager);
        tv_membership_type.setText(membershipdetail.getItemname());
        tv_membership_price.setText("Qr " + String.format("%.2f", Double.parseDouble(membershipdetail.getPrice())));


        if (membershipdetail.getIgst().equals("0.0")) {
            tv_tax.setVisibility(View.GONE);
        } else {
            tv_tax.setText("( " + membershipdetail.getIgst() + " % " + membershipdetail.getTaxtypename() + " )");
            tv_tax.setVisibility(View.VISIBLE);
        }


        tv_per_month.setText("/" + membershipdetail.getDurationname());

        ArrayList<MembershipCourseItem> addToCartArrayList;
        addToCartArrayList = getCartArray(mainActivity);

        for (int i = 0; i < addToCartArrayList.size(); i++) {

            MembershipCourseItem model = new MembershipCourseItem();
            model.setId(addToCartArrayList.get(i).getId());

            if (membershipMainArrayList.contains(model)) {
                int index = membershipMainArrayList.indexOf(model);
                membershipMainArrayList.get(index).setIsadded(1);
            }
        }


        if (membershipdetail.getIsadded() == 1) {
            btn_buy_membership.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_HOME_ALREADY_ADDED).getLabel());
        } else {
            btn_buy_membership.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_HOME_ADD_TO_CART).getLabel());
        }


        if (IsbtnShow) {
            btn_buy_membership.setVisibility(View.VISIBLE);
        } else {
            btn_buy_membership.setVisibility(View.GONE);
        }

        Glide.with(mainActivity).asBitmap().load(membershipdetail.getIconimg()).into(iv_icon_img);

        MembershipDetailAdapter adapter = new MembershipDetailAdapter(mainActivity, membershipdetail.getAttributedetail(), membershipdetail.getAttributedetail().size());
        rv_membership_detail.setAdapter(adapter);

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

        btn_buy_membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // type: 1-membership /2- Special Package / 0-courses
                AddToCart(mainActivity, membershipdetail);
                btn_buy_membership.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_HOME_ALREADY_ADDED).getLabel());
                membershipdetail.setIsadded(1);
            }
        });


    }
    //</editor-fold>


    private void TotalAmount() {

        double subAmount = 0.0;
        double totalTax = 0.0;
        double finalPrice = 0.0;
        double inclusiveTax = 0.0;
        double exclusiveTax = 0.0;
        double taxable = 0.0;


        // coupon applied then change final price
        for (int i = 0; i < finalCartArrayList.get(0).getCartiteminfo().size(); i++) {

            subAmount = subAmount + Double.parseDouble(finalCartArrayList.get(0).getCartiteminfo().get(i).getPrice());
            totalTax = totalTax + finalCartArrayList.get(0).getCartiteminfo().get(i).getIgsttaxamt();
            finalPrice = finalPrice + finalCartArrayList.get(0).getCartiteminfo().get(i).getFinalprice();
            taxable = taxable + finalCartArrayList.get(0).getCartiteminfo().get(i).getTaxable();

            if (finalCartArrayList.get(0).getCartiteminfo().get(i).getTaxtype().equals("2")) {

                inclusiveTax = inclusiveTax + finalCartArrayList.get(0).getCartiteminfo().get(i).getIgsttaxamt();

            } else if (finalCartArrayList.get(0).getCartiteminfo().get(i).getTaxtype().equals("1")) {

                exclusiveTax = exclusiveTax + finalCartArrayList.get(0).getCartiteminfo().get(i).getIgsttaxamt();
            }

        }

        tv_sub_amount.setText("Qr." + String.format("%.2f", subAmount));
        tv_taxable_amount.setText("Qr." + String.format("%.2f", taxable));
        tv_total_tax.setText("Qr." + String.format("%.2f", totalTax));
        tv_final_total.setText("Qr " + String.format("%.2f", finalPrice));

        try {
            if (tv_taxable != null && tv_tax_amount != null && tv_total_amount != null && tv_cpn_amount != null) {
                tv_taxable.setText("Qr." + String.format("%.2f", taxable));
                tv_tax_amount.setText("Qr." + String.format("%.2f", totalTax));
                tv_total_amount.setText("Qr " + String.format("%.2f", finalPrice));
                tv_cpn_amount.setText("Qr " + String.format("%.2f", finalCartArrayList.get(0).getCouponamount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        if (inclusiveTax > 0) {
//            rl_inclusive_tax.setVisibility(View.VISIBLE);
//            tv_inclusive_tax.setText("Qr." + String.format("%.2f", inclusiveTax));
//        } else {
//            rl_inclusive_tax.setVisibility(View.GONE);
//        }
//
//        if (exclusiveTax > 0) {
//            rl_exclusive_tax.setVisibility(View.VISIBLE);
//            tv_exclusive_tax.setText("Qr." + String.format("%.2f", exclusiveTax));
//        } else {
//            rl_exclusive_tax.setVisibility(View.GONE);
//        }
    }


    //<editor-fold desc="Delete Dialog">
    Dialog deleteDialog;

    public void DeleteDialog(ArrayList<MembershipCourseItem> cartArrayList, int adapterPosition) {
        try {

            deleteDialog = new Dialog(getContext());
            deleteDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            deleteDialog.setContentView(R.layout.dialog_alert_message);

            TextView dialogTitle = deleteDialog.findViewById(R.id.tv_dlg_clr_cart);

            String item_name = cartArrayList.get(adapterPosition).getItemname();
            String message = Utility.languageLabel(mainActivity, LabelMaster.LBL_DELETE_DLG_MESS).getLabel() + " ?";
            message = message.replace("#item_name#", item_name);
            dialogTitle.setText(message);

            Button btnOk = deleteDialog.findViewById(R.id.btn_clear_cart);
            Button btnno = deleteDialog.findViewById(R.id.btn_goto_cart);

            btnOk.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_BTN_DELETE).getLabel());
            btnno.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_PER_BTN_CANCEL).getLabel());


            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    MembershipCourseItem model = new MembershipCourseItem();
                    model.setId(finalCartArrayList.get(0).getCartiteminfo().get(adapterPosition).getId());
                    if (membershipCourseItemArrayList.contains(model)) {
                        int index = membershipCourseItemArrayList.indexOf(model);
                        membershipCourseItemArrayList.get(index).setIsadded(0);
                    }
                    cartArrayList.remove(adapterPosition);
                    SessionManagement.savePreferences(getContext(), AppConstant.CARTARRAY, new Gson().toJson(cartArrayList));
                    finalCartArrayList.get(0).setCartiteminfo(cartArrayList);
//                    Log.e("remove", "onClick: " + new Gson().toJson(cartArrayList));
                    if (cartArrayList.size() > 0) {
                        TotalAmount();
                    } else {
                        tv_nodata_cart.setVisibility(View.VISIBLE);
                        rl_cart_data.setVisibility(View.GONE);
                        ll_maintotal.setVisibility(View.GONE);
                    }
                    cartListAdapter.notifyDataSetChanged();
                    deleteDialog.dismiss();
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    deleteDialog.dismiss();
                    cartListAdapter.notifyDataSetChanged();
                }
            });

            deleteDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //</editor-fold>


    private void enableSwipeToDeleteAndUndo() {

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                DeleteDialog(finalCartArrayList.get(0).getCartiteminfo(), position);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(rv_cart);

    }


    //<editor-fold desc="Add to Cart">
    public static ArrayList<MembershipCourseItem> addToCartArrayList;
    public static double final_Price = 0.0, taxableamt = 0.0, taxamt = 0.0;

    public void AddToCart(Context mainActivity, MembershipCourseItem membershipCourseItemModel) {
        final_Price = 0.0;
        taxableamt = 0.0;
        taxamt = 0.0;

        try {
            addToCartArrayList = getCartArray(mainActivity);
        } catch (Exception e) {
            e.printStackTrace();
            addToCartArrayList = new ArrayList<>();
        }

        MembershipCourseItem cartModel = new MembershipCourseItem();

        cartModel.setId(membershipCourseItemModel.getId());
        cartModel.setItemname(membershipCourseItemModel.getItemname());
        cartModel.setPrice(membershipCourseItemModel.getPrice());
        cartModel.setDuration(membershipCourseItemModel.getDuration());
        cartModel.setDurationname(membershipCourseItemModel.getStrduration());
        cartModel.setTaxtype(membershipCourseItemModel.getTaxtype());
        cartModel.setType(membershipCourseItemModel.getType());
        cartModel.setTaxtypename(membershipCourseItemModel.getTaxtypename());
        cartModel.setSgst(membershipCourseItemModel.getSgst());
        cartModel.setIgst(membershipCourseItemModel.getIgst());
        cartModel.setCgst(membershipCourseItemModel.getCgst());
        cartModel.setStrvalidityduration(membershipCourseItemModel.getStrvalidityduration());
        cartModel.setImage(membershipCourseItemModel.getImage());
        cartModel.setIconimg(membershipCourseItemModel.getIconimg());

        if (membershipCourseItemModel.getTaxtype().equals("1")) {
            //  Exclusive  tax
            taxableamt = Double.parseDouble(membershipCourseItemModel.getPrice());
            taxamt = (taxableamt * Double.parseDouble(membershipCourseItemModel.getIgst())) / 100;
            final_Price = taxableamt + taxamt;

        } else if (membershipCourseItemModel.getTaxtype().equals("2")) {
            // Inclusive  tax
            final_Price = Double.parseDouble(membershipCourseItemModel.getPrice());
            taxableamt = (final_Price * 100) / (100 + Double.parseDouble(membershipCourseItemModel.getIgst()));
            taxamt = final_Price - taxableamt;
        }

        cartModel.setFinalprice(final_Price);
        cartModel.setTaxable(taxableamt);
        cartModel.setIgsttaxamt(taxamt);
        cartModel.setCgsttaxamt(taxamt / 2);
        cartModel.setSgsttaxamt(taxamt / 2);

        if (addToCartArrayList.contains(cartModel)) {
//            Toast.makeText(mainActivity, "Already added into cart", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(mainActivity, "Successfully Added to cart...", Toast.LENGTH_SHORT).show();
            addToCartArrayList.add(cartModel);
        }

        SessionManagement.savePreferences(mainActivity, AppConstant.CARTARRAY, new Gson().toJson(addToCartArrayList));
//        Log.e("Add", "onClick: " + new Gson().toJson(addToCartArrayList));

        CreateCartFinalJson();


    }

    //<editor-fold desc="CartList Array get">

    public static ArrayList<MembershipCourseItem> getCartArray(Context mainActivity) {
        addToCartArrayList = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<MembershipCourseItem>>() {
        }.getType();
        addToCartArrayList = new Gson().fromJson(SessionManagement.getStringValue(mainActivity, AppConstant.CARTARRAY, ""), listType);
        if (addToCartArrayList == null) {
            addToCartArrayList = new ArrayList<>();
        }
        return addToCartArrayList;
    }
    //</editor-fold>

    //</editor-fold>


    public void CreateCartFinalJson() {

        finalCartArrayList = new ArrayList<>();

        FinalCartListModel finalCartModel = new FinalCartListModel();
        finalCartModel.setKey(SessionManagement.getStringValue(getContext(), AppConstant.KEY, ""));
        finalCartModel.setUnqkey(SessionManagement.getStringValue(getContext(), AppConstant.UNQKEY, ""));
        finalCartModel.setUid(SessionManagement.getStringValue(getContext(), AppConstant.UID, ""));
        finalCartModel.setIss(SessionManagement.getStringValue(getContext(), AppConstant.ISS, ""));

        finalCartModel.setUsername(SessionManagement.getStringValue(getContext(), AppConstant.USERNAME, ""));
        finalCartModel.setUtypeid(SessionManagement.getStringValue(getContext(), AppConstant.UTYPEID, ""));
        finalCartModel.setFullname(SessionManagement.getStringValue(getContext(), AppConstant.FULLNAME, ""));
        finalCartModel.setAdlogin(SessionManagement.getStringValue(getContext(), AppConstant.ADLOGIN, ""));
        finalCartModel.setYearid(SessionManagement.getStringValue(getContext(), AppConstant.YEARID, ""));
        finalCartModel.setYearname(SessionManagement.getStringValue(getContext(), AppConstant.YEARNAME, ""));
        finalCartModel.setActiveyearid(SessionManagement.getStringValue(getContext(), AppConstant.ACTIVEYEARID, ""));

        ArrayList<MembershipCourseItem> cartList = getCartArray(mainActivity);
        finalCartModel.setCartiteminfo(cartList);
        finalCartArrayList.add(finalCartModel);

        SetCartAdapter();

//        Log.e("TAG", "CreateCartFinalJson: " + new Gson().toJson(finalCartArrayList));
//        Log.e("TAG", "CartJson " + new Gson().toJson(cartList));
    }


    //<editor-fold desc="Apply coupon code api">

    public void callApiApplyCoupon(String str_couponcode, String str_cartdata, String member_id, int isRemoveCoupop, boolean isShowmsg) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "applycoupon");
        params.put("couponcode", str_couponcode);
        params.put("memberid", member_id);
        params.put("cartitemdata", str_cartdata);
        params.put("removecoupon", "" + isRemoveCoupop);//Pass 1 When Remove Coupon

//        Log.e("params", "callApiGetCouponList: " + params.toString());

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MEMBERSHIP_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {

//                        Log.e("applycoupon", "onResponse: " + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            coupon_status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");

                            if (coupon_status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("cartitemdata");

                                ArrayList<FinalCartListModel> FinalCpnAppliedArrayList = new ArrayList<>();
                                Gson gson = new Gson();
                                Type listtype = new TypeToken<ArrayList<FinalCartListModel>>() {
                                }.getType();

                                FinalCpnAppliedArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                finalCartArrayList.clear();
                                finalCartArrayList = new ArrayList<>();
                                finalCartArrayList = FinalCpnAppliedArrayList;

                                SetCartAdapter();
                                TotalAmount();

                                if (isShowmsg) {
                                    tv_message.setText(message);
                                }

                            } else {
                                if (isShowmsg) {
                                    Utility.initErrorMessagePopupWindow(getActivity(), message);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Tag", "onResponse: " + e.toString());
                        }
                    }
                });

    }
    //</editor-fold>


    public static Dialog clearcart;

    public void initExitMessagePopupWindow(Activity activity, String message) {
        try {
            // We need to get the instance of the LayoutInflater

            if (clearcart != null && clearcart.isShowing())
                return;
            clearcart = new Dialog(activity);

            clearcart.requestWindowFeature(Window.FEATURE_NO_TITLE);
            clearcart.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            clearcart.setContentView(R.layout.dialog_exit_message_box);
            TextView dialogTitle = clearcart.findViewById(R.id.idTvDialogMsg);
            dialogTitle.setText(message);
            Button btnOk = clearcart.findViewById(R.id.btnyes);
            Button btnno = clearcart.findViewById(R.id.btnno);
            ImageView iv_gif = clearcart.findViewById(R.id.iv_gif);

            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_lefttoright);
            Glide.with(activity).asGif().load(R.drawable.loader).into(iv_gif);
            iv_gif.startAnimation(animation);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (int i = 0; i < finalCartArrayList.get(0).getCartiteminfo().size(); i++) {
                        MembershipCourseItem model = new MembershipCourseItem();
                        model.setId(finalCartArrayList.get(0).getCartiteminfo().get(i).getId());
                        if (membershipCourseItemArrayList.contains(model)) {
                            int index = membershipCourseItemArrayList.indexOf(model);
                            membershipCourseItemArrayList.get(index).setIsadded(0);
                        }
                    }

                    SessionManagement.savePreferences(mainActivity, AppConstant.CARTARRAY, "");
                    ArrayList<MembershipCourseItem> cartList = new ArrayList<>();
                    finalCartArrayList.get(0).setCartiteminfo(cartList);
                    SetCartAdapter();
                    clearcart.dismiss();
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    clearcart.dismiss();
                }
            });
            clearcart.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    ArrayList<Model> paymentModeArrayList;
    SearchableSpinnerDialog paymentdialog;


    private void callApiGetpaymentList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "listorderpaymenttype");

        VolleyUtils.makeVolleyRequest(mainActivity, AppConstant.MEMBERSHIP_ORDER_URL, params, 1, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "callApiGetpaymentList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("paymentdata");

                                paymentModeArrayList = new ArrayList<>();

                                Gson gson = new Gson();
                                Type listtype = new TypeToken<ArrayList<Model>>() {
                                }.getType();

                                paymentModeArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                try {
                                    if (tv_select_payment_mode != null && edt_rf_number != null && til_rf_number != null) {
                                        tv_select_payment_mode.setText(paymentModeArrayList.get(0).getName());
                                        payment_mode_id = paymentModeArrayList.get(0).getId();
                                        if (payment_mode_id.equals("1")) {
                                            edt_rf_number.setText("");
                                            til_rf_number.setVisibility(View.GONE);
                                        } else {
                                            edt_rf_number.requestFocus();
                                            til_rf_number.setVisibility(View.VISIBLE);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                paymentdialog = new SearchableSpinnerDialog(mainActivity, paymentModeArrayList, Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_PAYMENT_MODE_TITLE).getLabel(), R.style.DialogAnimations_SmileWindow);
                                paymentdialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                    @Override
                                    public void onClick(Model model, int i) {
                                        try {
                                            if (tv_select_payment_mode != null && edt_rf_number != null && til_rf_number != null) {
                                                tv_select_payment_mode.setText(model.getName());
                                                payment_mode_id = model.getId();
                                                if (payment_mode_id.equals("1")) {
                                                    edt_rf_number.setText("");
                                                    til_rf_number.setVisibility(View.GONE);
                                                } else {
                                                    edt_rf_number.requestFocus();
                                                    til_rf_number.setVisibility(View.VISIBLE);
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            tv_select_payment_mode.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        paymentdialog.showSpinerDialog();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    public void callApiInsertOrder() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "insertorderdata");
        params.put("memberid", member_id);
        params.put("paymenttype", payment_mode_id);
        params.put("ordernote", edt_order_note.getText().toString().trim());
        params.put("referenceno", edt_rf_number.getText().toString().trim());
        params.put("cartitemdata", new Gson().toJson(finalCartArrayList));

        Log.e("params", "callApiInsertOrder: " + params.toString());

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MEMBERSHIP_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "callApiInsertOrder: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String message = jsonObject.getString("message");
                            if (status == 1) {

                                String orderno = jsonObject.getString("orderno");
                                String cmp_logo = jsonObject.getString("cmp_logo");
                                String cmp_address = jsonObject.getString("cmp_address");
                                String cmp_email = jsonObject.getString("cmp_email");
                                String cmp_contact = jsonObject.getString("cmp_contact");
                                String cmp_israngehour = jsonObject.getString("cmp_israngehour");
                                String date = jsonObject.getString("curr_datetime");

                                try {
                                    SetPrintBillValues(orderno, cmp_logo, cmp_address, cmp_email, cmp_contact, cmp_israngehour, date);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("Exception", "onResponse: " + e.toString());
                                }

                                for (int i = 0; i < finalCartArrayList.get(0).getCartiteminfo().size(); i++) {
                                    MembershipCourseItem model = new MembershipCourseItem();
                                    model.setId(finalCartArrayList.get(0).getCartiteminfo().get(i).getId());
                                    if (membershipCourseItemArrayList.contains(model)) {
                                        int index = membershipCourseItemArrayList.indexOf(model);
                                        membershipCourseItemArrayList.get(index).setIsadded(0);
                                    }
                                }
                                SessionManagement.savePreferences(mainActivity, AppConstant.CARTARRAY, "");
                                ArrayList<MembershipCourseItem> cartList = new ArrayList<>();
                                finalCartArrayList.get(0).setCartiteminfo(cartList);
                                SetCartAdapter();
                                Utility.initErrorMessagePopupWindow(getActivity(), message);
                                SessionManagement.savePreferences(mainActivity, AppConstant.CARTARRAY, "");
                                paymentDialog.dismiss();
                                paymentDialog = null;
                                mainActivity.addFragment(new MembershipListingFragment());
                            } else {
                                Utility.initErrorMessagePopupWindow(getActivity(), message);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Exception", "onResponse: " + e.toString());
                        }
                    }
                });

    }

    private void SetPrintBillValues(String orderno, String cmp_logo, String cmp_address, String cmp_email, String cmp_contact, String cmp_israngehour, String date) {
//        ArrayList<MyCart> billItemArrayList = getCartArray(getContext());
        ArrayList<MembershipCourseItem> cartArrayList = getCartArray(getContext());
        Glide.with(getContext()).load(cmp_logo).into(iv_prt_companylogo);
        tv_contact_number.setText("Mobile No : " + cmp_contact + "\n Email : " + cmp_email);

        tv_store_name.setText("Store Name: " + SessionManagement.getStringValue(getContext(), AppConstant.STORE_NAME, ""));
        tv_ord_no.setText("Order No: " + orderno);
        tv_cust_name.setText("Customer Name : " + member_name);
//        tv_cust_mobile.setText("Mobile No : " + SessionManagement.getStringValue(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, ""));
        tv_cust_mobile.setVisibility(GONE);
        tv_address.setText(cmp_address);

        tv_date.setText("Date : " + date);
        tv_salepersonname.setText("Sale Person : " + SessionManagement.getStringValue(getContext(), AppConstant.FULLNAME, ""));

        rv_item_bill.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_bill_paytype.setLayoutManager(new LinearLayoutManager(getContext()));

        Double totalPrice = 0.0;
        Double totalTax = 0.0;
        for (int i = 0; i < cartArrayList.size(); i++) {
            totalPrice += cartArrayList.get(i).getFinalprice();
            totalTax += cartArrayList.get(i).getIgsttaxamt();
        }

        tv_order_qty.setText(String.format("%.2f", totalTax));

        BillIMemberShipAdapter billItemAdapter = new BillIMemberShipAdapter(getContext(), cartArrayList);
        rv_item_bill.setAdapter(billItemAdapter);

        tv_bill_total.setText("Qr : " + totalPrice);

//        //<editor-fold desc="PaymentType in bill">
//        String paymentlist = SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, "");
//        Gson gson = new Gson();
//        Type type1 = new TypeToken<List<PaymentModel>>() {
//        }.getType();
//
//        if (!paymentlist.isEmpty()) {
//            rv_bill_paytype.setVisibility(VISIBLE);
//            paymentModelArrayList = gson.fromJson(paymentlist, type1);
//
//            BillPayTypeAdapter billPayTypeAdapter = new BillPayTypeAdapter(getContext(), paymentModelArrayList);
//            rv_bill_paytype.setAdapter(billPayTypeAdapter);
//        }
//        //</editor-fold>

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
//        tv_prt_redeeamt.setText("Qr: " + String.format("%.2f", total_payable_amount_withqty));
        tv_prt_redeeamt.setVisibility(GONE);
        view1.setVisibility(VISIBLE);
        view2.setVisibility(GONE);

//        if (totalChangeAmt > 0.0) {
//            tv_bill_change.setText("Qr: " + String.format("%.2f", totalChangeAmt));
//        } else {
//            tv_bill_change.setText("Qr: " + "0.00");
//        }

        tv_bill_change.setVisibility(GONE);
        tv_takeordine.setVisibility(GONE);
//        final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
//        final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight() + 600), true);
        if (ISCONNECT) {
//        final Bitmap bitmap1 = BitmapProcess.compressBmpByYourWidth
//                (BitmapFactory.decodeResource(getResources(), R.drawable.test), 300);

            final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
            final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight() + 600), true);
            new DownloadImageAsynktask(getContext(), bitmap, bitmap2).execute();
//            new DownloadImageAsynktask(getContext(), bitmap, bitmap2).execute();
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
            Log.e("onPreExecute", "onPreExecute: ");
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
                        for (int i = 0; i < blist.size() * 2; i++) {
                            list.add(DataForSendToPrinterPos80.printRasterBmp(0, blist.get(i), BitmapToByteData.BmpType.Dithering, BitmapToByteData.AlignType.Center, 576));
                        }
                        list.add(DataForSendToPrinterPos80.printAndFeedLine());

                    } catch (Exception e) {
                        Log.e("exception", "processDataBeforeSend: " + e.toString());
                    }
                    return list;
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("onPostExecute", "onPostExecute: ");

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
                    mainActivity.addFragment(new MembershipListingFragment());
                    return true;
                }
                return false;
            }
        });
    }
}