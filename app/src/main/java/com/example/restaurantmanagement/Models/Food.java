package com.example.restaurantmanagement.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import kotlin.jvm.Transient;

@Entity(tableName = "Food")
public class Food    {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private String description;

    private Double price;

    private byte[] image;

    private Long categoryId;
    public Food( ) {


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
