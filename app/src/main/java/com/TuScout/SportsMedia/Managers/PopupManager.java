package com.TuScout.SportsMedia.Managers;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Adapters.CommentsAdapter;
import com.TuScout.SportsMedia.Adapters.VideosAdapter;
import com.TuScout.SportsMedia.Models.Comments;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.R;
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

public class PopupManager {

    /* |||==> Singleton Pattern Related <==||| */
    private static PopupManager instance;

    private PopupManager() {
    }

    public static PopupManager getInstance() {

        if (instance == null)
            instance = new PopupManager();

        return instance;

    }
    /* End of Singleton Pattern Related */


    //Report Popup
    private String reportText;

    //Add Comments Popup
    private String commentText;

    //Rate Popup
    private int rateValue;
    private ArrayList<ImageView> starsList;

    //Filter Popup
    String filterValue;

    public void startReportPopup(Context context) {

        Dialog reportDialog = new Dialog(context);

        reportDialog.setContentView(R.layout.popup_report);

        EditText reportEditText = reportDialog.findViewById(R.id.report_edit_text);
        reportEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reportText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button sendReportBtn = reportDialog.findViewById(R.id.send_report_btn);
        sendReportBtn.setOnClickListener(v -> {
            if (reportText != null && !reportText.equals("")) {
                Toast.makeText(context, "Reporte Enviado", Toast.LENGTH_SHORT).show();
                reportDialog.dismiss();
            } else {
                Toast.makeText(context, "Favor escribir un reporte", Toast.LENGTH_SHORT).show();
            }
        });

        reportDialog.show();

        Window window = reportDialog.getWindow();

        if (window != null)
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void startViewCommentsPopup(Context context) {

        Dialog showCommentDialog = new Dialog(context);

        showCommentDialog.setContentView(R.layout.popup_show_comments);

        RecyclerView recyclerView = showCommentDialog.findViewById(R.id.comments_recycler);
        CommentsAdapter commentsAdapter = new CommentsAdapter(context);
        recyclerView.setAdapter(commentsAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);

        //For Test
        ArrayList<Comments> commentsList = new ArrayList<>();
        commentsList.add(new Comments("User1", "https://images.pexels.com/photos/207962/pexels-photo-207962.jpeg", "This is just some random text for testing porpurse ja ja ja :v"));
        commentsList.add(new Comments("User2", "https://pub-static.haozhaopian.net/static/web/site/features/one-tap-enhance/images/1-tap-enhance_comparison_chart0cd39fa2358c48f674db97b65327666e.jpg", "This is just some random text for testing porpurse ja ja ja :v"));
        commentsList.add(new Comments("User3", "", "This is just some random text for testing porpurse ja ja ja :v"));

        commentsAdapter.addCommentList(commentsList);

        Button addCommentBtn = showCommentDialog.findViewById(R.id.add_comment_btn);
        addCommentBtn.setOnClickListener(v -> {
            //Toast.makeText(context, "Boton Presionado", Toast.LENGTH_SHORT).show();
            //showCommentDialog.dismiss();
            startAddCommentPopup(context);
        });

        showCommentDialog.show();

        Window window = showCommentDialog.getWindow();

        if (window != null)
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void startAddCommentPopup(Context context) {

        Dialog addCommentDialog = new Dialog(context);

        addCommentDialog.setContentView(R.layout.popup_add_comment);

        EditText reportEditText = addCommentDialog.findViewById(R.id.add_comment_edit_text);
        reportEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                commentText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button sendReportBtn = addCommentDialog.findViewById(R.id.summit_comment_btn);
        sendReportBtn.setOnClickListener(v -> {
            if (commentText != null && !commentText.equals("")) {
                Toast.makeText(context, "Comentario añadido", Toast.LENGTH_SHORT).show();
                addCommentDialog.dismiss();
            } else {
                Toast.makeText(context, "Favor escribir un comentario", Toast.LENGTH_SHORT).show();
            }
        });

        addCommentDialog.show();

        Window window = addCommentDialog.getWindow();

        if (window != null)
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void startRateVideoPopup(Context context) {

        Dialog rateDialog = new Dialog(context);

        rateDialog.setContentView(R.layout.popup_rate);

        ImageView star1Btn = rateDialog.findViewById(R.id.star_1);
        ImageView star2Btn = rateDialog.findViewById(R.id.star_2);
        ImageView star3Btn = rateDialog.findViewById(R.id.star_3);
        ImageView star4Btn = rateDialog.findViewById(R.id.star_4);
        ImageView star5Btn = rateDialog.findViewById(R.id.star_5);

        starsList = new ArrayList<>();

        starsList.add(star1Btn);
        starsList.add(star2Btn);
        starsList.add(star3Btn);
        starsList.add(star4Btn);
        starsList.add(star5Btn);

        rateValue = 0;

        star1Btn.setOnClickListener(v -> {
            rateValue = 1;
            changeRateStarColor(rateValue, context);
        });
        star2Btn.setOnClickListener(v -> {
            rateValue = 2;
            changeRateStarColor(rateValue, context);
        });
        star3Btn.setOnClickListener(v -> {
            rateValue = 3;
            changeRateStarColor(rateValue, context);
        });
        star4Btn.setOnClickListener(v -> {
            rateValue = 4;
            changeRateStarColor(rateValue, context);
        });
        star5Btn.setOnClickListener(v -> {
            rateValue = 5;
            changeRateStarColor(rateValue, context);
        });

        Button sendRateBtn = rateDialog.findViewById(R.id.summit_rate_btn);
        sendRateBtn.setOnClickListener(v -> {
            if (rateValue > 0) {
                Toast.makeText(context, "Video valorado con " + rateValue + " estrellas", Toast.LENGTH_SHORT).show();
                rateDialog.dismiss();
            } else {
                Toast.makeText(context, "Favor valorar video", Toast.LENGTH_SHORT).show();
            }
        });

        rateDialog.show();

        Window window = rateDialog.getWindow();

        if (window != null)
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void startShareVideoPopup(Context context) {

        Dialog shareDialog = new Dialog(context);

        shareDialog.setContentView(R.layout.popup_share);

        ImageView facebookBtn = shareDialog.findViewById(R.id.facebook_share_btn);
        facebookBtn.setOnClickListener(v -> {
            Toast.makeText(context, "Compartido en Facebook", Toast.LENGTH_SHORT).show();
            shareDialog.dismiss();
        });

        ImageView instagramBtn = shareDialog.findViewById(R.id.instagram_share_btn);
        instagramBtn.setOnClickListener(v -> {
            Toast.makeText(context, "Compartido en Instagram", Toast.LENGTH_SHORT).show();
            shareDialog.dismiss();
        });

        ImageView twitterBtn = shareDialog.findViewById(R.id.twitter_share_btn);
        twitterBtn.setOnClickListener(v -> {
            Toast.makeText(context, "Compartido en Twitter", Toast.LENGTH_SHORT).show();
            shareDialog.dismiss();
        });

        shareDialog.show();

        Window window = shareDialog.getWindow();

        if (window != null)
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    private void changeRateStarColor(int rateStarsValue, Context context) {
        for (int i = 0; i < rateStarsValue; i++) {
            starsList.get(i).setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        //For "disabling" unselected stars
        for (int i = 0; i < starsList.size(); i++) {
            if (i + 1 > rateStarsValue)
                starsList.get(i).setColorFilter(ContextCompat.getColor(context, R.color.text_1));
        }
    }

    public void filterSelectionPopup(Context context, String selectedFilter, VideosAdapter videosAdapter) {

        Dialog filterDialog = new Dialog(context);

        filterDialog.setContentView(R.layout.filter_popup);

        TextView filterSelectedTxt = filterDialog.findViewById(R.id.filter_selected_text);
        TextView suggestionText = filterDialog.findViewById(R.id.suggestion_text);
        switch (selectedFilter) {
            case Constants.VIDEO_PESO_FILTER:
                filterSelectedTxt.setText("Ingrese el peso deseado");
                suggestionText.setText("El peso se ingresa en libras(lb)");
                break;
            case Constants.VIDEO_ALTURA_FILTER:
                filterSelectedTxt.setText("Ingrese la estatura deseada");
                suggestionText.setText("La altura va en el formato: 5.6");
                break;
            case Constants.VIDEO_EDAD_FILTER:
                filterSelectedTxt.setText("Ingrese la edad deseada");
                suggestionText.setText("La edad se ingresa en años");
                break;
            case Constants.VIDEO_PAIS_FILTER:
                filterSelectedTxt.setText("Ingrese el pais deseado");
                suggestionText.setText("Puede ir mayuscula o minusculas");
                break;
            default:
                filterSelectedTxt.setText("Ingrese la opcion deseada");
                suggestionText.setText("");
                break;
        }

        EditText filterEditText = filterDialog.findViewById(R.id.user_value_edittext);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //reportText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button filterPopupBtn = filterDialog.findViewById(R.id.filter_popup_btn);
        filterPopupBtn.setOnClickListener(view -> {
            //
            filterValue = filterEditText.getText().toString();
            loadRecyclerviewSearchData(context, filterValue, selectedFilter, videosAdapter);
            filterDialog.dismiss();
        });

        filterDialog.show();

        Window window = filterDialog.getWindow();
        if (window != null)
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void loadRecyclerviewSearchData (Context context,String value, String category, VideosAdapter videosAdapter) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.APP_MAIN_API_LINK)
                .addConverterFactory(GsonConverterFactory.create(/*gson*/))
                //.client(client)
                .build();

        TuScoutApiService service = retrofit.create(TuScoutApiService.class);
        Call<VideosRespuesta> videosRespuestaCall = service.obtainSearch(value, category);

        videosRespuestaCall.enqueue(new Callback<VideosRespuesta>() {
            @Override
            public void onResponse(@NonNull Call<VideosRespuesta> call, @NonNull Response<VideosRespuesta> response) {
                if (response.isSuccessful()) {

                    VideosRespuesta videosRespuesta = response.body();

                    if (videosRespuesta != null) {
                        ArrayList<Videos> listaVideos = videosRespuesta.getData();
                        //listSongAdapter.addSongList(listaCanciones, circle);
                        String toastValue = "Filtrado por " + category + " " + value;
                        Toast.makeText(context, toastValue, Toast.LENGTH_SHORT).show();
                        videosAdapter.clearVideoList();

                        videosAdapter.addVideoList(listaVideos);
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
