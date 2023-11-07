package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.restaurantmanagement.Adapters.TableListsAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.EventListener.TableOrderClickListener;
import com.example.restaurantmanagement.Models.TableOrder;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListTableActivity extends AppCompatActivity {
    private AppDatabase database;
    private ImageView back;
    private List<TableOrder> tables = new ArrayList<>();
    private RecyclerView recyclerView;
    private TableListsAdapter tableListAdapter;
    private FloatingActionButton f;
    UserDatabase userDatabase;
    public UserEntity user = new UserEntity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_table);

        database = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recycler_table);
        back = findViewById(R.id.back);
        f = findViewById(R.id.f_add);

        loadTables(); // Load tables initially


        userDatabase = UserDatabase.getUserDatabase(this);
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                user = userDatabase.userDao().getUserByUsername(userName);

            }
        }).start();
        if(user.getIsAdmin()==0){
            f.setVisibility(View.INVISIBLE);
            back.setVisibility(View.INVISIBLE);
        }
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTableActivity.this, AddTable.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListTableActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    // Load tables asynchronously
    private void loadTables() {
        new LoadTablesTask().execute();
    }

    // AsyncTask to load tables in the background
    private class LoadTablesTask extends AsyncTask<Void, Void, List<TableOrder>> {
        @Override
        protected List<TableOrder> doInBackground(Void... voids) {
            return database.tableOrderDAO().getAll();
        }

        @Override
        protected void onPostExecute(List<TableOrder> result) {
            super.onPostExecute(result);
            updateRecycler(result);
        }
    }

    // Update the RecyclerView with the new data
    private void updateRecycler(List<TableOrder> tables) {
        this.tables = tables;
        recyclerView.setHasFixedSize(true);
        tableListAdapter = new TableListsAdapter(ListTableActivity.this, tables, tableOrderClickListener);
        recyclerView.setAdapter(tableListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private final TableOrderClickListener tableOrderClickListener = new TableOrderClickListener() {
        @Override
        public void onClick(TableOrder tableOrder) {
            // Handle table click event if needed
        }
    };
}
