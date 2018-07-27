package com.uok.se.thisara.smart.trackerapplication.util;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.uok.se.thisara.smart.trackerapplication.dao.BusDao;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;

@Database(entities = {Bus.class}, version = 1)
public abstract class SqliteDbRef extends RoomDatabase {

    public abstract BusDao busDao();

}
