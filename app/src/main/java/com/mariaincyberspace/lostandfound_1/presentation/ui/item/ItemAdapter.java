package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import android.content.Context;
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


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    Context context;
    List<Item> list;
    private OnItemClickListener mOnItemClickListener;

    public ItemAdapter(Context context, List<Item> list, OnItemClickListener mOnItemClickListener) {
        this.context = context;
        this.list = list;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ItemHolder(v, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = list.get(position);
        holder.name.setText(item.getName());
        CharSequence sequence = "Lost in " + item.getAddress();
        holder.description.setText(sequence);
        Glide.with(context).load(list.get(position).getPhotoUri()).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, description;
        ImageView picture;
        OnItemClickListener onItemClickListener;


        public ItemHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textView_ItemName);
            description = itemView.findViewById(R.id.textView_ItemDescription);
            picture = itemView.findViewById(R.id.imageView_ItemPicture);
            this.onItemClickListener = onItemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getBindingAdapterPosition());
        }
    }

    public void updateItems(List<Item> newList) {
        list.clear();
        list.addAll(newList);
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
