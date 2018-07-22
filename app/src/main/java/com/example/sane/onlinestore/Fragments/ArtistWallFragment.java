package com.example.sane.onlinestore.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.Models.TblArtistPost;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.adapters.ArtistWallAdapters;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistWallFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<TblArtistPost> tblArtistPosts = new ArrayList<>();
    private List<TblArtistPost> tblArtistPostsList = new ArrayList<>();
    private TblUser tblUser;
    private Context context;


    public ArtistWallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        Call<TblUser> User = service.getUser(storedToken, storedId);

        User.enqueue(new Callback<TblUser>() {
            @Override
            public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                tblUser = response.body();


            }

            @Override
            public void onFailure(Call<TblUser> call, Throwable t) {
                Log.i("ArtistProfileFaliure", "ArtistProfileFaliure" + call + " Throwable: " + t);
            }
        });

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_artist_wall, container, false);
        final ArtistWallAdapters artistWallAdapters = new ArtistWallAdapters(tblArtistPosts, tblUser, context);
        recyclerView = v.findViewById(R.id.recylerView_Wall);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(artistWallAdapters);

        ArtistAPI service2 = ArtistAPI.retrofit.create(ArtistAPI.class);
        Call<List<TblArtistPost>> GetPosts = service2.GetPosts(storedToken);
        GetPosts.enqueue(new Callback<List<TblArtistPost>>() {
            @Override
            public void onResponse(Call<List<TblArtistPost>> call, Response<List<TblArtistPost>> response) {
                tblArtistPosts = response.body();
                artistWallAdapters.setList(tblArtistPosts,tblUser);
            }

            @Override
            public void onFailure(Call<List<TblArtistPost>> call, Throwable t) {

                Log.i("ArtistProfileFaliure", "ArtistProfileFaliure" + call + " Throwable: " + t);

            }
        });


        return v;
    }


}
