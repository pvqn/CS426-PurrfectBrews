package com.example.coffeeshop;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.coffeeshop.BillAdapter;
import com.example.coffeeshop.BillItem;
import com.example.coffeeshop.CoffeeCart;
import com.example.coffeeshop.MainActivity;
import com.example.coffeeshop.coffeeCartRecycleAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class historyPage extends Fragment implements coffeeCartRecycleAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<BillItem> billItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public historyPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_page, container, false);

        // Initialize the SwipeRefreshLayout
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        billAdapter = new BillAdapter(billItems, this);
        recyclerView.setAdapter(billAdapter);

        // Load data when the fragment is first created
        loadHistoryData();

        return rootView;
    }

    // Method to load history data (You can replace this with your own data loading mechanism)
    private void loadHistoryData() {
        billItems = ((MainActivity) requireActivity()).getBillItemsHistory();
        Collections.reverse(billItems);
        billAdapter.setBillItems(billItems);
        billAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        loadHistoryData();
        Log.d("history size",String.valueOf(billItems.size()));
        swipeRefreshLayout.setRefreshing(false); // Call this method to stop the refreshing animation
    }

    public void onItemClick(CoffeeCart coffeeCart) {

    }


}
