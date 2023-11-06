package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmanagement.Models.TableOrder;

import java.util.List;

@Dao
public interface TableOrderDAO {
    @Query("SELECT * FROM tableOrder")
    List<TableOrder> getAll();

    @Query("SELECT * FROM tableOrder WHERE ID IN (:ids)")
    List<TableOrder> loadAllByIds(int[] ids);

    @Query("SELECT * FROM tableOrder WHERE ID = :id")
    TableOrder findById(int id);
    @Query("SELECT * FROM tableOrder WHERE ID = :floor")
    TableOrder findByFloor(int floor);
    @Insert
    void insertAll(TableOrder... tableOrder);

    @Delete
    void delete(TableOrder tableOrder);
}