package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.restaurantmanagement.Models.ComboTable;

import java.util.List;

@Dao
public interface ComboTableDAO {
    @Query("SELECT * FROM comboTable")
    List<ComboTable> getAll();

    @Query("SELECT * FROM comboTable WHERE ID IN (:ids)")
    List<ComboTable> loadAllByIds(int[] ids);

    @Query("SELECT * FROM comboTable WHERE ID = :id")
    ComboTable findById(int id);

    @Insert
    void insertAll(ComboTable... comboTable);

    @Delete
    void delete(ComboTable comboTable);
}