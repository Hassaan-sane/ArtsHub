package com.example.sane.onlinestore.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.sane.onlinestore.API.CategoryAPI;
import com.example.sane.onlinestore.Events.CategoryEvent;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.adapters.CategoryAdapters;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ArrayList arrayList = new ArrayList();


    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
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

        RecyclerView recyclerView;

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);
        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }
//        }

        final CategoryAdapters categoryAdapter = new CategoryAdapters(this.getContext(), arrayList);
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = view.findViewById(R.id.recylerView_category);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(categoryAdapter);


        CategoryAPI service = CategoryAPI.retrofit.create(CategoryAPI.class);
        Call<ArrayList<TblCategory>> ItemList = service.getCategoryList(storedToken);

        ItemList.enqueue(new Callback<ArrayList<TblCategory>>() {

            @Override
            public void onResponse(Call<ArrayList<TblCategory>> call, Response<ArrayList<TblCategory>> response) {

                ArrayList<TblCategory> ProductDetailList = response.body();
                Log.i("response_body", String.valueOf(response.body()));
                categoryAdapter.setItemList(ProductDetailList);

                CategoryEvent categoryEvent = new CategoryEvent(ProductDetailList);
                EventBus.getDefault().post(categoryEvent);

            }

            @Override
            public void onFailure(Call<ArrayList<TblCategory>> call, Throwable t) {
                Log.i(TAG, "onFailure: failed " + call + " thowable  " + t);
            }
        });


        return view;
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
            Toast.makeText(context, "fragment", Toast.LENGTH_SHORT).show();
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
