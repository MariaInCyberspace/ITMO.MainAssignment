package com.mariaincyberspace.lostandfound_1;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;

public class ItemAdapter extends ListAdapter<Item, ItemAdapter.ItemHolder> {

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    protected ItemAdapter(@NonNull DiffUtil.ItemCallback diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {

    }


}
