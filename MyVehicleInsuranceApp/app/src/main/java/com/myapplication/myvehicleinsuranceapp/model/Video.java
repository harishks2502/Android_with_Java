package com.myapplication.myvehicleinsuranceapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "videos")
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fileName;
    private String uri;

    // Constructor
    public Video(String fileName, String uri) {
        this.fileName = fileName;
        this.uri = uri;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

