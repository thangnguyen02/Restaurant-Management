package com.example.restaurantmanagement;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.room.Room;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.example.restaurantmanagement.Database.AppDatabase;
        import com.example.restaurantmanagement.Database.TableOrderDAO;
        import com.example.restaurantmanagement.Models.TableOrder;

        import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connect db
        database = AppDatabase.getInstance(this);
        setContentView(R.layout.activity_main);

    }

}