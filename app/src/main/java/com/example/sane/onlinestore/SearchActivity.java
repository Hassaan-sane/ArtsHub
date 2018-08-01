package com.example.sane.onlinestore;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.sane.onlinestore.API.HomeAPI;
import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.adapters.SearchAdapters;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchAdapters searchAdapters;
    private RecyclerView recyclerView;
    private ArrayList<TblUser> tblUsers;
    private ArrayList<TblUser> ArtistList = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main2_drawer, menu);
        MenuItem cart_view = menu.findItem(R.id.navigation_cart);
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
                searchAdapters.getFilter().filter(newText);
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
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);
getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (storedToken == null) {

            Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        ArrayList<TblUser> arrayList= new ArrayList<>();
        searchAdapters = new SearchAdapters(this.getApplicationContext(), arrayList);

        recyclerView = findViewById(R.id.recylerView_searchAct);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(searchAdapters);

        UserAPI service = UserAPI.retrofit.create(UserAPI.class);

        Call<ArrayList<TblUser>> Search = service.getUserList(storedToken);
        Search.enqueue(new Callback<ArrayList<TblUser>>() {
            @Override
            public void onResponse(Call<ArrayList<TblUser>> call, Response<ArrayList<TblUser>> response) {
                tblUsers = response.body();
                Log.i("InSearch", "onResponse: " + response.body());

//                for (TblUser item : tblUsers) {
//                    if (item.getRole().toLowerCase().contains("r")) {
//                        ArtistList.add(item);
//                    }
//                }
                searchAdapters.AddUserList(tblUsers);
            }

            @Override
            public void onFailure(Call<ArrayList<TblUser>> call, Throwable t) {
                Log.i("inSearch", "onFailure: " + call + " Throwable: " + t);
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),UserProfileActivity.class);
        finish();
        startActivity(intent);
    }
}
