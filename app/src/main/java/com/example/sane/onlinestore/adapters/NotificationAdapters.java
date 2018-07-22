package com.example.sane.onlinestore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sane.onlinestore.Models.TblPostNotification;
import com.example.sane.onlinestore.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NotificationAdapters extends RecyclerView.Adapter<NotificationAdapters.ViewHolder>{
    private ArrayList<TblPostNotification> NotiList = new ArrayList<>();

    public NotificationAdapters(ArrayList<TblPostNotification> NotiList) {
        this.NotiList=NotiList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    public void SetNoti(ArrayList<TblPostNotification> NotiList) {
        this.NotiList=NotiList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView MainText, SecondaryText;

        public ViewHolder(View itemView) {
            super(itemView);

            MainText = itemView.findViewById(R.id.NotificationMainTextView);
            SecondaryText = itemView.findViewById(R.id.NotificationSecTextView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String maintext, secondarytext;

        maintext = this.NotiList.get(position).getTblUser().getName()+" has Posted on his Wall";
        secondarytext = "Go and Checkout his Post";

        holder.MainText.setText(maintext);
        holder.SecondaryText.setText(secondarytext);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return NotiList.size();
    }


}
