package com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter;


import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.instanceit.alhadafpos.Activities.Adapter.FullImageAdapter;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.FullImage;
import com.instanceit.alhadafpos.Entity.RangeRelease;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Entity.Storeinstructiondatum;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.InstructionListAdapter;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.ExtendedViewPager;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class ServiceOrderListAdapter extends RecyclerView.Adapter<ServiceOrderListAdapter.ViewHolder> {

    Context context;
    ArrayList<ServiceOrder> serviceOrderArrayList;
    OnclickListener onclickListener;
    String Action;
    int editright = 0;
    int deleteright = 0;
    int print_right = 0;

    //variable
    MainActivity mainActivity;

    public ServiceOrderListAdapter(Context context, String Action, ArrayList<ServiceOrder> serviceOrderArrayList, int editright, int deleteright, int print_right, OnclickListener onclickListener) {
        this.context = context;
        this.serviceOrderArrayList = serviceOrderArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
        this.Action = Action;
        this.editright = editright;
        this.deleteright = deleteright;
        this.print_right = print_right;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_service_order_list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_trs_id.setText(serviceOrderArrayList.get(position).getTransactionid());
        holder.tv_order_date.setText(serviceOrderArrayList.get(position).getOfulldate());
        holder.tv_member.setText(serviceOrderArrayList.get(position).getMembername() + "\n (" + serviceOrderArrayList.get(position).getMembercontact() + ")");
        holder.tv_order_no.setText(serviceOrderArrayList.get(position).getOrderno());
        holder.tv_status.setText(serviceOrderArrayList.get(position).getOrderstatus());
        holder.tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(serviceOrderArrayList.get(position).getTotalpaid())));
        holder.tv_entery_by.setText(serviceOrderArrayList.get(position).getEntrypersonname() + "\n (" + serviceOrderArrayList.get(position).getEntrypersoncontact() + ")");

        if (!serviceOrderArrayList.get(position).getOrderstatuscolor().isEmpty()) {
            holder.tv_status.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(serviceOrderArrayList.get(position).getOrderstatuscolor())));
        }

        if (serviceOrderArrayList.get(position).getOrderstatus().isEmpty()) {
            holder.tv_status.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_status.setVisibility(View.VISIBLE);
        }

        if (editright == 1) {
            if (serviceOrderArrayList.get(position).getShoweditbutton().equals("1")) {
                holder.iv_edit_order.setVisibility(View.VISIBLE);
            } else {
                holder.iv_edit_order.setVisibility(GONE);
            }
        } else {
            holder.iv_edit_order.setVisibility(GONE);
        }


        if (deleteright == 1) {
            if (serviceOrderArrayList.get(position).getShowcancelbutton().equals("1")) {
                holder.iv_del_order.setVisibility(View.VISIBLE);
            } else {
                holder.iv_del_order.setVisibility(GONE);
            }
        } else {
            holder.iv_del_order.setVisibility(GONE);
        }

        if (print_right == 1) {
            holder.iv_print_invoice.setVisibility(View.VISIBLE);
//            if (serviceOrderArrayList.get(position).getInvoicepdfurl().isEmpty()) {
//                holder.iv_print_invoice.setVisibility(GONE);
//            } else {
//                holder.iv_print_invoice.setVisibility(View.VISIBLE);
//            }
        } else {
            holder.iv_print_invoice.setVisibility(GONE);
        }


        holder.iv_del_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(serviceOrderArrayList.get(holder.getAdapterPosition()), 3);
            }
        });

        holder.iv_print_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(serviceOrderArrayList.get(holder.getAdapterPosition()), 4);
            }
        });

        holder.iv_edit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(serviceOrderArrayList.get(holder.getAdapterPosition()), 2);
            }
        });

        holder.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(serviceOrderArrayList.get(holder.getAdapterPosition()), 1);
                notifyDataSetChanged();
            }
        });

        if (Action.equals(AppConstant.ACTION_LISTING)) {
            if (print_right == 0) {
                holder.ln_opration.setVisibility(GONE);
                holder.iv_edit_order.setVisibility(GONE);
                holder.iv_del_order.setVisibility(GONE);
            } else {
                holder.ln_opration.setVisibility(View.VISIBLE);
                holder.iv_edit_order.setVisibility(GONE);
                holder.iv_del_order.setVisibility(GONE);
            }
        } else {
            if (editright == 0 && deleteright == 0 && print_right == 0) {
                holder.ln_opration.setVisibility(GONE);
            } else {
                holder.ln_opration.setVisibility(View.VISIBLE);
            }
        }

    }


    @Override
    public int getItemCount() {
        return serviceOrderArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trs_id, tv_order_no, tv_order_date, tv_amount, tv_member, tv_entery_by, tv_more, tv_status;
        LinearLayout ln_opration;

        ImageView iv_edit_order, iv_del_order, iv_print_invoice;

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
            iv_edit_order = itemView.findViewById(R.id.iv_edit_order);
            iv_del_order = itemView.findViewById(R.id.iv_del_order);
            ln_opration = itemView.findViewById(R.id.ln_opration);
            iv_print_invoice = itemView.findViewById(R.id.iv_print_invoice);
        }
    }

    public interface OnclickListener {
        void onClick(ServiceOrder model, int clickMange);
    }

}

