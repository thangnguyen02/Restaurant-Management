package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.R;

public class AddTable extends AppCompatActivity {
    ImageView back;
    AppDatabase database;
    Button add;

    EditText edit_floor,edit_number,edit_description;
    TableOrder table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);
        back=findViewById(R.id.back);
        add=findViewById(R.id.btn_addTable);
        edit_floor=findViewById(R.id.edit_floor);
        edit_number=findViewById(R.id.edit_number);
        edit_description=findViewById(R.id.edit_description);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this, ListTableActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_floor.getText().length() <= 0 ||
                        edit_number.getText().length() <= 0 ||
                        edit_description.getText().length() <= 0 ||) {
                    Toast.makeText(AddTable.this, "", Toast.LENGTH_SHORT).show();
                    return;
                }
                TableOrder newTable = new TableOrder()
            }
        });
    }
}