package com.myapplication.myvehicleinsuranceapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapplication.myvehicleinsuranceapp.R;
import com.myapplication.myvehicleinsuranceapp.model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final Context context;
    private final List<Video> videoList;
    private final OnVideoDeleteListener deleteListener;

    public interface OnVideoDeleteListener {
        void onVideoDelete(int videoId);
    }

    public VideoAdapter(Context context, List<Video> videoList, OnVideoDeleteListener deleteListener) {
        this.context = context;
        this.videoList = videoList;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = videoList.get(position);

        // Use the getter methods
        holder.tvName.setText(video.getFileName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUri()));
            intent.setDataAndType(Uri.parse(video.getUri()), "video/*");
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> deleteListener.onVideoDelete(video.getId()));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton btnDelete;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
