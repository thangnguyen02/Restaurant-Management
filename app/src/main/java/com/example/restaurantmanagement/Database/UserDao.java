package com.example.restaurantmanagement.Database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.restaurantmanagement.Models.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);
}