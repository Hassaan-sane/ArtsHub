package com.example.sane.onlinestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import android.widget.Toast;

import com.example.sane.onlinestore.API.HomeAPI;
import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.Fragments.AuctionBaseFragment;
import com.example.sane.onlinestore.Fragments.AuctionFragment;
import com.example.sane.onlinestore.Fragments.CartFragment;
import com.example.sane.onlinestore.Fragments.CategoryFragment;
import com.example.sane.onlinestore.Fragments.HomeFragment;
import com.example.sane.onlinestore.Fragments.NotificationFragment;
import com.example.sane.onlinestore.Fragments.SearchFragment;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.adapters.ProductAdapters;
import com.example.sane.onlinestore.adapters.SearchAdapters;
import com.example.sane.onlinestore.adapters.SearchProductAdapters;
import com.example.sane.onlinestore.adapters.SpecificProductAdapters;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    NavigationView navigationView = null;
    Toolbar toolbar = null;

    private SearchAdapters searchAdapters;
    private SearchProductAdapters searchProductAdapters;

    private SharedPreferences preferences;
    private String storedToken;
    private int storedId;
    private String storedRole;

    ProgressDialog progressDialog;

    private ProductAdapters productAdapter;
    ArrayList arrayList = new ArrayList();
    TblCategory items = new TblCategory();
    ArrayList<TblItem> ItemDetails = new ArrayList<>();
    List<TblItem> tblItems;

    boolean doubleBackToExitPressedOnce = false;


    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.container, new HomeFragment()).commit();
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_category:
                    Toast.makeText(MainActivity.this, "Category", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.container, new CategoryFragment()).commit();

                    return true;
//                case R.id.navigation_Profile:
//
//                    Intent intentUp = new Intent(getApplicationContext(), UploadActivity.class);
//                    startActivity(intentUp);
//                    Toast.makeText(MainActivity.this,"NOTI",Toast.LENGTH_SHORT).show();
//                    transaction.replace(R.id.container, new NotificationFragment()).commit();
//                    return true;

                case R.id.navigation_Profile:
                    if (storedRole.equals("R")) {
                        Intent intent = new Intent(getApplicationContext(), ProfileArtistActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                        startActivity(intent);
                    }
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_bid:
                    //Bid Activity
                    transaction.replace(R.id.container, new AuctionBaseFragment()).commit();
                    Toast.makeText(MainActivity.this, "Auction", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_cart:

                    Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.container, new CartFragment()).commit();
                    return true;

                default:
                    transaction.replace(R.id.container, new HomeFragment()).commit();
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main2_drawer, menu);
//        MenuItem cart_view = menu.findItem(R.id.navigation_cart);
        MenuItem searchView = menu.findItem(R.id.navigation_search2);
        SearchView searchView1 = (SearchView) searchView.getActionView();
        searchView1.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productAdapter.getFilter().filter(newText);
//                searchAdapters.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.navigation_search2) {
//            transaction.replace(R.id.container, new SearchFragment()).commit();
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        storedToken = preferences.getString("TokenKey", null);
        storedId = preferences.getInt("UserID", 0);
        storedRole = preferences.getString("Role", null);


        if (storedToken == null) {

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        Log.i(TAG, "onCreate: " + storedToken);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        transaction.replace(R.id.container, new HomeFragment()).commit();
//        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();


        productAdapter = new ProductAdapters(arrayList, getApplicationContext(), storedId, storedToken);
        recyclerView = findViewById(R.id.recyclerView_homeactivity);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(productAdapter);

        HomeAPI service = HomeAPI.retrofit.create(HomeAPI.class);
        Call<ArrayList<TblItem>> ItemList = service.getItemList(storedToken);
        ItemList.enqueue(new Callback<ArrayList<TblItem>>() {
            @Override
            public void onResponse(Call<ArrayList<TblItem>> call, Response<ArrayList<TblItem>> response) {
                ItemDetails = response.body();
                Log.i(TAG, "onResponse: " + response.toString());
                productAdapter.setItemList(ItemDetails);
                Log.i(TAG, "onResponse2: " + ItemDetails);

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<TblItem>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + call + " Throwable: " + t);

            }
        });


    }


    public void onBackPressed() {
//        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        String storedToken = preferences.getString("TokenKey", null);
//        int storedId = preferences.getInt("UserID", 0);
//
//        preferences.edit().clear().commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        //intent.putExtra("EXIT", true);
        startActivity(intent);
////        finish();

//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);


    }


}