package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.Fragments.AddMember.AddMemberFragment;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    ArrayList<MembershipCourseItem> cartList;
    Context context;
    AddMemberFragment addMemberFragment;
    MainActivity mainActivity;

    public CartListAdapter(Context context, ArrayList<MembershipCourseItem> cartList, AddMemberFragment addMemberFragment) {
        this.context = context;
        this.cartList = cartList;
        this.addMemberFragment = addMemberFragment;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cart_row, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_payable_amount.setText("Qr " + String.format("%.2f", cartList.get(position).getFinalprice()));

        //1-Membership, 2-Packages, 3-Course
        if (cartList.get(position).getType().equals("3")) {
            holder.tv_type.setText(Utility.languageLabel(mainActivity,LabelMaster.LBL_CART_TYPE_COURSE).getLabel());
        } else if (cartList.get(position).getType().equals("1")) {
            holder.tv_type.setText(Utility.languageLabel(mainActivity,LabelMaster.LBL_CART_TYPE_MEMBERSHIP).getLabel());
        } else {
            holder.tv_type.setText(Utility.languageLabel(mainActivity,LabelMaster.LBL_CART_TYPE_PACKAGE).getLabel());
        }

        holder.tv_title.setText(cartList.get(position).getItemname());

        holder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberFragment.DeleteDialog(cartList, holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_payable_amount, tv_title, tv_type, tv_coupon_discount_amount, lbl_tv_name_title, lbl_tv_coupon_discount_title, lbl_tv_payable_amount_title;
        ImageView iv_close;
        View line;
        RelativeLayout rl_titleBar;
        LinearLayout ln_coupon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_payable_amount = itemView.findViewById(R.id.tv_payable_amount);
            rl_titleBar = itemView.findViewById(R.id.rl_titleBar);
            iv_close = itemView.findViewById(R.id.iv_close);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_type = itemView.findViewById(R.id.tv_type);
            ln_coupon = itemView.findViewById(R.id.ln_coupon);
            line = itemView.findViewById(R.id.line);
            tv_coupon_discount_amount = itemView.findViewById(R.id.tv_coupon_discount_amount);
            lbl_tv_name_title = itemView.findViewById(R.id.lbl_tv_name_title);
            lbl_tv_coupon_discount_title = itemView.findViewById(R.id.lbl_tv_coupon_discount_title);
            lbl_tv_payable_amount_title = itemView.findViewById(R.id.lbl_tv_payable_amount_title);

            //<editor-fold desc="dynamic lbl">
            try {
                lbl_tv_name_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_NAME).getLabel());
                lbl_tv_coupon_discount_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_CPN_DIS_AMOUNT).getLabel());
                lbl_tv_payable_amount_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_PAYABLE_AMOUNT).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //</editor-fold>
        }
    }
}
