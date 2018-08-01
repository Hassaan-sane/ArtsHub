package com.example.sane.onlinestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.CartAPI;
import com.example.sane.onlinestore.Events.ProductDetailEvent;
import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProductActivity extends AppCompatActivity {


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);

        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }


        TextView ProductNameTextView;
        TextView ProductPriceTextView;
        ImageView ProductImageView;
        Button AddToCart;

        ProductNameTextView = findViewById(R.id.ProductNameTextView);
        ProductPriceTextView = findViewById(R.id.ProductPriceTextView);
        ProductImageView = findViewById(R.id.ProductImageView);
        AddToCart = findViewById(R.id.BtnAddtoCart);


    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(ProductDetailEvent Event) {

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);

        TextView ProductNameTextView;
        TextView ProductPriceTextView;
        ImageView ProductImageView;
        TextView ProductDetailsTextView;
        Button AddToCart;

        final TblItem Details;
        int position;



        Details = Event.getMessage();
        position = Event.getPosition();

        ProductNameTextView = findViewById(R.id.ProductNameTextView);
        ProductPriceTextView = findViewById(R.id.ProductPriceTextView);
        ProductImageView = findViewById(R.id.ProductImageView);
        ProductDetailsTextView = findViewById(R.id.ProductDetailsTextView);
        AddToCart = findViewById(R.id.BtnDetailAddtoCart);

        ProductNameTextView.setText(Details.getItemName());
        ProductPriceTextView.setText(Details.getPrice());
        ProductDetailsTextView.setText(Details.getItemDesc());

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(ProductActivity.this);
                progressDialog.setMax(100);
                progressDialog.setMessage("Please wait...");
                progressDialog.setTitle("Adding Item to Cart");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
//                String PID = Details.getItemId().toString();
//                int Quantity = 1;
//
//                final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//                String storedToken = preferences.getString("TokenKey", null);
//                int storedId = preferences.getInt("UserID", 0);
//
//
//                CartAPI service = CartAPI.retrofit.create(CartAPI.class);
//                retrofit2.Call<TblCart> CartItems = service.addToCart(storedToken, PID, storedId, Quantity);
//
//                CartItems.enqueue(new Callback<TblCart>() {
//                    @Override
//                    public void onResponse(retrofit2.Call<TblCart> call, Response<TblCart> response) {
//                        Toast.makeText(ProductActivity.this, "Added Items", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(retrofit2.Call<TblCart> call, Throwable t) {
//                        Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
//                    }
//                });
                final int PID = Details.getItemId();
                final int Quantity = 1;


                final CartAPI service = CartAPI.retrofit.create(CartAPI.class);
                retrofit2.Call<ArrayList<TblCart>> GetCartItems = service.getItemOrder(storedToken, storedId);

                GetCartItems.enqueue(new Callback<ArrayList<TblCart>>() {
                    @Override
                    public void onResponse(retrofit2.Call<ArrayList<TblCart>> call, Response<ArrayList<TblCart>> response) {
                        ArrayList<TblCart> CartList = response.body();
                        if (response.isSuccessful()) {
                            boolean exist = false;
                            for (TblCart item : CartList) {
                                if (item.getTblItemItemId() == PID) {
                                    exist = true;
                                }
                            }

                            if (exist==false){
                                Toast.makeText(getApplicationContext(), "Added Items", Toast.LENGTH_SHORT).show();
                                retrofit2.Call<TblCart> CartItems = service.addToCart(storedToken, PID, storedId, Quantity);

                                CartItems.enqueue(new Callback<TblCart>() {
                                    @Override
                                    public void onResponse(retrofit2.Call<TblCart> call, Response<TblCart> response) {
                                        progressDialog.dismiss();
                                        Log.i(TAG, "onResponsei add to cart: "+response);
                                        Toast.makeText(ProductActivity.this, "Item Added", Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onFailure(retrofit2.Call<TblCart> call, Throwable t) {
                                        Log.i(TAG, "onFailure in add to cart: " + call + " Throwable: " + t);
                                    }
                                });

                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(ProductActivity.this, "Item Already Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ArrayList<TblCart>> call, Throwable t) {

                        Log.i(TAG, "onFailure in Padapter: Call: " + call + " Throwable: " + t);
                    }
                });


            }
        });


    }


}
