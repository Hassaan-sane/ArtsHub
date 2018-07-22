package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblShipping;
import com.example.sane.onlinestore.Models.User;

import java.util.ArrayList;

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
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartAPI {

    String BaseURL = "http://192.168.10.13:80/app/api/";

    @GET("ItemOrder/{tbl_User_UserId}/User")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblCart>> getItemOrder(@Header("Authorization") String token,
                                          @Path("tbl_User_UserId") int UserID);

    @POST("ItemOrder")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblCart> addToCart(
            @Header("Authorization") String token,
            @Field("tbl_Item_ItemId") String ItemID,
            @Field("tbl_User_UserId") int UserID,
            @Field("Quantity") int Quantity);

    @DELETE("ItemOrder/{OrderId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<TblCart> removeItem(@Header("Authorization") String token,
                             @Path("OrderId") String OrderId
    );

    @PUT("ItemOrder/{OrderId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblCart> addToQuantity(@Header("Authorization") String token,
                                @Field("tbl_Item_ItemId") int ItemID,
                                @Field("tbl_User_UserId") int UserID,
                                @Field("Quantity") int Quantity,
                                @Field("OrderId") String OrderID,
                                @Path("OrderId") String OrderId);

    @POST("Shipping")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblShipping> addShipping(@Header("Authorization") String token,
                                  @Field("ShippingDate") String date,
                                  @Field("UserId") int UserID,
                                  @Field("ShippingTotal") String total,
                                  @Field("ItemId") int itemId,
                                  @Field("Quantity") int Quantity);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

