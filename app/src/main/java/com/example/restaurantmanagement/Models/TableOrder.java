package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableOrder")
public class TableOrder {
    @PrimaryKey(autoGenerate = true)
    int ID;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "floor")
    int floor;
    @ColumnInfo(name = "number")
    int number;
}
