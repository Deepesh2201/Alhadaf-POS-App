package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.instanceit.alhadafpos.Entity.PaymentModel;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.SessionManagement;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    Context context;
    ArrayList<PaymentModel> mData;
    CreateOrderMainFragment createOrderMainFragment;
    ServiceOrderFragment serviceOrderFragment;

    public PaymentAdapter(Context context, ArrayList<PaymentModel> mData, CreateOrderMainFragment createOrderMainFragment, ServiceOrderFragment serviceOrderFragment) {
        this.context = context;
        this.mData = mData;
        this.createOrderMainFragment = createOrderMainFragment;
        this.serviceOrderFragment = serviceOrderFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_paymentmethode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tv_paymenttype.setSelected(true);
        holder.tv_amt.setSelected(true);

        holder.tv_paymenttype.setText(mData.get(holder.getAdapterPosition()).getPaytypename());
        holder.tv_amt.setText(mData.get(holder.getAdapterPosition()).getPayamt());

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.remove(holder.getAdapterPosition());
                SessionManagement.savePreferences(context, AppConstant.PAYMENTLIST, new Gson().toJson(mData));
                notifyDataSetChanged();

                if (createOrderMainFragment != null) {
                    createOrderMainFragment.CalculateChanegAndRemainingAmt();
                } else {
                    serviceOrderFragment.CalculateChanegAndRemainingAmt();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_paymenttype, tv_amt;
        ImageView iv_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_paymenttype = itemView.findViewById(R.id.tv_paymenttype);
            tv_amt = itemView.findViewById(R.id.tv_amt);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
