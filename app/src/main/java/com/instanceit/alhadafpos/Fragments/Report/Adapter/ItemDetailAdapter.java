package com.instanceit.alhadafpos.Fragments.Report.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Itemsubdetail;
import com.instanceit.alhadafpos.Entity.ReportModel;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Entity.itemfulldetail;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<itemfulldetail> reportArrayList;

    //variable
    MainActivity mainActivity;

    public ItemDetailAdapter(Context context, ArrayList<itemfulldetail> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_sub_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_lbl_index.setText("" + (position + 1));
        holder.tv_lbl_item.setText(reportArrayList.get(position).getItemname());
        holder.tv_lbl_qty.setText(reportArrayList.get(position).getQty());
        holder.tv_lbl_used_qty.setText(reportArrayList.get(position).getUsedqty());
        holder.tv_lbl_remain_qty.setText(reportArrayList.get(position).getRemainqty());
        holder.tv_lbl_decount.setText(reportArrayList.get(position).getDiscount());
        holder.tv_lbl_vat_type.setText(reportArrayList.get(position).getTaxtypename());
        holder.tv_lbl_vat.setText(reportArrayList.get(position).getIgst());
        holder.tv_lbl_prince.setText(reportArrayList.get(position).getPrice());
        holder.tv_lbl_duration.setText(reportArrayList.get(position).getDurationname());

    }


    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_lbl_index, tv_lbl_item, tv_lbl_qty, tv_lbl_used_qty, tv_lbl_remain_qty, tv_lbl_decount, tv_lbl_vat_type, tv_lbl_vat, tv_lbl_prince, tv_lbl_duration;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_lbl_index = itemView.findViewById(R.id.tv_lbl_index);
            tv_lbl_item = itemView.findViewById(R.id.tv_lbl_item);
            tv_lbl_qty = itemView.findViewById(R.id.tv_lbl_qty);
            tv_lbl_used_qty = itemView.findViewById(R.id.tv_lbl_used_qty);
            tv_lbl_remain_qty = itemView.findViewById(R.id.tv_lbl_remain_qty);
            tv_lbl_decount = itemView.findViewById(R.id.tv_lbl_decount);
            tv_lbl_vat_type = itemView.findViewById(R.id.tv_lbl_vat_type);
            tv_lbl_vat = itemView.findViewById(R.id.tv_lbl_vat);
            tv_lbl_prince = itemView.findViewById(R.id.tv_lbl_prince);
            tv_lbl_duration = itemView.findViewById(R.id.tv_lbl_duration);
        }
    }

    public interface OnclickListener {
        void onClick(ServiceOrder model, int clickMange);
    }

}

