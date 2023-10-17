package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmanagement.Models.FoodTable;

import java.util.List;

@Dao
public interface FoodTableDAO {
    @Query("SELECT * FROM foodTable")
    List<FoodTable> getAll();

    @Query("SELECT * FROM foodTable WHERE ID IN (:ids)")
    List<FoodTable> loadAllByIds(int[] ids);

    @Query("SELECT * FROM foodTable WHERE ID = :id")
    FoodTable findById(int id);

    @Insert
    void insertAll(FoodTable... foodTable);

    @Delete
    void delete(FoodTable foodTable);
}