package com.example.restaurantmanagement.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.Models.ComboTable;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.Models.FoodCombo;
import com.example.restaurantmanagement.Models.FoodTable;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.Models.TableOrderCustomer;
import com.example.restaurantmanagement.Models.UserEntity;

import org.w3c.dom.CDATASection;

@Database(entities = {UserEntity.class, TableOrder.class, TableOrderCustomer.class, FoodTable.class, ComboTable.class, FoodCategory.class, Food.class, Combo.class, FoodCombo.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase database;

    // Use a single database name
    private static final String DATABASE_NAME = "restaurantManagementDatabase";

    public synchronized static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() // It's not recommended to allow main thread queries in production
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    // Include all DAOs here
    public abstract TableOrderDAO tableOrderDAO();
    public abstract TableOrderCustomerDAO tableOrderCustomerDAO();
    public abstract FoodTableDAO foodTableDAO();
    public abstract ComboTableDAO comboTableDAO();
    public abstract FoodCategoryDAO foodCategoryDAO();
    public abstract FoodDAO foodDAO();
    public abstract ComboDAO comboDAO();
    public abstract FoodComboDAO foodComboDAO();
    public abstract UserDao userDao(); // Include UserDao from UserDatabase
}