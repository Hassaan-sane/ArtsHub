package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.Models.TblItemNotification;
import com.google.gson.GsonBuilder;


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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HomeAPI {
    String BaseURL = "http://192.168.10.18/app/api/";

    @GET("item")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblItem>> getItemList(@Header("Authorization") String token);

    @GET("Category/{CategoryId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<TblCategory> getItemListCategory(@Header("Authorization") String token,
                                          @Path("CategoryId") int CategoryId
    );

    @GET("ItemNotification")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblItemNotification>> GetItemNotification(@Header("Authorization") String token);

    @PUT("ItemNotification/{NotificationId}")
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblItemNotification>> PutItemNotification(@Header("Authorization") String token,
                                                             @Field("BuyerId") int BuyerId,
                                                             @Field("SellerId") int SellerId,
                                                             @Field("ItemId") int ItemId,
                                                             @Field("ViewTime")String ViewTime,
                                                             @Field("NotificationId") int Nid,
                                                             @Path("NotificationId") int Nid2);



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
