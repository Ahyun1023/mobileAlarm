package com.codesample.alarm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;

import java.util.List;

@Dao
public interface AlarmDao {
    @Query("SELECT * FROM alarm")
    @TypeConverter
    List<Alarm> getAll();

    @Insert
    public long addAlarm(Alarm alarm);

    @Delete
    public int deleteAlarm(Alarm alarm);
}
