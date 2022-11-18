package com.instanceit.alhadafpos.Fragments.OrderHistory.Adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.OrderHistoryList;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class OrderHistoryListAdapter extends RecyclerView.Adapter<OrderHistoryListAdapter.ViewHolder> {

    Context context;
    ArrayList<OrderHistoryList> orderHistoryListArrayList;
    OnclickListener onclickListener;

    //variable
    MainActivity mainActivity;

    public OrderHistoryListAdapter(Context context, ArrayList<OrderHistoryList> orderHistoryListArrayList, OnclickListener onclickListener) {
        this.context = context;
        this.orderHistoryListArrayList = orderHistoryListArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order_history_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tv_trs_id.setText(orderHistoryListArrayList.get(position).getTransactionid());
        holder.tv_order_date.setText(orderHistoryListArrayList.get(position).getOrderdate());
        holder.tv_member.setText(orderHistoryListArrayList.get(position).getMembername());
        holder.tv_order_no.setText(orderHistoryListArrayList.get(position).getOrderno());
        holder.tv_index.setText(String.valueOf(position + 1));
        holder.tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(orderHistoryListArrayList.get(position).getTotalpaid())));
        holder.tv_entery_by.setText(orderHistoryListArrayList.get(position).getEntrypersonname());

        holder.tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(orderHistoryListArrayList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderHistoryListArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trs_id, tv_order_no, tv_order_date, tv_amount, tv_member, tv_entery_by, tv_more, tv_index;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_trs_id = itemView.findViewById(R.id.tv_trs_id);
            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_order_date = itemView.findViewById(R.id.tv_order_date);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_member = itemView.findViewById(R.id.tv_member);
            tv_entery_by = itemView.findViewById(R.id.tv_entery_by);
            tv_more = itemView.findViewById(R.id.tv_more);
            tv_index = itemView.findViewById(R.id.tv_index);
        }
    }

    public interface OnclickListener {
        void onClick(OrderHistoryList model);
    }

}

