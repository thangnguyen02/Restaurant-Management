package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.restaurantmanagement.Database.UserDao;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        addAdminAccountIfNotExist();
        Thread thread = new Thread(){
            @Override
            public void run() {

                try {
                    sleep(4000);
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }catch (Exception e){
                    System.out.println("Error splash screen");
                }
            }
        };
        thread.start();
    }
    private byte[] getDefaultAdminImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.admin);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        return stream.toByteArray();
    }
    private void addAdminAccountIfNotExist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                UserDao userDao = userDatabase.userDao();

                // Kiểm tra xem tài khoản admin đã tồn tại chưa
                UserEntity admin = userDao.getUserByUsername("admin");

                if (admin == null) {
                    // Tạo và thêm tài khoản admin
                    UserEntity adminUser = new UserEntity();
                    adminUser.setUserId("admin");
                    adminUser.setEmail("admin@gmail.com");
                    adminUser.setPassword("admin"); // Lưu ý: Mật khẩu nên được mã hóa trước khi lưu
                    adminUser.setFullName("admin");
                    adminUser.setPhone("12345678");
                    adminUser.setIsAdmin(1); // Đặt là quản trị viên
                    adminUser.setImage(getDefaultAdminImage()); // Sử dụng hình ảnh mặc định cho admin

                    userDao.registerUser(adminUser);
                }
            }
        }).start();
    }
}