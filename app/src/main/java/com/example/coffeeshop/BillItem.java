package com.example.coffeeshop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BillItem {
    private String name;
    private String description;
    private double price;
    private boolean expanded=false;
    private ArrayList<CoffeeCart> CoffeeCarts; // List of sub-items or detailed information

    public BillItem(String name, String description, double price, ArrayList<CoffeeCart> CoffeeCarts) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.expanded = false;
        this.CoffeeCarts = CoffeeCarts;
    }

    // Add getters and setters for other fields if needed

    public ArrayList<CoffeeCart> getCoffeeCarts() {
        return CoffeeCarts;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

