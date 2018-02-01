package com.example.dahae.myandroiice.NewPlan.ErrorCheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.TreeArrayAdapter;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-01-26.
 */

public class MainGrammarError extends AppCompatActivity {

    ViewPager mViewPager;
    ErrorPagerAdapter errorPagerAdapter;
    public errorPageOne mErrorPageOne;
    public errorPageTwo mErrorPageTwo;
    public errorPageThree mErrorPageThree;

    TextView errorPlanName;

    Keyword[] triggerArray;
    Keyword[] actionArray;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.error_grammer_check);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        errorPagerAdapter = new ErrorPagerAdapter(getSupportFragmentManager(), mErrorPageOne, mErrorPageTwo, mErrorPageThree);
        mViewPager.setAdapter(errorPagerAdapter);
        errorPlanName = (TextView) findViewById(R.id.textViewError);

        intent = getIntent();
        if(intent != null) {

            errorPlanName.setText(intent.getStringExtra("planName").toString());
            triggerArray = (Keyword[]) intent.getExtras().getSerializable("triggerArray");
            actionArray = (Keyword[]) intent.getExtras().getSerializable("actionArray");

        }
    //    mViewPager.addOnPageChangeListener(OnPageChangeListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_error, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_explain){}

        return super.onOptionsItemSelected(item);
    }

    public class ErrorPagerAdapter extends FragmentPagerAdapter {

        errorPageOne mErrorPageOne;
        errorPageTwo mErrorPageTwo;
        errorPageThree mErrorPageThree;

        public ErrorPagerAdapter(FragmentManager fm, errorPageOne errorPageOne, errorPageTwo errorPageTwo, errorPageThree errorPageThree) {
            super(fm);
            mErrorPageOne = errorPageOne;
            mErrorPageTwo = errorPageTwo;
            mErrorPageThree = errorPageThree;;
        }

        public ErrorPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public Fragment getItem(int position) {
            if (position == 0) {
                return mErrorPageOne.init(position);
            } else if(position == 1){
                return mErrorPageTwo.init(position);
            } else if(position == 2){
                return mErrorPageThree.init(position);
            }else
                return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }
}
