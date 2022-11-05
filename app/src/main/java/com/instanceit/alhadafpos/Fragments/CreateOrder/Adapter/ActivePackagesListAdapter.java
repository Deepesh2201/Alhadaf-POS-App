package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Mshipdetail;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class ActivePackagesListAdapter extends RecyclerView.Adapter<ActivePackagesListAdapter.ViewHolder> {

    Context context;
    MainActivity mainActivity;
    ArrayList<Mshipdetail> mshipdetailArrayList;

    public ActivePackagesListAdapter(Context context, ArrayList<Mshipdetail> mshipdetailArrayList) {
        this.context = context;
        mainActivity = (MainActivity) context;
        this.mshipdetailArrayList = mshipdetailArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_membership_list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tv_membership_name.setText(mshipdetailArrayList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mshipdetailArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_membership_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_membership_name = itemView.findViewById(R.id.tv_membership_name);
        }
    }


}

