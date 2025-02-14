package com.myapplication.myvehicleinsuranceapp.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Claim {
    @PrimaryKey(autoGenerate = true)
    public int claimId;
     public String description;
    public String status; // e.g., "Pending", "Approved", "Rejected"
    public String dateSubmitted;
    public String dateUpdated;

    // Constructor
    public Claim( String description, String status, String dateSubmitted, String dateUpdated) {
        //this.claimId = claimId;
        this.description = description;
        this.status = status;
        this.dateSubmitted = dateSubmitted;
        this.dateUpdated = dateUpdated;
    }

    // Getters and Setters
    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    //@Override
    /*public String toString() {
        return "Claim ID: " + claimId + "\nClaim Description: " + description +"\nStatus: " + status + "\nSubmitted on: " + dateSubmitted + "\nLast Updated: " + dateUpdated;
    }*/
}