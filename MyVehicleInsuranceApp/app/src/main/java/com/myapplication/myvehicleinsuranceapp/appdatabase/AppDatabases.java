package com.myapplication.myvehicleinsuranceapp.appdatabase;






import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myapplication.myvehicleinsuranceapp.DashFragment;
import com.myapplication.myvehicleinsuranceapp.dao.VideoDao;
import com.myapplication.myvehicleinsuranceapp.model.AppDAO;
import com.myapplication.myvehicleinsuranceapp.model.Claim;
import com.myapplication.myvehicleinsuranceapp.model.ClaimImage;
import com.myapplication.myvehicleinsuranceapp.model.ClaimImageDao;
import com.myapplication.myvehicleinsuranceapp.model.Video;


@Database(entities = {Claim.class, ClaimImage.class, Video.class}, version = 4, exportSchema = false)
public abstract class AppDatabases extends RoomDatabase {

    // DAO Definitions
    public abstract AppDAO appDao();
    public abstract ClaimImageDao claimImageDao();

    public abstract VideoDao videoDao();

    // Singleton Instance
    private static volatile AppDatabases instance;

    // Synchronized Method to Get the Instance
    public static synchronized AppDatabases getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabases.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabases.class, "claim_database")
                            .fallbackToDestructiveMigration() // Handle migration during version changes
                            .build();
                }
            }
        }
        return instance;
    }
}
