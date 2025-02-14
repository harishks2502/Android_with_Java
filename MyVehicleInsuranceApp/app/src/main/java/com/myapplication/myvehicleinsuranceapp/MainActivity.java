package com.myapplication.myvehicleinsuranceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    FrameLayout framelayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        framelayout1=findViewById(R.id.framelayout1);
        if (savedInstanceState == null) {
            replaceFragment(new LoginFragment(), "LoginFragment");
        }
    }

    public void replaceFragment(Fragment fragment, String fragmentName) {
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1, fragment, fragmentName).addToBackStack(null).commit();
    }
}

