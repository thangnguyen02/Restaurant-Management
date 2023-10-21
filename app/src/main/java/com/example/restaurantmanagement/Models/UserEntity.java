package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "userId")
    String userId;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "fullName")
    String fullName;

    @ColumnInfo(name = "phone")
    String phone;

    public UserEntity() {
    }

    public UserEntity(Integer id, String userId, String email, String password, String fullName, String phone) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
