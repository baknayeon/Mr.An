package com.example.dahae.myandroiice.MainFunction;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.dahae.myandroiice.Actions.ActionForCamera;
import com.example.dahae.myandroiice.Actions.ActionForRecord;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.StringTokenizer;

public class MyAction extends Service implements TextToSpeech.OnInitListener {

    android.hardware.Camera mCamera = null;
    boolean isCameraOn = false;

    TextToSpeech mTts;
    String str="";
    @Override
    public void onCreate() {
        super.onCreate();

        PowerManager powerManager = (PowerManager)this.getSystemService(Context.POWER_SERVICE);
        //mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, MainActivity.TAG);
//        pref = getSharedPreferences("pref",
//                Activity.MODE_PRIVATE);
//        editor = pref.edit();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if(intent != null) {

            String tableName = intent.getStringExtra("tableName");
            Cursor cursor =  MainActivity.database.rawQuery("SELECT * FROM " + tableName + " where keyword_level = -1", null);

            while(cursor.moveToNext()){
                String action = cursor.getString(1);
                String actionInfo = cursor.getString(2);

                Log.d(MainActivity.TAG, "*My action " +action +"/ "+actionInfo);
                Action(action, actionInfo);
            }
        }
        stopSelf();

        Log.d(MainActivity.TAG, "*My action Stop");
    }


    public void Action(String actionName, String actionInfo) {

        // Bluetooth - Local Bluetooth adapter
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Mode - Sound,Vibration,Silence - detecting & setting
        AudioManager aManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            /**
             *   Do Action
             */
            if (actionName.toString().equals("WifiOn")) {
                setWiFi(true);
            } else if (actionName.toString().equals("WifiOff")) {
                setWiFi(false);
            } else if (actionName.toString().equals("Sound")) {
                aManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            } else if (actionName.toString().equals("Vibration")) {
                aManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            } else if (actionName.toString().equals("Silence")) {
                aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            } else if (actionName.toString().equals("DataOn")) {
                setMobileDataState(true);
            } else if (actionName.toString().equals("DataOff")) {
                setMobileDataState(false);
            } else if (actionName.toString().equals("BluetoothOn")) {
                if (mBluetoothAdapter != null)
                    if (!mBluetoothAdapter.isEnabled())
                        mBluetoothAdapter.enable();
            } else if (actionName.toString().equals("BluetoothOff")) {
                if (mBluetoothAdapter != null)
                    if (mBluetoothAdapter.isEnabled())
                        mBluetoothAdapter.disable();
            } else if (actionName.toString().equals("AirplaneModeOn")) {
                Log.d(MainActivity.TAG, "AirplaneModeOn");
                modifyAirplanemode(true);
            } else if (actionName.toString().equals("AirplaneModeOff")) {
                Log.d(MainActivity.TAG, "AirplaneModeOff");
                modifyAirplanemode(false);
            }else if (actionName.toString().equals("Camera")) {
                Log.d(MainActivity.TAG, "Camera");

                Intent intent = new Intent(MyAction.this, ActionForCamera.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (actionName.toString().equals("FlashOn")) {
                    Log.d(MainActivity.TAG, "Flash ON");
                    try{
                       if(mCamera == null) {
                           mCamera = android.hardware.Camera.open();
                           android.hardware.Camera.Parameters mCameraParameters = mCamera.getParameters();
                           mCameraParameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                           mCameraParameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_INFINITY);
                           mCamera.setParameters(mCameraParameters);
                           mCamera.startPreview();

                       }
                    }catch(Exception e) {
                        Log.e(MainActivity.TAG, "again "+e);}
            }else if (actionName.toString().equals("FlashOff")) {
                Log.d(MainActivity.TAG, "Flash Off");
                try{
                    if(mCamera == null) {
                        mCamera = android.hardware.Camera.open();
                        android.hardware.Camera.Parameters mCameraParameters = mCamera.getParameters();
                        mCameraParameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                        mCameraParameters.setFocusMode(android.hardware.Camera.Parameters.FOCUS_MODE_INFINITY);
                        mCamera.setParameters(mCameraParameters);
                        mCamera.startPreview();
                        mCamera.release();
                        mCamera = null;
                    }
                }catch(Exception e) {
                    Log.e(MainActivity.TAG, "again "+e);
                }
            }else if (actionName.toString().equals("Bookmark")){
                Log.d(MainActivity.TAG, "Bookmark : " + actionInfo);

                Intent intent = this.getPackageManager().getLaunchIntentForPackage(actionInfo);
                startActivity(intent);
            } else if (actionName.toString().equals("AudioRecorder")){
                Log.d(MainActivity.TAG, "AudioRecorder");

                Intent intent = new Intent(getApplicationContext(), ActionForRecord.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else if (actionName.toString().equals("Call")){
                Log.d(MainActivity.TAG, "Call ;" + actionInfo);

                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+actionInfo));
                intentCall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentCall);
            }else if (actionName.toString().equals("SendingSMS")){
                Log.d(MainActivity.TAG, "SendingSMS ");
                String num = null;
                String massage = null;
                int i = 0;

                if (actionInfo.contains("+")) {
                    StringTokenizer st = new StringTokenizer(actionInfo, "+");
                    if(st.hasMoreTokens()) {
                        massage = st.nextToken();
                        num= st.nextToken();
                    }
                }
                Log.d(MainActivity.TAG, "num " + num);
                Log.d(MainActivity.TAG, "massage " + massage);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(num, null, massage, null, null);
            }else if (actionName.toString().equals("Notification")){

                Log.d(MainActivity.TAG, "Notification");
                sendNotification(actionInfo);
            }else if (actionName.toString().equals("TextToVoice")){

                Log.d(MainActivity.TAG, "TextToVoice " + actionInfo);
                mTts = new TextToSpeech(getApplicationContext(), this);
                mTts.setSpeechRate(0.3f);
            }else if (actionName.toString().equals("VolumeRing")){

                int index = Integer.parseInt(actionInfo);
                aManager.setStreamVolume(AudioManager.STREAM_RING, index, 0);
            }else if (actionName.toString().equals("VolumeMusic")){

                int index = Integer.parseInt(actionInfo);
                aManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
            }else if (actionName.toString().equals("HomeScreen")){
                Log.d(MainActivity.TAG, "HomeScreen");

                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }else if (actionName.toString().equals("keyLock")){
                Log.d(MainActivity.TAG, "keyLock");

                KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
                KeyguardManager.KeyguardLock keyLock = km.newKeyguardLock(KEYGUARD_SERVICE);
                keyLock.disableKeyguard();
                Toast.makeText(getApplicationContext(), "disableKeyguard()", Toast.LENGTH_SHORT).show();
            }else if (actionName.toString().equals("TellPhoneNum")){
                Log.d(MainActivity.TAG, "TellPhoneNum " + actionInfo);
                mTts = new TextToSpeech(getApplicationContext(), this);
            }else if (actionName.toString().equals("TellSMS")){
                Log.d(MainActivity.TAG, "TellSMS " + actionInfo);
                mTts = new TextToSpeech(getApplicationContext(), this);
            } else if (actionName.toString().equals("Plantrue")){
                Log.d(MainActivity.TAG, "Plantrue");
                PlanSetting(true, actionInfo);
            }else if (actionName.toString().equals("Planfalse")){
                Log.d(MainActivity.TAG, "Planfalse");
                PlanSetting(false, actionInfo);
            }



//             if (actionName.toString().equals("ScreenOn")) {
//                Log.d(MainActivity.TAG, "ScreenOn");
//
//                Intent intentScreen = new Intent(this, ActionForScreen.class);
//                intentScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intentScreen.putExtra("isScreen", true);
//                startActivity(intentScreen);
//            }else if (actionName.toString().equals("ScreenOff")){
//                if ((mWakeLock != null) &&           // we have a WakeLock
//                        (mWakeLock.isHeld() == false)) {  // but we don't hold it
//                    mWakeLock.acquire();
//                }
//            }
    }

    public void onDestroy() {
//        if (mWakeLock.isHeld())
//            mWakeLock.release();
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    // Wifi - Setting
    public void  setWiFi(boolean status) {
        WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        if ( status && !wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(true);
        else if ( !status && wifiManager.isWifiEnabled())
            wifiManager.setWifiEnabled(false);
    }
    public void setMobileDataState(boolean mobileDataEnabled)
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);

            if (null != setMobileDataEnabledMethod)
            {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
            }
        }
        catch (Exception ex)
        {
            Log.e(MainActivity.TAG, "Error setting mobile data state", ex);
        }
    }

    public void modifyAirplanemode(boolean mode) {

        if (mode) {
            Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 1);
            Intent intent = new Intent (Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", true);
            sendBroadcast(intent);

        } else {
            Settings.System.putInt(getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
            Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            intent.putExtra("state", false);
            sendBroadcast(intent);

        }
    }

    public void PlanSetting(boolean SettingOfPlan , String planName){

        try {
            if( SettingOfPlan){
                if(MainActivity.databaseForRecordTime != null)
                    MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable SET activation = 'true' " +
                            "WHERE planName = '" + planName + "';");
            }
            else if( !SettingOfPlan) {
                if (MainActivity.databaseForRecordTime != null)
                    MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable SET activation = 'false' " +
                            "WHERE planName = '" + planName + "';");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(String massage){

        double d = Math.random() *10000;
        int i = Integer.parseInt(String.valueOf(Math.round(d)));

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.anbisa, "[ANDROI-ICE]", System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE ;
        notification.number = 5;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, i, new Intent(this, MyAction.class), PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(this, "Massage", massage, pendingIntent);
        nm.notify(i, notification);

    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.KOREA);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.v(MainActivity.TAG, "Language is not available.");
            } else {
                Log.v(MainActivity.TAG, "sayHello(Speak)");
                mTts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
            }
        } else {
            Log.v(MainActivity.TAG, "Could not initialize TextToSpeech.");
        }
    }

}
