package com.example.sane.onlinestore.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.sane.onlinestore.API.AuctionAPI;
import com.example.sane.onlinestore.BidActivity;
import com.example.sane.onlinestore.EditAuctionActivity;
import com.example.sane.onlinestore.Events.AuctionEvent;
import com.example.sane.onlinestore.Models.TblAuction;
import com.example.sane.onlinestore.Models.TblAuctionDetail;
import com.example.sane.onlinestore.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MyAuctionAdapters extends RecyclerView.Adapter<MyAuctionAdapters.ViewHolder> {

    private ArrayList<TblAuction> listItems;
    private Context context;

    String StoredToken;
    ArrayList<TblAuctionDetail> tblAuctionDetails = new ArrayList<>();
    TblAuctionDetail detail;

    public MyAuctionAdapters(ArrayList<TblAuction> listItems, Context context, String storedToken) {
        this.listItems = listItems;
        this.context = context;
        this.StoredToken = storedToken;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_auction_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setAuctionList(ArrayList<TblAuction> tblAuctions) {
        this.listItems = tblAuctions;
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
            BtnEdit = itemView.findViewById(R.id.BtnAuctionEdit);
            BtnDelete = itemView.findViewById(R.id.BtnAuctionDelete);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        context = holder.imageView.getContext();

        String dtStart = this.listItems.get(position).getAuctionStartDate();
        String dtEnd = this.listItems.get(position).getAuctionLastDate();
        String formatStartDate = null, formatEndDate = null;

        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat FormatOut = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date Sdate = Format.parse(dtStart);
            Date Edate = Format.parse(dtEnd);
            formatStartDate = FormatOut.format(Sdate);
            formatEndDate = FormatOut.format(Edate);
            Log.i(TAG, "onBindViewHolder: " + dtStart);
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
                final AuctionAPI service = AuctionAPI.retrofit.create(AuctionAPI.class);
                Call<ArrayList<TblAuctionDetail>> GetDetail = service.getAuctionDetail(StoredToken);
                GetDetail.enqueue(new Callback<ArrayList<TblAuctionDetail>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TblAuctionDetail>> call, Response<ArrayList<TblAuctionDetail>> response) {
                        tblAuctionDetails = response.body();
                        detail = filterAuction(listItems.get(position).getAuctionId());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TblAuctionDetail>> call, Throwable t) {

                    }
                });

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you Sure You want to Delete this?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Call<TblAuctionDetail> deldetail = service.DeleteAuctionDets(StoredToken, detail.getAuctionDetailId());
                                deldetail.enqueue(new Callback<TblAuctionDetail>() {
                                    @Override
                                    public void onResponse(Call<TblAuctionDetail> call, Response<TblAuctionDetail> response) {
                                        if (response.isSuccessful()) {
                                            Call<TblAuction> delAuc = service.DeleteAuction(StoredToken, listItems.get(position).getAuctionId());
                                            delAuc.enqueue(new Callback<TblAuction>() {
                                                @Override
                                                public void onResponse(Call<TblAuction> call, Response<TblAuction> response) {
                                                    if (response.isSuccessful()) {
                                                        Toast.makeText(context, "DELETED", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<TblAuction> call, Throwable t) {

                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<TblAuctionDetail> call, Throwable t) {

                                    }
                                });

                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    private TblAuctionDetail filterAuction(int id) {

        for (TblAuctionDetail item : tblAuctionDetails) {
            if (item.getAuctionId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}