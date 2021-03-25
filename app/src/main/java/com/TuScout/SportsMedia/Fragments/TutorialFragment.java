package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.TuScout.SportsMedia.Adapters.TutorialSliderAdapter;
import com.TuScout.SportsMedia.R;

public class TutorialFragment extends Fragment {

    private ImageView[] dots;
    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private LinearLayout backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);

        viewPager = view.findViewById(R.id.tutorial_carrousel);
        dotLayout = view.findViewById(R.id.tutorial_dots);
        backBtn = view.findViewById(R.id.back_from_tutorial);

        TutorialSliderAdapter tutorialSliderAdapter = new TutorialSliderAdapter(getContext());

        viewPager.setAdapter(tutorialSliderAdapter);

        addDotsIndicator(0);

        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(viewListener);

        backBtn.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            AddVideoFragment fragm;
            if (fm != null) {
                fragm = (AddVideoFragment) fm.findFragmentByTag("6");
                if (fragm != null) {
                    RecordFragment fragm2 = (RecordFragment) fm.findFragmentByTag("8");
                    fragm.showSelectedFragment(fragm2);
                    fragm.getBottomNavigationView().setSelectedItemId(R.id.nav_record);
                }
            }
        });

        return view;
    }

    private void addDotsIndicator (int position) {
        dots = new ImageView[4];
        dotLayout.removeAllViews();

        //currentPage = position;

        for (int i = 0; i < dots.length; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.border);
            if(getContext() != null)
                dots[i].setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));

            LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(32, 32);
            layoutParams.leftMargin = 8;
            layoutParams.rightMargin = 8;
            dots[i].setLayoutParams(layoutParams);

            dotLayout.addView(dots[i]);
        }

        dots[position].setImageResource(R.drawable.circle);
    }

    private ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
