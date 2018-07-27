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

import com.example.sane.onlinestore.API.ArtistAPI;
import com.example.sane.onlinestore.API.CartAPI;
import com.example.sane.onlinestore.Events.ProductDetailEvent;
import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.ProductActivity;
import com.example.sane.onlinestore.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SearchProductAdapters extends RecyclerView.Adapter<SearchProductAdapters.ViewHolder> implements Filterable {
    private ArrayList<TblItem> ItemList;
    private ArrayList<TblItem> ItemListFull;
    Context context;
    String searchString, storedToken;
    int storedId;


    public SearchProductAdapters(ArrayList<TblItem> arraylistItem, Context context, String s, int storedId, String storedToken) {

        ItemList = arraylistItem;
        ItemListFull = new ArrayList<>(arraylistItem);
        this.context = context;
        this.searchString = s;
        this.storedId=storedId;
        this.storedToken=storedToken;

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

            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (TblItem item: ItemListFull){
                    if(item.getItemName().toLowerCase().contains(filterPattern)){
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
            ItemList.clear();
            ItemList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public void setItemList(ArrayList<TblItem> itemList) {
        this.ItemList=itemList;
        this.ItemListFull= new ArrayList<>(itemList);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ProductNameTextView;
        TextView IDTextView;
        TextView ProductPriceTextView;
        TextView QTextView;
        ImageView ProductImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            IDTextView = itemView.findViewById(R.id.ItemTextView);
            ProductNameTextView = itemView.findViewById(R.id.ProductNameTextView);
            ProductPriceTextView = itemView.findViewById(R.id.ProductPriceTextView);
            ProductImageView = itemView.findViewById(R.id.ProductImageView);
            QTextView = itemView.findViewById(R.id.QTextView);
        }
    }
    @Override
    public SearchProductAdapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_product_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchProductAdapters.ViewHolder holder, final int position) {

        holder.IDTextView.setText(this.ItemList.get(position).getItemId().toString());
        holder.ProductNameTextView.setText(this.ItemList.get(position).getItemName().toString());
       // ItemID = this.listItems.get(position).getItemId().toString();
        holder.ProductPriceTextView.setText(this.ItemList.get(position).getPrice().toString());
        holder.QTextView.setText(this.ItemList.get(position).getQuantity().toString());
       // Quantity = this.listItems.get(position).getCategoryId();
       // Log.i(TAG, "onBindViewHolder in Product Adapters: Name " + ItemID + " Category: " + Quantity);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "ajsfsahfsaghfvasj " + position, Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String string = gson.toJson(ItemList.get(position));

                EventBus.getDefault().postSticky(new ProductDetailEvent(ItemList.get(position), position));
                Intent intent = new Intent(context, ProductActivity.class);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return ItemList.size();
    }



    public void filterList(ArrayList<TblItem> filteredList) {
        ItemList = filteredList;
        notifyDataSetChanged();
    }
}
