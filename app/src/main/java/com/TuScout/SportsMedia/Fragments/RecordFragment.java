package com.TuScout.SportsMedia.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.TuScout.SportsMedia.R;

public class RecordFragment extends Fragment {

    private ImageView recordBtn;
    private LinearLayout nextBtn;
    private LinearLayout cancelBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, container, false);

        cancelBtn = view.findViewById(R.id.record_cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            if(getActivity() != null)
                getActivity().onBackPressed();
        });

        nextBtn = view.findViewById(R.id.record_next_btn);
        nextBtn.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            AddVideoFragment fragm;
            if (fm != null) {
                fragm = (AddVideoFragment) fm.findFragmentByTag("6");
                if (fragm != null) {
                    TutorialFragment fragm2 = (TutorialFragment) fm.findFragmentByTag("9");
                    fragm.showSelectedFragment(fragm2);
                    fragm.getBottomNavigationView().setSelectedItemId(R.id.nav_tutorial);
                }
            }
        });

        recordBtn = view.findViewById(R.id.record_btn);
        recordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivity(intent);
        });

        return view;
    }

}
