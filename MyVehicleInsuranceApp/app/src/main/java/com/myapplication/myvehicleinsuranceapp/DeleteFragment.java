package com.myapplication.myvehicleinsuranceapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapplication.myvehicleinsuranceapp.appdatabase.AppDatabases;
import com.myapplication.myvehicleinsuranceapp.model.Claim;
import com.myapplication.myvehicleinsuranceapp.notification.NotificationHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class DeleteFragment extends Fragment {

   EditText delete_text_id;
   Button deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_delete, container, false);
        delete_text_id=v.findViewById(R.id.delete_text_id);
        deleteBtn=v.findViewById(R.id.deleteBtn);


        AppDatabases db=AppDatabases.getInstance(getContext());
      deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Confirmation");
                builder.setMessage("Are you sure to delete the User?");
                int userId=Integer.parseInt(delete_text_id.getText().toString());

                Executors.newSingleThreadExecutor().execute(()->{
                    Claim clm=db.appDao().getClaimById(userId);
                    if(clm!=null)
                    {
                        db.appDao().deleteClaim(clm);
                        getActivity().runOnUiThread(()->{
                            Toast.makeText(getContext(),"Claim deleted successfully",Toast.LENGTH_SHORT).show();
                            ((MainActivity) requireActivity()).replaceFragment(new DashFragment(), "Dashboard Fragment");
                            NotificationHelper.sendNotification(requireContext(),"Claim is Deleted");
                        });
                    }
                });
            }
        });
        return v;
    }


}