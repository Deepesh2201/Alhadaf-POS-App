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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.MembershipCourseItem;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    MainActivity mainActivity;
    Context context;
    ArrayList<MembershipCourseItem> membershipCourseItemArrayList;
    Boolean btn_show;
    private final OnItemClickListener listener;


    public CoursesAdapter(Context context, ArrayList<MembershipCourseItem> membershipCourseItemArrayList, Boolean btn_show, OnItemClickListener listener) {
        mainActivity = (MainActivity) context;
        this.context = context;
        this.membershipCourseItemArrayList = membershipCourseItemArrayList;
        this.btn_show = btn_show;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adepter_courses_courses_row, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (btn_show) {
            holder.tv_add_to_cart.setVisibility(View.VISIBLE);
        } else {
            holder.tv_add_to_cart.setVisibility(View.GONE);
        }

        if (membershipCourseItemArrayList.get(position).getIsadded() == 1) {
            holder.tv_add_to_cart.setText("Go to cart");
        } else {
            holder.tv_add_to_cart.setText("Add to cart");
        }

        Glide.with(holder.iv_image_view).asBitmap().load(membershipCourseItemArrayList.get(position).getImage()).into(holder.iv_image_view);

        holder.tv_course_name.setText(membershipCourseItemArrayList.get(position).getItemname());

        holder.tv_course_price.setText("Staring from Qr " + String.format("%.2f", Double.parseDouble(membershipCourseItemArrayList.get(position).getPrice())));

        holder.ln_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ClickManage-1= detail / 0-add to cart
                if (membershipCourseItemArrayList.get(holder.getAdapterPosition()).getIsadded() == 1) {
                    Toast.makeText(context, "Already added into cart", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onItemClick(membershipCourseItemArrayList.get(holder.getAdapterPosition()), 0);
                    membershipCourseItemArrayList.get(holder.getAdapterPosition()).setIsadded(1);
                    notifyDataSetChanged();
                }
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ClickManage-1= detail / 0-add to cart
                listener.onItemClick(membershipCourseItemArrayList.get(holder.getAdapterPosition()), 1);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return membershipCourseItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image_view;
        TextView tv_course_name, tv_course_price, tv_add_to_cart;
        LinearLayout ln_add_to_cart;
        CardView cv_course_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_course_name = itemView.findViewById(R.id.tv_course_name);
            tv_course_price = itemView.findViewById(R.id.tv_course_price);
            iv_image_view = itemView.findViewById(R.id.iv_image_view);
            ln_add_to_cart = itemView.findViewById(R.id.ln_add_to_cart);
            cv_course_detail = itemView.findViewById(R.id.cv_course_detail);
            tv_add_to_cart = itemView.findViewById(R.id.tv_add_to_cart);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MembershipCourseItem model, int ClickManage);
    }
}
