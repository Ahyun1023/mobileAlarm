package com.codesample.alarm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Alarm.class}, version = 1, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    public abstract AlarmDao getAlarmDao();

    private static AlarmDatabase instance;
    public static AlarmDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                context,
                AlarmDatabase.class,
                    "db"
            ).build();
        }
        return instance;
    }
}
