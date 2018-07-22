package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sane.onlinestore.Models.TblArtistPost;
import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ArtistWallAdapters extends RecyclerView.Adapter<ArtistWallAdapters.ViewHolder> {

    private List<TblArtistPost> tblArtistPosts;
    private TblUser tblUser;
    private Context context;

    public ArtistWallAdapters() {

    }

    public ArtistWallAdapters(List<TblArtistPost> tblArtistPosts, TblUser tblUser, Context context) {
        this.tblArtistPosts = tblArtistPosts;
        this.tblUser = tblUser;
        this.context = context;
    }

    public void setList(List<TblArtistPost> list,TblUser tblUser) {
        this.tblArtistPosts = list;
        this.tblUser = tblUser;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView PostDesc,Artistname;

        public ViewHolder(View itemView) {
            super(itemView);
            PostDesc = itemView.findViewById(R.id.PostDescTextView);
            Artistname=itemView.findViewById(R.id.ArtistPostNameTextView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_post_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.PostDesc.setText(this.tblArtistPosts.get(position).getPostDesc());
        holder.Artistname.setText(tblUser.getName());

    }

    @Override
    public int getItemCount() {
        return tblArtistPosts.size();
    }
}
