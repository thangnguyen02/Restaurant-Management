package com.example.restaurantmanagement.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.EventListener.TableOrderCustomerClickListener;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.Models.TableOrderCustomer;
import com.example.restaurantmanagement.R;

import java.util.Arrays;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    Context context;
    List<TableOrderCustomer> listOrder;
    List<TableOrder> listTableOrder;

    TableOrderCustomerClickListener listener;

    public OrderListAdapter(Context context, List<TableOrderCustomer> listOrder, List<TableOrder> listTableOrder, TableOrderCustomerClickListener listener) {
        this.context = context;
        this.listOrder = listOrder;
        this.listTableOrder = listTableOrder;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new OrderViewHolder(LayoutInflater.from(context).inflate(R.layout.order_components,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        int number = 0,floor = 0;
        for (int i = 0; i < listTableOrder.size();i++) {
            if (listTableOrder.get(i).getID() == listOrder.get(position).getTable_order_id()) {
                number =listTableOrder.get(i).getNumber();
                floor =listTableOrder.get(i).getFloor();
                break;
            }
        }
        holder.text_table.setText(listOrder.get(position).getName());
        holder.text_table.setSelected(true);

        holder.text_number.setText("Floor: "+ floor +"; Number: "+number);
        holder.text_date.setText("Date");
        holder.text_time.setText("Time");
        holder.order_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(listOrder.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }
}
class OrderViewHolder extends RecyclerView.ViewHolder{
    CardView order_container;
    TextView text_table,text_number,text_date,text_time;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        order_container = itemView.findViewById(R.id.order_container);
        text_table = itemView.findViewById(R.id.textview_table);
        text_number = itemView.findViewById(R.id.textview_number);
        text_date = itemView.findViewById(R.id.textview_date);
        text_time = itemView.findViewById(R.id.textview_time);
    }
}