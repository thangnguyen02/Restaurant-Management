package com.example.restaurantmanagement.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restaurantmanagement.Models.FoodCategory;

import java.util.List;

@Dao
public interface FoodCategoryDAO {
    @Query("select * from FoodCategory")
    List<FoodCategory> getListFoodCategory();

    @Insert
    void insertFoodCategory(FoodCategory foodCategory);

    @Update
    void updateFoodCategory(FoodCategory foodCategory);

    @Delete
    void deleteFoodCategory(FoodCategory foodCategory);
    @Query("select * from FoodCategory where id =:id")
    FoodCategory findByFoodCategoryId(Long id );
}
