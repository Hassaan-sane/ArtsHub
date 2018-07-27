package com.example.sane.onlinestore.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.API.HomeAPI;
import com.example.sane.onlinestore.API.UserAPI;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.adapters.SearchAdapters;
import com.example.sane.onlinestore.adapters.SearchProductAdapters;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    ArrayList<TblUser> arrayList = new ArrayList();
    ArrayList<TblItem> arraylistItem = new ArrayList<>();
    ArrayList<TblUser> tblUsers;
    ArrayList<TblUser> ArtistList = new ArrayList<>();
    ArrayList<TblItem> ItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_Product;
    private SearchAdapters searchAdapters;
    private SearchProductAdapters searchProductAdapters;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);

        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText SearchET = v.findViewById(R.id.SearchET);
        SearchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
                filterProduct(editable.toString());

            }
        });

        searchAdapters = new SearchAdapters(this.getContext(), arrayList, SearchET.getText().toString());
        searchProductAdapters = new SearchProductAdapters(arraylistItem, this.getContext(), SearchET.getText().toString(), storedId, storedToken);

        recyclerView = v.findViewById(R.id.recylerView_search);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, true));

        recyclerView_Product = v.findViewById(R.id.recylerView_search_product);
        recyclerView_Product.setHasFixedSize(true);
        recyclerView_Product.setLayoutManager(new LinearLayoutManager(this.getContext()));

        recyclerView.setAdapter(searchAdapters);
        recyclerView_Product.setAdapter(searchProductAdapters);


        UserAPI service = UserAPI.retrofit.create(UserAPI.class);
        HomeAPI service2 = HomeAPI.retrofit.create(HomeAPI.class);

        Call<ArrayList<TblUser>> Search = service.getUserList(storedToken);
        Search.enqueue(new Callback<ArrayList<TblUser>>() {
            @Override
            public void onResponse(Call<ArrayList<TblUser>> call, Response<ArrayList<TblUser>> response) {
                tblUsers = response.body();
                Log.i("InSearch", "onResponse: " + response.body());

                for (TblUser item : tblUsers) {
                    if (item.getRole().toLowerCase().contains("r")) {
                        ArtistList.add(item);
                    }
                }
                searchAdapters.AddUserList(ArtistList);
            }

            @Override
            public void onFailure(Call<ArrayList<TblUser>> call, Throwable t) {
                Log.i("inSearch", "onFailure: " + call + " Throwable: " + t);
            }
        });
        Call<ArrayList<TblItem>> SearchItem = service2.getItemList(storedToken);
        SearchItem.enqueue(new Callback<ArrayList<TblItem>>() {
            @Override
            public void onResponse(Call<ArrayList<TblItem>> call, Response<ArrayList<TblItem>> response) {
                ItemList = response.body();

                searchProductAdapters.setItemList(ItemList);
                Log.i("InSearchItem", "onResponseItems: " + response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<TblItem>> call, Throwable t) {
                Log.i("inSearchItems", "onFailure: " + call + " Throwable: " + t);
            }
        });


        return v;
    }

    //    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.activity_main2_drawer, menu);
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
                searchProductAdapters.getFilter().filter(newText);
                searchAdapters.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void filterProduct(String s) {
        ArrayList<TblItem> filteredList = new ArrayList<>();

        for (TblItem item : ItemList) {
            if (item.getItemName().toLowerCase().contains(s.toLowerCase())) {
                filteredList.add(item);
            }
        }
        searchProductAdapters.filterList(filteredList);
    }

    private void filter(String text) {
        ArrayList<TblUser> filteredList = new ArrayList<>();

        for (TblUser item : ArtistList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        searchAdapters.filterList(filteredList);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
