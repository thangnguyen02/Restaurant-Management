package com.example.restaurantmanagement.Activity;


import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.Database.UserDao;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {

    EditText userId, password;
    Button login;
    TextView linkToRegister, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("userName", "");

        if (!userName.isEmpty()) {
            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        linkToRegister = findViewById(R.id.linkToRegister);
        reset = findViewById(R.id.reset);

        linkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userIdText = userId.getText().toString();
                final String passwordText = password.getText().toString();
                if (userIdText.isEmpty() || passwordText.isEmpty()) {
                    StyleableToast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG, R.style.toast_error).show();
                } else {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(userIdText, passwordText);
                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StyleableToast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG, R.style.toast_error).show();
                                    }
                                });
                            } else {
                                SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                boolean isFirstLogin = sharedPreferences.getBoolean("firstLogin_"+userName, true);
                                editor.putBoolean("isLoggedIn", true);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StyleableToast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG, R.style.toast_successfully).show();
                                    }
                                });

                                if (isFirstLogin) {
                                    editor.putBoolean("isFirstLogin", true);
                                    editor.putString("userName", userIdText);
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, SlideActivity.class));
                                } else {
                                    editor.putBoolean("isFirstLogin", false);
                                    editor.putString("userName", userIdText);
                                    editor.apply();
                                    startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                                }

                                finish();
                            }
                        }
                    }).start();
                }
            }
        });

    }

}
