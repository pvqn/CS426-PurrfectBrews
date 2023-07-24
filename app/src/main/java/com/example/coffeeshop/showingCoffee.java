package com.example.coffeeshop;

import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.coffeeshop.NestedGridView;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link showingCoffee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class showingCoffee extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<Coffee> coffeeList;
    private CoffeeShopDatabaseHelper dbHelper;
    private int originalCardHeight = 0;
    private Coffee selectedCoffee;
    public showingCoffee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment showingCoffee.
     */
    public static showingCoffee newInstance(String param1, String param2) {
        showingCoffee fragment = new showingCoffee();
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
        View rootView = inflater.inflate(R.layout.fragment_showing_coffee, container, false);
        dbHelper = MainActivity.getDatabaseHelper();
        coffeeList = dbHelper.getAllCoffeeTypes();

        TextView welcome=rootView.findViewById(R.id.welcome);
        welcome.setText("How are you today, " + ((MainActivity)requireActivity()).getName()+"?");
        ImageView imgR=rootView.findViewById(R.id.imgR);
        TextView nameR=rootView.findViewById(R.id.nameR);
        TextView priceR=rootView.findViewById(R.id.priceR);
        ImageView profile=rootView.findViewById(R.id.profile);

        // random coffee item
        int random=(new Random()).nextInt(9-0)+0;
        Coffee randomC=coffeeList.get(random);
        imgR.setImageResource(randomC.getImage());
        imgR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change to detail fragment
                selectedCoffee=randomC;
                ((MainActivity)requireActivity()).setSelectedCoffee(selectedCoffee);
                ((MainActivity)requireActivity()).switchToFragmentDetailCoffee();
            }
        });
        nameR.setText(randomC.getName());
        priceR.setText("$"+String.valueOf(randomC.getPrice()));


        ViewSwitcher viewSwitcher = rootView.findViewById(R.id.viewSwitcher);
        viewSwitcher.setInAnimation(requireContext(), android.R.anim.slide_in_left);
        viewSwitcher.setOutAnimation(requireContext(), android.R.anim.slide_out_right);

        LinearLayout coffeeContainer = rootView.findViewById(R.id.coffeeContainer);
        NestedGridView gridCoffeeContainer = rootView.findViewById(R.id.gridCoffeeContainer);
        CardView cardView=rootView.findViewById(R.id.cardView);

       coffeeListAdapter adapter = new coffeeListAdapter(requireContext(), coffeeList,(coffeeListAdapter.OnCoffeeItemClickListener) requireActivity());

        int count = 0;
        for (Coffee coffee : coffeeList) {
            View coffeeItemView = inflater.inflate(R.layout.list_item_coffee, container, false);
            TextView nameTextView = coffeeItemView.findViewById(R.id.nameCoffee);
            TextView priceTextView = coffeeItemView.findViewById(R.id.priceCoffee);

            //set id for horizontalScrollView
            // TODO: update id for coffeeListItem

            coffeeItemView.setTag((count+1)*10);

            ImageView imageView = coffeeItemView.findViewById(R.id.imageCoffee);

            nameTextView.setText(coffee.getName());
            imageView.setImageResource(coffee.getImage());
            priceTextView.setText("$"+String.valueOf(coffee.getPrice()));
            coffeeItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index=((int)v.getTag())/10-1;
                    selectedCoffee=coffeeList.get(index);
                    ((MainActivity)requireActivity()).setSelectedCoffee(selectedCoffee);
                    ((MainActivity)requireActivity()).switchToFragmentDetailCoffee();
                }
            });

            //set onClick


            if (count < 5) {
                coffeeContainer.addView(coffeeItemView);
                ++count;
            } else break;
        }
        TextView viewAll = rootView.findViewById(R.id.textViewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewSwitcher.getNextView().getId() == R.id.gridCoffeeContainer) {
                    viewSwitcher.showPrevious();
                    viewAll.setText("Collapse");
                    gridCoffeeContainer.setIsHorizontalView(false);
                    gridCoffeeContainer.setAdapter(adapter);

                } else {
                    viewSwitcher.showNext();
                    viewAll.setText("View All");
                    gridCoffeeContainer.setIsHorizontalView(true);
                    gridCoffeeContainer.setAdapter(adapter);

                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).switchToFragmentProfile();
            }
        });
        return rootView;
    }


}