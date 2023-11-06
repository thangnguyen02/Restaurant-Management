package com.example.restaurantmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.EventListener.TableOrderClickListener;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.R;

import java.util.List;

public class TableListsAdapter extends RecyclerView.Adapter<TableViewHolder> {
   Context context;
   List<TableOrder> tableOrders;
   TableOrderClickListener tableOrderClickListener;

    public TableListsAdapter(Context context, List<TableOrder> tableOrders, TableOrderClickListener tableOrderClickListener) {
        this.context = context;
        this.tableOrders = tableOrders;
        this.tableOrderClickListener = tableOrderClickListener;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableViewHolder(LayoutInflater.from(context).inflate(R.layout.table_component,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        holder.text_table.setText(tableOrders.get(position).getDescription());
        holder.text_number.setText("Number: "+tableOrders.get(position).getNumber());
        holder.text_status.setText("Free");

        holder.table_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOrderClickListener.onClick(tableOrders.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableOrders.size();
    }
}
class TableViewHolder extends RecyclerView.ViewHolder {
        CardView table_container;
    TextView text_table,text_number,text_status;
    public TableViewHolder(@NonNull View itemView) {
        super(itemView);
        table_container = itemView.findViewById(R.id.table_container);
        text_table = itemView.findViewById(R.id.textview_table);
        text_number = itemView.findViewById(R.id.textview_number);
        text_status = itemView.findViewById(R.id.textview_status);
    }
}