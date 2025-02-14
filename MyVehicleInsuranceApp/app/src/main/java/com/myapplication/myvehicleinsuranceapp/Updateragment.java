package com.myapplication.myvehicleinsuranceapp;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.myvehicleinsuranceapp.appdatabase.AppDatabases;
import com.myapplication.myvehicleinsuranceapp.model.Claim;
import com.myapplication.myvehicleinsuranceapp.notification.NotificationHelper;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Updateragment extends Fragment {

    View v;
    EditText update_text_id,update_text_status;
    Button updateBtn;
    private ExecutorService executorService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_updateragment, container, false);
        AppDatabases db = AppDatabases.getInstance(getContext());
        executorService = Executors.newSingleThreadExecutor(); // Executor for background tasks
        updateBtn=v.findViewById(R.id.updateBtn);
        update_text_id=v.findViewById(R.id.update_text_id);
        update_text_status=v.findViewById(R.id.update_text_status);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String claimIdStr = update_text_id.getText().toString();
                String newStatus = update_text_status.getText().toString();

                if (claimIdStr.isEmpty() || newStatus.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int claimId = Integer.parseInt(claimIdStr);
                    String updatedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    executorService.execute(() -> {
                        // Perform database update operation
                        Claim existingClaim = db.appDao().getClaimById(claimId);

                        if (existingClaim != null) {
                            existingClaim.setStatus(newStatus);
                            existingClaim.setDateUpdated(updatedDate);
                            db.appDao().updateClaim(existingClaim);

                            // Update UI on the main thread
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "User status updated", Toast.LENGTH_SHORT).show();
                                NotificationHelper.sendNotification(requireContext(), existingClaim.getStatus());
                                ((MainActivity) requireActivity()).replaceFragment(new DashFragment(), "Dashboard Fragment");

                            });
                        } else {
                            // Claim not found
                            requireActivity().runOnUiThread(() -> {
                                Toast.makeText(getContext(), "User " + claimId + " not available!", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.layout_main, new DashFragment()).commit();

                            });
                        }
                    });

                }
                catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid User ID.", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return  v;
    }



}