package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CategoryAPI {

    String baseURL = "http://192.168.10.16:80/app/api/";

    @GET("Category")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblCategory>> getCategoryList(@Header("Authorization") String token);

    @GET("Category/{CategoryId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblCategory>> getCategoryList(
            @Header("Authorization")
            @Path("CategoryId") int CategoryId
//            @Field("CategoryName") String CategoryName,
//            @Field("CategoryDesc") String CategoryDesc
    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


}
