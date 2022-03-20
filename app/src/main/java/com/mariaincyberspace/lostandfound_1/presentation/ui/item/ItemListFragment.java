package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ItemRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemListFragment extends Fragment {

    private RecyclerView recyclerView;
    private AuthenticationRepositoryImpl authenticationRepository;
    private ItemRepositoryImpl itemRepository;
    private DatabaseReference reference;
    private ItemAdapter itemAdapter;
    private List<Item> itemArrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemListFragment newInstance(String param1, String param2) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ItemListLog: ", "");
        reference = FirebaseDatabase.getInstance().getReference().child(Literals.Nodes.ITEM_KEY);
        authenticationRepository = new AuthenticationRepositoryImpl(requireActivity().getApplication());
        itemRepository = new ItemRepositoryImpl(requireActivity().getApplication(), reference);
        itemArrayList = new ArrayList<>();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_ItemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("ItemListLog: ", "2");
        itemAdapter = new ItemAdapter(getContext(), itemArrayList);
        recyclerView.setAdapter(itemAdapter);
        getData();
        Log.d("ItemListLog: ", "3");
        return view;
    }


    public void getData() {
        itemRepository.getAllItems(items -> {
            Log.d("ItemListLog: ", "4");
            itemArrayList = items;
            itemAdapter.updateItems(itemArrayList);
            Log.d("ItemListLog: ", "5");
            Log.d("ItemListLog: list", itemArrayList.toString());
        } );
    }
}