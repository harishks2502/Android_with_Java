package com.myapplication.myvehicleinsuranceapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.common.util.concurrent.ListenableFuture;
import com.myapplication.myvehicleinsuranceapp.adapter.ClaimImageAdapter;
import com.myapplication.myvehicleinsuranceapp.appdatabase.AppDatabases;
import com.myapplication.myvehicleinsuranceapp.model.ClaimImage;
import com.myapplication.myvehicleinsuranceapp.model.ClaimImageDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment {
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private AppDatabases database;
    private RecyclerView imagesRecyclerView;
    private Button capButton,back_btn;
    private PreviewView previewView;
    private ImageCapture imageCapture;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = AppDatabases.getInstance(requireContext());
        imagesRecyclerView = view.findViewById(R.id.claimimagerecyclerView);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        capButton = view.findViewById(R.id.capture_btn);
        previewView = view.findViewById(R.id.previewView);

        //back to dashboard
        back_btn=view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->{
            ((MainActivity)requireActivity()).replaceFragment(new DashFragment(),"Dashboard Fragment");
        });

        loadClaims();
        displayImages("CLAIM_ID_123");

        capButton.setOnClickListener(v -> checkAndRequestCameraPermission());
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();
                cameraProviderFuture.get().bindToLifecycle(this, cameraSelector, preview, imageCapture);

                capButton.setOnClickListener(v -> takePicture());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void takePicture() {
        if (imageCapture == null) {
            Toast.makeText(requireContext(), "Camera not ready", Toast.LENGTH_SHORT).show();
            return;
        }

        File photoFile = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                System.currentTimeMillis() + "_vehicle_damage.jpg");

        ImageCapture.OutputFileOptions options = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(options, ContextCompat.getMainExecutor(requireContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        String savedUri = photoFile.getAbsolutePath();
                        Log.d("savedURi path", savedUri);
                        saveImageToDb("CLAIM_ID_123", savedUri);
                        displayImages("CLAIM_ID_123");
                        Toast.makeText(requireContext(), "Image saved: " + savedUri, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                        Toast.makeText(requireContext(), "Error saving image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveImageToDb(String claimId, String imagePath) {
        ClaimImageDao dao = database.claimImageDao();

        new Thread(() -> {
            ClaimImage claimImage = new ClaimImage();
            claimImage.setClaimId(claimId);
            claimImage.setImagePath(imagePath);
            dao.insertImage(claimImage);
        }).start();
    }

    private void loadClaims() {
        new Thread(() -> {
            List<ClaimImage> claims = database.claimImageDao().getImagesByClaimId("CLAIM_ID_123");
            requireActivity().runOnUiThread(() -> {
                ClaimImageAdapter adapter = new ClaimImageAdapter(requireContext(), claims);
                imagesRecyclerView.setAdapter(adapter);
            });
        }).start();
    }

    private void displayImages(String claimId) {
        ClaimImageDao dao = database.claimImageDao();

        new Thread(() -> {
            List<ClaimImage> imagesList = new ArrayList<>();
            List<ClaimImage> images = dao.getImagesByClaimId(claimId);
            requireActivity().runOnUiThread(() -> {
                for (ClaimImage image : images) {
                    Log.d("Image", "Path: " + image.getImagePath());
                    imagesList.add(image);
                }
                ClaimImageAdapter claimImageAdapter = new ClaimImageAdapter(requireContext(), images);
                imagesRecyclerView.setAdapter(claimImageAdapter);
                claimImageAdapter.notifyItemInserted(imagesList.size());
            });
        }).start();
    }

    private void checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission is required to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
