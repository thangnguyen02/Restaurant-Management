package com.example.restaurantmanagement.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.restaurantmanagement.Models.FoodCategory;

@Database(entities = {FoodCategory.class}, version = 1)
public abstract class FoodCategoryDB extends RoomDatabase {
    private static final String database_name = "restaurant.db";
    private static FoodCategoryDB instance;

    public static synchronized FoodCategoryDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FoodCategoryDB.class, database_name)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract FoodCategoryDAO foodCategoryDAO();
}
