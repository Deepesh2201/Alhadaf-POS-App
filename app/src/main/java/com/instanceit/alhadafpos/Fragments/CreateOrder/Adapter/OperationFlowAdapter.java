package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Lastvisititem;
import com.instanceit.alhadafpos.Entity.OperationFlow;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class OperationFlowAdapter extends RecyclerView.Adapter<OperationFlowAdapter.ViewHolder> {

    MainActivity mainActivity;
    Context context;
    ArrayList<OperationFlow> operationFlowArrayList;
    OnclickInterface onclickInterface;


    public OperationFlowAdapter(Context context, ArrayList<OperationFlow> operationFlowArrayList,OnclickInterface onclickInterface) {
        mainActivity = (MainActivity) context;
        this.context = context;
        this.operationFlowArrayList = operationFlowArrayList;
        this.onclickInterface=onclickInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_flow_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_operation_name.setText(operationFlowArrayList.get(position).getOperationname());
        holder.tv_store_name.setText(operationFlowArrayList.get(position).getStorename());

        if (position == 0) {
            holder.view_start.setVisibility(View.GONE);
        } else {
            holder.view_start.setVisibility(View.VISIBLE);
            holder.view_start.setBackgroundColor(Color.parseColor(operationFlowArrayList.get(position - 1).getStatuscolor()));
        }

        if (position == operationFlowArrayList.size() - 1) {
            holder.view_end.setVisibility(View.GONE);
        }


        if (operationFlowArrayList.get(position).getIscompleted().equals("1")) {
            holder.iv_flow_indicater.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checkmark));
        } else {
            holder.iv_flow_indicater.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circular_ring));
        }


        if (operationFlowArrayList.get(position).getIscurrent().equals("1")) {
            holder.iv_flow_indicater.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_button));
        }


        if (operationFlowArrayList.get(position).getIscompleted().equals("1") && operationFlowArrayList.get(position).getIscurrent().equals("1")) {
            holder.iv_flow_indicater.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_checkmark));
        }

        holder.iv_flow_indicater.setImageTintList(ColorStateList.valueOf(Color.parseColor(operationFlowArrayList.get(position).getStatuscolor())));
        holder.view_end.setBackgroundColor(Color.parseColor(operationFlowArrayList.get(position).getStatuscolor()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickInterface.ClickOnOperationFlow(operationFlowArrayList.get(holder.getAdapterPosition()));
            }
        });

    }


    @Override
    public int getItemCount() {
        return operationFlowArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_operation_name, tv_store_name;
        ImageView iv_flow_indicater;
        View view_start, view_end;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_operation_name = itemView.findViewById(R.id.tv_operation_name);
            tv_store_name = itemView.findViewById(R.id.tv_store_name);
            iv_flow_indicater = itemView.findViewById(R.id.iv_flow_indicater);
            view_start = itemView.findViewById(R.id.view_start);
            view_end = itemView.findViewById(R.id.view_end);

        }
    }

    public interface OnclickInterface {
        void ClickOnOperationFlow(OperationFlow operationFlow);
    }

}

