package com.example.coffeeshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coffee_shop.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_COFFEE = "type_of_coffee";
    private static final String TABLE_ORDER_HISTORY = "order_history";

    // Common column names
    private static final String KEY_ID = "id";

    // Columns for 'type_of_coffee' table
    private static final String KEY_COFFEE_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DESCRIPTION = "description";

    // Columns for 'order_history' table
    private static final String KEY_COFFEE_ID = "coffee_id";
    private static final String KEY_SIZE = "size";
    private static final String KEY_DATE = "date";

    public CoffeeShopDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create 'type_of_coffee' table
        String createTypeOfCoffeeTable = "CREATE TABLE " + TABLE_COFFEE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COFFEE_NAME + " TEXT," + KEY_PRICE + " REAL," + KEY_DESCRIPTION + " TEXT" + ")";
        db.execSQL(createTypeOfCoffeeTable);

        // Create 'order_history' table
        String createOrderHistoryTable = "CREATE TABLE " + TABLE_ORDER_HISTORY + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COFFEE_ID + " INTEGER," + KEY_SIZE + " TEXT," + KEY_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," + "FOREIGN KEY(" + KEY_COFFEE_ID + ") REFERENCES " + TABLE_COFFEE + "(" + KEY_ID + ")" + ")";
        db.execSQL(createOrderHistoryTable);
    }

    public void insertCoffeeData() {
        SQLiteDatabase db = getWritableDatabase();

        String[] coffeeNames = {"Espresso", "Cappuccino", "Latte", "Americano", "Macchiato", "Mocha", "Flat White", "Affogato", "Vienna Coffee", "Egg Coffee", "Black Tea", "Green Tea", "Chai Tea", "Peppermint Tea", "Jasmine Tea", "Earl Grey Tea", "Bubble Tea", "Milk Tea", "Lemonade", "Iced Chocolate"};

        double[] coffeePrices = {2.50, 3.50, 3.00, 2.75, 3.25, 4.00, 3.50, 4.25, 4.50, 3.75, 2.00, 2.25, 3.50, 2.25, 2.50, 2.25, 4.00, 3.75, 2.75, 3.50};

        String[] coffeeDescriptions = {"A strong black coffee made by forcing hot water through finely-ground coffee beans.", "A coffee drink made with espresso, hot milk, and steamed milk foam.", "A coffee drink made with espresso and steamed milk, often topped with froth.", "A diluted espresso with hot water, resembling a drip-brewed coffee.", "An espresso with a small amount of steamed milk and foam on top.", "An espresso with chocolate syrup, steamed milk, and whipped cream.", "A coffee drink made with espresso and velvety steamed milk.", "A dessert of ice cream or gelato topped with a shot of hot espresso.", "A blend of coffee, whipped cream, and cocoa powder on top.", "A traditional Vietnamese coffee drink with egg yolks and condensed milk.", "A type of tea made from the leaves of the Camellia sinensis plant.", "A type of tea made from unoxidized tea leaves.", "A spiced tea drink made with black tea, milk, and various spices.", "A herbal tea made from peppermint leaves.", "A type of tea scented with the aroma of jasmine flowers.", "A flavored tea with a distinctive citrus flavor from bergamot orange.", "A tea-based drink with chewy tapioca pearls and various flavors.", "A tea-based drink made with milk and sweeteners.", "A sweetened lemon-flavored beverage.", "Chilled chocolate-flavored drink served with ice."};
        // Insert data into type_of_coffee table
        for (int i = 0; i < 10; i++) {
            ContentValues values = new ContentValues();
            values.put(KEY_COFFEE_NAME, coffeeNames[i]);
            values.put(KEY_PRICE, coffeePrices[i]);
            values.put(KEY_DESCRIPTION, coffeeDescriptions[i]);
            db.insert(TABLE_COFFEE, null, values);
        }
        // Sample data for order_history table
        int coffeeId1 = 2; // Assuming 2 corresponds to "Cappuccino" in the type_of_coffee table
        String size1 = "l"; // Assuming 'l' stands for large

        int coffeeId2 = 3; // Assuming 3 corresponds to "Latte" in the type_of_coffee table
        String size2 = "s"; // Assuming 's' stands for small

        int coffeeId3 = 5; // Assuming 5 corresponds to "Macchiato" in the type_of_coffee table
        String size3 = "m"; // Assuming 'm' stands for medium

// Insert data into order_history table
        ContentValues values1 = new ContentValues();
        values1.put(KEY_COFFEE_ID, coffeeId1);
        values1.put(KEY_SIZE, size1);
        db.insert(TABLE_ORDER_HISTORY, null, values1);

        ContentValues values2 = new ContentValues();
        values2.put(KEY_COFFEE_ID, coffeeId2);
        values2.put(KEY_SIZE, size2);
        db.insert(TABLE_ORDER_HISTORY, null, values2);

        ContentValues values3 = new ContentValues();
        values3.put(KEY_COFFEE_ID, coffeeId3);
        values3.put(KEY_SIZE, size3);
        db.insert(TABLE_ORDER_HISTORY, null, values3);

// Add more order history data as needed

// Close the database when done
        db.close();

    }
    public List<Coffee> getAllCoffeeTypes() {
        List<Coffee> coffeeList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {KEY_ID, KEY_COFFEE_NAME, KEY_PRICE, KEY_DESCRIPTION};
        Cursor cursor = db.query(TABLE_COFFEE, projection, null, null, null, null, null);


int[] imageResource = {R.drawable.a,
        R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h,
        R.drawable.i, R.drawable.j};


        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
                String coffeeName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_COFFEE_NAME));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PRICE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION));

                Coffee coffee = new Coffee(id, coffeeName, price, description);
                if (i < imageResource.length) {
                    coffee.setImage(imageResource[i]);

                } else break;

                ++i;
                coffeeList.add(coffee);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return coffeeList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if needed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEE);
        onCreate(db);
    }
}