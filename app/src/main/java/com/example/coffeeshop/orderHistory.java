package com.example.coffeeshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link orderHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class orderHistory extends Fragment implements coffeeCartRecycleAdapter.OnItemClickListener, BottomNavigationView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MyPageAdapter vpa;
    ViewPager viewPager2;

    public orderHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment orderHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static orderHistory newInstance(String param1, String param2) {
        orderHistory fragment = new orderHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!(vpa == null)) {

            vpa.notifyDataSetChanged();


        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_order_history, container, false);
        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        orderPage orderFragment = new orderPage();
        historyPage historyFragment = new historyPage();

        TabLayout tabLayout;
        tabLayout=rootView.findViewById(R.id.tabLayout);

        viewPager2=rootView.findViewById(R.id.viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(orderFragment);
        fragments.add(historyFragment);
        String[] tabTitles = {"On going", "History"};

        vpa=new MyPageAdapter(getChildFragmentManager(), fragments, tabTitles);


        viewPager2.setAdapter(vpa);

        tabLayout.setupWithViewPager(viewPager2);

        return rootView;
    }
    public void onItemClick(CoffeeCart coffeeCart) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {// Respond to navigation item 1 click
            ((MainActivity)requireActivity()).switchToFragmentShowingCoffee();
            return true;
        } else if (itemId == R.id.gift) {// Respond to navigation item 2 click
            ((MainActivity)requireActivity()).switchToFragmentGift();
            return true;
        }
        return false;
    }



}