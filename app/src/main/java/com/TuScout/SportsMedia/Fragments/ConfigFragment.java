package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.Utilities.Constants;
import com.TuScout.SportsMedia.Utilities.MainHelper;

public class ConfigFragment extends Fragment {

    private SwitchCompat notiSwitch;
    private SwitchCompat wifiOnlySwitch;

    private LinearLayout historialBtn;
    private LinearLayout terminosBtn;
    private LinearLayout calidadBtn;
    private LinearLayout facturacionBtn;
    private LinearLayout resenasBtn;
    private LinearLayout condicionesBtn;
    private LinearLayout logoutBtn;

    private LinearLayout backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        notiSwitch = view.findViewById(R.id.notifications_switch);
        notiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                Toast.makeText(getContext(), "Notificaciones On", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Notificaciones Off", Toast.LENGTH_SHORT).show();
        });

        wifiOnlySwitch = view.findViewById(R.id.wifi_only_switch);
        wifiOnlySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                Toast.makeText(getContext(), "Solo Wi-Fi On", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "Solo Wi-Fi Off", Toast.LENGTH_SHORT).show();
        });

        historialBtn = view.findViewById(R.id.historial_btn);
        historialBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Historial", Toast.LENGTH_SHORT).show();
        });

        terminosBtn = view.findViewById(R.id.terminos_btn);
        terminosBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Terminos", Toast.LENGTH_SHORT).show();
        });

        calidadBtn = view.findViewById(R.id.calidad_btn);
        calidadBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Calidad", Toast.LENGTH_SHORT).show();
        });

        facturacionBtn = view.findViewById(R.id.facturacion_btn);
        facturacionBtn.setOnClickListener(v -> {
            if(getActivity() != null)
                ((MainActivity)getActivity()).showSelectedFragment(((MainActivity)getActivity()).getBillingFragment());
            Toast.makeText(getContext(), "Facturacion", Toast.LENGTH_SHORT).show();
        });

        resenasBtn = view.findViewById(R.id.resenas_btn);
        resenasBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Reseñas", Toast.LENGTH_SHORT).show();
        });

        condicionesBtn = view.findViewById(R.id.condiciones_btn);
        condicionesBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Condiciones", Toast.LENGTH_SHORT).show();
        });

        logoutBtn = view.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Deslogueo", Toast.LENGTH_SHORT).show();
        });

        backBtn = view.findViewById(R.id.back_from_config);
        backBtn.setOnClickListener(v -> {
            if(getActivity() != null)
                if(MainHelper.getInstance().settingsFrom == Constants.SETTINGS_FROM_PROFILE_FRAGMENT)
                    ((MainActivity)getActivity()).showSelectedFragment(((MainActivity)getActivity()).getProfileFragment());
                else if(MainHelper.getInstance().settingsFrom == Constants.SETTINGS_FROM_TUSCOUT_FRAGMENT)
                    ((MainActivity)getActivity()).showSelectedFragment(((MainActivity)getActivity()).getScoutFragment());
        });

        return view;
    }
}
