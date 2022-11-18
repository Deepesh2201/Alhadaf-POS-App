package com.instanceit.alhadafpos.Fragments.InventoryReturn.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Inventory;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class ReturnInventoryListAdapter extends RecyclerView.Adapter<ReturnInventoryListAdapter.ViewHolder> {

    Context context;
    ArrayList<Inventory> orderHistoryListArrayList;
    OnclickListener onclickListener;

    //variable
    MainActivity mainActivity;

    public ReturnInventoryListAdapter(Context context, ArrayList<Inventory> orderHistoryListArrayList, OnclickListener onclickListener) {
        this.context = context;
        this.orderHistoryListArrayList = orderHistoryListArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_return_inventory_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tv_member.setText(orderHistoryListArrayList.get(position).getMembername());
        holder.tv_ret_date.setText(orderHistoryListArrayList.get(position).getOfulldate());
        holder.tv_return_by.setText(orderHistoryListArrayList.get(position).getEntrypersonname() + " (" + orderHistoryListArrayList.get(position).getEntrypersoncontact() + ")");

        holder.btn_collect_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(orderHistoryListArrayList.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return orderHistoryListArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_member, tv_ret_date, tv_return_by, tv_lbl_member, tv_lbl_return_date, tv_lbl_return_by;
        Button btn_collect_items;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_member = itemView.findViewById(R.id.tv_member);
            tv_ret_date = itemView.findViewById(R.id.tv_ret_date);
            tv_return_by = itemView.findViewById(R.id.tv_return_by);
            btn_collect_items = itemView.findViewById(R.id.btn_collect_items);
            tv_lbl_member = itemView.findViewById(R.id.tv_lbl_member);
            tv_lbl_return_date = itemView.findViewById(R.id.tv_lbl_return_date);
            tv_lbl_return_by = itemView.findViewById(R.id.tv_lbl_return_by);

            try {
                tv_lbl_member.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_MEMBER).getLabel() + "  :  ");
                tv_lbl_return_date.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_RETURN_DATE).getLabel() + "  :  ");
                tv_lbl_return_by.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_RETURN_BY).getLabel() + "  :  ");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public interface OnclickListener {
        void onClick(Inventory model);
    }

}

