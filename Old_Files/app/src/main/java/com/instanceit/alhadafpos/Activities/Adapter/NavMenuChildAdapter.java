package com.instanceit.alhadafpos.Activities.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Activities.Adapter.Interfaces.NavMenuItemClickInterface;
import com.instanceit.alhadafpos.Entity.NavChild;
import com.instanceit.alhadafpos.Helper.FontManager;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;


public class NavMenuChildAdapter extends RecyclerView.Adapter<NavMenuChildAdapter.ViewHolder> {

    Context context;
    ArrayList<NavChild> navChildArrayList;
    public static int lastCheckedPosChild = -1;
    NavMenuItemClickInterface navMenuItemClickInterface;

    public NavMenuChildAdapter(Context context, ArrayList<NavChild> navChildArrayList, NavMenuItemClickInterface navMenuItemClickInterface) {
        this.context = context;
        this.navChildArrayList = navChildArrayList;
        this.navMenuItemClickInterface = navMenuItemClickInterface;
        //lastCheckedPosChild = -1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_child_nav_menu_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,  int i) {

        if (navChildArrayList.get(i).getViewright() == 1) {
            viewHolder.ln_main.setVisibility(View.VISIBLE);
            viewHolder.rl_bg.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ln_main.setVisibility(View.GONE);
            viewHolder.rl_bg.setVisibility(View.GONE);
        }


        if (i == lastCheckedPosChild) {
            viewHolder.rl_bg.setVisibility(View.VISIBLE);
//            viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(ThemeUtil.getThemeList().get(mTheme).getPrimaryDarkColor()));
//            viewHolder.tv_icon.setTextColor(context.getResources().getColor(ThemeUtil.getThemeList().get(mTheme).getPrimaryDarkColor()));
        } else {
            viewHolder.rl_bg.setVisibility(View.GONE);
            viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.gray_nav_light));
            viewHolder.tv_icon.setTextColor(context.getResources().getColor(R.color.gray_nav_light));
        }

        viewHolder.tv_menu_item.setText(navChildArrayList.get(i).getPagename());

        viewHolder.ln_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosChild = viewHolder.getAdapterPosition();

                navMenuItemClickInterface.MenuItemClick(navChildArrayList.get(viewHolder.getAdapterPosition()).getAppmenuname());

                if (viewHolder.rl_bg.getVisibility() == View.GONE) {
                    viewHolder.rl_bg.setVisibility(View.VISIBLE);
//                    viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(ThemeUtil.getThemeList().get(mTheme).getPrimaryDarkColor()));
//                    viewHolder.tv_icon.setTextColor(context.getResources().getColor(ThemeUtil.getThemeList().get(mTheme).getPrimaryDarkColor()));

                    notifyDataSetChanged();

                } else {
                    lastCheckedPosChild = -1;
//                    viewHolder.rl_bg.setVisibility(View.GONE);
//                    viewHolder.tv_menu_item.setTextColor(context.getResources().getColor(R.color.gray_nav_light));
//                    viewHolder.tv_icon.setTextColor(context.getResources().getColor(R.color.gray_nav_light));
                }
            }
        });

        viewHolder.tv_icon.setText(Html.fromHtml(navChildArrayList.get(i).getIcon()));

        String fontawesome = "";

        if (navChildArrayList.get(i).getIconstyle().equals("fas")) {
            fontawesome = "fonts/fontawesome_solid.ttf";
        } else if (navChildArrayList.get(i).getIconstyle().equals("far")) {
            fontawesome = "fonts/fontawesome_regular.ttf";
        } else if (navChildArrayList.get(i).getIconstyle().equals("fab")) {
            fontawesome = "fonts/fontawesome_brand.ttf";
        }
        Typeface custom_font = FontManager.getTypeface(context, fontawesome);
        viewHolder.tv_icon.setTypeface(custom_font);

    }

    @Override
    public int getItemCount() {
        return navChildArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_icon, tv_menu_item;
        RelativeLayout rl_bg;
        LinearLayout ln_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_icon = itemView.findViewById(R.id.tv_icon);
            tv_menu_item = itemView.findViewById(R.id.tv_menu_item);
            rl_bg = itemView.findViewById(R.id.rl_bg);
            ln_main = itemView.findViewById(R.id.ln_main);
        }
    }
}
