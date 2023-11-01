package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Database.UserDao;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;
import com.facebook.stetho.Stetho;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import io.github.muddz.styleabletoast.StyleableToast;

public class RegisterActivity extends AppCompatActivity {

    EditText userId, email, password, fullName, phone;
    Button register;
    TextView linkToLogin;
    ImageView avatar;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private byte[] imageBytes;


    @SuppressLint("MissingInflatedId")
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
        avatar = findViewById(R.id.avatar);
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
                register();
            }
        });

    }
    public void register(){
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(userId.getText().toString());
        userEntity.setEmail(email.getText().toString());
        userEntity.setPassword(password.getText().toString());
        userEntity.setFullName(fullName.getText().toString());
        userEntity.setPhone(phone.getText().toString());


        if (validateInput(userEntity) || selectedImageUri != null) {
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            UserDao userDao = userDatabase.userDao();
            String emailToCheck = userEntity.getEmail();
            String usernameToCheck = userEntity.getUserId();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                imageBytes = stream.toByteArray();
                userEntity.setImage(imageBytes);

            } catch (IOException e) {
                StyleableToast.makeText(getApplicationContext(), "Image is invalid", Toast.LENGTH_LONG, R.style.toast_error).show();
                e.printStackTrace();
            }


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

    public void selectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();

            // Set the selected image to the ImageView
            avatar.setImageURI(selectedImageUri);
        }
    }

    private Boolean validateInput(UserEntity userEntity) {
        if (userEntity.getUserId().isEmpty() ||
                userEntity.getEmail().isEmpty() ||
                userEntity.getPassword().isEmpty() ||
                userEntity.getFullName().isEmpty() ||
                userEntity.getPhone().isEmpty()
                        || selectedImageUri == null) {
            return false;
        }
        return true;
    }
}