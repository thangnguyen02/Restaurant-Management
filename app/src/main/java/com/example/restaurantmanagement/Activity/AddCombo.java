package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.R;

import java.util.List;

public class AddCombo extends AppCompatActivity {

    AppDatabase database;
    TextView tv_addComboName;
    TextView tv_addComboPrice;
    TextView tml_addComboDescription;
    Button btn_addCombo;
    Button btn_listCombo;
    Spinner spin_ComboAddCategory;
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
        spin_ComboAddCategory = findViewById(R.id.spin_ComboAddCategory);

        List<String> categoryNames =   database.foodCategoryDAO().getListFoodCategoryName();
        ArrayAdapter<String> spinnerAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_ComboAddCategory.setAdapter(spinnerAdapter);

        btn_listCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCombo.this, ComboListActivity.class);
                startActivityForResult(intent,101);
            }
        });
        btn_addCombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Combo combo = new Combo(0,tv_addComboName.getText().toString(),
                        tml_addComboDescription.getText().toString(),
                        Double.parseDouble(tv_addComboPrice.getText().toString()));
                database.comboDAO().insertCombos(combo);
            }
        });
    }
public class CategorySpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener{

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
}