package com.example.restaurantmanagement.Database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.restaurantmanagement.Models.UserEntity;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from users where userId=(:userId) and password=(:password)")
    UserEntity login(String userId, String password);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int checkEmailExists(String email);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email OR userId = :userId")
    int checkEmailOrUserIdExists(String email, String userId);

    @Query("UPDATE users SET password = :newPassword WHERE email = :email")
    void updatePassword(String email, String newPassword);

    @Query("SELECT * FROM users WHERE userId = :username")
    UserEntity getUserByUsername(String username);

    @Update
    void updateUser(UserEntity userEntity);

    @Query("SELECT COUNT(*) FROM users WHERE userId = :newUsername")
    int checkUsernameExists(String newUsername);
    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();
}
