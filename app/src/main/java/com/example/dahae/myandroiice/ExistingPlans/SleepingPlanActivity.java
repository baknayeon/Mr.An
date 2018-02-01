package com.example.dahae.myandroiice.ExistingPlans;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dahae.myandroiice.Adapter.PlanAdapter;
import com.example.dahae.myandroiice.Adapter.PlanItem;
import com.example.dahae.myandroiice.Adapter.KeywordAdapter;
import com.example.dahae.myandroiice.CheckGrammer.NewPlanCheckAsSyntax;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;

public class SleepingPlanActivity  extends AppCompatActivity {

    ListView subPlanListViewForTrigger;
    ListView subPlanListViewForAction ;
    ListView wholePlanListView;
    ArrayList<PlanItem> planList = new ArrayList<PlanItem>();

    KeywordAdapter subAdapterForTrigger = new KeywordAdapter(getApplication());
    KeywordAdapter subAdapterForAction = new KeywordAdapter(getApplication());

    TextView planName;

    NewPlanCheckAsSyntax newPlanCheck = new NewPlanCheckAsSyntax();
    SubFuntion subFuntion = new SubFuntion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholeplans);

        planName = (TextView) findViewById(R.id.nameText);
        subPlanListViewForTrigger = (ListView) findViewById(R.id.subListFortrigger);
        subPlanListViewForAction =(ListView) findViewById(R.id.subListForaction);
        wholePlanListView = (ListView) findViewById(R.id.wholePlanList);

        displayListView();

        wholePlanListView.setOnItemClickListener(mItemClickListener);
        wholePlanListView.setOnItemLongClickListener(mItemLongClickListener);
    }


    public void displayListView(){

        planList = subFuntion.getdisplayListInFalse();
        PlanAdapter planAdapter = new PlanAdapter(this, planList);
        wholePlanListView.setAdapter(planAdapter);
    }

    private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            PlanItem planItem = (PlanItem) parent.getItemAtPosition(position);

            final String listViewName = planItem.getName();

            PopupMenu popup = new PopupMenu(SleepingPlanActivity.this, view);
            getMenuInflater().inflate(R.menu.menu_listview, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.modify:

                            Intent intentModify = new Intent(getApplicationContext(), ModifyPlanActivity.class);
                            intentModify.putExtra("listViewName", listViewName);
                            intentModify.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivityForResult(intentModify, 0);
                            break;

                        case R.id.delete:

                            subFuntion.deletePlan(listViewName);
                            displayListView();
                            planName.setText(null);
                            setSubAdapter(null); // sub 초기화
                            break;

                        case R.id.copy:

                            String oldName = listViewName;
                            String newName = listViewName.concat("_복사본");

                            if (newPlanCheck.NoSameName(newName) ==1 ) {
                                subFuntion.copyPlanAsFalse(oldName);
                                displayListView();
                                setSubAdapter(newName);
                                planName.setText(newName);
                            }
                            break;

                        case R.id.change:

                            AlertDialog.Builder alert = new AlertDialog.Builder(SleepingPlanActivity.this);

                            alert.setTitle("이름바꾸기");
                            alert.setMessage("새 이름을 입력해주세요");
                            final EditText input = new EditText(SleepingPlanActivity.this);
                            alert.setView(input);

                            alert.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String newName = input.getText().toString();
                                    int checkName = newPlanCheck.NoSameName(newName);

                                    if (checkName == 1) {
                                        subFuntion.changePlanName( listViewName, newName);
                                        displayListView();
                                    }else if (checkName == 0) {
                                        Toast.makeText(getApplicationContext(), "같은 이름의 부탁이 있습니다.", Toast.LENGTH_LONG);
                                    }
                                }
                            });

                            alert.setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) { }
                                    });

                            alert.show();
                            break;
                    }
                    return false;
                }
            });
            popup.show();
            return false;
        }
    };

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            PlanItem planItem = (PlanItem) parent.getItemAtPosition(position);
            final String listViewName = planItem.getName();
            setSubAdapter(listViewName);
            planName.setText(listViewName);
        }
    };

    public void setSubAdapter(String listViewName){

        subAdapterForAction = new KeywordAdapter(this);
        subAdapterForTrigger = new KeywordAdapter(this);

        subAdapterForTrigger = subFuntion.getSubItemOfTrigger(subAdapterForTrigger, listViewName);
        subAdapterForAction = subFuntion.getSubItemOfAction(subAdapterForAction, listViewName);

        subPlanListViewForTrigger.setAdapter(subAdapterForTrigger);
        subPlanListViewForAction.setAdapter(subAdapterForAction);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayListView();
    }

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
        int id = item.getItemId();
        Intent Mytrigger = new Intent(getApplicationContext(), MyTrigger.class);
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_start_service) {

            Log.d(MainActivity.TAG, "ReStart");
            startService(Mytrigger);
            return true;

        } else if (id == R.id.action_stop_service) {

            Log.d(MainActivity.TAG, "Stop");
            stopService(Mytrigger);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}