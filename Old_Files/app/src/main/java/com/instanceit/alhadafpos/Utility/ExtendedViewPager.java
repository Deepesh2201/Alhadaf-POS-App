package com.instanceit.alhadafpos.Utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

public class ExtendedViewPager extends ViewPager
{
    public ExtendedViewPager(Context context) {
        super(context);
    }

    public ExtendedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ImageView) {
            return ((ImageView) v).canScrollHorizontally(-dx);
        } else {
            return super.canScroll(v, checkV, dx, x, y);
        }
    }

}