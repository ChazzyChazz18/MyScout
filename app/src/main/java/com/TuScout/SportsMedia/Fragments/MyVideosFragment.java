package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Adapters.MyVideosAdapter;
import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.Models.VideosID;
import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.TuScoutApiService.IDRespuesta;
import com.TuScout.SportsMedia.TuScoutApiService.TuScoutApiService;
import com.TuScout.SportsMedia.TuScoutApiService.VideosRespuesta;
import com.TuScout.SportsMedia.Utilities.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyVideosFragment extends Fragment {

    private MyVideosAdapter videosAdapter;

    private boolean videosLoaded = false;

    //private ArrayList<Videos> userVideos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_videos, container, false);

        LinearLayout addVideoBtn = view.findViewById(R.id.add_video_btn);
        addVideoBtn.setOnClickListener(v -> {

            if(!videosLoaded) {
                FragmentManager fm = getFragmentManager();
                LibraryFragment fragm;
                if (fm != null) {
                    fragm = (LibraryFragment) fm.findFragmentByTag("7");
                    if (fragm != null)
                        fragm.loadVideoListData();
                }
                videosLoaded = true;
            }

            FragmentManager fm = getFragmentManager();
            AddVideoFragment fragm;
            if (fm != null) {
                fragm = (AddVideoFragment) fm.findFragmentByTag("6");
                if (fragm != null) {
                    fragm.showSelectedFragment(fragm.getLibraryFragment());
                    fragm.getBottomNavigationView().setSelectedItemId(R.id.nav_library);
                }
            }

            //Toast.makeText(getContext(), "Add a video", Toast.LENGTH_SHORT).show();
            if(getActivity() != null)
                ((MainActivity)getActivity()).showSelectedFragment(((MainActivity)getActivity()).getAddVideoFragment());
        });

        RecyclerView recyclerView = view.findViewById(R.id.my_videos_recyclerview);
        videosAdapter = new MyVideosAdapter(getContext());
        recyclerView.setAdapter(videosAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        loadUserVideosIDList(1);

        return  view;
    }

    private void loadUserVideosIDList(int userID) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<IDRespuesta> videosIDRespuestaCall = service.obtainUserVideoIDList(userID);

        videosIDRespuestaCall.enqueue(new Callback<IDRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<IDRespuesta> call,@NonNull Response<IDRespuesta> response) {
                if (response.isSuccessful()) {

                    IDRespuesta videosIDRespuesta = response.body();

                    if (videosIDRespuesta != null) {
                        ArrayList<VideosID> listaVideosID = videosIDRespuesta.getData();
                        //listSongAdapter.addSongList(listaCanciones, circle);
                        //videosAdapter.clearVideoList();

                        //Log.e(TAG, " userVideoList: " + videosIDRespuesta.getData());

                        //videosAdapter.addVideoList(listaVideos);

                        for (int i = 0; i < listaVideosID.size(); i++){
                            VideosID v = listaVideosID.get(i);
                            //Log.i(TAG, " VideoID: " + v.getVideoID());
                            loadUserVideosList(v.getVideoID());
                        }
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<IDRespuesta> call,@NonNull Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }


    private void loadUserVideosList (int videoID) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<VideosRespuesta> videosRespuestaCall = service.obtainUserVideoList(videoID);

        videosRespuestaCall.enqueue(new Callback<VideosRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<VideosRespuesta> call,@NonNull Response<VideosRespuesta> response) {
                if (response.isSuccessful()) {

                    VideosRespuesta videosRespuesta = response.body();

                    if (videosRespuesta != null) {
                        ArrayList<Videos> listaVideos = videosRespuesta.getData();
                        //listSongAdapter.addSongList(listaCanciones, circle);
                        //videosAdapter.clearVideoList();

                        videosAdapter.addVideoList(listaVideos);

                        /*for (int i = 0; i < listaVideos.size(); i++){
                            Videos v = listaVideos.get(i);
                            Log.i(TAG, " Video: " + v.getNombre());
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

}
