package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FoodActivity extends AppCompatActivity {
    private EditText foodNameEditText;
    private EditText foodDescriptionEditText;
    private EditText foodPriceEditText;
    private ImageView foodImageView;
    private Spinner spinner;
    private Button goToList;
    private byte[] imageBytes; // Add this variable

    private Button btnAddFood;

    private static int indexCate;
    List<FoodCategory> foodCategoryList;
    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri selectedImageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        foodNameEditText = findViewById(R.id.foodNameEditText);
        foodDescriptionEditText = findViewById(R.id.foodDescriptionEditText);
        foodPriceEditText = findViewById(R.id.foodPriceEditText);
        foodImageView = findViewById(R.id.foodImageView);

        //

        //
        spinner = findViewById(R.id.categorySpinner);
        goToList = findViewById(R.id.goToList);
        btnAddFood = findViewById(R.id.btnAddFood);
        foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodCategoryList.stream().map(m -> m.getName()).collect(Collectors.toList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        goToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(FoodActivity.this, ListFoodActivity.class);
                startActivity(intent);

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle the selected item here
                String selectedItem = parentView.getItemAtPosition(position).toString();
                System.out.println(position);
                indexCate = position;
                // Perform actions based on the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected (optional)
            }
        });
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });


    }

    private void addFood() {
//        List<Food> foods = AppDatabase.getInstance(this).foodDAO().getListFood();
//        Bitmap bitmaps = BitmapFactory.decodeByteArray(foods.get(0).getImage(), 0, foods.get(0).getImage().length);
//
//        foodImageView.setImageBitmap(bitmaps);


        Food food = new Food();
        String name = foodNameEditText.getText().toString().trim();
        String description = foodDescriptionEditText.getText().toString().trim();
        String priceStr = foodPriceEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(priceStr) || selectedImageUri == null) {
            Toast.makeText(this, "Please fill in all fields and select an image.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);

            // Convert the selected image to a byte array
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBytes = stream.toByteArray();

            // Set the properties of the Food object
            food.setName(name);
            food.setDescription(description);
            food.setPrice(price);
            food.setImage(imageBytes);
            food.setCategoryId(foodCategoryList.get(indexCate).getId());

            // Insert the Food object into the database
            AppDatabase.getInstance(this).foodDAO().insertFood(food);

            // Optionally, you can clear the form fields and reset the ImageView here
            foodNameEditText.setText("");
            foodDescriptionEditText.setText("");
            foodPriceEditText.setText("");
            foodImageView.setImageURI(null);
            Toast.makeText(this, "Save food successfully!!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format. Please enter a valid number.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
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
            foodImageView.setImageURI(selectedImageUri);
        }
    }
}