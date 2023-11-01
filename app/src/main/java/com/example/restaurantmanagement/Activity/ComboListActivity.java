package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantmanagement.Adapters.ComboListAdapter;
import com.example.restaurantmanagement.Adapters.OrderListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.EventListener.ComboClickListener;
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
    Button btn_MoveToAddCombo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_list);
        recyclerView = findViewById(R.id.rv_Combo);
        btn_MoveToAddCombo = findViewById(R.id.btn_MoveToAddCombo);
        database = AppDatabase.getInstance(this);
//        combos = database.comboDAO().GetAllCombo();
        btn_MoveToAddCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboListActivity.this,AddCombo.class);
                startActivityForResult(intent,101);

            }
        });
        updateRecycler(combos);
    }

    private void updateRecycler(List<Combo> combos){
        recyclerView.setHasFixedSize(true);
        comboListAdapter = new ComboListAdapter(ComboListActivity.this,combos,comboClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(comboListAdapter);
    }

    private ComboClickListener comboClickListener = new ComboClickListener() {
        @Override
        public void onClick(Combo combo) {

        }
    };
}