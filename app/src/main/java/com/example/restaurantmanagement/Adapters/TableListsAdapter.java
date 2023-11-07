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
    private Context context;
    private List<TableOrder> tableOrders;
    private TableOrderClickListener tableOrderClickListener;


    public TableListsAdapter(Context context, List<TableOrder> tableOrders, TableOrderClickListener tableOrderClickListener) {
        this.context = context;
        this.tableOrders = tableOrders;
        this.tableOrderClickListener = tableOrderClickListener;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableViewHolder(LayoutInflater.from(context).inflate(R.layout.table_component, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        TableOrder currentTable = tableOrders.get(position);

        holder.text_table.setText(currentTable.getDescription());
        holder.text_number.setText("Number: " + currentTable.getNumber());

        // Set status based on whether the description is empty or not
        if (currentTable.getDescription().isEmpty()) {
            holder.text_status.setText("Free");
        } else {
            holder.text_status.setText("Occupied");
        }

        holder.table_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableOrderClickListener.onClick(currentTable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableOrders.size();
    }

    // Method to update the data without recreating the adapter
    public void setData(List<TableOrder> newData) {
        tableOrders = newData;
        notifyDataSetChanged();
    }
}

class TableViewHolder extends RecyclerView.ViewHolder {
    CardView table_container;
    TextView text_table, text_number, text_status;

    public TableViewHolder(@NonNull View itemView) {
        super(itemView);
        table_container = itemView.findViewById(R.id.table_container);
        text_table = itemView.findViewById(R.id.textview_table);
        text_number = itemView.findViewById(R.id.textview_number);
        text_status = itemView.findViewById(R.id.textview_status);
    }
}
