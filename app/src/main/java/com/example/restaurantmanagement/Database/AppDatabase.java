package com.example.restaurantmanagement.Database;

import androidx.room.Database;

import com.example.restaurantmanagement.Models.ComboTable;
import com.example.restaurantmanagement.Models.FoodTable;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.Models.TableOrderCustomer;

@Database(entities = {TableOrder.class, TableOrderCustomer.class, FoodTable.class, ComboTable.class},version = 1)
public abstract class AppDatabase {
    public abstract TableOrderDAO tableOrderDAO();
    public abstract  TableOrderCustomerDAO tableOrderCustomerDAO();
    public abstract  FoodTableDAO foodTableDAO();
    public  abstract  ComboTableDAO comboTableDAO();
}
