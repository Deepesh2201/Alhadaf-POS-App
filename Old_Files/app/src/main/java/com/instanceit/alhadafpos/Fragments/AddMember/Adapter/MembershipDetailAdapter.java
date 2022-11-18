package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Entity.Attributedetail;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class MembershipDetailAdapter extends RecyclerView.Adapter<MembershipDetailAdapter.ViewHolder> {
    Context context;
    ArrayList<Attributedetail> membershipdetailArrayList;
    int size = 0;


    public MembershipDetailAdapter(Context context, ArrayList<Attributedetail> membershipdetailArrayList, int size) {
        this.context = context;
        this.membershipdetailArrayList = membershipdetailArrayList;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_membership_row, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.iv_icon).asBitmap().load(membershipdetailArrayList.get(position).getIconimg()).into(holder.iv_icon);
        holder.tv_decription.setText(membershipdetailArrayList.get(position).getName());
        if (membershipdetailArrayList.get(position).getDurationname() != null) {
            holder.tv_duration.setText(membershipdetailArrayList.get(position).getDurationname());
            holder.tv_duration.setVisibility(View.VISIBLE);
        } else {
            holder.tv_duration.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ln_image;
        ImageView iv_icon;
        TextView tv_decription, tv_duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ln_image = itemView.findViewById(R.id.ln_image);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_decription = itemView.findViewById(R.id.tv_decription);
            tv_duration = itemView.findViewById(R.id.tv_duration);

        }
    }

}
