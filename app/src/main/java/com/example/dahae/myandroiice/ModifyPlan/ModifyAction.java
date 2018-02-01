package com.example.dahae.myandroiice.ModifyPlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dahae.myandroiice.Actions.ActionForActivation;
import com.example.dahae.myandroiice.Actions.ActionForCall;
import com.example.dahae.myandroiice.Actions.ActionForNotify;
import com.example.dahae.myandroiice.Actions.ActionForSMS;
import com.example.dahae.myandroiice.Actions.ActionForTextToVoice;
import com.example.dahae.myandroiice.Actions.ActionForVolume;
import com.example.dahae.myandroiice.Actions.AppInfoActivity;
import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.KeywordAdapter;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

public class ModifyAction extends AppCompatActivity { //http://apphappy.tistory.com/10 참조!

    String actionName = "";
    String actionInfo = "";

    String arr2[] = { "Wi-Fi 켜기", "Wi-Fi 끄기", "소리모드로 전환", "진동모드로 전환", "무음모드로 전환", // 5
            "데이터네트워크 켜기", "데이터네트워크 끄기", "블루투스 켜기", "블루투스 끄기", //6-9
            "번호읽어주기", "문자메세지 읽어주기", // 10-11
            "텍스트읽어주기","카메라","후레쉬","녹음" , //12-15
            "벨볼륨바꾸기","음악볼륨바꾸기", //16-17
            "전화걸기","메세지 보내기", //18-19
            "알림메세지(Notification)","즐겨찾기","바탕화면가기", //20-22
            "명령 활성화 시키기", "명령 비활성화 시키기" // 23-24`
    };

    ListView actionKeyword;
    /**
     * Retrieving this instance's number from its arguments.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plans_list);
        actionKeyword = (ListView) findViewById(R.id.planList);

        setActionKeyword();

        actionKeyword.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * 액션 설정
                 */
                if (position == 0) {
                    //와이파이 켜기
                    actionName = "WifiOn";
                    intent();
                } else if (position == 1) {
                    //와이파이 끄기
                    actionName = "WifiOff";
                    intent();
                } else if (position == 2) {
                    //화면 켜기
                    actionName = "Sound";
                    intent();
                } else if (position == 3) {
                    //화면 끄기
                    actionName = "Vibration";
                    intent();
                } else if (position == 4) {
                    //소리모드로 전환
                    actionName = "Silence";
                    intent();
                } else if (position == 5) {
                    //진동모드로 전환
                    actionName = "DataOn";
                    intent();
                } else if (position == 6) {
                    //무음모드로 전환
                    actionName = "DataOff";
                    intent();
                } else if (position == 7) {
                    //블루투스 켜기
                    actionName = "BluetoothOn";
                    intent();
                } else if (position == 8) {
                    //블루투스 끄기
                    actionName = "BluetoothOff";
                    intent();
                } else if (position == 9) {
                    actionName = "TellPhoneNum";
                    intent();
                } else if (position == 10) {
                    actionName = "TellSMS";
                    intent();
                } else if (position == 11) {
                    actionName = "TextToVoice";
                    Intent intent = new Intent(getApplicationContext(), ActionForTextToVoice.class);
                    startActivityForResult(intent, 0);
                } else if (position == 12) {
                    actionName = "Camera";
                    intent();
                } else if (position == 13) {
                    actionName = "FlashOn";
                    intent();
                } else if (position == 14) {
                    actionName = "AudioRecorder";
                    intent();
                } else if (position == 15) {
                    actionName = "VolumeRing";
                    Intent intent = new Intent(getApplicationContext(), ActionForVolume.class);
                    intent.putExtra("type", "Ring");
                    startActivityForResult(intent, 0);
                } else if (position == 16) {
                    actionName = "VolumeMusic";
                    Intent intent = new Intent(getApplicationContext(), ActionForVolume.class);
                    intent.putExtra("type", "Music");
                    startActivityForResult(intent, 0);
                } else if (position == 17) {
                    actionName = "Call";
                    Intent intentCall = new Intent(getApplicationContext(), ActionForCall.class);
                    startActivityForResult(intentCall, 0);
                } else if (position == 18) {
                    actionName = "SendingSMS";
                    Intent intentSMS = new Intent(getApplicationContext(), ActionForSMS.class);
                    startActivityForResult(intentSMS, 0);
                } else if (position == 19) {
                    actionName = "Notification";
                    Intent intentNot = new Intent(getApplicationContext(), ActionForNotify.class);
                    startActivityForResult(intentNot, 0);
                } else if (position == 20) {
                    actionName = "Bookmark";
                    Intent intent = new Intent(getApplicationContext(), AppInfoActivity.class);
                    startActivityForResult(intent, 0);
                } else if (position == 21) {
                    actionName = "HomeScreen";
                    intent();
                } else if (position == 22) {
                    actionName = "Plantrue";
                    Intent intentAct = new Intent(getApplicationContext(), ActionForActivation.class);
                    startActivityForResult(intentAct, 0);
                } else if (position == 23) {
                    actionName = "Planfalse";
                    Intent intentAct = new Intent(getApplicationContext(), ActionForActivation.class);
                    startActivityForResult(intentAct, 0);
                }

            }


        });
    }

    private void setActionKeyword(){
        KeywordAdapter planAdapter = new KeywordAdapter(this);

        for(int i = 0 ; i < arr2.length; i++) {
            planAdapter.addItem(new Keyword(arr2[i]));//고쳐
        }
        actionKeyword.setAdapter(planAdapter);
    }

    public void intent(){

        Intent intent = getIntent();
        intent.putExtra("actionName", actionName);
        intent.putExtra("actionInfo", actionInfo);

        intent.setAction("ModifyAction");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(data.getAction().toString().equals("Call")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Call ActionInfo  " +actionInfo);
            }else if(data.getAction().toString().equals("SMS")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "SMS ActionInfo  " + actionInfo);
            }else if(data.getAction().toString().equals("TextToVoice")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "TextToVoice " + actionInfo);
            }else if(data.getAction().toString().equals("Volume")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Volume " + actionInfo);
            }else if(data.getAction().toString().equals("Bookmark")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Bookmark " + actionInfo);
            }else if(data.getAction().toString().equals("Notification")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Notification ;" + actionInfo);
            }else if(data.getAction().toString().equals("Activation")) {
                actionInfo= data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Activation ;" + actionInfo);
            }
        }
        Log.d(MainActivity.TAG, "onActivityResult In Action ; " + actionInfo);
        intent();
    }

}