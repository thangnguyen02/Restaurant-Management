package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "comboTable")
public class ComboTable {
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
