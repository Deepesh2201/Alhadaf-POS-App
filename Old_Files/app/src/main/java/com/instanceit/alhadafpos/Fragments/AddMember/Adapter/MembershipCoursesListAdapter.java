package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class MembershipCoursesListAdapter extends RecyclerView.Adapter<MembershipCoursesListAdapter.ViewHolder> {

    MainActivity mainActivity;
    Context context;
    ArrayList<MembershipCourseItem> membershipCourseItemArrayList;
    private final OnItemClickListener listener;
    Boolean added;

    public MembershipCoursesListAdapter(Context context, ArrayList<MembershipCourseItem> membershipCourseItemArrayList, OnItemClickListener listener) {
        mainActivity = (MainActivity) context;
        this.context = context;
        this.membershipCourseItemArrayList = membershipCourseItemArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adepter_item_list_row, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);


        Glide.with(context).asBitmap().load(membershipCourseItemArrayList.get(position).getIconimg()).into(holder.iv_icon_img);
        holder.tv_membership_type.setText(membershipCourseItemArrayList.get(position).getItemname());
        holder.tv_membership_price.setText("Qr " + String.format("%.2f", Double.parseDouble(membershipCourseItemArrayList.get(position).getPrice())));
        holder.tv_per_month.setText("/" + membershipCourseItemArrayList.get(position).getDurationname());


        if (membershipCourseItemArrayList.get(position).getIgst().equals("0.0")) {
            holder.tv_tax.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_tax.setText("(" + membershipCourseItemArrayList.get(position).getIgst() + " % " + membershipCourseItemArrayList.get(position).getTaxtypename() + ")");
            holder.tv_tax.setVisibility(View.VISIBLE);
        }

        int size = 0;
        if (membershipCourseItemArrayList.get(position).getAttributedetail().size() < 5) {
            size = membershipCourseItemArrayList.get(position).getAttributedetail().size();
        } else {
            size = 5;
        }


        ItemDetailAdapter adapter = new ItemDetailAdapter(context, membershipCourseItemArrayList.get(position).getAttributedetail(), size);
        holder.rv_membership_detail.setAdapter(adapter);

        holder.ln_full_specification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clickManage : 1 for full detail / 0 for add to cart
                listener.onItemClick(membershipCourseItemArrayList.get(holder.getAdapterPosition()), 1);
            }
        });


        holder.tv_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // clickManage : 1 for full detail / 0 for add to cart
                if (membershipCourseItemArrayList.get(holder.getAdapterPosition()).getIsadded() == 1) {
                    Toast.makeText(context, Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_MESS_ALREADY_ADDED).getLabel(), Toast.LENGTH_SHORT).show();
                } else {
                    listener.onItemClick(membershipCourseItemArrayList.get(holder.getAdapterPosition()), 0);
                    membershipCourseItemArrayList.get(holder.getAdapterPosition()).setIsadded(1);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return membershipCourseItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_icon_img;
        RecyclerView rv_membership_detail;
        public TextView tv_membership_type, tv_add_to_cart, tv_membership_price, tv_full_specification, tv_tax, tv_per_month;
        LinearLayout ln_add_to_cart, ln_full_specification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_membership_type = itemView.findViewById(R.id.tv_membership_type);
            tv_membership_price = itemView.findViewById(R.id.tv_membership_price);
            iv_icon_img = itemView.findViewById(R.id.iv_icon_img);
            tv_full_specification = itemView.findViewById(R.id.tv_full_specification);
            ln_add_to_cart = itemView.findViewById(R.id.ln_add_to_cart);
            tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);
            rv_membership_detail = itemView.findViewById(R.id.rv_membership_detail);
            tv_tax = itemView.findViewById(R.id.tv_tax);
            ln_full_specification = itemView.findViewById(R.id.ln_full_specification);
            tv_per_month = itemView.findViewById(R.id.tv_per_month);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            rv_membership_detail.setLayoutManager(linearLayoutManager);

            try {
                tv_full_specification.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_TV_MORE_DETAIL).getLabel());
                tv_add_to_cart.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_HOME_ADD_TO_CART).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(MembershipCourseItem model, int ClickManage);
    }

}
