package com.example.sane.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sane.onlinestore.API.FollowAPI;
import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Events.UserEvent;
import com.example.sane.onlinestore.Models.TblFollow;
import com.example.sane.onlinestore.Models.TblUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSearchResult extends AppCompatActivity {
    List<TblUser> userList;
    ArrayList<TblFollow> FollowerList;
    int position;
    private Boolean IsFollowed = false;
    private Button BtnFollow;
    private Button BtnUnFollow;

    @Override
    protected void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search_result);


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        final FollowAPI service = FollowAPI.retrofit.create(FollowAPI.class);
        BtnFollow = findViewById(R.id.BtnPS_FollowProfile);
        BtnUnFollow = findViewById(R.id.BtnPS_UnFollowProfile);
        final int ArtistID = 0;
        Intent i = getIntent();
        i.getIntExtra("Id", ArtistID);

        final TextView Followers = findViewById(R.id.PS_FollowersTextView);

        Call<ArrayList<TblFollow>> GetFollowers = service.getFollowList(storedToken, ArtistID);
        GetFollowers.enqueue(new Callback<ArrayList<TblFollow>>() {
            @Override
            public void onResponse(Call<ArrayList<TblFollow>> call, Response<ArrayList<TblFollow>> response) {
                FollowerList = response.body();
                int NumberOfFollowers = FollowerList.size();
                for (TblFollow item : FollowerList) {
                    if (storedId == item.getUserId()) {
                        IsFollowed = true;
                        BtnFollow.setVisibility(View.GONE);
                        BtnUnFollow.setVisibility(View.VISIBLE);

                    } else {
                        BtnUnFollow.setVisibility(View.GONE);
                        BtnFollow.setVisibility(View.VISIBLE);
                    }
                }
                Followers.setText("" + NumberOfFollowers);


            }

            @Override
            public void onFailure(Call<ArrayList<TblFollow>> call, Throwable t) {
                Log.i("inProfile onc", "onFailure: " + call + " Throwable: " + t);
            }
        });

        if (IsFollowed == true) {
            BtnFollow.setVisibility(View.GONE);
            BtnUnFollow.setVisibility(View.VISIBLE);
            BtnUnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Unfollow();
                }
            });
        } else {

            BtnFollow.setVisibility(View.VISIBLE);
            BtnUnFollow.setVisibility(View.GONE);
            BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow();
                }
            });
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(UserEvent userEvent) {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);

        userList = userEvent.getUser();
        position = userEvent.getPosition();

        final TextView Username, Name, Email, Phone;

        Username = findViewById(R.id.PS_username_profile);
        Name = findViewById(R.id.PS_Name_profile);
        Email = findViewById(R.id.PS_email_profileText);
        Phone = findViewById(R.id.PS_contact_profileText);


        Username.setText(userList.get(position).getUsername());
        Name.setText(userList.get(position).getName());
        Email.setText(userList.get(position).getTblUserDetail().getEmail());
        Phone.setText(userList.get(position).getTblUserDetail().getPhone());


        final int ArtistID = userList.get(position).getUserId();

        final TextView Followers = findViewById(R.id.PS_FollowersTextView);
        BtnFollow = findViewById(R.id.BtnPS_FollowProfile);
        BtnUnFollow = findViewById(R.id.BtnPS_UnFollowProfile);


        final int[] temp = new int[1];
        final FollowAPI service = FollowAPI.retrofit.create(FollowAPI.class);
        Call<ArrayList<TblFollow>> GetFollowers = service.getFollowList(storedToken, ArtistID);
        GetFollowers.enqueue(new Callback<ArrayList<TblFollow>>() {
            @Override
            public void onResponse(Call<ArrayList<TblFollow>> call, Response<ArrayList<TblFollow>> response) {
                FollowerList = response.body();
                int NumberOfFollowers = FollowerList.size();
                for (TblFollow item : FollowerList) {
                    if (storedId == item.getUserId()) {
                        IsFollowed = true;
                        BtnFollow.setVisibility(View.GONE);
                        BtnUnFollow.setVisibility(View.VISIBLE);

                    } else {
                        BtnUnFollow.setVisibility(View.GONE);
                        BtnFollow.setVisibility(View.VISIBLE);
                    }
                }

                Followers.setText("" + NumberOfFollowers);


            }

            @Override
            public void onFailure(Call<ArrayList<TblFollow>> call, Throwable t) {
                Log.i("inProfile in ev", "onFailure: " + call + " Throwable: " + t);
            }
        });

        if (IsFollowed == true) {
            BtnFollow.setVisibility(View.GONE);
            BtnUnFollow.setVisibility(View.VISIBLE);
            BtnUnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Unfollow();
                }
            });
        } else {

            BtnFollow.setVisibility(View.VISIBLE);
            BtnUnFollow.setVisibility(View.GONE);
            BtnFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    follow();

                }
            });
        }


    }

    public void follow() {

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);
        final FollowAPI service = FollowAPI.retrofit.create(FollowAPI.class);
        final int ArtistID = userList.get(position).getUserId();
        final TextView Followers = findViewById(R.id.PS_FollowersTextView);
        Call<TblFollow> PostFollow = service.PostFollow(storedToken, ArtistID, storedId);
        PostFollow.enqueue(new Callback<TblFollow>() {
            @Override
            public void onResponse(Call<TblFollow> call, Response<TblFollow> response) {
                TblFollow tblFollow = response.body();
                Log.i("in FOllow", "onResponse: " + response);
                BtnFollow.setVisibility(View.GONE);
                BtnUnFollow.setVisibility(View.VISIBLE);
                IsFollowed = true;
                finish();
                startActivity(getIntent());

            }

            @Override
            public void onFailure(Call<TblFollow> call, Throwable t) {
                Log.i("inProfile in cr infal", "onFailure: " + call + " Throwable: " + t);

            }
        });
    }

    public void Unfollow() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);
        final FollowAPI service = FollowAPI.retrofit.create(FollowAPI.class);
        final int ArtistID = userList.get(position).getUserId();
        Call<TblFollow> UnFollow = service.DeleteFollow(storedToken, ArtistID, storedId);
        UnFollow.enqueue(new Callback<TblFollow>() {
            @Override
            public void onResponse(Call<TblFollow> call, Response<TblFollow> response) {
                Log.i("In UNfollow", "onResponse: " + response);
                BtnFollow.setVisibility(View.VISIBLE);
                BtnUnFollow.setVisibility(View.GONE);
                IsFollowed = false;
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onFailure(Call<TblFollow> call, Throwable t) {
                Log.i("inProfile in cr in tru", "onFailure: " + call + " Throwable: " + t);
            }
        });
    }

}
