package com.example.sane.onlinestore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.API.FollowAPI;
import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Models.TblArtistPost;
import com.example.sane.onlinestore.Models.TblFollow;
import com.example.sane.onlinestore.Models.TblPostNotification;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.adapters.ArtistWallAdapters;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ArtistPostWallActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private RecyclerView recyclerView;
    private List<TblArtistPost> tblArtistPosts = new ArrayList<>();
    private List<TblArtistPost> tblArtistPostsList = new ArrayList<>();
    private TblUser tblUser;
    private ArrayList<TblFollow> FollowListNew;
    private List<Integer> UserIdList = new ArrayList<>();
    private Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_post_wall);
        progressDialog = new ProgressDialog(ArtistPostWallActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(this.context, LoginActivity.class);
            startActivity(intent);
        }
        final ArtistWallAdapters artistWallAdapters = new ArtistWallAdapters(tblArtistPosts, tblUser, context);

        recyclerView = findViewById(R.id.recylerView_Wall1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setAdapter(artistWallAdapters);
        UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        Call<TblUser> User = service.getUser(storedToken, storedId);

        User.enqueue(new Callback<TblUser>() {
            @Override
            public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                tblUser = response.body();
                tblArtistPosts = tblUser.getTblArtistPost();
                for (TblArtistPost item : tblArtistPosts) {
                    if (item.getUserId().equals(storedId)) {
                        tblArtistPostsList.add(item);
                    }
                }
                progressDialog.dismiss();
                artistWallAdapters.setList(tblArtistPosts, tblUser);

            }

            @Override
            public void onFailure(Call<TblUser> call, Throwable t) {
                Log.i("ArtistProfileFaliure", "ArtistProfileFaliure" + call + " Throwable: " + t);
            }
        });


        FollowAPI service3 = FollowAPI.retrofit.create(FollowAPI.class);
        Call<ArrayList<TblFollow>> GetFollowers = service3.getFollowList(storedToken, storedId);
        GetFollowers.enqueue(new Callback<ArrayList<TblFollow>>() {
            @Override
            public void onResponse(Call<ArrayList<TblFollow>> call, Response<ArrayList<TblFollow>> response) {
                FollowListNew = response.body();
                for (TblFollow item : FollowListNew) {
                    if (item.getArtistId().equals(storedId)) {
                        UserIdList.add(item.getUserId());

                        Log.i(TAG, "onCreate: " + UserIdList);

                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TblFollow>> call, Throwable t) {
                Log.i("inProfile", "onFailure: " + call + " Throwable: " + t);

            }
        });


        Button BtnPost;
        final EditText CaptionET;
        BtnPost = findViewById(R.id.BtnPost);
        CaptionET = findViewById(R.id.PostDescEditText);

        BtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String tmp;
                tmp = CaptionET.getText().toString();
                final ArtistAPI service = ArtistAPI.retrofit.create(ArtistAPI.class);
                Call<TblArtistPost> AddPost = service.addPost(storedToken, storedId, tmp);

                AddPost.enqueue(new Callback<TblArtistPost>() {
                    @Override
                    public void onResponse(Call<TblArtistPost> call, Response<TblArtistPost> response) {
                        TblArtistPost tblArtistPost = response.body();
                        int PostId;
                        PostId = tblArtistPost.getPostId();
                        int size = UserIdList.size();
                        for (int pos = 0; pos < size; pos++) {

                            Call<TblPostNotification> AddPostNoti = service
                                    .addPostNotification(storedToken, storedId, UserIdList.get(pos), PostId);
                            AddPostNoti.enqueue(new Callback<TblPostNotification>() {
                                @Override
                                public void onResponse(Call<TblPostNotification> call, Response<TblPostNotification> response) {
                                    TblPostNotification tblPostNotifications = response.body();
                                }

                                @Override
                                public void onFailure(Call<TblPostNotification> call, Throwable t) {

                                    Log.i(TAG, "onFailureIn Noti: " + call + " Throwable: " + t);
                                }
                            });

                        }

                    }

                    @Override
                    public void onFailure(Call<TblArtistPost> call, Throwable t) {
                        Log.i(TAG, "onFailure in Artist: " + call + " Throwable: " + t);
                    }
                });

                Intent intent = new Intent(getApplicationContext(), ArtistPostWallActivity.class);
                startActivity(intent);
            }
        });


    }
}
