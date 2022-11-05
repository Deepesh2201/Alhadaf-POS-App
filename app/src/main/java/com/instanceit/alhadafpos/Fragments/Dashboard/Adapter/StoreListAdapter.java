package com.instanceit.alhadafpos.Fragments.Dashboard.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.SessionManagement;

import java.util.ArrayList;


public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> {

    MainActivity mainActivity;
    Context context;
    ArrayList<Model> storeArrayList;
    int ln_width;

    public StoreListAdapter(Context context, ArrayList<Model> storeArrayList, int ln_width) {
        this.context = context;
        this.ln_width = ln_width;
        this.storeArrayList = storeArrayList;
        mainActivity = (MainActivity) context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_company_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_cmp_name.setText(storeArrayList.get(holder.getAdapterPosition()).getName());

        Glide.with(context).asBitmap().load(storeArrayList.get(holder.getAdapterPosition()).getImage()).into(holder.iv_bg_logo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement.savePreferences(context, AppConstant.LAST_ADDED_CUST_CONTACT, "");
                SessionManagement.savePreferences(context, AppConstant.STORE_ID, storeArrayList.get(holder.getAdapterPosition()).getId());
                SessionManagement.savePreferences(context, AppConstant.STORE_NAME, storeArrayList.get(holder.getAdapterPosition()).getName());
                mainActivity.addFragment(new CreateOrderMainFragment());
            }
        });

        holder.rl_main.setLayoutParams(new RelativeLayout.LayoutParams(ln_width / 3, ln_width / 3));

    }


    @Override
    public int getItemCount() {
        return storeArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_main;
        CardView cv_store;
        TextView tv_cmp_name;
        ImageView iv_bg_logo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rl_main = itemView.findViewById(R.id.rl_main);
            cv_store = itemView.findViewById(R.id.cv_store);
            tv_cmp_name = itemView.findViewById(R.id.tv_cmp_name);
            iv_bg_logo = itemView.findViewById(R.id.iv_bg_logo);
        }
    }


}

