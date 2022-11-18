package com.instanceit.alhadafpos.Fragments.Report.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Coursebenefitdetail;
import com.instanceit.alhadafpos.Entity.Itemwebsitedetail;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class CourseBenefitAdapter extends RecyclerView.Adapter<CourseBenefitAdapter.ViewHolder> {

    Context context;
    ArrayList<Coursebenefitdetail> reportArrayList;

    //variable
    MainActivity mainActivity;

    public CourseBenefitAdapter(Context context, ArrayList<Coursebenefitdetail> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_web_display_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_lbl_index.setText("" + (position + 1));
        holder.tv_lbl_benefits.setText(reportArrayList.get(position).getName());
        holder.tv_lbl_duration.setText(reportArrayList.get(position).getDurationname());
    }


    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_lbl_index, tv_lbl_benefits, tv_lbl_duration;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_lbl_index = itemView.findViewById(R.id.tv_lbl_index);
            tv_lbl_benefits = itemView.findViewById(R.id.tv_lbl_benefits);
            tv_lbl_duration = itemView.findViewById(R.id.tv_lbl_duration);
        }
    }

    public interface OnclickListener {
        void onClick(ServiceOrder model, int clickMange);
    }

}

