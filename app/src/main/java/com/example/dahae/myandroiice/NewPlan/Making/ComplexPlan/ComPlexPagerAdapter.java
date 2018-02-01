package com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.Button;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan.ComplexAction;
import com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan.ComplexConfig;
import com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan.ComplexTrigger;
import com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan.NewComplexPlan;
import com.example.dahae.myandroiice.R;

import java.util.Locale;

public class ComPlexPagerAdapter extends FragmentPagerAdapter {

    public ComplexTrigger complexTrigger;
    public ComplexAction complexAction;
    public ComplexConfig complexConfig;

    public ComPlexPagerAdapter(FragmentManager fm, ComplexTrigger complexTrigger, ComplexAction complexAction, ComplexConfig complexConfig) {
        super(fm);
        this.complexTrigger = complexTrigger;
        this.complexAction = complexAction;
        this.complexConfig = complexConfig;
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            complexTrigger = ComplexTrigger.init(position);
            return complexTrigger;
        } else if(position == 1){complexAction = ComplexAction.init(position);
            return complexAction;
        } else if(position == 2){
            complexConfig = ComplexConfig.init(position);
            return complexConfig;
        }else
            return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}