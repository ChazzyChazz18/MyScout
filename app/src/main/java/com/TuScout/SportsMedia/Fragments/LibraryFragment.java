package com.TuScout.SportsMedia.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Adapters.DeviceVideosAdapter;
import com.TuScout.SportsMedia.Utilities.ItemOffsetDecoration;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.Utilities.Constants;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    private ArrayList<Videos> deviceVideoList;

    private DeviceVideosAdapter deviceVideosAdapter;

    private int actualVideoInArrayPos;

    private LinearLayout nextBtn;

    private LinearLayout closeBtn;

    public ArrayList<Videos> getDeviceVideoList(){return  deviceVideoList;}

    public int getActualVideoInArrayPos() {return actualVideoInArrayPos;}
    public void setActualVideoInArrayPos(int actualVideoInArrayPos) {this.actualVideoInArrayPos = actualVideoInArrayPos;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        closeBtn = view.findViewById(R.id.library_close_btn);
        closeBtn.setOnClickListener(v -> {
            if(getActivity() != null)
                getActivity().onBackPressed();
        });

        nextBtn = view.findViewById(R.id.library_next_btn);
        nextBtn.setOnClickListener(v -> {
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

        RecyclerView recyclerView;

        recyclerView = view.findViewById(R.id.video_library_recyclerview);
        deviceVideosAdapter = new DeviceVideosAdapter(getActivity(), this);
        recyclerView.setAdapter(deviceVideosAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ItemOffsetDecoration itemDecoration;
        if(getContext() != null) {
            itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.item_offset);
            recyclerView.addItemDecoration(itemDecoration);
        }

        return view;
    }

    void loadVideoListData () {

        if(getContext() != null) {

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if(getActivity() != null) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                       requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CODE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }
            } else {
                getVideoList();
            }
        }

    }

    private void getVideoList() {
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA};
        Cursor cursor;
        deviceVideoList = new ArrayList<>();
        if(getContext() != null) {
            cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
            ArrayList<String> pathArrList = new ArrayList<>();
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    pathArrList.add(cursor.getString(0));
                    deviceVideoList.add(new Videos());
                }
                cursor.close();
            }

            for (int i = 0; i < pathArrList.size(); i++){
                deviceVideoList.get(i).setVideoURL(pathArrList.get(i));
            }

            deviceVideosAdapter.clearVideoList();

            deviceVideosAdapter.addVideoList(deviceVideoList);

            Log.e("device videos No.",""+deviceVideoList.size());
            Log.e("all path",pathArrList.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == Constants.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE_CODE){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

                getVideoList();

            } /*else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }*/
        }
    }

}
