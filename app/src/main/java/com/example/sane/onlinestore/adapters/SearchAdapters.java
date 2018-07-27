package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.sane.onlinestore.Events.UserEvent;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.ProfileSearchResult;
import com.example.sane.onlinestore.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapters extends RecyclerView.Adapter<SearchAdapters.ViewHolder> implements Filterable{
    private ArrayList<TblUser> UserList;
    private ArrayList<TblUser> UserListFull;
    Context context;
    String searchString;

    public SearchAdapters(Context context, ArrayList<TblUser> arrayList, String s) {
        this.context = context;
        this.searchString = s;
        this.UserList = arrayList;
        UserListFull = new ArrayList<>(arrayList);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        TblUser currentItem = UserList.get(position);
        String AN;

        holder.ArtistName.setText(this.UserList.get(position).getName().toString());
        AN = this.UserList.get(position).getName();
        Log.i("searchAdaper", "onBindViewHolder: " + AN);
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                EventBus.getDefault().postSticky(new UserEvent(UserList, position));
                Intent intent = new Intent(context, ProfileSearchResult.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }

    public void AddUserList(ArrayList<TblUser> tblUsers) {
        this.UserList = tblUsers;
        UserListFull = new ArrayList<>(tblUsers);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return UserFilter;
    }
    private Filter UserFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TblUser> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {

            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (TblUser user: UserListFull){
                    if(user.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(user);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            UserList.clear();
            UserList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public void setArtistList(ArrayList<TblUser> artistList) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ArtistName;

        public ViewHolder(View itemView) {
            super(itemView);
            ArtistName = itemView.findViewById(R.id.ArtistName);
        }
    }

    public void filterList(ArrayList<TblUser> filteredList) {
        UserList = filteredList;
        notifyDataSetChanged();
    }
}
