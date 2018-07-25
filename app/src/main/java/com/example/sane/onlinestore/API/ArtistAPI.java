package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblArtistPost;
import com.example.sane.onlinestore.Models.TblComplaint;
import com.example.sane.onlinestore.Models.TblPostNotification;
import com.example.sane.onlinestore.Models.TblUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ArtistAPI {

    String baseURL = "http://192.168.10.16:80/app/api/";

    @POST("ArtistPost")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblArtistPost> addPost(@Header("Authorization") String token,
                                @Field("UserId") int UserId,
                                @Field("PostDesc") String PostDesc
    );

    @GET("ArtistPost")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<List<TblArtistPost>> GetPosts(@Header("Authorization") String token
    );

    @GET("PostNotification/{UserId}/User")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblPostNotification>> GetPostNotification(@Header("Authorization") String token,
                                                  @Path("UserId") int UserId
    );


    @POST("PostNotification")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblPostNotification> addPostNotification(@Header("Authorization") String token,
                                                  @Field("ArtistId") int ArtistId,
                                                  @Field("UserId") int UserId,
                                                  @Field("PostId") int PostId
    );

    @POST("Complaint")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
            Call<TblComplaint> AddComplaint(@Header("Authorization") String token,
                                            @Field("Title") String title,
                                            @Field("Description") String Desc,
                                            @Field("UserId") int userid,
                                            @Field("ComplaintId") int CompId

    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
