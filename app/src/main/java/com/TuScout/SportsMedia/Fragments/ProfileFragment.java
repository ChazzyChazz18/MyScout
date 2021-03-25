package com.TuScout.SportsMedia.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.TuScout.SportsMedia.LoginActivity;
import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.SessionManager;
import com.TuScout.SportsMedia.TuScoutApiService.LoginRespuesta;
import com.TuScout.SportsMedia.TuScoutApiService.RegisterUpdateRespuesta;
import com.TuScout.SportsMedia.TuScoutApiService.TuScoutApiService;
import com.TuScout.SportsMedia.Utilities.Constants;
import com.TuScout.SportsMedia.Utilities.GlideCircleWithBorder;
import com.TuScout.SportsMedia.Utilities.MainHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private final static String TAG = "ProfileFragment";

    private ImageView profileIcon;
    private ImageView pencilIcon;

    private LinearLayout viewProfileInfo;
    private LinearLayout inputProfileInfo;

    private LinearLayout settingsBtn;
    private LinearLayout save_UpdateBtn;

    private TextView save_UpdateBtnTxt;

    private Button userNameBtn;
    private Button userLastNameBtn;
    private Button userAgeBtn;
    private Button userSexBtn;
    private Button userSportBtn;
    private Button userCountryBtn;
    private Button userWeightBtn;
    private Button userHeightBtn;

    private EditText userNameInput;
    private EditText userLastNameInput;
    private Spinner userAgeInput;
    private Spinner userSexInput;
    private Spinner userSportInput;
    private Spinner userCountryInput;
    private Spinner userWeightInput;
    private Spinner userHeightInput;

    private String userNameValue;
    private String userLastNameValue;
    private String userAgeValue;
    private String userSexValue;
    private String userSportValue;
    private String userCountryValue;
    private String userWeightValue;
    private String userHeightValue;

    private boolean isInfoUpdating;

    private Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileIcon = view.findViewById(R.id.profile_picture);
        pencilIcon = view.findViewById(R.id.profile_picture_pencil);
        settingsBtn = view.findViewById(R.id.profile_settings_btn);

        viewProfileInfo = view.findViewById(R.id.view_profile_info);
        inputProfileInfo = view.findViewById(R.id.input_profile_info);

        save_UpdateBtn = view.findViewById(R.id.profile_save_btn);
        save_UpdateBtnTxt = view.findViewById(R.id.profile_save_btn_txt);

        userNameBtn = view.findViewById(R.id.user_name_btn);
        userLastNameBtn = view.findViewById(R.id.user_last_name_btn);
        userAgeBtn = view.findViewById(R.id.user_age_btn);
        userSexBtn = view.findViewById(R.id.user_sex_btn);
        userSportBtn = view.findViewById(R.id.user_sport_btn);
        userCountryBtn = view.findViewById(R.id.user_country_btn);
        userWeightBtn = view.findViewById(R.id.user_weight_btn);
        userHeightBtn = view.findViewById(R.id.user_height_btn);

        userNameInput = view.findViewById(R.id.user_name_input);
        userLastNameInput = view.findViewById(R.id.user_last_name_input);
        userAgeInput = view.findViewById(R.id.user_age_input);
        userSexInput = view.findViewById(R.id.user_sex_input);
        userSportInput = view.findViewById(R.id.user_sport_input);
        userCountryInput = view.findViewById(R.id.user_country_input);
        userWeightInput = view.findViewById(R.id.user_weight_input);
        userHeightInput = view.findViewById(R.id.user_height_input);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                .build();

        userNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userNameValue = s.toString();
            }
        });
        userLastNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userLastNameValue = s.toString();
            }
        });
        if(getContext() != null) {
            //Age
            ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.user_age_array, android.R.layout.simple_spinner_item);
            ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userAgeInput.setAdapter(ageAdapter);
            userAgeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Toast.makeText(getContext(), "Edad seleccionada: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        userAgeValue = parent.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Sex
            ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.user_sex_array, android.R.layout.simple_spinner_item);
            sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userSexInput.setAdapter(sexAdapter);
            userSexInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Toast.makeText(getContext(), "Sexo seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        userSexValue = parent.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Sport
            ArrayAdapter<CharSequence> sportAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.user_sport_array, android.R.layout.simple_spinner_item);
            sportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userSportInput.setAdapter(sportAdapter);
            userSportInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Toast.makeText(getContext(), "Deporte seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        userSportValue = parent.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Country
            ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.user_country_array, android.R.layout.simple_spinner_item);
            countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userCountryInput.setAdapter(countryAdapter);
            userCountryInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Toast.makeText(getContext(), "Pais seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        userCountryValue = parent.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Weight
            ArrayAdapter<CharSequence> pesoAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.user_weight_array, android.R.layout.simple_spinner_item);
            pesoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userWeightInput.setAdapter(pesoAdapter);
            userWeightInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Toast.makeText(getContext(), "Peso seleccionado: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        userWeightValue = parent.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //Height
            ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.user_height_array, android.R.layout.simple_spinner_item);
            heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            userHeightInput.setAdapter(heightAdapter);
            userHeightInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0) {
                        Toast.makeText(getContext(), "Altura seleccionada: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                        userHeightValue = parent.getItemAtPosition(position).toString();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        isInfoUpdating = false;

        save_UpdateBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() != null) {
                    if (!isInfoUpdating) {
                        save_UpdateBtnTxt.setText(getActivity().getString(R.string.guardar));
                        inputProfileInfo.setVisibility(View.VISIBLE);
                        viewProfileInfo.setVisibility(View.GONE);
                        isInfoUpdating = true;
                    }else {
                        save_UpdateBtnTxt.setText(getActivity().getString(R.string.actualizar));
                        inputProfileInfo.setVisibility(View.GONE);
                        viewProfileInfo.setVisibility(View.VISIBLE);
                        updateUserInfo(SessionManager.loggedUser.getIdusuario(), userNameValue, userLastNameValue,
                                userAgeValue, userSexValue, userSportValue, userCountryValue, userWeightValue, userHeightValue);

                        isInfoUpdating = false;
                    }
                }
            }
        });

        if(getContext() != null) {
            //ProfilePicture
            Glide.with(getContext())
                    .load(R.drawable.profile_test)
                    //.apply(RequestOptions.circleCropTransform()) //Circle the image into the ImageView
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .transform(new GlideCircleWithBorder(2, ContextCompat.getColor(getContext(), R.color.colorPrimary)))
                    .into(profileIcon);

            //Pencil
            Glide.with(getContext())
                    .load(R.drawable.pencil)
                    //.apply(RequestOptions.circleCropTransform()) //Circle the image into the ImageView
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .transform(new GlideCircleWithBorder(1, ContextCompat.getColor(getContext(), R.color.colorPrimary)))
                    .into(pencilIcon);
        }

        settingsBtn.setOnClickListener(v -> {
            if(getActivity() != null) {
                ((MainActivity) getActivity()).showSelectedFragment(((MainActivity) getActivity()).getCongfigFragment());
                MainHelper.getInstance().settingsFrom = Constants.SETTINGS_FROM_PROFILE_FRAGMENT;
            }
        });

        return view;
    }

    public void setUserInfo () {

        if (!SessionManager.loggedUser.getNombre().equals(""))
            userNameBtn.setText(SessionManager.loggedUser.getNombre());

        if (!SessionManager.loggedUser.getAltura().equals(""))
            userLastNameBtn.setText(SessionManager.loggedUser.getAltura());

        if (SessionManager.loggedUser.getEdad() != null)
            userAgeBtn.setText(SessionManager.loggedUser.getEdad());

        if (SessionManager.loggedUser.getSexo() != null)
            userSexBtn.setText(SessionManager.loggedUser.getSexo());

        if (SessionManager.loggedUser.getDeporte() != null)
            userSportBtn.setText(SessionManager.loggedUser.getDeporte());

        if (SessionManager.loggedUser.getPais() != null)
            userCountryBtn.setText(SessionManager.loggedUser.getPais());

        if (!SessionManager.loggedUser.getPeso().equals(""))
            userWeightBtn.setText(SessionManager.loggedUser.getPeso());

        if (!SessionManager.loggedUser.getAltura().equals(""))
            userHeightBtn.setText(SessionManager.loggedUser.getAltura());
    }

    private void updateUserInfo (int userID, String name, String lastName, String age,
                                String sex, String sport, String country, String weight, String height) {
        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<RegisterUpdateRespuesta> loginRespuestaCall = service.actualizarUser(userID, name,
                lastName, age, sex, sport, country,
                weight, height);

        loginRespuestaCall.enqueue(new Callback<RegisterUpdateRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<RegisterUpdateRespuesta> call,@NonNull Response<RegisterUpdateRespuesta> response) {
                if (response.isSuccessful()) {

                    RegisterUpdateRespuesta registerUpdateRespuesta = response.body();

                    if (registerUpdateRespuesta != null) {

                        switch (registerUpdateRespuesta.getData().get(0).getResponse()) {
                            case "Update done Successfully":
                                Toast.makeText(getContext(), "Actualizacion completada", Toast.LENGTH_SHORT).show();
                                loadUserInfo(SessionManager.loggedUser.getIdusuario());
                                break;
                            case "Error":
                                Toast.makeText(getContext(), "Hubo un error, intente de nuevo", Toast.LENGTH_SHORT).show();
                                break;
                            case "0 results":
                                Toast.makeText(getContext(), "No hubo resultados", Toast.LENGTH_SHORT).show();

                                break;
                        }

                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterUpdateRespuesta> call,@NonNull Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    public void loadUserInfo (int userID) {

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<LoginRespuesta> loginRespuestaCall = service.obtainUserInfo(userID);

        loginRespuestaCall.enqueue(new Callback<LoginRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<LoginRespuesta> call,@NonNull Response<LoginRespuesta> response) {
                if (response.isSuccessful()) {

                    LoginRespuesta loginRespuesta = response.body();

                    if (loginRespuesta != null) {
                        //ArrayList<Videos> listaVideos = loginRespuesta.getData();
                        Log.e(TAG, "onResponse: " + loginRespuesta.getData().get(0).getNombre());
                        SessionManager.loggedUser = null;
                        SessionManager.loggedUser = loginRespuesta.getData().get(0);
                        setUserInfo();
                        //SessionManager.isUserLogged = true;
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginRespuesta> call,@NonNull Throwable t) {
                Log.e(TAG, " onFailureUserUpdate: " + t.getMessage());
            }
        });
    }

    public void setProfileImage () {
        if(getContext() != null){
            if(SessionManager.loggedUser.getImagenPerfil() != null &&
                    !SessionManager.loggedUser.getImagenPerfil().equals("")) {
                Glide.with(getContext())
                        .load(SessionManager.loggedUser.getImagenPerfil())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .transform(new GlideCircleWithBorder(2, ContextCompat.getColor(getContext(), R.color.colorAccent)))
                        .into(profileIcon);
            }else {
                Glide.with(getContext())
                        .load(R.drawable.profile_test)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .transform(new GlideCircleWithBorder(2, ContextCompat.getColor(getContext(), R.color.colorAccent)))
                        .into(profileIcon);
            }
        }
    }
}
