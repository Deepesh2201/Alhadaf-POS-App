package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

public class PaymentTypeAdapter extends RecyclerView.Adapter<PaymentTypeAdapter.ViewHolder> {

    Context context;
    ArrayList<Model> mData;

    //variable
    int lastcheckposition = -1;

    //class
    MainActivity mainActivity;
    CreateOrderMainFragment createOrderMainFragment;
    ServiceOrderFragment serviceOrderFragment;

    public PaymentTypeAdapter(Context context, ArrayList<Model> mData, CreateOrderMainFragment createOrderMainFragment, ServiceOrderFragment serviceOrderFragment) {
        this.context = context;
        this.mData = mData;
        this.createOrderMainFragment = createOrderMainFragment;
        this.serviceOrderFragment = serviceOrderFragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_paymenttype, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        mainActivity = (MainActivity) context;

        holder.tv_paymentname.setSelected(true);
        holder.tv_paymentname.setText(mData.get(holder.getAdapterPosition()).getName());

        Glide.with(context).load(mData.get(holder.getAdapterPosition()).getImage()).into(holder.iv_payment);

        holder.rl_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastcheckposition = holder.getAdapterPosition();

                if (createOrderMainFragment != null) {
                    createOrderMainFragment.str_type = mData.get(holder.getAdapterPosition()).getType();
                    createOrderMainFragment.str_paytypeID = mData.get(holder.getAdapterPosition()).getId();
                    createOrderMainFragment.str_name_paytype = mData.get(holder.getAdapterPosition()).getName();
                    createOrderMainFragment.Additemtopaymentlist();
                } else {
                    serviceOrderFragment.str_type = mData.get(holder.getAdapterPosition()).getType();
                    serviceOrderFragment.str_paytypeID = mData.get(holder.getAdapterPosition()).getId();
                    serviceOrderFragment.str_name_paytype = mData.get(holder.getAdapterPosition()).getName();
                    serviceOrderFragment.Additemtopaymentlist();
                }
                notifyDataSetChanged();

            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_payment;
        TextView tv_paymentname, tv_bankname;
        RelativeLayout rl_payment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_payment = itemView.findViewById(R.id.iv_payment);
            tv_paymentname = itemView.findViewById(R.id.tv_paymentname);
            rl_payment = itemView.findViewById(R.id.rl_payment);
            tv_bankname = itemView.findViewById(R.id.tv_bankname);
        }
    }
}
