package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "foodCombo")
public class FoodCombo {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "food_id")
    private Long food_id;
    @ColumnInfo(name = "combo_id")
    private Long combo_id;
    @ColumnInfo(name = "quantity")
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFood_id() {
        return food_id;
    }

    public void setFood_id(Long food_id) {
        this.food_id = food_id;
    }

    public Long getCombo_id() {
        return combo_id;
    }

    public void setCombo_id(Long combo_id) {
        this.combo_id = combo_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public FoodCombo(Long id, Long food_id, Long combo_id, int quantity) {
        this.id = id;
        this.food_id = food_id;
        this.combo_id = combo_id;
        this.quantity = quantity;
    }
}
