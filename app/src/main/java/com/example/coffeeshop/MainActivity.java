package com.example.coffeeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements coffeeListAdapter.OnCoffeeItemClickListener{
    private FragmentManager fragmentManager = null;
    private Fragment currentFragment = null;
    private String name = "Nhu";
    private String email = "pvqn21@apcs.fitus.edu.vn";
    private String phone = "+1234567890";
    private String address = "227 Nguyen Van Cu, quan 5, TPHCM";
    private int points = 10000;
    private Coffee selectedCoffee;
    private static CoffeeShopDatabaseHelper databaseHelper;
    private ArrayList<CoffeeCart> coffeeCarts = new ArrayList<>();
    private ArrayList<BillItem> billItems=new ArrayList<>();
    private ArrayList<BillItem> billItemsHistory=new ArrayList<>();

    private ArrayList<CoffeeCart> orderedCoffeeCarts = new ArrayList<>();
    private int score = 1000;
    private int cardProgress=0;

    public void updateScore(int t)
    {
        score+=t;
    }
    public void updateCardProgress(int t, boolean isMinus)
    {
        if (!isMinus)
        cardProgress+=t;
        else cardProgress-=8;
    }

    public int getCardProgress()
    {
        return cardProgress;
    }
    public void addOrderedCoffeeCarts(CoffeeCart coffeeCart)
    {
        orderedCoffeeCarts.add(coffeeCart);
    }
    public ArrayList<CoffeeCart> getOrderedCoffeeCarts()
    {
        return orderedCoffeeCarts;
    }

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
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, cur).commit();
        else fragmentManager.popBackStack("showingCoffee",0);
        currentFragment = cur;
    }

    public void switchToFragmentProfile(){
        if (currentFragment instanceof settingProfile) return;
        settingProfile cur=new settingProfile();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cur).commit();
        currentFragment=cur;

    }

    public void switchToFragmentDetailCoffee(){
        if (currentFragment instanceof detailCoffee) return;
        detailCoffee cur=new detailCoffee();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cur).commit();
        currentFragment=cur;

    }
    public void switchToFragmentMyCart(){
        if (currentFragment instanceof myCart) return;
        myCart cur=new myCart();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cur).commit();
        currentFragment=cur;

    }
    public void switchToFragmentOrderedSuccess(){
        if (currentFragment instanceof orderedSucess) return;
        orderedSucess cur=new orderedSucess();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cur).commit();
        currentFragment=cur;

    }
    public void switchToFragmentOrderHistory(){
        if (currentFragment instanceof orderHistory) return;
        orderHistory cur=new orderHistory();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cur).commit();
        currentFragment=cur;

    }

    public void switchToFragmentGift(){
        if (currentFragment instanceof giftPage) return;
        giftPage cur=new giftPage();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,cur).commit();
        currentFragment=cur;

    }


    @Override
    public void onCoffeeItemClick(Coffee coffee) {
        setSelectedCoffee(coffee);
        switchToFragmentDetailCoffee();
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
    public Coffee getSelectedCoffee() {
        return selectedCoffee;
    }

    public void setSelectedCoffee(Coffee selectedCoffee) {
        this.selectedCoffee = selectedCoffee;
    }

    public ArrayList<CoffeeCart> getCoffeeCarts() {
        return coffeeCarts;
    }

    public void setCoffeeCarts(ArrayList<CoffeeCart> coffeeCarts) {
        this.coffeeCarts = coffeeCarts;
    }




    public void removeAllCoffeeCart()
    {
        this.coffeeCarts.clear();
    }

    public void addCoffeeCart(CoffeeCart coffeeCart)
    {
        this.coffeeCarts.add(coffeeCart);
    }

    public ArrayList<BillItem> getBillItems() {
        return billItems;
    }
    public ArrayList<BillItem> getBillItemsHistory() {
        return billItemsHistory;
    }

    public void addBillItems(BillItem billItems) {
        this.billItems.add(billItems);
    }
    public void addBillItemsHistory(BillItem billItems) {
        this.billItemsHistory.add(billItems);
    }


    public int getScore() {
        return score;
    }
}