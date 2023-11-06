package com.example.restaurantmanagement.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restaurantmanagement.Models.Combo;

import java.util.List;

@Dao
public interface ComboDAO {
    @Query("SELECT * FROM combo")
    List<Combo> GetAllCombo();
    @Query("SELECT * FROM combo WHERE ID IN (:ids)")
    List<Combo> GetCombosById(int... ids);
    @Query("SELECT * FROM combo WHERE ID = :id")
    Combo GetComboById(int id);

    @Query("SELECT * FROM combo WHERE name LIKE :name")
    List<Combo> findComboByName(String name);
    @Insert
    void insertCombos(Combo... combos);

    @Insert
    long insertCombo(Combo combo);

    @Delete
    void deleteCombos(Combo...combos);

    @Update
    void updateCombos(Combo...combos);
}
