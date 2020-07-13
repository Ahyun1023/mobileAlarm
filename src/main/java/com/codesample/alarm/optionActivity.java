package com.codesample.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codesample.alarm.databinding.ActivityOptionBinding;
import java.time.LocalDate;
import java.util.Calendar;

public class optionActivity extends AppCompatActivity{
    private static final String TAG = "optionActivity";
    private ActivityOptionBinding binding;
    private AlarmDatabase db;
    MediaPlayer player;
    public int volume = 0;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AlarmDatabase.getInstance(getApplicationContext());
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = binding.seekBar.getProgress();
                setSeekBar();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                volume = binding.seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                volume = binding.seekBar.getProgress();
            }
        });

        binding.buttonPlay.setOnClickListener(v -> {
            if(player != null){

            } else{
                player = MediaPlayer.create(this, R.raw.sound);
                player.start();
            }
        });
        binding.buttonStop.setOnClickListener(v -> {
            if(player != null){
                player.stop();
                player.release();
                player = null;
            }
        });

        binding.buttonSubmit.setOnClickListener(v->{
            save();
        });
    }

    private void setSeekBar(){
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nCurrentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        binding.seekBar.setMax(nMax);
        binding.seekBar.setProgress(nCurrentVolume);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(player != null){
            player.release();
            player = null;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(player != null){
            player.release();
            player = null;
        }
    }

    private void save(){
        Alarm alarm = getAlarm();
        if(alarm != null){
            setAlarm(this, 30);
            new SaveTask().execute(alarm);
        }
    }

    private void setAlarm(Context context, long second) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent Intent = new Intent(this, CallResultActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, Intent, 0);

        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + second, pIntent);
    }

    private Alarm getAlarm(){
        final RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        int id = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(id);

        TimePicker mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        String mHour = String.valueOf(mTimePicker.getHour());
        String mMin = String.valueOf(mTimePicker.getMinute());

        int alarmSound = R.raw.sound;
        String volumeNum = String.valueOf(new StringBuilder().append(volume));
        String mission = rb.getText().toString();

        Alarm alarm = new Alarm();
        alarm.time = mHour + ":" + mMin;
        alarm.volume = volumeNum;
        alarm.mission = mission;
        alarm.alarmSound = alarmSound;
        Log.d(TAG, alarm.time);
        return alarm;
    }

    class SaveTask extends AsyncTask<Alarm, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }

        @Override
        protected Void doInBackground(Alarm... alarms) {
            db.getAlarmDao().addAlarm(alarms[0]);
            return null;
        }
    }
}
