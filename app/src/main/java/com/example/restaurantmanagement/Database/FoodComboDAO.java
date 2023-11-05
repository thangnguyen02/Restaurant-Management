package com.example.restaurantmanagement.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.restaurantmanagement.Models.FoodCombo;

import java.util.List;

@Dao
public interface FoodComboDAO {
    @Query("SELECT * FROM foodCombo WHERE combo_id = :id")
    List<FoodCombo> getFoodCombosByComboId(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFoodCombo(FoodCombo... foodCombos);

    @Query("Delete from foodCombo where combo_id = :id")
    void deleteFoodComboByComboId(int id);
//    @Query("SELECT * FROM food WHERE ")
//    List<Food> GetFoodsByFoodsId(int... ids)
}
