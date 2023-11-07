package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmanagement.Models.TableOrderCustomer;

import java.util.List;

@Dao
public interface TableOrderCustomerDAO {
    @Query("SELECT * FROM tableOrderCustomer")
    List<TableOrderCustomer> getAll();

    @Query("SELECT * FROM tableOrderCustomer WHERE ID IN (:ids)")
    List<TableOrderCustomer> loadAllByIds(int[] ids);

    @Query("SELECT * FROM tableOrderCustomer WHERE ID = :id")
    TableOrderCustomer findById(int id);
    @Query("SELECT * FROM tableOrderCustomer WHERE table_order_id = :id")
    TableOrderCustomer findByTableOrder(int id);
    @Insert
    void insertAll(TableOrderCustomer... tableOrderCustomer);

    @Delete
    void delete(TableOrderCustomer tableOrderCustomer);
}