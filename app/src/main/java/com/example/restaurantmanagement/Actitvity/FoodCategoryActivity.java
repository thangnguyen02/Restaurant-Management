package com.example.restaurantmanagement.Actitvity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restaurantmanagement.Adapters.FoodCategoryAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class FoodCategoryActivity extends AppCompatActivity implements FoodCategoryAdapter.OnItemClickListener {

    EditText txtName;
    EditText txtDescription;
    Button btnAdd;
    RecyclerView recyclerView;
    FoodCategoryAdapter foodCategoryAdapter;

    List<FoodCategory> foodCategoryList;

    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        initView();
        foodCategoryAdapter = new FoodCategoryAdapter();
        foodCategoryList = new ArrayList<>();
        foodCategoryAdapter.setData(foodCategoryList);

        foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();
        foodCategoryAdapter.setData(foodCategoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodCategoryAdapter);

        foodCategoryAdapter.setListener(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodCategory();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFoodCategory();
            }
        });

    }

    private void updateFoodCategory() {
        FoodCategory foodCategory = new FoodCategory();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        foodCategory.setId(sharedPreferences.getLong("id", 0));
        foodCategory.setName(txtName.getText().toString());
        foodCategory.setDescription(txtDescription.getText().toString());
        AppDatabase.getInstance(this).foodCategoryDAO().updateFoodCategory(foodCategory);
        reload();
    }

    public void reload() {
        hideSoftKeyboard(this);
        foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();
        foodCategoryAdapter.setData(foodCategoryList);
    }

    private void addFoodCategory() {
        String name = txtName.getText().toString().trim();
        String description = txtDescription.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
            return;
        }
        FoodCategory foodCategory = new FoodCategory(name, description);
        System.out.println(name);
        AppDatabase.getInstance(this).foodCategoryDAO().insertFoodCategory(foodCategory);
        Toast.makeText(this, "Add oke", Toast.LENGTH_SHORT).show();
        txtName.setText("");
        txtDescription.setText("");
        reload();

    }

    private void initView() {
        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        recyclerView = findViewById(R.id.rcViewFoodCategory);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(FoodCategory foodCategory) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("id", foodCategory.getId());
        editor.apply();
        txtName.setText(foodCategory.getName());
        txtDescription.setText(foodCategory.getDescription());
    }

    @Override
    public void OnDeleteItemClickListener(FoodCategory clickedCategory) {
        AppDatabase.getInstance(this).foodCategoryDAO().deleteFoodCategory(clickedCategory);
        Toast.makeText(this, "Delete successful", Toast.LENGTH_SHORT).show();
        reload();
    }
}