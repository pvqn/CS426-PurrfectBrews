package com.example.coffeeshop;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class coffeeRewardRecycleAdapter extends RecyclerView.Adapter<coffeeRewardRecycleAdapter.ViewHolder> {
    private ArrayList<CoffeeCart> coffeeCartList;


    public coffeeRewardRecycleAdapter(ArrayList<CoffeeCart> coffeeCartList) {
        this.coffeeCartList = coffeeCartList;
    }


    @NonNull
    @Override
    public coffeeRewardRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the cart_item_layout.xml
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_list_reward, parent, false);
        return new coffeeRewardRecycleAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull coffeeRewardRecycleAdapter.ViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder
        CoffeeCart coffeeCart = coffeeCartList.get(position);
        holder.tvCoffeeName.setText(coffeeCart.getCoffee().getName() + " x" + String.valueOf(coffeeCart.getQuantity()));
        holder.tvPoint.setText("+ "+ String.valueOf(round(coffeeCart.getPrice() * 10))+" pts");
        holder.tvDayTime.setText(coffeeCart.getDateTime());

        String size, shot, singOrDou, hotOrCold;

        int sizeS = coffeeCart.getSize();
        if (sizeS == 1) size = "Small";
        else if (sizeS == 2) size = "Medium";
        else size = "Large";
        if (coffeeCart.getIsHot()) hotOrCold = "Hot";
        else hotOrCold = "Cold";
        if (coffeeCart.isSingle()) singOrDou = "Single";
        else singOrDou = "Double";
        holder.tvDescription.setText(singOrDou + " | " + hotOrCold + " | " + size);

        // Add other views and data bindings here
    }

    @Override
    public int getItemCount() {
        if (coffeeCartList == null) {
            return 0;
        }
        return coffeeCartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoffeeName;
        TextView tvDescription;
        TextView tvPoint;
        TextView tvDayTime;

        // Add other views here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from cart_item_layout.xml
            tvCoffeeName = itemView.findViewById(R.id.nameCoffee);
            tvDescription = itemView.findViewById(R.id.coffeeDesciption);
            tvPoint = itemView.findViewById(R.id.point);
            tvDayTime = itemView.findViewById(R.id.coffeeDayTime);
            // Initialize other views here
        }
    }

    public ArrayList<CoffeeCart> getData() {
        return coffeeCartList;
    }
}
