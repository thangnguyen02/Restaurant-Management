package com.example.restaurantmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.EventListener.TableOrderClickListener;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.Models.TableOrderCustomer;
import com.example.restaurantmanagement.R;

import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableViewHolder> {
    Context context;
    List<TableOrder> listTableOrder;
    TableOrderClickListener listener;
    public TableListAdapter(Context context, List<TableOrder> listTableOrder, TableOrderClickListener listener) {
        this.context = context;
        this.listTableOrder = listTableOrder;
        this.listener = listener;
    }
    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TableViewHolder(LayoutInflater.from(context).inflate(R.layout.table_component,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        int status = 1;
        TableOrderCustomer busy = AppDatabase.getInstance(
                context
        ).tableOrderCustomerDAO().findByTableOrder(listTableOrder.get(position).getID());
        holder.text_table.setText(listTableOrder.get(position).getDescription());

        holder.text_number.setText("Number: "+listTableOrder.get(position).getNumber());
        holder.text_status.setText("Free");
        holder.table_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(listTableOrder.get(holder.getAdapterPosition()));
            }
        });
    }



    @Override
    public int getItemCount() {
        return listTableOrder.size();
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