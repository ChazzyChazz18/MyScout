package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TuScout.SportsMedia.Adapters.HomeSliderAdapter;
import com.TuScout.SportsMedia.Adapters.VideosAdapter;
import com.TuScout.SportsMedia.Managers.PopupManager;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.TuScoutApiService.TuScoutApiService;
import com.TuScout.SportsMedia.TuScoutApiService.VideosRespuesta;
import com.TuScout.SportsMedia.Utilities.Constants;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private LinearLayout dotLayout;

    private VideosAdapter videosAdapter;

    //private Button filterBtn;
    private LinearLayout filterPanel;

    //Filter panel Btns
    private LinearLayout filterCancelBtn;
    private TextView filterCancelTxt;

    private LinearLayout filterDateBtn;
    private TextView filterDateTxt;

    private LinearLayout filterCertificateBtn;
    private TextView filterCertificateTxt;

    private LinearLayout filterFootballBtn;
    private TextView filterFootballTxt;
    private ImageView filterFootballImg;

    private LinearLayout filterBaseballBtn;
    private TextView filterBaseballTxt;
    private ImageView filterBaseballImg;

    private LinearLayout filterBasketballBtn;
    private TextView filterBasketballTxt;
    private ImageView filterBasketballImg;

    private LinearLayout filterMartialArtsBtn;
    private TextView filterMartialArtsTxt;
    private ImageView filterMartialArtsImg;

    private LinearLayout filterWeightBtn;
    private TextView filterWeightTxt;

    private LinearLayout filterHeightBtn;
    private TextView filterHeightTxt;

    private LinearLayout filterAgeBtn;
    private TextView filterAgeTxt;

    private LinearLayout filterCountryBtn;
    private TextView filterCountryTxt;

    private boolean isPaginizanle = false;

    //Paginization
    private int actualPage;
    private boolean isReadyToUpdate;

    //ImageView[] dots;

    //HomeSliderAdapter homeSliderAdapter;

    //private int currentPage;

    //VideosAdapter videosAdapter;

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            if(getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if(viewPager.getCurrentItem() == 0)
                        viewPager.setCurrentItem(1);
                    else if(viewPager.getCurrentItem() == 1)
                        viewPager.setCurrentItem(2);
                    else
                        viewPager.setCurrentItem(0);
                });
            }
        }
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            //Toast.makeText(MyActivity.this, "C'Mom no hands!", Toast.LENGTH_SHORT).show();
            filterPanel.setVisibility(View.GONE);

            if(getContext() != null) {
                setFilterBtnColorsToDefault(filterDateBtn, filterDateTxt);
                setFilterBtnColorsToDefault(filterCertificateBtn, filterCertificateTxt);
                setFilterBtnColorsToDefault(filterFootballBtn, filterFootballTxt, filterFootballImg);
                setFilterBtnColorsToDefault(filterBaseballBtn, filterBaseballTxt, filterBaseballImg);
                setFilterBtnColorsToDefault(filterBasketballBtn, filterBasketballTxt, filterBasketballImg);
                setFilterBtnColorsToDefault(filterMartialArtsBtn, filterMartialArtsTxt, filterMartialArtsImg);
                setFilterBtnColorsToDefault(filterWeightBtn, filterWeightTxt);
                setFilterBtnColorsToDefault(filterHeightBtn, filterHeightTxt);
                setFilterBtnColorsToDefault(filterAgeBtn, filterAgeTxt);
                setFilterBtnColorsToDefault(filterCountryBtn, filterCountryTxt);
                setFilterBtnColorsToDefault(filterCancelBtn, filterCancelTxt);
            }
        }
    };

    private void setFilterBtnColorsToDefault (LinearLayout btn, TextView txt) {
        if(getContext() != null) {
            txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }
    }

    private void setFilterBtnColorsToDefault (LinearLayout btn, TextView txt, ImageView img) {
        if(getContext() != null) {
            txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            img.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView;

        viewPager = view.findViewById(R.id.home_auto_carrousel);
        dotLayout = view.findViewById(R.id.home_carrousel_dots);

        Button filterBtn = view.findViewById(R.id.filter_btn);
        filterPanel = view.findViewById(R.id.filter_menu);

        filterDateBtn = view.findViewById(R.id.filter_date_btn);
        filterDateTxt = view.findViewById(R.id.filter_date_txt);

        filterCertificateBtn = view.findViewById(R.id.filter_certificate_btn);
        filterCertificateTxt = view.findViewById(R.id.filter_certificate_txt);

        filterFootballBtn = view.findViewById(R.id.filter_football_btn);
        filterFootballTxt = view.findViewById(R.id.filter_football_txt);
        filterFootballImg = view.findViewById(R.id.filter_football_img);

        filterBaseballBtn = view.findViewById(R.id.filter_baseball_btn);
        filterBaseballTxt = view.findViewById(R.id.filter_baseball_txt);
        filterBaseballImg = view.findViewById(R.id.filter_baseball_img);

        filterBasketballBtn = view.findViewById(R.id.filter_basketball_btn);
        filterBasketballTxt = view.findViewById(R.id.filter_basketball_txt);
        filterBasketballImg = view.findViewById(R.id.filter_basketball_img);

        filterMartialArtsBtn = view.findViewById(R.id.filter_martial_arts_btn);
        filterMartialArtsTxt = view.findViewById(R.id.filter_martial_arts_txt);
        filterMartialArtsImg = view.findViewById(R.id.filter_martial_arts_img);

        filterWeightBtn = view.findViewById(R.id.filter_weight_btn);
        filterWeightTxt = view.findViewById(R.id.filter_weight_txt);

        filterHeightBtn = view.findViewById(R.id.filter_height_btn);
        filterHeightTxt = view.findViewById(R.id.filter_height_txt);

        filterAgeBtn = view.findViewById(R.id.filter_age_btn);
        filterAgeTxt = view.findViewById(R.id.filter_age_txt);

        filterCountryBtn = view.findViewById(R.id.filter_country_btn);
        filterCountryTxt = view.findViewById(R.id.filter_country_txt);

        filterCancelBtn = view.findViewById(R.id.filter_cancel_btn);
        filterCancelTxt = view.findViewById(R.id.filter_cancel_txt);

        HomeSliderAdapter homeSliderAdapter = new HomeSliderAdapter(getContext());

        viewPager.setAdapter(homeSliderAdapter);

        addDotsIndicator(0);

        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(viewListener);

        recyclerView = view.findViewById(R.id.home_recyclerview);
        videosAdapter = new VideosAdapter(getContext());
        recyclerView.setAdapter(videosAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),
                Constants.HOME_SLIDER_CHANGE_INTERVAL,
                Constants.HOME_SLIDER_CHANGE_INTERVAL * 2);

        //loadRecyclerviewData ("Basketball");
        isReadyToUpdate = true;
        actualPage = 1;
        loadRecyclerviewAllData(actualPage);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(isPaginizanle) {

                    if (dy > 0) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                        if (isReadyToUpdate) {
                            if ((visibleItemCount + pastVisibleItems) >= (totalItemCount - 5)) {
                                //circle2.setVisibility(View.VISIBLE);
                                Log.i(TAG, "We came to the end.");
                                isReadyToUpdate = false;
                                actualPage++;
                                loadRecyclerviewAllData(actualPage);
                            }
                        }
                    }

                }

            }
        });

        filterBtn.setOnClickListener(v -> {
            if(filterPanel.getVisibility() == View.GONE) {
                filterPanel.setVisibility(View.VISIBLE);
                filterPanel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_filter));
            }
            else {
                filterPanel.setVisibility(View.GONE);
                filterPanel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_filter));
            }
        });

        filterDateBtn.setOnClickListener(v -> filterPressedBtnAnim(filterDateBtn, filterDateTxt));
        filterCertificateBtn.setOnClickListener(v -> filterPressedBtnAnim(filterCertificateBtn, filterCertificateTxt));
        filterFootballBtn.setOnClickListener(v -> filterPressedBtnAnim(filterFootballBtn, filterFootballTxt, filterFootballImg));
        filterBaseballBtn.setOnClickListener(v -> filterPressedBtnAnim(filterBaseballBtn, filterBaseballTxt, filterBaseballImg));
        filterBasketballBtn.setOnClickListener(v -> filterPressedBtnAnim(filterBasketballBtn, filterBasketballTxt, filterBasketballImg));
        filterMartialArtsBtn.setOnClickListener(v -> filterPressedBtnAnim(filterMartialArtsBtn, filterMartialArtsTxt, filterMartialArtsImg));
        filterWeightBtn.setOnClickListener(v -> filterPressedBtnAnim(filterWeightBtn, filterWeightTxt));
        filterHeightBtn.setOnClickListener(v -> filterPressedBtnAnim(filterHeightBtn, filterHeightTxt));
        filterAgeBtn.setOnClickListener(v -> filterPressedBtnAnim(filterAgeBtn, filterAgeTxt));
        filterCountryBtn.setOnClickListener(v -> filterPressedBtnAnim(filterCountryBtn, filterCountryTxt));
        filterCancelBtn.setOnClickListener(v -> filterPressedBtnAnim(filterCancelBtn, filterCancelTxt));

        return view;
    }

    private void filterPressedBtnAnim (LinearLayout btn, TextView txt) {
        if(getContext() != null) {
            txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        if(btn.equals(filterDateBtn)) {
            //loadRecyclerviewSearchData ("Football");
            //Toast.makeText(getContext(), "Filtro: Fecha", Toast.LENGTH_SHORT).show();
            isPaginizanle = false;
        }
        else if(btn.equals(filterCertificateBtn)) {
            isPaginizanle = false;
            //loadRecyclerviewSearchData ("Football");
            //Toast.makeText(getContext(), "Filtro: Certificado", Toast.LENGTH_SHORT).show();
        }
        else if(btn.equals(filterWeightBtn)) {
            isPaginizanle = false;
            //loadRecyclerviewSearchData ("Baseball");
            //Toast.makeText(getContext(), "Filtro: Peso", Toast.LENGTH_SHORT).show();
            PopupManager.getInstance().filterSelectionPopup(getContext(), Constants.VIDEO_PESO_FILTER, videosAdapter);
        }
        else if(btn.equals(filterHeightBtn)) {
            isPaginizanle = false;
            //loadRecyclerviewSearchData ("Baseball");
            //Toast.makeText(getContext(), "Filtro: Altura", Toast.LENGTH_SHORT).show();
            PopupManager.getInstance().filterSelectionPopup(getContext(), Constants.VIDEO_ALTURA_FILTER, videosAdapter);
        }
        else if(btn.equals(filterAgeBtn)) {
            isPaginizanle = false;
            //loadRecyclerviewSearchData ("Basketball");
            //Toast.makeText(getContext(), "Filtro: Edad", Toast.LENGTH_SHORT).show();
            PopupManager.getInstance().filterSelectionPopup(getContext(), Constants.VIDEO_EDAD_FILTER, videosAdapter);
        }
        else if(btn.equals(filterCountryBtn)) {
            isPaginizanle = false;
            //loadRecyclerviewSearchData ("Basketball");
            //Toast.makeText(getContext(), "Filtro: Pais", Toast.LENGTH_SHORT).show();
            PopupManager.getInstance().filterSelectionPopup(getContext(), Constants.VIDEO_PAIS_FILTER, videosAdapter);
        }else {
            Toast.makeText(getContext(), "Filtro: Limpiado", Toast.LENGTH_SHORT).show();
            actualPage = 1;
            loadRecyclerviewAllData(actualPage);
        }

        filterPanel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_filter));

        handler.postDelayed(runnable, Constants.HOME_FILTER_FADE_OUT_TIME);
    }

    private void filterPressedBtnAnim (LinearLayout btn, TextView txt, ImageView img) {
        if(getContext() != null) {
            txt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            btn.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            img.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
        }

        if(btn.equals(filterFootballBtn)) {
            loadRecyclerviewSearchData (Constants.VIDEO_FOOTBALL_CATEGORY, Constants.VIDEO_CATEGORY_FILTER);
            Toast.makeText(getContext(), "Filtro: Football", Toast.LENGTH_SHORT).show();
        }
        else if(btn.equals(filterBaseballBtn)) {
            loadRecyclerviewSearchData (Constants.VIDEO_BASEBALL_CATEGORY, Constants.VIDEO_CATEGORY_FILTER);
            Toast.makeText(getContext(), "Filtro: Baseball", Toast.LENGTH_SHORT).show();
        }
        else if(btn.equals(filterBasketballBtn)) {
            loadRecyclerviewSearchData (Constants.VIDEO_BASKETBALL_CATEGORY, Constants.VIDEO_CATEGORY_FILTER);
            Toast.makeText(getContext(), "Filtro: Basketball", Toast.LENGTH_SHORT).show();
        }
        else if(btn.equals(filterMartialArtsBtn)) {
            loadRecyclerviewSearchData (Constants.VIDEO_MARTIAL_ARTS_CATEGORY, Constants.VIDEO_CATEGORY_FILTER);
            Toast.makeText(getContext(), "Filtro: Artes Marciales Mixta", Toast.LENGTH_SHORT).show();
        }

        filterPanel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_filter));

        handler.postDelayed(runnable, Constants.HOME_FILTER_FADE_OUT_TIME);
    }

    private void loadRecyclerviewSearchData (String value, String category) {

        isPaginizanle = false;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                //.client(client)
                .build();

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<VideosRespuesta> videosRespuestaCall = service.obtainSearch(value, category);

        videosRespuestaCall.enqueue(new Callback<VideosRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<VideosRespuesta> call,@NonNull Response<VideosRespuesta> response) {
                if (response.isSuccessful()) {

                    VideosRespuesta videosRespuesta = response.body();

                    if (videosRespuesta != null) {
                        ArrayList<Videos> listaVideos = videosRespuesta.getData();
                        //listSongAdapter.addSongList(listaCanciones, circle);
                        videosAdapter.clearVideoList();

                        videosAdapter.addVideoList(listaVideos);
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideosRespuesta> call,@NonNull Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    private void loadRecyclerviewAllData (int page) {

        isPaginizanle = true;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                //.client(client)
                .build();

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<VideosRespuesta> videosRespuestaCall = service.obtainAllApiVideos(page);

        videosRespuestaCall.enqueue(new Callback<VideosRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<VideosRespuesta> call,@NonNull Response<VideosRespuesta> response) {
                isReadyToUpdate = true;
                if (response.isSuccessful()) {

                    VideosRespuesta videosRespuesta = response.body();

                    if (videosRespuesta != null) {
                        ArrayList<Videos> listaVideos = videosRespuesta.getData();
                        //listSongAdapter.addSongList(listaCanciones, circle);
                        //videosAdapter.clearVideoList();

                        if(actualPage > 1)
                            Toast.makeText(getContext(), "Nueva pagina cargada", Toast.LENGTH_SHORT).show();
                        else
                            videosAdapter.clearVideoList();

                        videosAdapter.addVideoList(listaVideos);
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideosRespuesta> call,@NonNull Throwable t) {
                isReadyToUpdate = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    private void addDotsIndicator (int position) {
        ImageView[] dots = new ImageView[3];
        dotLayout.removeAllViews();

        //currentPage = position;

        for (int i = 0; i < dots.length; i++){
            dots[i] = new ImageView(getContext());
            dots[i].setImageResource(R.drawable.border);
            if(getContext() != null)
                dots[i].setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));

            LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(20, 20);
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
