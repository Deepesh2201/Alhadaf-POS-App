package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Storeinstructiondatum;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class InstructionListAdapter extends RecyclerView.Adapter<InstructionListAdapter.ViewHolder> {

    Context context;
    ArrayList<Storeinstructiondatum> instructionArrayList;

    //variable
    MainActivity mainActivity;

    public InstructionListAdapter(Context context, ArrayList<Storeinstructiondatum> instructionArrayList) {
        this.context = context;
        this.instructionArrayList = instructionArrayList;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_instruction_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_no.setText((position + 1) + ". ");
        holder.tv_item.setText(instructionArrayList.get(position).getName());

        if (instructionArrayList.get(position).getQty() != null) {
            holder.tv_operation_status.setText(instructionArrayList.get(position).getQty());
        } else {
            holder.tv_operation_status.setText(instructionArrayList.get(position).getInstructionstatus());
        }

    }


    @Override
    public int getItemCount() {
        return instructionArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_no, tv_item, tv_operation_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item = itemView.findViewById(R.id.tv_item);
            tv_no = itemView.findViewById(R.id.tv_no);
            tv_operation_status = itemView.findViewById(R.id.tv_operation_status);

        }
    }


}

