package com.example.dahae.myandroiice.CheckGrammer;

import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.CheckGrammer.meaning.MeaningOfTriggerNAction;
import com.example.dahae.myandroiice.MainActivity;

/**
 * Created by b_newyork on 2016-01-18.
 */
public class BetweenTriggerAndAction {

    private Keyword[] triggerArray;
    private Keyword[] actionArray;

    public BetweenTriggerAndAction(Keyword[] triggerArray, Keyword[] actionArray){
        this.triggerArray = triggerArray;
        this.actionArray = actionArray;
    }

    public Boolean Check(){

        int FindingTriggerResult = findingTrigger(); //4
        int sameContant = sameConstantTriggerAndAction(); //3
        int sameKeyword = sameKeywordTriggerAndAction(); //2

        if ( FindingTriggerResult == 1 && sameContant == 1 && sameKeyword == 1)
            return true;
        return false;
    }


    public int sameKeywordTriggerAndAction(){

        for(int i=0; i<triggerArray.length; i++)
            for(int j =0; j <actionArray.length; j++)
                if(triggerArray[i].getKeyword().equals(actionArray[j].getKeyword())) {

                    return 2;
                }

        return 1;
    }

    public int sameConstantTriggerAndAction(){

        MeaningOfTriggerNAction meaning = new MeaningOfTriggerNAction();
        String triggerConstant, actionConstant;

        for(int i = 0; i<triggerArray.length; i++) {
            triggerConstant = meaning.triggerConstant(triggerArray[i].getKeyword());
            for(int j =0; j< actionArray.length; j++) {
              actionConstant = meaning.actionConstant(actionArray[j].getKeyword());
              if (!triggerConstant.equals("-1") && !actionConstant.equals("-1"))
                  if (actionConstant.contains(triggerConstant)) {
                      Log.d(MainActivity.TAG, "sameConstant action and trigger " + actionConstant +" = " +triggerConstant);
                      return 3;
                  }
            }
        }
        return 1;
    }

    public int findingTrigger(){

        int result = 1;
        for(int j =0; j < actionArray.length; j++)
            if(actionArray[j].getKeyword().contains("TellPhoneNum")) {
                result = 4;
                for (int i = 0; i < triggerArray.length; i++)
                    if (triggerArray[i].getKeyword().contains("Call") || triggerArray[i].getKeyword().contains("SMS"))
                        result = 1;
            }
        if( result == 4)
            return 4;

        for(int j =0; j < actionArray.length; j++)
            if(actionArray[j].getKeyword().contains("TellSMS")) {
                result = 4;
                for (int i = 0; i < triggerArray.length; i++)
                    if (triggerArray[i].getKeyword().contains("SMS"))
                        result = 1;
            }
        if( result == 4)
            return 4;

        return result;
    }

}
