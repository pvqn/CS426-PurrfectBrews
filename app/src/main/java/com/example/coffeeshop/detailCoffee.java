package com.example.coffeeshop;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detailCoffee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detailCoffee extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Coffee coffee;
    private double priceOff;
    private double quantityCur;
    private boolean isSingle;
    private boolean isHot;
    private int size;

    public detailCoffee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detailCoffee.
     */
    public static detailCoffee newInstance(String param1, String param2) {
        detailCoffee fragment = new detailCoffee();
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
        // TODO: update iconTint when set enable=false
        View rootView = inflater.inflate(R.layout.fragment_detail_coffee, container, false);

        ImageView image = rootView.findViewById(R.id.image);
        TextView name = rootView.findViewById(R.id.coffeName);

        ImageView minus = rootView.findViewById(R.id.buttonMinus);
        ImageView plus = rootView.findViewById(R.id.buttonPlus);
        TextView quantity = rootView.findViewById(R.id.quantity);

        ImageButton small = rootView.findViewById(R.id.buttonSmall);
        ImageButton medium = rootView.findViewById(R.id.buttonMedium);
        ImageButton large = rootView.findViewById(R.id.buttonLarge);

        Button singleS = rootView.findViewById(R.id.buttonSingle);
        Button doubleS = rootView.findViewById(R.id.buttonDouble);

        ImageButton hot = rootView.findViewById(R.id.buttonHot);
        ImageButton cold = rootView.findViewById(R.id.buttonCold);

        TextView price = rootView.findViewById(R.id.priceCoffee);
        Button addCart = rootView.findViewById(R.id.buttonAddCart);

        coffee = ((MainActivity) requireActivity()).getSelectedCoffee();

        image.setImageResource(coffee.getImage());
        name.setText(coffee.getName());
        price.setText("$" + String.valueOf(coffee.getPrice()));
        priceOff = coffee.getPrice();
        quantityCur = Integer.valueOf(quantity.getText().toString());
        isSingle=true;
        isHot = true;

        MaterialToolbar toolbar = rootView.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)requireActivity()).switchToFragmentShowingCoffee();
            }
        });


        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.cart) {
                    ((MainActivity)requireActivity()).switchToFragmentMyCart();
                    return true;
                }
                return false;}

        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = Integer.valueOf(quantity.getText().toString());
                if (cur > 1) {
                    --cur;
                    quantity.setText(String.valueOf(cur));
                    quantityCur=cur;
                    price.setText(String.valueOf("$" + calPrice()));
                }
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = Integer.valueOf(quantity.getText().toString());

                    ++cur;
                    quantity.setText(String.valueOf(cur));
                    quantityCur=cur;
                    price.setText(String.valueOf("$" +calPrice()));

            }
        });

        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceOff=coffee.getPrice();
                size = 1;
                small.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
                medium.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                large.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                price.setText(String.valueOf("$" +calPrice()));
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceOff=coffee.getPrice()+0.5;
                size = 2;
                medium.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
                small.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                large.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                price.setText(String.valueOf("$" +calPrice()));
            }
        });

        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceOff=coffee.getPrice()+1.0;
                size = 3;
                large.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
                small.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                medium.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                price.setText(String.valueOf("$" +calPrice()));
            }
        });

        singleS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                singleS.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
                doubleS.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                price.setText(String.valueOf("$" +calPrice()));
            }
        });

        doubleS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=false;
                singleS.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                doubleS.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
                price.setText(String.valueOf("$" +calPrice()));
            }
        });
        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHot = true;
                cold.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                hot.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
            }
        });
        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isHot = false;
                hot.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.white1));
                cold.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.pale));
            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoffeeCart coffeeCart = new CoffeeCart();
                coffeeCart.setCoffee(coffee);
                coffeeCart.setQuantity((int)quantityCur);
                coffeeCart.setIsHot(isHot);
                coffeeCart.setSingle(isSingle);
                coffeeCart.setSize(size);
                coffeeCart.setPrice(calPrice());
                ((MainActivity)requireActivity()).getCoffeeCarts().add(coffeeCart);
                Toast.makeText(requireContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                ((MainActivity)requireActivity()).switchToFragmentMyCart();
            }
        });
        return rootView;
    }

    public double getPriceCur() {
        return priceOff;
    }

    public void setPriceCur(double priceCur) {
        this.priceOff = priceCur;
    }

    public double getQuantityCur() {
        return quantityCur;
    }

    public void setQuantityCur(int quantityCur) {
        this.quantityCur = quantityCur;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }
    public double calPrice()
    {
        double t;
        if (isSingle) t=0; else t=1;
        return priceOff*quantityCur+0.5*quantityCur*t;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}