package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.Database.UserDao;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class UpdatePassword extends AppCompatActivity {

    EditText password, confirm;
    TextView linkToProfile;
    ImageView back;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        final UserDao userDao = userDatabase.userDao();
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("userName", "");
        closeContextMenu();

        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm);
        save = findViewById(R.id.save);
        linkToProfile = findViewById(R.id.linkToProfile);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdatePassword.this, ProfileActivity.class));
            }
        });

        linkToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdatePassword.this, ProfileActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = password.getText().toString();
                String confirmPassword = confirm.getText().toString();

                if (!newPassword.equals(confirmPassword)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            StyleableToast.makeText(getApplicationContext(), "Confirm password is not match", Toast.LENGTH_LONG, R.style.toast_error).show();

                        }
                    });
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final UserEntity userEntity = userDao.getUserByUsername(name);
                            if (userEntity != null) {
                                userDao.updatePassword(userEntity.getEmail(), newPassword);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        StyleableToast.makeText(getApplicationContext(), "Update password successfully", Toast.LENGTH_LONG, R.style.toast_successfully).show();
                                        Intent intent = new Intent(UpdatePassword.this, ProfileActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });




    }
}