package com.example.dahae.myandroiice.CheckGrammer;

import android.database.Cursor;
import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewPlanCheckAsSyntax {

    public static String TAGS = "-iceg";

    Keyword[] triggerArray;
    Keyword[] actionArray;

    public NewPlanCheckAsSyntax(){

    }

    public NewPlanCheckAsSyntax(Keyword[] triggerArray, Keyword[] actionArray){
        this.triggerArray = triggerArray;
        this.actionArray = actionArray;
    }

    public int NoSameName(String planName){
        if(!planName.equals("")) {
            Cursor cursor = MainActivity.database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
            try {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            String tableName = cursor.getString(0);
                            // Log.d(TAG, "compare "+ tableName+ " and " + planName);
                            if (tableName.equals(planName)) {
                                return 0;
                            }
                            cursor.moveToNext();
                        }
                    }
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return 1;
        }else{
            return -1;
        }
    }

    public Boolean check() {

        double resultTrigger = Trigger();
        int resultAction = Action();

        Log.d(TAGS, "** Syntax " + resultTrigger + " / " + resultAction);

        if (resultAction == 1 && resultTrigger == 1)
            return true;
        else
            return false;
    }

    public double Trigger(){

        double result = -1;

        if(triggerArray.length == 1 ) {
          //  if(!triggerArray[0].getKeyword().equals("And")&& !triggerArray[0].getKeyword().equals("Or")&& !triggerArray[0].getKeyword().equals("Done"))
                result = SimpleTrigger(); //상황키워드 하나인경우
//            else if (triggerArray[0].getKeyword().equals("And") || triggerArray[0].getKeyword().equals("Or") || triggerArray[0].getKeyword().equals("Done"))
//                return 2.1;
        }else if(triggerArray.length > 1)
            result = ComplexTrigger();

        return result;
    }

    private int SimpleTrigger(){
        int resultTrigger = 1;
        String trigger = "";

        trigger = triggerArray[0].getKeyword();
        if (trigger.equals("And") || trigger.equals("Or") || trigger.equals("Done") || trigger.equals("End")) {
            resultTrigger = 2;
        }

        return resultTrigger;
    }

    private double ComplexTrigger() {
        double result = 0;

        if(numOfoperator()) {
            if (triggerArray[0].getKeyword().equals("And") || triggerArray[0].getKeyword().equals("Or")) {
                if (countKeyWords())
                    result = 1;
                else
                    result = 4;

                if( index+1 != triggerArray.length)
                    result = 4.1; // 필요하지 않는 키워드기 들어갔다.
            }
        }else{
            result = 3;
            Log.d(MainActivity.TAG, "error numOfoperator" + result);
        }
        return result;
    }

    private boolean numOfoperator(){
        int count = 0;
        int count2 = 0;

        for(int i = 0; i <triggerArray.length; i++)
            if(triggerArray[i].getKeyword().equals("And")  || triggerArray[i].getKeyword().equals("Or"))
                count++;
        for(int i = 0; i <triggerArray.length; i++)
            if(triggerArray[i].getKeyword().equals("Done"))
                count2++;


        if(count == count2)
            return true;
        else
            return false;
    }

    int index = 1;
    private boolean countKeyWords(){

        List<Boolean> listResult = new ArrayList<>();
        int Count = 0 ;

        while( !triggerArray[index].getKeyword().equals("Done")){
            if(triggerArray[index].getKeyword().equals("And")  || triggerArray[index].getKeyword().equals("Or")) {
                ++index;
                listResult.add(countKeyWords());
            }else{
                ++index;
                Count++;
            }
        }

        if(Count < 2)
            listResult.add(false);

        return result(listResult);
    }

    private boolean result(List<Boolean> listResult){
        Iterator iterator = listResult.iterator();

        while (iterator.hasNext()) {
            Boolean and = (Boolean) iterator.next();
            Log.d(MainActivity.TAG, "result " + and);
            if (and != true)
                return false;
        }
        return true;
    }

    public int Action() {
        int result = -1;

        if (actionArray.length != 0) {
            String action = "";
            String actionInfo = "";

            for (int i = 0; i < actionArray.length; i++) {
                action = actionArray[i].getKeyword();
                actionInfo = actionArray[i].getKeywordInfo();
                if (action.equals(""))
                    return 2;
                else {
                    result = 1;

                    if (action.equals("Call")) {
                        if ("".equals(actionInfo)) {

                            Log.d(MainActivity.TAG, "Call as Syntax " + actionInfo + 3);
                            return 3;
                        }
                    } else if (action.equals("SMS")) {
                        if ("".equals(actionInfo)){
                            Log.d(MainActivity.TAG, "SMS as Syntax  " + actionInfo + 4);
                            return 4;
                        }
                    } else if (action.equals("TextToVoice")) {
                        if ("".equals(actionInfo)){
                            Log.d(MainActivity.TAG, "TextToVoice  as Syntax" + actionInfo + result);
                            return 5;
                        }
                    } else if (action.equals("Volume")) {
                        if ("".equals(actionInfo)) {
                            Log.d(MainActivity.TAG, "Volume  as Syntax" + actionInfo + result);
                            return 6;
                        }
                    } else if (action.equals("Bookmark")) {
                        if ("".equals(actionInfo)) {
                            Log.d(MainActivity.TAG, "Bookmark  as Syntax" + actionInfo + result);
                            return 7;
                        }
                    } else if (action.equals("Notification")) {
                        if ("".equals(actionInfo)){
                            Log.d(MainActivity.TAG, "Notification  as Syntax" + actionInfo + result);
                            return 8;
                        }
                    }
                }
            }
        }
        return result;
    }
}
