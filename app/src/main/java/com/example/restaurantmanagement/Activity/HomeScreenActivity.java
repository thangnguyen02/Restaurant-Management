package com.example.restaurantmanagement.Activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restaurantmanagement.R;

public class HomeScreenActivity extends AppCompatActivity {

    TextView fullName;
    LinearLayout logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn || userName.isEmpty()){
            Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home_screen);
            fullName = findViewById(R.id.username);
            String name = "Hi "+sharedPreferences.getString("userName", "")+"✋";closeContextMenu();

            fullName.setText(name);
            logout = findViewById(R.id.logout);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog for confirmation
                new AlertDialog.Builder(HomeScreenActivity.this)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User confirmed the logout, perform logout actions
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.putString("userName", "");
                                editor.apply();

                                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null) // Do nothing if the user selects "No"
                        .show();
            }
        });


    }

}