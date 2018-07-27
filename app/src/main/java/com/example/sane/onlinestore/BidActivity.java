package com.example.sane.onlinestore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.BidAPI;
import com.example.sane.onlinestore.Events.AuctionEvent;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblBid;
import com.example.sane.onlinestore.Models.TblUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class BidActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    Context context;
    TblBid Bid;
    int HighestBid;
    int itemid = -1;
    int userid = -1;


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }





    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(AuctionEvent auctionEvent) {
        List<TblAuction> Details = new ArrayList<>();
        final int position;

        position = auctionEvent.getPosition();
        Details = auctionEvent.getMessage();

        final int itemidE = Details.get(position).getAuctionId();
        Log.i(TAG, "onEvent in BidActivity: " + itemid);
        int useridE = Details.get(position).getUserId() + 1;

        Log.i(TAG, "onEvent in Bid Activity: " + Details.toString());

        TextView PaintingNameTextView;
        TextView ArtistNameTextView,LastDateTextView;
        final TextView HighestBidTextView;


        PaintingNameTextView = findViewById(R.id.PaintingNameTextView);
        HighestBidTextView = findViewById(R.id.HighestBidTextView);
        ArtistNameTextView = findViewById(R.id.ArtistNameTextView);
        LastDateTextView = findViewById(R.id.AuctionLastDateTextView);

        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat Formatout = new SimpleDateFormat("dd MMM yyyy");

        PaintingNameTextView.setText(Details.get(position).getAuctionName());
        ArtistNameTextView.setText(Details.get(position).getTblUser().getUsername());

        String LastDateString = Details.get(position).getAuctionLastDate() ;
        Date LastDate = null;
        try {
           LastDate = Format.parse(LastDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LastDateTextView.setText(Formatout.format(LastDate));

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);

        BidAPI service = BidAPI.retrofit.create(BidAPI.class);
        Call<TblBid> Bids = service.getBids(storedToken, itemidE);
        Log.i(TAG, "onCreate in BiD Activity: " + itemidE);

        final List<TblAuction> finalDetails = Details;
        Bids.enqueue(new Callback<TblBid>() {
            @Override
            public void onResponse(Call<TblBid> call, Response<TblBid> response) {
                Bid = response.body();
                Log.i(TAG, "onResponse in Bid: " + response.body());

                if (response.body() == null) {
                    HighestBidTextView.setText(finalDetails.get(position).getStartingBid());
                } else {
                    HighestBidTextView.setText(Bid.getBidPrice());
                }
            }

            @Override
            public void onFailure(Call<TblBid> call, Throwable t) {
                Log.i(TAG, "onFailure: " + call + " Throwable: " + t);

            }
        });


        final EditText BidEditText;
        Button BtnBid;
        BidEditText = findViewById(R.id.BidEditText);
        BtnBid = findViewById(R.id.BtnBid);

        BtnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean digitsOnly;


                if (BidEditText.getText().length() <= 0) {
                    Toast.makeText(BidActivity.this, "You did not Enter a Bid", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(HighestBidTextView.getText().toString()) >= Integer.parseInt(String.valueOf(BidEditText.getText()))) {
                    Toast.makeText(BidActivity.this, "Your Bid Is too low ", Toast.LENGTH_SHORT).show();
                } else {

                    int bidprice = Integer.parseInt(String.valueOf(BidEditText.getText()));

                    BidAPI service = BidAPI.retrofit.create(BidAPI.class);
                    Call<TblBid> PutBids = service.setBid(storedToken,itemidE , storedId, bidprice);
                    PutBids.enqueue(new Callback<TblBid>() {
                        @Override
                        public void onResponse(Call<TblBid> call, Response<TblBid> response) {
                            Toast.makeText(BidActivity.this, "You Made a Bid", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TblBid> call, Throwable t) {
                            Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                        }
                    });


                }
                finish();
                startActivity(getIntent());
            }
        });


    }
}
