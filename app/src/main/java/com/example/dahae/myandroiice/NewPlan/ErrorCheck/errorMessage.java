package com.example.dahae.myandroiice.NewPlan.ErrorCheck;

import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.CheckGrammer.AmongKeyword;
import com.example.dahae.myandroiice.CheckGrammer.BetweenPlans;
import com.example.dahae.myandroiice.CheckGrammer.BetweenTriggerAndAction;
import com.example.dahae.myandroiice.CheckGrammer.NewPlanCheckAsSyntax;
import com.example.dahae.myandroiice.MainActivity;

/**
 * Created by b_newyork on 2016-02-11.
 */
public class errorMessage {

    NewPlanCheckAsSyntax planAsSyntax;
    AmongKeyword amongKeyword;
    BetweenTriggerAndAction betweenTriggerAndAction;
    BetweenPlans betweenPlans;

    public errorMessage(Keyword[] triggerArray,  Keyword[] actionArray){

        planAsSyntax = new NewPlanCheckAsSyntax(triggerArray, actionArray);
        amongKeyword = new AmongKeyword(triggerArray, actionArray);

        betweenTriggerAndAction = new BetweenTriggerAndAction(triggerArray, actionArray);
        betweenPlans = new BetweenPlans(triggerArray, actionArray);
    }

    public String wholeErrorMessage(){
        String errorMessage ="";

        errorMessage = errorMessage.concat(errorSyntaxTrigger());
        errorMessage = errorMessage.concat(errorSyntaxAction());
        errorMessage =  errorMessage.concat(errorAmongKeyword());
        errorMessage = errorMessage.concat(errorBetweenTriggerAndAction());
        errorMessage = errorMessage.concat(errorBetweenPlans());

        return errorMessage;
    }

    public String wholeSolutionMessage(){
        String solutionMessage ="";

        solutionMessage = solutionMessage.concat(solutionSyntaxTrigger());
        solutionMessage = solutionMessage.concat(solutionSyntaxAction());
        solutionMessage =  solutionMessage.concat(solutionAmongKeyword());
        solutionMessage = solutionMessage.concat(solutionBetweenTriggerAndAction());
        solutionMessage = solutionMessage.concat(solutionBetweenPlans());

        return solutionMessage;
    }

    public String solutionSyntaxTrigger(){

        double mSyntaxTrigger = planAsSyntax.Trigger();
        String errorString ="";

        if (mSyntaxTrigger == -1)
            errorString = errorString.concat("상황키워드를 한개이상 선택해주세요\n");
        else if (mSyntaxTrigger == 0)
            errorString = errorString.concat("복잡한 상황문일경우엔 첫번째 키워드는 꼭 상황연산자를 쓰셔야 합니다\n");
        else if (mSyntaxTrigger == 2)
            errorString = errorString.concat("간단하 상황문을 만들경우에는 상황연산자를 쓰시면 안됩니다\n");
//        else if (mSyntaxTrigger == 2.1)
//            errorString = errorString.concat("복잡한 상황문을 계속해서 만들어 주세요\n");
        else if (mSyntaxTrigger == 4  || mSyntaxTrigger == 3)
            if (mSyntaxTrigger == 3)
                errorString = errorString.concat("AND/OR와 DONE의 갯수를 같게 해주세요\n");
            else if (mSyntaxTrigger == 4)
                errorString = errorString.concat("AND/OR와 DONE사이의 상황 키워드를 두개이상 선택해야합니다\n");

        if(mSyntaxTrigger == 4.1)
            errorString = errorString.concat("완성된 상황문 뒤의 필요하지 않는 키워드들을 없애주세요\n");
        return errorString;
    }

    public String solutionSyntaxAction(){

        int mSyntaxAction = planAsSyntax.Action();
        String errorString ="";

        if (mSyntaxAction == -1)
            errorString = errorString.concat("행동키워드를 한개이상 선택해주세요\n");
        else if (mSyntaxAction == 3)
            errorString = errorString.concat("행동키워드 전화하기의 번호를 설정해주세요\n");
        else if (mSyntaxAction == 4)
            errorString = errorString.concat("행동키워드 메세지보내기의 메세지를 작성해주세요\n");
        else if (mSyntaxAction == 5)
            errorString = errorString.concat("행동키워드 Call의 번호를 설정해주세요\n");
        else if (mSyntaxAction == 6)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");
        else if (mSyntaxAction == 7)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");
        else if (mSyntaxAction == 8)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");

        return errorString;
    }

    public String solutionAmongKeyword(){

        int mAmongKeywordTrigger = amongKeyword.InTriggerCase();
        int mAmongKeywordAction = amongKeyword.InActionCase();
        String errorString ="";

        if (mAmongKeywordTrigger == 3)
            errorString = errorString.concat("같은 타입의 상황키워드들이 있습니다\n");
        if (mAmongKeywordTrigger == 4)
            errorString = errorString.concat("같은 상수를 가지는 상황키워드들이 있습니다\n");
        if (mAmongKeywordTrigger == 5)
            errorString = errorString.concat("같은 타입의 상황키워드들이 있습니다.\n" +
                    "같은 상수를 가지는 상황키워드들이 있습니다.\n");

        if (mAmongKeywordAction == 3)
            errorString = errorString.concat("같은 타입의 행동키워드들이 있습니다.\n");
        if (mAmongKeywordAction == 4)
            errorString = errorString.concat("같은 상수를 가지는 행동키워드들이 있습니다.\n");

        return errorString;
    }

    public String solutionBetweenTriggerAndAction(){

        int mSameContant = betweenTriggerAndAction.sameConstantTriggerAndAction();
        int mSameKeyword = betweenTriggerAndAction.sameKeywordTriggerAndAction();
        int mFindingTriggerResult = betweenTriggerAndAction.findingTrigger();
        String errorString ="";

        if(mFindingTriggerResult == 4)
            errorString = errorString.concat("*특정 상황키워드와 꼭! 매칭되어야 하는 행동키워드가 있습니다.\n");
        if(mSameContant == 3)
            errorString = errorString.concat("*같은 상수를 가진 키워드끼리는 함께 계획을 만드실수 없습니다.\n");
        if(mSameKeyword == 2)
            errorString = errorString.concat("*같은 종류 키워드끼리는 함께 계획을 만드실수 없습니다.\n");

        return errorString;
    }

    public String solutionBetweenPlans(){

        double mGraph = betweenPlans.checkGraph();
        double mDeadLock = betweenPlans.checkDeadLock();
        String errorString ="";

        //betwennPlans의 경우 어떤 계획과 오류가 생기는지 알게 위해 0을 true로 간주한다.
        if(mGraph != -1)
            errorString = errorString.concat(mGraph +"번째 계획과 " +
                    "심각한 오류를 발생시킬수 있는 부탁을 만들고 계십니다. \n");
        if(mDeadLock != -1)
            errorString = errorString.concat(mDeadLock +"번째 계획과 " +
                    "비슷한 부탁입니다. 새로운 계획을 만들어 주세요^^\n");

        return errorString;
    }



    public String errorSyntaxTrigger(){

        double mSyntaxTrigger = planAsSyntax.Trigger();
        String errorString ="";

        if (mSyntaxTrigger == -1)
            errorString = errorString.concat("*상황키워드가 0개 입니다..\n");
        else if (mSyntaxTrigger == 0)
            errorString = errorString.concat("*복잡한 상황문일경우엔 첫번째 키워드는 꼭 상황연산자를 쓰셔야 합니다.\n");
        else if (mSyntaxTrigger == 2)
            errorString = errorString.concat("*간단하 상황문을 만들경우에는 상황연산자를 쓰시면 안됩니다.\n");
        else if (mSyntaxTrigger == 2.1)
            errorString = errorString.concat("*상황연산자 하나만 있는경우\n");
        else if (mSyntaxTrigger == 4  || mSyntaxTrigger == 3)
            if (mSyntaxTrigger == 3)
                errorString = errorString.concat("*AND/OR와 DONE의 갯수 매칭이 잘못 되었습니다. \n");
            else if (mSyntaxTrigger == 4)
                errorString = errorString.concat("*AND/OR와 DONE사이의 상황 키워드의 수가 부족합니다.\n");
        if(mSyntaxTrigger == 4.1)
            errorString = errorString.concat("*필요하지 않는 키워드 들이 상황문 뒤에 추가되있는것 같습니다.\n");

        return errorString;
    }

    public String errorSyntaxAction(){

        int mSyntaxAction = planAsSyntax.Action();
        String errorString ="";

        if (mSyntaxAction == -1)
            errorString = errorString.concat("*행동키워드가 0개 입니다. \n");
        else if (mSyntaxAction == 3)
            errorString = errorString.concat("*행동키워드 전화하기의 정보가 없습니다.\n");
        else if (mSyntaxAction == 4)
            errorString = errorString.concat("*행동키워드 메세지보내기의 정보가 없습니다.\n");
        else if (mSyntaxAction == 5)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");
        else if (mSyntaxAction == 6)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");
        else if (mSyntaxAction == 7)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");
        else if (mSyntaxAction == 8)
            errorString = errorString.concat("*행동키워드 Call의 정보가 없습니다.\n");

        return errorString;
    }

    public String errorAmongKeyword(){

        int mAmongKeywordTrigger = amongKeyword.InTriggerCase();
        int mAmongKeywordAction = amongKeyword.InActionCase();

        Log.d(MainActivity.TAG, "* amongKeyword " + mAmongKeywordTrigger + "/" + mAmongKeywordAction);
        String errorString ="";

        if (mAmongKeywordTrigger == 3)
            errorString = errorString.concat("*같은 타입의 상황키워드들이 있습니다.\n");
        if (mAmongKeywordTrigger == 4)
            errorString = errorString.concat("*같은 상수를 가지는 상황키워드들이 있습니다.\n");
        if (mAmongKeywordTrigger == 5)
            errorString = errorString.concat("*같은 타입의 상황키워드들이 있습니다.\n" +
                    "*같은 상수를 가지는 상황키워드들이 있습니다.\n");

        if (mAmongKeywordAction == 3)
            errorString = errorString.concat("*같은 타입의 행동키워드들이 있습니다.\n");
        if (mAmongKeywordAction == 4)
            errorString = errorString.concat("*같은 상수를 가지는 행동키워드들이 있습니다.\n");

        return errorString;
    }

    public String errorBetweenTriggerAndAction(){

        int mSameContant = betweenTriggerAndAction.sameConstantTriggerAndAction();
        int mSameKeyword = betweenTriggerAndAction.sameKeywordTriggerAndAction();
        int mFindingTriggerResult = betweenTriggerAndAction.findingTrigger();
        String errorString ="";

        Log.d(MainActivity.TAG, "* betweenTriggerAndAction "
                + mFindingTriggerResult +"/" + mSameContant +"/" + mSameKeyword);

        if(mFindingTriggerResult == 4)
            errorString = errorString.concat("*특정 상황키워드와 꼭! 매칭되어야 하는 행동키워드가 있습니다.\n");
        if(mSameContant == 3)
            errorString = errorString.concat("*같은 상수를 가진 키워드끼리는 함께 계획을 만드실수 없습니다.\n");
        if(mSameKeyword == 2)
            errorString = errorString.concat("*같은 종류 키워드끼리는 함께 계획을 만드실수 없습니다.\n");

        return errorString;
    }

    public String errorBetweenPlans(){

        double mGraph = betweenPlans.checkGraph();
        double mDeadLock = betweenPlans.checkDeadLock();
        String errorString ="";

        Log.d(MainActivity.TAG, "* betweenPlans " + mGraph + "/" + mDeadLock);
        //betwennPlans의 경우 어떤 계획과 오류가 생기는지 알게 위해 0을 true로 간주한다.
        if(mGraph != -1)
            errorString = errorString.concat(mGraph +"번째 계획과 " +
                    "심각한 오류를 발생시킬수 있는 부탁을 만들고 계십니다. \n");
        if(mDeadLock != -1)
            errorString = errorString.concat(mDeadLock +"번째 계획과 " +
                    "비슷한 부탁입니다. 새로운 계획을 만들어 주세요^^\n");

        return errorString;
    }


}
