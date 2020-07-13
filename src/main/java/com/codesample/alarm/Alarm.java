package com.codesample.alarm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Alarm {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String time;
    public int alarmSound;
    public String volume;
    public String mission;
}
