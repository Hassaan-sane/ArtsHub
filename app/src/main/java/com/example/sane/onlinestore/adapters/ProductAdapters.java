package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.CartAPI;
import com.example.sane.onlinestore.Events.ProductDetailEvent;
import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.ProductActivity;
import com.example.sane.onlinestore.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ProductAdapters extends RecyclerView.Adapter<ProductAdapters.ViewHolder> implements Filterable {


    int Quantity, storedId;
    String ItemID, storedToken;
    private List<TblItem> listItems;
    private List<TblItem> listItemsFull;
    private Context context;

    public ProductAdapters(List<TblItem> listItems, Context context, int storedId, String storedToken) {
        this.listItems = listItems;
        listItemsFull = new ArrayList<>(listItems);
        this.context = context;
        this.storedId = storedId;
        this.storedToken = storedToken;

    }

    public void setItemList(ArrayList<TblItem> arrayList) {
        this.listItems = arrayList;
        listItemsFull= new ArrayList<>(arrayList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ProductNameTextView;
        TextView IDTextView;
        TextView ProductPriceTextView;
        TextView QTextView;
        ImageView ProductImageView;
        Button AddToCart;

        public ViewHolder(View itemView) {
            super(itemView);

            IDTextView = itemView.findViewById(R.id.ItemTextView);
            ProductNameTextView = itemView.findViewById(R.id.ProductNameTextView);
            ProductPriceTextView = itemView.findViewById(R.id.ProductPriceTextView);
            ProductImageView = itemView.findViewById(R.id.ProductImageView);
            QTextView = itemView.findViewById(R.id.QTextView);
            AddToCart = itemView.findViewById(R.id.BtnAddtoCart);
        }
    }

    @Override
    public Filter getFilter() {
        return productFilter;
    }
    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TblItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listItemsFull);

            } else {
                String filterPattern = charSequence.toString().toLowerCase();
                for (TblItem item: listItemsFull)
                {
                    if(item.getItemName().toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listItems.clear();
            listItems.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        context = holder.ProductImageView.getContext();

        holder.IDTextView.setText(this.listItems.get(position).getItemId().toString());
        holder.ProductNameTextView.setText(this.listItems.get(position).getItemName().toString());
        ItemID = this.listItems.get(position).getItemId().toString();
        holder.ProductPriceTextView.setText(this.listItems.get(position).getPrice().toString());
        Quantity = this.listItems.get(position).getCategoryId();
        holder.QTextView.setText(""+Quantity);
        Log.i(TAG, "onBindViewHolder in Product Adapters: Name " + ItemID + " Category: " + Quantity);


        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String PID = holder.IDTextView.getText().toString();
                int Quantity = 1;

                CartAPI service = CartAPI.retrofit.create(CartAPI.class);
                retrofit2.Call<ArrayList<TblCart>> GetCartItems = service.getItemOrder(storedToken, storedId);

                GetCartItems.enqueue(new Callback<ArrayList<TblCart>>() {
                    @Override
                    public void onResponse(retrofit2.Call<ArrayList<TblCart>> call, Response<ArrayList<TblCart>> response) {
                        ArrayList<TblCart> CartList = response.body();
                        CartList.contains(PID);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ArrayList<TblCart>> call, Throwable t) {

                        Log.i(TAG, "onFailure: Call: " + call + " Throwable: " + t);
                    }
                });

                retrofit2.Call<TblCart> CartItems = service.addToCart(storedToken, PID, storedId, Quantity);

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
            public void onClick(View v) {

                Toast.makeText(context, "ajsfsahfsaghfvasj " + position, Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String string = gson.toJson(listItems.get(position));

//                EventBus.builder().build().post(CategoryEvent.class);

                EventBus.getDefault().postSticky(new ProductDetailEvent(listItems.get(position), position));
                Intent intent = new Intent(context, ProductActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();

    }



}
