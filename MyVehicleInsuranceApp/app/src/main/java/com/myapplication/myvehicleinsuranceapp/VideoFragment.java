package com.myapplication.myvehicleinsuranceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import android.Manifest;
import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.MediaStoreOutputOptions;
import androidx.camera.video.PendingRecording;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.util.concurrent.ListenableFuture;
import com.myapplication.myvehicleinsuranceapp.adapter.VideoAdapter;
import com.myapplication.myvehicleinsuranceapp.appdatabase.AppDatabases;
import com.myapplication.myvehicleinsuranceapp.model.Video;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoFragment extends Fragment {

    private static final String TAG = "CameraXVideo";
    private ExecutorService cameraExecutor;
    private VideoCapture<Recorder> videoCapture;
    private Recording currentRecording;
    private AppDatabases videoDatabase;
    private VideoAdapter videoAdapter;
    private List<Video> videoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videoDatabase = AppDatabases.getInstance(requireContext());

        PreviewView previewView = view.findViewById(R.id.previewView);
        Button btnRecord = view.findViewById(R.id.btnRecord);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        //back to dashboard
        Button back_btn=view.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v->{
            ((MainActivity)requireActivity()).replaceFragment(new DashFragment(),"Dashboard Fragment");
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        loadVideos(recyclerView);

        ActivityResultLauncher<String[]> permissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    Boolean cameraGranted = result.get(Manifest.permission.CAMERA);
                    Boolean audioGranted = result.get(Manifest.permission.RECORD_AUDIO);

                    if (cameraGranted != null && cameraGranted && audioGranted != null && audioGranted) {
                        startCamera(previewView, btnRecord);
                    } else {
                        Toast.makeText(requireContext(), "Permissions not granted", Toast.LENGTH_SHORT).show();
                    }
                });

        permissionLauncher.launch(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO});
        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    private void loadVideos(RecyclerView recyclerView) {
        Executors.newSingleThreadExecutor().execute(() -> {
            videoList = videoDatabase.videoDao().getAllVideos();
            requireActivity().runOnUiThread(() -> {
                videoAdapter = new VideoAdapter(requireContext(), videoList, videoId -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        videoDatabase.videoDao().deleteById(videoId);
                        loadVideos(recyclerView);
                    });
                });
                recyclerView.setAdapter(videoAdapter);
            });
        });
    }

    private void startCamera(PreviewView previewView, Button btnRecord) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Recorder recorder = new Recorder.Builder().setExecutor(cameraExecutor).build();
                videoCapture = VideoCapture.withOutput(recorder);
                Preview preview = new Preview.Builder().build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                Camera camera = cameraProvider.bindToLifecycle(this, androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA,
                        videoCapture,
                        preview);

                btnRecord.setOnClickListener(view -> toggleRecording(btnRecord));

            } catch (Exception e) {
                Log.e(TAG, "Use case binding failed", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void toggleRecording(Button btnRecord) {
        if (currentRecording != null) {
            // Stop recording
            currentRecording.stop();
            currentRecording = null;
            btnRecord.setText("Record");
        } else {
            // Create the video file
            String fileName = System.currentTimeMillis() + "_video.mp4";

            // Set up MediaStore content values
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/MyAppVideos");

            // Create MediaStore output options
            MediaStoreOutputOptions outputOptions = new MediaStoreOutputOptions.Builder(
                    requireContext().getContentResolver(),
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                    .setContentValues(contentValues)
                    .build();

            // Start recording
            PendingRecording pendingRecording = videoCapture.getOutput().prepareRecording(requireContext(), outputOptions);
            currentRecording = pendingRecording.start(ContextCompat.getMainExecutor(requireContext()), event -> {
                if (event instanceof VideoRecordEvent.Start) {
                    btnRecord.setText("Stop");
                } else if (event instanceof VideoRecordEvent.Finalize) {
                    btnRecord.setText("Record");

                    VideoRecordEvent.Finalize finalizeEvent = (VideoRecordEvent.Finalize) event;

                    if (finalizeEvent.hasError()) {
                        Log.e(TAG, "Recording failed: " + finalizeEvent.getError());
                    } else {
                        // Retrieve the URI from the finalize event
                        String savedUri = finalizeEvent.getOutputResults().getOutputUri().toString();
                        Log.d(TAG, "Recording saved to: " + savedUri);

                        // Save video details in the database
                        Executors.newSingleThreadExecutor().execute(() -> {
                            videoDatabase.videoDao().insert(new Video(fileName, savedUri));
                            requireActivity().runOnUiThread(() -> loadVideos((RecyclerView) requireView().findViewById(R.id.recyclerView)));
                        });
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cameraExecutor.shutdown();
    }
}
