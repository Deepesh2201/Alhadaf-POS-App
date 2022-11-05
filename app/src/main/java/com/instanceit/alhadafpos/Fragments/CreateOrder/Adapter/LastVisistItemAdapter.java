package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.LastvisitParent;
import com.instanceit.alhadafpos.Entity.Lastvisititem;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class LastVisistItemAdapter extends RecyclerView.Adapter<LastVisistItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Lastvisititem> lastvisititemArrayList;
    MainActivity mainActivity;


    public LastVisistItemAdapter(Context context, ArrayList<Lastvisititem> lastvisititemArrayList) {
        this.context = context;
        this.lastvisititemArrayList = lastvisititemArrayList;
        mainActivity=(MainActivity) context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lastvisit_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_item_name.setText(lastvisititemArrayList.get(position).getName());
        holder.tv_item_qty.setText(lastvisititemArrayList.get(position).getQty());

        if (!lastvisititemArrayList.get(position).getReturnstatus().trim().equals("")) {
            holder.tv_status.setText(lastvisititemArrayList.get(position).getReturnstatus());
            if (!lastvisititemArrayList.get(position).getReturnstatuscolor().trim().equals("")) {
                holder.tv_status.setTextColor((Color.parseColor(lastvisititemArrayList.get(position).getReturnstatuscolor())));
            }
        } else {
            holder.tv_status.setText("");
        }
    }


    @Override
    public int getItemCount() {
        return lastvisititemArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name, tv_item_qty, tv_status,tv_lbl_qty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_qty = itemView.findViewById(R.id.tv_item_qty);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_lbl_qty = itemView.findViewById(R.id.tv_lbl_qty);

            tv_lbl_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_QTY).getLabel()+"  :  ");
        }
    }

}

