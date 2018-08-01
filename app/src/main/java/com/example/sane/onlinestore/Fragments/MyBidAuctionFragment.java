package com.example.sane.onlinestore.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sane.onlinestore.API.BidAPI;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.MainActivity;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblBid;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.adapters.AuctionAdapters;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyBidAuctionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyBidAuctionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBidAuctionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList arrayList = new ArrayList();
    private RecyclerView recyclerView;

    private ArrayList<TblAuction> tblAuctionNew = new ArrayList<>();
    private ArrayList<TblBid> mybids = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public MyBidAuctionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyBidAuctionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyBidAuctionFragment newInstance(String param1, String param2) {
        MyBidAuctionFragment fragment = new MyBidAuctionFragment();
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
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);


        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }

        final Context context = getContext();
        final AuctionAdapters auctionAdapters = new AuctionAdapters(this.getContext(), arrayList);
        View view = inflater.inflate(R.layout.fragment_auction, container, false);
        recyclerView = view.findViewById(R.id.recylerView_auction);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(auctionAdapters);

        BidAPI service = BidAPI.retrofit.create(BidAPI.class);
        Call<ArrayList<TblBid>> GetMYBids = service.getMyBids(storedToken, storedId);
        GetMYBids.enqueue(new Callback<ArrayList<TblBid>>() {
            @Override
            public void onResponse(Call<ArrayList<TblBid>> call, Response<ArrayList<TblBid>> response) {
                mybids = response.body();
                Log.i("TAG", "onResponse: " + response);
                if (response.isSuccessful() && response.body() != null) {
                    tblAuctionNew = filter();
                    auctionAdapters.setAuctionList(tblAuctionNew);
                } else {
                    Toast.makeText(getContext(), "You Have Not Made Any Bids", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new AuctionBaseFragment())
                            .commit();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<TblBid>> call, Throwable t) {
                Log.i("inMyBids", "onFailure: " + call + " Throwable: " + t);
            }
        });

        return view;
    }

    private ArrayList<TblAuction> filter() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        int storedId = preferences.getInt("UserID", 0);

        ArrayList<TblBid> filteredBidList = new ArrayList<>();
        ArrayList<TblAuction> filterAuctionList = new ArrayList<>();

        for (TblBid Bid : mybids) {
            if (Bid.getUserId().toString().equals(storedId + "")) {
                if (!filteredBidList.contains(Bid)) {
                    filteredBidList.add(Bid);
                    filterAuctionList.add(Bid.getTblAuction());
                }
            }
        }

        return filterAuctionList;
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
