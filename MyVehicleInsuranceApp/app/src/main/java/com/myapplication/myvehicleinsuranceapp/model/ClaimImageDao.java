package com.myapplication.myvehicleinsuranceapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClaimImageDao {
    @Insert
    void insertImage(ClaimImage claimImage);

    @Query("SELECT * FROM claim_images WHERE claim_id = :claimId")
    List<ClaimImage> getImagesByClaimId(String claimId);
}


