package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.TuScout.SportsMedia.Adapters.ScoutTabAdapter;
import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.SessionManager;
import com.TuScout.SportsMedia.Utilities.Constants;
import com.TuScout.SportsMedia.Utilities.GlideCircleWithBorder;
import com.TuScout.SportsMedia.Utilities.MainHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;

public class ScoutFragment extends Fragment {

    private ScoutTabAdapter adapter;

    private ImageView profileIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scout, container, false);

        ViewPager viewPager = view.findViewById(R.id.tuscout_view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tuscout_tab_layout);

        LinearLayout settingsBtn = view.findViewById(R.id.tuscout_settings_btn);

        profileIcon = view.findViewById(R.id.tuscout_profile_picture);

        if (getActivity() != null)
            adapter = new ScoutTabAdapter(getActivity().getSupportFragmentManager());

        adapter.addFragment(new MyVideosFragment(), "Mis videos");
        adapter.addFragment(new MyMessagesFragment(), "Mis mensajes");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        settingsBtn.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).showSelectedFragment(((MainActivity) getActivity()).getCongfigFragment());
                MainHelper.getInstance().settingsFrom = Constants.SETTINGS_FROM_TUSCOUT_FRAGMENT;
            }
        });

        return view;
    }

    public void setProfileImage() {
        if (getContext() != null) {
            if (SessionManager.loggedUser.getImagenPerfil() != null &&
                    !SessionManager.loggedUser.getImagenPerfil().equals("")) {
                Glide.with(getContext())
                        .load(SessionManager.loggedUser.getImagenPerfil())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .transform(new GlideCircleWithBorder(2, ContextCompat.getColor(getContext(), R.color.colorAccent)))
                        .into(profileIcon);
            } else {
                Glide.with(getContext())
                        .load(R.drawable.profile_test)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .transform(new GlideCircleWithBorder(2, ContextCompat.getColor(getContext(), R.color.colorAccent)))
                        .into(profileIcon);
            }

        }
    }

}
