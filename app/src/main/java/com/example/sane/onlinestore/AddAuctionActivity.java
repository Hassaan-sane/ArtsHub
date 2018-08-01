package com.example.sane.onlinestore;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.AuctionAPI;
import com.example.sane.onlinestore.Fragments.DatePickerFragment;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblAuctionDetail;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.provider.ContactsContract.CommonDataKinds.Event.START_DATE;

public class AddAuctionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private String formatStartDate = null, formatEndDate = null;

    static final int START_DATE = 1;
    static final int END_DATE = 2;
    private int mChosenDate;

    private static final int PICK_IMAGE = 100;
    private String ImageStr;

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
        Button BtnPicImg = findViewById(R.id.BtnPickImage);

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
        BtnPicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Title, Desc;
                final int Bid;

                Title = AuctionTitle.getText().toString();
                Desc = AuctionDesc.getText().toString();
                Bid = Integer.parseInt(StartBid.getText().toString());

                SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy");

                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = Format.parse(formatStartDate);
                    endDate = Format.parse(formatEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date check = Calendar.getInstance().getTime();

                if (check.getTime() > startDate.getTime()) {
                    Toast.makeText(AddAuctionActivity.this, "Start date cannot be less than today", Toast.LENGTH_SHORT).show();

                } else if (check.getTime() > endDate.getTime()) {
                    Toast.makeText(AddAuctionActivity.this, "end date cannot be less than today", Toast.LENGTH_SHORT).show();


                } else if (startDate.getTime() > endDate.getTime()) {
                    Toast.makeText(AddAuctionActivity.this, "start date cannot be greater than end date", Toast.LENGTH_SHORT).show();

                }else if(!Title.matches("[a-zA-Z ]") ){
                    Toast.makeText(AddAuctionActivity.this, "Title can only contain alphabets", Toast.LENGTH_SHORT).show();

                }
                else if(!StartBid.getText().toString().matches("[0-9]") ){
                    Toast.makeText(AddAuctionActivity.this, "Bid can only contain numbers", Toast.LENGTH_SHORT).show();
                }

                else if (startDate.getTime() < endDate.getTime()) {

                    final AuctionAPI service = AuctionAPI.retrofit.create(AuctionAPI.class);
                    Call<TblAuction> PostAuction = service
                            .PostAuction(storedToken, Title, formatEndDate, storedId, Desc, Bid, formatStartDate);

                    PostAuction.enqueue(new Callback<TblAuction>() {
                        @Override
                        public void onResponse(Call<TblAuction> call, Response<TblAuction> response) {
                            TblAuction tblAuction = response.body();
                            Log.i("onresoponse", "onResponse: " + response.toString());
                            if (response.isSuccessful() && ImageStr != null) {

                                Call<TblAuctionDetail> PostAucDetail = service.PostAuctionDetail(storedToken,
                                        tblAuction.getAuctionId(),
                                        ImageStr);
                                PostAucDetail.enqueue(new Callback<TblAuctionDetail>() {
                                    @Override
                                    public void onResponse(Call<TblAuctionDetail> call, Response<TblAuctionDetail> response) {
                                        Log.i(TAG, "onResponse in auc detail: " + response);

                                    }

                                    @Override
                                    public void onFailure(Call<TblAuctionDetail> call, Throwable t) {

                                        Log.i("AuctionPostFaliure", "AuctionPostDetailFaliure" + call + " Throwable: " + t);
                                    }
                                });

                            }

                        }

                        @Override
                        public void onFailure(Call<TblAuction> call, Throwable t) {

                            Log.i("AuctionPostFaliure", "AuctionPostFaliure" + call + " Throwable: " + t);

                        }
                    });
                }
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
                ;
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

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedid = preferences.getInt("UserID", 0);

        if (requestCode == PICK_IMAGE && null != data) {
            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                String picturePath = cursor.getString(columnIndex);

                cursor.close();

                ImageView imageView = findViewById(R.id.ImageViewAuction);
                imageView.setImageURI(selectedImage);

                ImageStr = ImageConvert(picturePath);


            }
        }
    }

    public String ImageConvert(String path) {

        Bitmap bm = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        final byte[] b = baos.toByteArray();

        String encodedImage = Base64.encodeToString(b, Base64.CRLF);
        Log.i("encodedImage", "encodedImagee: " + encodedImage);
        return encodedImage;
    }

}