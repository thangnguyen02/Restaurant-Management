package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantmanagement.Adapters.FoodComboChooseListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.Models.FoodCombo;
import com.example.restaurantmanagement.R;

import java.util.List;

public class ComboDetailActivity extends AppCompatActivity {

    AppDatabase database;
    TextView tv_ComboDetailName, tv_ComboDetailDescription, tv_ComboDetailPrice;
    Button btn_ComboDetailBack;
    RecyclerView rv_ComboDetailFood;

    List<FoodCombo> foodCombos;
    Combo combo;

    FoodComboChooseListAdapter foodComboChooseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_detail);

        database = AppDatabase.getInstance(this);
        tv_ComboDetailDescription = findViewById(R.id.tv_ComboDetailDescription);
        tv_ComboDetailName = findViewById(R.id.tv_ComboDetailName);
        tv_ComboDetailPrice = findViewById(R.id.tv_ComboDetailPrice);
        btn_ComboDetailBack = findViewById(R.id.btn_ComboDetailBack);
        rv_ComboDetailFood = findViewById(R.id.rv_ComboDetailFood);
        combo = (Combo) getIntent().getSerializableExtra("combo");

        foodCombos = database.foodComboDAO().getFoodCombosByComboId(combo.getID());

        tv_ComboDetailDescription.setText(combo.getDescription());
        tv_ComboDetailName.setText(combo.getName());
        tv_ComboDetailPrice.setText(String.valueOf(combo.getPrice()));
        btn_ComboDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboDetailActivity.this, ComboListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        updateRecycler(foodCombos);
    }

    private void updateRecycler(List<FoodCombo> foodCombos) {
        rv_ComboDetailFood.setHasFixedSize(true);
        foodComboChooseListAdapter = new FoodComboChooseListAdapter(this, foodCombos, false);
        rv_ComboDetailFood.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rv_ComboDetailFood.setAdapter(foodComboChooseListAdapter);
    }

}