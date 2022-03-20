package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import java.util.List;


// todo: finish writing this class
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    Context context;
    List<Item> list;

    public ItemAdapter(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
        Log.d("AdapterLog: ", "created adapter");
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // todo: create item xml to be specified in here
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        Log.d("AdapterLog: ", "creating view holder");
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = list.get(position);
        holder.name.setText(item.getName());
        CharSequence sequence = item.getLatitude() + " : " + item.getLongitude();
        holder.description.setText(sequence);
        Glide.with(context).load(list.get(position).getPhotoUri()).into(holder.picture);
        Log.d("AdapterLog: ", item.toString());
        Log.d("AdapterLog: ", holder.toString());
        // todo: figure out how to set picture url
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {

        TextView name, description;
        ImageView picture;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_ItemName);
            description = itemView.findViewById(R.id.textView_ItemDescription);
            picture = itemView.findViewById(R.id.imageView_ItemPicture);
        }

    }

    public void updateItems(List<Item> newList) {
        list.clear();
        list.addAll(newList);
        this.notifyDataSetChanged();
    }

}
