package com.mariaincyberspace.lostandfound_1.presentation.ui.item;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.ItemRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ItemListFragment extends Fragment implements ItemAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ItemRepositoryImpl itemRepository;
    private ItemAdapter itemAdapter;
    private List<Item> itemArrayList;
    private SearchView searchView;

    public ItemListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ItemListLog: ", "");
        itemRepository = new ItemRepositoryImpl();
        itemArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_ItemList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("ItemListLog: ", "2");
        itemAdapter = new ItemAdapter(getContext(), itemArrayList, this);
        recyclerView.setAdapter(itemAdapter);
        getData();
        searchView = view.findViewById(R.id.searchView_SearchItem);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(getOnQueryTextListener());
        Log.d("ItemListLog: ", "3");
        return view;
    }

    public SearchView.OnQueryTextListener getOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    onSearchViewEmpty();
                }
                filterText(newText);
                return true;
            }
        };
    }

    private void filterText(String text) {
        List<Item> filteredList = new ArrayList<>();
        for (Item i: itemArrayList) {
            if (i.getName().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT)) ||
                i.getAddress().toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                    filteredList.add(i);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "No matches", Toast.LENGTH_LONG).show();
        } else {
            itemAdapter.updateItems(filteredList);
            itemArrayList = filteredList;
        }
    }

    public void getData() {
        itemRepository.getAllItems(items -> {
            Log.d("ItemListLog: ", "4");
            itemArrayList = items;
            Collections.reverse(itemArrayList);
            itemAdapter.updateItems(itemArrayList);
            Log.d("ItemListLog: ", "5");
            Log.d("ItemListLog: list", itemArrayList.toString());
        } );
    }

    @Override
    public void onItemClick(int position) {
        Item item = itemArrayList.get(position);
        Intent intent = new Intent(requireActivity(), ItemActivity.class);
        intent.putExtra(Literals.BundleName.SELECTED_ITEM, item);
        startActivity(intent);
    }

    public void onSearchViewEmpty() {
        getData();
    }

}