package com.example.sane.onlinestore.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sane.onlinestore.API.CategoryAPI;
import com.example.sane.onlinestore.API.HomeAPI;
import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.ServiceGenerator;
import com.example.sane.onlinestore.adapters.ProductAdapters;
import com.example.sane.onlinestore.adapters.SpecificProductAdapters;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    ArrayList arrayList = new ArrayList();
    TblCategory items = new TblCategory();
    ArrayList<TblItem> ItemDetails = new ArrayList<>();
    List<TblItem> tblItems;


    ProgressDialog progressDialog;


    Gson gson;
    public int CategoryID = -2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);

        // Inflate the layout for this fragment
        HomeAPI service = HomeAPI.retrofit.create(HomeAPI.class);
        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }


        if (CategoryID < 0) {


            final ProductAdapters productAdapter = new ProductAdapters(arrayList, this.getContext(), storedId, storedToken);
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            recyclerView = view.findViewById(R.id.recylerView_home);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(productAdapter);


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


            return view;
        } else {


            final SpecificProductAdapters SproductAdapter = new SpecificProductAdapters(items, this.getContext(), storedId, storedToken);
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            recyclerView = view.findViewById(R.id.recylerView_home);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(SproductAdapter);

            Call<TblCategory> ItemList = service.getItemListCategory(storedToken, CategoryID);

            ItemList.enqueue(new Callback<TblCategory>() {
                @Override
                public void onResponse(Call<TblCategory> call, Response<TblCategory> response) {

                    TblCategory ItemDetails = response.body();
                    Log.i(TAG, "onResponse: " + response.toString());

                    // Log.i(TAG, "onResponse2: " + ItemDetails.getTblItem().toString());
                    SproductAdapter.setItem(ItemDetails);
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<TblCategory> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + call + " Throwable: " + t);

                }
            });

            return view;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(HomeEvent HomeEvent) {
        int ID = HomeEvent.getMessage();

        CategoryID = ID;
        Log.i(TAG, "onEventin HomeFragment: " + ID);

        if (HomeEvent != null) {
            EventBus.getDefault().removeStickyEvent(HomeEvent);
        }

    }


    @Override
    public void onStop() {
        HomeEvent homeEvent = EventBus.getDefault().getStickyEvent(HomeEvent.class);


        EventBus.getDefault().unregister(this);
        super.onStop();

    }
}
