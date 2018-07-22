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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.CartAPI;
import com.example.sane.onlinestore.API.HomeAPI;
import com.example.sane.onlinestore.Events.CartEvent;
import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.LoginActivity;
import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblShipping;
import com.example.sane.onlinestore.R;
import com.example.sane.onlinestore.adapters.CartAdapters;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private List<TblCart> Cartlist = new ArrayList<>();
    private  ArrayList<TblCart> CartList;
    private TblShipping tblShipping;
    private Context context;
    int SumPrice=0;

    @Override
    public String toString() {
        return "" + SumPrice;
    }

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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


        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        final String storedToken = preferences.getString("TokenKey", null);
        final int storedId = preferences.getInt("UserID", 0);

        if (storedToken == null) {

            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }


        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        final CartAdapters cartAdapters = new CartAdapters(context, Cartlist);
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.recylerView_cart);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(cartAdapters);

        final TextView GrandTotal = view.findViewById(R.id.CartSumTextView);
        Button Checkout = view.findViewById(R.id.BtnCheckout);


        final CartAPI service = CartAPI.retrofit.create(CartAPI.class);
        final retrofit2.Call<ArrayList<TblCart>> CartItems = service.getItemOrder(storedToken,storedId);

        CartItems.enqueue(new Callback<ArrayList<TblCart>>() {
            @Override
            public void onResponse(Call<ArrayList<TblCart>> call, Response<ArrayList<TblCart>> response) {
                CartList = response.body();

                int size = CartList.size();
                int pos = 0;
                for (; pos < size; pos++) {

                    SumPrice = (Integer.parseInt(CartList.get(pos).getTblItem().getPrice())*CartList.get(pos).getQuantity()) + SumPrice;

                }

                GrandTotal.setText(""+SumPrice);


                cartAdapters.setCartList(storedToken,storedId,CartList);
            }

            @Override
            public void onFailure(Call<ArrayList<TblCart>> call, Throwable t) {

                Log.i(TAG, "onFailure: Call: " + call + " Throwable: " + t);
            }
        });

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat Formatout = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat Format = new SimpleDateFormat("dd MMM yyyy");
                int size = CartList.size();
                int pos = 0;
                for (; pos < size; pos++) {

                    tblShipping.setItemId(CartList.get(pos).getTblItemItemId());
                    tblShipping.setQuantity(CartList.get(pos).getQuantity());

                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    String pickedDate = DateFormat.getDateInstance().format(c.getTime());
                    Date startDate = null;
                    try {
                        startDate = Format.parse(pickedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SumPrice = (Integer.parseInt(CartList.get(pos).getTblItem().getPrice())*CartList.get(pos).getQuantity());
                    tblShipping.setShippingTotal(SumPrice+"");
                    tblShipping.setShippingDate(Formatout.format(startDate));

                    Call<TblShipping> AddShipping = service.addShipping(storedToken,tblShipping.getShippingDate(),storedId,tblShipping.getShippingTotal(),tblShipping.getItemId(),tblShipping.getQuantity());

                    Toast.makeText(context, "You are Checked Out", Toast.LENGTH_SHORT).show();
                }

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

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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

    public void onBackPressed() {
        Intent intent = new Intent(this.getContext(), HomeFragment.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventCart(CartEvent cartEvent) {
        int SumofPrice = cartEvent.getMessage();
        Log.i(TAG, "onEventCart: " + SumofPrice);

//        SumPrice = SumofPrice;

    }
}
