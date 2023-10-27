package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodCombo")
public class FoodCombo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "food_id")
    private int food_id;
    @ColumnInfo(name = "combo_id")
    private int combo_id;
    @ColumnInfo(name = "quantity")
    private int quantity;

    public FoodCombo(int id, int food_id, int combo_id, int quantity) {
        this.id = id;
        this.food_id = food_id;
        this.combo_id = combo_id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getCombo_id() {
        return combo_id;
    }

    public void setCombo_id(int combo_id) {
        this.combo_id = combo_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
