package com.example.sane.onlinestore.API;

import android.content.SharedPreferences;

import com.example.sane.onlinestore.Models.TblItemDetails;
import com.example.sane.onlinestore.Models.TblToken;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;
import com.example.sane.onlinestore.Models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

import static android.content.Context.MODE_PRIVATE;


public interface UserAPI {


    String baseURL = "http://192.168.10.16:80/app/";

    @GET("api/User")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ArrayList<TblUser>> getUserList(@Header("Authorization") String token);

    @GET("api/UserDetail/{UserId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<TblUserDetail> getUserDetail(@Header("Authorization") String token,
                                      @Path("UserId") int UserId
    );

    @GET("api/User/{UserId}")
    Call<TblUser> getUser(@Header("Authorization") String token,
                          @Path("UserId") int UserId
    );

    @POST("api/User")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblUser> addUser(@Header("Authorization") String token,
                          @Field("Name") String Name,
                          @Field("Username") String Email,
                          @Field("Role") String Role,
                          @Field("AspNetUserId") int id,
                          @Field("IsActive") Boolean Active
    );

    @PUT("api/User/{UserId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblUser> editUser(@Header("Authorization") String token,
                           @Field("Name") String Name,
                           @Field("Username") String Email,
                           @Field("Role") String Role,
                           @Field("AspNetUserId") String Aspid,
                           @Field("IsActive") String Active,
                           @Field("UserId") int Userid,
                           @Path("UserId") int id
    );

    @POST("api/UserDetail")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblUserDetail> addUserDetail(@Header("Authorization") String token,
                                      @Field("UserId") int id,
                                      @Field("Phone") String phone,
                                      @Field("Email") String Email,
                                      @Field("Address") String Address,
                                      @Field("UserImage") String image
    );

    @PUT("api/UserDetail/{UserId}")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblUserDetail> editUserDetail(@Header("Authorization") String token,
                                       @Path("UserId") int Userid,
                                       @Field("UserId") int id,
                                       @Field("Phone") String phone,
                                       @Field("Email") String Email,
                                       @Field("Address") String Address,
                                       @Field("UserImage") String image
    );


    @POST("api/Account/Register")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<Void> UserSignUp(
            @Field("Email") String email,
            @Field("Password") String password,
            @Field("grant_type") String grantype,
            @Field("ConfirmPassword") String confirmPassword
    );


    @POST("token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<TblToken> UserSignIn(
            @Field("userName") String email,
            @Field("Password") String password,
            @Field("grant_type") String grantype
//                    @Field("access_token") String Token,
//                    @Field("token_type") String tokenType,
//                    @Field("UserName") String userName,
//                    @Field("expires_in") Integer expiresIn,
//                    @Field(".issued") String issued,
//                    @Field(".expires") String expires

    );


    ;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}

/*

    @POST("api/itemDetail")
    @FormUrlEncoded
    Call<TblItemDetails> addItemPic(
            @Field("ItemId") int id,
            @Field("ItemImage")byte[] Image
    );

    @GET("api/ItemDetail/")
    Call<List<TblItemDetails>> getItemDetailList(
    );*/
