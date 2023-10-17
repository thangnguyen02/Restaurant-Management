package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tableOrderCustomer")
public class TableOrderCustomer {
    @PrimaryKey(autoGenerate = true)
    int ID;
    @ColumnInfo(name = "table_order_id")
    String table_order_id;
    @ColumnInfo(name="start")
    Date start;
    @ColumnInfo(name = "end")
    Date end;
    @ColumnInfo(name="customer_id")
    int customer_id;
    @ColumnInfo(name="description")
    String description;
}
