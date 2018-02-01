package com.example.dahae.myandroiice.CheckGrammer;

import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.CheckGrammer.meaning.MeaningOfKeyword;
import com.example.dahae.myandroiice.MainActivity;

/**
 * Created by b_newyork on 2016-01-15.
 */
public class AmongKeyword {

    MeaningOfKeyword meaningOfkeyword = new MeaningOfKeyword();

    private Keyword[] triggerArray;
    private Keyword[] actionArray;

    public AmongKeyword(Keyword[] triggerArray, Keyword[] actionArray){
        this.triggerArray = triggerArray;
        this.actionArray = actionArray;
    }

    public int InActionCase(){

        int SameType = IftheyareSameType(actionArray);//2
        int SameConstant = IftheyareSameConstant(actionArray);//3

        if(SameType == 1 && SameConstant == 1)
            return 1;
        else
            return SameConstant + SameType;
    }

    public int InTriggerCase(){
        int result = 0;

        if(triggerArray.length == 1)
            result =  1;
        else if(isthatLevelOne())
            result = levelOneCase();
        else ;
           // result = ComplexTrigger();

       // Log.d(MainActivity.TAG, "In trigger case " +result);
        return result;
    }

    private boolean isthatLevelOne(){
        int count = 0;

        for(int i = 0; i < triggerArray.length; i++ )
            if(triggerArray[i].getKeyword().equals("Done"))
                count++;
        if(count == 1)
            return true;
        else
            return false;
    }
    private int levelOneCase(){

        int result = 0;

        if(WhatisAndtrigger())
            result = SimpleAndCase();
        else if (WhatisOrtrigger())
            result = SimpleOrCase();

        return result;
    }

    private int SimpleOrCase(){

        int SameType = IftheyareSameType(triggerArray);//2
        int SameConstant = IftheyareSameConstant(triggerArray);//3

        Log.d(MainActivity.TAG, "SimpleOrCase " + SameConstant + SameType);

        if(SameType == 1 && SameConstant == 1)
            return 1;
        else
            return SameConstant + SameType;
    }

    private int SimpleAndCase(){

        int SameConstant = IftheyareSameConstant(triggerArray);
        int SameType = IftheyareSameType(triggerArray);

        Log.d(MainActivity.TAG, "SimpleAndCase " + SameConstant + SameType);
        if(SameType == 1 && SameConstant == 1)
            return 1;
        else
            return SameConstant + SameType;
    }

    private int IftheyareSameType(Keyword[] Keyword){

        for(int i = 0; i < Keyword.length; i++)
            for (int j =  i+1; j < Keyword.length; j++)
                if(meaningOfkeyword.Type(Keyword[i].getKeyword()).equals(meaningOfkeyword.Type(Keyword[j].getKeyword())) )
                    return 2;

        return 1;
    }

    private void BatteryInSimpleORcase() {
        if(triggerArray[1].getKeyword().contains("LowBattery") && triggerArray[1].getKeyword().contains("FullBattery"));

    }

    private int IftheyareSameConstant(Keyword[] Keyword){

        for(int i = 0; i < Keyword.length; i++) {
            for (int j = i+1; j < Keyword.length; j++) {
                if(meaningOfkeyword.Constant(Keyword[i].getKeyword()) == meaningOfkeyword.Constant(Keyword[j].getKeyword()) ) {
                 Log.d(MainActivity.TAG, "SameConstant " + meaningOfkeyword.Constant(Keyword[i].getKeyword()) + meaningOfkeyword.Constant(Keyword[j].getKeyword()) );
                    return 3;
                }
            }
        }
        return 1;
    }

    private Boolean ComplexTrigger(){

        boolean result =false;

        if(WhatisAndtrigger())
            result = ComplexAndCase();
        if(WhatisOrtrigger())
            result = ComplexOrCase();

        return result;

    }

    private Boolean WhatisAndtrigger(){
        if(triggerArray[0].getKeyword().equals("And"))
            return true;
        else
            return false;
    }

    private Boolean WhatisOrtrigger(){
        if(triggerArray[0].getKeyword().equals("Or"))
            return true;
        else
            return false;
    }

    private Boolean ComplexAndCase(){

        return true;
    }

    private Boolean ComplexOrCase(){

        return true;
    }

}
