package com.example.dahae.myandroiice.NewPlan.Making.SimplePlan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.dahae.myandroiice.NewPlan.Making.SimplePlan.SimpleAction;
import com.example.dahae.myandroiice.NewPlan.Making.SimplePlan.SimpleConfig;
import com.example.dahae.myandroiice.NewPlan.Making.SimplePlan.SimpleTrigger;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {

    public SimpleTrigger simpleTrigger;
    public SimpleAction simpleAction;
    public SimpleConfig simpleConfig;

    public SimplePagerAdapter(FragmentManager fm, SimpleTrigger simpleTrigger, SimpleAction simpleAction, SimpleConfig simpleConfig) {
        super(fm);
        this.simpleTrigger = simpleTrigger;
        this.simpleAction = simpleAction;
        this.simpleConfig = simpleConfig;
    }

    //해당하는 page의 fragment 생성
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            simpleTrigger = SimpleTrigger.init(position);
            return simpleTrigger;
        } else if(position == 1){
            simpleAction = SimpleAction.init(position);
            return simpleAction;
        } else {
            simpleConfig = SimpleConfig.init(position);
            return simpleConfig;
        }
    }

    @Override
    public int getCount() {
        return 3;  // 총 3개의 page 사용
    }

}