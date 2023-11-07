package com.example.restaurantmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.R;

public class AddTable extends AppCompatActivity {
    ImageView back;
    AppDatabase database;
    Button add;

    EditText edit_floor, edit_number, edit_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);

        back = findViewById(R.id.back);
        add = findViewById(R.id.btn_addTable);
        edit_floor = findViewById(R.id.edit_floor);
        edit_number = findViewById(R.id.edit_number);
        edit_description = findViewById(R.id.edit_description);
        database = AppDatabase.getInstance(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the activity and go back to the previous activity (ListTableActivity)
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (edit_floor.getText().toString().trim().isEmpty() ||
                            edit_number.getText().toString().trim().isEmpty() ||
                            edit_description.getText().toString().trim().isEmpty()) {
                        Toast.makeText(AddTable.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int floor = Integer.parseInt(edit_floor.getText().toString());
                    int number = Integer.parseInt(edit_number.getText().toString());
                    String description = edit_description.getText().toString();

                    TableOrder current = database.tableOrderDAO().findByFloorAndNumber(floor, number);
                    if (current != null) {
                        Toast.makeText(AddTable.this, "Table already exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        TableOrder newTable = new TableOrder(0, floor, number, description);
                        long t = database.tableOrderDAO().insertTable(newTable);
                        Toast.makeText(AddTable.this, "Table added", Toast.LENGTH_SHORT).show();

                        // Return to ListTableActivity and refresh the list
                        Intent intent = new Intent(AddTable.this, ListTableActivity.class);
                        startActivity(intent);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AddTable.this, "Invalid input for floor or number", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AddTable.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
}
