package com.example.coffeeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static androidx.core.content.ContextCompat.startActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {

    TextView welcomeText, passwordText;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        welcomeText = findViewById(R.id.welcomeText);
        passwordText = findViewById(R.id.passwordText);
        logoutButton = findViewById(R.id.logoutButton);

        // Retrieve the data using Intent
        Intent intent = getIntent();
        String username = null;
        int userId = 0;
        if(intent != null){
            username = intent.getStringExtra("Username");
            userId = intent.getIntExtra("UserId", -1); // -1 to avoid NullPointerException
        }

        if(username != null && userId != 0){
            welcomeText.setText("Welcome, " + username + ",  User ID: " + userId);
        } else{
            welcomeText.setText("User");
        }

        // Retrieve the data using Bundle
        Bundle bundle = getIntent().getExtras();
        String password = null;
        int passwordId = 0;
        if(bundle != null){
             password = bundle.getString("Password");
             passwordId = bundle.getInt("PasswordId");
        }

        if(password != null && passwordId != 0){
            passwordText.setText("Password: " + password + ",  Password ID: " + passwordId);
        } else{
            passwordText.setText("");
        }


        logoutButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent1);
        });

    }
}