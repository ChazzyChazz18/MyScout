package com.TuScout.SportsMedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.TuScout.SportsMedia.Models.RegisterAns;
import com.TuScout.SportsMedia.TuScoutApiService.LoginRespuesta;
import com.TuScout.SportsMedia.TuScoutApiService.RegisterRespuesta;
import com.TuScout.SportsMedia.TuScoutApiService.TuScoutApiService;
import com.TuScout.SportsMedia.Utilities.Constants;
import com.TuScout.SportsMedia.Utilities.MainHelper;
import com.TuScout.SportsMedia.Utilities.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    //Login
    private EditText username;
    private EditText password;
    private Button login;
    private Button signUp;
    private TextView forgotPass;
    private ImageView closeBtn;

    //Register
    private EditText usernameReg;
    private EditText passwordReg;
    private EditText confirmPasswordReg;
    private ImageView closeBtnReg;
    private Button submitBtn;
    private String userType;

    private LinearLayout signInSection;
    private LinearLayout signUpSection;

    private Retrofit retrofit;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.hideStatusBar(LoginActivity.this);

        signUpSection = findViewById(R.id.sign_up_section);
        signInSection = findViewById(R.id.sign_in_section);

        //Login
        username = findViewById(R.id.user_edit_text);
        password = findViewById(R.id.password_edit_text);
        login = findViewById(R.id.log_in_btn);
        signUp = findViewById(R.id.sign_up_btn);
        forgotPass = findViewById(R.id.forgot_password_txt);
        closeBtn = findViewById(R.id.login_close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Register
        usernameReg = findViewById(R.id.user_email_input_reg);
        passwordReg = findViewById(R.id.user_pass_input_reg);
        confirmPasswordReg = findViewById(R.id.user_confirm_pass_input);

        submitBtn = findViewById(R.id.submit_register_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserTypePopup(LoginActivity.this);
            }
        });

        closeBtnReg = findViewById(R.id.register_close_btn);
        closeBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSignUp(true);
            }
        });

        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient();*/

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                .build();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToSignUp(false);
            }
        });

    }

    private void changeToSignUp (boolean isSignUp) {
        if(isSignUp){
            signInSection.setVisibility(View.VISIBLE);
            signUpSection.setVisibility(View.GONE);
        }else {
            signInSection.setVisibility(View.GONE);
            signUpSection.setVisibility(View.VISIBLE);
        }
    }

    private void validateLogin(/*String username, String password*/) {

        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(user.isEmpty()){
            username.setError("Se requiere usuario");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            username.setError("Ingrese un usuario valido");
            username.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            password.setError("Se requiere contraseña");
            password.requestFocus();
            return;
        }

        if(pass.length() < 3){
            password.setError("La contraseña debe ser de almenos 3 caracteres");
            password.requestFocus();
            return;
        }

        loadData(user, pass);

    }

    private void validateRegister() {

        String user = usernameReg.getText().toString().trim();
        String pass = passwordReg.getText().toString().trim();
        String confirmPass = confirmPasswordReg.getText().toString().trim();

        /*if(user.isEmpty()){
            username.setError("Se requiere usuario");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            username.setError("Ingrese un usuario valido");
            username.requestFocus();
            return;
        }

        if(pass.isEmpty()){
            password.setError("Se requiere contraseña");
            password.requestFocus();
            return;
        }

        if(pass.length() < 3){
            password.setError("La contraseña debe ser de almenos 3 caracteres");
            password.requestFocus();
            return;
        }*/

        registerUser(user, pass, userType);

    }

    private void registerUser (String user, String pass, String userType) {

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<RegisterRespuesta> registerRespuestaCall = service.userRegister(user,pass,userType.toLowerCase());

        registerRespuestaCall.enqueue(new Callback<RegisterRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<RegisterRespuesta> call,@NonNull Response<RegisterRespuesta> response) {
                if (response.isSuccessful()) {

                    RegisterRespuesta registerRespuesta = response.body();

                    myDialog.dismiss();

                    if (registerRespuesta != null) {
                        ArrayList<RegisterAns> listaRespuestas = registerRespuesta.getData();
                        if(!listaRespuestas.get(0).getResponse().equals("Username is already taken.")){
                            Toast.makeText(LoginActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                            changeToSignUp(true);
                        }else {
                            Toast.makeText(LoginActivity.this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                        Log.e(TAG, "onResponse: " + listaRespuestas.get(0).getResponse());
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterRespuesta> call,@NonNull Throwable t) {

                myDialog.dismiss();

                if(t instanceof IOException) { // Error with the connection
                    Log.e(TAG, " onFailure: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Error de connexion", Toast.LENGTH_SHORT).show();
                }
                else{ // Mostlikely conversion error
                    Log.e(TAG, " onFailure: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Error de conv...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData (String user, String pass) {

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<LoginRespuesta> loginRespuestaCall = service.userLogin(user,pass);

        loginRespuestaCall.enqueue(new Callback<LoginRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<LoginRespuesta> call,@NonNull Response<LoginRespuesta> response) {
                if (response.isSuccessful()) {

                    LoginRespuesta loginRespuesta = response.body();

                    if (loginRespuesta != null) {
                        //ArrayList<Videos> listaVideos = loginRespuesta.getData();
                        //Log.e(TAG, "onResponse: " + loginRespuesta.getData().getIdusuario());
                        loadUserInfo(loginRespuesta.getData().get(0).getIdusuario());
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginRespuesta> call,@NonNull Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "Usuario o contrasena incorrecta", Toast.LENGTH_SHORT).show();
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
                        Log.e(TAG, "Usuario Logueado: " + loginRespuesta.getData().get(0).getNombre());
                        SessionManager.loggedUser = null;
                        SessionManager.loggedUser = loginRespuesta.getData().get(0);
                        Log.e(TAG, "Imagen Usuario: " + loginRespuesta.getData().get(0).getImagenPerfil());
                        SessionManager.isUserLogged = true;

                        if(!loginRespuesta.getData().get(0).getNombre().equals("")){
                            Toast.makeText(getApplicationContext(), "Bienvenido " +
                                    loginRespuesta.getData().get(0).getNombre(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        MainHelper.getInstance().isMainBackFromLogin = true;

                        onBackPressed();
                    }

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginRespuesta> call,@NonNull Throwable t) {
                Log.e(TAG, " onFailureUser: " + t.getMessage());
            }
        });
    }

    //Use "this" or similar as a context on this method to work properly
    public void showUserTypePopup (final Context context) {

        //Button closeBtn;
        TextView athleteText;
        TextView ScoutText;

        RelativeLayout popupBg;

        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.user_type_popup);

        athleteText = myDialog.findViewById(R.id.deportista_btn);
        ScoutText = myDialog.findViewById(R.id.scout_btn);

        popupBg = myDialog.findViewById(R.id.user_type_popup_bg);

        athleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = athleteText.getText().toString();
                //Active
                athleteText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                athleteText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                //Inactive
                ScoutText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                ScoutText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));

                validateRegister();
            }
        });

        ScoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userType = ScoutText.getText().toString();
                //Active
                ScoutText.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                ScoutText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                //Inactive
                athleteText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                athleteText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));

                validateRegister();
            }
        });

        popupBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.show();

    }

}
