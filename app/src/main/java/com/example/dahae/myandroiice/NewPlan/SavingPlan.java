package com.example.dahae.myandroiice.NewPlan;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.Triggers.AlarmReceive;
import com.example.dahae.myandroiice.Triggers.MapReceive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by b_newyork on 2016-01-08.
 */
public class SavingPlan {
    LocationManager mLocationManager;
    MapReceive mIntentReceiver;
    String intentKey = "locationProximity";
    ArrayList mPendingIntentList;

    Context getApplicationContext;

    public SavingPlan(Context context) {

        this.getApplicationContext = context;
    }

    public void saveNewDatabase(String tableName, List<Keyword> listTrigger, List<Keyword> listAction) {

        if (!tableName.equals("")) {

            makeNewTable(tableName);
            saveTrigger(tableName, listTrigger);
            saveAction(tableName, listAction);
            saveAsActive(tableName);

            Log.d(MainActivity.TAG, "**Save done");
        }
    }

    public void saveModifyDatabase(String tableName,  List<Keyword> listTrigger, List<Keyword> listAction) {

        if (!tableName.equals("")) {

            UseWhenPlanModify(tableName);
            makeNewTable(tableName);
            saveTrigger(tableName, listTrigger);
            saveAction(tableName, listAction);
            saveAsActive(tableName);

        }
    }

    private void makeNewTable(String tableName) {

        if (MainActivity.database != null) {
            MainActivity.database.execSQL("CREATE TABLE if not exists " + tableName + "("
                    + "_id integer primary KEY autoincrement,"
                    + " Keyword text,"
                    + " Keyword_info text,"
                    + " Keyword_level integer"
                    + ");");
            Log.d(MainActivity.TAG, "Table " + tableName + " created");
        }
    }

    private void saveTrigger(String tableName, List<Keyword> TriggerList) {

        int level_id = 0;
        Iterator<Keyword> iterator = TriggerList.iterator();
        Keyword keyword;

        while (iterator.hasNext()) {

            keyword = iterator.next();
            String trigger = keyword.getKeyword();
            String triggerInfo = keyword.getKeywordInfo();

            if (trigger.contains("And")) {
                if (MainActivity.database != null) {
                    MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) values "
                            + "('And', " + level_id + ");");
                } else {
                    Log.e(MainActivity.TAG, "first, you should open the table ");
                }
                level_id++;
            } else if (trigger.contains("Or")) {
                if (MainActivity.database != null) {
                    MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) VALUES "
                            + "('Or', " + level_id + ");");
                } else {
                    Log.e(MainActivity.TAG, "first, you should open the table ");
                }
                level_id++;
            } else if (trigger.contains("Done")) {
                MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) values "
                        + "('Done', " + level_id + ");");
                level_id--;
            }else {

                if (trigger.equals("Time") || trigger.equals("BrightDown") ||
                        trigger.equals("BrightUp") || trigger.equals("PhoneReception") ||
                        trigger.equals("LowBattery") || trigger.equals("FullBattery")
                        || trigger.equals("Location")) {
                    Log.d(MainActivity.TAG, "triggerInfo  " + triggerInfo);

                    if (triggerInfo.equals("Time")) {
                        TriggerOfTime(triggerInfo);
                    } else if (triggerInfo.equals("Location")) {
//                                TriggerOfLoaction(triggerInfo);

                    }
                }
                if (MainActivity.database != null)
                        MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level, Keyword_Info) VALUES "
                                + "('" + trigger + "', " + level_id + ", '" + triggerInfo + "');");

            }
        }

        if (MainActivity.database != null)
            MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level) VALUES "
                    + "('End', 0);");
    }


    private void UseWhenPlanModify(String tableName) {

        if(MainActivity.database!=null)
                MainActivity.database.execSQL("DROP TABLE IF EXISTS "+tableName);

        if(MainActivity.databaseForRecordTime!=null)
                MainActivity.databaseForRecordTime.execSQL("DELETE FROM ActivationInfoTable where planName = '"+tableName+"';");

    }

    private void saveAction(String tableName, List<Keyword> listAction){

        Iterator<Keyword> iterator = listAction.iterator();
        Keyword keyword;


       while(iterator.hasNext()) {

           keyword = iterator.next();
           String action = keyword.getKeyword();
           String actionInfo = keyword.getKeywordInfo();

           //액션 저장
           try {
               if (MainActivity.database != null)
                   MainActivity.database.execSQL("INSERT INTO " + tableName + "(Keyword, Keyword_level, Keyword_Info) VALUES "
                           + "('" + action + "', -1, '" + actionInfo + "');");

           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }

    private void saveAsActive(String tableName){

        //액션 저장
        try {
            if(MainActivity.databaseForRecordTime != null) {
                MainActivity.databaseForRecordTime.execSQL("INSERT INTO ActivationInfoTable(planName, activation) VALUES "
                        + "('" + tableName + "', 'true');");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    private void TriggerOfTime(String TriggerInfo){
//        StringTokenizer stFortime = new StringTokenizer(TriggerInfo, "+");
//        boolean[] week = new boolean[7];
//        StringTokenizer stWeek = new StringTokenizer(stFortime.nextToken(), ".");
//
//        for (int i = 0; i < 7; i++) {
//            String Week = stWeek.nextToken();
//            //    Log.d(TAG,"Week " + Week);
//            if (Week.equals("true"))
//                week[i] = true;
//            else
//                week[i] = false;
//        }
//        Long triggerTime = Long.valueOf(stFortime.nextToken()).longValue();
//        alramSet(week, triggerTime);
//    }
//
//    private void TriggerOfLoaction(String TriggerInfo){
//
//        StringTokenizer stFormap = new StringTokenizer(TriggerInfo, "+");
//        if (stFormap.hasMoreTokens()) {
//            startLocationService();
//            Double latitudeForRegister = Double.parseDouble(stFormap.nextToken());
//            Double longitudeForRegister = Double.parseDouble(stFormap.nextToken());
//            register(1001, latitudeForRegister, longitudeForRegister, 200, -1);
//            mIntentReceiver = new MapReceive(intentKey);
//            registerReceiver(mIntentReceiver, mIntentReceiver.getFilter());
//            Log.d(MainActivity.TAG, "Proximity Start!");
//        }
//    }
//

//    public void register(int id, double latitude, double longitude, float radius, long expiration) {
//
//        Intent proximityIntent = new Intent(getApplicationContext(),
//                MapReceive.class);
//        proximityIntent.putExtra("id", id);
//        proximityIntent.putExtra("latitude", latitude);
//        proximityIntent.putExtra("longitude", longitude);
//
//        PendingIntent pending = getPendingIntent(proximityIntent);
//
//        mLocationManager.addProximityAlert(latitude, longitude, radius, expiration,  pending);
//        mPendingIntentList.add(pending);
//    }
//
//
//    private void startLocationService() {
//
//        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        GPSListener gpsListener = new GPSListener();
//
//        long minTime = 100000;
//        float minDistance = 0;
//
//        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
//        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
//
//    }
//
//    private void unregister() {
//        if (mPendingIntentList != null) {
//            for (int i = 0; i < mPendingIntentList.size(); i++) {
//                PendingIntent curIntent = (PendingIntent) mPendingIntentList.get(i);
//                mLocationManager.removeProximityAlert(curIntent);
//                mPendingIntentList.remove(i);
//            }
//        }
//
//        if (mIntentReceiver != null) {
//            unregisterReceiver(mIntentReceiver);
//            mIntentReceiver = null;
//        }
//    }
//
//    private class GPSListener implements LocationListener {
//
//        public void onLocationChanged(Location location) {
//            Double latitude = location.getLatitude();
//            Double longitude = location.getLongitude();
//
//            Log.i(MainActivity.TAG, "Latitude : "+ latitude + ", Longitude:"+ longitude + " InGPS");
//        }
//
//        public void onProviderDisabled(String provider) { }
//
//        public void onProviderEnabled(String provider) { }
//
//        public void onStatusChanged(String provider, int status, Bundle extras) { }
//    }
//

    private void TriggerOfTime(String TriggerInfo){
        StringTokenizer stFortime = new StringTokenizer(TriggerInfo, "+");
        boolean[] week = new boolean[7];

        StringTokenizer stWeek = new StringTokenizer(stFortime.nextToken(), ".");

        for (int i = 0; i < 7; i++) {
            String Week = stWeek.nextToken();
            if (Week.equals("true"))
                week[i] = true;
            else
                week[i] = false;
        }
        Long triggerTime = Long.valueOf(stFortime.nextToken()).longValue();
        alramSet(week, triggerTime);
    }


    public void alramSet(boolean[] week ,Long triggerTime){

        AlarmManager alarm = (AlarmManager) getApplicationContext.getSystemService(Context.ALARM_SERVICE);

        boolean isRepeat = false;
        int len = week.length;
        long oneday = 24 * 60 * 60 * 1000;// 24시간

        for (int i = 1; i < len; i++){
            if (week[i]) {
                isRepeat = true;
                break;
            }
        }
        Intent intent = new Intent(getApplicationContext, AlarmReceive.class);

        if(isRepeat){

            intent.putExtra("Week", week);
            intent.putExtra("Repeat", true);
            PendingIntent pending = getPendingIntent(intent);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, oneday, pending);
            Log.d(MainActivity.TAG, "Alarm isRepeat");
        }else{

            intent.putExtra("Week", week);
            intent.putExtra("Repeat", false);
            PendingIntent pending = getPendingIntent(intent);
            alarm.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
            Log.d(MainActivity.TAG, "Alarm one time");
        }
    }

    private PendingIntent getPendingIntent(Intent intent) {
        double d = Math.random() *10000;
        int i = Integer.parseInt(String.valueOf(Math.round(d)));

        PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext, i, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }
}
