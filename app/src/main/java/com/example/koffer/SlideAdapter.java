package com.example.koffer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        return view == (ConstraintLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_img);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_header);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_img[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
