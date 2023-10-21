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

public class RegisterActivity extends AppCompatActivity {

    EditText userId, email, password, fullName, phone;
    Button register;
    TextView linkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    final UserDao userDao = userDatabase.userDao();
                    final String emailToCheck = userEntity.getEmail();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Kiểm tra xem địa chỉ email đã tồn tại trong cơ sở dữ liệu hay chưa.
                            int emailExists = userDao.checkEmailExists(emailToCheck);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (emailExists > 0) {
                                        // Địa chỉ email đã tồn tại, hiển thị thông báo lỗi.
                                        Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Địa chỉ email chưa tồn tại, tiến hành đăng ký người dùng.
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                userDao.registerUser(userEntity);
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private Boolean validateInput(UserEntity userEntity) {
        if (userEntity.getUserId().isEmpty() ||
                userEntity.getEmail().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                userEntity.getFullName().isEmpty() ||
                userEntity.getPhone().isEmpty()){
            return false;
        }
        return true;
    }
}