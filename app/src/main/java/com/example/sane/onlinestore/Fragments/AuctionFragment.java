package com.example.sane.onlinestore.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sane.onlinestore.API.AuctionAPI;

import com.example.sane.onlinestore.API.HomeAPI;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.MainActivity;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.adapters.AuctionAdapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AuctionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AuctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuctionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayList arrayList = new ArrayList();
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private ArrayList<TblAuction> AuctionItems= new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    private String TAG;

    public AuctionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuctionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuctionFragment newInstance(String param1, String param2) {
        AuctionFragment fragment = new AuctionFragment();
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedToken = preferences.getString("TokenKey", null);
        int storedId = preferences.getInt("UserID", 0);

        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        // Inflate the layout for this fragment
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMax(100);
        progressDialog.setMessage("Please wait...");
        progressDialog.setTitle("Fetching Data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Log.i(TAG, "onCreateView in aution fragmetn: ");

        final AuctionAdapters auctionAdapters = new AuctionAdapters(this.getContext(), arrayList);
        View view = inflater.inflate(R.layout.fragment_auction, container, false);
        recyclerView = view.findViewById(R.id.recylerView_auction);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(auctionAdapters);


        AuctionAPI service = AuctionAPI.retrofit.create(AuctionAPI.class);
        Call<ArrayList<TblAuction>> ItemList = service.getAuctionList(storedToken);

        ItemList.enqueue(new Callback<ArrayList<TblAuction>>() {
            @Override
            public void onResponse(Call<ArrayList<TblAuction>> call, Response<ArrayList<TblAuction>> response) {

                AuctionItems=response.body();
                Log.i(TAG, "onResponse: "+response.toString());
                progressDialog.dismiss();
                auctionAdapters.setAuctionList(filterAuctionItemByDate());

            }

            @Override
            public void onFailure(Call<ArrayList<TblAuction>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+call+" Throwable: "+t);

            }
        });

        return view;

    }
    private ArrayList<TblAuction> filterAuctionItemByDate() {
        ArrayList<TblAuction> filteredList = new ArrayList<>();

        for (TblAuction item : AuctionItems ) {
            String dtStart = item.getAuctionStartDate();
            String dtEnd = item.getAuctionLastDate();

            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            try {
                Date Sdate = Format.parse(dtStart);
                Date Edate = Format.parse(dtEnd);
                if (System.currentTimeMillis()>Sdate.getTime()&&System.currentTimeMillis()<Edate.getTime()) {
                    filteredList.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return filteredList;
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
            Toast.makeText(context, "Auction fragment", Toast.LENGTH_SHORT).show();
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
