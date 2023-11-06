package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.Adapters.FoodComboChooseListAdapter;
import com.example.restaurantmanagement.Adapters.FoodForComboListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.EventListener.FoodComboClickListener;
import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCombo;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class AddCombo extends AppCompatActivity implements FoodComboChooseListAdapter.OnItemDeleteListener {

    AppDatabase database;
    TextView tv_addComboName;
    TextView tv_addComboPrice;
    TextView tml_addComboDescription;
    Button btn_addCombo;
    Button btn_listCombo;
    Button btn_deleteCombo;
    Spinner spin_ComboAddCategory;
    RecyclerView rv_foodCombo, rv_choosenFood;
    FoodForComboListAdapter foodComboListAdapter;
    FoodComboChooseListAdapter foodComboChooseListAdapter;
    List<Food> foods;
    List<FoodCombo> foodCombos;
    Combo updatingCombo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combo);
        tv_addComboName = findViewById(R.id.tv_AddComboName);
        tv_addComboPrice = findViewById(R.id.tv_addComboPrice);
        tml_addComboDescription = findViewById(R.id.tml_AddDescription);
        database = AppDatabase.getInstance(this);
        btn_listCombo = findViewById(R.id.btn_ComboList);
        btn_addCombo = findViewById(R.id.btn_addCombo);
        btn_deleteCombo = findViewById(R.id.btn_deleteCombo);
        spin_ComboAddCategory = findViewById(R.id.spin_ComboAddCategory);
        rv_foodCombo = findViewById(R.id.rv_foodCombo);
        rv_choosenFood = findViewById(R.id.rv_choosenFood);
        foods = database.foodDAO().getListFood();
        updatingCombo = (Combo) getIntent().getSerializableExtra("combo");

        foodCombos = new ArrayList<>();
        setupSpinner();
        setupFoodRecycler(foods);
        btn_listCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCombo.this, ComboListActivity.class);
                startActivityForResult(intent, 101);
            }
        });
        if (updatingCombo == null) {
            btn_deleteCombo.setVisibility(View.INVISIBLE);
            btn_addCombo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_addComboName.getText().length() <= 0 ||
                            tv_addComboPrice.getText().length() <= 0 ||
                            tml_addComboDescription.getText().length() <= 0) {
                        Toast.makeText(AddCombo.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Combo combo = new Combo(0,
                            tv_addComboName.getText().toString(),
                            tml_addComboDescription.getText().toString(),
                            Double.parseDouble(tv_addComboPrice.getText().toString()));
                    long insertComboId = database.comboDAO().insertCombo(combo);
//                    for (FoodCombo f : foodCombos) {
//                        f.setCombo_id(insertComboId);
//                        database.foodComboDAO().insertFoodCombo(f);
//                    }
                    for(int i =0;i<foodCombos.size(); i++){
                        foodCombos.get(i).setCombo_id(insertComboId);
                        FoodComboChooseListAdapter.FoodComboChooseHolder holder = (FoodComboChooseListAdapter.FoodComboChooseHolder) rv_choosenFood.findViewHolderForAdapterPosition(i);
                        foodCombos.get(i).setQuantity(holder.getQuantity());
                        FoodCombo foodCombo = foodCombos.get(i);
                        database.foodComboDAO().insertFoodCombo(foodCombo);
                    }
                    Toast.makeText(AddCombo.this, "Combo added", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btn_deleteCombo.setVisibility(View.VISIBLE);
            tv_addComboName.setText(updatingCombo.getName());
            tv_addComboPrice.setText(updatingCombo.getPrice() + "");
            tml_addComboDescription.setText(updatingCombo.getDescription());
            btn_addCombo.setText("update");
            foodCombos = database.foodComboDAO().getFoodCombosByComboId(updatingCombo.getID());
            updateFoodComboRecycler(foodCombos);
            btn_deleteCombo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database.foodComboDAO().deleteFoodComboByComboId(updatingCombo.getID());
                    database.comboDAO().deleteCombos(updatingCombo);
                    Toast.makeText(AddCombo.this, "Combo deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCombo.this, ComboListActivity.class);
                    startActivity(intent);
                }
            });
            btn_addCombo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tv_addComboName.getText().length() <= 0 ||
                            tv_addComboPrice.getText().length() <= 0 ||
                            tml_addComboDescription.getText().length() <= 0) {
                        Toast.makeText(AddCombo.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    updatingCombo.setName(tv_addComboName.getText().toString().trim());
                    updatingCombo.setDescription(tml_addComboDescription.getText().toString().trim());
                    updatingCombo.setPrice(Double.parseDouble(tv_addComboPrice.getText().toString().trim()));
                    database.foodComboDAO().deleteFoodComboByComboId(updatingCombo.getID());
                    for(int i =0;i<foodCombos.size(); i++){
                        foodCombos.get(i).setCombo_id(Long.parseLong(String.valueOf(updatingCombo.getID())));
                        FoodComboChooseListAdapter.FoodComboChooseHolder holder = (FoodComboChooseListAdapter.FoodComboChooseHolder) rv_choosenFood.findViewHolderForAdapterPosition(i);
                        foodCombos.get(i).setQuantity(holder.getQuantity());
                        FoodCombo foodCombo = foodCombos.get(i);
                        database.foodComboDAO().insertFoodCombo(foodCombo);
                    }
                    database.comboDAO().updateCombos(updatingCombo);
                    Toast.makeText(AddCombo.this, "Combo updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCombo.this, ComboListActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    private void setupSpinner() {
        List<String> categoryNames = database.foodCategoryDAO().getListFoodCategoryName();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_ComboAddCategory.setOnItemSelectedListener(new CategorySpinnerActivity());
        spin_ComboAddCategory.setAdapter(spinnerAdapter);
    }

    private void setupFoodRecycler(List<Food> foods) {
        rv_foodCombo.setHasFixedSize(true);
        foodComboListAdapter = new FoodForComboListAdapter(AddCombo.this, foods, foodComboClickListener);
        rv_foodCombo.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rv_foodCombo.setAdapter(foodComboListAdapter);
    }

    private void updateFoodComboRecycler(List<FoodCombo> foodCombos) {
        rv_choosenFood.setHasFixedSize(true);
        foodComboChooseListAdapter = new FoodComboChooseListAdapter(AddCombo.this, foodCombos,true);
        foodComboChooseListAdapter.setListener(this);
        rv_choosenFood.setLayoutManager(new LinearLayoutManager(this));
        rv_choosenFood.setAdapter(foodComboChooseListAdapter);
    }

    private FoodComboClickListener foodComboClickListener = new FoodComboClickListener() {
        @Override
        public void onClick(Food food) {
            FoodCombo foodCombo = new FoodCombo(null, food.getId(), 0l, 1);
            if (foodCombos.isEmpty()) {
                foodCombos.add(foodCombo);
                updateFoodComboRecycler(foodCombos);
            } else {
                Long sameRecord = foodCombos.stream().filter(f -> f.getFood_id() == food.getId()).count();
                if (sameRecord <= 0) {
                    foodCombos.add(foodCombo);
                    updateFoodComboRecycler(foodCombos);
                }
            }

        }
    };

    @Override
    public void OnDeleteItem(FoodCombo foodCombo) {
        foodCombos.remove(foodCombo);
        updateFoodComboRecycler(foodCombos);
    }


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