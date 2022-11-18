package com.instanceit.alhadafpos.Activities.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.instanceit.alhadafpos.Entity.FullImage;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Views.ZoomableIamge.ZoomageView;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class FullImageAdapter extends PagerAdapter {
    Context context;
    private ArrayList<FullImage> itemList = new ArrayList<>();
    LayoutInflater layoutInflater;


    public FullImageAdapter(Context context, ArrayList<FullImage> itemList) {
        this.context = context;
        this.itemList = itemList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.adapter_fullimag_viewpager, container, false);

        try {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.app_logo);
            requestOptions.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

            final ZoomageView imageView = itemView.findViewById(R.id.myZoomageView);

            imageView.setVisibility(View.VISIBLE);

//            Glide.with(context).load(itemList.get(position).getImg()).apply(requestOptions).thumbnail(0.3f).into(imageView);

            Glide.with(context).asBitmap().load(itemList.get(position).getImg()).into(imageView);


            container.addView(itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}