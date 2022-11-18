package com.instanceit.alhadafpos.Fragments.ServiceOrder;

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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Activities.R80Activity;
import com.instanceit.alhadafpos.Entity.Instruction;
import com.instanceit.alhadafpos.Entity.Items;
import com.instanceit.alhadafpos.Entity.Member360;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.MyCart;
import com.instanceit.alhadafpos.Entity.OperationFlow;
import com.instanceit.alhadafpos.Entity.PaymentModel;
import com.instanceit.alhadafpos.Entity.ServiceOrderDetail;
import com.instanceit.alhadafpos.Entity.Storeinstructiondatum;
import com.instanceit.alhadafpos.Entity.SummaryDetail;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.ActivePackagesListAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.AdapterCallback;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.CartAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.CashNoteAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.CategoryListAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.InstructionListAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.InstructionSelectionAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.ItemDetailAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.ItemListAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.LastVisistParentAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.MembershipItemsLisAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.OperationFlowAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.PaymentAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.PaymentTypeAdapter;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.BillItemAdapter;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.BillPayTypeAdapter;
import com.instanceit.alhadafpos.Helper.OnSpinerItemClickSpinnerDialog;
import com.instanceit.alhadafpos.Helper.SearchableSpinnerDialog;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;
import com.instanceit.alhadafpos.Utility.Validation;
import com.instanceit.alhadafpos.Utility.VolleyResponseListener;
import com.instanceit.alhadafpos.Utility.VolleyUtils;
import com.instanceit.alhadafpos.Views.Numpad;
import com.instanceit.alhadafpos.Views.NumpadHandler;

import net.posprinter.posprinterface.IMyBinder;
import net.posprinter.posprinterface.ProcessData;
import net.posprinter.posprinterface.TaskCallback;
import net.posprinter.service.PosprinterService;
import net.posprinter.utils.BitmapProcess;
import net.posprinter.utils.BitmapToByteData;
import net.posprinter.utils.DataForSendToPrinterPos80;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServiceOrderFragment extends Fragment implements AdapterCallback {

    MainActivity mainActivity;

    //    Views
    public RecyclerView rv_category, rv_item, rv_cart, rv_membership_list, rv_item_list, rv_lastvisit, rv_operation_flow_list;
    public TextView tv_cancel_order, tv_proceed, tv_cart_switch, tv_customerdata_switch, tv_nodata_cart, tv_last_visit_title, tv_view_more, tv_operation_flow_title,
            tv_lbl_total_amount, tv_lbl_taxable_amount, tv_lbl_vat, tv_lbl_total;
    public TextView tv_full_name, tv_c_number, tv_email, tv_qatar_id, tv_q_expiry, tv_dob, tv_active_pck_title, tv_item_title, tv_lbl_name, tv_lbl_contact_no, tv_lbl_email, tv_lbl_qatar_id, tv_lbl_qatar_id_expiry_date, tv_lbl_dob;
    public TextView tv_total_price, tv_lbl_nodata_customer, tv_tot_disc_amount, tv_total_taxable_amt, tv_tot_vat, tv_total_payable_amount, tv_lbl_title_customer_det,
            tv_passport_id, tv_passport_expiry, tv_nodata_item;
    SwipeRefreshLayout swiperefresh;
    LinearLayout ll_button, ll_total, ll_maintotal, ln_view_more_package_detail, ll_category;
    LinearLayout ln_qatar_data, ln_passport;
    public RelativeLayout rl_mycart, rl_no_item, rl_item_main;
    View view_customer360;
    CardView cv_last_visit, cv_operation_flow, cv_active_packages;
    TextView tv_goto_service_order;

    //bill print views
    FrameLayout frameLayout_bill;
    TextView tv_cust_name, tv_bill_Remaining, tv_cust_mobile, tv_salepersonname, tv_date, tv_bill_taxbale, tv_bill_sgst, tv_bill_cgst, tv_bill_total,tx_discount,tv_bill_total_disc, tv_companyname,
            tv_gst, tv_fssaino, tv_address, tv_orderno, tv_cashamt, tv_ordertype, tv_header_paytype, tv_parentcompany, tv_order_qty, tv_store_name, tv_ord_no;
    RecyclerView rv_item_bill, rv_bill_paytype;
    View view1, view2;
    LinearLayout ll_cash, ll_ordertype, ll_changeamt, ln_payment_details;
    ImageView iv_logo, iv_prt_companylogo;
    LinearLayout ll_taxsummary18, ll_taxsummary0, ll_taxsummary5, ll_taxsummary12, ll_taxsummary28;
    TextView tv_bill_change, tv_type18, tv_taxable18, tv_taxamt18, tv_netamt18, tv_type0, tv_taxable0, tv_taxamt0, tv_netamt0, tv_type5, tv_taxable5,
            tv_taxamt5, tv_netamt5, tv_type12, tv_taxable12, tv_taxamt12, tv_netamt12, tv_type28, tv_taxable28, tv_taxamt28, tv_netamt28, tv_typetotal, tv_taxabletotal,
            tv_taxamttotal, tv_netamttotal, tv_prt_redeeamt, tv_takeordine, tv_contact_number, tv_prt_gstnumber;


    //    Variables
    String str_subcatID = "";
    public static int lastCheckedPos = -1;
    String contact_no;
    String formevent = "addright";
    String Action = "";
    String id_update = "", referenceno = "";

    public ArrayList<Model> subCatArrayList;
    public ArrayList<Items> itemsArrayList;
    public ArrayList<MyCart> cartArrayList = new ArrayList<>();
    ArrayList<Items> membershipItemArrayList;

    ItemListAdapter itemListAdapter;
    CategoryListAdapter categoryListAdapter;

    Bundle bundle;
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
    int open=1;
    void checkcart(){
        Gson gson = new Gson();

        if(!Objects.equals(SessionManagement.getStringValue(getContext(), AppConstant.TEMPCART, ""), "")) {

            List<MyCart> list = Arrays.asList(new GsonBuilder().create().fromJson(SessionManagement.getStringValue(getContext(), AppConstant.TEMPCART, ""), MyCart[].class));

//        ArrayList<MyCart> cart = gson.fromJson(SessionManagement.getStringValue(getContext(),AppConstant.TEMPCART,""), (Type) MyCart.class);

//        SessionManagement.savePreferences(getContext(), AppConstant.TEMPCART, cart);

            Log.d("testing", SessionManagement.getStringValue(getContext(), AppConstant.TEMPCART, ""));
//        cartArrayList=list;
            if (list.size() > 0) {
//                if(open==1) {
                    open=0;
                    OpenCartConfirmDialog("position", "fn", "Would You like to proceed with old cart list? ");

//                }
                cartArrayList.addAll(list);
//                SessionManagement.savePreferences(getContext(), AppConstant.TEMPCART, "");
            }


//        cartAdapter.notifyDataSetChanged();

        }

    }

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
        return inflater.inflate(R.layout.fragment_service_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization(view);
//        checkcart();

        onBackPress(view);
//        checkcart();
        SetCartAdapter();
        CalculateCartTotal();


//        CalculateCartTotal();


    }

    private void Declaration(View view) {
        tx_discount = view.findViewById(R.id.tv_bill_total_lbl);
        rv_category = view.findViewById(R.id.rv_category);
        rv_item = view.findViewById(R.id.rv_item);
        rv_cart = view.findViewById(R.id.rv_cart);
        tv_cancel_order = view.findViewById(R.id.tv_cancel_order);
        tv_proceed = view.findViewById(R.id.tv_proceed);
        swiperefresh = view.findViewById(R.id.swiperefresh);
        tv_cart_switch = view.findViewById(R.id.tv_cart_switch);
        tv_customerdata_switch = view.findViewById(R.id.tv_customerdata_switch);
        rl_mycart = view.findViewById(R.id.rl_mycart);
        view_customer360 = view.findViewById(R.id.view_customer360);
        rv_membership_list = view.findViewById(R.id.rv_membership_list);
        tv_full_name = view.findViewById(R.id.tv_full_name);
        tv_c_number = view.findViewById(R.id.tv_c_number);
        tv_email = view.findViewById(R.id.tv_email);
        tv_qatar_id = view.findViewById(R.id.tv_qatar_id);
        tv_q_expiry = view.findViewById(R.id.tv_q_expiry);
        tv_dob = view.findViewById(R.id.tv_dob);
        rv_item_list = view.findViewById(R.id.rv_item_list);
        tv_active_pck_title = view.findViewById(R.id.tv_active_pck_title);
        tv_item_title = view.findViewById(R.id.tv_item_title);
        ll_button = view.findViewById(R.id.ll_button);
        ll_total = view.findViewById(R.id.ll_total);
        ll_maintotal = view.findViewById(R.id.ll_maintotal);
        tv_nodata_cart = view.findViewById(R.id.tv_nodata_cart);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        tv_tot_disc_amount = view.findViewById(R.id.tv_tot_disc_amount);
        tv_total_taxable_amt = view.findViewById(R.id.tv_total_taxable_amt);
        tv_tot_vat = view.findViewById(R.id.tv_tot_vat);
        tv_total_payable_amount = view.findViewById(R.id.tv_total_payable_amount);
        rv_lastvisit = view.findViewById(R.id.rv_lastvisit);
        cv_last_visit = view.findViewById(R.id.cv_last_visit);
        tv_last_visit_title = view.findViewById(R.id.tv_last_visit_title);
        rl_no_item = view.findViewById(R.id.rl_no_item);
        rl_item_main = view.findViewById(R.id.rl_item_main);
        tv_view_more = view.findViewById(R.id.tv_view_more);
        ln_view_more_package_detail = view.findViewById(R.id.ln_view_more_package_detail);
        tv_operation_flow_title = view.findViewById(R.id.tv_operation_flow_title);
        rv_operation_flow_list = view.findViewById(R.id.rv_operation_flow_list);
        cv_operation_flow = view.findViewById(R.id.cv_operation_flow);
        cv_active_packages = view.findViewById(R.id.cv_active_packages);
        tv_nodata_item = view.findViewById(R.id.tv_nodata_item);

        tv_lbl_total_amount = view.findViewById(R.id.tv_lbl_total_amount);
        tv_lbl_taxable_amount = view.findViewById(R.id.tv_lbl_taxable_amount);
        tv_lbl_vat = view.findViewById(R.id.tv_lbl_vat);
        tv_lbl_total = view.findViewById(R.id.tv_lbl_total);

        tv_lbl_name = view.findViewById(R.id.tv_lbl_name);
        tv_lbl_contact_no = view.findViewById(R.id.tv_lbl_contact_no);
        tv_lbl_email = view.findViewById(R.id.tv_lbl_email);
        tv_lbl_qatar_id = view.findViewById(R.id.tv_lbl_qatar_id);
        tv_lbl_qatar_id_expiry_date = view.findViewById(R.id.tv_lbl_qatar_id_expiry_date);
        tv_lbl_dob = view.findViewById(R.id.tv_lbl_dob);
        tv_lbl_title_customer_det = view.findViewById(R.id.tv_lbl_title_customer_det);
        tv_lbl_nodata_customer = view.findViewById(R.id.tv_lbl_nodata_customer);
        ll_category = view.findViewById(R.id.ll_category);
        ln_qatar_data = view.findViewById(R.id.ln_qatar_data);
        ln_passport = view.findViewById(R.id.ln_passport);
        tv_passport_id = view.findViewById(R.id.tv_passport_id);
        tv_passport_expiry = view.findViewById(R.id.tv_passport_expiry);
        tv_goto_service_order = view.findViewById(R.id.tv_goto_service_order);
        tv_store_name = view.findViewById(R.id.tv_store_name);


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
        tv_bill_total_disc = view.findViewById(R.id.tv_bill_discount);

        tv_bill_discount= view.findViewById(R.id.tv_bill_discount);
        ll_bill_discount=view.findViewById(R.id.ll_bill_discount);

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
        mainActivity.iv_search.setImageResource(R.drawable.ic_search);
        mainActivity.edt_search.setText("");
        tv_goto_service_order.setVisibility(GONE);
        cv_operation_flow.setVisibility(GONE);
        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, "");
        bundle = getArguments();

        if (bundle != null) {
            Action = bundle.getString(AppConstant.SERVICE_ORDER_ACTION);
            contact_no = bundle.getString(AppConstant.LAST_ADDED_CUST_CONTACT);
            if (Action.equals(AppConstant.INSERT_SERVICE_ORDER_ACTION)) {
                formevent = "addright";
                SessionManagement.savePreferences(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, contact_no);
                ll_button.setVisibility(View.GONE);
                rl_mycart.setVisibility(View.GONE);
                view_customer360.setVisibility(VISIBLE);
                tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));
            } else {
                SessionManagement.savePreferences(getContext(), AppConstant.PAYMENTLIST, "");
                formevent = "editright";
                ServiceOrderDetail serviceOrderDetail_data = new Gson().fromJson(bundle.getString(AppConstant.UPDATE_SERVICE_ORDER_DATA), ServiceOrderDetail.class);
//                Log.e("myCart_update_Data", "Initialization: " + serviceOrderDetail_data);
                id_update = serviceOrderDetail_data.getId();
                referenceno = serviceOrderDetail_data.getReferenceno();

                String paymentlist = SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, "");

                Gson gson = new Gson();
                Type type = new TypeToken<List<PaymentModel>>() {
                }.getType();

                if (!paymentlist.isEmpty()) {
                    paymentModelArrayList = gson.fromJson(paymentlist, type);
                } else {
                    paymentModelArrayList = new ArrayList<>();
                }

                for (int i = 0; i < serviceOrderDetail_data.getServiceorderpaymentdetailinfo().size(); i++) {
                    PaymentModel paymentModel = new PaymentModel();
                    paymentModel.setType(serviceOrderDetail_data.getServiceorderpaymentdetailinfo().get(i).getType());
                    paymentModel.setPaytypeid(serviceOrderDetail_data.getServiceorderpaymentdetailinfo().get(i).getPaytypeid());
                    paymentModel.setPaytypename(serviceOrderDetail_data.getServiceorderpaymentdetailinfo().get(i).getPaytypename());
                    paymentModel.setPayamt(serviceOrderDetail_data.getServiceorderpaymentdetailinfo().get(i).getAmount());
                    paymentModelArrayList.add(paymentModel);
                }

                SessionManagement.savePreferences(getContext(), AppConstant.PAYMENTLIST, gson.toJson(paymentModelArrayList));

//                Log.e("Paymentlist", "Initialization: " + new Gson().toJson(paymentModelArrayList));

//                CalculateChanegAndRemainingAmt();

                if (cartArrayList == null) {
                    cartArrayList = new ArrayList<>();
                }

                for (int i = 0; i < serviceOrderDetail_data.getServiceorderdetailinfo().size(); i++) {

                    MyCart addItemList = new MyCart();
                    addItemList.setItemid(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getItemid());
                    addItemList.setItemname(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getItemname());
                    addItemList.setCategoryid(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getCatid());
                    addItemList.setCategory(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getCategory());
                    addItemList.setSubcategoryid(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getSubcatid());
                    addItemList.setSubcategory(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getSubcategory());
                    addItemList.setQty(Integer.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getQty()));


                    ArrayList<SummaryDetail> summaryDetailsArraylist = new ArrayList<>();
                    SummaryDetail summaryDetail = new SummaryDetail();
                    summaryDetail.setQty(Integer.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getQty()));
                    summaryDetail.setPrice(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getPrice()));
                    summaryDetail.setDiscountamt(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getDiscountamt()));
                    summaryDetail.setCgst(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getCgst()));
                    summaryDetail.setIgst(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getIgst()));
                    summaryDetail.setSgst(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getSgst()));
                    summaryDetail.setCgsttaxamt(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getCgsttaxamt()));
                    summaryDetail.setIgsttaxamt(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getIgsttaxamt()));
                    summaryDetail.setSgsttaxamt(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getSgsttaxamt()));
                    summaryDetail.setDiscount(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getDiscountper()));
                    summaryDetail.setOidid(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getOrderid());
                    summaryDetail.setTaxable(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getTaxable()));
                    summaryDetail.setTaxtype(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getTaxtype());
                    summaryDetail.setTaxtypename(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getTaxtypename());
                    summaryDetail.setType(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getType());
                    summaryDetail.setFinalprice(Double.valueOf(serviceOrderDetail_data.getServiceorderdetailinfo().get(i).getFinalprice()));
                    summaryDetailsArraylist.add(summaryDetail);


                    if (cartArrayList.contains(addItemList)) {
                        int index = cartArrayList.indexOf(addItemList);
                        int total_qty = 0;
                        ArrayList<SummaryDetail> tem_summary_etail = cartArrayList.get(index).getSummaryDetails();
                        tem_summary_etail.add(summaryDetail);
                        for (int j = 0; j < tem_summary_etail.size(); j++) {
                            total_qty += tem_summary_etail.get(j).getQty();
                        }
                        cartArrayList.get(index).setQty(total_qty);
                        cartArrayList.get(index).setSummaryDetails(tem_summary_etail);
                    } else {
                        addItemList.setSummaryDetails(summaryDetailsArraylist);
                        cartArrayList.add(addItemList);
                    }
                }
                SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));
                SessionManagement.savePreferences(mainActivity, AppConstant.TEMPCART, new Gson().toJson(cartArrayList));

//                Log.e("update_array", "Initialization: " + SessionManagement.getStringValue(mainActivity, AppConstant.ITEMCARTARRAY, ""));
                CalculateCartTotal();

                SetCartAdapter();
            }
            callApiGetMember360Data();
        } else {
            ContactDialog(getActivity());
        }

        mainActivity.iv_workflow.setVisibility(GONE);
        mainActivity.iv_range_booking.setVisibility(GONE);
        mainActivity.iv_add_contact.setVisibility(VISIBLE);

        mainActivity.iv_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactDialog(getActivity());
            }
        });

        //<editor-fold desc="Dynamic lbl">
        try {
            tv_cart_switch.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ADD_MEM_SWITCH_CART).getLabel());
            tv_customerdata_switch.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CREATE_ORD_SWITCH_CUSTOMER_360).getLabel() + "*");
            tv_nodata_cart.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_EMPTY_CART).getLabel());
            tv_lbl_total_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_AMONT_SCHEME).getLabel());
            tv_lbl_taxable_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TAXABLE_AMOUNT).getLabel());
            tv_lbl_vat.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_VAT).getLabel());
            tv_lbl_total.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT).getLabel());
            tv_lbl_name.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_NAME).getLabel());
            tv_lbl_contact_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_CONTACT_NO).getLabel());
            tv_lbl_email.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_EMAIL).getLabel());
            tv_lbl_qatar_id.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_QATAR_ID).getLabel());
            tv_lbl_qatar_id_expiry_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_QATAR_ID_EXPIRY_DATE).getLabel());
            tv_lbl_dob.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_DOB).getLabel());
            tv_operation_flow_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_OPRATION_FLOW_TITLE).getLabel());
            tv_item_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_ITEM_TITLE).getLabel());
            tv_view_more.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_VIEW_MORE_TITLE).getLabel());
            tv_active_pck_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_ACTIVE_PACKAGE_TITLE).getLabel());
            tv_lbl_title_customer_det.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_TITLE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        mainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        tv_view_more.setPaintFlags(tv_view_more.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_lbl_nodata_customer.setPaintFlags(tv_lbl_nodata_customer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        rv_category.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_operation_flow_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_item.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rv_membership_list.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv_item_list.setLayoutManager(new GridLayoutManager(getContext(), 3));

        callApiSubCategory();

        str_subcatID = "";
        callApiItemList();

        membershipItemArrayList = new ArrayList<>();

        tv_cart_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_mycart.setVisibility(VISIBLE);
                view_customer360.setVisibility(View.GONE);
                ll_button.setVisibility(VISIBLE);
                tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));
            }
        });

        tv_customerdata_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_mycart.setVisibility(View.GONE);
                view_customer360.setVisibility(VISIBLE);
                tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));
                ll_button.setVisibility(View.GONE);
            }
        });

        swiperefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        str_subcatID = "";
                        callApiItemList();

                        lastCheckedPos = -1;
                        categoryListAdapter.notifyDataSetChanged();

                        swiperefresh.setRefreshing(false);
                        swiperefresh.destroyDrawingCache();
                        swiperefresh.clearAnimation();
                    }
                }, 200);
            }
        });

        mainActivity.iv_search.setVisibility(VISIBLE);
        mainActivity.iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mainActivity.edt_search.getVisibility() == View.GONE) {
                    mainActivity.edt_search.setVisibility(VISIBLE);
                    mainActivity.iv_search.setImageResource(R.drawable.ic_close);
                    mainActivity.edt_search.requestFocus();
                    Utility.showKeyboardFrom(getContext(), mainActivity.edt_search);
                } else {
                    mainActivity.edt_search.setVisibility(View.GONE);
                    mainActivity.iv_search.setImageResource(R.drawable.ic_search);
                    callApiItemList();
                    mainActivity.edt_search.setText("");
                    Utility.hideKeyboardFrom(getContext(), mainActivity.edt_search);
                }

            }
        });

        mainActivity.edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rv_item.stopScroll();
                    rv_item.getRecycledViewPool().clear();
                    itemListAdapter.getFilter().filter(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mainActivity.edt_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view1, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    try {
                        rv_item.stopScroll();
                        rv_item.getRecycledViewPool().clear();
                        itemListAdapter.getFilter().filter(mainActivity.edt_search.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } else if (i == KeyEvent.KEYCODE_BACK) {
                    mainActivity.edt_search.clearFocus();
                    view.requestFocus();

                    return true;
                } else
                    return false;
            }
        });

        tv_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartArrayList != null && cartArrayList.size() > 0) {
//                    Person p = new Person();
//                    p.setname("xyz");
//                    p.setage("18");
                    Gson gson = new Gson();
                    String cart = gson.toJson(cartArrayList);
                    Log.d("testing",cart);
                    SessionManagement.savePreferences(getContext(), AppConstant.TEMPCART, cart);
                    SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));

                    if (Action.equals(AppConstant.INSERT_SERVICE_ORDER_ACTION)) {

                        SessionManagement.savePreferences(getContext(), AppConstant.PAYMENTLIST, "");
                    }
                    getPaymentMethodeDialog();
                } else {
                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_ORD_FIRST).getLabel(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartArrayList != null && cartArrayList.size() > 0) {
                    ClearCartDialog(getActivity(), Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_CLEAR_CART).getLabel() + "?");
                } else {
                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_EMPTY_CART).getLabel(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv_lbl_nodata_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactDialog(getActivity());
            }
        });

    }




    //<editor-fold desc="cart clear dialog">
    public static Dialog clearcart;

    public void ClearCartDialog(Activity activity, String message) {
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

            try {
                btnOk.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_EXIT_YES).getLabel());
                btnno.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_EXIT_NO).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_lefttoright);
            Glide.with(activity).asGif().load(R.drawable.loader).into(iv_gif);
            iv_gif.startAnimation(animation);

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        //  cartArrayList.clear();
                        cartArrayList = new ArrayList<>();
                        value=0;
                        total_item_discount=0;

                        SessionManagement.savePreferences(getContext(), AppConstant.ITEMCARTARRAY, "");
                        SessionManagement.savePreferences(getContext(), AppConstant.TEMPCART, "");

                        SessionManagement.savePreferences(getContext(), AppConstant.PAYMENTLIST, "");
                        getCartArray();
                        SetCartAdapter();

                        clearcart.dismiss();
                        clearcart = null;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    //</editor-fold>


    //<editor-fold desc="open active package item detail">
    Dialog itemDetailDialog = null;
    RecyclerView rv_item_detail;
    ImageView iv_close;

    private void OpenItemDetailDialog() {

        if (itemDetailDialog == null) {
            itemDetailDialog = new Dialog(getContext());
            itemDetailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            itemDetailDialog.setContentView(R.layout.dialog_item_deatil);
            itemDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            final int height = ViewGroup.LayoutParams.MATCH_PARENT;
            itemDetailDialog.getWindow().setLayout(width, height);
            itemDetailDialog.setCancelable(false);
            itemDetailDialog.show();
        }

        TextView tv_lbl_item_name, tv_lbl_type, tv_lbl_total_qty, tv_lbl_remaing_qty, tv_lbl_price;
        rv_item_detail = itemDetailDialog.findViewById(R.id.rv_item_detail);
        iv_close = itemDetailDialog.findViewById(R.id.iv_close);
        tv_lbl_item_name = itemDetailDialog.findViewById(R.id.tv_lbl_item_name);
        tv_lbl_type = itemDetailDialog.findViewById(R.id.tv_lbl_type);
        tv_lbl_total_qty = itemDetailDialog.findViewById(R.id.tv_lbl_total_qty);
        tv_lbl_remaing_qty = itemDetailDialog.findViewById(R.id.tv_lbl_remaing_qty);
        tv_lbl_price = itemDetailDialog.findViewById(R.id.tv_lbl_price);

        //<editor-fold desc="dynamic lbl">
        try {
            tv_lbl_item_name.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_ITEM_NAME).getLabel());
            tv_lbl_type.setText("(" + Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_TYPE).getLabel() + ")");
            tv_lbl_total_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_QTY).getLabel());
            tv_lbl_remaing_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_REM_QTY).getLabel());
            tv_lbl_price.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_PRICE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //</editor-fold>

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_item_detail.setLayoutManager(linearLayoutManager);
//        Log.e("itemDetail", "OpenItemDetailDialog: " + new Gson().toJson(itemsDetailArrayList));
        ItemDetailAdapter itemDetailAdapter = new ItemDetailAdapter(getActivity(), itemsDetailArrayList);
        rv_item_detail.setAdapter(itemDetailAdapter);

        itemDetailDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    itemDetailDialog.dismiss();
                    itemDetailDialog = null;
                }
                return false;
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDetailDialog.dismiss();
                itemDetailDialog = null;
            }
        });

    }
    //</editor-fold>


    //<editor-fold desc="Call API Sub Category">

    public void callApiSubCategory() {

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "liststoresubcategory");
        params.put("isserviceorder", "" + 1);
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
//        Log.e("TAG", "liststoresubcategory: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MASTER_URL, params, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Log.e("TAG", "liststoresubcategory: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                if (jsonObject.has("subcategorydata")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("subcategorydata");

                                    subCatArrayList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<ArrayList<Model>>() {
                                    }.getType();
                                    subCatArrayList = gson.fromJson(jsonArray.toString(), listType);


                                    categoryListAdapter = new CategoryListAdapter(getContext(), subCatArrayList,
                                            new CategoryListAdapter.subCatClickInterface() {
                                                @Override
                                                public void clickOnSubCat(String subcatId) {
                                                    str_subcatID = subcatId;
                                                    callApiItemList();
                                                }
                                            });
                                    rv_category.setAdapter(categoryListAdapter);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


    //<editor-fold desc="Call API Item">

    public void callApiItemList() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "liststoreitem");
        params.put("isserviceorder", "" + 1);
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("subcategoryid", str_subcatID);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MASTER_URL, params, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e("callApiItemList", "onResponse: " + response);
                        try {

//                            Log.e("TAG", "itemdata: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                if (jsonObject.has("itemdata")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("itemdata");

                                    itemsArrayList = new ArrayList<>();
                                    Gson gson = new Gson();
                                    Type listType = new TypeToken<ArrayList<Items>>() {
                                    }.getType();
                                    itemsArrayList = gson.fromJson(jsonArray.toString(), listType);

                                    if (itemsArrayList.size() > 0) {
                                        itemListAdapter = new ItemListAdapter(getContext(), itemsArrayList, null, ServiceOrderFragment.this);
                                        rv_item.setAdapter(itemListAdapter);
                                        rl_no_item.setVisibility(GONE);
                                        tv_nodata_item.setVisibility(GONE);
                                        rl_item_main.setVisibility(VISIBLE);
                                        tv_cart_switch.setVisibility(VISIBLE);
                                    } else {
                                        rl_no_item.setVisibility(VISIBLE);
                                        rl_item_main.setVisibility(GONE);
                                        tv_cart_switch.setVisibility(GONE);
                                        tv_nodata_item.setVisibility(VISIBLE);
                                    }
                                }
                            } else {
                                rl_no_item.setVisibility(VISIBLE);
                                rl_item_main.setVisibility(GONE);
                                tv_cart_switch.setVisibility(GONE);
                                tv_nodata_item.setVisibility(GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


    //<editor-fold desc="Contact dialog">
    Dialog contactDialog;
    EditText edt_contact_number;

    public void ContactDialog(Activity activity) {

        if (contactDialog != null && contactDialog.isShowing()) return;


        try {
            // We need to get the instance of the LayoutInflater

            contactDialog = new Dialog(activity);

            contactDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            contactDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            contactDialog.setContentView(R.layout.dialog_add_contact__box);
            contactDialog.setCancelable(true);
            Button btnOk = contactDialog.findViewById(R.id.btnyes);
            Button btn_cancel_contact_dgl = contactDialog.findViewById(R.id.btn_cancel_contact_dgl);
            TextInputLayout txt_hint_enter_contact_number = contactDialog.findViewById(R.id.txt_hint_enter_contact_number);
            edt_contact_number = contactDialog.findViewById(R.id.edt_contact_number);
            ImageView iv_gif = contactDialog.findViewById(R.id.iv_gif);

            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_lefttoright);
            Glide.with(activity).asGif().load(R.drawable.loader).into(iv_gif);
            iv_gif.startAnimation(animation);

            SessionManagement.savePreferences(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, "");
            try {
                txt_hint_enter_contact_number.setHint(Utility.languageLabel(mainActivity, LabelMaster.LBL_CONTACT_DLG_HIT_CONTACT_NUM).getLabel());
                btnOk.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_BTN_SUBMIT).getLabel());
                btn_cancel_contact_dgl.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_PER_BTN_CANCEL).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (edt_contact_number.getText().toString().isEmpty()) {
                        edt_contact_number.requestFocus();
                        edt_contact_number.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_CONTACT_DLG_ERR_CONTACT_NUM).getLabel());
                    } else if (!Validation.isValidPhoneNumber(getContext(), edt_contact_number)) {
                        edt_contact_number.requestFocus();
                        edt_contact_number.setError(Utility.languageLabel(mainActivity, LabelMaster.LBL_REG_ERR_VALID_CONTACT).getLabel());
                    } else {
                        contact_no = edt_contact_number.getText().toString();
                        ll_button.setVisibility(View.GONE);
                        rl_mycart.setVisibility(View.GONE);
                        view_customer360.setVisibility(VISIBLE);
                        tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                        tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));
                        callApiGetMember360Data();
                    }
                }
            });

            contactDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER /*&& event.getAction() == KeyEvent.ACTION_UP*/) {
                        contact_no = edt_contact_number.getText().toString();
                        ll_button.setVisibility(View.GONE);
                        rl_mycart.setVisibility(View.GONE);
                        view_customer360.setVisibility(VISIBLE);
                        tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                        tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));
                        callApiGetMember360Data();
                    }
                    return false;
                }
            });

            btn_cancel_contact_dgl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contactDialog.dismiss();
                    contactDialog = null;
                }
            });

            contactDialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>


    //<editor-fold desc="360 customer data api call">
    ArrayList<Member360> member360ArrayList;
    ArrayList<Items> itemsDetailArrayList;

    public void callApiGetMember360Data() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "listmemberhistory");
        params.put("contactno", contact_no);
        params.put("isserviceorder", "" + 1);
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));

//        Log.e("callApiGetMember360Data", "callApiGetMember360Data: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.MEMBER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Log.e("response", "callApiGetMember360Data: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                SessionManagement.savePreferences(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, contact_no);

                                ll_category.setVisibility(VISIBLE);
                                tv_lbl_nodata_customer.setVisibility(GONE);
                                try {
                                    contactDialog.dismiss();
                                    contactDialog = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                if (jsonObject.getInt("ismemberdata") == 1) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("memberdata");

                                    member360ArrayList = new ArrayList<>();
                                    itemsDetailArrayList = new ArrayList<>();

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Member360>>() {
                                    }.getType();

                                    member360ArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                    tv_full_name.setText(member360ArrayList.get(0).getPersonname());
                                    tv_c_number.setText(member360ArrayList.get(0).getContact());
                                    tv_email.setText(member360ArrayList.get(0).getEmail());
                                    tv_qatar_id.setText(member360ArrayList.get(0).getQataridno());
                                    tv_q_expiry.setText(member360ArrayList.get(0).getQataridexpiry());
                                    tv_dob.setText(member360ArrayList.get(0).getDob());
                                    tv_passport_id.setText(member360ArrayList.get(0).getPassportidno());
                                    tv_passport_expiry.setText(member360ArrayList.get(0).getPassportidexpiry());

                                    if (member360ArrayList.get(0).getQataridno().isEmpty() && member360ArrayList.get(0).getQataridexpiry().isEmpty()) {
                                        ln_qatar_data.setVisibility(GONE);
                                    } else {
                                        ln_qatar_data.setVisibility(VISIBLE);
                                    }

                                    if (member360ArrayList.get(0).getPassportidno().isEmpty() && member360ArrayList.get(0).getPassportidexpiry().isEmpty()) {
                                        ln_passport.setVisibility(GONE);
                                    } else {
                                        ln_passport.setVisibility(VISIBLE);
                                    }

                                    if (member360ArrayList.get(0).getIsmshipdetail() == (1)) {
                                        ActivePackagesListAdapter activePackagesListAdapter = new ActivePackagesListAdapter(getActivity(), member360ArrayList.get(0).getMshipdetail());
                                        rv_membership_list.setAdapter(activePackagesListAdapter);
                                        rv_membership_list.setVisibility(VISIBLE);
                                        tv_active_pck_title.setVisibility(VISIBLE);
                                        cv_active_packages.setVisibility(VISIBLE);
                                    } else {
                                        rv_membership_list.setVisibility(View.GONE);
                                        tv_active_pck_title.setVisibility(View.GONE);
                                        cv_active_packages.setVisibility(GONE);
                                    }

                                    if (member360ArrayList.get(0).getIsitemdetail() == (1)) {

                                        membershipItemArrayList = member360ArrayList.get(0).getItemdetail();

                                        MembershipItemsLisAdapter adapter = new MembershipItemsLisAdapter(getActivity(), membershipItemArrayList);
                                        rv_item_list.setAdapter(adapter);
                                        rv_item_list.setVisibility(VISIBLE);
                                        tv_item_title.setVisibility(VISIBLE);
                                        cv_active_packages.setVisibility(VISIBLE);
                                    }

                                    if (member360ArrayList.get(0).getIslastvisitcategory() == 1) {
                                        tv_last_visit_title.setText("Last Visit - " + member360ArrayList.get(0).getLastvisitdate());
                                        cv_last_visit.setVisibility(VISIBLE);
                                        rv_lastvisit.setLayoutManager(new LinearLayoutManager(getContext()));

                                        LastVisistParentAdapter lastVisistParentAdapter = new LastVisistParentAdapter(getContext(), member360ArrayList.get(0).getLastvisitcategory());
                                        rv_lastvisit.setAdapter(lastVisistParentAdapter);

                                    } else {
                                        cv_last_visit.setVisibility(GONE);
                                    }

                                    if (member360ArrayList.get(0).getIsitemdetail() == 1) {
                                        itemsDetailArrayList = member360ArrayList.get(0).getItemdetail();
                                        ln_view_more_package_detail.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                OpenItemDetailDialog();
                                            }
                                        });
                                        tv_view_more.setVisibility(VISIBLE);

                                    } else {
                                        tv_view_more.setVisibility(GONE);
                                    }

//                                    callApiGetWrokFlow();
                                }
                            } else {
                                ll_category.setVisibility(GONE);
                                tv_lbl_nodata_customer.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MESS_NO_CUSTOMER_DATA).getLabel());
                                tv_lbl_nodata_customer.setVisibility(VISIBLE);
                                mainActivity.iv_range_booking.setVisibility(GONE);
                                mainActivity.iv_workflow.setVisibility(GONE);

                                try {
                                    int incompleteprevstore = jsonObject.getInt("incompleteprevstore");
                                    if (incompleteprevstore == 1) {
                                        Utility.initErrorMessagePopupWindow(getActivity(), message);
                                    } else {
                                        edt_contact_number.requestFocus();
                                        edt_contact_number.setError(message);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    edt_contact_number.requestFocus();
                                    edt_contact_number.setError(message);
                                    tv_lbl_nodata_customer.setVisibility(VISIBLE);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //</editor-fold>


   /* ArrayList<Instruction> workFlowInstructionArrayList;
    ArrayList<OperationFlow> operationFlowArrayList;
    int current_pos;
    String curr_operationid, curr_displayorder;
    String sopid;

    public void callApiGetWrokFlow() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "lisoperationflow");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", member360ArrayList.get(0).getId());
        Log.e("TAG", "callApiGetWrokFlow: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.OPERATION_FLOW_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("TAG", "callApiGetWrokFlow: " + response);

                            SessionManagement.savePreferences(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, contact_no);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            sopid = jsonObject.getString("sopid");
                            int status = jsonObject.getInt("status");
                            int iscompulsory = jsonObject.getInt("iscompulsory");
                            int isinstruction = jsonObject.getInt("isinstruction");
                            int isoperationflowdata = jsonObject.getInt("isoperationflowdata");
                            int israngeassign = jsonObject.getInt("israngeassign");
                            curr_operationid = jsonObject.getString("curr_operationid");
                            curr_displayorder = jsonObject.getString("curr_displayorder");

                            if (iscompulsory == 2) {
                                mainActivity.iv_workflow.setVisibility(GONE);
                            } else {
                                mainActivity.iv_workflow.setVisibility(VISIBLE);
                            }

                            if (israngeassign == 1) {
                                mainActivity.iv_range_booking.setVisibility(VISIBLE);
                            } else {
                                mainActivity.iv_range_booking.setVisibility(GONE);
                            }

                            if (status == 1) {
                                workFlowInstructionArrayList = new ArrayList<>();
                                operationFlowArrayList = new ArrayList<>();

                                try {
                                    JSONArray jsonArray = jsonObject.getJSONArray("instructiondata");

                                    Gson gson = new Gson();
                                    Type listtype = new TypeToken<ArrayList<Instruction>>() {
                                    }.getType();
                                    workFlowInstructionArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                if (isoperationflowdata == 1) {
                                    try {
                                        JSONArray jsonArray = jsonObject.getJSONArray("operationflowdata");

                                        Gson gson = new Gson();
                                        Type listtype = new TypeToken<ArrayList<OperationFlow>>() {
                                        }.getType();
                                        operationFlowArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                        for (int i = 0; i < operationFlowArrayList.size(); i++) {
                                            if (operationFlowArrayList.get(i).getIscurrent().equals("1")) {
                                                current_pos = i;
                                            }
                                        }

                                        OperationFlowAdapter operationFlowAdapter = new OperationFlowAdapter(getActivity(), operationFlowArrayList, new OperationFlowAdapter.OnclickInterface() {
                                            @Override
                                            public void ClickOnOperationFlow(OperationFlow model) {
                                                if (model.getIsstoreinstruction().equals("1")) {
                                                    InstructionStoreVise(model.getStoreinstructiondata());
                                                } else {
                                                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_ERR_NO_INSTRUCTION).getLabel(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        rv_operation_flow_list.setAdapter(operationFlowAdapter);
                                        cv_operation_flow.setVisibility(VISIBLE);
                                        rv_operation_flow_list.getLayoutManager().scrollToPosition(current_pos);
                                        operationFlowAdapter.notifyDataSetChanged();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    cv_operation_flow.setVisibility(GONE);
                                }


                                mainActivity.iv_workflow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (isinstruction == 1) {
                                            InstructionSelectDialog(iscompulsory);
                                        } else {
                                            callApiInsertOperationInstruction("");
                                        }
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    BottomSheetDialog instructionDialog = null;

    private void InstructionStoreVise(ArrayList<Storeinstructiondatum> storeinstructiondata) {

        if (instructionDialog != null && instructionDialog.isShowing()) return;

        instructionDialog = new BottomSheetDialog(mainActivity);
        instructionDialog.setContentView(R.layout.dialog_instruction_selection);
        FrameLayout bottomSheet = instructionDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        instructionDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        instructionDialog.setCancelable(true);
        instructionDialog.show();


        ImageView iv_close_dlg;
        RecyclerView rv_instruction;
        TextView tv_dlg_nodata, tv_btn_dlg_done, tv_lbl_instruction_title;

        rv_instruction = instructionDialog.findViewById(R.id.rv_instruction);
        iv_close_dlg = instructionDialog.findViewById(R.id.iv_close_dlg);
        tv_dlg_nodata = instructionDialog.findViewById(R.id.tv_dlg_nodata);
        tv_btn_dlg_done = instructionDialog.findViewById(R.id.tv_btn_dlg_done);
        tv_lbl_instruction_title = instructionDialog.findViewById(R.id.tv_lbl_instruction_title);

        try {
            tv_lbl_instruction_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_INSTRUCTION).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }


        tv_btn_dlg_done.setVisibility(GONE);

        rv_instruction.setLayoutManager(new LinearLayoutManager(mainActivity));


        iv_close_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionDialog.dismiss();
            }
        });

        InstructionListAdapter instructionListAdapter = new InstructionListAdapter(getContext(), storeinstructiondata);
        rv_instruction.setAdapter(instructionListAdapter);


        instructionDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    instructionDialog.dismiss();
                }
                return false;
            }
        });


    }


    BottomSheetDialog instructionSelectDialog = null;

    ImageView iv_close_dlg;
    RecyclerView rv_instruction;
    TextView tv_dlg_nodata, tv_btn_dlg_done, tv_lbl_instruction_title;

    ArrayList<Instruction> instructionAPIArrayList;

    public void InstructionSelectDialog(int iscompulsory) {
        if (instructionSelectDialog != null && instructionSelectDialog.isShowing()) return;
        {
            instructionSelectDialog = new BottomSheetDialog(mainActivity);
            instructionSelectDialog.setContentView(R.layout.dialog_instruction_selection);
            FrameLayout bottomSheet = instructionSelectDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            instructionSelectDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            instructionSelectDialog.setCancelable(true);
            instructionSelectDialog.show();
        }

        iv_close_dlg = instructionSelectDialog.findViewById(R.id.iv_close_dlg);
        rv_instruction = instructionSelectDialog.findViewById(R.id.rv_instruction);
        tv_dlg_nodata = instructionSelectDialog.findViewById(R.id.tv_dlg_nodata);
        tv_btn_dlg_done = instructionSelectDialog.findViewById(R.id.tv_btn_dlg_done);
        tv_lbl_instruction_title = instructionSelectDialog.findViewById(R.id.tv_lbl_instruction_title);
        rv_instruction.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            tv_lbl_instruction_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_INSTRUCTION).getLabel());
            tv_btn_dlg_done.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_DONE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }


        InstructionSelectionAdapter instructionSelectionAdapter = new InstructionSelectionAdapter(getContext(), workFlowInstructionArrayList,
                new InstructionSelectionAdapter.OnclickListener() {
                    @Override
                    public void onClick(ArrayList<Instruction> instructionArrayList) {

                        instructionAPIArrayList = new ArrayList<>();

                        if (instructionArrayList.size() > 0) {
                            for (int i = 0; i < instructionArrayList.size(); i++) {
                                if (instructionArrayList.get(i).getIsitemadded() == 1) {
                                    instructionAPIArrayList.add(instructionArrayList.get(i));
                                }
                            }
                        }
                    }
                });

        rv_instruction.setAdapter(instructionSelectionAdapter);


        tv_btn_dlg_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (iscompulsory == 1) {
                    if (instructionAPIArrayList != null && instructionAPIArrayList.size() > 0) {
                        Log.e("TAG", "onClick: " + new Gson().toJson(instructionAPIArrayList));
                        callApiInsertOperationInstruction(new Gson().toJson(instructionAPIArrayList));
                    } else {
                        Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_ERR_SELECT_INSTRUCTION).getLabel(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    callApiInsertOperationInstruction(new Gson().toJson(instructionAPIArrayList));
                }
            }
        });


        iv_close_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionSelectDialog.dismiss();
            }
        });


        instructionSelectDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    instructionSelectDialog.dismiss();
                }
                return false;
            }
        });

    }

    public void callApiInsertOperationInstruction(String str_instructionArray) {

        HashMap<String, String> params = new HashMap<>();
        params.put("action", "insertstoreoperationdata");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", member360ArrayList.get(0).getId());
        params.put("operationid", curr_operationid);
        params.put("displayorder", curr_displayorder);
        params.put("instructiondata", str_instructionArray);
        Log.e("instructiondata", "params: " + params.toString());

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.OPERATION_FLOW_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("instructiondata", "response: " + response);

                            SessionManagement.savePreferences(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, contact_no);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            Utility.initErrorMessagePopupWindow(getActivity(), message);
//                            callApiGetWrokFlow();

                            try {
                                instructionSelectDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    //<editor-fold desc="Book Range Dialog">

    Dialog rangeBookingDialog = null;
    EditText edt_sel_range, edt_sel_lane;
    LinearLayout ln_book_range, ln_cancel_dlg;
    TextView tv_lbl_select_range, tv_lbl_select_lane, tv_btn_book_range, tv_lbl_cancel;

    public void OpenRangeBookingDialog() {

        if (rangeBookingDialog == null) {
            rangeBookingDialog = new Dialog(mainActivity);
            rangeBookingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            rangeBookingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            rangeBookingDialog.setContentView(R.layout.dialog_range_booking);
            rangeBookingDialog.setCancelable(false);
            rangeBookingDialog.show();
        }

        edt_sel_range = rangeBookingDialog.findViewById(R.id.edt_sel_range);
        edt_sel_lane = rangeBookingDialog.findViewById(R.id.edt_sel_lane);
        ln_book_range = rangeBookingDialog.findViewById(R.id.ln_book_range);
        tv_lbl_select_range = rangeBookingDialog.findViewById(R.id.tv_lbl_select_range);
        tv_lbl_select_lane = rangeBookingDialog.findViewById(R.id.tv_lbl_select_lane);
        tv_btn_book_range = rangeBookingDialog.findViewById(R.id.tv_btn_book_range);
        ln_cancel_dlg = rangeBookingDialog.findViewById(R.id.ln_cancel_dlg);
        tv_lbl_cancel = rangeBookingDialog.findViewById(R.id.tv_lbl_cancel);

        try {
            tv_lbl_select_range.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_RANGE).getLabel());
            tv_lbl_select_lane.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_LANE).getLabel());
            edt_sel_range.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_RANGE).getLabel());
            edt_sel_lane.setHint(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_LANE).getLabel());
            tv_btn_book_range.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_ASSIGN).getLabel());
            tv_lbl_cancel.setText(Utility.languageLabel(getActivity(), LabelMaster.LBL_DLG_PER_BTN_CANCEL).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        rangeBookingDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    rangeBookingDialog.dismiss();
                    rangeBookingDialog = null;
                }
                return false;
            }
        });

        ln_book_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidBookRange()) {
                    CallApiToBookRange();
                }
            }
        });

        ln_cancel_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rangeBookingDialog.dismiss();
                rangeBookingDialog = null;
            }
        });

        CallApiGetRangeList();
        callApiGetLaneList();
    }


    private void CallApiToBookRange() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "insertrangeassigndata");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", member360ArrayList.get(0).getId());
        params.put("rangeid", range_id);
        params.put("laneid", lane_id);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.OPERATION_FLOW_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.e("TAG", "itemdata: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            Utility.initErrorMessagePopupWindow(getActivity(), message);

                            if (status == 1) {

                                try {
                                    rangeBookingDialog.dismiss();
                                    rangeBookingDialog = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    private boolean isValidBookRange() {
        if (edt_sel_range.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_ERR_SELECT_RANGE).getLabel(), Toast.LENGTH_SHORT).show();
        } else if (edt_sel_lane.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_ERR_SELECT_LANE).getLabel(), Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    ArrayList<Model> rangeArrayList;
    SearchableSpinnerDialog rangeSpinnerDialog;
    String range_id = "";

    private void CallApiGetRangeList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststorerange");
        params.put("sopid", sopid);

        Log.e("response", "CallApiGetRangeList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.OPERATION_FLOW_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "CallApiGetRangeList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("storerangedata");
                                rangeArrayList = new ArrayList<>();

                                Gson gson = new Gson();
                                Type listtype = new TypeToken<ArrayList<Model>>() {
                                }.getType();

                                rangeArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                rangeSpinnerDialog = new SearchableSpinnerDialog(getActivity(), rangeArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_RANGE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                rangeSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                    @Override
                                    public void onClick(Model model, int i) {
                                        edt_sel_range.setText(model.getName());
                                        range_id = model.getId();
                                        callApiGetLaneList();
                                    }
                                });

                            } else {
                                rangeArrayList = new ArrayList<>();
                                range_id = "";
                                edt_sel_range.setText("");
                            }

                            edt_sel_range.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        rangeSpinnerDialog.showSpinerDialog();
                                    } else {
                                        Utility.initErrorMessagePopupWindow(getActivity(), message);
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    ArrayList<Model> laneArrayList;
    SearchableSpinnerDialog laneSpinnerDialog;
    String lane_id = "";

    private void callApiGetLaneList() {
        Map<String, String> params = new HashMap<>();
        params.put("action", "liststorerangelane");
        params.put("sopid", sopid);
        params.put("rangeid", range_id);

        Log.e("response", "callApiGetLaneList: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.OPERATION_FLOW_URL, params, 1, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "callApiGetLaneList: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            int status = jsonObject.getInt("status");

                            if (status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("storerangelanedata");
                                laneArrayList = new ArrayList<>();

                                Gson gson = new Gson();
                                Type listtype = new TypeToken<ArrayList<Model>>() {
                                }.getType();

                                laneArrayList = gson.fromJson(jsonArray.toString(), listtype);

                                laneSpinnerDialog = new SearchableSpinnerDialog(getActivity(), laneArrayList, Utility.languageLabel(getActivity(), LabelMaster.LBL_RANGE_SELECT_LANE).getLabel(), R.style.DialogAnimations_SmileWindow);

                                laneSpinnerDialog.bindOnSpinerListener(new OnSpinerItemClickSpinnerDialog() {
                                    @Override
                                    public void onClick(Model model, int i) {
                                        edt_sel_lane.setText(model.getName());
                                        lane_id = model.getId();
                                    }
                                });

                            } else {
                                laneArrayList = new ArrayList<>();
                                lane_id = "";
                                edt_sel_lane.setText("");
                            }

                            edt_sel_lane.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (status == 1) {
                                        laneSpinnerDialog.showSpinerDialog();
                                    } else {
                                        Utility.initErrorMessagePopupWindow(getActivity(), message);
                                    }
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    //</editor-fold>
*/

    //<editor-fold desc="CartList Array get">

    public ArrayList<MyCart> getCartArray() {
        ArrayList<MyCart> temp_cartArrayList = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<MyCart>>() {
        }.getType();
        temp_cartArrayList = new Gson().fromJson(SessionManagement.getStringValue(mainActivity, AppConstant.TEMPCART, ""), listType);
        if (temp_cartArrayList == null) {
            temp_cartArrayList = new ArrayList<>();
        }
        return temp_cartArrayList;
    }
    //</editor-fold>


    //<editor-fold desc="Add to Cart">


    public double final_Price = 0.0, taxableamt = 0.0, taxamt = 0.0;

    public void AddToCart(Items itemModel) {
        final_Price = 0.0;
        taxableamt = 0.0;
        taxamt = 0.0;

        try {
            if(cartArrayList.isEmpty() ||cartArrayList==null){
            cartArrayList = getCartArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
            cartArrayList = new ArrayList<>();
        }


        MyCart cartModel = new MyCart();
        cartModel.setItemid(itemModel.getId());

        if (cartArrayList.contains(cartModel)) {
            int cartIndex = cartArrayList.indexOf(cartModel);
            cartArrayList.get(cartIndex).setQty(cartArrayList.get(cartIndex).getQty() + 1);
            cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getQty());
            SessionManagement.savePreferences(mainActivity, AppConstant.TEMPCART, new Gson().toJson(cartArrayList));
            manageCartScheme(itemModel, cartIndex, true);
        } else {
            calculateDefaultItem(itemModel, cartArrayList.size() - 1, 1, false);
            SessionManagement.savePreferences(mainActivity, AppConstant.TEMPCART, new Gson().toJson(cartArrayList));
            manageCartScheme(itemModel, cartArrayList.size() - 1, true);
        }


        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));


    }
    //</editor-fold>


    //<editor-fold desc="Manage free / discount / fix and default price">
    public void manageCartScheme(Items itemModel, int cartIndex, boolean isUpdate) {

        Items item360Model = new Items();
        item360Model.setId(itemModel.getId());

        try {
            if(cartArrayList==null){
            cartArrayList = new ArrayList<>();
            }
            cartArrayList = getCartArray();
        } catch (Exception e) {
            e.printStackTrace();
            cartArrayList = new ArrayList<>();
        }

        cartArrayList.get(cartIndex).setSummaryDetails(new ArrayList<>());

        if (membershipItemArrayList.contains(item360Model)) {

            int schemeIndex = membershipItemArrayList.indexOf(item360Model);

            for (int i = 0; i < membershipItemArrayList.get(schemeIndex).getItemsubdetail().size(); i++) {
                if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getType().equals("1")) { // free

                    if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty() >= cartArrayList.get(cartIndex).getTemp_remainqty()) {
                        calculateFreeItem(schemeIndex, i, cartIndex, cartArrayList.get(cartIndex).getTemp_remainqty());
                        break;
                    } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty() > 0 &&
                            cartArrayList.get(cartIndex).getTemp_remainqty() > 0) {

                        int tempQty = cartArrayList.get(cartIndex).getTemp_remainqty() - membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty();
                        calculateFreeItem(schemeIndex, i, cartIndex, membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty());


                        if (i == membershipItemArrayList.get(schemeIndex).getItemsubdetail().size() - 1) {
                            calculateDefaultItem(itemModel, cartIndex, tempQty, isUpdate);
                        }
                    } else {
                        calculateDefaultItem(itemModel, cartIndex, (cartArrayList.get(cartIndex).getQty()), isUpdate);
                    }


                } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getType().equals("2")) {  // discount

                    if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty() >= cartArrayList.get(cartIndex).getTemp_remainqty()) {
                        calculateDiscountItem(schemeIndex, i, cartIndex, cartArrayList.get(cartIndex).getTemp_remainqty());
                        break;
                    } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty() > 0 &&
                            cartArrayList.get(cartIndex).getTemp_remainqty() > 0) {

                        int tempQty = cartArrayList.get(cartIndex).getTemp_remainqty() - membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty();
                        calculateDiscountItem(schemeIndex, i, cartIndex, membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty());

                        if (i == membershipItemArrayList.get(schemeIndex).getItemsubdetail().size() - 1) {
                            calculateDefaultItem(itemModel, cartIndex, tempQty, isUpdate);
                        }
                    } else {
                        calculateDefaultItem(itemModel, cartIndex, (cartArrayList.get(cartIndex).getQty()), isUpdate);
                    }

                } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getType().equals("3")) {  // fix rate

                    if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty() >= cartArrayList.get(cartIndex).getTemp_remainqty()) {
                        calculateFixItem(schemeIndex, i, cartIndex, cartArrayList.get(cartIndex).getTemp_remainqty());
                        break;
                    } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty() > 0 &&
                            cartArrayList.get(cartIndex).getTemp_remainqty() > 0) {

                        int tempQty = cartArrayList.get(cartIndex).getTemp_remainqty() - membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty();
                        calculateFixItem(schemeIndex, i, cartIndex, membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(i).getQty());
                        if (i == membershipItemArrayList.get(schemeIndex).getItemsubdetail().size() - 1) {
                            calculateDefaultItem(itemModel, cartIndex, tempQty, isUpdate);
                        }
                    } else {
//                        calculateFreeItem(schemeIndex, i, cartIndex, cartArrayList.get(cartIndex).getTemp_remainqty());
                        calculateDefaultItem(itemModel, cartIndex, (cartArrayList.get(cartIndex).getQty()), isUpdate);
                    }

                } else {
                    calculateDefaultItem(itemModel, cartIndex, (cartArrayList.get(cartIndex).getQty()), isUpdate);
                }
            }
        } else {
            calculateDefaultItem(itemModel, cartIndex, (cartArrayList.get(cartIndex).getQty()), isUpdate);
        }

        // Log.e("TAG", "manageCartScheme: " + new Gson().toJson(cartArrayList));

//        Log.e("TAG", "AddToCart: " + new Gson().toJson(cartArrayList));
        SessionManagement.savePreferences(mainActivity, AppConstant.TEMPCART, new Gson().toJson(cartArrayList));
        CalculateCartTotal();
        SetCartAdapter();


    }


    public void calculateFreeItem(int schemeIndex, int subItemPos, int cartIndex, int itemQty) {


        SummaryDetail summaryDetail = new SummaryDetail();
        summaryDetail.setType(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getType());
        summaryDetail.setTypename(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTypename());
        summaryDetail.setOidid(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getOidid());
        summaryDetail.setQty(itemQty);
        summaryDetail.setDiscount(0.0);
        summaryDetail.setDiscountamt(0.0);
        summaryDetail.setPrice(0.0);
        summaryDetail.setTaxable(0.0);
        summaryDetail.setTaxtypename(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtypename());
        summaryDetail.setTaxtype(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype());
        summaryDetail.setSgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getSgst());
        summaryDetail.setCgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getCgst());
        summaryDetail.setIgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst());
        summaryDetail.setSgsttaxamt(0.0);
        summaryDetail.setCgsttaxamt(0.0);
        summaryDetail.setIgsttaxamt(0.0);
        summaryDetail.setFinalprice(0.0);

//        cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getQty() - itemQty);
        cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getTemp_remainqty() - itemQty);

        if (cartArrayList.get(cartIndex).getSummaryDetails().contains(summaryDetail)) {
            cartArrayList.get(cartIndex).getSummaryDetails().remove(summaryDetail);
            cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
        } else {
            cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
        }

        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));
    }


    public void calculateDiscountItem(int schemeIndex, int subItemPos, int cartIndex, int itemQty) {

        SummaryDetail summaryDetail = new SummaryDetail();
        summaryDetail.setType(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getType());
        summaryDetail.setTypename(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTypename());
        summaryDetail.setOidid(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getOidid());
        summaryDetail.setQty(itemQty);

        double taxableamt = 0.0;
        double taxamt = 0.0;
        double final_Price = 0.0;
        double price = 0.0;
        price = membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice();

        // dis = 8
        double discAmt = ((price * membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getDiscount()) / 100);
        price = membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice() - discAmt;  // 72

//        if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype().equals("1")) {
//            //  Exclusive  tax
//            taxableamt = (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice());
//            taxamt = (taxableamt * (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst())) / 100;
//            final_Price = taxableamt + taxamt;
//        } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype().equals("2")) {
//            // Inclusive  tax
//            final_Price = (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice());
//            taxableamt = (final_Price * 100) / (100 + (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst()));
//            taxamt = final_Price - taxableamt;
//        }

        if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype().equals("1")) {
            //  Exclusive  tax
            taxableamt = (price);
            taxamt = (taxableamt * (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst())) / 100;
            final_Price = taxableamt + taxamt;
        } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype().equals("2")) {
            // Inclusive  tax
            final_Price = (price);
            taxableamt = (final_Price * 100) / (100 + (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst()));
            taxamt = final_Price - taxableamt;
        }

        summaryDetail.setTaxable(taxableamt * itemQty);

//        double discAmt = ((taxableamt * membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getDiscount()) / 100);
//        final_Price = final_Price - discAmt;

        summaryDetail.setDiscount(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getDiscount());
        summaryDetail.setDiscountamt(discAmt);
        summaryDetail.setPrice(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice());
        summaryDetail.setTaxtypename(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtypename());
        summaryDetail.setTaxtype(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype());
        summaryDetail.setSgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getSgst());
        summaryDetail.setCgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getCgst());
        summaryDetail.setIgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst());
        summaryDetail.setSgsttaxamt((taxamt / 2) * itemQty);
        summaryDetail.setCgsttaxamt((taxamt / 2) * itemQty);
        summaryDetail.setIgsttaxamt(taxamt * itemQty);
        summaryDetail.setFinalprice(final_Price * itemQty);


//        cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getQty() - itemQty);
        cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getTemp_remainqty() - itemQty);

        if (cartArrayList.get(cartIndex).getSummaryDetails().contains(summaryDetail)) {
            cartArrayList.get(cartIndex).getSummaryDetails().remove(summaryDetail);
            cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
        } else {
            cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
        }
        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));
    }


    public void calculateFixItem(int schemeIndex, int subItemPos, int cartIndex, int itemQty) {


        SummaryDetail summaryDetail = new SummaryDetail();
        summaryDetail.setType(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getType());
        summaryDetail.setTypename(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTypename());
        summaryDetail.setOidid(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getOidid());
        summaryDetail.setQty(itemQty);

        double taxableamt = 0.0;
        double taxamt = 0.0;
        double final_Price = 0.0;

        if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype().equals("1")) {
            //  Exclusive  tax
            taxableamt = (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice());
            taxamt = (taxableamt * (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst())) / 100;
            final_Price = taxableamt + taxamt;
        } else if (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype().equals("2")) {
            // Inclusive  tax
            final_Price = (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice());
            taxableamt = (final_Price * 100) / (100 + (membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst()));
            taxamt = final_Price - taxableamt;
        }

        summaryDetail.setTaxable(taxableamt * itemQty);
        summaryDetail.setDiscount(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getDiscount());
        summaryDetail.setDiscountamt(0.00);
        summaryDetail.setPrice(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getPrice());
        summaryDetail.setTaxtypename(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtypename());
        summaryDetail.setTaxtype(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getTaxtype());
        summaryDetail.setSgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getSgst());
        summaryDetail.setCgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getCgst());
        summaryDetail.setIgst(membershipItemArrayList.get(schemeIndex).getItemsubdetail().get(subItemPos).getIgst());
        summaryDetail.setSgsttaxamt((taxamt / 2) * itemQty);
        summaryDetail.setCgsttaxamt((taxamt / 2) * itemQty);
        summaryDetail.setIgsttaxamt(taxamt * itemQty);
        summaryDetail.setFinalprice(final_Price * itemQty);


//        cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getQty() - itemQty);
        cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getTemp_remainqty() - itemQty);

        if (cartArrayList.get(cartIndex).getSummaryDetails().contains(summaryDetail)) {
            cartArrayList.get(cartIndex).getSummaryDetails().remove(summaryDetail);
            cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
        } else {
            cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
        }

        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));
    }


    public void calculateDefaultItem(Items itemModel, int cartIndex, int itemQty, boolean isUpdate) {

        if (isUpdate) {

            SummaryDetail summaryDetail = new SummaryDetail();
            summaryDetail.setType(itemModel.getType());
            summaryDetail.setTypename("");
            summaryDetail.setOidid("00000000-0000-0000-0000-000000000000");
            summaryDetail.setQty(itemQty);

            double taxableamt = 0.0;
            double taxamt = 0.0;
            double final_Price = 0.0;

            if (itemModel.getTaxtype().equals("1")) {
                //  Exclusive  tax
                taxableamt = (itemModel.getPrice());
                taxamt = (taxableamt * Double.parseDouble(itemModel.getIgst())) / 100;
                final_Price = taxableamt + taxamt;
            } else if (itemModel.getTaxtype().equals("2")) {
                // Inclusive  tax
                final_Price = (itemModel.getPrice());
                taxableamt = (final_Price * 100) / (100 + Double.parseDouble(itemModel.getIgst()));
                taxamt = final_Price - taxableamt;
            }

            summaryDetail.setTaxable(taxableamt * itemQty);
            summaryDetail.setDiscount(itemModel.getDiscount());
            summaryDetail.setDiscountamt(0.00);
            summaryDetail.setPrice(itemModel.getPrice());
            summaryDetail.setTaxtypename(itemModel.getTaxtypename());
            summaryDetail.setTaxtype(itemModel.getTaxtype());
            summaryDetail.setSgst(Double.valueOf(itemModel.getSgst()));
            summaryDetail.setCgst(Double.valueOf(itemModel.getCgst()));
            summaryDetail.setIgst(Double.valueOf(itemModel.getIgst()));
            summaryDetail.setSgsttaxamt((taxamt / 2) * itemQty);
            summaryDetail.setCgsttaxamt((taxamt / 2) * itemQty);
            summaryDetail.setIgsttaxamt(taxamt * itemQty);
            summaryDetail.setFinalprice(final_Price * itemQty);


//            cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getQty() - itemQty);
            cartArrayList.get(cartIndex).setTemp_remainqty(cartArrayList.get(cartIndex).getTemp_remainqty() - itemQty);

            if (cartArrayList.get(cartIndex).getSummaryDetails().contains(summaryDetail)) {
                cartArrayList.get(cartIndex).getSummaryDetails().remove(summaryDetail);
                cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
            } else {
                cartArrayList.get(cartIndex).getSummaryDetails().add(summaryDetail);
            }


        } else {

            MyCart myCart = new MyCart();
            myCart.setItemid(itemModel.getId());
            myCart.setItemname(itemModel.getName());
            myCart.setCategoryid(itemModel.getCategoryid());
            myCart.setCategory(itemModel.getCategory());
            myCart.setSubcategoryid(itemModel.getSubcategoryid());
            myCart.setSubcategory(itemModel.getSubcategory());
            myCart.setQty(itemQty);
            myCart.setTemp_remainqty(itemQty);

            if (!isUpdate) {
                cartArrayList.add(myCart);
            } else {
                cartArrayList.remove(myCart);
                cartArrayList.add(myCart);
            }
        }

        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));
    }
    //</editor-fold>


    //<editor-fold desc="Calculate Cart Total">
    double total_price_withqty = 0.0;
    double total_taxable_amount_withqty = 0.0;
    double total_tax_amount_withqty = 0.0;
    double total_payable_amount_withqty = 0.0;
    double total_item_discount = 0.0;
    double total_item_discount_all = 0.0;

    public void CalculateCartTotal() {

        total_price_withqty = 0.0;
        total_taxable_amount_withqty = 0.0;
        total_tax_amount_withqty = 0.0;
        total_payable_amount_withqty = 0.0;
        final_Price = 0.0;
        taxableamt = 0.0;
        taxamt = 0.0;
        total_item_discount=0.0;
        total_item_discount_all=0.0;
        Double price = 0.0;

        try {
            if(cartArrayList==null){
            cartArrayList = getCartArray();}
        } catch (Exception e) {
            e.printStackTrace();
            cartArrayList = new ArrayList<>();
        }


//        if (cartArrayList.size() > 0) {
//            for (int i = 0; i < cartArrayList.size(); i++) {
//                for (int j = 0; j < cartArrayList.get(i).getSummaryDetails().size(); j++) {
//                    total_price_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getQty()
//                            * (cartArrayList.get(i).getSummaryDetails().get(j).getPrice());
//                    total_taxable_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getTaxable();
//                    total_tax_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getIgsttaxamt();
//                    total_payable_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getFinalprice();
//                }
//            }
//        }

        if (cartArrayList.size() > 0) {

            for (int i = 0; i < cartArrayList.size(); i++) {
                for (int j = 0; j < cartArrayList.get(i).getSummaryDetails().size(); j++) {

                    if (cartArrayList.get(i).getSummaryDetails().get(j).getType() != null && cartArrayList.get(i).getSummaryDetails().get(j).getType().equals("2")) {
                        price = (cartArrayList.get(i).getSummaryDetails().get(j).getPrice()*cartArrayList.get(i).getSummaryDetails().get(j).getDiscount())/100 - cartArrayList.get(i).getSummaryDetails().get(j).getDiscountamt();
//                        total_item_discount+=cartArrayList.get(i).getSummaryDetails().get(j).getDiscount();

                        if (cartArrayList.get(i).getSummaryDetails().get(j).getTaxtype().equals("1")) {
                            //  Exclusive  tax
                            taxableamt = (price);
                            taxamt = (taxableamt * (cartArrayList.get(i).getSummaryDetails().get(j).getIgst())) / 100;
                            final_Price = taxableamt + taxamt;
                        } else if (cartArrayList.get(i).getSummaryDetails().get(j).getTaxtype().equals("2")) {
                            // Inclusive  tax
                            final_Price = (price);
                            taxableamt = (final_Price * 100) / (100 + (cartArrayList.get(i).getSummaryDetails().get(j).getIgst()));
                            taxamt = final_Price - taxableamt;
                        }
                        total_item_discount = ( (cartArrayList.get(i).getSummaryDetails().get(j).getPrice())*cartArrayList.get(i).getDisc())/100;
                        total_price_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getFinalprice();
                        total_item_discount_all+=total_item_discount;

                        total_taxable_amount_withqty += taxableamt * cartArrayList.get(i).getSummaryDetails().get(j).getQty()-total_item_discount;
                        total_tax_amount_withqty += taxamt * cartArrayList.get(i).getSummaryDetails().get(j).getQty();
                        cartArrayList.get(i).getSummaryDetails().get(j).setFinalprice(cartArrayList.get(i).getSummaryDetails().get(j).getFinalprice()-total_item_discount);
                        total_payable_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getFinalprice();
                    } else {
                        total_item_discount = (cartArrayList.get(i).getSummaryDetails().get(j).getQty()
                                * (cartArrayList.get(i).getSummaryDetails().get(j).getPrice())*cartArrayList.get(i).getDisc())/100;
                        total_price_withqty += (cartArrayList.get(i).getSummaryDetails().get(j).getQty()
                                * (cartArrayList.get(i).getSummaryDetails().get(j).getPrice()));
                        total_item_discount_all+=total_item_discount;
                        total_taxable_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getTaxable()-total_item_discount;
                        total_tax_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getIgsttaxamt();
                        cartArrayList.get(i).getSummaryDetails().get(j).setFinalprice(cartArrayList.get(i).getSummaryDetails().get(j).getFinalprice()-total_item_discount);
                        total_payable_amount_withqty += cartArrayList.get(i).getSummaryDetails().get(j).getFinalprice();
                    }
                }
            }
        }

        tv_total_price.setText("Qr." + String.format("%.2f", total_price_withqty));
        tv_tot_vat.setText("Qr." + String.format("%.2f", (total_tax_amount_withqty)));
        tv_total_taxable_amt.setText("Qr." + String.format("%.2f", total_taxable_amount_withqty));
        tv_total_payable_amount.setText("Qr." + String.format("%.2f", total_taxable_amount_withqty));

        if(total_item_discount_all>0){
        ll_bill_discount.setVisibility(VISIBLE);
        tv_bill_discount.setText("Qr." + String.format("%.2f", total_item_discount_all));
        }
        else{
            ll_bill_discount.setVisibility(GONE);
        }

        SessionManagement.savePreferences(mainActivity, AppConstant.TEMPCART, new Gson().toJson(cartArrayList));
//        Log.e("Add", "ITEMCARTARRAY" + new Gson().toJson(cartArrayList));

//        SetCartAdapter();

    }
    //</editor-fold>

//    CartAdapter cartAdapter = new CartAdapter((Context) getActivity(), cartArrayList, null, ServiceOrderFragment.this,ServiceOrderFragment.this ,SessionManagement.getStringValue(getContext(),AppConstant.UDISCOUNT,""));

    //<editor-fold desc="Set Adapter">
    public void SetCartAdapter() {
//        Log.e("SetCartAdapter", "SetCartAdapter: " + new Gson().toJson(cartArrayList));
//        checkcart();
        try {
            if (cartArrayList != null && cartArrayList.size() > 0) {
                rv_cart.setLayoutManager(new LinearLayoutManager(getActivity()));
                CartAdapter cartAdapter = new CartAdapter((Context) getActivity(), cartArrayList, null, ServiceOrderFragment.this,ServiceOrderFragment.this ,SessionManagement.getStringValue(getContext(),AppConstant.UDISCOUNT,""));
                cartAdapter.notifyDataSetChanged();
                rv_cart.setAdapter(cartAdapter);
                rv_cart.setVisibility(VISIBLE);
                ll_total.setVisibility(VISIBLE);
                ll_maintotal.setVisibility(VISIBLE);
                tv_nodata_cart.setVisibility(View.GONE);

                rl_mycart.setVisibility(VISIBLE);
                view_customer360.setVisibility(View.GONE);
                ll_button.setVisibility(VISIBLE);
                tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));

            } else {
                tv_nodata_cart.setVisibility(VISIBLE);
                rv_cart.setVisibility(View.GONE);
                ll_maintotal.setVisibility(View.GONE);
                ll_total.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>


    //<editor-fold desc="Payment dialog">

    Dialog paymentdialog = null;


    EditText et_amount;
    TextView tv_header, tv_change, tv_remaining, tv_bill_discount;
    LinearLayout ll_bill_discount;
    Button btn_cancel, btn_printbill;
    TextView tv_totamt, tv_paidamt,tv_totdiscamt;
    RecyclerView rv_payment, rv_paymenttype, rv_cashnote;
    LinearLayout ll_remaing, ll_change;
    TextInputLayout til_rf_number;
    EditText edt_rf_number,edt_discount;


    //variable
    public String str_name_paytype = "", str_type = "", str_paytypeID = "";
    ArrayList<PaymentModel> paymentModelArrayList;


    Numpad numpad;
    double value=0.0;
    private void getPaymentMethodeDialog() {

        if (paymentdialog == null) {
            paymentdialog = new Dialog(getContext());
            paymentdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            paymentdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            paymentdialog.setContentView(R.layout.dialog_paymentmethode);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            final int height = ViewGroup.LayoutParams.MATCH_PARENT;
            paymentdialog.getWindow().setLayout(width, height);
            paymentdialog.setCancelable(false);
            paymentdialog.show();
        }

        TextView tv_lbl_payment_type, tv_lbl_amount, tv_lbl_total_amount, tv_discounttitle, tv_lbl_payable_amt, tv_lbl_paid_amt, tv_lbl_remaing_amt, tv_lbl_payment_dlg_title_change_amt;
        //<editor-fold desc="Find View by Id">
        et_amount = paymentdialog.findViewById(R.id.et_amount);
        tv_header = paymentdialog.findViewById(R.id.tv_header);
        tv_change = paymentdialog.findViewById(R.id.tv_change);
        tv_remaining = paymentdialog.findViewById(R.id.tv_remaining);
        rv_paymenttype = paymentdialog.findViewById(R.id.rv_paymenttype);
        numpad = paymentdialog.findViewById(R.id.numpad);
        tv_totamt = paymentdialog.findViewById(R.id.tv_totamt);
        tv_totdiscamt=paymentdialog.findViewById(R.id.tv_totdiscamt);
        tv_paidamt = paymentdialog.findViewById(R.id.tv_paidamt);
        ll_remaing = paymentdialog.findViewById(R.id.ll_remaing);
        ll_change = paymentdialog.findViewById(R.id.ll_change);
        tv_lbl_payment_type = paymentdialog.findViewById(R.id.tv_lbl_payment_type);
        tv_lbl_amount = paymentdialog.findViewById(R.id.tv_lbl_amount);
        tv_lbl_total_amount = paymentdialog.findViewById(R.id.tv_lbl_total_amount);
        tv_discounttitle = paymentdialog.findViewById(R.id.tv_discounttitle);
        tv_lbl_payable_amt = paymentdialog.findViewById(R.id.tv_lbl_payable_amt);
        tv_lbl_paid_amt = paymentdialog.findViewById(R.id.tv_lbl_paid_amt);
        tv_lbl_remaing_amt = paymentdialog.findViewById(R.id.tv_lbl_remaing_amt);
        tv_lbl_payment_dlg_title_change_amt = paymentdialog.findViewById(R.id.tv_lbl_payment_dlg_title_change_amt);
        tv_header = paymentdialog.findViewById(R.id.tv_header);
        til_rf_number = paymentdialog.findViewById(R.id.til_rf_number);
        edt_rf_number = paymentdialog.findViewById(R.id.edt_rf_number);
        edt_discount = paymentdialog.findViewById(R.id.edt_discount);

        //</editor-fold>

        try {
            if(total_item_discount_all>0){
                edt_discount.setFocusable(false);
                edt_discount.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                edt_discount.setClickable(false);
                tv_totdiscamt.setText("Qr ."+total_item_discount_all);
            }
            tv_lbl_payment_type.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_PAYMENT_TYPE).getLabel());
            tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
            tv_lbl_total_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_TOTAL_AMT).getLabel() + " :");
            tv_discounttitle.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_DIS_AMT).getLabel() + " :");
            tv_lbl_payable_amt.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_PAYABLE_AMT).getLabel() + " :");
            tv_lbl_paid_amt.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_PAID_AMT).getLabel() + " :");
            tv_lbl_remaing_amt.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_REM_AMT).getLabel() + " :");
            tv_lbl_payment_dlg_title_change_amt.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_CHANGE_AMT).getLabel() + " :");
            btn_printbill.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_BTN_PRINT_BILL).getLabel());
            btn_cancel.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_PER_BTN_CANCEL).getLabel());
            tv_header.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Utility.hideKeyboardFrom(getContext(), et_amount);

        rv_paymenttype.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        btn_cancel = paymentdialog.findViewById(R.id.btn_cancel);
        btn_printbill = paymentdialog.findViewById(R.id.btn_printbill);

        rv_payment = paymentdialog.findViewById(R.id.rv_payment);
        rv_payment.setLayoutManager(new LinearLayoutManager(getContext()));

        rv_cashnote = paymentdialog.findViewById(R.id.rv_cashnote);
        rv_cashnote.setLayoutManager(new GridLayoutManager(getContext(), 4));

        if (referenceno.isEmpty()) {
            edt_rf_number.setText("");
        } else {
            edt_rf_number.setText(referenceno);
        }

        String paymentlist = SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<PaymentModel>>() {
        }.getType();

        if (!paymentlist.isEmpty()) {
            paymentModelArrayList = gson.fromJson(paymentlist, type);
        } else {
            paymentModelArrayList = new ArrayList<>();
        }

//        Log.e("Dialog_payment", "getPaymentMethodeDialog: " + new Gson().toJson(paymentModelArrayList));

        Callapilistcurrency();
        getPaymentTypelist();


        et_amount.requestFocus();
        et_amount.setCursorVisible(true);
        et_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.hideKeyboardFrom(getContext(), view);
            }
        });


        numpad.bindNextStepHandler(new NumpadHandler() {
            @Override
            public void nextStep() {
                if (!et_amount.getText().toString().contains(".")) {
                    String number = et_amount.getText().toString().trim() + ".";
                    et_amount.setText("");
                    et_amount.append(number);
                }
            }
        });

        numpad.setIconsColor(getResources().getColor(R.color.colorPrimary));

        tv_totamt.setText(String.format("%.2f", Double.parseDouble(String.valueOf(total_payable_amount_withqty))));
//         value=total_payable_amount_withqty;
//
//        tv_totdiscamt.setText(String.format("%.2f", value));
        tv_totamt.setText(String.format("%.2f", Double.parseDouble(String.valueOf(total_taxable_amount_withqty))));

        tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(total_taxable_amount_withqty))-value));
        tv_header.setText("Total : " + String.format("%.2f", total_taxable_amount_withqty));


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentdialog.dismiss();
                paymentdialog = null;
            }
        });
        edt_discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()>0&&Double.parseDouble(editable.toString())<=
                        Double.parseDouble(SessionManagement.getStringValue(getContext(),AppConstant.UDISCOUNT,"")))
                {
                    value = (total_taxable_amount_withqty * Double.parseDouble(edt_discount.getText().toString())) / 100;
                    tv_totdiscamt.setText(String.format("%.2f", value));
                    total_item_discount_all=value;
                    tv_totamt.setText(String.format("%.2f", Double.parseDouble(String.valueOf(total_taxable_amount_withqty))));
                    double temp_FinalTotal=total_taxable_amount_withqty-value;
                    int remaininng = (int) Math.round(temp_FinalTotal);
                    tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(remaininng))));

//                    tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(totalRemainAmt))));
                    tv_header.setText("Total : " + String.format("%.2f", total_taxable_amount_withqty));

                }else{
                    value = 0.0;
                    tv_totdiscamt.setText(String.format("%.2f", value));
                    total_item_discount_all=value;
                    tv_totamt.setText(String.format("%.2f", Double.parseDouble(String.valueOf(total_taxable_amount_withqty))));
                    double temp_FinalTotal=total_taxable_amount_withqty-value;
                    int remaininng = (int) Math.round(temp_FinalTotal);
                    tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(remaininng))));

//                    tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(totalRemainAmt))));
                    tv_header.setText("Total : " + String.format("%.2f", total_taxable_amount_withqty));

                }
                if(editable.toString().length()>0&&Double.parseDouble(editable.toString())>
                        Double.parseDouble(SessionManagement.getStringValue(getContext(),AppConstant.UDISCOUNT,""))){
                    OpenConfirmDialog("position","fn","You don't have permission to give much discount ");
                }
                            {
            }

        }
        });

        btn_printbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Double.valueOf(tv_remaining.getText().toString()) > 0) {
                    Toast.makeText(getContext(), Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_ERR_FULL_AMT).getLabel(), Toast.LENGTH_SHORT).show();
                } else {
                    callApiInsertOrder();
                }
            }
        });

        paymentAdapter = new PaymentAdapter(getContext(), paymentModelArrayList, null, ServiceOrderFragment.this);
        rv_payment.setAdapter(paymentAdapter);

        CalculateChanegAndRemainingAmt();
    }


    //<editor-fold desc="List Currency API call">
    private void Callapilistcurrency() {

        Map<String, String> params = new HashMap<>();
        params.put("action", "listnoteamount");

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            String msg = jsonObject.getString("message");

                            if (status == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("notedata");
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<Model>>() {
                                }.getType();
                                ArrayList<Model> currencyModelArrayList = new ArrayList<>();
                                currencyModelArrayList = gson.fromJson(jsonArray.toString(), type);

                                CashNoteAdapter cashNoteAdapter = new CashNoteAdapter(getContext(), currencyModelArrayList, et_amount, null, ServiceOrderFragment.this);
                                rv_cashnote.setAdapter(cashNoteAdapter);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    //</editor-fold>


    public ArrayList<Model> paymentmodelArrayList;

    private void getPaymentTypelist() {

        Map<String, String> param = new HashMap<>();
        param.put("action", "listpaymenttype");

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.STORE_ORDER_URL, param, 2, false, false,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("status") == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("paymenttypedata");
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<Model>>() {
                                }.getType();


                                paymentmodelArrayList = new ArrayList<>();
                                paymentmodelArrayList = gson.fromJson(jsonArray.toString(), type);
                                paymentmodelArrayList.remove(0);

                                PaymentTypeAdapter paymentTypeAdapter = new PaymentTypeAdapter(getContext(), paymentmodelArrayList, null, ServiceOrderFragment.this);
                                rv_paymenttype.setAdapter(paymentTypeAdapter);

                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }


                    }
                });
    }

    PaymentAdapter paymentAdapter;

    public void Additemtopaymentlist() {

        if (!et_amount.getText().toString().isEmpty() && Double.valueOf(et_amount.getText().toString()) > 0 && !et_amount.getText().toString().equals("")) {

            et_amount.setError(null);

            String paymentlist = SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, "");
            Gson gson = new Gson();
            Type type = new TypeToken<List<PaymentModel>>() {
            }.getType();

            if (!paymentlist.isEmpty()) {
                paymentModelArrayList = gson.fromJson(paymentlist, type);
            } else {
                paymentModelArrayList = new ArrayList<>();
            }

            PaymentModel paymentModel = new PaymentModel();

            paymentModel.setType(str_type);
            paymentModel.setPaytypeid(str_paytypeID);
            paymentModel.setPaytypename(str_name_paytype);
            paymentModel.setPayamt(et_amount.getText().toString());

            paymentModelArrayList.add(paymentModel);

            paymentAdapter = new PaymentAdapter(getContext(), paymentModelArrayList, null, ServiceOrderFragment.this);
            rv_payment.setAdapter(paymentAdapter);


            SessionManagement.savePreferences(getContext(), AppConstant.PAYMENTLIST, gson.toJson(paymentModelArrayList));

            CalculateChanegAndRemainingAmt();

            et_amount.setText("");
        } else {
            et_amount.requestFocus();
            et_amount.setError("Insert Amt");
        }

    }


    double totalPaidAmt = 0.0;
    double totalChangeAmt = 0.0;
    double totalRemainAmt = 0.0;

    public void CalculateChanegAndRemainingAmt() {

        Double temp_FinalTotal = total_taxable_amount_withqty-value;

        String paymenttype = SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, "");
        Type type = new TypeToken<List<PaymentModel>>() {
        }.getType();
        ArrayList<PaymentModel> paymentModelArrayList = new ArrayList<>();

        if (!paymenttype.isEmpty()) {
            paymentModelArrayList = new Gson().fromJson(paymenttype, type);

            if (paymentModelArrayList.size() >= 0) {

                totalPaidAmt = 0.0;

                for (int i = 0; i < paymentModelArrayList.size(); i++) {
                    totalPaidAmt += Double.parseDouble(paymentModelArrayList.get(i).getPayamt());
                }

                int paidamt = (int) Math.round(totalPaidAmt);
                tv_paidamt.setText(String.format("%.2f", Double.parseDouble(String.valueOf(paidamt))));

                if (totalPaidAmt > total_payable_amount_withqty) {
                    totalChangeAmt = totalPaidAmt - temp_FinalTotal;
                    int changeamt = (int) Math.round(totalChangeAmt);
                    tv_change.setText(String.format("%.2f", Double.parseDouble(String.valueOf(changeamt))));
                    tv_remaining.setText("0.00");
                } else {
                    totalRemainAmt = temp_FinalTotal - totalPaidAmt;
                    int remaininng = (int) Math.round(totalRemainAmt);
                    tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(remaininng))));
                    tv_change.setText("0.00");
                }
            }

        } else {
            int remaininng = (int) Math.round(temp_FinalTotal);
            tv_remaining.setText(String.format("%.2f", Double.parseDouble(String.valueOf(remaininng))));
            tv_change.setText("0.00");
        }

    }

    //</editor-fold>


    public void callApiInsertOrder() {
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "insertserviceorderdata");
        params.put("storeid", SessionManagement.getStringValue(getContext(), AppConstant.STORE_ID, ""));
        params.put("memberid", member360ArrayList.get(0).getId());
        params.put("membercontact", member360ArrayList.get(0).getContact());
        params.put("totalpayableamt", "" + (total_taxable_amount_withqty-value));
        params.put("totalpaidamount", "" + totalPaidAmt);
        params.put("totalchangeamount", "" + totalChangeAmt);
//        if(total_item_discount_all>0){
        params.put("totaldiscount", "" + String.valueOf(total_item_discount_all));
    //    params.put("totaldiscount", "" + String.valueOf(8));
        Log.d("asdf", String.valueOf(total_item_discount_all));
//        }else if(value>0){
//            params.put("totaldiscount", "" + String.valueOf(value));
//
//        }

        params.put("ordernote", "");
        params.put("cartitemdata", SessionManagement.getStringValue(getContext(), AppConstant.ITEMCARTARRAY, ""));
        params.put("paymentdata", SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, ""));
        params.put("formevent", formevent);
        params.put("referenceno", edt_rf_number.getText().toString().trim());
        params.put("id", id_update);//pass when edit service order
//        Log.e("params", "callApiInsertOrder: " + params);

        VolleyUtils.makeVolleyRequest(getActivity(), AppConstant.SERVICE_ORDER_URL, params, 2, true, true,
                new VolleyResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Log.e("callApiInsertOrder", "response: " + response);

                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            int status = jsonObject.getInt("status");

//                            Utility.initErrorMessagePopupWindow(getActivity(), message);

                            if (status == 1) {
                                String orderno = jsonObject.getString("orderno");
                                String cmp_logo = jsonObject.getString("cmp_logo");
                                String cmp_address = jsonObject.getString("cmp_address");
                                String cmp_email = jsonObject.getString("cmp_email");
                                String cmp_contact = jsonObject.getString("cmp_contact");
                                String cmp_israngehour = jsonObject.getString("cmp_israngehour");
                                String date = jsonObject.getString("curr_datetime");

                                try {

                                    SetPrintBillValues(orderno, cmp_logo, cmp_address, cmp_email, cmp_contact, cmp_israngehour,date);


                                    str_subcatID = "";
                                    callApiItemList();

                                    lastCheckedPos = -1;
                                    categoryListAdapter.notifyDataSetChanged();

                                    contact_no = member360ArrayList.get(0).getContact();

                                    ll_button.setVisibility(View.GONE);
                                    rl_mycart.setVisibility(View.GONE);
                                    view_customer360.setVisibility(VISIBLE);
                                    tv_customerdata_switch.setBackgroundColor((getResources().getColor(R.color.colorPrimary)));
                                    tv_cart_switch.setBackgroundColor((getResources().getColor(R.color.gray_nav_light)));
                                    callApiGetMember360Data();

                                    cartArrayList.clear();
                                    cartArrayList = new ArrayList<>();

                                    SessionManagement.savePreferences(getContext(), AppConstant.ITEMCARTARRAY, "");
                                    SessionManagement.savePreferences(getContext(), AppConstant.TEMPCART, "");
                                    SessionManagement.savePreferences(getContext(), AppConstant.PAYMENTLIST, "");
                                    getCartArray();
                                    SetCartAdapter();
                                    mainActivity.addFragment(new ServiceOrderListingFragment());


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    paymentdialog.dismiss();
                                    paymentdialog = null;
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


    private void SetPrintBillValues(String orderno, String cmp_logo, String cmp_address, String cmp_email, String cmp_contact, String cmp_israngehour,String date) {
        tv_orderno.setVisibility(GONE);
        ArrayList<MyCart> billItemArrayList = getCartArray();
        Glide.with(getContext()).load(cmp_logo).into(iv_prt_companylogo);
        tv_contact_number.setText("Mobile No : " + cmp_contact + "\n Email : " + cmp_email);

        tv_store_name.setText("Store Name: " + SessionManagement.getStringValue(getContext(), AppConstant.STORE_NAME, ""));
        tv_ord_no.setText("Order No: " + orderno);
        tv_cust_name.setText("Customer Name : " + tv_full_name.getText().toString());
        tv_cust_mobile.setText("Mobile No : " + SessionManagement.getStringValue(getContext(), AppConstant.LAST_ADDED_CUST_CONTACT, ""));
        tv_address.setText(cmp_address);

        tv_date.setText("Date : " + date);
        tv_salepersonname.setText("Sale Person : " + SessionManagement.getStringValue(getContext(), AppConstant.FULLNAME, ""));

        rv_item_bill.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_bill_paytype.setLayoutManager(new LinearLayoutManager(getContext()));

        int totalQty = 0;
        for (int i = 0; i < billItemArrayList.size(); i++) {
            for (int j = 0; j < billItemArrayList.get(i).getSummaryDetails().size(); j++) {
                totalQty += billItemArrayList.get(i).getSummaryDetails().get(j).getQty();
            }
        }

        tv_order_qty.setText("" + totalQty);
        BillItemAdapter billItemAdapter = new BillItemAdapter(getContext(), billItemArrayList);
        rv_item_bill.setAdapter(billItemAdapter);
        tv_bill_total.setText("Qr : " + total_taxable_amount_withqty);
        if(value>0){
        tx_discount.setVisibility(VISIBLE);
        tv_bill_total_disc.setText("Qr : " + value);
        }else if(total_item_discount_all>0){
//            tx_discount.setVisibility(VISIBLE);
            tv_bill_total_disc.setText("Qr : " + total_item_discount_all);

        }

        //<editor-fold desc="PaymentType in bill">
        String paymentlist = SessionManagement.getStringValue(getContext(), AppConstant.PAYMENTLIST, "");
        Gson gson = new Gson();
        Type type1 = new TypeToken<List<PaymentModel>>() {
        }.getType();

        if (!paymentlist.isEmpty()) {
            rv_bill_paytype.setVisibility(VISIBLE);
            paymentModelArrayList = gson.fromJson(paymentlist, type1);

            BillPayTypeAdapter billPayTypeAdapter = new BillPayTypeAdapter(getContext(), paymentModelArrayList);
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
        tv_prt_redeeamt.setText("Qr: " + String.format("%.2f", total_item_discount_all));
        view1.setVisibility(VISIBLE);
        view2.setVisibility(VISIBLE);

        if (totalChangeAmt > 0.0) {
            tv_bill_change.setText("Qr: " + String.format("%.2f", totalChangeAmt));
        } else {
            tv_bill_change.setText("Qr: " + "0.00");
        }

        tv_takeordine.setVisibility(GONE);

//        final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
//        final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight()+600), true);
        if (ISCONNECT) {
//        final Bitmap bitmap1 = BitmapProcess.compressBmpByYourWidth
//                (BitmapFactory.decodeResource(getResources(), R.drawable.test), 300);

            final Bitmap bitmap = loadBitmapFromView(frameLayout_bill);
            final Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), (bitmap.getHeight()+600), true);
            new DownloadImageAsynktask(getContext(), bitmap, bitmap2).execute();
            new DownloadImageAsynktask(getContext(), bitmap, bitmap2).execute();

        } else {
            Utility.OpenPrintingOptionDlg(mainActivity, myBinder);
            Toast.makeText(getContext(), getString(R.string.connect_first), Toast.LENGTH_SHORT).show();
        }
    }
    public static Dialog confirmDialog;
    private void OpenConfirmDialog(String id, String orderid, String message) {
        try {

            if (confirmDialog != null && confirmDialog.isShowing())
                return;

            confirmDialog = new Dialog(getContext());
            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmDialog.setContentView(R.layout.dialog_alert_message);
            TextView dialogTitle = confirmDialog.findViewById(R.id.tv_dlg_clr_cart);
            dialogTitle.setText(message);
            Button btnOk = confirmDialog.findViewById(R.id.btn_goto_cart);
            Button btnno = confirmDialog.findViewById(R.id.btn_clear_cart);
            btnno.setVisibility(GONE);

            try {
                btnOk.setText("ok");
//                btnno.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_EXIT_NO).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }


            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        confirmDialog.dismiss();
                        confirmDialog = null;
//                        if (id.equals("")) {
//                            CallApiCancelOrder(orderid);
//                        } else {
//                            CallApiCancelItem(id, orderid);
//                        }
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
    public static Dialog confirmCartDialog;
    private void OpenCartConfirmDialog(String id, String orderid, String message) {
        try {

            if (confirmCartDialog != null && confirmCartDialog.isShowing())
                return;

            confirmCartDialog = new Dialog(getContext());
            confirmCartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmCartDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmCartDialog.setContentView(R.layout.dialog_alert_message);
            TextView dialogTitle = confirmCartDialog.findViewById(R.id.tv_dlg_clr_cart);
            dialogTitle.setText(message);
            Button btnOk = confirmCartDialog.findViewById(R.id.btn_goto_cart);
            Button btnno = confirmCartDialog.findViewById(R.id.btn_clear_cart);
//            btnno.setVisibility(GONE);

            try {
                btnOk.setText("ok");
//                btnno.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_BTN_EXIT_NO).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }


            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        confirmCartDialog.dismiss();
                        confirmCartDialog = null;
//                        if (id.equals("")) {
//                            CallApiCancelOrder(orderid);
//                        } else {
//                            CallApiCancelItem(id, orderid);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            btnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cartArrayList.clear();
                    SetCartAdapter();
                    confirmCartDialog.dismiss();
                }
            });
            confirmCartDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClicked(int position) {
        Log.d("testing","dialog ");
        OpenConfirmDialog("position","fn","You don't have permission to give much discount ");

    }

    @Override
    public void onItemvalue(double value, int index) {
//        cartArrayList.get(index).getSummaryDetails().get(0).setDiscount(
//                value);
        Log.d("testing","for live  ");
        total_item_discount_all=0;
        total_item_discount=0;
        CalculateCartTotal();

//        SetCartAdapter();

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
                    if (mainActivity.edt_search.getVisibility() == View.VISIBLE) {
                        mainActivity.edt_search.clearFocus();
                        mainActivity.edt_search.setVisibility(View.GONE);
                        mainActivity.iv_search.setImageResource(R.drawable.ic_search);
                        callApiItemList();
                        mainActivity.edt_search.setText("");
                        Utility.hideKeyboardFrom(getContext(), mainActivity.edt_search);
                    } else {
                        mainActivity.addFragment(new ServiceOrderListingFragment());
                    }
                    return true;
                }
                return false;
            }
        });
    }
}