package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddVideoFragment extends Fragment {

    FragmentManager fragmentManager;

    Fragment libraryFragment = new LibraryFragment();
    Fragment recordFragment = new RecordFragment();
    Fragment tutorialFragment = new TutorialFragment();

    // Gallery|Record sub-fragment
    Fragment uploadVideoFragment = new UploadVideoFragment();

    Fragment activeFragment = libraryFragment;

    BottomNavigationView bottomNavigationView;

    private boolean uploadVideoFragmentActive;

    public Fragment getUploadVideoFragment () {return uploadVideoFragment;}
    public Fragment getLibraryFragment () {return libraryFragment;}
    public boolean isUploadVideoFragmentActive () {return uploadVideoFragmentActive;}
    public BottomNavigationView getBottomNavigationView () {return bottomNavigationView;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_video, container, false);

        if(getActivity() != null)
            fragmentManager = getActivity().getSupportFragmentManager();

        bottomNavigationView = view.findViewById(R.id.bottom_nav_add_video);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        initializeFragments();

        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {

        switch (menuItem.getItemId()){
            case R.id.nav_library:
                FragmentManager fm = getFragmentManager();
                LibraryFragment fragm;
                if (fm != null) {
                    fragm = (LibraryFragment) fm.findFragmentByTag("7");
                    if (fragm != null)
                        fragm.loadVideoListData();
                }
                showSelectedFragment(libraryFragment);
                break;
            case R.id.nav_record:
                showSelectedFragment(recordFragment);
                break;
            case R.id.nav_tutorial:
                showSelectedFragment(tutorialFragment);
                break;
        }

        return true;
    };

    private void initializeFragments () {
        //We dont hide the topFragment as it is the one selected(we choose it)
        fragmentManager.beginTransaction().add(R.id.fragment_container_add_video, libraryFragment, "7").commit();

        fragmentManager.beginTransaction().add(R.id.fragment_container_add_video, recordFragment, "8").hide(recordFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container_add_video, tutorialFragment, "9").hide(tutorialFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container_add_video, uploadVideoFragment, "10").hide(uploadVideoFragment).commit();}

    public void showSelectedFragment (Fragment fragmentToShow) {
        if(fragmentToShow == uploadVideoFragment){
            FragmentManager fm = getFragmentManager();
            UploadVideoFragment fragm;
            if (fm != null) {
                fragm = (UploadVideoFragment) fm.findFragmentByTag("10");
                if (fragm != null)
                    fragm.setSelectedVideoInfo();
            }
        }

        fragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(fragmentToShow)
                .commit();
        activeFragment = fragmentToShow;

        uploadVideoFragmentActive = activeFragment == uploadVideoFragment;

    }
}
