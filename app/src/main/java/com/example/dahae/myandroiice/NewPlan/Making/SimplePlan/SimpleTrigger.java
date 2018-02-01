package com.example.dahae.myandroiice.NewPlan.Making.SimplePlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.KeywordAdapter;
import com.example.dahae.myandroiice.Information.trigger.IntroClose;
import com.example.dahae.myandroiice.Information.trigger.IntroShakeLR;
import com.example.dahae.myandroiice.Information.trigger.IntroShakeUPDOWN;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.ChangingName;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.TriggerForAlarm;
import com.example.dahae.myandroiice.Triggers.TriggerForBattery;
import com.example.dahae.myandroiice.Triggers.TriggerForMap;
import com.example.dahae.myandroiice.Triggers.TriggerForPhoneReception;
import com.example.dahae.myandroiice.Triggers.TriggerForSMS;

public class SimpleTrigger extends Fragment {

    String triggerName = "empty";
    String triggerInfo = "";

    ListView triggerListView;
    KeywordAdapter triggerAdapter;

    com.example.dahae.myandroiice.NewPlan.ChangingName ChangingName = new ChangingName();

    String arr1[] = { "Wi-Fi 켜짐", "Wi-Fi 꺼짐", "화면 켜짐", "화면 꺼짐", "소리모드", "진동모드", "무음모드", // 7
            "데이터네트워크 켜짐", "데이터네트워크 꺼짐", "블루투스 켜짐", "블루투스 꺼짐", // 8-11
            "비행기모드 켜짐","비행기모드 꺼짐", //12 - 13
            "통화 종료시", "전화 수신시", "SMS 수신시", // 14 - 16
            "충전기 연결시", "충전기 해제시", "이어폰 연결시", "이어폰 연결 해제시",  // 17- 20
            "베터리 N이하", "베터리 N이상", // 21-22
            "폰 뒤집기", "양쪽 흔들기", "위아래 흔들기","밝기 센서","근접 센서", // 23 - 27
            "장소", "시간"//28-29
    };

    public SimpleTrigger() {
    }

    public static SimpleTrigger init(int pageNumber) {
        SimpleTrigger fragment = new SimpleTrigger();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.new_simple_plan_trigger, null);

        /* Type ListView Setting */
        triggerListView = (ListView) layoutView.findViewById(R.id.expandableListView);
        triggerAdapter = new KeywordAdapter(container.getContext());
        for(int i = 0; i < arr1.length; i++)
            triggerAdapter.addItem(new Keyword(arr1[i]));
        triggerListView.setAdapter(triggerAdapter);
        triggerListView.setOnItemClickListener(mTriggerItemClickListener);

        return layoutView;
    }

    private AdapterView.OnItemClickListener mTriggerItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            triggerName = "";

            if (position == 0) {
                //와이파이 켜짐
                triggerName = "WifiOn";
                addTolist();
            } else if (position == 1) {
                //와이파이 꺼짐
                triggerName = "WifiOff";
                addTolist();
            } else if (position == 2) {
                //화면 켜짐
                triggerName = "ScreenOn";
                addTolist();
            } else if (position == 3) {
                //화면 꺼짐
                triggerName = "ScreenOff";
                addTolist();
            } else if (position == 4) {
                //소리모드
                triggerName = "Sound";
                addTolist();
            } else if (position == 5) {
                //진동모드
                triggerName = "Vibration";
                addTolist();
            } else if (position == 6) {
                //무음모드
                triggerName = "Silence";
                addTolist();
            } else if (position == 7) {
                //데이터네트워크 켜짐
                triggerName = "DataOn";
                addTolist();
            } else if (position == 8) {
                //데이터네트워크 꺼짐
                triggerName = "DataOff";
                addTolist();
            } else if (position == 9) {
                //블루투스 켜짐
                triggerName = "BluetoothOn";
                addTolist();
            } else if (position == 10) {
                //블루투스 꺼짐
                triggerName = "BluetoothOff";
                addTolist();
            } else if (position == 11) {
                //비행기모드 바꼈을때
                triggerName = "AirplaneModeOn";
                addTolist();
            } else if (position == 12) {
                //비행기모드 바꼈을때
                triggerName = "AirplaneModeOff";
                addTolist();
            } else if (position == 13) {
                //전화 발신
                triggerName = "CallEnded";
                addTolist();
            } else if (position == 14) {
                Intent intentPhone = new Intent(getActivity(), TriggerForPhoneReception.class);
                startActivityForResult(intentPhone, 0);
                triggerName = "CallReception";
            } else if (position == 15) {
                //문자 수신시
                Intent intentSMS = new Intent(getActivity(), TriggerForSMS.class);
                startActivityForResult(intentSMS, 0);
                triggerName = "SMSreceiver";
            } else if (position == 16) {
                //전원 연결시
                triggerName = "PowerConnected";
                addTolist();
            } else if (position == 17) {
                //전원 해제시
                triggerName = "PowerDisConnected";
                addTolist();
            } else if (position == 18) {
                //이어폰 연결시
                triggerName = "EarphoneIn";
                addTolist();
            } else if (position == 19) {
                //이어폰 해제시
                triggerName = "EarphoneOut";
                addTolist();
            } else if (position == 20) {
                Intent intentBattery = new Intent(getActivity(), TriggerForBattery.class);
                intentBattery.putExtra("Battery", "Low");
                startActivityForResult(intentBattery, 0);
                triggerName = "LowBattery";

            } else if (position == 21) {
                Intent intentBattery = new Intent(getActivity(), TriggerForBattery.class);
                intentBattery.putExtra("Battery", "Full");
                startActivityForResult(intentBattery, 0);
                triggerName = "FullBattery";
            } else if (position == 22) {
                triggerName = "UpsideDown";
                addTolist();
            } else if (position == 23) {
                Intent intentS = new Intent(getActivity(), IntroShakeLR.class);
                startActivity(intentS);
                triggerName = "SensorLR";
                addTolist();
            } else if (position == 24) {
                Intent intentS = new Intent(getActivity(), IntroShakeUPDOWN.class);
                startActivity(intentS);
                triggerName = "SensorUPDOWN";
                addTolist();
            } else if (position == 25) {
                triggerName = "SensorBright";
                addTolist();
            } else if (position == 26) {
                Intent intentS = new Intent(getActivity(), IntroClose.class);
                startActivity(intentS);
                triggerName = "SensorClose";
                addTolist();
            } else if (position == 27) {
                Intent intentMap = new Intent(getActivity(), TriggerForMap.class);
                startActivityForResult(intentMap, 0);
                triggerName = "Location";
            } else if (position == 28) {
                Intent intentTime = new Intent(getActivity(), TriggerForAlarm.class);
                startActivityForResult(intentTime, 0);
                triggerName = "Time";
            }


        }
    };

    private void addTolist(){
        NewSimplePlan newSimplePlan = (NewSimplePlan) getActivity();
        newSimplePlan.listTrigger.clear();
        newSimplePlan.listTrigger.add(new Keyword(triggerName, triggerInfo));
        newSimplePlan.mViewPager.setCurrentItem(1);

        newSimplePlan.triggerText.setText(ChangingName.Trigger(triggerName));
        triggerName = "";
        triggerInfo = "";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            triggerInfo = data.getStringExtra("mTriggerInfo");

            if(data.getAction().toString().equals("Time")) {
                Log.d(MainActivity.TAG, "Time in onActivityResult");
                Bundle extra = data.getExtras();

                boolean[] week = extra.getBooleanArray("mTriggerInfo_week");
                Long triggerTime= data.getLongExtra("mTriggerInfo_time", 0);

                for(int i =1; i<week.length ; i++)
                    triggerInfo += week[i]+".";

                triggerInfo += "+"+ triggerTime+"+";
                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger33 " + triggerInfo);
            } else if(data.getAction().toString().equals("BrightUp")) {

                Log.d(MainActivity.TAG, " BrightUp ;" + triggerInfo);
            } else if(data.getAction().toString().equals("BrightDown")) {

                Log.d(MainActivity.TAG, "BrightDown " + triggerInfo);
            } else if(data.getAction().toString().equals("SMSreceiver")) {

                Log.d(MainActivity.TAG, "SMSreceiver " + triggerInfo);
            } else if(data.getAction().toString().equals("PhoneReception")) {

                Log.d(MainActivity.TAG, "CallReception " + triggerInfo);
            } else if(data.getAction().toString().equals("LowBattery")) {

                Log.d(MainActivity.TAG, "LowBattery " + triggerInfo);
            } else if(data.getAction().toString().equals("FullBattery")) {

                Log.d(MainActivity.TAG, "FullBattery " + triggerInfo);
            }else if (data.getAction().toString().equals("Map")) {
                Log.d(MainActivity.TAG, "Map " + triggerInfo);
            }
            addTolist();
        }
    }


}
