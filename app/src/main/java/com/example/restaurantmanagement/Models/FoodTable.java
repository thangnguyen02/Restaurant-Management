package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodTable")
public class FoodTable {

    @PrimaryKey(autoGenerate = true)
    int ID;
    @ColumnInfo(name = "food_id")
    int food_id;
    @ColumnInfo(name = "table_id")
    int table_id;
    @ColumnInfo(name = "quantity")
    int quantity;
    @ColumnInfo(name = "description")
    String description;
}
