package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;


import static android.view.View.GONE;

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
import com.instanceit.alhadafpos.Entity.MembershipListing;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;

import java.util.ArrayList;


public class MembershipListAdapter extends RecyclerView.Adapter<MembershipListAdapter.ViewHolder> {

    Context context;
    ArrayList<MembershipListing> memberOrderArrayList;
    OnclickListener onclickListener;
    String Action;
    int print_right = 0;
    int deleteright = 0;

    //variable
    MainActivity mainActivity;

    public MembershipListAdapter(Context context, ArrayList<MembershipListing> memberOrderArrayList, int deleteright, int print_right, OnclickListener onclickListener) {
        this.context = context;
        this.memberOrderArrayList = memberOrderArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
        this.Action = Action;
        this.print_right = print_right;
        this.deleteright = deleteright;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_membership_order_list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_trs_id.setText(memberOrderArrayList.get(position).getTransactionid());
        holder.tv_order_date.setText(memberOrderArrayList.get(position).getOfulldate());
        holder.tv_member.setText(memberOrderArrayList.get(position).getMembername() + "\n (" + memberOrderArrayList.get(position).getMembercontact() + ")");
        holder.tv_order_no.setText(memberOrderArrayList.get(position).getOrderno());
        holder.tv_status.setText(memberOrderArrayList.get(position).getOrderstatus());
        holder.tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(memberOrderArrayList.get(position).getTotalpaid())));
        holder.tv_entery_by.setText(memberOrderArrayList.get(position).getEntrypersonname() + "\n (" + memberOrderArrayList.get(position).getEntrypersoncontact() + ")");

        if (!memberOrderArrayList.get(position).getOrderstatuscolor().isEmpty()) {
            holder.tv_status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(memberOrderArrayList.get(position).getOrderstatuscolor())));
        }


//        if (memberOrderArrayList.get(position).getOrderstatus().isEmpty()) {
//            holder.tv_status.setVisibility(View.INVISIBLE);
//        } else {
//            holder.tv_status.setVisibility(View.VISIBLE);
//        }

        if (print_right == 1) {
            holder.iv_print_invoice.setVisibility(View.VISIBLE);
//            if (!memberOrderArrayList.get(position).getInvoicepdfurl().isEmpty()) {
//                holder.iv_print_invoice.setVisibility(View.VISIBLE);
//            } else {
//                holder.iv_print_invoice.setVisibility(GONE);
//            }
        } else {
            holder.iv_print_invoice.setVisibility(GONE);
        }


        if (deleteright == 1) {
            if (memberOrderArrayList.get(position).getIscancel().equals("1")) {
                holder.iv_del_order.setVisibility(View.VISIBLE);
            } else {
                holder.iv_del_order.setVisibility(GONE);
            }
        } else {
            holder.iv_del_order.setVisibility(GONE);
        }


        holder.iv_del_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(memberOrderArrayList.get(holder.getAdapterPosition()), 3);
            }
        });

        holder.iv_print_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(memberOrderArrayList.get(holder.getAdapterPosition()), 2);
            }
        });

        holder.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(memberOrderArrayList.get(holder.getAdapterPosition()), 1);
                notifyDataSetChanged();
            }
        });
//
//        if (Action.equals(AppConstant.ACTION_LISTING)) {
//            holder.ln_opration.setVisibility(GONE);
//        } else {
//            if (editright == 0 && deleteright == 0) {
//                holder.ln_opration.setVisibility(GONE);
//            } else {
//                holder.ln_opration.setVisibility(View.VISIBLE);
//            }
//        }

    }


    @Override
    public int getItemCount() {
        return memberOrderArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trs_id, tv_order_no, tv_order_date, tv_amount, tv_member, tv_entery_by, tv_more, tv_status;
        LinearLayout ln_opration;

        ImageView iv_print_invoice, iv_del_order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trs_id = itemView.findViewById(R.id.tv_trs_id);
            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_order_date = itemView.findViewById(R.id.tv_order_date);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_member = itemView.findViewById(R.id.tv_member);
            tv_entery_by = itemView.findViewById(R.id.tv_entery_by);
            tv_more = itemView.findViewById(R.id.tv_more);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_print_invoice = itemView.findViewById(R.id.iv_print_invoice);
            iv_del_order = itemView.findViewById(R.id.iv_del_order);
            ln_opration = itemView.findViewById(R.id.ln_opration);
        }
    }

    public interface OnclickListener {
        void onClick(MembershipListing model, int clickMange);
    }

}

