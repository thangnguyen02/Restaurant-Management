package com.example.restaurantmanagement.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.restaurantmanagement.Adapters.ComboListAdapter;
import com.example.restaurantmanagement.Adapters.OrderListAdapter;
import com.example.restaurantmanagement.Database.AppDatabase;
import com.example.restaurantmanagement.Database.UserDao;
import com.example.restaurantmanagement.Database.UserDatabase;
import com.example.restaurantmanagement.EventListener.ComboClickListener;
import com.example.restaurantmanagement.Models.Combo;
import com.example.restaurantmanagement.Models.TableOrderCustomer;
import com.example.restaurantmanagement.Models.UserEntity;
import com.example.restaurantmanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ComboListActivity extends AppCompatActivity {

    UserDatabase userDatabase;
    AppDatabase database;
    RecyclerView recyclerView;
    ComboListAdapter comboListAdapter;
    List<Combo> combos = new ArrayList<>();
    public UserEntity user = new UserEntity();
    Button btn_MoveToAddCombo;
    Button btn_MoveToHome;
    TextView txt_searchComboName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_list);

        userDatabase = UserDatabase.getUserDatabase(this);
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                user = userDatabase.userDao().getUserByUsername(userName);
            }
        }).start();


        database = AppDatabase.getInstance(this);
        recyclerView = findViewById(R.id.rv_Combo);
        btn_MoveToAddCombo = findViewById(R.id.btn_MoveToAddCombo);
        btn_MoveToHome = findViewById(R.id.btn_MoveToHome);
        txt_searchComboName = findViewById(R.id.txt_searchComboName);

        combos = database.comboDAO().GetAllCombo();

        btn_MoveToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComboListActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (user.getIsAdmin() == 1) {
            btn_MoveToAddCombo.setVisibility(View.VISIBLE);
            btn_MoveToAddCombo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ComboListActivity.this, AddCombo.class);
                    startActivity(intent);

                }
            });
        }

        updateComboRecycler(combos);

        txt_searchComboName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<Combo> searchCombos = database.comboDAO().findComboByName("%" + s.toString() + "%");
                updateComboRecycler(searchCombos);
            }
        });
    }

    private void updateComboRecycler(List<Combo> combos) {
        recyclerView.setHasFixedSize(true);
        comboListAdapter = new ComboListAdapter(ComboListActivity.this, combos, comboClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(comboListAdapter);
    }

    private ComboClickListener comboClickListener = new ComboClickListener() {
        @Override
        public void onClick(Combo combo) {
            if (user.getIsAdmin() == 1) {
                Intent intent = new Intent(ComboListActivity.this, AddCombo.class);
                intent.putExtra("combo", combo);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(ComboListActivity.this, ComboDetailActivity.class);
                intent.putExtra("combo", combo);
                startActivity(intent);
                finish();
            }
        }
    };
}