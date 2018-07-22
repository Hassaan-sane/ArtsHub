package com.example.sane.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.Models.TblComplaint;

import retrofit2.Callback;
import retrofit2.Response;

public class ComplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(ComplainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        final EditText TitleTV,ComplaintTV;

        TitleTV = findViewById(R.id.ComplaintTitleTextView);
        ComplaintTV=findViewById(R.id.ComplaintBodyTextView);

        Button BtnSubmitComplaint = findViewById(R.id.BtnSubmitComplaint);

        BtnSubmitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArtistAPI service =ArtistAPI.retrofit.create(ArtistAPI.class);
                retrofit2.Call<TblComplaint> AddComplaint= service.AddComplaint(storedToken,TitleTV.getText().toString(),ComplaintTV.getText().toString(),storedId,storedId);

                AddComplaint.enqueue(new Callback<TblComplaint>() {
                    @Override
                    public void onResponse(retrofit2.Call<TblComplaint> call, Response<TblComplaint> response) {
                        TblComplaint tblComplaint = response.body();
                        if(response.isSuccessful()){
                            Intent i = new Intent(ComplainActivity.this,SettingActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TblComplaint> call, Throwable t) {

                    }
                });

            }
        });
    }
}
