package com.example.restaurantmanagement.Actitvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

}