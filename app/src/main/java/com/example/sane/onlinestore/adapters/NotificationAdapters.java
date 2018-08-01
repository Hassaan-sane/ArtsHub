package com.example.sane.onlinestore.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.Events.NotiEvent;
import com.example.sane.onlinestore.Fragments.NotificationFragment;
import com.example.sane.onlinestore.Models.TblPostNotification;
import com.example.sane.onlinestore.NotificationActivity;
import com.example.sane.onlinestore.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NotificationAdapters extends RecyclerView.Adapter<NotificationAdapters.ViewHolder>{
    private ArrayList<TblPostNotification> NotiList = new ArrayList<>();
private Context context;
    public NotificationAdapters(ArrayList<TblPostNotification> NotiList, Context applicationContext) {
        this.NotiList=NotiList;
        context=applicationContext;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String maintext, secondarytext;

        maintext = this.NotiList.get(position).getTblUser().getName()+" has Posted on his Wall";
        secondarytext = "Go and Checkout his Post";

        holder.MainText.setText(maintext);
        holder.SecondaryText.setText(secondarytext);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new NotiEvent(NotiList.get(position),position));

                ((NotificationActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerNoti, new NotificationFragment())
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return NotiList.size();
    }


}
