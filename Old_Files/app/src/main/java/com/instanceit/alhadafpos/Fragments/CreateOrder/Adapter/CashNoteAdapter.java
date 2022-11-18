package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

public class CashNoteAdapter extends RecyclerView.Adapter<CashNoteAdapter.ViewHolder> {

    Context context;
    ArrayList<Model> mData;
    EditText et_amount;
    CreateOrderMainFragment createOrderMainFragment;
    ServiceOrderFragment serviceOrderFragment;


    public CashNoteAdapter(Context context, ArrayList<Model> mData, EditText et_amount, CreateOrderMainFragment createOrderMainFragment, ServiceOrderFragment serviceOrderFragment) {
        this.context = context;
        this.mData = mData;
        this.et_amount = et_amount;
        this.createOrderMainFragment = createOrderMainFragment;
        this.serviceOrderFragment = serviceOrderFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_cashnote, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels / 3;
        holder.btn_cashnote.getLayoutParams().width = width;

        holder.btn_cashnote.setText(mData.get(holder.getAdapterPosition()).getName());

        holder.btn_cashnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                et_amount.setText(mData.get(holder.getAdapterPosition()).getName());
                if (createOrderMainFragment != null) {
                    createOrderMainFragment.str_type = mData.get(holder.getAdapterPosition()).getType();
                    createOrderMainFragment.str_paytypeID = mData.get(holder.getAdapterPosition()).getId();
                    createOrderMainFragment.str_name_paytype = createOrderMainFragment.paymentmodelArrayList.get(0).getName();
                    createOrderMainFragment.Additemtopaymentlist();
                } else {
                    serviceOrderFragment.str_type = mData.get(holder.getAdapterPosition()).getType();
                    serviceOrderFragment.str_paytypeID = mData.get(holder.getAdapterPosition()).getId();
                    serviceOrderFragment.str_name_paytype = serviceOrderFragment.paymentmodelArrayList.get(0).getName();
                    serviceOrderFragment.Additemtopaymentlist();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn_cashnote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_cashnote = itemView.findViewById(R.id.btn_cashnote);
        }
    }
}
