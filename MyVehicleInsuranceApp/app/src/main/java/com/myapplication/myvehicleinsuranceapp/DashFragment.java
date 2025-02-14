package com.myapplication.myvehicleinsuranceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.myapplication.myvehicleinsuranceapp.adapter.ClaimModelAdapter;
import com.myapplication.myvehicleinsuranceapp.appdatabase.AppDatabases;
import com.myapplication.myvehicleinsuranceapp.model.Claim;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;


public class DashFragment extends Fragment {

    EditText editText1;
    Button add_btn,delete_btn,show_btn,update_btn,add_img,add_video;
    //ListView listView1;

    LinearLayout layout_main;
    private RecyclerView recyclerView;
    private ClaimModelAdapter claimAdapter;
    private List<Claim> claimsList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.fragment_dash, container, false);

        AppDatabases db = AppDatabases.getInstance(getContext());
        // Initialize list_item first
        recyclerView = v.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        claimAdapter = new ClaimModelAdapter(getContext(), claimsList);
        recyclerView.setAdapter(claimAdapter);

        editText1=v.findViewById(R.id.editText1);
        add_btn=v.findViewById(R.id.add_btn);
        delete_btn=v.findViewById(R.id.delete_btn);
        show_btn=v.findViewById(R.id.show_btn);
        update_btn=v.findViewById(R.id.update_btn);
        add_img=v.findViewById(R.id.add_img);
        add_video=v.findViewById(R.id.add_video);
        layout_main=v.findViewById(R.id.layout_main);


        //adding data
        add_btn.setOnClickListener(v1 -> {
            String data = editText1.getText().toString();

            if (data.isEmpty()) {
                Toast.makeText(getContext(), "Please Enter Description", Toast.LENGTH_SHORT).show();
            } else {
                String status = "pending";
                String dateSub = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(new Date());
                String dateUpdate = dateSub;

                Claim newClaim = new Claim(data, status, dateSub, dateUpdate);

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.appDao().insertClaim(newClaim);
                    //Log.d("Database", "Data inserted: " + newClaim.toString());

                    // Fetch updated claims and update RecyclerView
                    List<Claim> updatedClaims = db.appDao().getAllClaims();

                    getActivity().runOnUiThread(() -> {
                        claimAdapter.setClaims(updatedClaims);
                        claimAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Data Added Successfully!", Toast.LENGTH_SHORT).show();
                    });
                });

                editText1.setText(""); // Clear input field
            }
        });





        //displaying data => view history
        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    List<Claim> newClaimList = db.appDao().getAllClaims();

                    getActivity().runOnUiThread(() -> {
                        claimAdapter.clearClaims();
                        claimAdapter.setClaims(newClaimList);
                        claimAdapter.notifyDataSetChanged();
                    });
                });
            }
        });

        //deleting
        delete_btn.setOnClickListener(v1 -> {
            ((MainActivity) requireActivity()).replaceFragment(new DeleteFragment(), "Delete Fragment");

        });


        //updating
        update_btn.setOnClickListener(v1 -> {
        ((MainActivity) requireActivity()).replaceFragment(new Updateragment(), "Update Fragment");

    });

        //image fragment
        add_img.setOnClickListener(v1 ->{
            ((MainActivity) requireActivity()).replaceFragment(new ImageFragment(), "Image  Fragment");
        });

        //video fragment
        add_video.setOnClickListener(v1->{
            ((MainActivity) requireActivity()).replaceFragment(new VideoFragment(), "Video  Fragment");
        });
        return v;
    }

}