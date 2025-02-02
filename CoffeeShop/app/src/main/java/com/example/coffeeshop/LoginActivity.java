package com.example.coffeeshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginButton, registerButton;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Accessing the components
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        resultText = findViewById(R.id.resultText);

        loginButton.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "Login Button Pressed", Toast.LENGTH_SHORT).show();

            if (username.getText().toString().equalsIgnoreCase("harish_ks") && password.getText().toString().equalsIgnoreCase("admin")) {
                resultText.setText(getString(R.string.loginSuccessful));

                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);

                // Intent - passing data
                intent.putExtra("Username", username.getText().toString());
                intent.putExtra("UserId", 2502);

                // Bundle - passing data
                Bundle bundle = new Bundle();
                bundle.putString("Password", password.getText().toString());
                bundle.putInt("PasswordId", 431);
                intent.putExtras(bundle);

                startActivity(intent);
            } else {
                resultText.setText(getString(R.string.loginFailed));
            }
        });

        registerButton.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "Register Button Pressed", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}

