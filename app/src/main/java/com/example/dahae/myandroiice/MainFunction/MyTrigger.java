package com.example.dahae.myandroiice.MainFunction;

import android.app.Service;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.dahae.myandroiice.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTrigger extends Service implements SensorEventListener {

    Intent CheckPlan;

    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;
    SensorManager mSm;

    TimerTask mTask;
    Timer  mTimer = new Timer();
    int mCountLifeCycle = 3;

    String mTriggerInfo ="";

    int mCounter = 0;
    int mCounterCapture = 0;
    int mProxiCount = 0;
    int mCloseCount = 0;
    float mSenRange = 26.0f;
    int mSenSpeed = 1500;

    ArrayList<AccelValue> arValue = new ArrayList<>();
    final static int MAXCOUNT = 20;

    class AccelValue {
        float[] value = new float[3];
        long time;
    }

    public void onCreate() {
        super.onCreate();

        mTask = new TimerTask() {
            @Override
            public void run() {
                mCountLifeCycle++;
                Log.d(MainActivity.TAG, "mCountLifeCycle " +mCountLifeCycle);
            }
        };
        mTimer.schedule(mTask, 1000, 1000);
        Log.d(MainActivity.TAG, "*My trigger onCreate()");

//         SMS수신
        registerReceiver(mybroadcastforComplex, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
//         스크린
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_SCREEN_OFF));
//         비행기모드
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
//         전화발신
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
//         충전기
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
//         블루투스
        registerReceiver(mybroadcastforComplex, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        registerReceiver(mybroadcastforComplex, new IntentFilter(BluetoothA2dp.ACTION_CONNECTION_STATE_CHANGED));
//        와이파이
        registerReceiver(mybroadcastforComplex, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        registerReceiver(mybroadcastforComplex, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        registerReceiver(mybroadcastforComplex, new IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION));
        registerReceiver(mybroadcastforComplex, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
//        이어폰
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
//        부팅됐을때
        registerReceiver(mybroadcastforComplex, new IntentFilter(Intent.ACTION_BOOT_COMPLETED));

        registerReceiver(mybroadcastforComplex, new IntentFilter("android.intent.action.PHONE_STATE"));
        registerReceiver(mybroadcastforComplex, new IntentFilter("android.intent.action.NEW_OUTGOING_CALL"));
        registerReceiver(mybroadcastforComplex, new IntentFilter("Alarm"));

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        int delay = SensorManager.SENSOR_DELAY_UI;
        mSm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSm.registerListener(mSensorListener,mSm.getDefaultSensor(Sensor.TYPE_PROXIMITY), delay);
        mSm.registerListener(mSensorListener, mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        mSm.registerListener(mSensorListener, mSm.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

        StartCheckPlan("");
    }

    public void StartCheckPlan(String brodcastInfo){
        if( mCountLifeCycle > 2) {
            CheckPlan  = new Intent(getApplicationContext(), CheckPlan.class);
            CheckPlan.putExtra("BrodcastInfo", brodcastInfo);
            startService(CheckPlan);
            mCountLifeCycle = 0;
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mybroadcastforComplex);
        mTimer.cancel();
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
        Log.d(MainActivity.TAG,"*My trigger OnDestroy");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    SensorEventListener mSensorListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {

            if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
                ;
            Intent CheckPlan = new Intent(getApplication(), CheckPlan.class);
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER: {

                    AccelValue av = new AccelValue();
                    av.value[0] = event.values[0];
                    av.value[1] = event.values[1];

                    av.time = event.timestamp / 100000L;
                    if (arValue.size() == MAXCOUNT)
                        arValue.remove(0);
                    arValue.add(av);

                    float[] min = new float[]{100, 100, 100};
                    float[] max = new float[]{-100, -100, -100};
                    for (int i = arValue.size() - 1; i >= 0; i--) {
                        AccelValue v = arValue.get(i);
                        if (av.time - v.time > mSenSpeed)
                            break;
                        for (int j = 0; j < 3; j++) {
                            min[j] = Math.min(min[j], v.value[j]);
                            max[j] = Math.max(max[j], v.value[j]);
                        }
                    }
                    float[] diff = new float[3];
                    for (int j = 0; j < 3; j++)
                        diff[j] = Math.abs(max[j] - min[j]);

                    if (diff[0] > mSenRange) {
                        mCounter++;
                        if (mCounter == 6) {
                            Log.d(MainActivity.TAG, mCounter +" in sensor");
                            mCounter = 0;
                                if (checkInBroadcast("SensorLR")) {
                                    Log.d(MainActivity.TAG, "SensorLR");
                                    StartCheckPlan("SensorLR");
                                }
                                arValue.clear();
                        }
                        break;
                    }

                    if (diff[1] > mSenRange){
                        mCounterCapture++;
                        if (mCounterCapture ==6) {
                            if (checkInBroadcast("SensorUPDOWN")) {

                                Log.d(MainActivity.TAG, "SensorUPDOWN");
                                StartCheckPlan("SensorUPDOWN");
                            }
                        }
                        break;
                    }

                    if (event.values[0] < 1 && event.values[1] < 1 && event.values[2] < -9 && event.values[0] > -1 && event.values[1] > -1) {
                        if (checkInBroadcast("UpsideDown")) {
                            Log.d(MainActivity.TAG, "UpsideDown");
                            StartCheckPlan("UpsideDown");
                        }
                        break;
                    }
                }
                break;
                case Sensor.TYPE_PROXIMITY: {
                    mProxiCount++;
                    if (mProxiCount == 4) {
                        if (checkInBroadcast("SensorClose")) {

                            Log.d(MainActivity.TAG, "SensorClose / mProxiCount " + mProxiCount);
                            StartCheckPlan("SensorClose");
                        }
                        mProxiCount = 0;
                    }
                }
                break;

                case Sensor.TYPE_LIGHT: {
                    float bright = event.values[0];
                    if(bright>0) {
                        if (bright < 20) {
                            //Log.d(MainActivity.TAG, "Sensor.TYPE_LIGHT " + bright);
                            mCloseCount++;
                        }
                    }
                    if(mCloseCount == 3) {
                        if (checkInBroadcast("SensorBright")) {
                            StartCheckPlan("SensorBright");
                        }
                        mCloseCount = 0;
                    }
                }
                break;
            }
        }
    };

    BroadcastReceiver mybroadcastforComplex = new BroadcastReceiver() {

        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            String action = intent.getAction();
            Bundle extras = intent.getExtras();
            Log.i(MainActivity.TAG, "hi " + action);

            if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {

                WifiManager m_WifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                switch (m_WifiManager.getWifiState()) {
                    case WifiManager.WIFI_STATE_DISABLING:

                        if (checkInBroadcast("WifiOff")) {
                            Log.i(MainActivity.TAG, "WifiOff");
                            StartCheckPlan("WifiOff");
                        }
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:

                        if (checkInBroadcast("WifiOn")) {
                            Log.i(MainActivity.TAG, "WifiOn");
                            StartCheckPlan("WifiOn");
                        }
                        break;
                }
            } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {

                if (checkInBroadcast("ScreenOff")) {
                    Log.i(MainActivity.TAG, "Screen OFF");

                    StartCheckPlan("ScreenOff");
                }

            } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                if (checkInBroadcast("ScreenOn")) {
                    Log.i(MainActivity.TAG, "Screen ON");

                    StartCheckPlan("ScreenOn");
                }

            } else if (action.equals("android.media.RINGER_MODE_CHANGED")) {
                AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                switch (am.getRingerMode()) {
                    case AudioManager.RINGER_MODE_NORMAL:
                        if (checkInBroadcast("Sound"))
                            StartCheckPlan("Sound");
                        break;
                    case AudioManager.RINGER_MODE_SILENT:
                        if (checkInBroadcast("Silence"))
                            StartCheckPlan("Silence");

                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        if (checkInBroadcast("Vibration"))
                            StartCheckPlan("Vibration");

                        break;
                }
            } else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo niMobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (niMobile.getState() == NetworkInfo.State.CONNECTED) {
                    if (checkInBroadcast("DataOn")) {
                        Log.d(MainActivity.TAG, "Mobile CONNECTED");
                        StartCheckPlan("Mobile CONNECTED");
                    }
                } else if (niMobile.getState() == NetworkInfo.State.DISCONNECTED) {
                    if (checkInBroadcast("DataOff")) {
                        Log.d(MainActivity.TAG, "Mobile DISCONNECTED");
                        StartCheckPlan("Mobile DISCONNECTED");
                    }
                }
            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                switch (extras.getInt("android.bluetooth.adapter.extra.STATE", -1)) {
                    case BluetoothAdapter.STATE_OFF:
                        if (checkInBroadcast("BluetoothOff")) {
                            Log.d(MainActivity.TAG, " false BluetoothAdapter STATE_OFF");
                            StartCheckPlan("BluetoothOff");
                        }
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        if (checkInBroadcast("BluetoothOff")) {
                            Log.d(MainActivity.TAG, " false BluetoothAdapter STATE_TURNING_OFF");
                            StartCheckPlan("BluetoothOff");
                        }
                    case BluetoothAdapter.STATE_TURNING_ON:
                        if (checkInBroadcast("BluetoothOn")) {
                            Log.d(MainActivity.TAG, "True BluetoothAdapter STATE_TURNING_ON");//블루투스켜짐
                            StartCheckPlan("BluetoothOn");
                        }
                    case BluetoothAdapter.STATE_ON:
                        if (checkInBroadcast("BluetoothOn")) {
                            Log.d(MainActivity.TAG, "True BluetoothAdapter STATE_ON");//블루투스켜짐
                            StartCheckPlan("BluetoothOn");
                        }
                }
            } else if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
                if (checkInBroadcast("SMSreceiver")) {
                    Log.d(MainActivity.TAG, "SMS_RECEIVED");

                    abortBroadcast();

                    Bundle bundle = intent.getExtras();
                    Object messages[] = (Object[]) bundle.get("pdus");
                    SmsMessage smsMessage[] = new SmsMessage[messages.length];

                    int smsCount = messages.length;
                    for (int i = 0; i < smsCount; i++)
                        smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);

                    Date curDate = new Date(smsMessage[0].getTimestampMillis());
                    Log.i(MainActivity.TAG, "SMS Timestamp : " + curDate.toString());

                    String origNumber = smsMessage[0].getOriginatingAddress();

                    String message = smsMessage[0].getMessageBody().toString();
                    Log.i(MainActivity.TAG, "SMS : " + origNumber + ", " + message);

                    Intent CheckPlan  = new Intent(getApplicationContext(), CheckPlan.class);
                    CheckPlan.putExtra("OrigNumber", origNumber);
                    CheckPlan.putExtra("Message", message);
                    CheckPlan.putExtra("BrodcastInfo", "SMSreceiver");
                    startService(CheckPlan);


                }
            } else if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                boolean enabling = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;

                //비행기 모드 On
                if (checkInBroadcast("AirplaneModeOn")) {
                    if (enabling = true) {
                        Log.d(MainActivity.TAG, "AirplaneModeOn");
                        StartCheckPlan("AirplaneModeOn");
                    }
                }
                //비행기 모드 Off
                if (checkInBroadcast("AirplaneModeOff")) {
                    if (enabling = false) {
                        Log.d(MainActivity.TAG, "AirplaneModeOff");
                        StartCheckPlan("AirplaneModeOff");
                    }
                }

            } else if (action.equals("android.intent.action.PHONE_STATE")) {

                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                    if(checkInBroadcast("CallEnded")) {
                        Log.d(MainActivity.TAG, " EXTRA_STATE_IDLE ");
                        StartCheckPlan("CallEnded");
                    }
                }else if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

                    Log.d(MainActivity.TAG, "Telephony Manager.EXTRA_STATE_RINGING");
                    if(checkInBroadcast("CallReception")) {
                        if(mTriggerInfo.equals(extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER))) {

                            Log.d(MainActivity.TAG, " EXTRA_STATE_RINGING INCOMMING NUMBER : " + extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER));
                            StartCheckPlan("CallReception");
                        }
                    }
                }else if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                    Log.d(MainActivity.TAG, " EXTRA_STATE_OFFHOOK ");
                }
            } else if(action.equals(Intent.ACTION_NEW_OUTGOING_CALL)){
                if (checkInBroadcast("NewOutgoingCall")) {
                    StartCheckPlan("NewOutgoingCall");
                    Log.d(MainActivity.TAG, " OUTGOING CALL : " + extras.getString(Intent.EXTRA_PHONE_NUMBER));
                }
            } else if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                //배터리충전
                if (checkInBroadcast("PowerConnected")) {
                    Log.d(MainActivity.TAG, "ACTION_POWER_CONNECTED");
                    StartCheckPlan("PowerConnected");
                }
            } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {

                //배터리충전끝
                if (checkInBroadcast("PowerDisConnected")) {
                    Log.d(MainActivity.TAG, "ACTION_POWER_DISCONNECTED");
                    StartCheckPlan("PowerDisConnected");
                }
            } else if (action.equals(intent.ACTION_HEADSET_PLUG)) {
                switch (extras.getInt("state")) {
                    case 1:
                        if((checkInBroadcast("EarphoneIn"))) {
                            Log.d(MainActivity.TAG, "ACTION_HEADSET_PLUG (IN) DETECTED");
                            StartCheckPlan("EarphoneIn");
                        }
                        break;
                    case 0:
                        if(checkInBroadcast("EarphoneOut")) {
                            Log.d(MainActivity.TAG, "ACTION_HEADSET_PLUG (OUT) DETECTED");
                            StartCheckPlan("EarphoneOut");
                        }
                        break;
                }
            } else if(action.equals(intent.ACTION_BOOT_COMPLETED)) {

                if(checkInBroadcast("Booted")) {
                    Log.d(MainActivity.TAG, "ACTION_BOOT_COMPLETED");
                    StartCheckPlan("Booted");
                }
            } else if (action.equals(Intent.ACTION_BATTERY_CHANGED)){

                int level =  Integer.parseInt(BatteryManager.EXTRA_LEVEL);
                int scale = Integer.parseInt(BatteryManager.EXTRA_SCALE);
                float batteryPct = level / (float)scale;
                int bat= (int)(100 * batteryPct);

                if (checkInBroadcast("LowBattery")) {
                    if(bat < Integer.parseInt(mTriggerInfo))
                        StartCheckPlan("LowBattery");
                }
                if (checkInBroadcast("FullBattery")) {
                    if (bat > Integer.parseInt(mTriggerInfo))
                        StartCheckPlan("FullBattery");
                }
            }
        }
    };

    public boolean checkInBroadcast(String triggerName){

        String tableNameDB;
        boolean result = false;

        try {
            Cursor cursor =  MainActivity.database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

            try {
                // 모든 테이블 목록(플랜 목록) 보여주기
                if (MainActivity.database != null) {
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            tableNameDB = cursor.getString(0);
                            if (!tableNameDB.equals("android_metadata") && !tableNameDB.equals("sqlite_sequence")) {
                                if (triggerName != null && tableNameDB != null) {
                                    if (check(triggerName, tableNameDB)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }finally {
                if(cursor != null)
                    cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean check(String trigger, String tableNameDB){

        boolean result = false;
        try {
            Cursor CursorCheck = MainActivity.database.rawQuery("SELECT * FROM " + tableNameDB, null);
            try {
                if( MainActivity.database != null) {
                    if ( CursorCheck != null) {
                        while (CursorCheck.moveToNext()) {
                            if (!tableNameDB.equals("android_metadata") && !tableNameDB.equals("sqlite_sequence")) {
                                String keywordInDB = CursorCheck.getString(1);
                                int level_id = CursorCheck.getInt(3);

                                if (trigger.equals("LowBattery") || trigger.equals("FullBattery") || trigger.equals("CalleReception"))
                                    mTriggerInfo = CursorCheck.getString(2);
                                if(keywordInDB != null) {
                                    if (level_id != -1) {
                                        if (keywordInDB.equals(trigger)) {
                                            return true;
                                        }
                                    }}}}}}
            } finally {
                if(CursorCheck != null)
                    CursorCheck.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
