package com.example.sane.onlinestore;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.Models.TblArtistPost;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PostingActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        Button BtnPicImage, BtnPost;
        final EditText CaptionET;
        ImageView ImageIV;

//        BtnPicImage = findViewById(R.id.BtnPickImage);
        BtnPost = findViewById(R.id.BtnPost);
        CaptionET = findViewById(R.id.CaptionEditText);
//        ImageIV = findViewById(R.id.ImgSelectImageView);
//
//        BtnPicImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGallery();
//            }
//        });
        BtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tmp;
                tmp = CaptionET.getText().toString();
                ArtistAPI service = ArtistAPI.retrofit.create(ArtistAPI.class);
                Call<TblArtistPost> AddPost = service.addPost(storedToken, storedId, tmp);

                AddPost.enqueue(new Callback<TblArtistPost>() {
                    @Override
                    public void onResponse(Call<TblArtistPost> call, Response<TblArtistPost> response) {
                        TblArtistPost tblArtistPost = response.body();

                        Log.i(TAG, "onResponse: "+tblArtistPost.getPostId());

                    }

                    @Override
                    public void onFailure(Call<TblArtistPost> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
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

//                ImageView imageView = findViewById(R.id.ImgSelectImageView);
//                imageView.setImageURI(selectedImage);

                Bitmap bm = BitmapFactory.decodeFile(picturePath);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                final byte[] b = baos.toByteArray();

                String encodedImage = Base64.encodeToString(b, Base64.CRLF);
                Log.i("encodedImage", "encodedImagee: " + encodedImage);


            }
        }
    }
}
