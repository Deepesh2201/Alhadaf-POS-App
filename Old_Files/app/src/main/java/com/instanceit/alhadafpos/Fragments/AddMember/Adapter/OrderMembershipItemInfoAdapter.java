package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Orderdetailinfo;
import com.instanceit.alhadafpos.Entity.Storeorderdetailinfo;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class OrderMembershipItemInfoAdapter extends RecyclerView.Adapter<OrderMembershipItemInfoAdapter.ViewHolder> {

    Context context;
    ArrayList<Orderdetailinfo> orderdetailinfoArrayList;
    OnclickListener onclickListener;
    //variable
    MainActivity mainActivity;
    String action_type = "";

    public OrderMembershipItemInfoAdapter(Context context, ArrayList<Orderdetailinfo> orderdetailinfoArrayList, OnclickListener onclickListener) {
        this.context = context;
        this.orderdetailinfoArrayList = orderdetailinfoArrayList;
        mainActivity = (MainActivity) context;
        this.action_type = action_type;
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_membership_order_listing_detail_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_itemname.setText(orderdetailinfoArrayList.get(position).getItemname());
        holder.tv_amount.setText(String.format("%.2f", Double.parseDouble(orderdetailinfoArrayList.get(position).getFinalprice())));
        holder.tv_discount.setText(String.format("%.2f", Double.parseDouble(orderdetailinfoArrayList.get(position).getCouponamount())));
        holder.tv_item_type.setText(orderdetailinfoArrayList.get(position).getTypename());
        holder.tv_validity.setText(orderdetailinfoArrayList.get(position).getStrvalidityduration());
        holder.tv_tax_p.setText("(" + orderdetailinfoArrayList.get(position).getIgst() + "%)");
        holder.tv_vat.setText(String.format("%.2f", Double.parseDouble(orderdetailinfoArrayList.get(position).getIgsttaxamt())));
        holder.tv_taxable.setText(String.format("%.2f", Double.parseDouble(orderdetailinfoArrayList.get(position).getTaxable())));
        holder.tv_price.setText(String.format("%.2f", Double.parseDouble(orderdetailinfoArrayList.get(position).getPrice())));

        holder.tv_item_cat.setText("Your package " + orderdetailinfoArrayList.get(position).getStrexpire());

        if (!orderdetailinfoArrayList.get(position).getStrexpirecolor().isEmpty()) {
            holder.tv_item_cat.setTextColor((Color.parseColor(orderdetailinfoArrayList.get(position).getStrexpirecolor())));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(orderdetailinfoArrayList.get(position), 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderdetailinfoArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemname, tv_item_type, tv_qty, tv_price, tv_discount, tv_taxable, tv_vat, tv_amount, tv_tax_p, tv_item_cat, tv_qty_issue, tv_qty_remain, tv_status, tv_validity;
        LinearLayout ln_operation, ln_vat;
        ImageView iv_del_order;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_itemname = itemView.findViewById(R.id.tv_itemname);
            tv_item_type = itemView.findViewById(R.id.tv_item_type);
            tv_qty = itemView.findViewById(R.id.tv_qty);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            tv_taxable = itemView.findViewById(R.id.tv_taxable);
            tv_vat = itemView.findViewById(R.id.tv_vat);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_tax_p = itemView.findViewById(R.id.tv_tax_p);
            tv_item_cat = itemView.findViewById(R.id.tv_item_cat);
            ln_operation = itemView.findViewById(R.id.ln_operation);
            tv_qty_issue = itemView.findViewById(R.id.tv_qty_issue);
            tv_qty_remain = itemView.findViewById(R.id.tv_qty_remain);
            iv_del_order = itemView.findViewById(R.id.iv_del_order);
            tv_status = itemView.findViewById(R.id.tv_status);
            ln_vat = itemView.findViewById(R.id.ln_vat);
            tv_validity = itemView.findViewById(R.id.tv_validity);
        }
    }

    public interface OnclickListener {
        void onClick(Orderdetailinfo model, int clickMange);
    }

}

