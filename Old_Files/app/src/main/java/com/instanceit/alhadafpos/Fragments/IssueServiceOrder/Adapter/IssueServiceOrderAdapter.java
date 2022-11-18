package com.instanceit.alhadafpos.Fragments.IssueServiceOrder.Adapter;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Issueorderdetailinfo;
import com.instanceit.alhadafpos.Entity.Returnorderdetailinfo;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class IssueServiceOrderAdapter extends RecyclerView.Adapter<IssueServiceOrderAdapter.ViewHolder> {

    Context context;
    ArrayList<Issueorderdetailinfo> issueorderdetailinfoArrayList;
    OnclickListener onclickListener;
    boolean isShowCheckbox;

    //variable
    MainActivity mainActivity;

    public IssueServiceOrderAdapter(Context context, ArrayList<Issueorderdetailinfo> issueorderdetailinfoArrayList, boolean isShowCheckbox,
                                    OnclickListener onclickListener) {
        this.context = context;
        this.issueorderdetailinfoArrayList = issueorderdetailinfoArrayList;
        this.onclickListener = onclickListener;
        this.isShowCheckbox = isShowCheckbox;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_return_inventory_item, parent, false);
        return new ViewHolder(view, new QtyChangeTextChangeListener());
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.ln_total_qty.setVisibility(View.VISIBLE);
        holder.ln_issued_qty.setVisibility(View.VISIBLE);

        if (isShowCheckbox) {

            holder.qtyChangeTextChangeListener.UpdateExtraOrderData(holder, holder.getAdapterPosition());

            holder.edt_qty.setClickable(true);
            holder.edt_qty.setFocusable(true);
            holder.edt_qty.setEnabled(true);

            if (issueorderdetailinfoArrayList.get(position).getIsitemadded() == 1) {
                holder.chk_collect.setChecked(true);
            } else {
                holder.chk_collect.setChecked(false);
            }

            holder.tv_total_qty.setText(issueorderdetailinfoArrayList.get(position).getQty());
            holder.tv_issued_qty.setText(issueorderdetailinfoArrayList.get(position).getIssuedQty());

            holder.chk_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.chk_collect.isChecked()) {
                        issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty(holder.edt_qty.getText().toString());
                        issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(1);
                    } else {
                        issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(0);
                    }
                    onclickListener.onClick(issueorderdetailinfoArrayList);
                }
            });

        } else {
            holder.chk_collect.setVisibility(View.GONE);
            holder.edt_qty.setClickable(false);
            holder.edt_qty.setFocusable(false);
            holder.edt_qty.setEnabled(false);
        }

        if (Integer.parseInt(issueorderdetailinfoArrayList.get(position).getRemainQty()) > 0) {
            holder.chk_collect.setVisibility(View.VISIBLE);
        } else {
            holder.chk_collect.setVisibility(View.GONE);
            holder.edt_qty.setClickable(false);
            holder.edt_qty.setFocusable(false);
            holder.edt_qty.setEnabled(false);
            holder.edt_qty.setFocusableInTouchMode(false);
        }



        holder.tv_item.setText(issueorderdetailinfoArrayList.get(position).getItemname() + "(" + issueorderdetailinfoArrayList.get(position).getTypename() + ")");
        holder.edt_qty.setText(issueorderdetailinfoArrayList.get(position).getRemainQty());
        holder.edt_qty.setSelection(holder.edt_qty.getText().length());

        if (issueorderdetailinfoArrayList.get(position).getItemstatus().isEmpty() && issueorderdetailinfoArrayList.get(position).getItemstatuscolor().isEmpty()) {
            holder.tv_stats.setVisibility(View.GONE);
        } else {
            holder.tv_stats.setText(issueorderdetailinfoArrayList.get(position).getItemstatus());
            holder.tv_stats.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(issueorderdetailinfoArrayList.get(position).getItemstatuscolor())));
            holder.tv_stats.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return issueorderdetailinfoArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item, tv_lbl_item, tv_lbl_qty, tv_stats, tv_lbl_total_qty, tv_total_qty, tv_lbl_issued_qty, tv_issued_qty;
        EditText edt_qty, edt_comment;
        CheckBox chk_collect;
        LinearLayout ln_total_qty, ln_issued_qty;
        QtyChangeTextChangeListener qtyChangeTextChangeListener;

        public ViewHolder(@NonNull View itemView, QtyChangeTextChangeListener qtyChangeTextChangeListener) {
            super(itemView);

            tv_item = itemView.findViewById(R.id.tv_item);
            edt_qty = itemView.findViewById(R.id.edt_qty);
            chk_collect = itemView.findViewById(R.id.chk_collect);
            tv_lbl_item = itemView.findViewById(R.id.tv_lbl_item);
            tv_lbl_qty = itemView.findViewById(R.id.tv_lbl_qty);
            edt_comment = itemView.findViewById(R.id.edt_comment);
            tv_stats = itemView.findViewById(R.id.tv_stats);
            ln_total_qty = itemView.findViewById(R.id.ln_total_qty);
            tv_lbl_total_qty = itemView.findViewById(R.id.tv_lbl_total_qty);
            tv_total_qty = itemView.findViewById(R.id.tv_total_qty);
            ln_issued_qty = itemView.findViewById(R.id.ln_issued_qty);
            tv_lbl_issued_qty = itemView.findViewById(R.id.tv_lbl_issued_qty);
            tv_issued_qty = itemView.findViewById(R.id.tv_issued_qty);

            this.qtyChangeTextChangeListener = qtyChangeTextChangeListener;
            edt_qty.addTextChangedListener(qtyChangeTextChangeListener);

            tv_lbl_item.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_ITEM).getLabel() + " :  ");
            tv_lbl_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_QTY).getLabel() + " :  ");
            tv_lbl_total_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SERVICE_TOTAL_QTY).getLabel() + " :  ");
            tv_lbl_issued_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_SERVICE_ISSUE_QTY).getLabel() + " :  ");
        }
    }


    public class QtyChangeTextChangeListener implements TextWatcher {

        ViewHolder holder;
        int position;

        public void UpdateExtraOrderData(ViewHolder holder, int position) {
            this.holder = holder;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {


            try {

                if (holder.edt_qty.hasFocus()) {
                    if (s.toString().equals("") || Integer.parseInt(s.toString()) <= 0) {
                        holder.edt_qty.setHint("0");
                        issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty("0");
                    } else if (Integer.parseInt(s.toString()) > Integer.parseInt(issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).getRemainQty())) {
                        Toast.makeText(context, "Not valid Qty", Toast.LENGTH_SHORT).show();
                        holder.edt_qty.setText(issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).getRemainQty());
                        issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty(issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).getQty());
                    } else {
                        issueorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty(s.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface OnclickListener {
        void onClick(ArrayList<Issueorderdetailinfo> issueorderdetailinfoArrayList);
    }

}

