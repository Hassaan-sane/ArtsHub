package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuctionAPI {


    String baseURL = "http://192.168.10.13:80/app/api/";

    @GET("Auction")
    Call<ArrayList<TblAuction>> getAuctionList(@Header("Authorization") String token);

    @GET("Auction/{AuctionId}")
    Call<ArrayList<TblAuction>> getAuctionListById(@Header("Authorization") String token,@Path("AuctionId") int id);

    @POST("Auction")
    @FormUrlEncoded
    Call<TblAuction> PostAuction(@Header("Authorization") String token,
                                 @Field("AuctionName") String Name,
                                 @Field("AuctionLastDate") String LastDate,
                                 @Field("UserId") int Userid,
                                 @Field("AuctionDesc") String AuctionDesc,
                                 @Field("StartingBid") int StartingBid,
                                 @Field("AuctionStartDate") String StartDate);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
