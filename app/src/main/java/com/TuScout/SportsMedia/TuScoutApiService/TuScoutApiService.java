package com.TuScout.SportsMedia.TuScoutApiService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TuScoutApiService {

    @GET("Search.php")
    Call<VideosRespuesta> obtainSearch(@Query("search") String value, @Query("campBuscar") String videoCategory);

    @GET("Login.php")
    Call<LoginRespuesta> userLogin (@Query("loginUser") String user, @Query("loginPass") String pass);

    @GET("GetActualUser.php")
    Call<LoginRespuesta> obtainUserInfo(@Query("userID") int userID);

    @GET("GetVideosID.php")
    Call<IDRespuesta> obtainUserVideoIDList(@Query("userID") int userID);

    @GET("GetVideo.php")
    Call<VideosRespuesta> obtainUserVideoList (@Query("videoID") int videoID);

    @GET("GetAllVideos.php")
    Call<VideosRespuesta> obtainAllApiVideos (@Query("page") int page);

    @GET("RegisterUser.php")
    Call<RegisterRespuesta> userRegister (@Query("loginUser") String registUser, @Query("loginPass") String registPass, @Query("userType") String type);

    @GET("UpdateUser.php")
    Call<RegisterUpdateRespuesta> actualizarUser (@Query("userID") int userID, @Query("userName") String userName, @Query("userLastname") String userLastName, @Query("userAge") String userAge, @Query("userSex") String userSex, @Query("userSport") String userSport, @Query("userCountry") String userCountry, @Query("userWeight") String userWeight, @Query("userHeight") String userHeight);

    @Multipart
    @POST("Upload_file.php")
    Call<UploadServiceResponse> uploadVideo(@Part MultipartBody.Part file, @Part("file")RequestBody name);

    @GET("AddNewVideo.php")
    Call<UploadServiceResponse> addNewVideo (@Query("userID") int userID, @Query("videoName") String videoName, @Query("videoDescription") String videoDescription, @Query("videoCoverURL") String videoCoverURL, @Query("videoURL") String videoURL, @Query("videoCategory") String videoCategory);
}
