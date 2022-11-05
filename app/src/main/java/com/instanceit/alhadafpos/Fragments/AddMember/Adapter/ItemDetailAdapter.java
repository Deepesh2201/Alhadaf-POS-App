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
public class ItemDetailAdapter extends RecyclerView.Adapter<ItemDetailAdapter.ViewHolder> {

    Context context;
    ArrayList<Attributedetail> itemDetailArrayList;
    int size = 0;


    public ItemDetailAdapter(Context context, ArrayList<Attributedetail> itemDetailArrayList, int size) {
        this.context = context;
        this.itemDetailArrayList = itemDetailArrayList;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_detail_row, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.iv_icon).asBitmap().load(itemDetailArrayList.get(position).getIconimg()).into(holder.iv_icon);
        holder.tv_decription.setText(itemDetailArrayList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return size;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ln_image;
        ImageView iv_icon;
        TextView tv_decription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ln_image = itemView.findViewById(R.id.ln_image);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_decription = itemView.findViewById(R.id.tv_decription);

        }
    }

}
