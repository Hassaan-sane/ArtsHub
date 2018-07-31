package com.example.sane.onlinestore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {
    private String m_Text = "";
    private String m_Text2 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        Bundle bundle = getIntent().getExtras();

        final String name = bundle.getString("Name");
        final String addrs = bundle.getString("Address");
        final String phone = bundle.getString("Phone");


        Button BtnLogOut = findViewById(R.id.BtnLogOut);
        Button BtnComplain = findViewById(R.id.BtnComplain);
        Button BtnEditProfile = findViewById(R.id.BtnEditProfile);

        BtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                preferences.edit().clear().commit();
                Intent i = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        BtnComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingActivity.this, ComplainActivity.class);
                startActivity(i);
            }
        });

        BtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EditProfileActivity.class);
                i.putExtra("Name",name);
                i.putExtra("Phone",phone);
                i.putExtra("Address",addrs);
                startActivity(i);
            }
        });



    }
}
