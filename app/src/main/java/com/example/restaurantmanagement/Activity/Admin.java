package com.example.restaurantmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantmanagement.R;

public class Admin extends AppCompatActivity {
    Button addFood;
    Button listFood;

    Button listCate;
    Button listCombo;
    Button addTable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin) ;
        listFood = findViewById(R.id.buttonlistFood);
        addFood = findViewById(R.id.buttonAddFood);
        listCate = findViewById(R.id.buttonListCate);
        listCombo = findViewById(R.id.buttonListCombo);
        addTable = findViewById(R.id.buttonAddTable);
        listCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, FoodCategoryActivity.class));
            }
        });
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, FoodActivity.class));
            }
        });
        listFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, ListFoodActivity.class));
            }
        });

        listCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, ComboListActivity.class));
            }
        });
        addTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, AddTable.class));
            }
        });

    }
}
