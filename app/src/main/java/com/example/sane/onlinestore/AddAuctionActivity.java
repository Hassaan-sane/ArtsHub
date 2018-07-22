package com.example.sane.onlinestore;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sane.onlinestore.API.AuctionAPI;
import com.example.sane.onlinestore.Fragments.DatePickerFragment;
import com.example.sane.onlinestore.Models.TblAuction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.ContactsContract.CommonDataKinds.Event.START_DATE;

public class AddAuctionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private String formatStartDate = null, formatEndDate = null;

    static final int START_DATE = 1;
    static final int END_DATE = 2;
    private int mChosenDate;

    int cur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        final EditText AuctionTitle, AuctionDesc, StartBid;


        AuctionTitle = findViewById(R.id.AuctionTitleTV);
        AuctionDesc = findViewById(R.id.AuctionDescTV);
        StartBid = findViewById(R.id.AuctionStartingBidTV);


        Button BtnStartDate = findViewById(R.id.BtnPickStartDate);
        Button BtnEndDate = findViewById(R.id.BtnPickEndDate);
        Button BtnSubmit = findViewById(R.id.BtnSubmitAuction);

        BtnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChosenDate = 1;
                android.support.v4.app.DialogFragment startDatePicker = new DatePickerFragment();
                startDatePicker.show(getSupportFragmentManager(), "Start Date Picker");

            }
        });

        BtnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChosenDate = 2;
                android.support.v4.app.DialogFragment EndDatePicker = new DatePickerFragment();
                EndDatePicker.show(getSupportFragmentManager(), "End Date Picker");

            }
        });

        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Title,Desc;
                final int Bid;
                Title=AuctionTitle.getText().toString();
                Desc = AuctionDesc.getText().toString();
                Bid = Integer.parseInt(StartBid.getText().toString());

                AuctionAPI service = AuctionAPI.retrofit.create(AuctionAPI.class);
                Call<TblAuction> PostAuction= service
                        .PostAuction(storedToken,Title,formatEndDate,storedId,Desc,Bid,formatStartDate);

                PostAuction.enqueue(new Callback<TblAuction>() {
                    @Override
                    public void onResponse(Call<TblAuction> call, Response<TblAuction> response) {
                        TblAuction tblAuction = response.body();
                        Log.i("onresoponse", "onResponse: "+response.toString());

                    }

                    @Override
                    public void onFailure(Call<TblAuction> call, Throwable t) {

                        Log.i("AuctionPostFaliure", "AuctionPostFaliure" + call + " Throwable: " + t);

                    }
                });
            }
        });


    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {


        SimpleDateFormat Formatout = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy");



        switch (mChosenDate) {

            case START_DATE:
                cur = START_DATE;
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, i);
                c.set(Calendar.MONTH, i1);
                c.set(Calendar.DAY_OF_MONTH, i2);
                String pickedDate = DateFormat.getDateInstance().format(c.getTime());//this will be displayed
                TextView STDTV = findViewById(R.id.STDTV);
                STDTV.setText(pickedDate);

                try {
                    Date startDate = Format.parse(pickedDate);
                    formatStartDate = Formatout.format(startDate);//this will go to database
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("onDateSet", "onDateSet: " + formatStartDate);
                break;

            case END_DATE:
                cur = END_DATE;
                Calendar c1 = Calendar.getInstance();
                c1.set(Calendar.YEAR, i);
                c1.set(Calendar.MONTH, i1);
                c1.set(Calendar.DAY_OF_MONTH, i2);

                String pickedDate2 = DateFormat.getDateInstance().format(c1.getTime());//this will be displayed
                TextView EDTV = findViewById(R.id.EDTV);
                EDTV.setText(pickedDate2);

                try {
                    Date endDate = Format.parse(pickedDate2);
                    formatEndDate = Formatout.format(endDate);//this will go to database
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.i("onDateSet", "onDateSet: " + pickedDate2);
                Log.i("onDateSet", "onDateSet: " + formatEndDate);
                break;
        }


    }


}