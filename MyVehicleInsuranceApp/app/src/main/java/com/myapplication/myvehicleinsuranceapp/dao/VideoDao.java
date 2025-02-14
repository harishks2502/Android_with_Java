package com.myapplication.myvehicleinsuranceapp.dao;

import android.provider.MediaStore;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.myapplication.myvehicleinsuranceapp.model.Video;

import java.util.List;

@Dao
public interface VideoDao {
    @Insert
    void insert(Video video);

    @Query("SELECT * FROM videos")
    List<Video> getAllVideos();

    @Query("DELETE FROM videos WHERE id = :videoId")
    void deleteById(int videoId);
}
