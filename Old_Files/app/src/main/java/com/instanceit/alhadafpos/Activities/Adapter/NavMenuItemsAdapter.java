package com.instanceit.alhadafpos.Activities.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Activities.Adapter.Interfaces.NavMenuItemClickInterface;
import com.instanceit.alhadafpos.Entity.UserRights;
import com.instanceit.alhadafpos.Helper.FontManager;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class NavMenuItemsAdapter extends RecyclerView.Adapter<NavMenuItemsAdapter.ViewHolder> {

    Context context;
    ArrayList<UserRights> navParentArrayList;
    public static int lastCheckedPos = -1;

    NavMenuItemClickInterface navMenuItemClickInterface;

    public NavMenuChildAdapter adapter;

    public NavMenuItemsAdapter(Context context, ArrayList<UserRights> navParentArrayList, NavMenuItemClickInterface navMenuItemClickInterface) {
        this.context = context;
        this.navParentArrayList = navParentArrayList;
        this.navMenuItemClickInterface = navMenuItemClickInterface;
        lastCheckedPos = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_nav_menu_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        if (navParentArrayList.get(i).getChild() != null && navParentArrayList.get(i).getChild().size() > 0) {
//        if (navParentArrayList.get(i).getIsindividual() == 0) {

            viewHolder.iv_dropdown.setVisibility(View.VISIBLE);

            boolean isViewRight = false;
            try {

                if (navParentArrayList.get(i).getChild() != null) {

                    isViewRight = false;

                    for (int j = 0; j < navParentArrayList.get(i).getChild().size(); j++) {

                        if (navParentArrayList.get(i).getChild().get(j).getViewright() == 1) {
                            isViewRight = true;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isViewRight) {
                viewHolder.ln_main.setVisibility(View.GONE);
                viewHolder.rl_bg.setVisibility(View.GONE);
            } else {
                viewHolder.ln_main.setVisibility(View.VISIBLE);
                viewHolder.rl_bg.setVisibility(View.VISIBLE);
            }

        } else {

            viewHolder.iv_dropdown.setVisibility(View.GONE);

            if (navParentArrayList.get(i).getViewright() == 1 && navParentArrayList.get(i).getIsindividual() == 1) {
                viewHolder.ln_main.setVisibility(View.VISIBLE);
                viewHolder.rl_bg.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ln_main.setVisibility(View.GONE);
                viewHolder.rl_bg.setVisibility(View.GONE);
            }
        }

        if (i == lastCheckedPos) {
//            viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
//            viewHolder.tv_icon.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));

            viewHolder.rl_bg.setVisibility(View.VISIBLE);

            if (navParentArrayList.get(i).getIsindividual() == 0) {
                viewHolder.rv_child_item.setVisibility(View.VISIBLE);
                if (navParentArrayList.get(i).getChild() != null) {
                    adapter = new NavMenuChildAdapter(context, navParentArrayList.get(i).getChild(), navMenuItemClickInterface);
                    viewHolder.rv_child_item.setAdapter(adapter);
                }
            } else {
                viewHolder.rv_child_item.setVisibility(View.GONE);
            }

        } else {

            viewHolder.rl_bg.setVisibility(View.GONE);
            viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.main_blue));
            viewHolder.tv_icon.setTextColor(context.getResources().getColor(R.color.main_blue));
            viewHolder.rv_child_item.setVisibility(View.GONE);

        }


        viewHolder.tv_menu_item.setText(navParentArrayList.get(i).getPagename());

        viewHolder.ln_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPos = i;

                if (navParentArrayList.get(i).getIsindividual() == 1) {
                    /*viewHolder.rv_child_item.setVisibility(View.VISIBLE);
                    NavMenuChildAdapter adapter = new NavMenuChildAdapter(context, navParentArrayList.get(i).getItem());
                    viewHolder.rv_child_item.setAdapter(adapter);*/
                    navMenuItemClickInterface.MenuItemClick(navParentArrayList.get(i).getAppmenuname());

                    notifyDataSetChanged();

                } else {

                    if (viewHolder.rl_bg.getVisibility() == View.GONE) {
                        viewHolder.rl_bg.setVisibility(View.VISIBLE);
//                        viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
//                        viewHolder.tv_icon.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                        viewHolder.rv_child_item.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    } else {
                        lastCheckedPos = -1;
                        viewHolder.rl_bg.setVisibility(View.GONE);
                        viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.main_blue));
                        viewHolder.tv_icon.setTextColor(context.getResources().getColor(R.color.main_blue));
                        viewHolder.rv_child_item.setVisibility(View.GONE);
                    }
                }
            }
        });


        viewHolder.tv_icon.setText(Html.fromHtml(navParentArrayList.get(i).getIcon()));

//        Log.e("onBindViewHolder", "onBindViewHolder: " + i + " icon " + navParentArrayList.get(i).getIcon());
        String fontawesome = "";

        if (navParentArrayList.get(i).getIconstyle().equals("fas")) {
            fontawesome = "fonts/fontawesome_solid.ttf";
        } else if (navParentArrayList.get(i).getIconstyle().equals("far")) {
            fontawesome = "fonts/fontawesome_regular.ttf";
        } else if (navParentArrayList.get(i).getIconstyle().equals("fab")) {
            fontawesome = "fonts/fontawesome_brand.ttf";
        } else if (navParentArrayList.get(i).getIconstyle().equals("fal")) {
            fontawesome = "fonts/fontawsome_light.ttf";
        }
        Typeface custom_font = FontManager.getTypeface(context, fontawesome);
        viewHolder.tv_icon.setTypeface(custom_font);


//        //<editor-fold desc="static menu">
//        if (i == lastCheckedPos) {
//            viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.loging_btn));
//            viewHolder.rl_bg.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.rl_bg.setVisibility(View.GONE);
//            viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.black));
//        }
//
//        Glide.with(context).load(navParentArrayList.get(i).getViewright()).into(viewHolder.iv_icon);
//        viewHolder.iv_icon.setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
//
//        viewHolder.tv_menu_item.setText(navParentArrayList.get(i).getPagename());
//
//        viewHolder.ln_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lastCheckedPos = i;
//                navMenuItemClickInterface.MenuItemClick(navParentArrayList.get(viewHolder.getAdapterPosition()).getAppmenuname());
//                notifyDataSetChanged();
//            }
//        });
//        //</editor-fold>

    }

    @Override
    public int getItemCount() {
        return navParentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_menu_item, tv_icon;
        RelativeLayout rl_bg, rl_main_parent;
        RecyclerView rv_child_item;
        LinearLayout ln_main;
        ImageView iv_dropdown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_menu_item = itemView.findViewById(R.id.tv_menu_item);
            tv_icon = itemView.findViewById(R.id.tv_icon);
            ln_main = itemView.findViewById(R.id.ln_main);
            iv_dropdown = itemView.findViewById(R.id.iv_dropdown);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            rv_child_item = itemView.findViewById(R.id.rv_child_item);
            rl_main_parent = itemView.findViewById(R.id.rl_main_parent);

            rv_child_item.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
