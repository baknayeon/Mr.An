package com.example.dahae.myandroiice.Information.ComplexPlan;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-02-04.
 */
public class informationOfComplexPlan extends AppCompatActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    Handler handler;
   int time = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.informaion_anbisa2);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                this.sendEmptyMessageDelayed(0, 2000);

                int page = mViewPager.getCurrentItem();
                page++;
                if(time == 0) {
                    mViewPager.setCurrentItem(0, true);
                    time++;
                }else
                    mViewPager.setCurrentItem(page, true);

            }
        };

        handler.sendEmptyMessage(1);

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new complex1().init(position);
            } else if(position == 1){
                return new complex2().init(position);
            } else if(position == 2){
                return new complex3().init(position);
            }else if(position == 3){
                return new complex4().init(position);
            }else if(position == 4){
                return new complex5().init(position);
            }else if(position == 5){
                return new complex6().init(position);
            }else if(position == 6){
                return new complex7().init(position);
            }else if(position == 7){
                return new complex8().init(position);
            }else if(position == 8){
                return new complex9().init(position);
            }else if(position == 9){
                return new complex10().init(position);
            }else if(position == 10){
                return new complex11().init(position);
            }else{
                return new complex12().init(position);
            }
        }

        @Override
        public int getCount() {
            return 12;  // 총 4개의 page 사용
        }

    }

}
