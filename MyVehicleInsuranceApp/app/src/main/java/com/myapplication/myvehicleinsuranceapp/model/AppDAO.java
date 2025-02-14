package com.myapplication.myvehicleinsuranceapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.myapplication.myvehicleinsuranceapp.model.Claim;
import java.util.List;

@Dao
public interface AppDAO {

    @Insert
    void insertClaim(Claim claim);

    @Query("SELECT * FROM Claim")
    List<Claim> getAllClaims();

    @Update
    void updateClaim(Claim claim);

    @Delete
    void deleteClaim(Claim claim);


    @Query("Select * from Claim where claimId=:id")
    Claim getClaimById(int id);

}