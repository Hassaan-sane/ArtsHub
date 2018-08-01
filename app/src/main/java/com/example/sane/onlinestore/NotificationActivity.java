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
import android.view.View;

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.Models.TblPostNotification;
import com.example.sane.onlinestore.adapters.NotificationAdapters;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    private ArrayList<TblPostNotification> NotiList = new ArrayList<>();
    private ArrayList<TblPostNotification> NotiListnew = new ArrayList<>();
    private Context context;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
        final NotificationAdapters notificationAdapters = new NotificationAdapters(NotiList, NotificationActivity.this);
        recyclerView = findViewById(R.id.recylerView_Noti);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(notificationAdapters);

        ArtistAPI service = ArtistAPI.retrofit.create(ArtistAPI.class);
        Call<ArrayList<TblPostNotification>> GetNoti = service.GetPostNotification(storedToken, storedId);

        GetNoti.enqueue(new Callback<ArrayList<TblPostNotification>>() {
            @Override
            public void onResponse(Call<ArrayList<TblPostNotification>> call, Response<ArrayList<TblPostNotification>> response) {
                NotiList = response.body();

                NotiListnew = filteredList();
                Collections.reverse(NotiListnew);
                notificationAdapters.SetNoti(NotiListnew);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ArrayList<TblPostNotification>> call, Throwable t) {
                Log.i("TAG", "onFailure in GetPostNoti Act: " + call + " Throwable: " + t);
            }
        });
    }

    public ArrayList<TblPostNotification> filteredList(){
        ArrayList<TblPostNotification> filteredList = new ArrayList<>();

        for(TblPostNotification item: NotiList )
        {
            if(item.getViewTime()==null){
                filteredList.add(item);
            }
        }


        return filteredList;
    }

    @Override
    public void onBackPressed() {
       Intent intent = new Intent(NotificationActivity.this, UserProfileActivity.class);
       startActivity(intent);
       finish();
    }
}
