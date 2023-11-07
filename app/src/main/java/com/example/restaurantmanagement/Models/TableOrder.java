package com.example.restaurantmanagement.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableOrder")
public class TableOrder {
    @PrimaryKey(autoGenerate = true)
    int ID=0;
    @ColumnInfo(name = "description")
    String description;
    @ColumnInfo(name = "floor")
    int floor;
    @ColumnInfo(name = "number")
    int number;

    public TableOrder(int ID, int floor, int number, String description) {
        this.ID = ID;
        this.floor = floor;
        this.description = description;
        this.number = number;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
