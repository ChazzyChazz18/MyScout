package com.TuScout.SportsMedia.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TuScout.SportsMedia.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;

    //Arrays
    private int[] slideImages = {
            R.drawable.logo_01,
            R.drawable.logo_03,
            R.drawable.logo_05,
            R.drawable.logo_08
    };

    private int[] slideBgs = {
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.colorSlide2,
            R.color.colorSlide3
    };

    public SliderAdapter (Context context) {
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        TextView slideTextView = view.findViewById(R.id.slide_text);
        ImageView slideImageView = view.findViewById(R.id.slide_image);
        LinearLayout slideBg = view.findViewById(R.id.slide_bg);
        LinearLayout slideImageHolder = view.findViewById(R.id.slide_image_holder);

        //Texts Array
        String[] slideTexts = {
                context.getString(R.string.intro_slide_text_1),
                context.getString(R.string.intro_slide_text_2),
                context.getString(R.string.intro_slide_text_3),
                context.getString(R.string.intro_slide_text_4)
        };

        slideTextView.setText(slideTexts[position]);
        slideImageView.setBackgroundResource(slideImages[position]);
        slideBg.setBackgroundColor(ContextCompat.getColor(context, slideBgs[position]));

        if(position == 0){

            int paddingDp = 32;
            float density = context.getResources().getDisplayMetrics().density;
            int paddingPixel = (int)(paddingDp * density);

            slideImageHolder.setPadding(paddingPixel,paddingPixel * 5,paddingPixel,paddingPixel * 5);

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            slideImageView.setLayoutParams(layoutParams);

            slideTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
