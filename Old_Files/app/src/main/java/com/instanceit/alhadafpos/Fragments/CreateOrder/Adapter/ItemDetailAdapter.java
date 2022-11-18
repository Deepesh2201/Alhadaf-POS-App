package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Entity.Items;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<Items> itemsDetailArrayList;


    public ItemDetailAdapter(Context context, ArrayList<Items> itemsDetailArrayList) {
        this.context = context;
        this.itemsDetailArrayList = itemsDetailArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_detail, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_item_name.setText(itemsDetailArrayList.get(position).getName());

        int total_qty_free = 0, tot_qty_rem_free = 0, total_qty_dis = 0, tot_qty_rem_dis = 0, tot_qty_fix = 0, tot_qty_rem_fix = 0;
        String typename = "";
        double price = 0;
        String type = "";

        for (int i = 0; i < itemsDetailArrayList.get(position).getItemsubdetail().size(); i++) {

            type = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getType();

            if (type.equals("1")) {
                total_qty_free = total_qty_free + Integer.parseInt(itemsDetailArrayList.get(position).getItemsubdetail().get(i).getBaseqty());
                tot_qty_rem_free = tot_qty_rem_free + itemsDetailArrayList.get(position).getItemsubdetail().get(i).getRemainqty();
                price = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getPrice();
                typename = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getTypename();
                holder.tv_total_qty.setText("" + total_qty_free);
                holder.tv_remaing_qty.setText("" + tot_qty_rem_free);
                holder.tv_price.setText("Qr." + String.format("%.2f", price));
                holder.tv_type.setText("(" + typename + ")");
            } else if (type.equals("2")) {
                total_qty_dis = total_qty_dis + Integer.parseInt(itemsDetailArrayList.get(position).getItemsubdetail().get(i).getBaseqty());
                tot_qty_rem_dis = tot_qty_rem_dis + itemsDetailArrayList.get(position).getItemsubdetail().get(i).getRemainqty();
                price = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getPrice();
                typename = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getTypename();
                holder.tv_total_qty.setText("" + total_qty_dis);
                holder.tv_remaing_qty.setText("" + tot_qty_rem_dis);
                holder.tv_price.setText("Qr." + String.format("%.2f", price));
                holder.tv_type.setText("(" + typename + ")");
            } else {
                tot_qty_fix = tot_qty_fix + Integer.parseInt(itemsDetailArrayList.get(position).getItemsubdetail().get(i).getBaseqty());
                tot_qty_rem_fix = tot_qty_rem_fix + itemsDetailArrayList.get(position).getItemsubdetail().get(i).getRemainqty();
                price = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getPrice();
                typename = itemsDetailArrayList.get(position).getItemsubdetail().get(i).getTypename();
                holder.tv_total_qty.setText("" + tot_qty_fix);
                holder.tv_remaing_qty.setText("" + tot_qty_rem_fix);
                holder.tv_price.setText("Qr." + String.format("%.2f", price));
                holder.tv_type.setText("(" + typename + ")");
            }

        }

    }


    @Override
    public int getItemCount() {
        return itemsDetailArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_type, tv_total_qty, tv_remaing_qty, tv_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_total_qty = itemView.findViewById(R.id.tv_total_qty);
            tv_remaing_qty = itemView.findViewById(R.id.tv_remaing_qty);
            tv_price = itemView.findViewById(R.id.tv_price);

        }
    }


}

