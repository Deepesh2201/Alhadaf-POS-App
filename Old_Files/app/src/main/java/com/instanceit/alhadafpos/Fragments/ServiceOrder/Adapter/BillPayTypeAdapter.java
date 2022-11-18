package com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Entity.PaymentModel;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class BillPayTypeAdapter extends RecyclerView.Adapter<BillPayTypeAdapter.ViewHolder> {

    Context context;
    ArrayList<PaymentModel> mdata;

    public BillPayTypeAdapter(Context context, ArrayList<PaymentModel> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adapter_billpaytype ,parent ,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_paytype.setText(mdata.get(position).getPaytypename());
        holder.tv_payamt.setText("Qr: "+String.format("%.2f" , Double.parseDouble(mdata.get(position).getPayamt())));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_paytype , tv_payamt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_paytype = itemView.findViewById(R.id.tv_paytype);
            tv_payamt =itemView.findViewById(R.id.tv_payamt);
        }
    }
}
