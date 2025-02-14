package com.myapplication.myvehicleinsuranceapp.model;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "claim_images") // This sets the table name as 'claim_images'
public class ClaimImage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "claim_id") // This sets the column name as 'claim_id'
    private String claimId;

    @ColumnInfo(name = "image_path") // This sets the column name as 'image_path'
    private String imagePath;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
