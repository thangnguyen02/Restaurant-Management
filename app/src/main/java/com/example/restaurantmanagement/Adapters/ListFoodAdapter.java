package com.example.restaurantmanagement.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Models.Food; // Import your FoodItem class
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.util.List;

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.ListFoodViewHolder> {
    private List<Food> foodItems;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void setData(List<Food> list) {
        this.foodItems = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_food, parent, false);
        return new ListFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFoodViewHolder holder, int position) {
        Food foodItem = foodItems.get(position);
        Bitmap bitmaps = BitmapFactory.decodeByteArray(foodItem.getImage(), 0, foodItem.getImage().length);
        holder.foodImageView.setImageBitmap(bitmaps);
        holder.nameTextView.setText(foodItem.getName());
        holder.descriptionTextView.setText(foodItem.getDescription());
        holder.priceTextView.setText(String.format("$%.2f", foodItem.getPrice()));
        // holder.categoryTextView.setText(Math.toIntExact(foodItem.getCategoryId()));
        // Load the image into the ImageView here using a library like Picasso or Glide.
        // Example: Picasso.get().load(foodItem.getImageUrl()).into(holder.foodImageView);
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);

        void OnDeleteItemClickListener(Food food);

        void UpdateItem(Food food);
    }

    public class ListFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImageView;
        TextView nameTextView, descriptionTextView, priceTextView, categoryTextView;
        private Button buttonDelete;
        private Button buttonUpdate;

        public ListFoodViewHolder(View itemView) {
            super(itemView);
            foodImageView = itemView.findViewById(R.id.foodImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            buttonDelete = itemView.findViewById(R.id.btnDeleteFoo);
            buttonUpdate = itemView.findViewById(R.id.updateButton);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Food clickedCategory = foodItems.get(position);
                        if (listener != null) {
                            listener.OnDeleteItemClickListener(clickedCategory);
                        }
                    }
                }
            });
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Food clickedCategory = foodItems.get(position);
                        if (listener != null) {
                            listener.UpdateItem(clickedCategory);
                        }
                    }
                }
            });
        }
    }
}
