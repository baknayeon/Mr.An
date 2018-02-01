package com.example.dahae.myandroiice.Triggers;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.dahae.myandroiice.R;

import java.util.Calendar;

public class TriggerForAlarm extends Activity implements View.OnClickListener {
    /** Called when the activity is first created. */

    TimePicker mTimePickerFrom;
    ToggleButton _toggleSun, _toggleMon, _toggleTue, _toggleWed, _toggleThu, _toggleFri, _toggleSat;
    AlarmManager alarm;

    int mHour;
    int mMinute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_alarm);

        alarm = (AlarmManager) this
                .getSystemService(Context.ALARM_SERVICE);
        mTimePickerFrom = (TimePicker) findViewById(R.id.timePickerFrom);
//         mTimePickerFrom = (TimePicker) findViewById(R.id.timePickerTo);
        _toggleSun = (ToggleButton) findViewById(R.id.toggle_sun);
        _toggleMon = (ToggleButton) findViewById(R.id.toggle_mon);
        _toggleTue = (ToggleButton) findViewById(R.id.toggle_tue);
        _toggleWed = (ToggleButton) findViewById(R.id.toggle_wed);
        _toggleThu = (ToggleButton) findViewById(R.id.toggle_thu);
        _toggleFri = (ToggleButton) findViewById(R.id.toggle_fri);
        _toggleSat = (ToggleButton) findViewById(R.id.toggle_sat);

        Calendar calendar = Calendar.getInstance();

        mTimePickerFrom.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });

        mTimePickerFrom.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        mTimePickerFrom.setCurrentMinute(calendar.get(Calendar.MINUTE));


        Button btn1 = (Button) this.findViewById(R.id.button1);
        btn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        switch (arg0.getId()) {
            case R.id.button1:

                Toast.makeText(getApplicationContext(), "알람 등록!", Toast.LENGTH_SHORT).show();

                boolean[] week = { false,  _toggleSun.isChecked(), _toggleMon.isChecked(), _toggleTue.isChecked(),
                        _toggleWed.isChecked(), _toggleThu.isChecked(), _toggleFri.isChecked(),
                        _toggleSat.isChecked() };

                Intent intent = getIntent();
                intent.setAction("Time");
                intent.putExtra("mTriggerInfo_week", week);
                intent.putExtra("mTriggerInfo_time", setTriggerTime());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
    private long setTriggerTime() {

        long atime = System.currentTimeMillis();

        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY,  mHour);
        curTime.set(Calendar.MINUTE, mMinute);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);

        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

}
