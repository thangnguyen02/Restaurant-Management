package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restaurantmanagement.Models.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where email=(:email) and password=(:password)")
    UserEntity login(String email, String password);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int checkEmailExists(String email);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email OR userId = :userId")
    int checkEmailOrUserIdExists(String email, String userId);

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    void updatePassword(String email, String newPassword);

    @Query("SELECT * FROM users WHERE userId = :username")
    UserEntity getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email")
    UserEntity getUserByEmail(String email);

    @Update
    void updateUser(UserEntity userEntity);
}
