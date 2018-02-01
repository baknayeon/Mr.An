package com.example.dahae.myandroiice.CheckGrammer;



import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.MainActivity;

/**
 * Created by b_newyork on 2016-01-12.
 */
public class NewPlanCheckAsSemantic {

    Keyword[] triggerArray;
    Keyword[] actionArray;

    public NewPlanCheckAsSemantic(Keyword[] triggerArray, Keyword[] actionArray){
        this.triggerArray = triggerArray;
        this.actionArray = actionArray;
    }

    public Boolean check(){

        boolean amongKeywords = AmongKeywords();
        boolean betweenTriggerAndAction = BetweenTriggerAndAction();
        boolean betweenPlans = BetweenPlans();
        Log.d(NewPlanCheckAsSyntax.TAGS, "**Semantic "+ amongKeywords+" / " +betweenTriggerAndAction +" / "+betweenPlans);

        if(amongKeywords && betweenTriggerAndAction && betweenPlans) //모두다 True
            return true;

        return false;
    }

    private Boolean AmongKeywords(){

        AmongKeyword amongKeyword = new AmongKeyword(triggerArray, actionArray);

        int resultTrigger = amongKeyword.InTriggerCase();
        int resultAction = amongKeyword.InActionCase();

        if (resultAction == 1 && resultTrigger == 1)
            return true;
        else
            return false;

    }

    private Boolean BetweenPlans(){
        BetweenPlans betweenPlans = new BetweenPlans(triggerArray, actionArray);
        int result = betweenPlans.check();

        if(result == 1)
            return true;
        else
            return false;

    }

    private Boolean BetweenTriggerAndAction() {
        boolean result = true;

        BetweenTriggerAndAction betweenTriggerAndAction = new BetweenTriggerAndAction(triggerArray, actionArray);
        result = betweenTriggerAndAction.Check();

        return result;
    }



}
