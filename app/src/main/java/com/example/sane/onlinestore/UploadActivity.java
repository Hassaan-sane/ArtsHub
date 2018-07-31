package com.example.sane.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;

import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Models.TblUserDetail;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedid = preferences.getInt("UserID",0);
        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        Button pickImageButton = findViewById(R.id.BtnPick);
        Button GetImage = findViewById(R.id.BtnGet);
        final ImageView imageView = findViewById(R.id.ImageView);

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        GetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                Call<TblUserDetail> GetUserImage = service.getUserDetail(storedToken, storedid);
                GetUserImage.enqueue(new Callback<TblUserDetail>() {
                    @Override
                    public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
                        TblUserDetail tblUserDetail = response.body();

                        String temp = tblUserDetail.getUserImage();
                        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);

                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(decodedByte);
                    }

                    @Override
                    public void onFailure(Call<TblUserDetail> call, Throwable t) {

                        Log.i(TAG, "onFailure: Call: " + call + " Throwable: " + t);
                    }
                });
            }
        });

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
        int storedid = preferences.getInt("UserID",0);

        if (requestCode == PICK_IMAGE && null != data) {
            if (resultCode == RESULT_OK) {

                Uri selectedImage = data.getData();

                Log.i("selectedImage", "selectedImage: " + selectedImage.toString());

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                Log.i("columnIndex", "columnIndex: " + columnIndex);

                String picturePath = cursor.getString(columnIndex);
                Log.i("picturePath", "picturePath: " + picturePath);

                cursor.close();

                ImageView imageView = findViewById(R.id.ImageView);
                //imageView.setImageURI(selectedImage);

//                FileInputStream fis = null;
//                File imagefile = new File(picturePath);
//
//                try {
//                    fis = new FileInputStream(imagefile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                final byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.CRLF);
                Log.i("encodedImage", "encodedImagee: " + encodedImage);

//                String c = null;
//                File fi = new File(picturePath);
//                try {
//                    c = FileUtils.readFileToString(fi);
////                    c= Files.readAllBytes(fi.toPath());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//                byte[] imagebytes = c.getBytes();
//
//                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);
//                Log.i("encodedImage in Byte", "encodedImage In Byte: " + bitmap);
//                imageView.setImageBitmap(bitmap);

//                byte[] bFile = readBytesFromFile(picturePath);
//                Log.i("encodedImage in Byte", "encodedImage In Byte path: " + b);
//                final byte[] imageBytes = bFile;
//                final Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

               UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                Call<TblUserDetail> UserDetails = service
                        .addUserDetail(storedToken, storedid, "0322", "hassaan@gmail.com", "1223 Lahore", encodedImage);

                UserDetails.enqueue(new Callback<TblUserDetail>() {
                    @Override
                    public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
                        Log.i("UserDetailResponse", "UserDetailResponse: " + response.toString());
                        TblUserDetail tblUserDetail = response.body();

//                        byte[] encodeByte = Base64.decode(encodedImage, Base64.DEFAULT);
//
//                         Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
//                                  encodeByte.length);
//
//                        Log.i("encodedImage in Byte", "encodedImage In Byte path: " + bitmap);
//                        imageView.setImageBitmap(bitmap);
                    }
                    @Override
                    public void onFailure(Call<TblUserDetail> call, Throwable t) {
                        Log.i(TAG, "onFailure: Call: " + call + " Throwable: " + t);
                    }
                });

//
//                Call<TblUserDetail> GetUserImage = service.getUserDetail(storedToken,2);
//                GetUserImage.enqueue(new Callback<TblUserDetail>() {
//                    @Override
//                    public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
//                        TblUserDetail tblUserDetail=response.body();
//
//                        String temp =tblUserDetail.getUserImage();
//                        byte[] decodedString = Base64.decode(temp, Base64.DEFAULT);
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        imageView.setImageBitmap(decodedByte);
//                    }
//
//                    @Override
//                    public void onFailure(Call<TblUserDetail> call, Throwable t) {
//
//                    }
//                });

//                Call<TblItemDetails> ItemPic = service.addItemPic(2,b);
//                ItemPic.enqueue(new Callback<TblItemDetails>() {
//                    @Override
//                    public void onResponse(Call<TblItemDetails> call, Response<TblItemDetails> response) {
//                        Log.i("UserDetailResponse", "UserDetailResponse: " + response.toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<TblItemDetails> call, Throwable t) {
//                        Log.i(TAG, "onFailure: Call: " + call + " Throwable: " + t);
//                    }
//                });


            }
        }
    }

}

