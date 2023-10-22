package com.example.restaurantmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.R;

import java.util.List;

public class ComboListAdapter extends RecyclerView.Adapter<ComboViewHolder> {

    Context context;
    List<Combo> listCombos;


    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComboViewHolder(LayoutInflater.from(context).inflate(R.layout.order_components,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder holder, int position) {
        Combo combo = listCombos.get(position);

        holder.tv_ComboPrice.setText(String.valueOf(combo.getPrice()));
        holder.tv_ComboName.setText(combo.getName());
        holder.tv_ComboDescription.setText(combo.getDescription());


    }

    @Override
    public int getItemCount() {
        return listCombos.size();
    }
}

class ComboViewHolder extends RecyclerView.ViewHolder{
    CardView combo_container;
    TextView tv_ComboName, tv_ComboDescription, tv_ComboPrice;


    public ComboViewHolder(@NonNull View itemView) {
        super(itemView);
        combo_container = itemView.findViewById(R.id.cv_Combo);
        tv_ComboDescription = itemView.findViewById(R.id.tv_ComboDescription);
        tv_ComboName = itemView.findViewById(R.id.tv_ComboName);
        tv_ComboPrice = itemView.findViewById(R.id.tv_ComboPrice);
    }
}
