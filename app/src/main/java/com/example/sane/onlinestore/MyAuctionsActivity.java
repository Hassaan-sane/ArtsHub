package com.example.sane.onlinestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.sane.onlinestore.API.AuctionAPI;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.adapters.AuctionAdapters;
import com.example.sane.onlinestore.adapters.MyAuctionAdapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAuctionsActivity extends AppCompatActivity {
    ArrayList arrayList = new ArrayList();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private ArrayList<TblAuction> AuctionItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auctions);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);

        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        // Inflate the layout for this fragment
        progressDialog = new ProgressDialog(MyAuctionsActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final MyAuctionAdapters myAuctionAdapters = new MyAuctionAdapters(arrayList, getApplicationContext(),storedToken);

        recyclerView = findViewById(R.id.recylerView_my_auction);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAuctionAdapters);


        AuctionAPI service = AuctionAPI.retrofit.create(AuctionAPI.class);
        Call<ArrayList<TblAuction>> ItemList = service.getAuctionList(storedToken);

        ItemList.enqueue(new Callback<ArrayList<TblAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<TblAuction>> call, Response<ArrayList<TblAuction>> response) {

                AuctionItems = response.body();
                Log.i("TAG", "onResponse: " + response.toString());
                progressDialog.dismiss();
                myAuctionAdapters.setAuctionList(filterAuctionItemByDate(storedId) );

            }

            @Override
            public void onFailure(Call<ArrayList<TblAuction>> call, Throwable t) {
                Log.i("TAG", "onFailure: " + call + " Throwable: " + t);

            }
        });
    }

    private ArrayList<TblAuction> filterAuctionItemByDate(int storedid) {
        ArrayList<TblAuction> filteredList = new ArrayList<>();
        int ID = storedid;

        for (TblAuction item : AuctionItems) {
            if (item.getUserId().equals(ID)) {
                String dtStart = item.getAuctionStartDate();
                String dtEnd = item.getAuctionLastDate();

                SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                try {
                    Date Sdate = Format.parse(dtStart);
                    if (System.currentTimeMillis() < Sdate.getTime()) {
                        filteredList.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        return filteredList;
    }
}
