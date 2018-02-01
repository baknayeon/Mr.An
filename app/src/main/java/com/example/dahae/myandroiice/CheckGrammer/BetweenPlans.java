package com.example.dahae.myandroiice.CheckGrammer;

import android.database.Cursor;
import android.util.Log;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.CheckGrammer.meaning.MeaningOfKeyword;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.ShowingDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by b_newyork on 2016-01-18.
 */
public class BetweenPlans {

    private Keyword[] triggerArray;
    private Keyword[] actionArray;

    private PlanList planlist;

    public BetweenPlans(Keyword[] triggerArray, Keyword[] actionArray){
        this.triggerArray = triggerArray;
        this.actionArray = actionArray;
    }

    public int check(){

        if(triggerArray.length != 0 && actionArray.length != 0) {
            new ShowingDB().seeInfoOfPlan();
            int graphResult = checkGraph();
            double deadLock = checkDeadLock();

            if (graphResult == -1 && deadLock == -1)
                return 1;
        }

        return 0;
    }
    public double checkDeadLock(){
        int result = -1;// true

        if(triggerArray.length != 0 && actionArray.length != 0) {
            planlist = getSavedPlan();

            for (int i = 0; i < planlist.legth(); i++) {
                KeywordList keywordList = planlist.getList(i);

                int sameTrigger = findSameTrigger(keywordList);
                if (sameTrigger != 1) {
                    result = findDeadLockAction(i);
                    Log.d(MainActivity.TAG, "plan " + result + "th;");
                    if (result != -1) // false인 경우
                        return result;//false
                }
            }
        }
        return result;
    }

    private int findDeadLockAction(int i){
        int result = -1;// true;

        KeywordList keywordList = planlist.getList(i);
        for (int j = 0; j < keywordList.legth(); j++)
            if (keywordList.getItem(j).Level == -1) {

                String action = keywordList.getItem(j).Keyword;
                for (int k = 0; k < actionArray.length; k++) {
                    if (deacLock(action, k))
                        return i; // 몇번째 계획인지를 return한다!
                }
            }

        return result;
    }

    private boolean deacLock(String action, int index){

        MeaningOfKeyword meaning = new MeaningOfKeyword();

       if(meaning.Type(action).equals(meaning.Type(actionArray[index].getKeyword())))
           return true;
       else
           return false;
    }

    private int findSameTrigger(KeywordList keywordList){

        for(int j =0; j<keywordList.legth(); j++)
            if (keywordList.getItem(j).Level != -1) {
                String trigger = keywordList.getItem(j).Keyword;
                for (int k = 0; k < triggerArray.length; k++)
                    if (trigger.equals(triggerArray[k].getKeyword()))
                        return k;
            }

        return 1;
    }

    public int checkGraph(){

        int result = -1;

        if(triggerArray.length != 0 && actionArray.length != 0) {
            planlist = getSavedPlan();

            addNewlist();
            for(int i=0; i <planlist.legth(); i++)
                if(planlist != null)
                    if (noCycle(i)) return i; //실패일 경우 return
        }
        return result;
    }

    private boolean noCycle(int i){

        Stack<Integer> stack = new Stack<Integer>();
        String golbalTrigger = planlist.getList(i).triggerItem();
        String standAction = planlist.getList(i).actionItem();

        for(int j=i+1; j <planlist.legth(); j++){
            String oldTrigger = planlist.getList(j).triggerItem();
            if( standAction.equals(oldTrigger))
                stack.push(j);
        }

        while (!stack.empty()){

            int index = stack.pop();

            standAction = planlist.getList(index).actionItem();
            if(golbalTrigger.equals(standAction)) {
                return true; //graph가 있다!
            }else
                for(int j = 0; j<planlist.legth(); j++){
                    String oldTrigger = planlist.getList(j).triggerItem();
                    if( standAction.equals(oldTrigger))
                        stack.push(j);
                }
        }
        return false;
    }

    private void addNewlist(){

        String newTrigger = "" ;
        String newAction = "" ;
        KeywordList standkeywordlist = new KeywordList();
        int index = 0;
        int level =0;

        for(int i =0; i< triggerArray.length; i++) {
            if (!triggerArray[i].getKeyword().equals("Done") && !triggerArray[i].getKeyword().equals("End") && !triggerArray[i].getKeyword().equals("Or") && !triggerArray[i].getKeyword().equals("And")) {
                newTrigger = triggerArray[i].getKeyword();
                standkeywordlist.addItem(new KeywordItem(++index, newTrigger, level));
            }
            if(triggerArray[i].getKeyword().equals("Or"))
                standkeywordlist.addItem(new KeywordItem(++index,"Or", level++));
            if( triggerArray[i].getKeyword().equals("And"))
                standkeywordlist.addItem(new KeywordItem(++index,"And", level++));
            if(triggerArray[i].getKeyword().equals("Done")){
                standkeywordlist.addItem(new KeywordItem(++index, "Done", level--));
            }
            if(triggerArray[i].getKeyword().equals("End")){
                standkeywordlist.addItem(new KeywordItem(++index, "End", level));
            }
        }

        for (int i =0; i<  actionArray.length; i++) {
            newAction = actionArray[i].getKeyword();
            standkeywordlist.addItem(new KeywordItem(++index, newAction, -1));
        }
        planlist.addList(standkeywordlist, "newPlanList");
    }

    class KeywordItem {
        String Keyword;
        int Index;
        int Level;

        private KeywordItem(int Index, String Keyword, int Level){
            this.Keyword = Keyword;
            this.Index = Index;
            this.Level = Level;
        }
    }

    class KeywordList {
        List<KeywordItem> Plan;

        private KeywordList(){
            this.Plan = new ArrayList<KeywordItem>();
        }

        private void addItem(KeywordItem planItem){
            Plan.add(planItem);
        }

        private KeywordItem getItem(int i){
            return Plan.get(i);
        }

        private String actionItem(){
            String action = Plan.get(Plan.size()-1).Keyword;
            return action;
        }

        private String triggerItem(){
            String trigger = Plan.get(0).Keyword;
            return trigger;
        }

        private int legth(){
            if(Plan == null)
                return 0;
            else
                return Plan.size();
        }
    }

    class PlanList {
        List<KeywordList> Plan;
        List<String> PlanName;

        private PlanList(){
            this.Plan = new ArrayList<KeywordList>();
            this.PlanName = new ArrayList<String>();
        }

        private void addList(KeywordList planItem, String planName){
            Plan.add(planItem);
            PlanName.add(planName);
        }

        private KeywordList getList(int i){
            return Plan.get(i);
        }

        private String name(int i){
            return PlanName.get(i);
        }

        private int legth(){
            if(Plan == null)
                return -1;
            else
                return Plan.size();
        }
    }

    private PlanList getSavedPlan(){

        Cursor cursor;

        PlanList planList = new PlanList();
        KeywordList keywordLisList;
        try {
            if (MainActivity.database != null) {
                // 모든 테이블 목록(플랜 목록) 보여주기
                Cursor cursorT = MainActivity.database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

                while (cursorT.moveToNext()) {

                    if (!cursorT.getString(0).equals("android_metadata") && !cursorT.getString(0).equals("sqlite_sequence")) {
                        String Tablename = cursorT.getString(0);

                        keywordLisList = new KeywordList();
                        cursor = MainActivity.database.rawQuery("SELECT * FROM " + Tablename, null);
                        for (int i = 0; i < cursor.getCount(); i++) {
                            if (cursor.moveToNext()) {
                                int index = cursor.getInt(0);
                                String keyword = cursor.getString(1);
                                int keyword_level = cursor.getInt(3);

                                if( !keyword.equals("Done") && !keyword.equals("End"))
                                    if( keyword.contains("/")) {
                                        StringTokenizer st = new StringTokenizer(keyword, "/");
                                        while(st.hasMoreTokens()){
                                            String newAction = st.nextToken();
                                            keywordLisList.addItem(new KeywordItem(index, newAction, keyword_level));
                                        }
                                    }
                                    else
                                        keywordLisList.addItem(new KeywordItem(index, keyword, keyword_level));
                            }
                        }
                        planList.addList(keywordLisList,Tablename);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return planList;
    }

}
