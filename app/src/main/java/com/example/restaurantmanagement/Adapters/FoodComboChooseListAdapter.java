package com.example.restaurantmanagement.Adapters;

import android.content.Context;
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

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Models.Food;
import com.example.restaurantmanagement.Models.FoodCombo;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;

import java.util.BitSet;
import java.util.List;

public class FoodComboChooseListAdapter extends RecyclerView.Adapter<FoodComboChooseListAdapter.FoodComboChooseHolder> {
    Context context;
    List<FoodCombo> foodComboList;
    private OnItemDeleteListener listener;
    boolean isAdmin = false;

    public void setListener(OnItemDeleteListener listener) {
        this.listener = listener;
    }

    public interface OnItemDeleteListener {
        void OnDeleteItem(FoodCombo foodCombo);
    }

    public FoodComboChooseListAdapter(Context context, List<FoodCombo> foodComboList,boolean isAdmin) {
        this.context = context;
        this.foodComboList = foodComboList;
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public FoodComboChooseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodComboChooseHolder(LayoutInflater.from(context).inflate(R.layout.choosefoodcombo_components, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodComboChooseListAdapter.FoodComboChooseHolder holder, int position) {
        FoodCombo foodCombo = foodComboList.get(position);
        AppDatabase db = AppDatabase.getInstance(context);
        Food food = db.foodDAO().findByFoodId(foodCombo.getFood_id());

        Bitmap bitmaps = BitmapFactory.decodeByteArray(food.getImage(), 0, food.getImage().length);

        holder.tv_chooseFoodName.setText(food.getName());
        holder.tv_chooseFoodQuantity.setText(String.valueOf(foodCombo.getQuantity()));
        holder.img_chooseFoodImage.setImageBitmap(bitmaps);
        if(!isAdmin){
            holder.btn_removeChooseFood.setVisibility(View.INVISIBLE);
            holder.tv_chooseFoodQuantity.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return foodComboList.size();
    }

    public class FoodComboChooseHolder extends RecyclerView.ViewHolder {
        TextView tv_chooseFoodName, tv_chooseFoodQuantity;
        Button btn_removeChooseFood;
        ImageView img_chooseFoodImage;


        public FoodComboChooseHolder(@NonNull View itemView) {
            super(itemView);
            img_chooseFoodImage = itemView.findViewById(R.id.img_chooseFoodImage);
            tv_chooseFoodName = itemView.findViewById(R.id.tv_chooseFoodName);
            tv_chooseFoodQuantity = itemView.findViewById(R.id.tv_chooseFoodQuantity);
            btn_removeChooseFood = itemView.findViewById(R.id.btn_removeChooseFood);
            btn_removeChooseFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        FoodCombo clickedFoodCombo = foodComboList.get(position);
                        if (listener != null) {
                            listener.OnDeleteItem(clickedFoodCombo);
                        }
                    }
                }
            });
        }

        public int getQuantity() {
            return Integer.parseInt(tv_chooseFoodQuantity.getText().toString().trim());
        }
    }

}


