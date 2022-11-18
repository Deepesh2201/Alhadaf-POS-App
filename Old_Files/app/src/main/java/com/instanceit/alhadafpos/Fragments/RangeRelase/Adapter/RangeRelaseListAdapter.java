package com.instanceit.alhadafpos.Fragments.RangeRelase.Adapter;


import static android.view.View.GONE;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.instanceit.alhadafpos.Activities.Adapter.FullImageAdapter;
import com.instanceit.alhadafpos.Activities.MainActivity;
import com.instanceit.alhadafpos.Entity.FullImage;
import com.instanceit.alhadafpos.Entity.RangeRelease;
import com.instanceit.alhadafpos.Entity.Storeinstructiondatum;
import com.instanceit.alhadafpos.Fragments.CreateOrder.Adapter.InstructionListAdapter;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.ExtendedViewPager;
import com.instanceit.alhadafpos.Utility.LabelMaster;
import com.instanceit.alhadafpos.Utility.Utility;

import java.util.ArrayList;


public class RangeRelaseListAdapter extends RecyclerView.Adapter<RangeRelaseListAdapter.ViewHolder> {

    Context context;
    ArrayList<RangeRelease> rangeReleaseArrayList;
    OnclickListener onclickListener;

    //variable
    MainActivity mainActivity;

    public RangeRelaseListAdapter(Context context, ArrayList<RangeRelease> rangeReleaseArrayList, OnclickListener onclickListener) {
        this.context = context;
        this.rangeReleaseArrayList = rangeReleaseArrayList;
        this.onclickListener = onclickListener;
        mainActivity = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_order_item_detail_row, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tv_name.setText(rangeReleaseArrayList.get(position).getPersonname());
        holder.tv_range.setText(rangeReleaseArrayList.get(position).getRangename());
        holder.tv_lane.setText(rangeReleaseArrayList.get(position).getLanename());
        holder.tv_date.setText(rangeReleaseArrayList.get(position).getDate());
        holder.tv_status.setText(rangeReleaseArrayList.get(position).getReleasestatus());

        if (rangeReleaseArrayList.get(position).getIsreleased().equals("0")) {
            holder.btn_release.setVisibility(View.VISIBLE);
        } else {
            holder.btn_release.setVisibility(View.GONE);
        }

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(2, Color.parseColor(rangeReleaseArrayList.get(position).getReleasestatuscolor()));
        holder.tv_status.setBackgroundDrawable(gd);

        holder.iv_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<FullImage> fullImageArrayList = new ArrayList<>();
                FullImage fullImage = new FullImage();
                fullImage.setImg(rangeReleaseArrayList.get(holder.getAdapterPosition()).getProfileimg());
                fullImage.setId("1");
                fullImage.setName(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_).getLabel());
                fullImageArrayList.add(fullImage);
                openFullImageDialog(fullImageArrayList, 0);
            }
        });

        if (rangeReleaseArrayList.get(holder.getAdapterPosition()).getIsitemdetail() == 1) {
            holder.btn_returnable_item.setVisibility(View.VISIBLE);
        } else {
            holder.btn_returnable_item.setVisibility(GONE);
        }


        Glide.with(context).asBitmap().load(rangeReleaseArrayList.get(position).getProfileimg()).into(holder.iv_profile_image);

        holder.btn_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickListener.onClick(rangeReleaseArrayList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });

        holder.btn_returnable_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReturnableItemDialog(rangeReleaseArrayList.get(holder.getAdapterPosition()).getItemdetail());
            }
        });
    }


    @Override
    public int getItemCount() {
        return rangeReleaseArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_range, tv_lane, tv_date, tv_status, tv_lbl_name, tv_lbl_assigned_range, tv_lbl_assigned_lane;
        ImageView iv_profile_image;
        Button btn_release, btn_returnable_item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_range = itemView.findViewById(R.id.tv_range);
            tv_lane = itemView.findViewById(R.id.tv_lane);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_status = itemView.findViewById(R.id.tv_status);
            btn_release = itemView.findViewById(R.id.btn_release);
            iv_profile_image = itemView.findViewById(R.id.iv_profile_image);
            tv_lbl_name = itemView.findViewById(R.id.tv_lbl_name);
            tv_lbl_assigned_range = itemView.findViewById(R.id.tv_lbl_assigned_range);
            tv_lbl_assigned_lane = itemView.findViewById(R.id.tv_lbl_assigned_lane);
            btn_returnable_item = itemView.findViewById(R.id.btn_returnable_item);

            try {
                tv_lbl_name.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_CART_TV_NAME).getLabel());
                tv_lbl_assigned_range.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_RANGE_ASSIGNED_RANGE).getLabel());
                tv_lbl_assigned_lane.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_RANGE_ASSIGNED_LANE).getLabel());
                btn_release.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_RANGE_RELEASE).getLabel());
                btn_returnable_item.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_RETURNABLE).getLabel());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface OnclickListener {
        void onClick(RangeRelease model);
    }


    //<editor-fold desc=" open member profile image full view">
    Dialog fullImageViewDialog = null;
    FullImageAdapter adapter;
    ExtendedViewPager viewPager;
    TextView tv_item_desc_title;
    ImageView iv_item_desc_back, iv_close_dlg;

    private void openFullImageDialog(ArrayList<FullImage> fullImageArrayList, int position) {
        if (fullImageViewDialog == null) {
            fullImageViewDialog = new Dialog(context, R.style.fullScreeDialog);
            fullImageViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            fullImageViewDialog.setContentView(R.layout.dialog_full_image_view);
            Window window = fullImageViewDialog.getWindow();
            window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            fullImageViewDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            fullImageViewDialog.show();
        }

        viewPager = fullImageViewDialog.findViewById(R.id.viewPager_full);
        tv_item_desc_title = fullImageViewDialog.findViewById(R.id.tv_toolbar_title);
        iv_item_desc_back = fullImageViewDialog.findViewById(R.id.iv_back);
        iv_close_dlg = fullImageViewDialog.findViewById(R.id.iv_close_dlg);
        adapter = new FullImageAdapter(context, fullImageArrayList);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, true);
        tv_item_desc_title.setText(fullImageArrayList.get(position).getName());
        adapter.notifyDataSetChanged();

        try {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(final int position) {
                    try {
                        tv_item_desc_title.setText(fullImageArrayList.get(position).getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPageScrollStateChanged(final int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        iv_item_desc_back.setVisibility(GONE);

        iv_close_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullImageViewDialog.dismiss();
                fullImageViewDialog = null;
            }
        });

        fullImageViewDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    fullImageViewDialog.dismiss();
                    fullImageViewDialog = null;
                }
                return false;
            }
        });
    }
    //</editor-fold>


    BottomSheetDialog bottomsheetDialog = null;
    LinearLayout ln_btn;

    private void ReturnableItemDialog(ArrayList<Storeinstructiondatum> itemdetailArrayList) {

        if (bottomsheetDialog != null && bottomsheetDialog.isShowing()) return;

        {
            bottomsheetDialog = new BottomSheetDialog(mainActivity);
            bottomsheetDialog.setContentView(R.layout.dialog_instruction_selection);
            FrameLayout bottomSheet = bottomsheetDialog.findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomsheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            bottomsheetDialog.setCancelable(true);
            bottomsheetDialog.show();
        }

        ImageView iv_close_dlg;
        RecyclerView rv_instruction;
        TextView tv_dlg_nodata, tv_btn_dlg_done, tv_lbl_instruction_title;

        rv_instruction = bottomsheetDialog.findViewById(R.id.rv_instruction);
        iv_close_dlg = bottomsheetDialog.findViewById(R.id.iv_close_dlg);
        tv_dlg_nodata = bottomsheetDialog.findViewById(R.id.tv_dlg_nodata);
        tv_btn_dlg_done = bottomsheetDialog.findViewById(R.id.tv_btn_dlg_done);
        tv_lbl_instruction_title = bottomsheetDialog.findViewById(R.id.tv_lbl_instruction_title);
        ln_btn = bottomsheetDialog.findViewById(R.id.ln_btn);

        try {
            tv_lbl_instruction_title.setText(Utility.languageLabel(mainActivity, LabelMaster.LBL_DLG_TITLE_RETURNABLE).getLabel());
        } catch (Exception e) {
            e.printStackTrace();
        }


        ln_btn.setVisibility(GONE);

        rv_instruction.setLayoutManager(new LinearLayoutManager(mainActivity));


        iv_close_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomsheetDialog.dismiss();
            }
        });

        InstructionListAdapter instructionListAdapter = new InstructionListAdapter(context, itemdetailArrayList);
        rv_instruction.setAdapter(instructionListAdapter);


        bottomsheetDialog.setOnKeyListener(new Dialog.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    bottomsheetDialog.dismiss();
                }
                return false;
            }
        });

    }

}

