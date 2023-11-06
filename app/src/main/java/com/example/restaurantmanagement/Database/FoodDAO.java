package com.example.restaurantmanagement.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;

import java.util.List;

@Dao
public interface FoodDAO {
    @Query("select * from Food")
    List<Food> getListFood();

    @Insert
    void insertFood(Food food);

    @Update
    void update(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("select * from Food where categoryId =:id")
    List<Food> findByFoodCategoryId(Long id );

    @Query("select name from food where id = :id")
    String findFoodNameById(Long id);
    @Query("SELECT * FROM Food WHERE id = :id LIMIT 1")
    Food findByFoodId(Long id);


}
