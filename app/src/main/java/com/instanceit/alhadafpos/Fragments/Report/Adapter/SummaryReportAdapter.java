package com.instanceit.alhadafpos.Fragments.Report.Adapter;


import static android.view.View.GONE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.ReportModel;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Entity.SummaryReport;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class SummaryReportAdapter extends RecyclerView.Adapter<SummaryReportAdapter.ViewHolder> {

    Context context;
    ArrayList<SummaryReport> reportArrayList;
    String action;

    //variable
    MainActivity mainActivity;

    public SummaryReportAdapter(Context context, String action, ArrayList<SummaryReport> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
        mainActivity = (MainActivity) context;
        this.action = action;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_summary_report_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_lbl_index.setText("" + (position + 1));
        holder.tv_lbl_item_name.setText(reportArrayList.get(position).getItemname());
        holder.tv_lbl_sale_qty.setText(reportArrayList.get(position).getSaleQty());
        if (action.equals("listitemsalesummaryreportdata")) {
            holder.tv_lbl_return_qty.setText(reportArrayList.get(position).getReturnQty());
            holder.tv_lbl_return_qty.setVisibility(View.VISIBLE);
            holder.view1.setVisibility(View.VISIBLE);
        } else {
            holder.tv_lbl_return_qty.setVisibility(GONE);
            holder.view1.setVisibility(GONE);
        }
        holder.tv_lbl_amount.setText(String.format("%.2f", Double.parseDouble(reportArrayList.get(position).getSaleAmount())));

    }


    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_lbl_index, tv_lbl_item_name, tv_lbl_sale_qty, tv_lbl_return_qty, tv_lbl_amount;
        View view1;

        public ViewHolder(@NonNull View view) {
            super(view);
            tv_lbl_index = view.findViewById(R.id.tv_lbl_index);
            tv_lbl_item_name = view.findViewById(R.id.tv_lbl_item_name);
            tv_lbl_sale_qty = view.findViewById(R.id.tv_lbl_sale_qty);
            tv_lbl_return_qty = view.findViewById(R.id.tv_lbl_return_qty);
            tv_lbl_amount = view.findViewById(R.id.tv_lbl_amount);
            view1 = view.findViewById(R.id.view1);
        }
    }


}

