package com.example.dahae.myandroiice.Information.Anbisa;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.dahae.myandroiice.R;

public class infomationOfAnbisa extends ActionBarActivity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    Handler handler;
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                return new anbisa1().init(position);
            } else if(position == 1){
                return new anbisa2().init(position);
            } else if(position == 2){
                return new anbisa3().init(position);
            }else if(position == 3){
                return new anbisa4().init(position);
            }else if(position == 4){
                return new anbisa5().init(position);
            }else if(position == 5){
                return new anbisa6().init(position);
            }else if(position == 6){
                return new anbisa7().init(position);
            }else if(position == 7){
                return new anbisa8().init(position);
            }else if(position == 8){
                return new anbisa9().init(position);
            }else {
                return new anbisa10().init(position);
            }
        }

        @Override
        public int getCount() {
            return 10;  // 총 4개의 page 사용
        }

    }
}
