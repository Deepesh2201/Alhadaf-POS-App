package com.instanceit.alhadafpos.Fragments.Report.Adapter;


import static android.view.View.GONE;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    Context context;
    ArrayList<ReportModel> reportArrayList;
    String withitemdetail, withfulldetail;

    //variable
    MainActivity mainActivity;

    public ReportAdapter(Context context, String withitemdetail, String withfulldetail, ArrayList<ReportModel> reportArrayList) {
        this.context = context;
        this.reportArrayList = reportArrayList;
        mainActivity = (MainActivity) context;
        this.withfulldetail = withfulldetail;
        this.withitemdetail = withitemdetail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_report_list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_index.setText("" + (position + 1));
        holder.tv_trs_id.setText(reportArrayList.get(position).getTransactionid());
        holder.tv_order_date.setText(reportArrayList.get(position).getOfulldate());
        holder.tv_member.setText(reportArrayList.get(position).getMembername() + "\n (" + reportArrayList.get(position).getMembercontact() + ")");
        holder.tv_order_no.setText(reportArrayList.get(position).getOrderno());
        holder.tv_status.setText(reportArrayList.get(position).getOrdstatusname());
        holder.tv_payment_type.setText(reportArrayList.get(position).getPaymenttypename());
        holder.tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(reportArrayList.get(position).getTotalamount())));
        holder.tv_entery_by.setText(reportArrayList.get(position).getEntrypersonname() + "\n (" + reportArrayList.get(position).getEntrypersoncontact() + ")");

        if (reportArrayList.get(position).getItemdetail().size() > 0) {
            ReportMembershipAdapter reportMembershipAdapter = new ReportMembershipAdapter(context, withitemdetail, withfulldetail, reportArrayList.get(position).getItemdetail());
            holder.rv_membership_list.setAdapter(reportMembershipAdapter);
            holder.ln_detail.setVisibility(View.VISIBLE);
        } else {
            holder.ln_detail.setVisibility(GONE);
        }

    }


    @Override
    public int getItemCount() {
        return reportArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trs_id, tv_order_no, tv_order_date, tv_amount, tv_member, tv_entery_by, tv_more, tv_status, tv_index, tv_payment_type,
                tv_lbl_index, tv_lbl_dec, tv_lbl_validity, tv_lbl_expiry, tv_lbl_price, tv_lbl_cmp, tv_lbl_taxable, tv_lbl_vat, tv_lbl_amount, tv_ord_details;
        LinearLayout ln_opration, ln_detail;
        RecyclerView rv_membership_list;

        ImageView iv_edit_order, iv_del_order, iv_print_invoice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_trs_id = itemView.findViewById(R.id.tv_trs_id);
            tv_order_no = itemView.findViewById(R.id.tv_order_no);
            tv_order_date = itemView.findViewById(R.id.tv_order_date);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_member = itemView.findViewById(R.id.tv_member);
            tv_entery_by = itemView.findViewById(R.id.tv_entery_by);
            tv_more = itemView.findViewById(R.id.tv_more);
            tv_status = itemView.findViewById(R.id.tv_status);
            iv_edit_order = itemView.findViewById(R.id.iv_edit_order);
            iv_del_order = itemView.findViewById(R.id.iv_del_order);
            ln_opration = itemView.findViewById(R.id.ln_opration);
            iv_print_invoice = itemView.findViewById(R.id.iv_print_invoice);
            tv_index = itemView.findViewById(R.id.tv_index);
            rv_membership_list = itemView.findViewById(R.id.rv_membership_list);
            ln_detail = itemView.findViewById(R.id.ln_detail);
            tv_payment_type = itemView.findViewById(R.id.tv_payment_type);
            tv_lbl_index = itemView.findViewById(R.id.tv_lbl_index);
            tv_lbl_dec = itemView.findViewById(R.id.tv_lbl_dec);
            tv_lbl_validity = itemView.findViewById(R.id.tv_lbl_validity);
            tv_lbl_expiry = itemView.findViewById(R.id.tv_lbl_expiry);
            tv_lbl_price = itemView.findViewById(R.id.tv_lbl_price);
            tv_lbl_cmp = itemView.findViewById(R.id.tv_lbl_cmp);
            tv_lbl_taxable = itemView.findViewById(R.id.tv_lbl_taxable);
            tv_lbl_vat = itemView.findViewById(R.id.tv_lbl_vat);
            tv_lbl_amount = itemView.findViewById(R.id.tv_lbl_amount);
            tv_ord_details = itemView.findViewById(R.id.tv_ord_details);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            rv_membership_list.setLayoutManager(linearLayoutManager);

            try {
                tv_lbl_index.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_INDEX).getLabel());
                tv_lbl_dec.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_VALIDITY).getLabel());
                tv_lbl_validity.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DESCRIPTION).getLabel());
                tv_lbl_expiry.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_EXPIRY_DATE).getLabel());
                tv_lbl_price.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_PRICE).getLabel());
                tv_lbl_cmp.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_CPN_DIS_AMOUNT).getLabel());
                tv_lbl_taxable.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ITEM_TAXABLE).getLabel());
                tv_lbl_vat.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_VAT).getLabel());
                tv_lbl_amount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_PAYMENT_DLG_TITLE_AMOUNT).getLabel());
                tv_ord_details.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_DETAIL).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface OnclickListener {
        void onClick(ServiceOrder model, int clickMange);
    }

}

