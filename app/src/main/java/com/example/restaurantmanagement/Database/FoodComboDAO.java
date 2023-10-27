package com.example.restaurantmanagement.Database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.restaurantmanagement.Models.FoodCombo;

import java.util.List;

@Dao
public interface FoodComboDAO {
    @Query("SELECT * FROM foodCombo WHERE combo_id = :id")
    List<FoodCombo> GetFoodsIdByComboId(int id);
//    @Query("SELECT * FROM food WHERE ")
//    List<Food> GetFoodsByFoodsId(int... ids)
}
