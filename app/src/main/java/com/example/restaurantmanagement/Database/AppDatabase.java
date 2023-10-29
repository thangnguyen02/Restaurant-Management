package com.example.restaurantmanagement.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.restaurantmanagement.Models.ComboTable;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.Models.FoodTable;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.Models.TableOrderCustomer;

import org.w3c.dom.CDATASection;

@Database(entities = {TableOrder.class, TableOrderCustomer.class, FoodTable.class, ComboTable.class, FoodCategory.class, Food.class},version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    private static String DATABASE_NAME="restaurantmanagement";

    public synchronized static AppDatabase getInstance(Context context) {
        if (database == null) {
             database = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        return database;

    }
    public abstract TableOrderDAO tableOrderDAO();
    public abstract  TableOrderCustomerDAO tableOrderCustomerDAO();
    public abstract  FoodTableDAO foodTableDAO();
    public  abstract  ComboTableDAO comboTableDAO();
    public abstract FoodCategoryDAO foodCategoryDAO();
    public abstract FoodDAO foodDAO();
}
