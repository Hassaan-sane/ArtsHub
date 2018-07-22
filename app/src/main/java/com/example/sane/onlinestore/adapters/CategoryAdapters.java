package com.example.sane.onlinestore.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sane.onlinestore.Events.HomeEvent;
import com.example.sane.onlinestore.Fragments.HomeFragment;
import com.example.sane.onlinestore.MainActivity;
import com.example.sane.onlinestore.Models.TblCategory;
import com.example.sane.onlinestore.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Sane on 3/6/2018.
 */

public class CategoryAdapters extends RecyclerView.Adapter<CategoryAdapters.ViewHolder> {
    private ArrayList<TblCategory> listItems;
    private Context context;
    static int Category= 0;

    public CategoryAdapters(Context context, ArrayList<TblCategory> arrayList) {
        this.context=context;
        this.listItems=arrayList;
    }
    public void setItemList(ArrayList<TblCategory> arrayList) {
        this.listItems = arrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_categoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_categoryName = itemView.findViewById(R.id.tv_categoryName);
        }
    }

    @Override
    public CategoryAdapters.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        CategoryAdapters.ViewHolder viewHolder = new CategoryAdapters.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tv_categoryName.setText(this.listItems.get(position).getCategoryName());
        Category = this.listItems.get(position).getCategoryId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "ajsfsahfsaghfvasj "+position, Toast.LENGTH_SHORT).show();

                Gson gson = new Gson();
                String string = gson.toJson(listItems.get(position));

                //EventBus.builder().build().post(CategoryEvent.class);

                EventBus.getDefault().postSticky(new HomeEvent(listItems.get(position).getCategoryId()));

                ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}
