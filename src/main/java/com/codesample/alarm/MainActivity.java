package com.codesample.alarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.codesample.alarm.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AlarmAdapter.OnListItemClickListener{
    private ActivityMainBinding binding;
    private AlarmDatabase db;
    private AlarmAdapter adapter;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = AlarmDatabase.getInstance(getApplicationContext());
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        adapter = new AlarmAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.floatingActionButton.setOnClickListener(v->{
            Intent intent = new Intent(this, optionActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        new LoadTask().execute();
    }

    @Override
    public void onListItemClick(Alarm a) {
    }

    @Override
    public void onListItemLongClick(Alarm a) {
        new AlertDialog.Builder(this)
                .setMessage("삭제하시겠습니까?")
                .setPositiveButton("확인", (d, w)->{
                    new DeleteTask().execute(a);
                })
                .setNegativeButton("아니오", (d, w)->{})
                .create()
                .show();
    }

    class LoadTask extends AsyncTask<Void, Void, List<Alarm>> {
        @Override
        protected void onPostExecute(List<Alarm> alarms) {
            super.onPostExecute(alarms);
            adapter.updateData(alarms);
        }

        @Override
        protected List<Alarm> doInBackground(Void... voids) {
            return db.getAlarmDao().getAll();
        }
    }

    class DeleteTask extends AsyncTask<Alarm, Void, List<Alarm>>{
        @Override
        protected void onPostExecute(List<Alarm> alarms) {
            super.onPostExecute(alarms);
            adapter.updateData(alarms);
        }

        @Override
        protected List<Alarm> doInBackground(Alarm... alarms) {
            db.getAlarmDao().deleteAlarm(alarms[0]);
            return db.getAlarmDao().getAll();
        }
    }
}
