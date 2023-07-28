package com.example.coffeeshop;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private ArrayList<BillItem> BillItems;
    private coffeeCartRecycleAdapter.OnItemClickListener mListener;

    public BillAdapter(ArrayList<BillItem> BillItems, coffeeCartRecycleAdapter.OnItemClickListener listener) {
        this.BillItems = BillItems;
        Collections.reverse(BillItems);
        mListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillItem billItem = BillItems.get(position);
        holder.textBillItemName.setText(billItem.getName());
        holder.textBillItemDescription.setText(billItem.getDescription());


        holder.textPrice.setText("$ "+String.valueOf(billItem.getPrice()));
        coffeeCartRecycleAdapter CoffeeCartRecycleAdapter = new coffeeCartRecycleAdapter(billItem.getCoffeeCarts());
        CoffeeCartRecycleAdapter.setOnItemClickListener(mListener); // Pass the listener here
        holder.recyclerViewCoffeeCartRecycles.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerViewCoffeeCartRecycles.setAdapter(CoffeeCartRecycleAdapter);
        // Set the visibility of the expanded info layout based on the expanded state
        if (billItem.isExpanded()) {
            holder.layoutExpandedInfo.setVisibility(View.VISIBLE);

        } else {
            holder.layoutExpandedInfo.setVisibility(View.GONE);
        }
        //Log.d("BillAdapter", "Item at position " + position + " isExpanded: " + billItem.isExpanded());
        //Log.d("BillAdapter", "Item at position " + position + " coffeeCarts count: " + billItem.getCoffeeCarts().size());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle the expanded state when the item view is clicked
                boolean expanded = billItem.isExpanded();
                billItem.setExpanded(!expanded);
                if (billItem.isExpanded()) {
                    holder.layoutExpandedInfo.setVisibility(View.VISIBLE);

                } else {
                    holder.layoutExpandedInfo.setVisibility(View.GONE);
                }
            notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BillItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textBillItemName;
        TextView textBillItemDescription;
        TextView textPrice;
        LinearLayout layoutExpandedInfo;
        RecyclerView recyclerViewCoffeeCartRecycles;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textBillItemName = itemView.findViewById(R.id.textMenuItemName);
            textBillItemDescription = itemView.findViewById(R.id.textMenuItemDescription);
            textPrice=itemView.findViewById(R.id.textMenuItemPrice);
            layoutExpandedInfo = itemView.findViewById(R.id.layoutExpandedInfo);
            recyclerViewCoffeeCartRecycles = itemView.findViewById(R.id.recyclerViewSubItem);

            // Initialize other views of the menu item here if needed
        }
    }
    public void removeItem(int position) {
        BillItems.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(BillItem item, int position) {
        BillItems.add(position, item);
        notifyItemInserted(position);
    }
    public void setBillItems(ArrayList<BillItem> billItems)
    {
        this.BillItems = billItems;
    }
public ArrayList<BillItem> getBillItems(){
    return BillItems;
}}

