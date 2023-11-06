package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.example.restaurantmanagement.Adapters.TableListsAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.EventListener.TableOrderClickListener;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListTableActivity extends AppCompatActivity {
    AppDatabase database;
    ImageView back;
    List<TableOrder> tables = new ArrayList<>();
    RecyclerView recyclerView;
    TableListsAdapter tableListAdapter;
    FloatingActionButton f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);
        database = AppDatabase.getInstance(this);
         tables = database.tableOrderDAO().getAll();
        recyclerView = findViewById(R.id.recycler_table);
        back = findViewById(R.id.back);
        f = findViewById(R.id.f_add);
        updateRecycler(tables);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTableActivity.this, AddTable.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTableActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateRecycler(List<TableOrder> tables) {
        recyclerView.setHasFixedSize(true);
        tableListAdapter = new TableListsAdapter(ListTableActivity.this,tables,tableOrderClickListener);
        recyclerView.setAdapter(tableListAdapter);
    }

    private final TableOrderClickListener tableOrderClickListener = new TableOrderClickListener() {
        @Override
        public void onClick(TableOrder tableOrder) {

        }
    };
}