package com.example.sane.onlinestore.API;

import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblAuctionDetail;
import com.example.sane.onlinestore.Models.TblCategory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AuctionAPI {


    String baseURL = "http://192.168.10.18:80/app/api/";

    @GET("Auction")
    Call<ArrayList<TblAuction>> getAuctionList(@Header("Authorization") String token);

    @GET("Auction/{AuctionId}")
    Call<ArrayList<TblAuction>> getAuctionListById(@Header("Authorization") String token, @Path("AuctionId") int id);

    @GET("AuctionDetial")
    Call<ArrayList<TblAuctionDetail>> getAuctionDetail(@Header("Authorization") String token);

    @POST("Auction")
    @FormUrlEncoded
    Call<TblAuction> PostAuction(@Header("Authorization") String token,
                                 @Field("AuctionName") String Name,
                                 @Field("AuctionLastDate") String LastDate,
                                 @Field("UserId") int Userid,
                                 @Field("AuctionDesc") String AuctionDesc,
                                 @Field("StartingBid") int StartingBid,
                                 @Field("AuctionStartDate") String StartDate);

    @POST("AuctionDetial")
    @FormUrlEncoded
    Call<TblAuctionDetail> PostAuctionDetail(@Header("Authorization") String token,
                                             @Field("AuctionId") int AuctionId,
                                             @Field("AuctionImage") String Image);

    @PUT("AuctionDetial/{AuctionDetaild}")
    @FormUrlEncoded
    Call<TblAuctionDetail> PutAuctionDetail(@Header("Authorization") String token,
                                            @Field("AuctionId") int AuctionId,
                                            @Field("AuctionImage") String Image,
                                            @Field("AuctionDetailId") int AucDetId,
                                            @Path("AuctionDetailId") int AucDetId2);

    @PUT("Auction/{AuctionId}")
    @FormUrlEncoded
    Call<TblAuction> PutAuction(@Header("Authorization") String token,
                                @Field("AuctionName") String Name,
                                @Field("AuctionLastDate") String LastDate,
                                @Field("UserId") int Userid,
                                @Field("AuctionDesc") String AuctionDesc,
                                @Field("StartingBid") int StartingBid,
                                @Field("AuctionStartDate") String StartDate,
                                @Path("AuctionId") int AuctionId);

    @DELETE("Auction/{AuctionId}")
    @FormUrlEncoded
    Call<TblAuction> DeleteAuction(@Header("Authorization") String token,
                                   @Path("AuctionId") int AuctionId);

    @DELETE("AuctionDetial/{AuctionDetailId}")
    @FormUrlEncoded
    Call<TblAuctionDetail> DeleteAuctionDets(@Header("Authorization") String token,
                                       @Path("AuctionDetailId") int AuctionDetId);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
