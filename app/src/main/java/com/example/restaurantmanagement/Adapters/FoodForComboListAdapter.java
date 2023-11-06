package com.example.restaurantmanagement.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.EventListener.FoodComboClickListener;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCategory;
import com.example.restaurantmanagement.R;

import java.util.List;

public class FoodForComboListAdapter extends RecyclerView.Adapter<FoodForComboViewHolder> {
    Context context;
    List<Food> foods;
    FoodComboClickListener foodComboClickListener;

    public FoodForComboListAdapter(Context context, List<Food> foods, FoodComboClickListener foodComboClickListener) {
        this.context = context;
        this.foods = foods;
        this.foodComboClickListener = foodComboClickListener;
    }


    public FoodForComboListAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodForComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodForComboViewHolder(LayoutInflater.from(context).inflate(R.layout.foodcombo_components,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodForComboViewHolder holder, int position) {
        Food food = foods.get(position);
        AppDatabase db =  AppDatabase.getInstance(context);
        FoodCategory foodCategory = db.foodCategoryDAO().findByFoodCategoryId(food.getCategoryId());
        Bitmap bitmaps = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);
        holder.tv_foodComboDesc.setText(food.getDescription());
        holder.tv_foodComboCategory.setText(foodCategory.getName());
        holder.tv_foodComboName.setText(food.getName());
        holder.img_foodComboImageView.setImageBitmap(bitmaps);
        if(foodComboClickListener != null){
            holder.foodcombo_component.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodComboClickListener.onClick(foods.get(holder.getAdapterPosition()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }
}
class  FoodForComboViewHolder extends RecyclerView.ViewHolder{
    LinearLayout foodcombo_component;
    ImageView img_foodComboImageView;
    TextView tv_foodComboName,tv_foodComboDesc,tv_foodComboCategory;

    public FoodForComboViewHolder(@NonNull View itemView) {
        super(itemView);
        foodcombo_component = itemView.findViewById(R.id.foodcombo_component);
        img_foodComboImageView = itemView.findViewById(R.id.img_foodComboImageView);
        tv_foodComboDesc = itemView.findViewById(R.id.tv_foodComboDesc);
        tv_foodComboName = itemView.findViewById(R.id.tv_foodComboName);
        tv_foodComboCategory = itemView.findViewById(R.id.tv_foodComboCategory);
    }
}
