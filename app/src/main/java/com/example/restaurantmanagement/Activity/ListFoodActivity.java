package com.example.restaurantmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Adapters.FoodCategoryAdapter;
import com.example.restaurantmanagement.Adapters.ListFoodAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.util.List;

public class ListFoodActivity extends AppCompatActivity implements ListFoodAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ListFoodAdapter listFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        recyclerView = findViewById(R.id.rcViewFood); // Assuming you have a RecyclerView in your layout XML


        reload();

    }


    @Override
    public void onItemClick(Food food) {

    }

    private void reload() {
        List<Food> foodItems = AppDatabase.getInstance(this).foodDAO().getListFood();
        listFoodAdapter = new ListFoodAdapter();
        listFoodAdapter.setData(foodItems, this);
        listFoodAdapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listFoodAdapter);
    }

    @Override
    public void OnDeleteItemClickListener(Food food) {
        AppDatabase.getInstance(this).foodDAO().deleteFood(food);
        Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show();
        reload();
    }

    @Override
    public void UpdateItem(Food food) {
        Intent intent = new Intent(this, UpdateFoodActivity.class);

        intent.putExtra("food", food.getId());
        startActivity(intent);
    }
}