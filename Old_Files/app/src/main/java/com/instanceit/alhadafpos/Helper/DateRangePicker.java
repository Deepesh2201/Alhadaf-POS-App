package com.instanceit.alhadafpos.Helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.instanceit.alhadafpos.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class DateRangePicker extends Dialog implements View.OnClickListener, TabLayout.OnTabSelectedListener {
    final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private OnCalenderClickListener onCalenderClickListener;
    private Context context;
    private ViewFlipper viewFlipper;
    private CalendarView startDateCalendarView;
    private CalendarView endDateCalendarView;
    private TextView startDate;
    private TextView endDate;
    private TextView btnNegative;
    private TextView btnPositive;
    private long selectedFromDate;
    private long selectedToDate = 0L;
    private Calendar startDateCal = Calendar.getInstance();
    private Calendar endDateCal = Calendar.getInstance();
    private TabLayout tabLayout;
    private String startDateTitle = "start date";
    private String endDateTitle = "end date";
    private String startDateError = "Please select start date";
    private String endDateError = "Please select end date";

    Animation startAnimation; // = AnimationUtils.loadAnimation(context, R.anim.blink_anim);
    ViewGroup vg;//= (ViewGroup) this.tabLayout.getChildAt(0);
    ViewGroup vgTab0;// = (ViewGroup) vg.getChildAt(1);  // position
    ViewGroup vgTab1;// = (ViewGroup) vg.getChildAt(1);  // position

    public DateRangePicker(@NonNull Context context, OnCalenderClickListener onCalenderClickListener) {
        super(context);
        this.context = context;
        this.onCalenderClickListener = onCalenderClickListener;

        startAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_zoom_out);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.setContentView(R.layout.date_range_picker);

        this.initView();

        startAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_zoom_out);

    }

    private void initView() {
        this.tabLayout = this.findViewById(R.id.drp_tabLayout);
        this.viewFlipper = this.findViewById(R.id.drp_viewFlipper);
        this.startDateCalendarView = this.findViewById(R.id.drp_calStartDate);
        this.endDateCalendarView = this.findViewById(R.id.drp_calEndDate);
        this.startDate = this.findViewById(R.id.drp_tvStartDate);
        this.endDate = this.findViewById(R.id.drp_tvEndDate);
        this.btnNegative = this.findViewById(R.id.drp_btnNegative);
        this.btnPositive = this.findViewById(R.id.drp_btnPositive);
        this.startDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                DateRangePicker.this.startDateCal.set(year, month, dayOfMonth);
                DateRangePicker.this.selectedFromDate = DateRangePicker.this.startDateCal.getTimeInMillis();
                DateRangePicker.this.startDate.setText(DateRangePicker.this.dateFormatter.format(DateRangePicker.this.startDateCal.getTime()));
                DateRangePicker.this.tabLayout.getTabAt(1).select();
            }
        });
        this.endDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                DateRangePicker.this.endDateCal.set(year, month, dayOfMonth);
                DateRangePicker.this.selectedToDate = DateRangePicker.this.endDateCal.getTimeInMillis();
                DateRangePicker.this.endDate.setText(DateRangePicker.this.dateFormatter.format(DateRangePicker.this.endDateCal.getTime()));
            }
        });
        this.tabLayout.addTab(this.tabLayout.newTab().setText(this.startDateTitle), true);
        this.tabLayout.addTab(this.tabLayout.newTab().setText(this.endDateTitle));
        this.btnPositive.setOnClickListener(this);
        this.btnNegative.setOnClickListener(this);
        this.tabLayout.addOnTabSelectedListener(this);

        vg = (ViewGroup) this.tabLayout.getChildAt(0);
        vgTab0 = (ViewGroup) vg.getChildAt(0);  // position
        vgTab1 = (ViewGroup) vg.getChildAt(1);  // position
        vgTab0.startAnimation(startAnimation);

    }

    public void onClick(View view) {
        if (view == this.btnPositive) {
            if (TextUtils.isEmpty(this.startDate.getText().toString())) {
                Snackbar.make(this.startDate, this.startDateError, -1).show();
            } else if (TextUtils.isEmpty(this.endDate.getText().toString())) {
                Snackbar.make(this.endDate, this.endDateError, -1).show();
            } else {
               this.onCalenderClickListener.onDateSelected(this.startDate.getText().toString(), this.endDate.getText().toString());
                this.dismiss();
            }
        } else if (view == this.btnNegative) {
            this.dismiss();
        }


    }

    private void showToDateCalender() {
        this.endDateCalendarView.setMinDate(0L);
        this.endDateCalendarView.setMinDate(this.selectedFromDate);
        if (this.selectedToDate != 0L) {
            this.endDateCalendarView.setDate(this.selectedToDate);
        }

        this.viewFlipper.showNext();
        if (!TextUtils.isEmpty(this.endDate.getText()) && this.endDateCal.before(this.startDateCal)) {
            this.endDate.setText(this.startDate.getText().toString());
        }


        vg = (ViewGroup) this.tabLayout.getChildAt(0);
        vgTab0 = (ViewGroup) vg.getChildAt(0);  // position
        vgTab1 = (ViewGroup) vg.getChildAt(1);  // position
        vgTab1.startAnimation(startAnimation);
        vgTab0.clearAnimation();

    }


    public void setBtnPositiveText(String text) {
        this.btnPositive.setText(text);
    }

    public void setBtnNegativeText(String text) {
        this.btnNegative.setText(text);
    }

    public SimpleDateFormat getDateFormatter() {
        return this.dateFormatter;
    }

    public String getStartDateTitle() {
        return this.startDateTitle;
    }

    public void setStartDateTitle(String startDateTitle) {
        this.startDateTitle = startDateTitle;
    }

    public CalendarView getStartDateCalendarView() {
        return this.startDateCalendarView;
    }

    public CalendarView getEndDateCalendarView() {
        return this.endDateCalendarView;
    }

    public TextView getStartDate() {
        return this.startDate;
    }

    public TextView getEndDate() {
        return this.endDate;
    }

    public TextView getBtnNegative() {
        return this.btnNegative;
    }

    public TextView getBtnPositive() {
        return this.btnPositive;
    }

    public TabLayout getTabLayout() {
        return this.tabLayout;
    }

    public String getEndDateTitle() {
        return this.endDateTitle;
    }

    public void setEndDateTitle(String endDateTitle) {
        this.endDateTitle = endDateTitle;
    }

    public String getStartDateError() {
        return this.startDateError;
    }

    public void setStartDateError(String startDateError) {
        this.startDateError = startDateError;
    }

    public String getEndDateError() {
        return this.endDateError;
    }

    public void setEndDateError(String endDateError) {
        this.endDateError = endDateError;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        vg = (ViewGroup) this.tabLayout.getChildAt(0);
        vgTab0 = (ViewGroup) vg.getChildAt(0);  // position
        vgTab1 = (ViewGroup) vg.getChildAt(1);  // position

        if (tab.getPosition() == 0) {
            this.viewFlipper.showPrevious();

            vgTab0.startAnimation(startAnimation);
            vgTab1.clearAnimation();

        } else {
            this.showToDateCalender();

            vgTab1.startAnimation(startAnimation);
            vgTab0.clearAnimation();

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public interface OnCalenderClickListener {
        void onDateSelected(String var1, String var2);
    }
}

