package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.restaurantmanagement.R;

public class UpdatePassword extends AppCompatActivity {

    EditText password, confirm;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        save = findViewById(R.id.save);

    }
}