package com.example.dahae.myandroiice.Triggers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.CheckPlan;

public class AlarmReceive extends BroadcastReceiver {

    public static String ACTION_ALARM = "com.ctsd.activity.HomeContainer";





    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        Bundle extra = intent.getExtras();

        boolean[] week = extra.getBooleanArray("Week");
        boolean isRepeat = extra.getBoolean("Repeat");

Log.d(MainActivity.TAG, "###################################ALARM");

        if (extra != null) {
            if (isRepeat) {
                for (int i = 1; i < week.length; i++) {
                    if (week[i]) {
                        Log.d(MainActivity.TAG, "**Alarm ; Repeat week[" + i + "] " + week[i]);
                        intentForService(context);
                    }
                }
            } else
                intentForService(context);

        }

    }

    public void intentForService(Context context){
        Log.d(MainActivity.TAG,"***Alarm! Ring");
        Intent intent = new Intent(context, CheckPlan.class);
       // intent.putExtra("i", 0);
        //intent.putExtra("query", "empty");
       // intent.putExtra("Complex", "empty");
        intent.putExtra("BrodcastInfo", "Time");
        context.startService(intent);
    }
}
