package com.example.sane.onlinestore.adapters;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.API.CartAPI;
import com.example.sane.onlinestore.Events.CartEvent;
import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.Events.ProductDetailEvent;
import com.example.sane.onlinestore.Fragments.CartFragment;
import com.example.sane.onlinestore.MainActivity;
import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblCart;
import com.example.sane.onlinestore.Models.TblItem;
import com.example.sane.onlinestore.ProductActivity;
import com.example.sane.onlinestore.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CartAdapters extends RecyclerView.Adapter<CartAdapters.ViewHolder> {

    private Context context;
    private List<TblCart> CartList = new ArrayList<>();
    private List<TblItem> tblItem;
    int SumPrice = 0, storedId;
    String storedToken;

    public CartAdapters(Context context, List<TblCart> cartList) {
        this.context = context;
        CartList = cartList;
    }

    public void setCartList(String storedToken, int storedId, ArrayList<TblCart> CartList) {
        this.CartList = CartList;
        this.storedToken = storedToken;
        this.storedId = storedId;
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CartItem;
        TextView Quantity;
        TextView CartItemPrice;
        TextView OrderId;
        Button BtnRemove, BtnAddQ, BtnMinusQ;

        public ViewHolder(View itemView) {
            super(itemView);

            OrderId = itemView.findViewById(R.id.CartOrderID);
            CartItem = itemView.findViewById(R.id.CartItemName);
            Quantity = itemView.findViewById(R.id.CartItemQuantity);
            CartItemPrice = itemView.findViewById(R.id.CartItemPrice);
            BtnRemove = itemView.findViewById(R.id.BtnRemoveItem);
            BtnAddQ = itemView.findViewById(R.id.BtnCartAddQ);
            BtnMinusQ = itemView.findViewById(R.id.BtnCartMinusQ);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        context = holder.itemView.getContext();


            holder.OrderId.setText(this.CartList.get(position).getOrderId().toString());
            holder.Quantity.setText(this.CartList.get(position).getQuantity().toString());
            holder.CartItem.setText(this.CartList.get(position).getTblItem().getItemName().toString());
            int PricexQuant=this.CartList.get(position).getQuantity()*(Integer.parseInt(this.CartList.get(position).getTblItem().getPrice()));
            holder.CartItemPrice.setText(PricexQuant+"");


        EventBus.getDefault().postSticky(new CartEvent(SumPrice));
            holder.BtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String Orderid = holder.OrderId.getText().toString();

                    CartAPI service = CartAPI.retrofit.create(CartAPI.class);
                    retrofit2.Call<TblCart> delCartItems = service.removeItem(storedToken, Orderid);

                    delCartItems.enqueue(new Callback<TblCart>() {
                        @Override
                        public void onResponse(retrofit2.Call<TblCart> call, Response<TblCart> response) {

                            Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "onResponse: " + response.body().toString());
                        }

                        @Override
                        public void onFailure(retrofit2.Call<TblCart> call, Throwable t) {
                            Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                        }
                    });

                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new CartFragment())
                            .commit();

                }
            });

            holder.BtnAddQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CartList.get(position).getTblItem().getQuantity() > Integer.parseInt(holder.Quantity.getText().toString())) {

                        Log.i(TAG, "onClick: " + CartList.get(position).getTblItem().getQuantity());

                        int Add = Integer.parseInt(holder.Quantity.getText().toString()) + 1;
                        int itemID = CartList.get(position).getTblItemItemId();
                        int UserId = CartList.get(position).getTblUserUserId();

                        CartAPI service = CartAPI.retrofit.create(CartAPI.class);
                        retrofit2.Call<TblCart> AddQuantityToItem = service.addToQuantity(storedToken,itemID,UserId, Add, holder.OrderId.getText().toString(),holder.OrderId.getText().toString());

                        AddQuantityToItem.enqueue(new Callback<TblCart>() {
                            @Override
                            public void onResponse(retrofit2.Call<TblCart> call, Response<TblCart> response) {
                                Toast.makeText(context, "Quantity aded", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onResponse in Cart: " + response);
                            }

                            @Override
                            public void onFailure(retrofit2.Call<TblCart> call, Throwable t) {
                                Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                            }
                        });

                        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new CartFragment())
                                .commit();
                    }
                    else{
                        Toast.makeText(context, "There Are No More Items left", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.BtnMinusQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CartList.get(position).getTblItem().getQuantity() >= 1) {

                        Log.i(TAG, "onClick: " + CartList.get(position).getTblItem().getQuantity());

                        int Minus = Integer.parseInt(holder.Quantity.getText().toString()) - 1;
                        int itemID = CartList.get(position).getTblItemItemId();
                        int UserId = CartList.get(position).getTblUserUserId();

                        CartAPI service = CartAPI.retrofit.create(CartAPI.class);
                        retrofit2.Call<TblCart> AddQuantityToItem = service.addToQuantity(storedToken,itemID,UserId, Minus, holder.OrderId.getText().toString(),holder.OrderId.getText().toString());

                        AddQuantityToItem.enqueue(new Callback<TblCart>() {
                            @Override
                            public void onResponse(retrofit2.Call<TblCart> call, Response<TblCart> response) {
                                Toast.makeText(context, "Quantity Removed", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "onResponse in Cart: " + response);
                            }

                            @Override
                            public void onFailure(retrofit2.Call<TblCart> call, Throwable t) {
                                Log.i(TAG, "onFailure: " + call + " Throwable: " + t);
                            }
                        });

                        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new CartFragment())
                                .commit();
                    }
                    else{
                        Toast.makeText(context, "Quantity Cant be Zero", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "alsfnsac" + position, Toast.LENGTH_SHORT).show();


                    EventBus.getDefault().postSticky(new ProductDetailEvent(CartList.get(position).getTblItem(), position));
                    Intent intent = new Intent(context, ProductActivity.class);
                    context.startActivity(intent);
                }
            });
//        }

    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }


}
