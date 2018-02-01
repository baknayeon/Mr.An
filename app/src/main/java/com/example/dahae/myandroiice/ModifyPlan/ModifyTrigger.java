package com.example.dahae.myandroiice.ModifyPlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.KeywordAdapter;
import com.example.dahae.myandroiice.Information.trigger.IntroClose;
import com.example.dahae.myandroiice.Information.trigger.IntroShakeLR;
import com.example.dahae.myandroiice.Information.trigger.IntroShakeUPDOWN;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.TriggerForBattery;
import com.example.dahae.myandroiice.Triggers.TriggerForMap;
import com.example.dahae.myandroiice.Triggers.TriggerForPhoneReception;
import com.example.dahae.myandroiice.Triggers.TriggerForSMS;
import com.example.dahae.myandroiice.Triggers.TriggerForAlarm;

public class ModifyTrigger extends AppCompatActivity { //http://apphappy.tistory.com/10 참조!

    String triggerName = "";
    String triggerInfo = "";

    String arr1[] = { "Wi-Fi 켜짐", "Wi-Fi 꺼짐", "화면 켜짐", "화면 꺼짐", "소리모드", "진동모드", "무음모드", // 7
            "데이터네트워크 켜짐", "데이터네트워크 꺼짐", "블루투스 켜짐", "블루투스 꺼짐", // 8-11
            "비행기모드 켜짐","비행기모드 꺼짐", //12 - 13
            "통화 종료시", "전화 수신시", "SMS 수신시", // 14 - 16
            "충전기 연결시", "충전기 해제시", "이어폰 연결시", "이어폰 연결 해제시",  // 17- 20
            "베터리 N이하", "베터리 N이상", // 21-22
            "폰 뒤집기", "양쪽 흔들기", "위아래 흔들기","밝기 센서","근접 센서", // 23 - 27
            "장소", "시간"//28-29
    };

    ListView triggerKeyword;
    /**
     * Retrieving this instance's number from its arguments.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plans_list);
        triggerKeyword = (ListView) findViewById(R.id.planList);

        setTriggerKeyword();

        triggerKeyword.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    //와이파이 켜짐
                    triggerName = "WifiOn";
                    intent();
                } else if (position == 1) {
                    //와이파이 꺼짐
                    triggerName = "WifiOff";
                    intent();
                } else if (position == 2) {
                    //화면 켜짐
                    triggerName = "ScreenOn";
                    intent();
                } else if (position == 3) {
                    //화면 꺼짐
                    triggerName = "ScreenOff";
                    intent();
                } else if (position == 4) {
                    //소리모드
                    triggerName = "Sound";
                    intent();
                } else if (position == 5) {
                    //진동모드
                    triggerName = "Vibration";
                    intent();
                } else if (position == 6) {
                    //무음모드
                    triggerName = "Silence";
                    intent();
                } else if (position == 7) {
                    //데이터네트워크 켜짐
                    triggerName = "DataOn";
                    intent();
                } else if (position == 8) {
                    //데이터네트워크 꺼짐
                    triggerName = "DataOff";
                    intent();
                } else if (position == 9) {
                    //블루투스 켜짐
                    triggerName = "BluetoothOn";
                    intent();
                } else if (position == 10) {
                    //블루투스 꺼짐
                    triggerName = "BluetoothOff";
                    intent();
                } else if (position == 11) {
                    //비행기모드 바꼈을때
                    triggerName = "AirplaneModeOn";
                    intent();
                } else if (position == 12) {
                    //비행기모드 바꼈을때
                    triggerName = "AirplaneModeOff";
                    intent();
                } else if (position == 13) {
                    //전화 발신
                    triggerName = "CallEnded";
                    intent();
                } else if (position == 14) {
                    triggerName = "CallReception";
                    Intent intentPhone = new Intent(getApplicationContext(), TriggerForPhoneReception.class);
                    startActivityForResult(intentPhone, 0);
                } else if (position == 15) {
                    //문자 수신시
                    triggerName = "SMSreceiver";
                    Intent intentSMS = new Intent(getApplicationContext(), TriggerForSMS.class);
                    startActivityForResult(intentSMS, 0);
                } else if (position == 16) {
                    //전원 연결시
                    triggerName = "PowerConnected";
                    intent();
                } else if (position == 17) {
                    //전원 해제시
                    triggerName = "PowerDisConnected";
                    intent();
                } else if (position == 18) {
                    //이어폰 연결시
                    triggerName = "EarphoneIn";
                    intent();
                } else if (position == 19) {
                    //이어폰 해제시
                    triggerName = "EarphoneOut";
                    intent();
                } else if (position == 20) {
                    triggerName = "LowBattery";
                    Intent intentBattery = new Intent(getApplicationContext(), TriggerForBattery.class);
                    intentBattery.putExtra("Battery", "Low");
                    startActivityForResult(intentBattery, 0);

                } else if (position == 21) {
                    triggerName = "FullBattery";
                    Intent intentBattery = new Intent(getApplicationContext(), TriggerForBattery.class);
                    intentBattery.putExtra("Battery", "Full");
                    startActivityForResult(intentBattery, 0);
                } else if (position == 22) {
                    triggerName = "UpsideDown";
                    intent();
                } else if (position == 23) {
                    Intent intentS = new Intent(getApplicationContext(), IntroShakeLR.class);
                    startActivity(intentS);
                    triggerName = "SensorLR";
                    intent();
                } else if (position == 24) {
                    Intent intentS = new Intent(getApplicationContext(), IntroShakeUPDOWN.class);
                    startActivity(intentS);
                    triggerName = "SensorUPDOWN";
                    intent();
                } else if (position == 25) {
                    triggerName = "SensorBright";
                } else if (position == 26) {
                    Intent intentS = new Intent(getApplicationContext(), IntroClose.class);
                    startActivity(intentS);
                    triggerName = "SensorClose";
                    intent();
                } else if (position == 27) {
                    triggerName = "Location";
                    Intent intentMap = new Intent(getApplicationContext(), TriggerForMap.class);
                    startActivityForResult(intentMap, 0);
                } else if (position == 28) {
                    triggerName = "Time";
                    Intent intentTime = new Intent(getApplicationContext(), TriggerForAlarm.class);
                    startActivityForResult(intentTime, 0);
                }

            }
        });
    }

    private void setTriggerKeyword(){

        KeywordAdapter keywordAdapter = new KeywordAdapter(this);
        for(int i = 0 ; i < arr1.length; i++) {
            keywordAdapter.addItem(new Keyword(arr1[i]));//고쳐
        }
        triggerKeyword.setAdapter(keywordAdapter);
    }

    public void intent(){

        Intent intent = getIntent();
        intent.putExtra("triggerName", triggerName);
        intent.putExtra("triggerInfo", triggerInfo);

        intent.setAction("ModifyTrigger");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){


            if(data.getAction().toString().equals("Time")) {
                Log.d(MainActivity.TAG, "Time in onActivityResult");
                Bundle extra = data.getExtras();

                boolean[] week = extra.getBooleanArray("mTriggerInfo_week");
                Long triggerTime= data.getLongExtra("mTriggerInfo_time", 0);

                for(int i =1; i<week.length ; i++)
                    triggerInfo += week[i]+".";

                triggerInfo += "+"+ triggerTime+"+";

            } else if(data.getAction().toString().equals("BrightUp")) {

                Log.d(MainActivity.TAG,"BrightUp in Type");
                triggerInfo= data.getStringExtra("mTriggerInfo");

                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger33" + triggerInfo);
            } else if(data.getAction().toString().equals("BrightDown")) {

                Log.d(MainActivity.TAG,"BrightDown in Type");
                triggerInfo= data.getStringExtra("mTriggerInfo");

            } else if(data.getAction().toString().equals("SMSreceiver")) {

                Log.d(MainActivity.TAG,"SMSreceiver");
                triggerInfo= data.getStringExtra("mTriggerInfo");

            } else if(data.getAction().toString().equals("PhoneReception")) {

                Log.d(MainActivity.TAG,"PhoneReception");
                triggerInfo= data.getStringExtra("mTriggerInfo");
            } else if(data.getAction().toString().equals("LowBattery")) {

                Log.d(MainActivity.TAG, "LowBattery in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");
            } else if(data.getAction().toString().equals("FullBattery")) {

                Log.d(MainActivity.TAG, "FullBattery in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");

            }else if (data.getAction().toString().equals("Map")) {
                Log.d(MainActivity.TAG, "Map in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");

            }
            intent();
        }
    }
}