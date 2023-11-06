package com.example.restaurantmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.stream.Collectors;

public class ListFoodActivity extends AppCompatActivity implements ListFoodAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    ListFoodAdapter listFoodAdapter;
    private Spinner spinner;
    private static int indexCate ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);
        recyclerView = findViewById(R.id.rcViewFood); // Assuming you have a RecyclerView in your layout XML

        spinner = findViewById(R.id.categorySpinnerxxx);

        List<FoodCategory> foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodCategoryList.stream().map(m -> m.getName()).collect(Collectors.toList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = parentView.getItemAtPosition(position).toString();
                System.out.println(position);
                indexCate = position;
                reload();
                // Perform actions based on the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected (optional)
            }
        });
        reload();

    }


    @Override
    public void onItemClick(Food food) {

    }
    int check = 0;
    private void reload() {
        List<Food> foodItems;
        if (indexCate != 0 && check ==0) {
            check++;
            List<FoodCategory> foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();
            foodItems = AppDatabase.getInstance(this).foodDAO().findByFoodCategoryId(foodCategoryList.get(indexCate).getId());
        } else {
            foodItems = AppDatabase.getInstance(this).foodDAO().getListFood();
        }

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