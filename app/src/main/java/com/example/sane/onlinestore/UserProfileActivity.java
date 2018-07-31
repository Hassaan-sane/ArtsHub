package com.example.sane.onlinestore;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Events.UserEvent;
import com.example.sane.onlinestore.Fragments.HomeFragment;
import com.example.sane.onlinestore.Models.TblItemDetails;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UserProfileActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private String img;
    private TblUser tblUser;
    ProgressDialog progressDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_user_homepage:
                    //  mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.nav_user_noti:
                    //mTextMessage.setText("My Bids");
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.nav_user_artist_search:
                    Intent intentsearch = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(intentsearch);
                    finish();
                    return true;


                case R.id.nav_user_settings:
                    // mTextMessage.setText("Settings");
                    Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                    i.putExtra("Name", tblUser.getName());
                    i.putExtra("Phone", tblUser.getTblUserDetail().getPhone());
                    i.putExtra("Address", tblUser.getTblUserDetail().getAddress());
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_user);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        final TextView Username, Name, Email, Phone;
        final ImageView userImg = findViewById(R.id.img_user_profile);

        Username = findViewById(R.id.username_user_profile);
        Name = findViewById(R.id.Name_user_profile);
        Email = findViewById(R.id.email_user_profileText);
        Phone = findViewById(R.id.contact_user_profileText);

        UserAPI service = UserAPI.retrofit.create(UserAPI.class);

        Call<TblUser> User = service.getUser(storedToken, storedId);

        User.enqueue(new Callback<TblUser>() {
            @Override
            public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                tblUser = response.body();
                Name.setText(tblUser.getName());
                Username.setText(tblUser.getUsername());
                Email.setText(tblUser.getTblUserDetail().getEmail());
                Phone.setText(tblUser.getTblUserDetail().getPhone());
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TblUser> call, Throwable t) {
                Log.i("UserProfileFaliure", "UserProfileFaliure" + call + " Throwable: " + t);
            }
        });

        Call<TblUserDetail> GetUserImage = service.getUserDetail(storedToken, storedId);
        GetUserImage.enqueue(new Callback<TblUserDetail>() {
            @Override
            public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
                TblUserDetail tblUserDetail = response.body();

                String temp = tblUserDetail.getUserImage();
                byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);

                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                userImg.setImageBitmap(decodedByte);
                Log.i(TAG, "onResponse in usr pr img bm: " +decodedByte);
                Log.i(TAG, "onResponse: " + decodedString);
            }

            @Override
            public void onFailure(Call<TblUserDetail> call, Throwable t) {

                Log.i(TAG, "onFailure: Call: " + call + " Throwable: " + t);
            }
        });
    }

//        final ImageView userImage = findViewById(R.id.img_user_profile);
//
//        UserAPI service = UserAPI.retrofit.create(UserAPI.class);
//        Call<TblUserDetail> Userdetail = service.getUserDetailList(2);
//
//        Userdetail.enqueue(new Callback<TblUserDetail>() {
//            @Override
//            public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
//                TblUserDetail UserD = response.body();
//
//
//
//
////                byte[] byteArray = new byte[]{87, 79, 87, 46, 46, 46};
////                byte[] value = Base64.decode(img, Base64.DEFAULT);
////                ;
//
//                Log.i(TAG, "Image in response: " + img);
//
////                userImage.setImageBitmap(Bitmap.createScaledBitmap(bm, userImage.getWidth(),
////                        userImage.getHeight(), false));
//
//
//                Log.i(TAG, "onUser: " + img);
//            }
//
//            @Override
//            public void onFailure(Call<TblUserDetail> call, Throwable t) {
//                Log.i(TAG, "onFailure: " + call + " Throwable: " + t.getMessage());
//            }
//        });


    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}