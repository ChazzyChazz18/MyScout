package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.R;

public class BillingFragment extends Fragment {

    private LinearLayout backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing, container, false);

        backBtn = view.findViewById(R.id.back_from_billing);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null)
                    ((MainActivity)getActivity()).showSelectedFragment(((MainActivity)getActivity()).getCongfigFragment());
            }
        });

        return view;
    }
}
