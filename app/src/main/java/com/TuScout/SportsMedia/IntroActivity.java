package com.TuScout.SportsMedia;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.TuScout.SportsMedia.Adapters.SliderAdapter;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;

    private ImageView[] realDots;

    private Button backBtn;
    private Button nextBtnFinal;
    private ImageView nextBtn;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.intro_carrousel);
        dotLayout = findViewById(R.id.intro_carrousel_dots);

        backBtn = findViewById(R.id.back_button);
        nextBtn = findViewById(R.id.next_button);
        nextBtnFinal = findViewById(R.id.next_button_finish);

        SliderAdapter sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);
        viewPager.setOffscreenPageLimit(3);

        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator (int position) {
        realDots = new ImageView[4];
        dotLayout.removeAllViews();

        for (int i = 0; i < realDots.length; i++){

            realDots[i] = new ImageView(this);
            realDots[i].setImageResource(R.drawable.border);
            realDots[i].setColorFilter(ContextCompat.getColor(IntroActivity.this, R.color.colorAccent));

            LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(48, 48);
            layoutParams.leftMargin = 8;
            layoutParams.rightMargin = 8;
            realDots[i].setLayoutParams(layoutParams);

            dotLayout.addView(realDots[i]);
        }

        if(realDots.length > 0) {
            realDots[position].setImageResource(R.drawable.circle);
            if(position == 0) {
                for (ImageView realDot : realDots) {
                    realDot.setColorFilter(ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary));
                }
            }
        }

        nextBtn.setOnClickListener(v -> viewPager.setCurrentItem(currentPage + 1));

        nextBtnFinal.setOnClickListener(v -> {
            if(currentPage == realDots.length - 1){
                goToMainActivity();
            }
        });

        backBtn.setOnClickListener(v -> {

            if(currentPage != realDots.length - 1 && currentPage != 0){
                goToMainActivity();
                return;
            }

            viewPager.setCurrentItem(currentPage - 1);

        });
    }

    private void goToMainActivity () {
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            currentPage = i;

            if(i == 0){
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.INVISIBLE);
                nextBtn.setVisibility(View.VISIBLE);
                nextBtnFinal.setVisibility(View.GONE);

                nextBtn.setColorFilter(ContextCompat.getColor(IntroActivity.this, R.color.colorPrimary));

            } else if (i == realDots.length - 1){
                backBtn.setEnabled(false);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setVisibility(View.GONE);
                nextBtnFinal.setVisibility(View.VISIBLE);

                nextBtnFinal.setTextColor(ContextCompat.getColor(IntroActivity.this, R.color.colorAccent));

                nextBtnFinal.setText(getString(R.string.ready));
                backBtn.setText("");
            }else {
                backBtn.setEnabled(true);
                backBtn.setVisibility(View.VISIBLE);

                nextBtn.setVisibility(View.VISIBLE);
                nextBtnFinal.setVisibility(View.GONE);

                nextBtn.setColorFilter(ContextCompat.getColor(IntroActivity.this, R.color.colorAccent));

                backBtn.setText(getString(R.string.access));
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
