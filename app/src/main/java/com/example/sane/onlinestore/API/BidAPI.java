package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblBid;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BidAPI {

    String baseURL = "http://192.168.10.16:80/app/api/";

    @GET("Bid/{AuctionItemId}/Bidder")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<TblBid> getBids(@Header("Authorization") String token, @Path("AuctionItemId")int id);

    @GET("Bid/{UserId}/User")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblBid>> getMyBids(@Header("Authorization") String token, @Path("UserId")int id);



    @POST("Bid")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblBid> setBid(@Header("Authorization") String token,
            @Field("AuctionItemId") int AuctionItemId,
            @Field("UserId") int UserId,
            @Field("BidPrice") int BidPrice
    );


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
