package com.example.dahae.myandroiice.ExistingPlans;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.KeywordAdapter;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.ModifyPlan.ModifyAction;
import com.example.dahae.myandroiice.CheckGrammer.ModifyPalnChecking;
import com.example.dahae.myandroiice.ModifyPlan.ModifyTrigger;
import com.example.dahae.myandroiice.NewPlan.ChangingName;
import com.example.dahae.myandroiice.NewPlan.SavingPlan;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;
import java.util.List;

public class ModifyPlanActivity extends Activity {

    ListView TriggerListView;
    ListView ActionListView;
    KeywordAdapter TriggerKewordAdapter;
    KeywordAdapter actionKeywordAdapter;

    AlertDialog.Builder builder;
    Button ButSave;
    String PlaneName ="";

    final CharSequence[] items = {"수정하기", "삭제하기"};

    int DialogNum;

    TextView nameText;

    ChangingName ChangingName = new ChangingName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyplans);

        TriggerListView = (ListView) findViewById(R.id.triggerList);
        ActionListView = (ListView) findViewById(R.id.actionList);
        nameText = (TextView) findViewById(R.id.nameView);
        ButSave = (Button) findViewById(R.id.ButSave);

        Log.d(MainActivity.TAG, "hi modifyPlan");

        Intent intent = getIntent();
        if(intent != null) {
            PlaneName = intent.getStringExtra("listViewName");
            nameText.setText(PlaneName);
        }

        TriggerKewordAdapter = new KeywordAdapter(this);
        actionKeywordAdapter = new KeywordAdapter(this);

        setAdapter();

        TriggerListView.setOnItemClickListener(mTriggerItemClickListener);
        ActionListView.setOnItemClickListener(mActionItemClickListener);
        ButSave.setOnClickListener(OnSaveClickListener);

        builder = new AlertDialog.Builder(this);

    }

    public void setAdapter() {

        SubFuntion subFuntion = new SubFuntion();

        TriggerKewordAdapter = subFuntion.getTriggerItemForModify(TriggerKewordAdapter, PlaneName);
        actionKeywordAdapter = subFuntion.getActionItemForModify(actionKeywordAdapter, PlaneName);

        TriggerListView.setAdapter(TriggerKewordAdapter);
        ActionListView.setAdapter(actionKeywordAdapter);
    }

    private ListView.OnItemClickListener mTriggerItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final String triggerForChange = TriggerKewordAdapter.items.get(position).getKeyword();
            DialogNum = position;

            builder.setTitle("다음을 골라주세요").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        if (triggerForChange.equals("AND") || triggerForChange.equals("OR"))
                            Toast.makeText(getApplicationContext(), "and와 or의 수정을 추천하지 않습니다. ", Toast.LENGTH_SHORT).show(); //고치기
                        else
                            IntentForTrigger();

                    } else if (which == 1) {
                        if (triggerForChange.equals("AND") || triggerForChange.equals("OR")) {
                            Toast.makeText(getApplicationContext(), "and와 or의 삭제를 추천하지 않습니다. ", Toast.LENGTH_SHORT).show();
                        } else {
                            if(new ModifyPalnChecking().TriggerChecktoDelete(TriggerKewordAdapter, DialogNum)) {
                                TriggerKewordAdapter.deleteItem(DialogNum);
                                TriggerListView.setAdapter(TriggerKewordAdapter);
                                Toast.makeText(getApplicationContext(), ChangingName.Trigger(triggerForChange) +"를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "규칙에 위배됩니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            AlertDialog alert = builder.create();
            if (!triggerForChange.equals("AND") && !triggerForChange.equals("OR")  && !triggerForChange.equals("Done")) {
                alert.show();
            }
        }
    };

    private ListView.OnItemClickListener mActionItemClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            DialogNum = position;
            builder.setTitle("다음을 골라주세요").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        IntentForAction();
                    } else if (which == 1) {
                        if(actionKeywordAdapter.getCount() !=1 ) {
                            actionKeywordAdapter.deleteItem(DialogNum);
                            ActionListView.setAdapter(actionKeywordAdapter);
                        }else{
                            Toast.makeText(getApplicationContext(), "행동을 더이상 삭제하지 마세요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    private View.OnClickListener OnSaveClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {

            List<Keyword> listTrigger = new ArrayList<>();
            List<Keyword> listAction = new ArrayList<>();
            String triggerName = "";
            String triggerInfo = "";
            String actionName ="";
            String actionInfo ="";

            if(TriggerKewordAdapter.getCount() == 2){
                triggerName = TriggerKewordAdapter.items.get(0).getKeyword();
                triggerInfo = TriggerKewordAdapter.items.get(0).getKeywordInfo();
                listTrigger.add(new Keyword(ChangingName.TriggerToEglish(triggerName), triggerInfo));
            }

            for (int i = 0; i < TriggerKewordAdapter.getCount(); i++) {
                triggerName = TriggerKewordAdapter.items.get(i).getKeyword();
                triggerInfo = TriggerKewordAdapter.items.get(i).getKeywordInfo();
                if (triggerName.equals("OR"))
                    listTrigger.add(new Keyword("Or","emptyModify"));
                else if (triggerName.equals("AND"))
                    listTrigger.add(new Keyword("And","emptyModify"));
                else
                    listTrigger.add(new Keyword(ChangingName.TriggerToEglish(triggerName), triggerInfo));
            }

            for (int i = 0; i < actionKeywordAdapter.getCount(); i++) {
                actionName = ChangingName.ActionToEglish(actionKeywordAdapter.items.get(i).getKeyword()) ;
                actionInfo = actionKeywordAdapter.items.get(i).getKeywordInfo();
                listAction.add(new Keyword(actionName, actionInfo));
            }
            new SavingPlan(getApplicationContext()).saveModifyDatabase(PlaneName, listTrigger, listAction);
            Toast.makeText(getApplicationContext(), "잘 변경 되었습니다..", Toast.LENGTH_LONG).show();

            finish();
        }
    };


    public void IntentForTrigger(){
        Intent intentForModify = new Intent(getApplicationContext(), ModifyTrigger.class);
        //intentMap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intentForModify, 0);
    }

    public void IntentForAction(){
        Intent intentForModify = new Intent(getApplicationContext(), ModifyAction.class);
        //intentMap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intentForModify, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(data.getAction().equals("ModifyTrigger")) {

                String triggerName = data.getStringExtra("triggerName");
                String triggerInfo ="";

                if(!triggerName.equals("")) {

                    if(triggerName.equals("Time")) {
                        Log.d(MainActivity.TAG, "Time in onActivityResult");
                    } else if(triggerName.equals("BrightUp")) {
                        Log.d(MainActivity.TAG,"BrightUp in Type");
                    } else if(triggerName.equals("BrightDown")) {
                        Log.d(MainActivity.TAG,"BrightDown in Type");
                    } else if(triggerName.equals("SMSreceiver")) {
                        Log.d(MainActivity.TAG,"SMSreceiver");

                    } else if(triggerName.equals("PhoneReception")) {
                        Log.d(MainActivity.TAG,"PhoneReception");
                    } else if(triggerName.equals("LowBattery")) {
                        Log.d(MainActivity.TAG, "LowBattery in Type");
                    } else if(triggerName.equals("FullBattery")) {
                        Log.d(MainActivity.TAG, "FullBattery in Type");
                    }else if (triggerName.equals("Map")) {
                        Log.d(MainActivity.TAG, "Map in Type");
                    }

                    triggerInfo= data.getStringExtra("triggerInfo");
                    Log.d(MainActivity.TAG, "**triggerName " + triggerName);
                    Log.d(MainActivity.TAG, "**triggerInfo " +  triggerInfo);
                    Log.d(MainActivity.TAG, "**DialogNum in ComplexTrigger" + DialogNum);
                    TriggerKewordAdapter.items.set(DialogNum, new Keyword(ChangingName.Trigger(triggerName),triggerInfo));//trigger값 바꾸기
                    TriggerListView.setAdapter(TriggerKewordAdapter);
                }
            } else if(data.getAction().equals("ModifyAction")) {

                String actionName = data.getStringExtra("actionName");
                String actionInfo = data.getStringExtra("actionInfo");
                Log.d(MainActivity.TAG, "**actionName in ComplexTrigger" + actionName);
                Log.d(MainActivity.TAG, "**actionInfo in ComplexTrigger" + actionInfo);
                if(!actionName.equals("")) {
                    actionKeywordAdapter.items.set(DialogNum, new Keyword(ChangingName.Action(actionName),actionInfo)); //action값 바꾸기
                    ActionListView.setAdapter(actionKeywordAdapter);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

}
