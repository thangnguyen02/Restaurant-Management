package com.example.restaurantmanagement.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateFoodActivity extends AppCompatActivity {
    private EditText foodNameEditText;
    private EditText foodDescriptionEditText;
    private EditText foodPriceEditText;
    private ImageView foodImageView;
    private Spinner spinner;

    private byte[] imageBytes; // Add this variable
    private static int indexCate;
    private Button btnUpdate;
    private static final int PICK_IMAGE_REQUEST = 1;
    Food food;
    List<FoodCategory> foodCategoryList;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_food);

        foodNameEditText = findViewById(R.id.nameTextViewUpdate);
        foodDescriptionEditText = findViewById(R.id.descriptionTextViewUpdate);
        foodPriceEditText = findViewById(R.id.priceTextViewUpdate);
        foodImageView = findViewById(R.id.foodImageViewUpdate);
        spinner = findViewById(R.id.categoryTextViewUpdate);
        btnUpdate = findViewById(R.id.updateButtonss);

        foodCategoryList = AppDatabase.getInstance(this).foodCategoryDAO().getListFoodCategory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodCategoryList.stream().map(m -> m.getName()).collect(Collectors.toList()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        Long id = (Long) getIntent().getSerializableExtra("food");
        food = AppDatabase.getInstance(this).foodDAO().findByFoodId(id);
        foodNameEditText.setText(food.getName());
        foodDescriptionEditText.setText(food.getDescription());
        foodPriceEditText.setText(food.getPrice() + "");
        Bitmap bitmaps = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);
        foodImageView.setImageBitmap(bitmaps);


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
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Uri selectedImageUri;

    private void update() throws IOException {
        food.setName(foodNameEditText.getText().toString());
        food.setDescription(foodDescriptionEditText.getText().toString());
        food.setPrice(Double.parseDouble(String.valueOf(foodPriceEditText.getText())));
        food.setCategoryId(foodCategoryList.get(indexCate).getId());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageBytes = stream.toByteArray();
            food.setImage(imageBytes);
        } catch (Exception e) {

        }


        AppDatabase.getInstance(this).foodDAO().update(food);
        Intent intent = new Intent(this, ListFoodActivity.class);


        startActivity(intent);
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
