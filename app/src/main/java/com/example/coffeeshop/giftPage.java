package com.example.coffeeshop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link giftPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class giftPage extends Fragment implements BottomNavigationView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView totalPoint;
    private ArrayList<ImageView> cardProgressImage=new ArrayList<>();
    private TextView cardProgressText;
    private RecyclerView recyclerView;
    private MaterialCardView materialCardView;
    private int cardProgress;

    public giftPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment giftPage.
     */
    // TODO: Rename and change types and number of parameters
    public static giftPage newInstance(String param1, String param2) {
        giftPage fragment = new giftPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.fragment_gift_page, container, false);
        ArrayList<BillItem> billItem = ((MainActivity) requireActivity()).getBillItemsHistory();
        ArrayList<CoffeeCart> coffeeCarts = new ArrayList<>();
        for (int i = 0; i < billItem.size(); ++i) {
            for (int j = 0; j < billItem.get(i).getCoffeeCarts().size(); ++j)
                coffeeCarts.add(billItem.get(i).getCoffeeCarts().get(j));
        }
        totalPoint = rootView.findViewById(R.id.point);
        totalPoint.setText(String.valueOf(((MainActivity)requireActivity()).getScore()));
        cardProgress = ((MainActivity) requireActivity()).getCardProgress();

        cardProgressText = rootView.findViewById(R.id.cardProgress);
        cardProgressText.setText(String.valueOf(cardProgress) + "/8");

        cardProgressImage.add(rootView.findViewById(R.id.a));
        cardProgressImage.add(rootView.findViewById(R.id.b));
        cardProgressImage.add(rootView.findViewById(R.id.c));
        cardProgressImage.add(rootView.findViewById(R.id.d));
        cardProgressImage.add(rootView.findViewById(R.id.e));
        cardProgressImage.add(rootView.findViewById(R.id.g));
        cardProgressImage.add(rootView.findViewById(R.id.h));

        setCardProgress();

        materialCardView=rootView.findViewById(R.id.loyaltyCard);
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).updateCardProgress(8,true);
                cardProgress-=8;
                setCardProgress();
                cardProgressText.setText(String.valueOf(cardProgress) + "/8");
                ((MainActivity)requireActivity()).updateScore(8*2);
                totalPoint.setText(String.valueOf(((MainActivity)requireActivity()).getScore()));

            }
        });

        recyclerView = rootView.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        coffeeRewardRecycleAdapter cAdapter = new coffeeRewardRecycleAdapter(coffeeCarts);
        recyclerView.setAdapter(cAdapter);

        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);

        return rootView;
    }

    private void setCardProgress() {
        if (cardProgress > 8) {
            for (int i = 0; i < cardProgressImage.size(); ++i) {
                cardProgressImage.get(i).setImageResource(R.drawable.icons8_coffee_cup_100);
            }
        } else {

            for (int i = 0; i < cardProgress; ++i) {
                cardProgressImage.get(i).setImageResource(R.drawable.icons8_coffee_cup_100);
            }
            for (int i = cardProgress; i < cardProgressImage.size(); ++i) {
                cardProgressImage.get(i).setImageResource(R.drawable.icons8_coffee_cup_100_1);
            }

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {// Respond to navigation item 1 click
            ((MainActivity)requireActivity()).switchToFragmentShowingCoffee();
            return true;
        } else if (itemId == R.id.history) {// Respond to navigation item 2 click
            ((MainActivity)requireActivity()).switchToFragmentOrderHistory();
            return true;
        }
        return false;
    }
}