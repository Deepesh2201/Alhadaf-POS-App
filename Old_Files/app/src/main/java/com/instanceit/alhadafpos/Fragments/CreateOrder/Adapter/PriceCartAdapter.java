package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.instanceit.alhadafpos.Entity.MyCart;
import com.instanceit.alhadafpos.Entity.SummaryDetail;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class PriceCartAdapter extends RecyclerView.Adapter<PriceCartAdapter.ViewHolder> {

    Context context;
    ArrayList<SummaryDetail> summaryDetailArrayList;

    public PriceCartAdapter(Context context, ArrayList<SummaryDetail> summaryDetailArrayList) {
        this.context = context;
        this.summaryDetailArrayList = summaryDetailArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_price_cart_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_price.setText("Qr " + String.format("%.2f", (summaryDetailArrayList.get(holder.getAdapterPosition()).getPrice() - summaryDetailArrayList.get(holder.getAdapterPosition()).getDiscountamt()))
                + "   |    Qty : " + summaryDetailArrayList.get(holder.getAdapterPosition()).getQty());
//
//        holder.tv_price.setText("Qr " + summaryDetailArrayList.get(holder.getAdapterPosition()).getPrice()
//                + "   |    Qty : " + summaryDetailArrayList.get(holder.getAdapterPosition()).getQty());
    }


    @Override
    public int getItemCount() {
        return summaryDetailArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_price = itemView.findViewById(R.id.tv_price);

        }
    }


}

