package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.Entity.Orderdetailinfo;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    Context context;
    ArrayList<Orderdetailinfo> cartArrayList;

    public BillAdapter(Context context, ArrayList<Orderdetailinfo> cartArrayList) {
        this.context = context;
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.adapter_bill_posiflex, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_sr.setText(String.valueOf(position + 1));
        holder.tv_discount.setVisibility(GONE);

        if (position < (cartArrayList.size() - 1) && cartArrayList.get(position).getId().equals(cartArrayList.get(position + 1).getId())) {
            holder.view_bottom.setVisibility(GONE);
        }

        holder.tv_schemename.setVisibility(GONE);
        holder.tv_prt_itemname.setVisibility(VISIBLE);
        holder.tv_hsn_no.setVisibility(VISIBLE);

        holder.tv_prt_itemname.setText(cartArrayList.get(position).getItemname());
        holder.tv_hsn_no.setVisibility(GONE);
//        holder.tv_prt_Qty.setText("1");

        holder.tv_prt_price.setText(String.format("%.2f", Double.parseDouble(cartArrayList.get(position).getTaxable())));
        holder.tv_prt_total_Amt.setText(String.valueOf(cartArrayList.get(position).getFinalprice()));
        holder.tv_prt_Qty.setText(String.format("%.2f", Double.parseDouble(cartArrayList.get(position).getIgsttaxamt())));

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
