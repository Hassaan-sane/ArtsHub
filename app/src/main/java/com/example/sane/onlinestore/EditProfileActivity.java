package com.example.sane.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Events.UserEvent;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private TblUser tblUser;
    private TblUserDetail tblUserDetail;
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
        final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        Call<TblUser> getUser = service.getUser(storedToken, storedId);
        getUser.enqueue(new Callback<TblUser>() {
            @Override
            public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                if (response.isSuccessful()) {
                    tblUser = response.body();
                    Log.i(TAG, "onResponsein user: " + response.body());
                } else {
                    Log.i(TAG, "onResponsein : resoponse faliure");
                }
            }

            @Override
            public void onFailure(Call<TblUser> call, Throwable t) {
                Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
            }
        });


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

                if (!EditName.getText().toString().matches("[a-zA-Z ]")) {
                    Toast.makeText(EditProfileActivity.this, "Name Cannot contain Other than alphabets", Toast.LENGTH_SHORT).show();

                } else if (!EditPhone.getText().toString().matches("[0-9]")) {
                    Toast.makeText(EditProfileActivity.this, "Phone Number can only contain Number", Toast.LENGTH_SHORT).show();
                } else if (EditPhone.getText().toString().length() >= 15) {
                    Toast.makeText(EditProfileActivity.this, "Phone number cant be this long", Toast.LENGTH_SHORT).show();

                } else if (EditPhone.getText().toString().length() <= 10) {
                    Toast.makeText(EditProfileActivity.this, "Phone number cant be this short", Toast.LENGTH_SHORT).show();

                } else {

                    Call<TblUser> EditUser = service.editUser(storedToken,
                            EditName.getText().toString(),
                            tblUser.getTblUserDetail().getEmail(),
                            tblUser.getRole(),
                            tblUser.getAspNetUserId(),
                            tblUser.getIsActive(),
                            storedId,
                            storedId);
                    EditUser.enqueue(new Callback<TblUser>() {
                        @Override
                        public void onResponse(Call<TblUser> call, Response<TblUser> response) {
                            Log.i(TAG, "onResponse in confirm: " + response);
                        }

                        @Override
                        public void onFailure(Call<TblUser> call, Throwable t) {
                            Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                        }
                    });

                    Call<TblUserDetail> EditUserDetail = service.editUserDetail(storedToken,
                            storedId,
                            storedId,
                            EditPhone.getText().toString(),
                            tblUser.getTblUserDetail().getEmail(),
                            EditAddress.getText().toString(),
                            tblUser.getTblUserDetail().getUserImage());
                    EditUserDetail.enqueue(new Callback<TblUserDetail>() {
                        @Override
                        public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
                            Log.i(TAG, "onResponse in confirm detail: " + response);
                        }

                        @Override
                        public void onFailure(Call<TblUserDetail> call, Throwable t) {
                            Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                        }
                    });
                }
                if (storedRole.toLowerCase().equals("r")) {
                    Intent i = new Intent(getApplicationContext(), ProfileArtistActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), UserProfileActivity.class);
                    startActivity(i);
                }
            }
        });

    }


}
