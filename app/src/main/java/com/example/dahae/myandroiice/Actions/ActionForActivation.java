package com.example.dahae.myandroiice.Actions;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.PlanAdapter;
import com.example.dahae.myandroiice.Adapter.PlanItem;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

import java.util.ArrayList;

public class ActionForActivation extends ActionBarActivity {

    ListView wholePlanListView;
    PlanAdapter planAdapter;
    ArrayList<PlanItem> planList = new ArrayList<PlanItem>();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_activation);

        wholePlanListView = (ListView) findViewById(R.id.wholePlanList);
        textView = (TextView) findViewById(R.id.textViewSyntaxAction);

        displayListView();

        wholePlanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String planName = planAdapter.getItem(position).getName();
                textView.setText(planName);
            }
        });
    }

    public void onButton1Clicked(View v) {
        // 입력상자에 입력한 전화번호를 가져옴
        String planName = textView.getText().toString();

        Log.d(MainActivity.TAG, "planName " + planName);

        Intent intent = getIntent();
        intent.setAction("Activation");
        intent.putExtra("mActionInfo", planName);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void displayListView(){

        //메인List(보유 플랜 목록)한 뷰 띄우기
        planList.clear();

        PlanItem planItem;
        Cursor cursor = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);
        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String planName = cursor.getString(1);
                        String activationState = cursor.getString(2);
                        Log.d(MainActivity.TAG, "planName: " + planName + " / activationState: " + activationState);
                        if(activationState.equals("true")){
                            planItem = new PlanItem(planName, planName, true);
                        } else {
                            planItem = new PlanItem(planName, planName, false);
                        }
                        planList.add(planItem);
                    }
                }
            }
        } finally {
            cursor.close();
        }

        planAdapter = new PlanAdapter(this, planList);
        wholePlanListView.setAdapter(planAdapter);
    }

}