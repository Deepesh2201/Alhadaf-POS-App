package com.instanceit.alhadafpos.Activities.Adapter;


import static com.instanceit.alhadafpos.Utility.Utility.callApiGetLanguageLabels;
import static com.instanceit.alhadafpos.Utility.Utility.dialogLanguageSelector;
import static com.instanceit.alhadafpos.Utility.Utility.tv_dlg_continue;
import static com.instanceit.alhadafpos.Utility.Utility.tv_dlg_title;
import static com.instanceit.alhadafpos.Utility.Utility.tv_str_title;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.instanceit.alhadafpos.Entity.Language;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.SessionManagement;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class LanguageSelecterAdapter extends RecyclerView.Adapter<LanguageSelecterAdapter.Holder> {

    private Context context;
    private ArrayList<Language> languageArrayList;

    int lastcheckPos = 0;


    public LanguageSelecterAdapter(Context context, ArrayList<Language> languageArrayList, int lastcheckPos) {
        this.context = context;
        this.languageArrayList = languageArrayList;
        this.lastcheckPos = lastcheckPos;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_language_selector, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        if (lastcheckPos == holder.getAdapterPosition()) {
            holder.rb_language.setChecked(true);
            holder.ln_main.setBackground(context.getResources().getDrawable(R.drawable.bg_border_orange_selected));
            tv_dlg_title.setText(languageArrayList.get(holder.getAdapterPosition()).getLabel1());
            tv_dlg_continue.setText(languageArrayList.get(holder.getAdapterPosition()).getLabel2());
            tv_str_title.setText(languageArrayList.get(holder.getAdapterPosition()).getLabel3());

            tv_str_title.setTypeface(tv_str_title.getTypeface(), Typeface.BOLD);
        } else {
            holder.rb_language.setChecked(false);
            holder.ln_main.setBackground(context.getResources().getDrawable(R.drawable.bg_white_border_gray));
        }

        holder.tv_lang.setTypeface(holder.tv_lang.getTypeface(), Typeface.BOLD);


        if (!languageArrayList.get(holder.getAdapterPosition()).getIcon().equals("")) {
            Glide.with(context).load(languageArrayList.get(holder.getAdapterPosition()).getIcon()).into(holder.iv_language);
        }

        holder.tv_lang.setText(languageArrayList.get(holder.getAdapterPosition()).getLanguagename());
        holder.tv_lang_eng.setText(languageArrayList.get(holder.getAdapterPosition()).getLanguageengname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastcheckPos = holder.getAdapterPosition();
                notifyDataSetChanged();

            }
        });

        holder.rb_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastcheckPos = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });


        tv_dlg_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLanguageSelector.dismiss();
                dialogLanguageSelector=null;
                SessionManagement.savePreferences(context, LabelMaster.SELECTED_LANGUAGE_ID, languageArrayList.get(lastcheckPos).getId());
                callApiGetLanguageLabels((Activity) context, true);
            }
        });

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int DeviceTotalWidth = displaymetrics.widthPixels;
//        holder.ln_main_width.setLayoutParams(new LinearLayout.LayoutParams(DeviceTotalWidth / 2, DeviceTotalWidth / 2));
        holder.ln_main_width.setLayoutParams(new LinearLayout.LayoutParams(DeviceTotalWidth-100, ViewGroup.LayoutParams.WRAP_CONTENT));

    }


    @Override
    public int getItemCount() {
        return languageArrayList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {

        LinearLayout ln_main, ln_main_width;
        TextView tv_lang, tv_lang_eng;
        RadioButton rb_language;
        ImageView iv_language;


        public Holder(@NonNull View itemView) {
            super(itemView);

            tv_lang = itemView.findViewById(R.id.tv_lang);
            tv_lang_eng = itemView.findViewById(R.id.tv_lang_eng);
            rb_language = itemView.findViewById(R.id.rb_language);
            iv_language = itemView.findViewById(R.id.iv_language);
            ln_main = itemView.findViewById(R.id.ln_main);
            ln_main_width = itemView.findViewById(R.id.ln_main_width);
        }
    }
}
