package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblFollow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FollowAPI {


    String baseURL = "http://192.168.10.13/app/api/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("Follow/{ArtistId}/Artist")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblFollow>> getFollowList(@Header("Authorization") String token,
                                             @Path("ArtistId") int ArtistId);

    @DELETE("Follow/{ArtistId}/Artist/{UserId}/User")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<TblFollow> DeleteFollow(@Header("Authorization") String token,
                                            @Path("ArtistId") int ArtistId,
                                            @Path("UserId") int UserId);

    @POST("Follow")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblFollow> PostFollow(@Header("Authorization") String token,
                               @Field("ArtistId") int ArtistId,
                               @Field("UserId") int UserId,
                               @Field("FollowId") int FollowId);


}
