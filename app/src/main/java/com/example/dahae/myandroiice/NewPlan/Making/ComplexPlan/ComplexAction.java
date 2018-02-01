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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.dahae.myandroiice.Actions.ActionForActivation;
import com.example.dahae.myandroiice.Actions.ActionForCall;
import com.example.dahae.myandroiice.Actions.ActionForNotify;
import com.example.dahae.myandroiice.Actions.ActionForSMS;
import com.example.dahae.myandroiice.Actions.ActionForTextToVoice;
import com.example.dahae.myandroiice.Actions.ActionForVolume;
import com.example.dahae.myandroiice.Actions.AppInfoActivity;
import com.example.dahae.myandroiice.Adapter.ExpandableAdapter;
import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.TreeListAdapter;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.ChangingName;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;
import java.util.List;

public class ComplexAction extends Fragment {

    Button buttonActionEnd;
    ImageButton buttonSpace;

    String actionName = "";
    String actionInfo = "";

    ListView treeListView;
    TreeListAdapter treeListAdapter;

    ExpandableListView expandableActionList;

    private List<String> actionGroupList = null;
    private List<List<String>> actionChildList = null;
    private List<String> actionChildListContentOnOff = null;
    private List<String> actionChildListContentRing = null;
    private List<String> actionChildListContentActivation = null;
    private List<String> actionChildListContentVolume = null;
    private List<String> actionChildListContentEmpty = null;

String arr2[] = { "Wi-Fi", "데이터네트워크", "블루투스", //0-2^^
        "벨모드", //3
             "번호읽어주기", "문자메세지 읽어주기", // 4-5
             "텍스트읽어주기","카메라","녹음" , //6-8
        "후레쉬", //9
        "볼륨바꾸기", //10
            "전화걸기","메세지 보내기", //11-12
            "알림메세지","즐겨찾기","바탕화면가기", //13-15
        "안비서의 다른 부탁"//16
};

    String arrOnOff[] = { "켜기", "끄기"};
    String arrRing[] = { "소리모드", "진동모드", "무음모드"};
    String arrActivation[] = { "활성화시키기", "비활성화시키기"};
    String arrVolume[] = { "벨소리", "음악"};


    public static ComplexAction init(int val) {
        ComplexAction fragment = new ComplexAction();
        // Supply val number as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        fragment.setArguments(args);
        return fragment;
    }
    AlertDialog.Builder  builderConfig;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.new_complex_plan_action, null);

        builderConfig = new AlertDialog.Builder(getActivity());

        treeListView = (ListView) layoutView.findViewById(R.id.tree_list);
        buttonSpace = (ImageButton) layoutView.findViewById(R.id.buttonSpace);
        buttonActionEnd = (Button) layoutView.findViewById(R.id.buttonActionEnd);

        setGropList();

        expandableActionList = (ExpandableListView) layoutView.findViewById(R.id.expandableListView);
        expandableActionList.setAdapter(new ExpandableAdapter(getActivity(), actionGroupList, actionChildList ));
        expandableActionList.setOnGroupClickListener(mActionGroupClick);
        expandableActionList.setOnChildClickListener(mActionChildClik);

        buttonActionEnd.setOnClickListener(l);
        buttonSpace.setOnClickListener(l);
        buttonActionEnd.setOnTouchListener(butTouchListener);
        buttonSpace.setOnTouchListener(ImagebutTouchListener);

        return layoutView;
    }

    @Override
    public void onResume() {
        super.onResume();
        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, newComplexPlan.actionTree);
        treeListView.setAdapter(treeListAdapter);
    }

    private void setGropList(){
        actionGroupList = new ArrayList<String>();
        actionChildList = new ArrayList<List<String>>();
        actionChildListContentOnOff = new ArrayList<String>();
        actionChildListContentRing = new ArrayList<String>();
        actionChildListContentActivation  = new ArrayList<String>();
        actionChildListContentEmpty  = new ArrayList<String>();
        actionChildListContentVolume = new ArrayList<String>();

        for(int i = 0; i < arr2.length; i++)
            actionGroupList.add(arr2[i]);

        for(int i = 0; i < arrOnOff.length; i++)
            actionChildListContentOnOff.add(arrOnOff[i]);
        for(int i = 0; i < arrRing.length; i++)
            actionChildListContentRing.add(arrRing[i]);
        for(int i = 0; i < arrActivation.length; i++)
            actionChildListContentActivation.add(arrActivation[i]);
        for(int i = 0; i < arrVolume.length; i++)
            actionChildListContentVolume.add(arrVolume[i]);

        for(int i = 0; i <= 2; i++)
            actionChildList.add(actionChildListContentOnOff);
        actionChildList.add(actionChildListContentRing);//3
        for(int i = 4; i <= 8; i++)
            actionChildList.add(actionChildListContentEmpty);
        actionChildList.add(actionChildListContentOnOff); //9
        actionChildList.add(actionChildListContentVolume); //10
        for(int i = 11; i <= 15; i++)
            actionChildList.add(actionChildListContentEmpty);
        actionChildList.add(actionChildListContentActivation); //16
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
            if(v.getId()==R.id.buttonActionEnd){
                NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();

                double result = newComplexPlan.alertAction();
                if(result == 1)
                    newComplexPlan.mViewPager.setCurrentItem(2);
            } else if(v.getId() == R.id.buttonSpace){
                deleteTree();
            }
        }
    };

    private ExpandableListView.OnGroupClickListener mActionGroupClick = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

            if(groupPosition == 4){
                actionName = "TellPhoneNum";
                addTolist();
            }else if(groupPosition == 5){
                actionName = "TellSMS";
                addTolist();
            }else if(groupPosition == 6){
                actionName = "TextToVoice";
                Intent intent = new Intent(getActivity(), ActionForTextToVoice.class);
                startActivityForResult(intent, 0);
            }else if(groupPosition == 7){
                actionName = "Camera";
                addTolist();
            }else if(groupPosition == 8){
                actionName = "AudioRecorder";
                addTolist();
            }else if(groupPosition == 11){
                actionName = "Call";
                Intent intentCall = new Intent(getActivity(), ActionForCall.class);
                startActivityForResult(intentCall, 0);
            }else if(groupPosition == 12){
                actionName = "SendingSMS";
                Intent intentSMS = new Intent(getActivity(), ActionForSMS.class);
                startActivityForResult(intentSMS, 0);
            }else if(groupPosition == 13){
                actionName = "Notification";
                Intent intentNot = new Intent(getActivity(), ActionForNotify.class);
                startActivityForResult(intentNot, 0);
            }else if(groupPosition == 14){
                actionName = "Bookmark";
                Intent intent = new Intent(getActivity(), AppInfoActivity.class);
                startActivityForResult(intent, 0);
            }else if(groupPosition == 15){
                actionName = "HomeScreen";
                addTolist();
            }

            return false;
        }
    };

    private ExpandableListView.OnChildClickListener mActionChildClik = new ExpandableListView.OnChildClickListener(){
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            if(groupPosition == 0){
                if(childPosition == 0){
                    actionName = "WifiOn";
                    addTolist();
                }else if(childPosition == 1){
                    actionName = "WifiOff";
                    addTolist();
                }
            }else if(groupPosition == 1){
                if(childPosition == 0){
                    actionName = "DataOn";
                    addTolist();
                }else if(childPosition == 1){
                    actionName = "DataOff";
                    addTolist();
                }
            }else if(groupPosition == 2){
                if(childPosition == 0){
                    actionName = "BluetoothOn";
                    addTolist();
                }else if(childPosition == 1){
                    actionName = "BluetoothOff";
                    addTolist();
                }
            }else if(groupPosition == 3){
                if(childPosition == 0){
                    actionName = "Sound";
                    addTolist();
                }else if(childPosition == 1){
                    actionName = "Vibration";
                    addTolist();
                }else if(childPosition == 2){
                    actionName = "Silence";
                    addTolist();
                }
            }else if(groupPosition == 9){
                if(childPosition == 0){
                    actionName = "FlashOn";
                    addTolist();
                }else if(childPosition == 1){
                    actionName = "FlashOff";
                    addTolist();
                }
            }else if(groupPosition == 10){
                if(childPosition == 0){
                    Intent intent = new Intent(getActivity(), ActionForVolume.class);
                    intent.putExtra("type", "Ring");
                    startActivityForResult(intent, 0);
                    actionName = "VolumeRing";
                }else if(childPosition == 1){
                    Intent intent = new Intent(getActivity(), ActionForVolume.class);
                    intent.putExtra("type", "Music");
                    startActivityForResult(intent, 0);
                    actionName = "VolumeMusic";
                }
            }else if(groupPosition == 16){
                if(childPosition == 0){
                    actionName = "Plantrue";
                    Intent intentAct = new Intent(getActivity(), ActionForActivation.class);
                    startActivityForResult(intentAct, 0);
                }else if(childPosition == 1){
                    actionName = "Planfalse";
                    Intent intentAct = new Intent(getActivity(), ActionForActivation.class);
                    startActivityForResult(intentAct, 0);
                }
            }
            return false;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(data.getAction().toString().equals("Bookmark")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Bookmark " + actionInfo);
            }else if(data.getAction().toString().equals("Activation")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Activation ;" + actionInfo);
             }


            else if(data.getAction().toString().equals("Call")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Call ActionInfo  " + actionInfo);
            }else if(data.getAction().toString().equals("SMS")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "SMS ActionInfo  " + actionInfo);
            }else if(data.getAction().toString().equals("TextToVoice")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "TextToVoice " + actionInfo);
            }else if(data.getAction().toString().equals("Volume")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Volume " + actionInfo);
            }else if(data.getAction().toString().equals("Notification")) {
                actionInfo = data.getStringExtra("mActionInfo");
                Log.d(MainActivity.TAG, "Notification ;" + actionInfo);
            }
            addTolist();
        }

    }

    void builder(){

        builderConfig.setTitle("안비서가 들어줄수 없는 부탁입니다:(").setIcon(R.drawable.anbisa_head_sadness).setNeutralButton("도움말", onNeutralClickListener);
        builderConfig.setPositiveButton("확인", onPositivClickListener);
        builderConfig.setMessage("dd");

        AlertDialog alert = builderConfig.create();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = 20;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        alert.getWindow().setAttributes(params);
        alert.show();
    }

    DialogInterface.OnClickListener onNeutralClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };

    DialogInterface.OnClickListener onPositivClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            return ;
        }
    };


    private void addTolist(){
        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        newComplexPlan.listAction.add(new Keyword(actionName, actionInfo));

        ChangingName Chang = new ChangingName();
        newComplexPlan.actionTree.add(new Keyword(Chang.Action(actionName)));
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, newComplexPlan.actionTree);
        treeListView.setAdapter(treeListAdapter);
    }

    private void deleteTree(){

        NewComplexPlan newComplexPlan = (NewComplexPlan) getActivity();
        if(newComplexPlan.listAction.size() > 0) {
            Log.d(MainActivity.TAG, "listAction " + newComplexPlan.actionTree.size() +"/ " + newComplexPlan.listAction.size());
            newComplexPlan.listAction.remove(newComplexPlan.listAction.size()-1);
            newComplexPlan.actionTree.remove(newComplexPlan.actionTree.size()-1);
        }
        treeListAdapter = new TreeListAdapter(getActivity(), R.layout.item_tree, newComplexPlan.actionTree);
        treeListView.setAdapter(treeListAdapter);
    }

}