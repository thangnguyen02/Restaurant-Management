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

                // Kiểm tra xác nhận mật khẩu
                if (!newPass.equals(confirmPass)) {
                    Toast.makeText(ResetPasswordActivity.this, "Password doesn't match confirmation", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện cập nhật mật khẩu trong cơ sở dữ liệu
                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                final UserDao userDao = userDatabase.userDao();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Kiểm tra xem địa chỉ email đã tồn tại trong cơ sở dữ liệu hay chưa.
                        int emailExists = userDao.checkEmailExists(userEmail);

                        if (emailExists > 0) {
                            // Địa chỉ email đã tồn tại, tiến hành cập nhật mật khẩu.
                            userDao.updatePassword(userEmail, newPass);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ResetPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ResetPasswordActivity.this, "Email not registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
}


}