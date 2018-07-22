package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.BidActivity;
import com.example.sane.onlinestore.Events.AuctionEvent;
import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class AuctionAdapters extends RecyclerView.Adapter<AuctionAdapters.ViewHolder> {

    String name = " NULLL", bid = " NULL";
    private ArrayList<TblAuction> listItems;
    private Context context;
    static int Category = 0;

    public AuctionAdapters(Context context, ArrayList arrayList) {
        this.listItems = arrayList;
        this.context = context;
    }


    public void setAuctionList(ArrayList<TblAuction> arrayList) {
        this.listItems = arrayList;
        notifyDataSetChanged();
        Log.i(TAG, "onBindViewHolder in set  Auction Adapters: Name: " + arrayList);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView AuctionItemName, StartingDate, EndingDate;
        ImageView imageView;
        TextView AuctionStartingBid;

        public ViewHolder(View itemView) {
            super(itemView);

            AuctionItemName = itemView.findViewById(R.id.AuctionItemNameTextView);
            AuctionStartingBid = itemView.findViewById(R.id.AuctionStartBidTextView);
            StartingDate = itemView.findViewById(R.id.AuctionStartDateTextView);
            EndingDate = itemView.findViewById(R.id.AuctionLastDateTextView);
            imageView = itemView.findViewById(R.id.AuctionImageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_list, parent, false);
        AuctionAdapters.ViewHolder viewHolder = new ViewHolder(view);
        Log.d(TAG, "onBindViewHolder in adapter holder  Auction Adapters: Name: " + name + " Starting Bid: " + bid);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        context = holder.imageView.getContext();

        String dtStart = this.listItems.get(position).getAuctionStartDate();
        String dtEnd = this.listItems.get(position).getAuctionLastDate();
        String formatStartDate=null,formatEndDate=null;

        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat FormatOut = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date Sdate = Format.parse(dtStart);
            Date Edate = Format.parse(dtEnd);
            formatStartDate = FormatOut.format(Sdate);
            formatEndDate = FormatOut.format(Edate);
            Log.i(TAG, "onBindViewHolder: "+dtStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.AuctionItemName.setText(this.listItems.get(position).getAuctionName().toString());
        name = this.listItems.get(position).getAuctionName().toString();
        holder.AuctionStartingBid.setText(this.listItems.get(position).getStartingBid().toString());
        holder.StartingDate.setText(formatStartDate);
        holder.EndingDate.setText(formatEndDate);
        bid = this.listItems.get(position).getStartingBid().toString();
        Log.i(TAG, "onBindViewHolder  Auction Adapters: Name: " + name + " Starting Bid: " + bid);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "ajsfsahfsaghfvasj " + position, Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String string = gson.toJson(listItems.get(position));

//                EventBus.builder().build().post(CategoryEvent.class);

                EventBus.getDefault().postSticky(new AuctionEvent(listItems, position));
                Intent intent = new Intent(context, BidActivity.class);
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
