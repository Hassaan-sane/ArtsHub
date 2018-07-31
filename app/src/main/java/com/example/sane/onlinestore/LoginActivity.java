package com.example.sane.onlinestore;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.sane.onlinestore.Models.TblToken;
import com.example.sane.onlinestore.Models.TblUser;

import java.nio.file.Paths;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    String userName, password;
    String Role;
    TblToken tblToken;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Token = "TokenKey";
    public static final String UserID = "UserID";
    private ProgressDialog progressDialog;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);
        final String storedRole = preferences.getString("Role", null);


        if (storedToken != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }


        final EditText etusername, etpassword;
        Button btnSignUp, btnSignIn;

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        etusername = findViewById(R.id.etusername);
        etpassword = findViewById(R.id.etpassword);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMax(100);
                progressDialog.setMessage("Please wait...");
                progressDialog.setTitle("Fetching Data");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                userName = etusername.getText().toString();
                password = etpassword.getText().toString();

                if (!userName.contains("@") || !userName.contains(".")) {
                    Toast.makeText(LoginActivity.this, "Email Not Correct", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (password.length() <= 4) {
                    Toast.makeText(LoginActivity.this, "Password Should be atlease 7 charachters", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else if (password == "") {
                    Toast.makeText(LoginActivity.this, "Password Cant be Empty", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {

                    UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                    Call<TblToken> UserSignIn = service.UserSignIn(userName, password, "password");

                    UserSignIn.enqueue(new Callback<TblToken>() {
                        @Override
                        public void onResponse(Call<TblToken> call, Response<TblToken> response) {
                            final TblToken Response = response.body();
                            Log.i("SignInResponse", "SignInResponse: " + Response.getAccessToken());


                            final SharedPreferences.Editor editor = sharedpreferences.edit();


                            if (response.isSuccessful() && response.body() != null) {

                                final UserAPI service = UserAPI.retrofit.create(UserAPI.class);
                                Call<ArrayList<TblUser>> UserTbl = service.getUserList("Bearer " + Response.getAccessToken().toString());
                                UserTbl.enqueue(new Callback<ArrayList<TblUser>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<TblUser>> call, Response<ArrayList<TblUser>> response) {
                                        ArrayList<TblUser> tblUsers = response.body();

                                        int size = tblUsers.size();
                                        int pos = 0;
                                        for (; pos <= size; pos++) {
                                            String name;
                                            name = tblUsers.get(pos).getTblUserDetail().getEmail().toLowerCase();
                                            if (name.equals(userName.toLowerCase())) {
                                                break;
                                            }

                                        }
                                        Log.i("TAG", "onResponse in usrtbl: " + pos);

                                        Role = tblUsers.get(pos).getRole();
                                        String t = "Bearer " + Response.getAccessToken();
                                        editor.putString(Token, t);
                                        editor.putInt(UserID, tblUsers.get(pos).getUserId());
                                        editor.putString("Role", Role);
                                        editor.commit();
                                        editor.apply();

                                        progressDialog.dismiss();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<TblUser>> call, Throwable t) {
                                        Log.i("SignInFaliure", "SignInFailure: " + call + " Throwable: " + t);
                                    }
                                });


                            } else if (response.errorBody() != null) {

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.putExtra("Error", "" + response.errorBody());
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();

                            }


                            Toast.makeText(LoginActivity.this, "Signed IN" + response.toString(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<TblToken> call, Throwable t) {
                            Log.i("SignInFaliure", "SignInFailure: " + call + " Throwable: " + t);
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User or Password Incorrect", Toast.LENGTH_LONG).show();
                        }
                    });

                }


            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserAddActivity.class);
                startActivity(intent);
            }
        });

    }
}
