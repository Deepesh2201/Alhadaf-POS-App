package com.instanceit.alhadafpos.Fragments.Report.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Itemwebsitedetail;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.Entity.itemfulldetail;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class WebDisplayDetailAdapter extends RecyclerView.Adapter<WebDisplayDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<Itemwebsitedetail> reportArrayList;

    //variable
    MainActivity mainActivity;

    public WebDisplayDetailAdapter(Context context, ArrayList<Itemwebsitedetail> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_course_detail_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_lbl_index.setText("" + (position + 1));
        holder.tv_lbl_attribute.setText(reportArrayList.get(position).getAttributename());
        holder.tv_lbl_web.setText(reportArrayList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_lbl_index, tv_lbl_attribute, tv_lbl_web;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_lbl_index = itemView.findViewById(R.id.tv_lbl_index);
            tv_lbl_attribute = itemView.findViewById(R.id.tv_lbl_attribute);
            tv_lbl_web = itemView.findViewById(R.id.tv_lbl_web);
        }
    }

    public interface OnclickListener {
        void onClick(ServiceOrder model, int clickMange);
    }

}

