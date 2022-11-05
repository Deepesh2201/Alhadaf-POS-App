package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Items;
import com.instanceit.alhadafpos.Entity.MyCart;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<MyCart> cartArrayList;
    CreateOrderMainFragment createOrderMainFragment;
    ServiceOrderFragment serviceOrderFragment;
    MainActivity mainActivity;

    public CartAdapter(Context context, ArrayList<MyCart> cartArrayList, CreateOrderMainFragment createOrderMainFragment, ServiceOrderFragment serviceOrderFragment) {
        this.context = context;
        this.cartArrayList = cartArrayList;
        mainActivity = (MainActivity) context;
        this.createOrderMainFragment = createOrderMainFragment;
        this.serviceOrderFragment = serviceOrderFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart_corder_row, parent, false);
        return new ViewHolder(view, new QtyChangeTextChangeListener());
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.qtyChangeTextChangeListener.UpdateExtraOrderData(holder, holder.getAdapterPosition());

        holder.et_qty.setText("" + cartArrayList.get(holder.getAdapterPosition()).getQty());

        PriceCartAdapter priceCartAdapter = new PriceCartAdapter(context, cartArrayList.get(holder.getAdapterPosition()).getSummaryDetails());
        holder.rv_item_price.setAdapter(priceCartAdapter);

        holder.tv_order_name.setText(cartArrayList.get(holder.getAdapterPosition()).getItemname());


        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.et_qty.clearFocus();
                Utility.hideKeyboardFrom(context, view);
                int qty = Integer.parseInt(holder.et_qty.getText().toString().trim());
                qty += 1;
                holder.et_qty.setText(String.valueOf(qty));

                changeQtyDynamic(holder.getAdapterPosition(), qty);
            }
        });


        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.et_qty.clearFocus();
                Utility.hideKeyboardFrom(context, view);
                int qty = Integer.parseInt(holder.et_qty.getText().toString().trim());

                qty -= 1;

                if (qty == 0) {
                    cartArrayList.remove(holder.getAdapterPosition());
                    SessionManagement.savePreferences(context, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));
                    notifyDataSetChanged();
                    serviceOrderFragment.SetCartAdapter();

                    if (createOrderMainFragment != null) {
                        createOrderMainFragment.CalculateCartTotal();
                    } else {
                        serviceOrderFragment.CalculateCartTotal();
                    }
                } else {
                    holder.et_qty.setText(String.valueOf(qty));
                    changeQtyDynamic(holder.getAdapterPosition(), qty);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order_name, tv_price;
        Button btn_minus, btn_plus;
        EditText et_qty;
        RecyclerView rv_item_price;

        QtyChangeTextChangeListener qtyChangeTextChangeListener;

        public ViewHolder(@NonNull View itemView, QtyChangeTextChangeListener qtyChangeTextChangeListener) {
            super(itemView);

            tv_price = itemView.findViewById(R.id.tv_price);
            tv_order_name = itemView.findViewById(R.id.tv_order_name);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            et_qty = itemView.findViewById(R.id.et_qty);
            rv_item_price = itemView.findViewById(R.id.rv_item_price);

            rv_item_price.setLayoutManager(new LinearLayoutManager(context));

            this.qtyChangeTextChangeListener = qtyChangeTextChangeListener;
            et_qty.addTextChangedListener(qtyChangeTextChangeListener);

        }
    }


    public class QtyChangeTextChangeListener implements TextWatcher {

        ViewHolder holder;
        int position;

        public void UpdateExtraOrderData(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
//                if (createOrderMainFragment != null) {
//
//                    if (createOrderMainFragment.rv_cart.getScrollState() == SCROLL_STATE_IDLE) {
//                        try {
//
//                            if (holder.et_qty.hasFocus()) {
//
//                                if (s != null) {
//                                    int qty = 0;
//
//                                    if (String.valueOf(s).equals("") || s.toString().equals("0")) {
//                                        qty = 1;
//                                        holder.et_qty.setText(String.valueOf(qty));
//                                    } else {
//                                        if (s.toString().length() <= 5) {
//                                            qty = Integer.parseInt(String.valueOf(s));
//                                        } else {
//                                            qty = 1;
//                                            holder.et_qty.setText(String.valueOf(qty));
//                                        }
//                                    }
//                                    changeQtyDynamic(position, qty);
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                } else {

                if (serviceOrderFragment.rv_cart.getScrollState() == SCROLL_STATE_IDLE) {
                    try {
                        if (holder.et_qty.hasFocus()) {

                            if (s != null) {
                                int qty = 0;

                                if (String.valueOf(s).equals("") || s.toString().equals("0")) {
                                    qty = 1;
                                    holder.et_qty.setText(String.valueOf(qty));
                                } else {
                                    if (s.toString().length() <= 5) {
                                        qty = Integer.parseInt(String.valueOf(s));
                                    } else {
                                        qty = 1;
                                        holder.et_qty.setText(String.valueOf(qty));
                                    }
                                }
                                changeQtyDynamic(position, qty);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void changeQtyDynamic(int position, int qty) {

        cartArrayList.get(position).setQty(qty);
        cartArrayList.get(position).setTemp_remainqty(qty);
        SessionManagement.savePreferences(mainActivity, AppConstant.ITEMCARTARRAY, new Gson().toJson(cartArrayList));

        Items items = new Items();
        items.setId(cartArrayList.get(position).getItemid());


        if (serviceOrderFragment.itemsArrayList.contains(items)) {
            int index_item = serviceOrderFragment.itemsArrayList.indexOf(items);
            serviceOrderFragment.manageCartScheme(serviceOrderFragment.itemsArrayList.get(index_item), position, true);
        }

    }
}

