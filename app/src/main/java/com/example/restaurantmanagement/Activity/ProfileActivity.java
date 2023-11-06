package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    TextView username, fullname, phone, email;
    Button edit, update;
    EditText editUsername, editPhone, editFullname;
    ImageView back;

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
        username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        edit = findViewById(R.id.edit);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, HomeScreenActivity.class));
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("userName", "");
        closeContextMenu();

        new Thread(new Runnable() {
            ImageView avatarImageView = findViewById(R.id.avatar);

            @Override
            public void run() {
                final UserEntity userEntity = userDao.getUserByUsername(name);
                System.out.println("id: " + userEntity.getId());
                System.out.println("email: " + userEntity.getEmail());
                System.out.println("password: " + userEntity.getPassword());
                System.out.println("fullname: " + userEntity.getFullName());
                System.out.println("isAdmin: " + userEntity.getIsAdmin());
                System.out.println("phone: " + userEntity.getPhone());
                if (userEntity != null || userEntity.getImage() != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap userAvatar = BitmapFactory.decodeByteArray(userEntity.getImage(), 0, userEntity.getImage().length);
                            avatarImageView.setImageBitmap(userAvatar);
                            email.setText(userEntity.getEmail());
                            username.setText(userEntity.getUserId());
                            fullname.setText(userEntity.getFullName());
                            phone.setText(userEntity.getPhone());

                        }
                    });
                } else {
                    avatarImageView.setImageResource(R.drawable.avatar);
                }
            }
        }).start();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                View customView = getLayoutInflater().inflate(R.layout.update_profile_dialog, null);

                editUsername = customView.findViewById(R.id.edit_username);
                editPhone = customView.findViewById(R.id.edit_phone);
                editFullname = customView.findViewById(R.id.edit_fullname);
                update = customView.findViewById(R.id.updateProfile);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final UserEntity userEntity = userDao.getUserByUsername(name);
                        if (userEntity != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editUsername.setText(userEntity.getUserId());
                                    editPhone.setText(userEntity.getPhone());
                                    editFullname.setText(userEntity.getFullName());

                                }
                            });
                        }
                    }
                }).start();

                builder.setView(customView);

                AlertDialog dialog = builder.create();
                dialog.show();
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newUsername = editUsername.getText().toString().trim();
                        String newPhone = editPhone.getText().toString().trim();
                        String newFullname = editFullname.getText().toString().trim();
                        if (newUsername.isEmpty() || newPhone.isEmpty() || newFullname.isEmpty()) {
                            // Display an error message if any of the fields are empty
                            StyleableToast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG, R.style.toast_error).show();
                            return;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final UserEntity userEntity = userDao.getUserByUsername(name);
                                if (userEntity != null) {
                                    userEntity.setUserId(newUsername);
                                    userEntity.setPhone(newPhone);
                                    userEntity.setFullName(newFullname);

                                    userDao.updateUser(userEntity);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            StyleableToast.makeText(getApplicationContext(), "Update information successfully", Toast.LENGTH_LONG, R.style.toast_successfully).show();
                                            Intent profileIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
                                            startActivity(profileIntent);
                                            finish();
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                });
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

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpdatePassword.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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
                        editor.putBoolean("firstLogin", false);
                        editor.apply();

                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
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