package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restaurantmanagement.Models.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where userId=(:userId) and password=(:password)")
    UserEntity login(String userId, String password);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int checkEmailExists(String email);

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    void updatePassword(String email, String newPassword);


}
