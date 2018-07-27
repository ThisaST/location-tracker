package com.uok.se.thisara.smart.trackerapplication.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.uok.se.thisara.smart.trackerapplication.model.Bus;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BusDao {

    @Insert(onConflict = REPLACE)
    void save(Bus busList);


    /*@Query("SELECT * FROM bus")
    LiveData<List<Bus>> load(String userId);*/

}
