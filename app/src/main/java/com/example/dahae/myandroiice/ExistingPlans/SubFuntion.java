package com.example.dahae.myandroiice.ExistingPlans;

import android.database.Cursor;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.PlanItem;
import com.example.dahae.myandroiice.Adapter.KeywordAdapter;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.ChangingName;

import java.util.ArrayList;

public class SubFuntion {

    public ArrayList<PlanItem> getdisplayListInFalse(){

        PlanItem planItem;
        ArrayList<PlanItem> planList= new ArrayList<>();
        Cursor cursor = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);

        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String planName = cursor.getString(1);
                        String activationState = cursor.getString(2);

                        if(activationState.equals("false")){
                            planItem = new PlanItem(planName, planName, false);
                            planList.add(planItem);
                        }
                    }
                }
            }
        } finally {
            cursor.close();
        }
        return planList;
    }

    public ArrayList<PlanItem> getdisplayListInTrue(){

        PlanItem planItem;
        ArrayList<PlanItem> planList= new ArrayList<>();
        Cursor cursor = MainActivity.databaseForRecordTime.rawQuery("SELECT * FROM ActivationInfoTable", null);

        try {
            if (MainActivity.databaseForRecordTime != null){
                if (cursor != null && cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        String planName = cursor.getString(1);
                        String activationState = cursor.getString(2);
                      //  Log.d(TAG, "planName: " + planName + " / activationState: " + activationState);
                        if(activationState.equals("true")){
                            planItem = new PlanItem(planName, planName, true);
                            planList.add(planItem);
                        }
                    }
                }
            }
        } finally {
            cursor.close();
        }
        return planList;
    }

    public void copyPlanAsTrue(String oldName){

        String newName = oldName.concat("_복사본");

        try {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + newName + "("
                        + " _id integer primary KEY autoincrement,"
                        + " Keyword2 text,"
                        + " Keyword_info text,"
                        + " Keyword_level integer"
                        + ");");
                MainActivity.database.execSQL("INSERT INTO " + newName + " SELECT * FROM " + oldName + ";");

                if(MainActivity.databaseForRecordTime != null) {
                    MainActivity.databaseForRecordTime.execSQL("CREATE TABLE if not exists ActivationInfoTable("
                            + " _id integer primary KEY autoincrement,"
                            + "planName text,"
                            + "activation text"
                            + ");");
                    MainActivity.databaseForRecordTime.execSQL("INSERT INTO ActivationInfoTable(planName, activation) values "
                            + "('" + newName + "', 'true');");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyPlanAsFalse(String oldName){

        String newName = oldName.concat("_복사본");

        try {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("CREATE TABLE " + newName + "("
                        + " _id integer primary KEY autoincrement,"
                        + " Keyword2 text,"
                        + " Keyword_info text,"
                        + " Keyword_level integer"
                        + ");");
                MainActivity.database.execSQL("INSERT INTO " + newName + " SELECT * FROM " + oldName + ";");

                if(MainActivity.databaseForRecordTime != null) {
                    MainActivity.databaseForRecordTime.execSQL("CREATE TABLE if not exists ActivationInfoTable("
                            + " _id integer primary KEY autoincrement,"
                            + "planName text,"
                            + "activation text"
                            + ");");
                    MainActivity.databaseForRecordTime.execSQL("INSERT INTO ActivationInfoTable(planName, activation) values "
                            + "('" + newName + "', 'false');");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePlan(String Name ){
        try {
            if (MainActivity.database != null)
                MainActivity.database.execSQL("DROP TABLE IF EXISTS " + Name);
            if(MainActivity.databaseForRecordTime != null)
                MainActivity.databaseForRecordTime.execSQL("DELETE FROM ActivationInfoTable where planName = '" + Name + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePlanName(String oldName, String newName ){
        try {
            if (MainActivity.database != null) {
                MainActivity.database.execSQL("ALTER TABLE " + oldName+ " RENAME TO " + newName);
            }
            if (MainActivity.databaseForRecordTime != null)
                MainActivity.databaseForRecordTime.execSQL("UPDATE ActivationInfoTable SET planName = '" + newName + "' "
                        + "where planName = '" + oldName + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public KeywordAdapter getSubItemOfTrigger(KeywordAdapter subAdapterForTrigger,  String listViewName){

        ChangingName changingName = new ChangingName();

        if(listViewName != null) {
            Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM " + listViewName+" where keyword_level > -1", null);
            cursor.moveToFirst();
            try {
                if (MainActivity.database != null) {
                    if (cursor != null && cursor.getCount() != 0) {
                        for (int i = 0; i < cursor.getCount() - 2 ; i++) {
                            String trigger = cursor.getString(1);
                            subAdapterForTrigger.addItem(new Keyword(changingName.Trigger(trigger)));
                            cursor.moveToNext();
                        }
                    }}
            } finally {
                cursor.close();
            }
        }
        return subAdapterForTrigger;
    }

    public KeywordAdapter getSubItemOfAction(KeywordAdapter subAdapterForAction,  String listViewName){

        ChangingName changingName = new ChangingName();

        if(listViewName != null) {
            Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM " + listViewName +" where keyword_level = -1", null);
            try {
                if (MainActivity.database != null) {
                    cursor.moveToFirst();
                    for(int i=0; i<cursor.getCount(); i++) {
                        String action = cursor.getString(1);
                        subAdapterForAction.addItem(new Keyword(changingName.Action(action)));//고쳐
                        cursor.moveToNext();
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return subAdapterForAction;
    }
    public KeywordAdapter getTriggerItemForModify(KeywordAdapter subAdapterForTrigger,  String listViewName){

        ChangingName changingName = new ChangingName();

        if(listViewName != null) {
            Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM " + listViewName+" where keyword_level > -1", null);
            cursor.moveToFirst();
            try {
                if (MainActivity.database != null) {
                    if (cursor != null && cursor.getCount() != 0) {
                        for (int i = 0; i < cursor.getCount() - 2 ; i++) {
                            String trigger = cursor.getString(1);
                            String triggerInfo = cursor.getString(2);
                            subAdapterForTrigger.addItem(new Keyword(changingName.Trigger(trigger),triggerInfo));
                            cursor.moveToNext();
                        }
                    }}
            } finally {
                cursor.close();
            }
        }
        return subAdapterForTrigger;
    }

    public KeywordAdapter getActionItemForModify(KeywordAdapter subAdapterForAction,  String listViewName){

        ChangingName changingName = new ChangingName();

        if(listViewName != null) {
            Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM " + listViewName +" where keyword_level = -1", null);
            try {
                if (MainActivity.database != null) {
                    cursor.moveToFirst();
                    for(int i=0; i<cursor.getCount(); i++) {
                        String action = cursor.getString(1);
                        String actionInfo = cursor.getString(2);
                        subAdapterForAction.addItem(new Keyword(changingName.Action(action),actionInfo));//고쳐
                        cursor.moveToNext();
                    }
                }
            } finally {
                cursor.close();
            }
        }

        return subAdapterForAction;
    }
}
