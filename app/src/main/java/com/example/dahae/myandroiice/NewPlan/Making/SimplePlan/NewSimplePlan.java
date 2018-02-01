package com.example.dahae.myandroiice.NewPlan.Making.SimplePlan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.CheckGrammer.NewPlanCheckAsSemantic;
import com.example.dahae.myandroiice.CheckGrammer.NewPlanCheckAsSyntax;
import com.example.dahae.myandroiice.Information.informationOfSimplePlan;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.NewPlan.SavingPlan;
import com.example.dahae.myandroiice.NewPlan.ShowingDB;
import com.example.dahae.myandroiice.NewPlan.ErrorCheck.MainGrammarError;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Triggers.MapReceive;

import java.util.ArrayList;
import java.util.List;

public class NewSimplePlan extends AppCompatActivity {

    ViewPager mViewPager;
    SimplePagerAdapter simplePagerAdapter;

    public SimpleTrigger newPlanTrigger;
    public SimpleAction newPlanAction;
    public SimpleConfig newPlanConfig;

    String mPlanName;
    List<Keyword> listTrigger;
    List<Keyword> listAction;

    LocationManager mLocationManager;
    MapReceive mIntentReceiver;
    String intentKey = "locationProximity";
    ArrayList mPendingIntentList;

    TextView triggerText, actionText;

    Intent intent;
    Intent ServiceIntent;

    Button buttonTrigger;
    Button buttonAction;
    Button buttonConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_simple_plan_making);

        buttonTrigger = (Button) findViewById(R.id.buttonTrigger);
        buttonAction = (Button) findViewById(R.id.buttonAction);
        buttonConfig = (Button) findViewById(R.id.buttonConfig);

        buttonTrigger.setOnClickListener(onClickListener);
        buttonAction.setOnClickListener(onClickListener);
        buttonConfig.setOnClickListener(onClickListener);

        listTrigger = new ArrayList<Keyword>();
        listAction = new ArrayList<Keyword>();

        mViewPager = (ViewPager) findViewById(R.id.pager);
        simplePagerAdapter = new SimplePagerAdapter(getSupportFragmentManager(), newPlanTrigger,  newPlanAction, newPlanConfig);
        mViewPager.setAdapter(simplePagerAdapter);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mPendingIntentList = new ArrayList();
        mIntentReceiver = new MapReceive(intentKey);

        triggerText = (TextView) findViewById(R.id.textViewOfTrigger);
        actionText = (TextView) findViewById(R.id.textViewOfAction);

        buttonTrigger.setBackgroundColor(Color.parseColor("#FFF5D08E"));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    buttonTrigger.setBackgroundColor(Color.parseColor("#FFF5D08E"));
                    buttonAction.setBackgroundColor(Color.parseColor("#FFEBBD6C"));
                    buttonConfig.setBackgroundColor(Color.parseColor("#FFEBBD6C"));
                } else if (position == 1) {
                    buttonAction.setBackgroundColor(Color.parseColor("#FFF5D08E"));
                    buttonTrigger.setBackgroundColor(Color.parseColor("#FFEBBD6C"));
                    buttonConfig.setBackgroundColor(Color.parseColor("#FFEBBD6C"));

                } else if (position == 2) {
                    buttonConfig.setBackgroundColor(Color.parseColor("#FFF5D08E"));
                    buttonTrigger.setBackgroundColor(Color.parseColor("#FFEBBD6C"));
                    buttonAction.setBackgroundColor(Color.parseColor("#FFEBBD6C"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonTrigger){
                mViewPager.setCurrentItem(0); }
            else if(v.getId()==R.id.buttonAction){
                mViewPager.setCurrentItem(1); }
            else if(v.getId()==R.id.buttonConfig){
                mViewPager.setCurrentItem(2); }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.action_save){
           if(simplePagerAdapter.simpleConfig.planName != null) {
               mPlanName = simplePagerAdapter.simpleConfig.planName.getText().toString();

               Keyword[] triggerArray = listTrigger.toArray(new Keyword[listTrigger.size()]);
               Keyword[] actionArray = listAction.toArray(new Keyword[listAction.size()]);

               intent = new Intent(getApplicationContext(), MainGrammarError.class);
               intent.putExtra("planName", mPlanName);
               intent.putExtra("triggerArray", triggerArray);
               intent.putExtra("actionArray", actionArray);

               NewPlanCheckAsSyntax PlanAsSyntax = new NewPlanCheckAsSyntax(triggerArray, actionArray);
               NewPlanCheckAsSemantic PlanAsSemantic = new NewPlanCheckAsSemantic(triggerArray, actionArray);

               int checkName = PlanAsSyntax.NoSameName(mPlanName);
               if (checkName == 1) {
                   if (PlanAsSyntax.check()) {
                       if (PlanAsSemantic.check()) {

                           new SavingPlan(getApplicationContext()).saveNewDatabase(mPlanName, listTrigger, listAction);
                           new ShowingDB().seeInfoOfPlan();

                           Toast.makeText(getApplicationContext(), "서비스를 시작합니다.", Toast.LENGTH_LONG).show();
                           ServiceIntent = new Intent(getApplicationContext(), MyTrigger.class);
                           startService(ServiceIntent);
                           finish();
                       } else {
                           intent.putExtra("Error", "semantic");
                           startActivity(intent);
                       }
                   } else {
                       intent.putExtra("Error", "syntax");
                       startActivity(intent);
                   }

               } else if (checkName == 0) {
                   mPlanName = "";
                   Toast.makeText(getApplicationContext(), "같은 이름의 부탁이 있습니다.", Toast.LENGTH_LONG).show();
               } else if (checkName == -1) {
                   mPlanName = "";
                   Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_LONG).show();
               }
           }

       }else if (id == R.id.action_cancel){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
       }else if( id == R.id.action_explain){
           startActivity(new Intent(getApplicationContext(), informationOfSimplePlan.class));
       }
        return super.onOptionsItemSelected(item);
    }

}
