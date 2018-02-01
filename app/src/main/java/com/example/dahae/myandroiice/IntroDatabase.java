package com.example.dahae.myandroiice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.example.dahae.myandroiice.MainFunction.MyTrigger;

import java.util.ArrayList;
import java.util.List;

public class IntroDatabase  extends Activity {

    CheckBox CheckBox1, CheckBox2, CheckBox3, CheckBox4;
    CheckBox CheckBox5, CheckBox6, CheckBox7, CheckBox8;
    ImageButton Button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informain4);

        CheckBox1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox4 = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox5 = (CheckBox) findViewById(R.id.checkBox5);
        CheckBox6 = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox7 = (CheckBox) findViewById(R.id.checkBox7);
        CheckBox8 = (CheckBox) findViewById(R.id.checkBox8);
        Button = (ImageButton) findViewById(R.id.imageButton);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> mlistTrigger = new ArrayList<>();
                String mActionName = "";
                String tableName = "";
                String mActionInfo = "";
                String mTriggerInfo = "";

                if (CheckBox1.isChecked()) {
                    {
                        mlistTrigger.add("EarphoneIn/");
                        mActionName = "Bookmark/";
                        mActionInfo = "com.sec.android.app.music";
                        tableName = "이어폰꽂으면음악어플켜기";
                       // new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);
                    }
                    {
                        mlistTrigger.add("EarphoneIn/");
                        mTriggerInfo = "empty";
                        mActionName = "VolumeMusic/";
                        mActionInfo = "7";
                        tableName = "이어폰꽂으면음악볼륨7";
                     //   new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);
                    }
                    {
                        mlistTrigger.add("EarphoneOut/");
                        mActionName = "VolumeMusic/";
                        mActionInfo = "15";
                        tableName = "이어폰뽑으면음악볼륨최대";
                     //   new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);
                    }
                }
                if (CheckBox2.isChecked()) {

                    mlistTrigger.add("SMSreceiver/");
                    mTriggerInfo = "01049479094";
                    mActionName = "TellPhoneNum/";
                    mActionInfo = "TellPhoneNumInfo/";
                    tableName = "수신번호읽어주기";
                   // new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);

                    mlistTrigger.add("SMSreceiver/");
                    mTriggerInfo = "01049479094";
                    mActionName = "TellSMS/";
                    mActionInfo = "TellSMSInfo/";
                    tableName = "문자내용읽어주기";
                   // new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);

                }
                if (CheckBox3.isChecked()) {

                }
                if (CheckBox4.isChecked()) {

                }
                if (CheckBox5.isChecked()) {
                }
                if (CheckBox6.isChecked()) {
                }
                if (CheckBox7.isChecked()) {

                    mlistTrigger.add("LowBattery/");
                    mTriggerInfo = "35";
                    mActionName = "DataOff/WifiOn/";
                    mActionInfo = "";
                    tableName = "배터리적으면데이터끄기";
                 //   new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);

                    mlistTrigger.add("PowerDisConnected/");
                    mTriggerInfo = "empty";
                    mActionName = "WifiOn/DataOff/ ";
                    mActionInfo = "";
                    tableName = "충전해제시데이터끄기";
                  //  new SavingPlan(getApplicationContext()).saveNewDatabase(tableName, mlistTrigger, mActionName, mActionInfo, mTriggerInfo);
                }
                IntentFomain();
            }
        });
    }


    public void IntentFomain(){

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("IntroOnlyOnetime", true);
        editor.commit();
        IntroDatabase.this.finish();
    }
}
