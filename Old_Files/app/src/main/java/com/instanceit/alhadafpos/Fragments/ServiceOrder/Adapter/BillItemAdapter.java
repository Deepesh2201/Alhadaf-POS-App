package com.instanceit.alhadafpos.Fragments.ServiceOrder.Adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Entity.MyCart;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {

    Context context;
    ArrayList<MyCart> cartArrayList;


    public BillItemAdapter(Context context, ArrayList<MyCart> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.adapter_bill_posiflex_reprint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_sr.setText(String.valueOf(position+1));
        holder.tv_discount.setVisibility(GONE);

        if (position < (cartArrayList.size() - 1) && cartArrayList.get(position).getItemid().equals(cartArrayList.get(position + 1).getItemid())) {
            holder.view_bottom.setVisibility(GONE);
        }

        holder.tv_schemename.setVisibility(GONE);
        holder.tv_prt_itemname.setVisibility(VISIBLE);
        holder.tv_hsn_no.setVisibility(VISIBLE);

        holder.tv_prt_itemname.setText(cartArrayList.get(position).getItemname());
        holder.tv_hsn_no.setVisibility(GONE);
        holder.tv_prt_Qty.setText(String.valueOf(cartArrayList.get(position).getQty()));
        double price = 0.0;
        int qty = 0;
        for (int i = 0; i < cartArrayList.get(position).getSummaryDetails().size(); i++) {
            price = cartArrayList.get(position).getSummaryDetails().get(i).getPrice();
            qty += cartArrayList.get(position).getSummaryDetails().get(i).getQty();
        }
        holder.tv_prt_price.setText(String.format("%.2f", price));
        holder.tv_prt_total_Amt.setText(String.format("%.2f", price * qty));
        holder.tv_prt_Qty.setText(""+qty);

//        if (cartArrayList.get(position).getCgst() > 0) {
//            holder.tv_cgst.setVisibility(View.VISIBLE);
//            holder.tv_cgst.setText("Taxable :" + context.getResources().getString(R.string.Rs) + String.format("%.2f", Double.valueOf(cartArrayList.get(position).getTaxableamt()))
//                    + "  CGST " + cartArrayList.get(position).getCgst() + "%    " + String.format("%.2f", Double.valueOf(cartArrayList.get(position).getCtaxamt())));
//        } else {
//            holder.tv_cgst.setVisibility(View.GONE);
//        }

        holder.tv_cgst.setVisibility(View.GONE);
//        if (cartArrayList.get(position).getSgst() > 0) {
//            holder.tv_sgst.setVisibility(View.VISIBLE);
//            holder.tv_sgst.setText("SGST " + cartArrayList.get(position).getSgst() + "%    " + "   " + String.format("%.2f", Double.valueOf(cartArrayList.get(position).getStaxamt())));
//        } else {
//            holder.tv_sgst.setVisibility(View.GONE);
//        }

        holder.tv_sgst.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_prt_itemname, tv_prt_price, tv_prt_Qty, tv_prt_total_Amt, tv_sgst, tv_cgst, tv_sr, tv_schemename, tv_discount, tv_schemename_temp, tv_hsn_no;
        View view_bottom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_prt_itemname = itemView.findViewById(R.id.tv_prt_itemname);
            tv_prt_price = itemView.findViewById(R.id.tv_prt_price);
            tv_prt_Qty = itemView.findViewById(R.id.tv_prt_Qty);
            tv_prt_total_Amt = itemView.findViewById(R.id.tv_prt_total_Amt);
            tv_sgst = itemView.findViewById(R.id.tv_sgst);
            tv_cgst = itemView.findViewById(R.id.tv_cgst);
            tv_sr = itemView.findViewById(R.id.tv_sr);
            tv_schemename = itemView.findViewById(R.id.tv_schemename);
            tv_discount = itemView.findViewById(R.id.tv_discount);
            view_bottom = itemView.findViewById(R.id.view_bottom);
            tv_schemename_temp = itemView.findViewById(R.id.tv_schemename_temp);
            tv_hsn_no = itemView.findViewById(R.id.tv_hsn_no);


        }
    }
}
