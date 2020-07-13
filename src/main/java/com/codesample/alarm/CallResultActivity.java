package com.codesample.alarm;

import android.app.Activity;
import android.os.Bundle;
import com.codesample.alarm.databinding.ActivityAlarmBinding;

public class CallResultActivity extends Activity {
    ActivityAlarmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.textView.setText("호출된 페이지");
        binding.textView.setTextSize(20);
        setContentView(binding.textView);
    }
}
