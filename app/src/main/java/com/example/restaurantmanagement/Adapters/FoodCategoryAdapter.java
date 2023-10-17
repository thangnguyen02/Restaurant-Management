package com.example.restaurantmanagement.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.util.List;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.FoodCategoryViewHolder>{

    List<FoodCategory> foodCategoryList;

    private  OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(FoodCategory foodCategory);
    }
    public void setData(List<FoodCategory> list) {
        this.foodCategoryList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FoodCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_category, parent, false);
        return new FoodCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCategoryAdapter.FoodCategoryViewHolder holder, int position) {
        FoodCategory foodCategory = foodCategoryList.get(position);
        if (foodCategory == null) {
            return;
        }
        holder.txtName.setText(foodCategory.getName());
        holder.txtDescription.setText(foodCategory.getDescription());
    }

    @Override
    public int getItemCount() {
        if (foodCategoryList != null) {
            return foodCategoryList.size();
        }
        return 0;
    }
    public class FoodCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDescription;

        public FoodCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.itemTxtName);
            txtDescription = itemView.findViewById(R.id.itemTxtDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FoodCategory clickedCategory = foodCategoryList.get(position);
                        if (listener != null) {
                            listener.onItemClick(clickedCategory);
                        }
                    }
                }
            });
        }
    }

}
