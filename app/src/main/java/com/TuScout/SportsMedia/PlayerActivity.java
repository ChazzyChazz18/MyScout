package com.TuScout.SportsMedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.TuScout.SportsMedia.Adapters.VideosAdapter;
import com.TuScout.SportsMedia.Managers.PopupManager;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.TuScoutApiService.TuScoutApiService;
import com.TuScout.SportsMedia.TuScoutApiService.VideosRespuesta;
import com.TuScout.SportsMedia.Utilities.Constants;
import com.TuScout.SportsMedia.Utilities.Utility;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerActivity extends AppCompatActivity {

    private static final String TAG = "PlayerActivity";

    private PlayerView playerView;
    private PlayerView playerViewFullScreen;
    private SimpleExoPlayer simpleExoPlayer;
    private FrameLayout playerNormalScreen;
    private FrameLayout playerFullScreen;
    private boolean mExoPlayerFullscreen;

    private ImageView videoUserProfileImg;

    private ImageView rateVideoBtn;
    private ImageView commentsBtn;
    private ImageView shareBtn;

    private Button reportBtn;

    private VideosAdapter videosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Utility.changeStatusBar(this, R.color.colorPrimary);

        playerView = findViewById(R.id.player_view);
        playerViewFullScreen = findViewById(R.id.player_view_full);

        playerNormalScreen = findViewById(R.id.player_normal_screen_view);
        playerFullScreen = findViewById(R.id.player_full_screen_view);

        videoUserProfileImg = findViewById(R.id.profile_video_icon);

        rateVideoBtn = findViewById(R.id.in_video_star);
        commentsBtn = findViewById(R.id.in_video_comment);
        shareBtn = findViewById(R.id.in_video_share);

        reportBtn = findViewById(R.id.report_btn);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);

        playerView.setPlayer(simpleExoPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, getString(R.string.app_name));

        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(getIntent().getStringExtra("videoURL")));

        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);

        ImageButton fullScreenBtn = findViewById(R.id.exo_fullscreen_icon);

        RecyclerView recyclerView;

        recyclerView = findViewById(R.id.player_recyclerview);
        videosAdapter = new VideosAdapter(this);
        recyclerView.setAdapter(videosAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        loadRecyclerviewData ();

        Glide.with(this)
                .load(R.drawable.profile_test_2)
                .apply(RequestOptions.circleCropTransform())
                .into(videoUserProfileImg);

        fullScreenBtn.setOnClickListener(v -> openFullscreenView());

        rateVideoBtn.setOnClickListener(v -> PopupManager.getInstance().startRateVideoPopup(PlayerActivity.this));
        commentsBtn.setOnClickListener(v -> PopupManager.getInstance().startViewCommentsPopup(PlayerActivity.this));
        shareBtn.setOnClickListener(v -> PopupManager.getInstance().startShareVideoPopup(PlayerActivity.this));

        reportBtn.setOnClickListener(v -> PopupManager.getInstance().startReportPopup(PlayerActivity.this));

    }

    private void loadRecyclerviewData () {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<VideosRespuesta> videosRespuestaCall = service.obtainAllApiVideos(1);

        videosRespuestaCall.enqueue(new Callback<VideosRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<VideosRespuesta> call,@NonNull Response<VideosRespuesta> response) {
                if (response.isSuccessful()) {

                    VideosRespuesta videosRespuesta = response.body();

                    if (videosRespuesta != null) {
                        ArrayList<Videos> listaVideos = videosRespuesta.getData();
                        videosAdapter.addVideoList(listaVideos);

                        /*for (int i = 0; i < listaVideos.size(); i++){
                            Videos v = listaVideos.get(i);
                            Log.i(TAG, " Cancion: " + v.getNombre());
                        }*/
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

    private void openFullscreenView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        playerViewFullScreen = findViewById(R.id.player_view_full);

        playerView.setVisibility(View.GONE);
        playerNormalScreen.setVisibility(View.GONE);
        playerFullScreen.setVisibility(View.VISIBLE);
        playerViewFullScreen.setPlayer(simpleExoPlayer);
        playerView = null;

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mExoPlayerFullscreen = true;
    }

    private void closeFullscreenView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        playerView = findViewById(R.id.player_view);

        playerNormalScreen.setVisibility(View.VISIBLE);
        playerView.setVisibility(View.VISIBLE);
        playerView.setPlayer(simpleExoPlayer);
        playerViewFullScreen = null;
        playerFullScreen.setVisibility(View.GONE);

        View decorView = getWindow().getDecorView();
        // Show the status bar.
        decorView.setSystemUiVisibility(0);

        mExoPlayerFullscreen = false;
    }

    @Override
    public void onStop() {
        super.onStop();

        if(simpleExoPlayer != null)
            simpleExoPlayer.seekTo(0);
    }

    @Override
    public void finish() {
        super.finish();

        if(simpleExoPlayer != null) {
            //Views
            playerView = null;
            playerViewFullScreen = null;
            //Player
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }

    }

    @Override
    public void onBackPressed() {
        if(mExoPlayerFullscreen)
            closeFullscreenView();
        else {
            finish();
            super.onBackPressed();
        }
    }
}
