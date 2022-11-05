package com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter;


import android.content.Context;
import android.content.res.ColorStateList;
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
import com.instanceit.alhadafpos.Entity.OrderHistoryList;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Entity.Storeorderdetailinfo;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter.ServiceOrderListAdapter;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class OrderHistoryItemDetailAdapter extends RecyclerView.Adapter<OrderHistoryItemDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<Storeorderdetailinfo> storeorderdetailinfoArrayList;
    OnclickListener onclickListener;
    //variable
    MainActivity mainActivity;
    String action_type = "";
    int isEditable = 0, deleteright = 0;

    public OrderHistoryItemDetailAdapter(Context context, ArrayList<Storeorderdetailinfo> storeorderdetailinfoArrayList, String action_type, int isEditable, int deleteright, OnclickListener onclickListener) {
        this.context = context;
        this.storeorderdetailinfoArrayList = storeorderdetailinfoArrayList;
        mainActivity = (MainActivity) context;
        this.action_type = action_type;
        this.onclickListener = onclickListener;
        this.isEditable = isEditable;
        this.deleteright = deleteright;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_new_order_item_detail_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (action_type.equals("ServiceOrder")) {
            holder.ln_operation.setVisibility(View.VISIBLE);
            holder.tv_qty_issue.setVisibility(View.VISIBLE);
            holder.tv_qty_remain.setVisibility(View.VISIBLE);
            holder.tv_qty_remain.setText(storeorderdetailinfoArrayList.get(position).getRemainqty());
            holder.tv_qty_issue.setText(storeorderdetailinfoArrayList.get(position).getIssuedqty());
        } else {
            holder.ln_operation.setVisibility(View.GONE);
            holder.tv_qty_issue.setVisibility(View.GONE);
            holder.tv_qty_remain.setVisibility(View.GONE);
            holder.tv_price.setVisibility(View.GONE);
            holder.tv_discount.setVisibility(View.GONE);
            holder.tv_vat.setVisibility(View.GONE);
            holder.tv_taxable.setVisibility(View.GONE);
            holder.tv_tax_p.setVisibility(View.GONE);
            holder.ln_vat.setVisibility(View.GONE);
            holder.tv_amount.setVisibility(View.GONE);
        }

        holder.tv_item_cat.setText("(" + storeorderdetailinfoArrayList.get(position).getCategory() + ")");
        holder.tv_itemname.setText(storeorderdetailinfoArrayList.get(position).getItemname());
        holder.tv_amount.setText(String.format("%.2f", Double.parseDouble(storeorderdetailinfoArrayList.get(position).getFinalprice())));
        holder.tv_discount.setText(String.format("%.2f", Double.parseDouble(storeorderdetailinfoArrayList.get(position).getDiscountamt())));
        holder.tv_item_type.setText(storeorderdetailinfoArrayList.get(position).getTypename());
        holder.tv_qty.setText(storeorderdetailinfoArrayList.get(position).getQty());
        holder.tv_tax_p.setText("(" + storeorderdetailinfoArrayList.get(position).getIgst() + "%)");
        holder.tv_vat.setText(String.format("%.2f", Double.parseDouble(storeorderdetailinfoArrayList.get(position).getIgsttaxamt())));
        holder.tv_taxable.setText(String.format("%.2f", Double.parseDouble(storeorderdetailinfoArrayList.get(position).getTaxable())));
        holder.tv_price.setText(String.format("%.2f", Double.parseDouble(storeorderdetailinfoArrayList.get(position).getPrice())));

        holder.iv_del_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(storeorderdetailinfoArrayList.get(holder.getAdapterPosition()), 3);
            }
        });

        if (isEditable == 1) {
            if (deleteright == 1) {
                if (storeorderdetailinfoArrayList.get(holder.getAdapterPosition()).getShowcancelbutton() != null) {
                    if (storeorderdetailinfoArrayList.get(holder.getAdapterPosition()).getShowcancelbutton().equals("1")) {
                        holder.iv_del_order.setVisibility(View.VISIBLE);
                        holder.tv_status.setVisibility(View.GONE);
                    } else {
                        holder.iv_del_order.setVisibility(View.GONE);
                        if (!storeorderdetailinfoArrayList.get(position).getSoitemstatus().isEmpty()) {
                            holder.tv_status.setVisibility(View.VISIBLE);
                        }
                        holder.tv_status.setText(storeorderdetailinfoArrayList.get(position).getSoitemstatus());
                        if (!storeorderdetailinfoArrayList.get(position).getSoitemstatuscolor().isEmpty()) {
                            holder.tv_status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(storeorderdetailinfoArrayList.get(position).getSoitemstatuscolor())));
                        }
                    }
                }
            } else {
                holder.iv_del_order.setVisibility(View.GONE);
                if (!storeorderdetailinfoArrayList.get(position).getSoitemstatus().isEmpty()) {
                    holder.tv_status.setVisibility(View.VISIBLE);
                }
                holder.tv_status.setText(storeorderdetailinfoArrayList.get(position).getSoitemstatus());
                if (!storeorderdetailinfoArrayList.get(position).getSoitemstatuscolor().isEmpty()) {
                    holder.tv_status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(storeorderdetailinfoArrayList.get(position).getSoitemstatuscolor())));
                }
            }
        } else {
            holder.iv_del_order.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return storeorderdetailinfoArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_itemname, tv_item_type, tv_qty, tv_price, tv_discount, tv_taxable, tv_vat, tv_amount, tv_tax_p, tv_item_cat, tv_qty_issue, tv_qty_remain, tv_status;
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
        }
    }

    public interface OnclickListener {
        void onClick(Storeorderdetailinfo model, int clickMange);
    }

}

