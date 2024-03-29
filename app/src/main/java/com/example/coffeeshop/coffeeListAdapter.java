package com.example.coffeeshop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.List;


public class coffeeListAdapter extends ArrayAdapter<Coffee> {
    public interface OnCoffeeItemClickListener {
        void onCoffeeItemClick(Coffee coffee);
    }
    private Context context;
    private List<Coffee> coffees;
    private OnCoffeeItemClickListener clickListener;

    public coffeeListAdapter(Context context, List<Coffee> coffees, OnCoffeeItemClickListener onCoffeeItemClickListener) {
        super(context, R.layout.list_item_coffee, coffees);
        this.context = context;
        this.coffees = coffees;
        this.clickListener=onCoffeeItemClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_coffee, parent, false);

            //set id for gridview
            convertView.setTag(position);
        }

        Coffee coffee = coffees.get(position);

        // Bind movie data to the views in the list item layout
        TextView titleTextView = convertView.findViewById(R.id.nameCoffee);
        TextView priceTextView = convertView.findViewById(R.id.priceCoffee);
        ImageView posterImageView = convertView.findViewById(R.id.imageCoffee);

        titleTextView.setText(coffee.getName());
        priceTextView.setText("$"+String.valueOf(coffee.getPrice()));
        posterImageView.setImageResource(coffee.getImage());
        // Load and display the poster image using a library like Picasso or Glide
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null)
                {
                    clickListener.onCoffeeItemClick(coffees.get((int)v.getTag()));
                }

            }
        });
        return convertView;
    }
}

