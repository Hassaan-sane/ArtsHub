package com.example.sane.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Events.UserEvent;
import com.example.sane.onlinestore.Models.TblUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private SharedPreferences preferences;
    private String storedToken;
    private int storedId;
    private String storedRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        storedToken = preferences.getString("TokenKey", null);
        storedId = preferences.getInt("UserID", 0);
        storedRole = preferences.getString("Role", null);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        Bundle bundle = getIntent().getExtras();

        final String name = bundle.getString("Name");
        final String addrs = bundle.getString("Address");
        final String phone = bundle.getString("Phone");

        final EditText EditName, EditPhone, EditAddress;

        EditName = findViewById(R.id.EditNameTextView);
        EditPhone = findViewById(R.id.EditPhoneTextView);
        EditAddress = findViewById(R.id.EditAddressTextView);

        Button btnconfirm, btncancel;
        btncancel = findViewById(R.id.btnEditCancel);
        btnconfirm = findViewById(R.id.btnEditConfirm);


        EditName.setText(name);
        EditPhone.setText(phone);
        EditAddress.setText(addrs);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(i);
            }
        });
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                Call<TblUser> EditUser = service.editUser(storedToken,EditName.getText().toString(),storedId);
                EditUser.enqueue(new Callback<TblUser>() {
                    @Override
                    public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                        Log.i(TAG, "onResponse: "+response.body());
                    }

                    @Override
                    public void onFailure(Call<TblUser> call, Throwable t) {

                    }
                });

            }
        });


    }


}
