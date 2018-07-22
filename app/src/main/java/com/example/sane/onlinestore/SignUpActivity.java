package com.example.sane.onlinestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Models.TblToken;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;
import com.example.sane.onlinestore.Models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sane.onlinestore.LoginActivity.Token;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "Tag";
    String PswrdString = null, name, addrs, phone, role;


    SharedPreferences sharedpreferences;


    String b = TAG;
    int ID;
    TblUser tblUser;


    String email = null, confirmPassword = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);


        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("Name");
        addrs = bundle.getString("Address");
        phone = bundle.getString("Phone");
        role = bundle.getString("Role");


        Button btnSignUp;
        final EditText EmailTV, PasswordTV, ConfirmPasswordTV;


        btnSignUp = findViewById(R.id.btnSignUp);
        EmailTV = findViewById(R.id.EmailTextView);
        PasswordTV = findViewById(R.id.PasswordTextView);
        ConfirmPasswordTV = findViewById(R.id.ConfirmPasswordTextView);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = EmailTV.getText().toString();
                confirmPassword = ConfirmPasswordTV.getText().toString();
                PswrdString = PasswordTV.getText().toString();

                if (!EmailTV.getText().toString().contains("@") || !EmailTV.getText().toString().contains(".")) {
                    Toast.makeText(SignUpActivity.this, "Email Not Correct", Toast.LENGTH_SHORT).show();
                } else if (PswrdString.length() <= 4) {
                    Toast.makeText(SignUpActivity.this, "Password Should be atlease 7 charachters", Toast.LENGTH_SHORT).show();
                } else if (!PswrdString.matches("^[A-Z]+[a-z]+[0-9]+[+@.#$%^&*_&\\\\]+$")
//                        || !PswrdString.matches("^[a-z]+[A-Z]+[0-9]+[+@.#$%^&*_&\\\\]+$")
//                        || !PswrdString.matches("^[0-9]+[A-Z]+[a-z]+[+@.#$%^&*_&\\\\]+$")
//                        || !PswrdString.matches("^[+@.#$%^&*_&\\\\\\\\]+[A-Z]+[a-z]+[0-9]+$"))
                        ) {
                    Toast.makeText(SignUpActivity.this, "Password Should Contain Capital Letters,Small letter,Numbers and Special SYmbols", Toast.LENGTH_LONG).show();
                } else if (PswrdString == "") {
                    Toast.makeText(SignUpActivity.this, "Password Cant be Empty", Toast.LENGTH_SHORT).show();
                } else if (!PswrdString.equals(ConfirmPasswordTV.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Password Not Matching", Toast.LENGTH_SHORT).show();
                } else {

                    final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                    Call<Void> UserSignUp = service.UserSignUp(email, PswrdString, "Password", confirmPassword);

                    UserSignUp.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            Call<TblToken> UserSignIn = service.UserSignIn(email, PswrdString, "password");
                            UserSignIn.enqueue(new Callback<TblToken>() {
                                @Override
                                public void onResponse(Call<TblToken> call, Response<TblToken> response) {
                                    final TblToken Response = response.body();
                                    Log.i("SignInResponse", "SignInResponse: " + Response.getAccessToken());

                                    SharedPreferences.Editor editor = preferences.edit();
                                    final String t = "Bearer " + Response.getAccessToken();
                                    editor.putString("TokenKey", t);
                                    editor.commit();
                                    editor.apply();

                                    Call<TblUser> UserTblAdd = service.addUser(t, name, email, role, 56, true);
                                    UserTblAdd.enqueue(new Callback<TblUser>() {
                                        @Override
                                        public void onResponse(Call<TblUser> call, Response<TblUser> response) {

                                            tblUser = response.body();
                                            Log.i(TAG, "onResponse in signup: " + tblUser.getUserId().toString());
                                            ID = tblUser.getUserId();

                                            if (response.isSuccessful() && response.body() != null) {
                                                UserAPI service2 = UserAPI.retrofit.create(UserAPI.class);
                                                Call<TblUserDetail> UserDetailTblAdd = service2.addUserDetail(t, ID, phone, email, addrs, b);
                                                UserDetailTblAdd.enqueue(new Callback<TblUserDetail>() {
                                                    @Override
                                                    public void onResponse(Call<TblUserDetail> call, Response<TblUserDetail> response) {
                                                        TblUserDetail tblUserDetail = response.body();
                                                        Log.i(TAG, "onResponse of userdeet: ");
                                                    }

                                                    @Override
                                                    public void onFailure(Call<TblUserDetail> call, Throwable t) {
                                                        Log.i("SignupFaliure in userdt", "SignupFailure in userdeets: " + call + " Throwable: " + t);
                                                    }
                                                });

                                            } else if (response.body() == null) {


                                                Log.i(TAG, "onResponse of userdet Null: " + response.body());

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<TblUser> call, Throwable t) {
                                            Log.i("SignupFaliure", "SignupFailure: in User " + call + " Throwable: " + t);
                                        }
                                    });
                                }

                                public void onFailure(Call<TblToken> call, Throwable t) {
                                    Log.i("SignunFaliure", "SignunFailure: In TOken" + call + " Throwable: " + t);

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i(TAG, "onFailure: " + call + " Throwable: in ASPUser" + t);
                        }
                    });


                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }

            }
        });

    }
}

