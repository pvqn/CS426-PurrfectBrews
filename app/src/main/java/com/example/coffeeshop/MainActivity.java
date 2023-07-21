package com.example.coffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = null;
    private Fragment currentFragment = null;
    private String name = "Nhu";
    private String email = "pvqn21@apcs.fitus.edu.vn";
    private String phone = "+1234567890";
    private String address = "227 Nguyen Van Cu, quan 5, TPHCM";
    private int points = 10000;
    private static CoffeeShopDatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        currentFragment = new showingCoffee();
        databaseHelper = new CoffeeShopDatabaseHelper(this);
        databaseHelper.insertCoffeeData();

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, currentFragment).commit();
    }

    public static CoffeeShopDatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void switchToFragmentSignUp() {
        if (currentFragment instanceof signUp) return;
        signUp cur = new signUp();
        if (currentFragment instanceof signIn) {
            if (fragmentManager.findFragmentByTag("signUp") == null)
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).addToBackStack("signIn").commit();
            else fragmentManager.popBackStack("signUp", 0);
        } else fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).commit();
        currentFragment = cur;
    }

    public void switchToFragmentSignIn() {
        if (currentFragment instanceof signIn) return;
        signIn cur = new signIn();
        if (currentFragment instanceof signUp) {
            if (fragmentManager.findFragmentByTag("signIn") == null)
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).addToBackStack("signUp").commit();
            else fragmentManager.popBackStack("signIn", 0);
        } else fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).commit();
        currentFragment = cur;
    }

    public void switchToFragmentShowingCoffee() {
        if (currentFragment instanceof showingCoffee) return;
        showingCoffee cur = new showingCoffee();
        if (fragmentManager.findFragmentByTag("showingCoffee") == null)
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).addToBackStack("showingCoffee").commit();
        else fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).commit();
        currentFragment = cur;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}