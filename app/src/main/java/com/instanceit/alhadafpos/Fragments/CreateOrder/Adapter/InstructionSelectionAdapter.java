package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Instruction;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Entity.Returnorderdetailinfo;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class InstructionSelectionAdapter extends RecyclerView.Adapter<InstructionSelectionAdapter.ViewHolder> {

    Context context;
    ArrayList<Instruction> instructionArrayList;
    OnclickListener onclickListener;

    //variable
    MainActivity mainActivity;

    public InstructionSelectionAdapter(Context context, ArrayList<Instruction> instructionArrayList ,OnclickListener onclickListener) {
        this.context = context;
        this.instructionArrayList = instructionArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_instruction, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_no.setText((position + 1) + ". ");
        holder.tv_item.setText(instructionArrayList.get(position).getName());

        if (instructionArrayList.get(position).getIsitemadded() == 1) {
            holder.chk_collect.setChecked(true);
        } else {
            holder.chk_collect.setChecked(false);
        }

        holder.chk_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.chk_collect.isChecked()) {
                    instructionArrayList.get(holder.getAdapterPosition()).setIsitemadded(1);
                } else {
                    instructionArrayList.get(holder.getAdapterPosition()).setIsitemadded(0);
                }
                onclickListener.onClick(instructionArrayList);
                notifyDataSetChanged();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.chk_collect.isChecked()) {
                    holder.chk_collect.setChecked(false);
                    instructionArrayList.get(holder.getAdapterPosition()).setIsitemadded(0);
                } else {
                    holder.chk_collect.setChecked(true);
                    instructionArrayList.get(holder.getAdapterPosition()).setIsitemadded(1);
                }
                onclickListener.onClick(instructionArrayList);
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return instructionArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_no, tv_item;
        CheckBox chk_collect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item = itemView.findViewById(R.id.tv_item);
            tv_no = itemView.findViewById(R.id.tv_no);
            chk_collect = itemView.findViewById(R.id.chk_collect);
        }
    }

    public interface OnclickListener {
        void onClick(ArrayList<Instruction> instructionArrayList);
    }

}

