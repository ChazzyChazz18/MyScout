package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.TuScout.SportsMedia.R;
import com.TuScout.SportsMedia.SessionManager;
import com.TuScout.SportsMedia.TuScoutApiService.TuScoutApiService;
import com.TuScout.SportsMedia.TuScoutApiService.UploadResponse;
import com.TuScout.SportsMedia.TuScoutApiService.UploadServiceResponse;
import com.TuScout.SportsMedia.Utilities.Constants;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadVideoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private final String TAG = "UploadVideoFragment";

    private ImageView selectedVideoCover;

    private String titleText;
    private String categoryText;
    private String descriptionText;

    private Retrofit retrofit;

    private String videoToUploadPath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_video, container, false);

        selectedVideoCover = view.findViewById(R.id.selected_video_cover);

        LinearLayout uploadBtn = view.findViewById(R.id.upload_btn);

        LinearLayout cancelBtn = view.findViewById(R.id.upload_cancel_btn);

        Spinner spinner = view.findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter;

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                //.client(client)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                .build();

        if(getContext() != null) {

            adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.category_array, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //
            //adapter.getItem(0).
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(this);

        }

        EditText titleEditText = view.findViewById(R.id.title_edit_text);
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                titleText = s.toString();
            }
        });

        EditText descriptionEditText = view.findViewById(R.id.description_edit_text);
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                descriptionText = s.toString();
            }
        });

        uploadBtn.setOnClickListener(v -> uploadFile());

        cancelBtn.setOnClickListener(v -> {
            if(getActivity() != null)
                getActivity().onBackPressed();
        });

        return view;
    }

    void setSelectedVideoInfo () {
        FragmentManager fm = getFragmentManager();
        LibraryFragment fragm;
        if (fm != null) {
            fragm = (LibraryFragment) fm.findFragmentByTag("7");
            if (fragm != null)
                if(getContext() != null) {
                    Glide.with(getContext())
                            .load(fragm.getDeviceVideoList().get(fragm.getActualVideoInArrayPos()).getVideoURL()) // or URI/path
                            .into(selectedVideoCover); //imageview to set thumbnail to

                    videoToUploadPath = fragm.getDeviceVideoList().get(fragm.getActualVideoInArrayPos()).getVideoURL();
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0) {
            Toast.makeText(getContext(), "Selecciona una categoria", Toast.LENGTH_SHORT).show();
            categoryText = "";
        }
        else if(position == 1) {
            Toast.makeText(getContext(), "Categoria seleccionada: Football", Toast.LENGTH_SHORT).show();
            categoryText = Constants.VIDEO_FOOTBALL_CATEGORY;
        }
        else if(position == 2) {
            Toast.makeText(getContext(), "Categoria seleccionada: Baseball", Toast.LENGTH_SHORT).show();
            categoryText = Constants.VIDEO_BASEBALL_CATEGORY;
        }
        else if(position == 3) {
            Toast.makeText(getContext(), "Categoria seleccionada: Basketball", Toast.LENGTH_SHORT).show();
            categoryText = Constants.VIDEO_BASKETBALL_CATEGORY;
        }
        else if(position == 4) {
            Toast.makeText(getContext(), "Categoria seleccionada: Artes Marciales Mixtas", Toast.LENGTH_SHORT).show();
            categoryText = Constants.VIDEO_MARTIAL_ARTS_CATEGORY;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void uploadFile() {
        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(videoToUploadPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        //ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<UploadServiceResponse> call = service.uploadVideo(fileToUpload, filename);
        call.enqueue(new Callback<UploadServiceResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadServiceResponse> call,@NonNull Response<UploadServiceResponse> response) {
                if (response.isSuccessful()) {

                    UploadServiceResponse serverResponse = response.body();

                    if(serverResponse != null) {

                        ArrayList<UploadResponse> listaRespuestas = serverResponse.getData();

                        Log.e(TAG, "Upload Success status -> " + listaRespuestas.get(0).getSuccess());
                        Log.e(TAG, "Upload Message -> " + listaRespuestas.get(0).getMessage());

                        String videoUrl = Constants.APP_MAIN_API_LINK + "uploads/" + file.getName();
                        String coverUrl = "Unkown";

                        if (listaRespuestas.get(0).getSuccess()) { // Successfully Uploaded
                            //Toast.makeText(getContext(), listaRespuestas.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "Archivo subido", Toast.LENGTH_SHORT).show();
                            updateDBWithNewVideo(SessionManager.loggedUser.getIdusuario(),
                                    titleText, descriptionText,
                                    coverUrl, videoUrl, categoryText);
                        }
                        else { // Error while uploading
                            //Toast.makeText(getContext(), listaRespuestas.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Error al actualizar registro", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {

                    Log.e(TAG, "Upload notSuccessfull -> " + response.errorBody());

                }
                //progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UploadServiceResponse> call,@NonNull Throwable t) {
                Log.e(TAG, "Upload onFailure -> " + t.getMessage());
            }
        });
    }

    private void updateDBWithNewVideo(int userID, String videoName, String videoDescription,
                                      String videoCoverURL, String videoURL, String videoCategory) {

        //ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<UploadServiceResponse> call = service.addNewVideo(userID, videoName, videoDescription, videoCoverURL, videoURL, videoCategory);
        call.enqueue(new Callback<UploadServiceResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadServiceResponse> call,@NonNull Response<UploadServiceResponse> response) {
                if (response.isSuccessful()) {

                    UploadServiceResponse serverResponse = response.body();

                    if(serverResponse != null) {

                        ArrayList<UploadResponse> listaRespuestas = serverResponse.getData();

                        if (listaRespuestas.get(0).getSuccess()) { // Successfully Uploaded
                            //Toast.makeText(getContext(), listaRespuestas.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "Archivo subido", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Update Success status -> " + listaRespuestas.get(0).getSuccess());
                            Log.e(TAG, "Update Message -> " + listaRespuestas.get(0).getMessage());
                        }
                        else { // Error while uploading
                            //Toast.makeText(getContext(), listaRespuestas.get(0).getMessage(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "Error al subir archivo", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Update Success status -> " + listaRespuestas.get(0).getSuccess());
                            Log.e(TAG, "Update Message -> " + listaRespuestas.get(0).getMessage());
                        }

                    }

                } else {

                    Log.e(TAG, "Update notSuccessfull -> " + response.errorBody());

                }
                //progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UploadServiceResponse> call,@NonNull Throwable t) {
                Log.e(TAG, "Update onFailure -> " + t.getMessage());
            }
        });
    }
}
