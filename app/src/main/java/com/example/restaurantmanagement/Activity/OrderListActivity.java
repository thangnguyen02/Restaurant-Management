package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.restaurantmanagement.Adapters.OrderListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.EventListener.TableOrderCustomerClickListener;
import com.example.restaurantmanagement.MainActivity;
import com.example.restaurantmanagement.Models.TableOrderCustomer;
import com.example.restaurantmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends AppCompatActivity {
    AppDatabase database;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    OrderListAdapter orderListAdapter;
    List<TableOrderCustomer> orders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        recyclerView = findViewById(R.id.recycler_order);
        floatingActionButton = findViewById(R.id.f_add);
        database = AppDatabase.getInstance(this);
        orders = database.tableOrderCustomerDAO().getAll();
        updateRecycler(orders);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderListActivity.this, AddOrder.class);
                startActivityForResult(intent,101);
            }
        });
    }
    private void updateRecycler(List<TableOrderCustomer> orders) {
        recyclerView.setHasFixedSize(true);
        orderListAdapter = new OrderListAdapter(OrderListActivity.this,orders,database.tableOrderDAO().getAll(),TBeventlistener);
        recyclerView.setAdapter(orderListAdapter);
    }
    private final TableOrderCustomerClickListener TBeventlistener = new TableOrderCustomerClickListener() {
        @Override
        public void onClick(TableOrderCustomer tableOrderCustomer) {

        }

        @Override
        public void onLongClick(TableOrderCustomer tableOrderCustomer, CardView cardview) {

        }
    };
}