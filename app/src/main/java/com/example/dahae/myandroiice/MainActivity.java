package com.example.dahae.myandroiice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dahae.myandroiice.ExistingPlans.ActivePlanActivity;
import com.example.dahae.myandroiice.ExistingPlans.SleepingPlanActivity;
import com.example.dahae.myandroiice.Information.menuOfInformation;
import com.example.dahae.myandroiice.MainFunction.DBHelper;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan.NewComplexPlan;
import com.example.dahae.myandroiice.NewPlan.NewPlanMenu;

public class MainActivity extends AppCompatActivity {

    public static String TAG ="[ANDROI-ICE]";

    public static DBHelper dbHelperForRecordTime;
    public static DBHelper dbHelperForPlan;
    public static SQLiteDatabase database;
    public static SQLiteDatabase databaseForRecordTime;


    Button activePlanBut;
    Button sleepingPlanBut;
    Button recordBut;
    Button howToBut;
    Button butNewPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase();

        activePlanBut = (Button) findViewById(R.id.ButtonComplex);
        sleepingPlanBut = (Button) findViewById(R.id.ButtonSimple);
        recordBut = (Button) findViewById(R.id.ButtonRecord);
        howToBut = (Button) findViewById(R.id.buttonHow);
        butNewPlan = (Button) findViewById(R.id.buttonNewplan);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

        Boolean chkOnlyOnetime = pref.getBoolean("IntroOnlyOnetime", false);

        if (!chkOnlyOnetime) {
            Intent onetimeIntent = new Intent(getApplicationContext(), IntroDatabase.class);
            startActivity(onetimeIntent);
        }
        recordBut.setText("실행된 기록 :)");
        recordBut.setTextSize(20);

        howToBut.setText("안비서란?");
        howToBut.setTextSize(20);

        setActivePlanNum();
        setSleepingPlanNum();

        butNewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intentPlusButton = new Intent(getApplicationContext(), NewPlanMenu.class);
                startActivity(new Intent(getApplicationContext(), NewComplexPlan.class));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setActivePlanNum();
        setSleepingPlanNum();
    }

    public void setActivePlanNum(){

        Cursor cursor = databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable WHERE activation = 'true'", null);
        int num = cursor.getCount();

        if(num  < 1)
            num = 0;

        activePlanBut.setText("활성 부탁" + "\n" + Integer.toString(num) + " 개");
        activePlanBut.setTextSize(20);
    }

    public void setSleepingPlanNum(){

        Cursor cursor = databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable WHERE activation = 'false'", null);
        int num = cursor.getCount();

        if(num  < 1)
            num = 0;

        sleepingPlanBut.setText("휴면 부탁" + "\n" +  Integer.toString(num)+ " 개");
        sleepingPlanBut.setTextSize(20);
    }

    public void createDatabase(){

        try {
            //플랜을 저장,수정하기 위한 데이터베이스
            dbHelperForPlan = new DBHelper(getApplicationContext(), "planInformation");
            database = dbHelperForPlan.getWritableDatabase();

            //플랜의 추가정보(활성화여부, 기록) 저장을 위한 데이터베이스
            dbHelperForRecordTime = new DBHelper(getApplicationContext(), "planList");
            databaseForRecordTime = dbHelperForRecordTime.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseForRecordTime.execSQL("CREATE TABLE if not exists ActivationInfoTable("
                + " _id integer primary KEY autoincrement,"
                + "planName text,"
                + "activation text"
                + ");");

        databaseForRecordTime.execSQL("CREATE TABLE if not exists RecordTimeTable("
                + " _id integer primary KEY autoincrement,"
                + "planName text,"
                + "recordTime text"
                + ");");

    }

    public void onButtonActivePlanClicked(View v){
        Intent intentActivPlans = new Intent(getApplicationContext(), ActivePlanActivity.class);
        startActivityForResult(intentActivPlans, 0);
    }

    public void onButtonSleepingPlanClicked(View v){
        Intent intentSleepingPlans = new Intent(getApplicationContext(), SleepingPlanActivity.class);
        startActivityForResult(intentSleepingPlans, 0);
    }
    public void onButtonRecordClicked(View v){
        Intent intentRecordPlans = new Intent(getApplicationContext(), RecordTimeActivity.class);
        startActivityForResult(intentRecordPlans, 0);
    }
    public void onButtonHowToClicked(View v){
        Intent introIntent = new Intent(getApplicationContext(), menuOfInformation.class);
        startActivity(introIntent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setActivePlanNum();
        setSleepingPlanNum();
    }
}