package com.TuScout.SportsMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.TuScout.SportsMedia.R;

public class TutorialSliderAdapter extends PagerAdapter {

    private Context context;

    //Arrays
    private int[] slideImages = {
            R.drawable.tut_test_1,
            R.drawable.tut_test_2,
            R.drawable.tut_test_3,
            R.drawable.tut_test_4
    };

    public TutorialSliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.tutorial_slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.tut_slide_img);

        slideImageView.setBackgroundResource(slideImages[position]);

        container.addView(view);

        view.setOnClickListener(v -> {
            //this will log the page number that was click
            Toast.makeText(context, "The page clicked was the No. " + position, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
