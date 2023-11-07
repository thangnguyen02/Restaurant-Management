package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.restaurantmanagement.Adapters.ComboListAdapter;
import com.example.restaurantmanagement.Adapters.FoodForComboListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.EventListener.ComboClickListener;
import com.example.restaurantmanagement.EventListener.FoodComboClickListener;
import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCombo;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class AddOrder extends AppCompatActivity {
    UserDatabase userDatabase;
    AppDatabase database;
    Spinner spin_ComboAddCategory;
    FoodForComboListAdapter foodComboListAdapter;
    RecyclerView recyclerViewC, recyclerViewF;
    ComboListAdapter comboListAdapter;
    List<Food> foods;
    List<Combo> combos = new ArrayList<>();
    public UserEntity user = new UserEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        userDatabase = UserDatabase.getUserDatabase(this);
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                user = userDatabase.userDao().getUserByUsername(userName);
            }
        }).start();

        database = AppDatabase.getInstance(this);
        recyclerViewC = findViewById(R.id.rv_comboItems);
        recyclerViewF = findViewById(R.id.rv_menuItems);
        spin_ComboAddCategory = findViewById(R.id.spin_OrderCategory);

        combos = database.comboDAO().GetAllCombo();

        setupFoodRecycler(new ArrayList<>()); // Initialize the foodComboListAdapter
        updateComboRecycler(combos);

        ((Button) findViewById(R.id.btn_OrderList)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddOrder.this, ListOrder.class);
                startActivity(intent);
            }
        });

        // Add this line to initialize foodComboListAdapter
        foodComboListAdapter = new FoodForComboListAdapter(AddOrder.this, new ArrayList<>());
        setupSpinner(); // Add this line to set up the Spinner with category names
    }

    private void setupFoodRecycler(List<Food> foods) {
        recyclerViewF.setHasFixedSize(true);
        foodComboListAdapter = new FoodForComboListAdapter(AddOrder.this, foods);
        recyclerViewF.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerViewF.setAdapter(foodComboListAdapter);
    }

    private void setupSpinner() {
        List<String> categoryNames = database.foodCategoryDAO().getListFoodCategoryName();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_ComboAddCategory.setAdapter(spinnerAdapter);
        spin_ComboAddCategory.setOnItemSelectedListener(new CategorySpinnerActivity());
    }

    private void updateComboRecycler(List<Combo> combos) {
        recyclerViewC.setHasFixedSize(true);
        comboListAdapter = new ComboListAdapter(AddOrder.this, combos, comboClickListener);
        recyclerViewC.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerViewC.setAdapter(comboListAdapter);
    }

    private ComboClickListener comboClickListener = new ComboClickListener() {
        @Override
        public void onClick(Combo combo) {
            if (user.getIsAdmin() == 1) {
                Intent intent = new Intent(AddOrder.this, AddCombo.class);
                intent.putExtra("combo", combo);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(AddOrder.this, ComboDetailActivity.class);
                intent.putExtra("combo", combo);
                startActivity(intent);
                finish();
            }
        }
    };

    public class CategorySpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String categoryName = parent.getItemAtPosition(position).toString();
            Long categoryId = database.foodCategoryDAO().findCategoryIdByName(categoryName);
            foods = database.foodDAO().findByFoodCategoryId(categoryId);
            setupFoodRecycler(foods);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
