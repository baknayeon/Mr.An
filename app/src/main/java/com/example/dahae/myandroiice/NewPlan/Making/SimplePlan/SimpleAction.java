package com.example.dahae.myandroiice.NewPlan.Making.SimplePlan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.util.Log;
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
import com.example.dahae.myandroiice.NewPlan.ChangingName;
import com.example.dahae.myandroiice.R;

public class SimpleAction extends Fragment {

    int fragNum;

    String actionName = "";
    String actionInfo = "empty";

    ListView listAction;
    KeywordAdapter keywordAdapter;
    ChangingName ChangingName = new ChangingName();

    String arr2[] = { "Wi-Fi 켜기", "Wi-Fi 끄기", "소리모드로 전환", "진동모드로 전환", "무음모드로 전환", // 5
            "데이터네트워크 켜기", "데이터네트워크 끄기", "블루투스 켜기", "블루투스 끄기", //6-9
            "번호읽어주기", "문자메세지 읽어주기", // 10-11
            "텍스트읽어주기","카메라","후레쉬 켜기","녹음" , //12-15
            "벨볼륨바꾸기","음악볼륨바꾸기", //16-17
            "전화걸기","메세지 보내기", //18-19
            "알림메세지(Notification)","즐겨찾기","바탕화면가기", //20-22
            "명령 활성화 시키기", "명령 비활성화 시키기" // 23-24`
    };

    public static SimpleAction init(int val) {
        SimpleAction fragment = new SimpleAction();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.new_simple_plan_action, null);


        listAction = (ListView) layoutView.findViewById(R.id.actionList);
        keywordAdapter = new KeywordAdapter(container.getContext());
        for(int i=0; i<arr2.length; i++)
            keywordAdapter.addItem(new Keyword(arr2[i]));
        listAction.setAdapter(keywordAdapter);

        listAction.setOnItemClickListener(mActionItemClickListener);
        return layoutView;
    }


    private AdapterView.OnItemClickListener mActionItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (position == 0) {
                //와이파이 켜기
                actionName = "WifiOn";
                addTolist();
            } else if (position == 1) {
                //와이파이 끄기
                actionName = "WifiOff";
                addTolist();
            } else if (position == 2) {
                //화면 켜기
                actionName = "Sound";
                addTolist();
            } else if (position == 3) {
                //화면 끄기
                actionName = "Vibration";
                addTolist();
            } else if (position == 4) {
                //소리모드로 전환
                actionName = "Silence";
                addTolist();
            } else if (position == 5) {
                //진동모드로 전환
                actionName = "DataOn";
                addTolist();
            } else if (position == 6) {
                //무음모드로 전환
                actionName = "DataOff";
                addTolist();
            } else if (position == 7) {
                //블루투스 켜기
                actionName = "BluetoothOn";
                addTolist();
            } else if (position == 8) {
                //블루투스 끄기
                actionName = "BluetoothOff";
                addTolist();
            } else if (position == 9) {
                actionName = "TellPhoneNum";
                addTolist();
            } else if (position == 10) {
                actionName = "TellSMS";
                addTolist();
            } else if (position == 11) {
                actionName = "TextToVoice";
                Intent intent = new Intent(getActivity(), ActionForTextToVoice.class);
                startActivityForResult(intent, 0);
            } else if (position == 12) {
                actionName = "Camera";
                addTolist();
            } else if (position == 13) {
                actionName = "FlashOn";
                addTolist();
            } else if (position == 14) {
                actionName = "AudioRecorder";
                addTolist();
            } else if (position == 15) {
                actionName = "VolumeRing";
                Intent intent = new Intent(getActivity(), ActionForVolume.class);
                intent.putExtra("type", "Ring");
                startActivityForResult(intent, 0);
            } else if (position == 16) {
                actionName = "VolumeMusic";
                Intent intent = new Intent(getActivity(), ActionForVolume.class);
                intent.putExtra("type", "Music");
                startActivityForResult(intent, 0);
            } else if (position == 17) {
                actionName = "Call";
                Intent intentCall = new Intent(getActivity(), ActionForCall.class);
                startActivityForResult(intentCall, 0);
            } else if (position == 18) {
                actionName = "SendingSMS";
                Intent intentSMS = new Intent(getActivity(), ActionForSMS.class);
                startActivityForResult(intentSMS, 0);
            } else if (position == 19) {
                actionName = "Notification";
                Intent intentNot = new Intent(getActivity(), ActionForNotify.class);
                startActivityForResult(intentNot, 0);
            } else if (position == 20) {
                actionName = "Bookmark";
                Intent intent = new Intent(getActivity(), AppInfoActivity.class);
                startActivityForResult(intent, 0);
            } else if (position == 21) {
                actionName = "HomeScreen";
                addTolist();
            } else if (position == 22) {
                actionName = "Plantrue";
                Intent intentAct = new Intent(getActivity(), ActionForActivation.class);
                startActivityForResult(intentAct, 0);
            } else if (position == 23) {
                actionName = "Planfalse";
                Intent intentAct = new Intent(getActivity(), ActionForActivation.class);
                startActivityForResult(intentAct, 0);
            }


        }
    };

    private void addTolist(){
        NewSimplePlan newSimplePlan = (NewSimplePlan) getActivity();
        newSimplePlan.listAction.clear();
        newSimplePlan.listAction.add(new Keyword(actionName, actionInfo));
        newSimplePlan.mViewPager.setCurrentItem(2);

        newSimplePlan.actionText.setText(ChangingName.Action(actionName));
        actionName = "";
        actionInfo = "";
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            actionInfo = data.getStringExtra("mActionInfo");
            if(data.getAction().toString().equals("Call")) {
                Log.d(MainActivity.TAG, "Call ActionInfo  " + actionInfo);
            }else if(data.getAction().toString().equals("SMS")) {
                Log.d(MainActivity.TAG, "SMS ActionInfo  " + actionInfo);
            }else if(data.getAction().toString().equals("TextToVoice")) {
                Log.d(MainActivity.TAG, "TextToVoice " + actionInfo);
            }else if(data.getAction().toString().equals("Volume")) {
                Log.d(MainActivity.TAG, "Volume " + actionInfo);
            }else if(data.getAction().toString().equals("Bookmark")) {
                Log.d(MainActivity.TAG, "Bookmark " + actionInfo);
            }else if(data.getAction().toString().equals("Notification")) {
                Log.d(MainActivity.TAG, "Notification ;" + actionInfo);
            }else if(data.getAction().toString().equals("Activation")) {
                Log.d(MainActivity.TAG, "Activation ;" + actionInfo);
             }
            addTolist();
        }
    }
}