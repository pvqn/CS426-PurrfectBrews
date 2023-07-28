package com.example.coffeeshop;

import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.coffeeshop.NestedGridView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link showingCoffee#newInstance} factory method to
 * create an instance of this fragment.
 */
// TODO: implement navigation bar, set background item of navigation bar to pale
public class showingCoffee extends Fragment implements BottomNavigationView.OnItemSelectedListener{

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
    private ArrayList<ImageView> cardProgressImage=new ArrayList<>();
    private MaterialCardView materialCardView;
    private int cardProgress;
    private TextView cardProgressText;

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

        ArrayList<BillItem> billItem = ((MainActivity) requireActivity()).getBillItemsHistory();
        ArrayList<CoffeeCart> coffeeCarts = new ArrayList<>();
        for (int i = 0; i < billItem.size(); ++i) {
            for (int j = 0; j < billItem.get(i).getCoffeeCarts().size(); ++j)
                coffeeCarts.add(billItem.get(i).getCoffeeCarts().get(j));
        }

        cardProgress = ((MainActivity) requireActivity()).getCardProgress();

        cardProgressText = rootView.findViewById(R.id.cardProgress);
        cardProgressText.setText(String.valueOf(cardProgress) + "/8");

        cardProgressImage.add(rootView.findViewById(R.id.a));
        cardProgressImage.add(rootView.findViewById(R.id.b));
        cardProgressImage.add(rootView.findViewById(R.id.c));
        cardProgressImage.add(rootView.findViewById(R.id.d));
        cardProgressImage.add(rootView.findViewById(R.id.e));
        cardProgressImage.add(rootView.findViewById(R.id.f));
        cardProgressImage.add(rootView.findViewById(R.id.g));
        cardProgressImage.add(rootView.findViewById(R.id.h));

        setCardProgress();

        materialCardView=rootView.findViewById(R.id.loyaltyCard);
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardProgress>=8) {
                    ((MainActivity) requireActivity()).updateCardProgress(8, true);
                    cardProgress -= 8;
                    setCardProgress();
                    cardProgressText.setText(String.valueOf(cardProgress) + "/8");
                    ((MainActivity) requireActivity()).updateScore(8 * 2, false);
                    Toast.makeText(requireContext(), "+16 points. Your points: "+ String.valueOf(((MainActivity) requireActivity()).getScore()), Toast.LENGTH_SHORT).show();
                }
            }
        });


        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        TextView welcome=rootView.findViewById(R.id.welcome);
        welcome.setText("How are you today, " + ((MainActivity)requireActivity()).getName()+"?");
        ImageView imgR=rootView.findViewById(R.id.imgR);
        TextView nameR=rootView.findViewById(R.id.nameR);
        ImageView profile=rootView.findViewById(R.id.profile);
        ImageView myCart = rootView.findViewById(R.id.imageCartView);
        TextView coffeeDescription= rootView.findViewById(R.id.coffeeDescription);

        myCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)requireActivity()).switchToFragmentMyCart();
            }
        });

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
        coffeeDescription.setText("\""+ randomC.getDescription() + "\"");


        ViewSwitcher viewSwitcher = rootView.findViewById(R.id.viewSwitcher);
        viewSwitcher.setInAnimation(requireContext(), android.R.anim.slide_in_left);
        viewSwitcher.setOutAnimation(requireContext(), android.R.anim.slide_out_right);

        LinearLayout coffeeContainer = rootView.findViewById(R.id.coffeeContainer);
        NestedGridView gridCoffeeContainer = rootView.findViewById(R.id.gridCoffeeContainer);

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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.history) {// Respond to navigation item 1 click
            ((MainActivity)requireActivity()).switchToFragmentOrderHistory();
            return true;
        } else if (itemId == R.id.gift) {// Respond to navigation item 2 click
            ((MainActivity)requireActivity()).switchToFragmentGift();
            return true;
        }
        return false;
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
}