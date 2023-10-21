package com.example.restaurantmanagement.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "FoodCategory")
public class FoodCategory {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private String description;

    public FoodCategory( String name, String description) {

        this.name = name;
        this.description = description;
    }

    public FoodCategory() {

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
}
