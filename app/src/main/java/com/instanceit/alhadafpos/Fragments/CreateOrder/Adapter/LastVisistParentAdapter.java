package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.LastvisitParent;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class LastVisistParentAdapter extends RecyclerView.Adapter<LastVisistParentAdapter.ViewHolder> {

    Context context;
    ArrayList<LastvisitParent> lastvisitParentArrayList;


    public LastVisistParentAdapter(Context context, ArrayList<LastvisitParent> lastvisitParentArrayList) {
        this.context = context;
        this.lastvisitParentArrayList = lastvisitParentArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lastvisit_parent, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_lastvisit_type.setText(lastvisitParentArrayList.get(position).getCategory());

        if (lastvisitParentArrayList.get(holder.getAdapterPosition()).getIslastvisititem() == 1) {
            LastVisistItemAdapter lastVisistItemAdapter = new LastVisistItemAdapter(context, lastvisitParentArrayList.get(holder.getAdapterPosition()).getLastvisititem());
            holder.rv_lastvisit.setAdapter(lastVisistItemAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return lastvisitParentArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_lastvisit_type;
        RecyclerView rv_lastvisit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_lastvisit_type = itemView.findViewById(R.id.tv_lastvisit_type);
            rv_lastvisit = itemView.findViewById(R.id.rv_lastvisit);

            rv_lastvisit.setLayoutManager(new LinearLayoutManager(context));
        }
    }

}

