package com.instanceit.alhadafpos.Fragments.Report.Adapter;


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
import com.instanceit.alhadafpos.Entity.Itemdetail;
import com.instanceit.alhadafpos.Entity.ReportModel;
import com.instanceit.alhadafpos.Entity.ServiceOrder;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class ReportMembershipAdapter extends RecyclerView.Adapter<ReportMembershipAdapter.ViewHolder> {

    Context context;
    ArrayList<Itemdetail> ItemdetailArrayList;
    String withitemdetail, withfulldetail;

    //variable
    MainActivity mainActivity;

    public ReportMembershipAdapter(Context context, String withitemdetail, String withfulldetail, ArrayList<Itemdetail> ItemdetailArrayList) {
        this.context = context;
        this.ItemdetailArrayList = ItemdetailArrayList;
        mainActivity = (MainActivity) context;
        this.withfulldetail = withfulldetail;
        this.withitemdetail = withitemdetail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_report_membership_list_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_index.setText("" + (position + 1));
        holder.tv_dec.setText(ItemdetailArrayList.get(position).getItemname());
        holder.tv_validity.setText(ItemdetailArrayList.get(position).getExpirydate());
        holder.tv_expiry.setText(ItemdetailArrayList.get(position).getDurationname());
        holder.tv_taxable.setText(ItemdetailArrayList.get(position).getTaxable());
        holder.tv_vat.setText(ItemdetailArrayList.get(position).getIgsttaxamt());
        holder.tv_amount.setText("Qr " + String.format("%.2f", Double.parseDouble(ItemdetailArrayList.get(position).getFinalprice())));
        holder.tv_cmp.setText(ItemdetailArrayList.get(position).getCouponamount());

        if (ItemdetailArrayList.get(position).getIsitemfulldetail().equals("1")) {
            ItemDetailAdapter itemDetailAdapter = new ItemDetailAdapter(context, ItemdetailArrayList.get(position).getItemfulldetail());
            holder.rv_item_list.setAdapter(itemDetailAdapter);
            holder.ln_item.setVisibility(View.VISIBLE);
            holder.view_1.setVisibility(View.VISIBLE);
        } else {
            holder.ln_item.setVisibility(View.GONE);
            holder.view_1.setVisibility(View.GONE);
        }


        if (ItemdetailArrayList.get(position).getIsitemwebsitedetail().equals("1")) {
            WebDisplayDetailAdapter itemDetailAdapter = new WebDisplayDetailAdapter(context, ItemdetailArrayList.get(position).getItemwebsitedetail());
            holder.rv_web_list.setAdapter(itemDetailAdapter);
            holder.ln_web.setVisibility(View.VISIBLE);
            holder.view_2.setVisibility(View.VISIBLE);
        } else {
            holder.ln_web.setVisibility(View.GONE);
            holder.view_2.setVisibility(View.GONE);
        }

        if (ItemdetailArrayList.get(position).getIscoursebenefitdetail().equals("1")) {
            CourseBenefitAdapter itemDetailAdapter = new CourseBenefitAdapter(context, ItemdetailArrayList.get(position).getCoursebenefitdetail());
            holder.rv_benefits_list.setAdapter(itemDetailAdapter);
            holder.ln_course.setVisibility(View.VISIBLE);
            holder.view_3.setVisibility(View.VISIBLE);
        } else {
            holder.ln_course.setVisibility(View.GONE);
            holder.view_3.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return ItemdetailArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_index, tv_dec, tv_validity, tv_expiry, tv_price, tv_cmp, tv_taxable, tv_vat, tv_amount, tv_ord_details,
                tv_lbl_index, tv_lbl_item, tv_lbl_qty, tv_lbl_used_qty, tv_lbl_remain_qty, tv_lbl_decount, tv_lbl_vat_type, tv_lbl_vat,
                tv_lbl_prince, tv_lbl_duration, tv_lbl_attribute, tv_lbl_web, tv_lbl_course_benefits, tv_lbl_benefits,tv_item_details;
        RecyclerView rv_item_list, rv_web_list, rv_benefits_list;
        LinearLayout ln_item, ln_web, ln_course;
        View view_1, view_2, view_3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_index = itemView.findViewById(R.id.tv_index);
            tv_dec = itemView.findViewById(R.id.tv_dec);
            tv_validity = itemView.findViewById(R.id.tv_validity);
            tv_expiry = itemView.findViewById(R.id.tv_expiry);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_cmp = itemView.findViewById(R.id.tv_cmp);
            tv_taxable = itemView.findViewById(R.id.tv_taxable);
            tv_vat = itemView.findViewById(R.id.tv_vat);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            rv_item_list = itemView.findViewById(R.id.rv_item_list);
            rv_web_list = itemView.findViewById(R.id.rv_web_list);
            rv_benefits_list = itemView.findViewById(R.id.rv_benefits_list);
            ln_item = itemView.findViewById(R.id.ln_item);
            ln_web = itemView.findViewById(R.id.ln_web);
            ln_course = itemView.findViewById(R.id.ln_course);
            view_1 = itemView.findViewById(R.id.view_1);
            view_2 = itemView.findViewById(R.id.view_2);
            view_3 = itemView.findViewById(R.id.view_3);
            tv_ord_details = itemView.findViewById(R.id.tv_ord_details);
            tv_lbl_index = itemView.findViewById(R.id.tv_lbl_index);
            tv_lbl_item = itemView.findViewById(R.id.tv_lbl_item);
            tv_lbl_qty = itemView.findViewById(R.id.tv_lbl_qty);
            tv_lbl_used_qty = itemView.findViewById(R.id.tv_lbl_used_qty);
            tv_lbl_remain_qty = itemView.findViewById(R.id.tv_lbl_remain_qty);
            tv_lbl_decount = itemView.findViewById(R.id.tv_lbl_decount);
            tv_lbl_vat_type = itemView.findViewById(R.id.tv_lbl_vat_type);
            tv_lbl_vat = itemView.findViewById(R.id.tv_lbl_vat);
            tv_lbl_prince = itemView.findViewById(R.id.tv_lbl_prince);
            tv_lbl_duration = itemView.findViewById(R.id.tv_lbl_duration);
            tv_ord_details = itemView.findViewById(R.id.tv_ord_details);
            tv_lbl_attribute = itemView.findViewById(R.id.tv_lbl_attribute);
            tv_lbl_web = itemView.findViewById(R.id.tv_lbl_web);
            tv_lbl_course_benefits = itemView.findViewById(R.id.tv_lbl_course_benefits);
            tv_lbl_benefits = itemView.findViewById(R.id.tv_lbl_benefits);
            tv_lbl_duration = itemView.findViewById(R.id.tv_lbl_duration);
            tv_item_details = itemView.findViewById(R.id.tv_item_details);


//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            rv_benefits_list.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            rv_item_list.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            rv_web_list.setLayoutManager(new LinearLayoutManager(itemView.getContext()));


            try {
                tv_ord_details.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ITEM_DETAIL).getLabel());
                tv_lbl_index.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORDER_HIS_INDEX).getLabel());
                tv_lbl_item.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CUSTOMER_ITEM_TITLE).getLabel());
                tv_lbl_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_QTY).getLabel());
                tv_lbl_used_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_USED_QTY).getLabel());
                tv_lbl_remain_qty.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_REMAIN_QTY).getLabel());
                tv_lbl_decount.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ORD_DLG_ITEM_DESCOUNT).getLabel());
                tv_lbl_vat_type.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_VAT_TYPE).getLabel());
                tv_lbl_vat.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_TOT_VAT).getLabel());
                tv_lbl_prince.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DLG_PRICE).getLabel());
                tv_lbl_duration.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_DURATION_LBL).getLabel());
                tv_ord_details.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_WEB_DISPLAY).getLabel());
                tv_lbl_attribute.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ATTRIBUTE).getLabel());
                tv_lbl_web.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ATTRIBUTE_NAME).getLabel());
                tv_lbl_course_benefits.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_COURSE_HEADING_LBL).getLabel());
                tv_lbl_benefits.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_BENEFITS).getLabel());
                tv_lbl_duration.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_MEMBERSHIP_DLG_DURATION_LBL).getLabel());
                tv_item_details.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_ITEM_DETAIL).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    public interface OnclickListener {
        void onClick(ServiceOrder model, int clickMange);
    }

}

