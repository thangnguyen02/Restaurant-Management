package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.facebook.stetho.Stetho;

import io.github.muddz.styleabletoast.StyleableToast;

public class RegisterActivity extends AppCompatActivity {

    EditText userId, email, password, fullName, phone;
    Button register;
    TextView linkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_register);

        userId = findViewById(R.id.userId);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fullName = findViewById(R.id.fullName);
        phone = findViewById(R.id.phone);
        register = findViewById(R.id.register);
        linkToLogin = findViewById(R.id.linkToLogin);

        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating user entity
                UserEntity userEntity = new UserEntity();

                userEntity.setUserId(userId.getText().toString());
                userEntity.setEmail(email.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setFullName(fullName.getText().toString());
                userEntity.setPhone(phone.getText().toString());


                if (validateInput(userEntity)) {
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    String emailToCheck = userEntity.getEmail();
                    String usernameToCheck = userEntity.getUserId();


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Kiểm tra xem địa chỉ email đã tồn tại trong cơ sở dữ liệu hay chưa.
                            int userExists = userDao.checkEmailOrUserIdExists(emailToCheck, usernameToCheck);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (userExists > 0) {
                                        StyleableToast.makeText(getApplicationContext(), "Email or username already registered", Toast.LENGTH_LONG, R.style.toast_error).show();
                                    } else {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                userDao.registerUser(userEntity);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        StyleableToast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_LONG, R.style.toast_successfully).show();
                                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                                    }
                                                });
                                            }
                                        }).start();
                                    }
                                }
                            });
                        }
                    }).start();
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG, R.style.toast_error).show();
                }
            }
        });

    }

    private Boolean validateInput(UserEntity userEntity) {
        if (userEntity.getUserId().isEmpty() ||
                userEntity.getEmail().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                userEntity.getFullName().isEmpty() ||
                userEntity.getPhone().isEmpty()) {
            return false;
        }
        return true;
    }
}