package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tableOrderCustomer")
public class TableOrderCustomer {
    @PrimaryKey(autoGenerate = true)
    int ID=0;
    @ColumnInfo(name = "table_order_id")
    int table_order_id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name="start")
    String start;
    @ColumnInfo(name = "end")
    String end;
    @ColumnInfo(name="customer_id")
    int customer_id;
    @ColumnInfo(name="description")
    String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getTable_order_id() {
        return table_order_id;
    }

    public void setTable_order_id(int table_order_id) {
        this.table_order_id = table_order_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
