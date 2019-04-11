package com.example.koffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    //Arrays
    public int[] slide_img = {
        R.drawable.eat_icon,
        R.drawable.sleep_icon,
        R.drawable.code_icon
    };

    public String[] slide_headings = {
            "EAT",
            "SLEEP",
            "CODE"
    };

    public String[] slide_descs = {
            "Lorem ipsum dolor sit amet,\n" +
                    "        consectetur adipiscing elit.\n" +
                    "        Etiam at turpis condimentum,\n" +
                    "        pharetra enim sit amet, consectetur velit.\n" +
                    "        Integer pellentesque nibh varius quam hendrerit,\n" +
                    "        eget placerat orci porta.",
            "Lorem ipsum dolor sit amet,\n" +
                    "        consectetur adipiscing elit.\n" +
                    "        Etiam at turpis condimentum,\n" +
                    "        pharetra enim sit amet, consectetur velit.\n" +
                    "        Integer pellentesque nibh varius quam hendrerit,\n" +
                    "        eget placerat orci porta.",
            "Lorem ipsum dolor sit amet,\n" +
                    "        consectetur adipiscing elit.\n" +
                    "        Etiam at turpis condimentum,\n" +
                    "        pharetra enim sit amet, consectetur velit.\n" +
                    "        Integer pellentesque nibh varius quam hendrerit,\n" +
                    "        eget placerat orci porta."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.id.slide_layout, container, false);
        return view;
    }
}
