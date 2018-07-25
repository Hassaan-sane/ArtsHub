package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.Models.TblItem;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface HomeAPI {
    String BaseURL = "http://192.168.10.16/app/api/";
    @GET("item")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblItem>> getItemList(@Header("Authorization") String token);

    @GET("Category/{CategoryId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
            Call<TblCategory> getItemListCategory(@Header("Authorization") String token,
                    @Path("CategoryId") int CategoryId
    );


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
