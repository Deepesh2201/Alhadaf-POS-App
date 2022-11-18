package com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Storeorderdetailinfo;
import com.instanceit.alhadafpos.Entity.Storeorderpaymentdetailinfo;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class OrderHistoryPaymentDetailAdapter extends RecyclerView.Adapter<OrderHistoryPaymentDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<Storeorderpaymentdetailinfo> paymentdetailArrayList;

    //variable
    MainActivity mainActivity;

    public OrderHistoryPaymentDetailAdapter(Context context, ArrayList<Storeorderpaymentdetailinfo> paymentdetailArrayList) {
        this.context = context;
        this.paymentdetailArrayList = paymentdetailArrayList;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order_payment_detail_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_amount.setText(String.format("%.2f", Double.parseDouble(paymentdetailArrayList.get(position).getAmount())));
        holder.tv_payment_type.setText(paymentdetailArrayList.get(position).getPaytypename());
    }


    @Override
    public int getItemCount() {
        return paymentdetailArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_payment_type, tv_amount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_payment_type = itemView.findViewById(R.id.tv_payment_type);
            tv_amount = itemView.findViewById(R.id.tv_amount);
        }
    }


}

