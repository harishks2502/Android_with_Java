package com.myapplication.myvehicleinsuranceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {
    public TextView register_tv;
    public Button logedin;
    public EditText loginEmail,loginPass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        register_tv=v.findViewById(R.id.register_btn);
        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1,new RegisterFragment(),"Register Fragment").commit();

            }
        });
        logedin=v.findViewById(R.id.logedin);
        loginEmail=v.findViewById(R.id.loginEmail);
        loginPass=v.findViewById(R.id.loginPass);
        logedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(loginEmail.getText().toString().equals("admin@123gmail.com") && loginPass.getText().toString().equals("admin@123"))
                {
                    Toast.makeText(getContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout1,new DashFragment(),"Dashboard Fragment").commit();
                }
                else
                {
                    Toast.makeText(getContext(),"Please enter valid details.",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }



}