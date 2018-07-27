package com.example.sane.onlinestore;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.FollowAPI;
import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Fragments.ArtistWallFragment;
import com.example.sane.onlinestore.Models.TblFollow;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileArtistActivity extends AppCompatActivity {

    ArrayList<TblFollow> FollowerList;
    private  TblUser tblUser;
    ProgressDialog progressDialog;


    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_homepage:
                    //HOme
                    Toast.makeText(ProfileArtistActivity.this, "HOme", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.nav_Post_Wall:
//                    //Profile wall
                    Intent intent = new Intent(getApplicationContext(), ArtistPostWallActivity.class);
                    startActivity(intent);
                    Toast.makeText(ProfileArtistActivity.this, "Posts", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.nav_Auction_Request:
                    //Statuses
                    Intent intent1 = new Intent(getApplicationContext(), AddAuctionActivity.class);
                    startActivity(intent1);
                    Toast.makeText(ProfileArtistActivity.this, "auc req", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.nav_Auctions:
//                    Intent intent2 = new Intent(getApplicationContext(), PostingActivity.class);
//                    startActivity(intent2);
                    return true;

                case R.id.nav_settings:
                    Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                    i.putExtra("Name",tblUser.getName());
                    i.putExtra("Phone",tblUser.getTblUserDetail().getPhone());
                    i.putExtra("Address",tblUser.getTblUserDetail().getAddress());
                    startActivity(i);
                    return true;

                default:

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation_artist_Profile);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        final TextView Username, Name, Email, Phone;

        Username = findViewById(R.id.username_profile);
        Name = findViewById(R.id.Name_profile);
        Email = findViewById(R.id.email_profileText);
        Phone = findViewById(R.id.contact_profileText);
        final ImageView artistImg = findViewById(R.id.img_profile);

        UserAPI service = UserAPI.retrofit.create(UserAPI.class);

        Call<TblUser> User = service.getUser(storedToken, storedId);

        User.enqueue(new Callback<TblUser>() {
            @Override
            public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                tblUser = response.body();
                String temp1 = tblUser.getName();
                Name.setText(temp1);

                String temp2 = tblUser.getUsername();
                Username.setText(temp2);

                Email.setText(tblUser.getTblUserDetail().getEmail());
                Phone.setText(tblUser.getTblUserDetail().getPhone());

                String img = tblUser.getTblUserDetail().getUserImage();
                byte[] decodedString = Base64.decode(img, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                artistImg.setImageBitmap(bm);

            }

            @Override
            public void onFailure(Call<TblUser> call, Throwable t) {
                Log.i("ArtistProfileFaliure", "ArtistProfileFaliure" + call + " Throwable: " + t);
            }
        });

        final TextView Followers = findViewById(R.id.FollowersTextView);
        final int[] temp = new int[1];
        FollowAPI service2 = FollowAPI.retrofit.create(FollowAPI.class);
        Call<ArrayList<TblFollow>> GetFollowers = service2.getFollowList(storedToken, storedId);
        GetFollowers.enqueue(new Callback<ArrayList<TblFollow>>() {
            @Override
            public void onResponse(Call<ArrayList<TblFollow>> call, Response<ArrayList<TblFollow>> response) {
                FollowerList = response.body();
                int NumberOfFollowers = 0;
                for (TblFollow item : FollowerList) {
                    if (item.getArtistId().equals(storedId)) {
                        NumberOfFollowers = NumberOfFollowers + 1;
                    }
                }
                Followers.setText("" + NumberOfFollowers);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<TblFollow>> call, Throwable t) {

                Log.i("inProfile", "onFailure: " + call + " Throwable: " + t);

            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}