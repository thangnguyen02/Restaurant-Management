package com.example.restaurantmanagement.Actitvity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantmanagement.Adapters.ComboListAdapter;
import com.example.restaurantmanagement.Adapters.OrderListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.Models.TableOrderCustomer;
import com.example.restaurantmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ComboListActivity extends AppCompatActivity {

    AppDatabase database;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ComboListAdapter comboListAdapter;
    List<Combo> combos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_list);
        recyclerView = findViewById(R.id.rv_Combo);
    }
}