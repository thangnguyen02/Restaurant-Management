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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.Database.UserDao;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout home, updatePassword, logout;
    EditText email, fullname, phone;
    TextView username;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        final UserDao userDao = userDatabase.userDao();

        home = findViewById(R.id.home);
        updatePassword = findViewById(R.id.updatePassword);
        logout = findViewById(R.id.logout);
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        save = findViewById(R.id.save);

        username = findViewById(R.id.textView);
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("userName", "");
        closeContextMenu();
        username.setText(name);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final UserEntity userEntity = userDao.getUserByUsername(name);
                if (userEntity != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            email.setText(userEntity.getEmail());
                            fullname.setText(userEntity.getFullName());
                            phone.setText(userEntity.getPhone());
                        }
                    });
                }
            }
        }).start();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail = email.getText().toString();
                String ufullname = fullname.getText().toString();
                String uphone = phone.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserEntity userEntity = userDao.getUserByUsername(name);
                        if (userEntity != null) {
                            // Check if the email has been changed
                            if (!uemail.equals(userEntity.getEmail())) {
                                // Email has been changed, check if it already exists
                                int emailExists = userDao.checkEmailExists(uemail);
                                if (emailExists > 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            StyleableToast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_LONG, R.style.toast_error).show();
                                        }
                                    });
                                    return; // Exit the function if the email already exists
                                }
                            }

                            // Update userEntity with the new information
                            userEntity.setEmail(uemail);
                            userEntity.setFullName(ufullname);
                            userEntity.setPhone(uphone);

                            // Call the updateUser method to update the user in the database
                            userDao.updateUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    email.setText(uemail);
                                    fullname.setText(ufullname);
                                    phone.setText(uphone);
                                    StyleableToast.makeText(getApplicationContext(), "Update profile successfully", Toast.LENGTH_LONG, R.style.toast_successfully).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog for confirmation
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // User confirmed the logout, perform logout actions
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.putString("userName", "");
                                editor.putBoolean("firstLogin", false);
                                editor.apply();

                                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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