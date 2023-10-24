package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.util.List;
import java.util.stream.Collectors;

public class FoodActivity extends AppCompatActivity {
    private EditText foodNameEditText;
    private EditText foodDescriptionEditText;
    private EditText foodPriceEditText;
    private ImageView foodImageView;
    private Spinner spinner;

    List<FoodCategory> foodCategoryList;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        foodNameEditText = findViewById(R.id.foodNameEditText);
        foodDescriptionEditText = findViewById(R.id.foodDescriptionEditText);
        foodPriceEditText = findViewById(R.id.foodPriceEditText);
        foodImageView = findViewById(R.id.foodImageView);
        spinner = findViewById(R.id.categorySpinner);
        foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                foodCategoryList.stream().map(m -> m.getName()).collect(Collectors.toList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


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
            foodImageView.setImageURI(selectedImageUri);
        }
    }
}