package com.instanceit.alhadafpos.Fragments.InventoryReturn.Adapter;


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
import com.instanceit.alhadafpos.Entity.Returnorderdetailinfo;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class CollectInventoryItemAdapter extends RecyclerView.Adapter<CollectInventoryItemAdapter.ViewHolder> {

    Context context;
    ArrayList<Returnorderdetailinfo> returnorderdetailinfoArrayList;
    OnclickListener onclickListener;
    boolean isShowCheckbox;

    //variable
    MainActivity mainActivity;

    public CollectInventoryItemAdapter(Context context, ArrayList<Returnorderdetailinfo> returnorderdetailinfoArrayList, boolean isShowCheckbox,
                                       OnclickListener onclickListener) {
        this.context = context;
        this.returnorderdetailinfoArrayList = returnorderdetailinfoArrayList;
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


        if (isShowCheckbox) {

            holder.qtyChangeTextChangeListener.UpdateExtraOrderData(holder, holder.getAdapterPosition());

            holder.edt_qty.setClickable(true);
            holder.edt_qty.setFocusable(true);
            holder.edt_qty.setEnabled(true);

            if (returnorderdetailinfoArrayList.get(position).getIsitemadded() == 1) {
                holder.chk_collect.setChecked(true);
            } else {
                holder.chk_collect.setChecked(false);
            }


            holder.chk_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.chk_collect.isChecked()) {
                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(1);
                    } else {
                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(0);
                    }
                    onclickListener.onClick(returnorderdetailinfoArrayList);
                    notifyDataSetChanged();
                }
            });

        } else {
            holder.chk_collect.setVisibility(View.GONE);
            holder.edt_qty.setClickable(false);
            holder.edt_qty.setFocusable(false);
            holder.edt_qty.setEnabled(false);
        }

        holder.tv_item.setText(returnorderdetailinfoArrayList.get(position).getItemname());
        holder.edt_qty.setText(returnorderdetailinfoArrayList.get(position).getQty());

    }


    @Override
    public int getItemCount() {
        return returnorderdetailinfoArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item, tv_lbl_item, tv_lbl_qty;
        EditText edt_qty, edt_comment;
        CheckBox chk_collect;
        QtyChangeTextChangeListener qtyChangeTextChangeListener;

        public ViewHolder(@NonNull View itemView, QtyChangeTextChangeListener qtyChangeTextChangeListener) {
            super(itemView);

            tv_item = itemView.findViewById(R.id.tv_item);
            edt_qty = itemView.findViewById(R.id.edt_qty);
            chk_collect = itemView.findViewById(R.id.chk_collect);
            tv_lbl_item = itemView.findViewById(R.id.tv_lbl_item);
            tv_lbl_qty = itemView.findViewById(R.id.tv_lbl_qty);
            edt_comment = itemView.findViewById(R.id.edt_comment);

            this.qtyChangeTextChangeListener = qtyChangeTextChangeListener;
            edt_qty.addTextChangedListener(qtyChangeTextChangeListener);


            tv_lbl_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_QTY).getLabel() + " :  ");
            tv_lbl_item.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_ITEM).getLabel() + " :  ");
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
                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty("0");
//                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(0);
                    } else if (Integer.parseInt(s.toString()) > Integer.parseInt(returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).getOldqty())) {
                        holder.edt_qty.setText(returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).getOldqty());
                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty(returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).getOldqty());
//                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(1);
                    } else {
                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setQty(s.toString());
//                        returnorderdetailinfoArrayList.get(holder.getAdapterPosition()).setIsitemadded(1);
                    }
//                    notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public interface OnclickListener {
        void onClick(ArrayList<Returnorderdetailinfo> returnorderdetailinfoArrayList);
    }

}

