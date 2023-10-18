package com.example.restaurantmanagement;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.room.Room;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.example.restaurantmanagement.Actitvity.AddOrder;
        import com.example.restaurantmanagement.Adapters.OrderListAdapter;
        import com.example.restaurantmanagement.Database.AppDatabase;
        import com.example.restaurantmanagement.Database.TableOrderDAO;
        import com.example.restaurantmanagement.EventListener.TableOrderCustomerClickListener;
        import com.example.restaurantmanagement.Models.TableOrder;
        import com.example.restaurantmanagement.Models.TableOrderCustomer;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connect db
        setContentView(R.layout.activity_main);

    }



}