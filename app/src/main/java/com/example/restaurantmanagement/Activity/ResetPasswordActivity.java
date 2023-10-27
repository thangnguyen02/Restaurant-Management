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
import com.example.restaurantmanagement.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText userId, email, newPassword, confirmPassword;
    Button updatePassword;
    TextView linkToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        userId = findViewById(R.id.userId);
        email = findViewById(R.id.email);
        newPassword = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm);
        updatePassword = findViewById(R.id.save);
        linkToLogin = findViewById(R.id.linkToLogin);

        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userId.getText().toString();
                String userEmail = email.getText().toString();
                String newPass = newPassword.getText().toString();
                String confirmPass = confirmPassword.getText().toString();

                if (!newPass.equals(confirmPass)) {
                    StyleableToast.makeText(getApplicationContext(), "Password doesn't match confirmation", Toast.LENGTH_LONG, R.style.toast_error).show();

                    return;
                }

                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                final UserDao userDao = userDatabase.userDao();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int emailExists = userDao.checkEmailExists(userEmail);

                        if (emailExists > 0) {
                            userDao.updatePassword(userEmail, newPass);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StyleableToast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_LONG, R.style.toast_successfully).show();
                                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    StyleableToast.makeText(getApplicationContext(), "Email not registered", Toast.LENGTH_LONG, R.style.toast_error).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
}


