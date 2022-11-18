package com.instanceit.alhadafpos.Fragments.IssueServiceOrder.Adapter;


import static android.view.View.GONE;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.IssueServiceOrder;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class IssueServiceOrderListAdapter extends RecyclerView.Adapter<IssueServiceOrderListAdapter.ViewHolder> {

    Context context;
    ArrayList<IssueServiceOrder> issueServiceOrderArrayList;
    OnclickListener onclickListener;

    //variable
    MainActivity mainActivity;

    public IssueServiceOrderListAdapter(Context context, ArrayList<IssueServiceOrder> issueServiceOrderArrayList, OnclickListener onclickListener) {
        this.context = context;
        this.issueServiceOrderArrayList = issueServiceOrderArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_issue_service_order_list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_order_no.setText(issueServiceOrderArrayList.get(position).getOrderno());
        holder.tv_date.setText(issueServiceOrderArrayList.get(position).getOrderdate());
        holder.tv_customer_name.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBER_NAME).getLabel() + " : " + issueServiceOrderArrayList.get(position).getMembername());
        holder.tv_contact_no.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBER_CONTACT_NO).getLabel() + " : " + issueServiceOrderArrayList.get(position).getMembercontact());


        holder.btn_view_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(issueServiceOrderArrayList.get(holder.getAdapterPosition()), 1);
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return issueServiceOrderArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_no, tv_date, tv_customer_name, tv_contact_no;
        Button btn_view_more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_customer_name = itemView.findViewById(R.id.tv_customer_name);
            tv_contact_no = itemView.findViewById(R.id.tv_contact_no);
            btn_view_more = itemView.findViewById(R.id.btn_view_more);

            btn_view_more.setText(Utility.languageLabel(mainActivity,LabelMaster.LBL_CUSTOMER_VIEW_MORE_TITLE).getLabel());

        }
    }

    public interface OnclickListener {
        void onClick(IssueServiceOrder model, int clickMange);
    }

}

