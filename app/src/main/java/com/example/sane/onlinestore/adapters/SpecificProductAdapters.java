package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.CartAPI;
import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SpecificProductAdapters extends RecyclerView.Adapter<SpecificProductAdapters.ViewHolder> {

    String name, storedToken;
    private TblCategory items;
    int storedId;
    private List<TblItem> tblItem = new ArrayList<>();
    private Context context;


    public SpecificProductAdapters(TblCategory items, Context context, int storedId, String storedToken) {
        this.items = items;
        this.storedId = storedId;
        this.storedToken = storedToken;
        this.context = context;
    }

    public void setItem(TblCategory items) {
        this.items = items;
        tblItem = items.getTblItem();
        Log.i(TAG, "setItem: " + tblItem);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView IDTextView;
        TextView QTextView;
        TextView ProductNameTextView;
        TextView ProductPriceTextView;
        ImageView ProductImageView;
        Button AddToCart;

        public ViewHolder(View itemView) {
            super(itemView);

            IDTextView = itemView.findViewById(R.id.ItemTextView);
            QTextView = itemView.findViewById(R.id.QTextView);
            ProductNameTextView = itemView.findViewById(R.id.ProductNameTextView);
            ProductPriceTextView = itemView.findViewById(R.id.ProductPriceTextView);
            ProductImageView = itemView.findViewById(R.id.ProductImageView);
            AddToCart = itemView.findViewById(R.id.BtnAddtoCart);
        }
    }

    @Override
    public SpecificProductAdapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        SpecificProductAdapters.ViewHolder viewHolder = new SpecificProductAdapters.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.i("onbind before", "onBindViewHolder before holder: ");

        tblItem = this.items.getTblItem();
        holder.ProductNameTextView.setText(this.tblItem.get(position).getItemName().toString());
        holder.ProductPriceTextView.setText(this.tblItem.get(position).getPrice().toString());

        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PID = holder.IDTextView.getText().toString();
                int Quantity = 1;


                CartAPI service = CartAPI.retrofit.create(CartAPI.class);
                retrofit2.Call<TblCart> CartItems = service.addToCart(storedToken,Integer.parseInt(PID), storedId, Quantity);

                CartItems.enqueue(new Callback<TblCart>() {
                    @Override
                    public void onResponse(retrofit2.Call<TblCart> call, Response<TblCart> response) {
                        Toast.makeText(context, "Added Items", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<TblCart> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "ajsfsahfsaghfvasj " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tblItem.size();
    }

}