package com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.dahae.myandroiice.Adapter.ContactList;
import com.example.dahae.myandroiice.Adapter.ExpandableAdapter;
import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.TreeListAdapter;
import com.example.dahae.myandroiice.Information.trigger.IntroClose;
import com.example.dahae.myandroiice.Information.trigger.IntroShakeLR;
import com.example.dahae.myandroiice.Information.trigger.IntroShakeUPDOWN;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.ChangingName;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.TriggerForBattery;
import com.example.dahae.myandroiice.Triggers.TriggerForMap;
import com.example.dahae.myandroiice.Triggers.TriggerForAlarm;

import java.util.ArrayList;
import java.util.List;

public class ComplexTrigger extends Fragment {

    Button buttonOr;
    Button buttonAnd;
    Button buttonDone;
    Button buttonTriggerEnd;
    ImageButton buttonSpace;

    String triggerName = "";
    String triggerInfo = "";

    ExpandableListView expandableTriggerListView;

    ListView treeListView;
    TreeListAdapter treeListAdapter;

    private List<String> triggerGroupList = null;
    private List<List<String>> triggerChildList = null;
    private List<String> triggerChildListContentOnOff = null;
    private List<String> triggerChildListContentRing = null;
    private List<String> triggerChildListContentConection = null;
    private List<String> triggerChildListContentEmpty = null;
    private List<String> triggerChildListContentBattery  = null;
    private List<String> triggerChildListContentShake  = null;

    String arr1[] = { "Wi-Fi", "화면", "데이터네트워크", "블루투스", "비행기모드", // 0 - 4
            "벨모드",  // 5
            "통화 종료시", "전화 수신시", "SMS 수신시", //  6 - 8
            "충전기", "이어폰",  // 9 - 10
            "베터리", //  11
            "흔들기", //12
            "폰 뒤집기","밝기 센서","근접 센서", // 13 - 15
            "장소", "시간"// 16 - 17
    };

    String arrOnOff[] = { "켜졌을때", "꺼졌을때"};
    String arrSubRing[] = { "소리모드", "진동모드", "무음모드"};
    String arrSubConection[] = { "연결 시", "해제 시"};
    String arrBattery[] = { "N% 이상", "N%이하"};
    String arrShake[] = { "왼쪽 오른쪽", "위아래"};

    EditText number;
    public static final int SUCCESS = 1;
    public static final String SELECTED_PHONE = "selectedphone";
    public static final String SELECTED_NAME = "selectedname";

    public static ComplexTrigger init(int pageNumber) {
        ComplexTrigger fragment = new ComplexTrigger();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.new_complex_plan_trigger, null);


        treeListView = (ListView) layoutView.findViewById(R.id.tree_list);
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, new NewComplexPlan().triggerTree);

        expandableTriggerListView = (ExpandableListView) layoutView.findViewById(R.id.expandableListView);
        triggerGroupList = new ArrayList<String>();
        triggerChildList = new ArrayList<List<String>>();
        triggerChildListContentOnOff = new ArrayList<String>();
        triggerChildListContentRing = new ArrayList<String>();
        triggerChildListContentConection = new ArrayList<String>();
        triggerChildListContentBattery = new ArrayList<String>();
        triggerChildListContentShake = new ArrayList<String>();
        triggerChildListContentEmpty = new ArrayList<String>();

        setGroupList();

        expandableTriggerListView.setAdapter(new ExpandableAdapter(getActivity(), triggerGroupList, triggerChildList));
        expandableTriggerListView.setOnGroupClickListener(mTriggerGroupClick);
        expandableTriggerListView.setOnChildClickListener(mTriggerChildClick);

        buttonOr = (Button) layoutView.findViewById(R.id.buttonOr);
        buttonAnd = (Button) layoutView.findViewById(R.id.buttonAnd);
        buttonDone = (Button) layoutView.findViewById(R.id.buttonDone);
        buttonTriggerEnd = (Button) layoutView.findViewById(R.id.buttonTriggerEnd);
        buttonSpace = (ImageButton) layoutView.findViewById(R.id.buttonSpace);

        buttonOr.setOnClickListener(l);
        buttonAnd.setOnClickListener(l);
        buttonDone.setOnClickListener(l);
        buttonTriggerEnd.setOnClickListener(l);
        buttonSpace.setOnClickListener(l);

        buttonOr.setOnTouchListener(butTouchListener);
        buttonAnd.setOnTouchListener(butTouchListener);
        buttonDone.setOnTouchListener(butTouchListener);
        buttonTriggerEnd.setOnTouchListener(butTouchListener);
        buttonSpace.setOnTouchListener(ImagebutTouchListener);
        return layoutView;
    }

    @Override
    public void onResume() {
        super.onResume();
        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, newComplexPlan.triggerTree);
        treeListView.setAdapter(treeListAdapter);
    }

    private void setGroupList(){

        for(int i = 0; i < arr1.length; i++)
            triggerGroupList.add(arr1[i]);


        for(int i = 0; i < arrOnOff.length; i++)
            triggerChildListContentOnOff.add(arrOnOff[i]);
        for(int i = 0; i < arrSubRing.length; i++)
            triggerChildListContentRing.add(arrSubRing[i]);
        for(int i = 0; i < arrSubConection.length; i++)
            triggerChildListContentConection.add(arrSubConection[i]);
        for(int i = 0; i < arrBattery.length; i++)
            triggerChildListContentBattery.add(arrBattery[i]);
        for(int i = 0; i < arrShake.length; i++)
            triggerChildListContentShake.add(arrShake[i]);

        for(int i = 0 ; i <= 4; i++ )
            triggerChildList.add(triggerChildListContentOnOff);
        triggerChildList.add(triggerChildListContentRing);//5
        for(int i = 6 ; i <= 8; i++ )
            triggerChildList.add(triggerChildListContentEmpty);
        for(int i = 9 ; i <= 10; i++ )
            triggerChildList.add(triggerChildListContentConection);
        triggerChildList.add(triggerChildListContentBattery); //11
        triggerChildList.add(triggerChildListContentShake); //12
        for(int i = 13 ; i < arr1.length; i++ )
            triggerChildList.add(triggerChildListContentEmpty);

    }

    private View.OnTouchListener butTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Button button = (Button) v;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                button.setBackgroundColor(Color.parseColor("#FFAAE2DF"));
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                button.setBackgroundColor(Color.parseColor("#FF82B9B6"));
            }
            return false;
        }
    };

    private View.OnTouchListener ImagebutTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageButton button = (ImageButton) v;
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                button.setBackgroundColor(Color.parseColor("#FFAAE2DF"));
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                button.setBackgroundColor(Color.parseColor("#FF82B9B6"));
            }
            return false;
        }
    };


    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()== R.id.buttonAnd){
                addToList("And");
            }
            else if(v.getId()== R.id.buttonOr){
                addToList("Or");
            }
            else if(v.getId()== R.id.buttonDone){
                addToList("Done");
            }
            else if(v.getId()== R.id.buttonTriggerEnd){
                NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
                double result = newComplexPlan.alertTrigger();
                if(result == 1)
                    newComplexPlan.mViewPager.setCurrentItem(1);
            }
            else if(v.getId() == R.id.buttonSpace){
                deleteTree();
            }
        }
    };



    private ExpandableListView.OnGroupClickListener mTriggerGroupClick = new ExpandableListView.OnGroupClickListener(){
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            if (groupPosition == 6) {
                triggerName = "CallEnded";
                addToList();
            } else if (groupPosition == 7) {
                triggerName = "CallReception";
                builder();
//                Intent intentPhone = new Intent(getActivity(), TriggerForPhoneReception.class);
//                startActivityForResult(intentPhone, 0);
            } else if (groupPosition == 8) {
                triggerName = "SMSreceiver";
                builder();
//                Intent intentSMS = new Intent(getActivity(), TriggerForSMS.class);
//                startActivityForResult(intentSMS, 0);
            } else if (groupPosition == 13) {
                triggerName = "UpsideDown";
                addToList();
            } else if (groupPosition == 14) {
                triggerName = "SensorBright";
                addToList();
            } else if (groupPosition == 15) {
                triggerName = "SensorClose";
                Intent intentS = new Intent(getActivity(), IntroClose.class);
                startActivity(intentS);
                addToList();
            } else if (groupPosition == 16) {
                triggerName = "Location";
                Intent intentMap = new Intent(getActivity(), TriggerForMap.class);
                startActivityForResult(intentMap, 0);
            } else if (groupPosition == 17) {
                triggerName = "Time";
                Intent intentTime = new Intent(getActivity(), TriggerForAlarm.class);
                startActivityForResult(intentTime, 0);
            }

            return false;
        }
    };

    private ExpandableListView.OnChildClickListener mTriggerChildClick = new ExpandableListView.OnChildClickListener(){
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            if(groupPosition == 0){
                if(childPosition == 0){
                    triggerName = "WifiOn";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "WifiOff";
                    addToList();
                }
            }else if(groupPosition == 1){
                if(childPosition == 0){
                    triggerName = "ScreenOn";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "ScreenOff";
                    addToList();
                }
            }else if(groupPosition == 2){
                if(childPosition == 0){
                    triggerName = "DataOn";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "DataOff";
                    addToList();
                }
            }else if(groupPosition == 3){
                if(childPosition == 0){
                    triggerName = "BluetoothOn";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "BluetoothOff";
                    addToList();
                }
            }else if(groupPosition == 4){
                if(childPosition == 0){
                    triggerName = "AirplaneModeOn";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "AirplaneModeOff";
                    addToList();
                }
            }else if(groupPosition == 5){
                if(childPosition == 0){
                    triggerName = "Sound";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "Vibration";
                    addToList();
                }else if(childPosition == 2){
                    triggerName = "Silence";
                    addToList();
                }
            }else if(groupPosition == 9){
                if(childPosition == 0){
                    triggerName = "PowerConnected";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "PowerDisConnected";
                    addToList();
                }
            }else if(groupPosition == 10){
                if(childPosition == 0){
                    triggerName = "EarphoneIn";
                    addToList();
                }else if(childPosition == 1){
                    triggerName = "EarphoneOut";
                    addToList();;
                }
            }else if(groupPosition == 11){
                if(childPosition == 0){
                    triggerName = "LowBattery";
                    Intent intentBattery = new Intent(getActivity(), TriggerForBattery.class);
                    intentBattery.putExtra("Battery", "Low");
                    startActivityForResult(intentBattery, 0);
                }else if(childPosition == 1){
                    triggerName = "FullBattery";
                    Intent intentBattery = new Intent(getActivity(), TriggerForBattery.class);
                    intentBattery.putExtra("Battery", "Full");
                    startActivityForResult(intentBattery, 0);
                }
            }else if (groupPosition == 12) {
                if (childPosition == 0) {
                    triggerName = "SensorLR";
                    Intent intentS = new Intent(getActivity(), IntroShakeLR.class);
                    startActivity(intentS);
                    addToList();
                } else if (childPosition == 1) {
                    triggerName = "SensorUPDOWN";
                    Intent intentS = new Intent(getActivity(), IntroShakeUPDOWN.class);
                    startActivity(intentS);
                    addToList();
                }
            }
            return false;
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        triggerInfo ="";
        if(resultCode == Activity.RESULT_OK){

            if(data.getAction().toString().equals("Time")) {
                Log.d(MainActivity.TAG, "Time in onActivityResult");
                Bundle extra = data.getExtras();

                boolean[] week = extra.getBooleanArray("mTriggerInfo_week");
                Long triggerTime= data.getLongExtra("mTriggerInfo_time", 0);

                for(int i =1; i<week.length ; i++)
                    triggerInfo += week[i]+".";

                triggerInfo += "+"+ triggerTime+"+";
                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger " + triggerInfo);
            } else if(data.getAction().toString().equals("BrightUp")) {

                Log.d(MainActivity.TAG,"BrightUp in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");
                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
            } else if(data.getAction().toString().equals("BrightDown")) {

                Log.d(MainActivity.TAG,"BrightDown in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");
                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
            } else if(data.getAction().toString().equals("LowBattery")) {

                Log.d(MainActivity.TAG, "LowBattery in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");
                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
            } else if(data.getAction().toString().equals("FullBattery")) {

                Log.d(MainActivity.TAG, "FullBattery in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");
                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
            }else if (data.getAction().toString().equals("Map")) {
                Log.d(MainActivity.TAG, "Map in Type");
                triggerInfo = data.getStringExtra("mTriggerInfo");

                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
            }



//            else if(data.getAction().toString().equals("SMSreceiver")) {
//
//                Log.d(MainActivity.TAG,"SMSreceiver");
//                triggerInfo = data.getStringExtra("mTriggerInfo");
//                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
//            } else if(data.getAction().toString().equals("PhoneReception")) {
//
//                Log.d(MainActivity.TAG,"PhoneReception");
//                triggerInfo = data.getStringExtra("mTriggerInfo");
//                Log.d(MainActivity.TAG, "triggerInfo in ComplexTrigger" + triggerInfo);
//            }
            addToList();
        }
        if (requestCode == 10001) {
            if (resultCode == SUCCESS) {
                number.setText(data.getStringExtra(SELECTED_PHONE));
            } else {
                number.setText("");
            }
            triggerInfo = number.getText().toString();
            addToList();
        }
    }

    void builder(){

        number = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(number);
        builder.setTitle("번호를 입력해주세요")
                .setNegativeButton("주소록", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showContactlist();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        triggerInfo = number.getText().toString();
                        addToList();
                    }
                }).create().show();
    }
    private void showContactlist() {
        Intent intent = new Intent(getActivity(),
                ContactList.class);

        startActivityForResult(intent, 10001);
    }

    private void addToList(){
        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        Log.d(MainActivity.TAG, triggerName + triggerInfo);
        newComplexPlan.listTrigger.add(new Keyword(triggerName, triggerInfo));
        addToTree(triggerName);
    }

    private void addToList(String keyword){
        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        newComplexPlan.listTrigger.add(new Keyword(keyword, ""));
        addToTree(keyword);
    }

    private void addToTree(String keyword){
        ChangingName ChangingName = new ChangingName();
        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        newComplexPlan.triggerTree.add(new Keyword(ChangingName.Trigger(keyword), triggerInfo));
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, newComplexPlan.triggerTree);
        treeListView.setAdapter(treeListAdapter);
    }

    private void deleteTree(){

        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        if(newComplexPlan.listTrigger.size() > 0) {
           newComplexPlan.listTrigger.remove(newComplexPlan.listTrigger.size() - 1);
           newComplexPlan.triggerTree.remove(newComplexPlan.triggerTree.size() - 1);
        }
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, newComplexPlan.triggerTree);
        treeListView.setAdapter(treeListAdapter);
    }

}
