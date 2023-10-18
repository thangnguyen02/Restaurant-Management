package com.example.restaurantmanagement.EventListener;

import androidx.cardview.widget.CardView;

import com.example.restaurantmanagement.Models.TableOrderCustomer;

public interface TableOrderCustomerClickListener {
    void onClick(TableOrderCustomer tableOrderCustomer);
    void onLongClick(TableOrderCustomer tableOrderCustomer, CardView cardview);
}
