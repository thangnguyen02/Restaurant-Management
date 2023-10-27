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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.restaurantmanagement.R;

public class HomeScreenActivity extends AppCompatActivity {

    TextView fullName;
    ImageView profile;
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
            profile = findViewById(R.id.profile);
            logout = findViewById(R.id.logout);
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreenActivity.this);
                View customView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

                TextView dialogTitle = customView.findViewById(R.id.dialog_title);
                Button buttonYes = customView.findViewById(R.id.dialog_button_yes);
                Button buttonNo = customView.findViewById(R.id.dialog_button_no);

                dialogTitle.setText("Are you sure want to logout?");

                final AlertDialog dialog = builder.setView(customView).create();

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.putString("userName", "");
                        editor.putBoolean("firstLogin_"+userName, false);
                        editor.apply();

                        Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                        dialog.dismiss();
                    }
                });

                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

}