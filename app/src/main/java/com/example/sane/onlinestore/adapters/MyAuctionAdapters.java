package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.BidActivity;
import com.example.sane.onlinestore.EditAuctionActivity;
import com.example.sane.onlinestore.Events.AuctionEvent;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class MyAuctionAdapters extends RecyclerView.Adapter<MyAuctionAdapters.ViewHolder>{

    private ArrayList<TblAuction> listItems;
    private Context context;

    public MyAuctionAdapters(ArrayList<TblAuction> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_auction_list, parent, false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    public void setAuctionList(ArrayList<TblAuction> tblAuctions) {
        this.listItems=tblAuctions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView AuctionItemName, StartingDate, EndingDate;
        ImageView imageView;
        TextView AuctionStartingBid;
        Button BtnEdit, BtnDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            AuctionItemName = itemView.findViewById(R.id.MyAuctionItemNameTextView);
            AuctionStartingBid = itemView.findViewById(R.id.MyAuctionStartBidTextView);
            StartingDate = itemView.findViewById(R.id.MyAuctionStartDateTextView);
            EndingDate = itemView.findViewById(R.id.MyAuctionLastDateTextView);
            imageView = itemView.findViewById(R.id.MyAuctionImageView);
            BtnEdit =  itemView.findViewById(R.id.BtnAuctionEdit);
            BtnDelete = itemView.findViewById(R.id.BtnAuctionDelete);
        }
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
        holder.AuctionStartingBid.setText(this.listItems.get(position).getStartingBid().toString());
        holder.StartingDate.setText(formatStartDate);
        holder.EndingDate.setText(formatEndDate);

        holder.BtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new AuctionEvent(listItems, position));
                Intent intent = new Intent(context, EditAuctionActivity.class);
                context.startActivity(intent);
            }
        });
        holder.BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}