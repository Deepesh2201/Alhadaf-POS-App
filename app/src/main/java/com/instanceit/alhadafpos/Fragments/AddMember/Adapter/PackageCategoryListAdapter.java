package com.instanceit.alhadafpos.Fragments.AddMember.Adapter;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.Fragments.AddMember.AddMemberFragment;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class PackageCategoryListAdapter extends RecyclerView.Adapter<PackageCategoryListAdapter.ViewHolder> {

    Context context;
    ArrayList<Model> subCatArrayList;
    subCatClickInterface subCatClickInterface;

    //variable


    MainActivity mainActivity;

    public PackageCategoryListAdapter(Context context, ArrayList<Model> subCatArrayList, subCatClickInterface subCatClickInterface) {
        this.context = context;
        this.subCatArrayList = subCatArrayList;
        this.subCatClickInterface = subCatClickInterface;
        mainActivity = (MainActivity) context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_package_category_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tv_category_name.setText(subCatArrayList.get(holder.getAdapterPosition()).getName());

        //  Glide.with(context).asBitmap().load(subCatArrayList.get(holder.getAdapterPosition()).getImage()).into(holder.iv_category);

        if (AddMemberFragment.lastCheckedPos == holder.getAdapterPosition()) {
            holder.ll_category.setBackground(context.getResources().getDrawable(R.drawable.select_background));
            holder.tv_category_name.setTextSize(17);
            holder.tv_category_name.setTextColor(context.getColor(R.color.colorAccent));
            holder.tv_category_name.setTypeface(null, Typeface.BOLD);
        } else {
            holder.ll_category.setBackground(context.getResources().getDrawable(R.drawable.unselect_background));
            holder.tv_category_name.setTextSize(15);
            holder.tv_category_name.setTextColor(context.getResources().getColor(R.color.primaryTextColor));
            holder.tv_category_name.setTypeface(null, Typeface.NORMAL);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subCatClickInterface.clickOnSubCat(subCatArrayList.get(holder.getAdapterPosition()));
                AddMemberFragment.lastCheckedPos = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return subCatArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_category_name;
        LinearLayout ll_category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            ll_category = itemView.findViewById(R.id.ll_category);
        }
    }


    public interface subCatClickInterface {
        void clickOnSubCat(Model model);
    }

}

