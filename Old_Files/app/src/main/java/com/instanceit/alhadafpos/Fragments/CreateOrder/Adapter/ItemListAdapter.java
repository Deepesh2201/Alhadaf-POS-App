package com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.Items;
import com.instanceit.alhadafpos.Fragments.CreateOrder.CreateOrderMainFragment;
import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderFragment;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Items> itemsArrayList, itemsArrayList_filter;
    CreateOrderMainFragment createOrderMainFragment;
    ServiceOrderFragment serviceOrderFragment;
    MainActivity mainActivity;

    public ItemListAdapter(Context context, ArrayList<Items> itemsArrayList, CreateOrderMainFragment createOrderMainFragment, ServiceOrderFragment serviceOrderFragment) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
        this.itemsArrayList_filter = itemsArrayList;
        this.createOrderMainFragment = createOrderMainFragment;
        this.serviceOrderFragment = serviceOrderFragment;
        mainActivity = (MainActivity) context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.tv_itemname.setText(itemsArrayList.get(holder.getAdapterPosition()).getName());
        holder.tv_price.setText("" + itemsArrayList.get(holder.getAdapterPosition()).getPrice());
        Glide.with(context).asBitmap().load(itemsArrayList.get(holder.getAdapterPosition()).getImage()).into(holder.iv_item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                createOrderMainFragment.AddToCart(itemsArrayList.get(holder.getAdapterPosition()));

                if (!SessionManagement.getStringValue(context, AppConstant.LAST_ADDED_CUST_CONTACT, "contact_no").trim().equals("")) {
                    if (createOrderMainFragment != null) {
                        createOrderMainFragment.AddToCart(itemsArrayList.get(holder.getAdapterPosition()));
                    } else {
                        serviceOrderFragment.AddToCart(itemsArrayList.get(holder.getAdapterPosition()));
                    }
                } else {
                    Toast.makeText(context, Utility.languageLabel(mainActivity, LabelMaster.LBL_CONTACT_DLG_ERR_ENTER_CONTACT_NUM).getLabel(), Toast.LENGTH_SHORT).show();
                    if (createOrderMainFragment != null) {
                        createOrderMainFragment.ContactDialog((Activity) context);
                    } else {
                        serviceOrderFragment.ContactDialog((Activity) context);
                    }
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_price;
        ImageView iv_item;
        TextView tv_itemname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_price = itemView.findViewById(R.id.tv_price);
            iv_item = itemView.findViewById(R.id.iv_item);
            tv_itemname = itemView.findViewById(R.id.tv_itemname);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemsArrayList = itemsArrayList_filter;
                } else {
                    try {
                        ArrayList<Items> filteredList = new ArrayList<>();
                        for (Items row : itemsArrayList_filter) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                    || row.getItemno().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                        itemsArrayList = filteredList;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                try {
                    itemsArrayList = (ArrayList<Items>) filterResults.values;
                    if (itemsArrayList.size() <= 0) {
                        Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
//                        serviceOrderFragment.rl_no_item.setVisibility(VISIBLE);
//                        serviceOrderFragment.rl_item_main.setVisibility(GONE);
//                        serviceOrderFragment.tv_cart_switch.setVisibility(GONE);
                        serviceOrderFragment.tv_nodata_item.setVisibility(VISIBLE);
                    }
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
    }

//    @Override
//    public android.widget.Filter getFilter() {
//        return new android.widget.Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                final String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//                    itemsArrayList = itemsArrayList_filter;
//                } else {
//                    ArrayList<Items> filteredList = new ArrayList<>();
//
//                    for (Items row : itemsArrayList_filter) {
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getItemno().toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    itemsArrayList = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = itemsArrayList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                itemsArrayList = (ArrayList<Items>) filterResults.values;
//                if (itemsArrayList.size() <= 0) {
//                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
//                }
//                notifyDataSetChanged();
//            }
//        };
//    }

}

