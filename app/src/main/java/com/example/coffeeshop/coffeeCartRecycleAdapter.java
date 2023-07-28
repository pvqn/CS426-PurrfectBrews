package com.example.coffeeshop;

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

public class coffeeCartRecycleAdapter extends RecyclerView.Adapter<coffeeCartRecycleAdapter.ViewHolder>{
    public interface OnItemClickListener {
        void onItemClick(CoffeeCart coffeeCart);
    }
    private ArrayList<CoffeeCart> coffeeCartList;
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public coffeeCartRecycleAdapter(ArrayList<CoffeeCart> coffeeCartList) {
        this.coffeeCartList = coffeeCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the cart_item_layout.xml
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_list_cart, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Bind data to the views in the ViewHolder
        CoffeeCart coffeeCart = coffeeCartList.get(position);
        holder.tvCoffeeName.setText(coffeeCart.getCoffee().getName()+" x"+String.valueOf(coffeeCart.getQuantity()));
        holder.tvPrice.setText("$ " + String.valueOf(coffeeCart.getPrice()));
        holder.tvImage.setImageResource(coffeeCart.getCoffee().getImage());
        String size, shot, singOrDou, hotOrCold;

        int sizeS = coffeeCart.getSize();
        if (sizeS ==  1) size = "Small"; else if (sizeS == 2) size = "Medium"; else size = "Large";
        if (coffeeCart.getIsHot()) hotOrCold = "Hot"; else hotOrCold = "Cold";
        if (coffeeCart.isSingle()) singOrDou = "Single"; else singOrDou = "Double";
        holder.tvDescription.setText(singOrDou + " | " + hotOrCold + " | " + size);
        int backgroundColor = coffeeCart.isSelected() ?
                ContextCompat.getColor(holder.itemView.getContext(), R.color.darkPale) :
                ContextCompat.getColor(holder.itemView.getContext(), R.color.pale);
        holder.itemView.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CoffeeCart selectedCoffeeCart = coffeeCartList.get(position);
                    selectedCoffeeCart.setSelected(!selectedCoffeeCart.isSelected());

                    int backgroundColor = selectedCoffeeCart.isSelected() ?
                            ContextCompat.getColor(v.getContext(), R.color.darkPale) :
                            ContextCompat.getColor(v.getContext(), R.color.pale);
                    v.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
                    mListener.onItemClick(coffeeCartList.get(position));

                }
            }
        });

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
        TextView tvPrice;
        ImageView tvImage;
        // Add other views here

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from cart_item_layout.xml
            tvCoffeeName = itemView.findViewById(R.id.nameCoffee);
            tvDescription = itemView.findViewById(R.id.coffeeDesciption);
            tvPrice = itemView.findViewById(R.id.price);
            tvImage = itemView.findViewById(R.id.imageView);

            // Initialize other views here
        }
    }
    public void removeItem(int position) {
        coffeeCartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(CoffeeCart item, int position) {
        coffeeCartList.add(position, item);
        notifyItemInserted(position);
    }
    public ArrayList<CoffeeCart> getData() {
        return coffeeCartList;
    }
}

