package com.example.dahae.myandroiice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.Records;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.NewPlan.NewPlanMenu;

import java.util.ArrayList;
import java.util.List;

public class RecordTimeActivity extends AppCompatActivity {

    Button button;
    ListView RecordListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_time);

        RecordListView  = (ListView) findViewById(R.id.recordTimeListView);
        button = (Button) findViewById(R.id.buttonNewplan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.databaseForRecordTime.execSQL("DELETE FROM RecordTimeTable;");
                Log.d(MainActivity.TAG, "Table \'RecordTimeTable\' Clean ");
                displayRecordListView();
            }
        });

        displayRecordListView();
    }

    public void displayRecordListView(){
        Log.d("setAdapter", "hi displayRecordListView()");

        ArrayList<Records> recordList = new ArrayList<Records>();

        Cursor cursorR = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM RecordTimeTable", null);
        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursorR != null && cursorR.getCount() != 0) {
                    while (cursorR.moveToNext()) {
                        String planName = cursorR.getString(1);
                        String time = cursorR.getString(2);
                        recordList.add(new Records(time, planName));
                    }
                }
            }
        } finally {
            cursorR.close();
        }

        RecordAdapter recordAdapter = new RecordAdapter(this, R.layout.item_plan_record, recordList);
        RecordListView.setAdapter(recordAdapter);
    }


    //최근 기록 보여주는 리스트뷰용 아답터
    private class RecordAdapter extends ArrayAdapter<Records> {
        ArrayList<Records> recordList;

        public RecordAdapter(Context context, int textViewResourceId, ArrayList<Records> recordList) {
            super(context, textViewResourceId, recordList);
            this.recordList = recordList;
        }

        private class ViewHolder {
            TextView time;
            TextView name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.item_plan_record, null);

                holder = new ViewHolder();
                holder.time = (TextView) convertView.findViewById(R.id.recordTimeTextView);
                holder.name = (TextView) convertView.findViewById(R.id.recordNameTextView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Records records = recordList.get(recordList.size() - position -1);
            holder.time.setText(records.getTime());
            holder.name.setText(records.getName());

            return convertView;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent Mytrigger = new Intent(getApplicationContext(), MyTrigger.class);

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
