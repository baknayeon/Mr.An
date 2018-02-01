package com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.dahae.myandroiice.Adapter.Keyword;
import com.example.dahae.myandroiice.Adapter.TreeListAdapter;
import com.example.dahae.myandroiice.CheckGrammer.NewPlanCheckAsSemantic;
import com.example.dahae.myandroiice.CheckGrammer.NewPlanCheckAsSyntax;
import com.example.dahae.myandroiice.Information.ComplexPlan.informationOfComplexPlan;
import com.example.dahae.myandroiice.MainFunction.MyTrigger;
import com.example.dahae.myandroiice.NewPlan.ErrorCheck.errorMessage;
import com.example.dahae.myandroiice.NewPlan.SavingPlan;
import com.example.dahae.myandroiice.NewPlan.ShowingDB;
import com.example.dahae.myandroiice.NewPlan.ErrorCheck.MainGrammarError;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class NewComplexPlan  extends AppCompatActivity {

    ViewPager mViewPager;
    ComPlexPagerAdapter mComPlexPagerAdapter;

    public ComplexTrigger complexTrigger;
    public ComplexAction complexAction;
    public ComplexConfig complexConfig;

    String mPlanName;
    List<Keyword> listTrigger;
    List<Keyword> listAction;

    ArrayList<Keyword> triggerTree;
    ArrayList<Keyword> actionTree;

    Intent intent;
    Intent ServiceIntent;

    Button buttonTrigger;
    Button buttonAction;
    Button buttonConfig;

    AlertDialog.Builder builderConfig;
    AlertDialog.Builder builderTrigger;
    AlertDialog.Builder builderAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_complex_plan_making);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mComPlexPagerAdapter = new ComPlexPagerAdapter(getSupportFragmentManager(), complexTrigger, complexAction, complexConfig);
        mViewPager.setAdapter(mComPlexPagerAdapter);

        buttonTrigger = (Button) findViewById(R.id.buttonTrigger);
        buttonAction = (Button) findViewById(R.id.buttonAction);
        buttonConfig = (Button) findViewById(R.id.buttonConfig);

        buttonTrigger.setOnClickListener(onClickListener);
        buttonAction.setOnClickListener(onClickListener);
        buttonConfig.setOnClickListener(onClickListener);
        mViewPager.addOnPageChangeListener(onPageChangeListener);

        buttonTrigger.setBackgroundColor(Color.parseColor("#FFAAE2DF"));

        listTrigger = new ArrayList<Keyword>();
        listAction = new ArrayList<Keyword>();

        triggerTree = new ArrayList<Keyword>();
        actionTree = new ArrayList<Keyword>();

        builderConfig = new AlertDialog.Builder(this);
        builderTrigger = new AlertDialog.Builder(this);
        builderAction = new AlertDialog.Builder(this);

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        int positionChange = -1;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(MainActivity.TAG, "alert");
            if(position == 0)
                positionChange =0;
            if(position == 1)
                positionChange = 1;
            if(position == 2)
                positionChange = 2;
        }

        @Override
        public void onPageSelected(int position) {

            if(positionChange == 0 && position == 1){
                Log.d(MainActivity.TAG, "alertTrigger");
                alertTrigger();
            }
            if(positionChange == 1 && position == 2) {
                Log.d(MainActivity.TAG, "alertAction");
                alertAction();
            }

            if (position == 0) {
                buttonTrigger.setBackgroundColor(Color.parseColor("#FFAAE2DF"));
                buttonAction.setBackgroundColor(Color.parseColor("#FF82B9B6"));
                buttonConfig.setBackgroundColor(Color.parseColor("#FF82B9B6"));
            } else if (position == 1) {

                buttonAction.setBackgroundColor(Color.parseColor("#FFAAE2DF"));
                buttonTrigger.setBackgroundColor(Color.parseColor("#FF82B9B6"));
                buttonConfig.setBackgroundColor(Color.parseColor("#FF82B9B6"));

            } else if (position == 2) {

                buttonConfig.setBackgroundColor(Color.parseColor("#FFAAE2DF"));
                buttonTrigger.setBackgroundColor(Color.parseColor("#FF82B9B6"));
                buttonAction.setBackgroundColor(Color.parseColor("#FF82B9B6"));

                TreeListAdapter triggerAdapter  = new TreeListAdapter(getApplicationContext(), R.layout.item_tree, triggerTree);
                mComPlexPagerAdapter.complexConfig.triggerListView.setAdapter(triggerAdapter);
                TreeListAdapter actionAdapter  = new TreeListAdapter(getApplicationContext(), R.layout.item_tree, actionTree);
                mComPlexPagerAdapter.complexConfig.actionListView.setAdapter(actionAdapter);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

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

    public double alertTrigger(){

        Keyword[] triggerArray = listTrigger.toArray(new Keyword[listTrigger.size()]);
        NewPlanCheckAsSyntax PlanAsSyntax = new NewPlanCheckAsSyntax(triggerArray,  null);
        double result = PlanAsSyntax.Trigger();

        if(result != 1) {
            errorMessage message = new errorMessage(triggerArray, null);

            builderTrigger.setTitle("상황문에 오류가 있습니다").setIcon(R.drawable.anbisa_head_sadness).setNeutralButton("도움말", onNeutralClickListener);
            builderTrigger.setPositiveButton("확인", Triggerbutten);
            builderTrigger.setMessage(message.solutionSyntaxTrigger());

            AlertDialog alert = builderTrigger.create();
            alert.show();

        }
        return result;
    }

    public double alertAction(){

        Keyword[] actionArray = listAction.toArray(new Keyword[listAction.size()]);
        NewPlanCheckAsSyntax PlanAsSyntax = new NewPlanCheckAsSyntax(null, actionArray);
        double result = PlanAsSyntax.Action();

        if(result != 1) {
            errorMessage message = new errorMessage(null, actionArray);

            builderAction.setTitle("상황문에 오류가 있습니다").setIcon(R.drawable.anbisa_head_sadness).setNeutralButton("도움말", onNeutralClickListener);
            builderAction.setPositiveButton("확인", Actionbutten);
            builderAction.setMessage(message.solutionSyntaxAction());

            AlertDialog alert = builderAction.create();
            alert.show();

        }

        return result;
    }

    DialogInterface.OnClickListener Triggerbutten = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mViewPager.setCurrentItem(0);
            return;
        }
    };

    DialogInterface.OnClickListener Actionbutten = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mViewPager.setCurrentItem(1);
            return;
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

        int id = item.getItemId();

        if (id == R.id.action_save){
            if(mComPlexPagerAdapter.complexConfig.planName != null) {
                mPlanName = mComPlexPagerAdapter.complexConfig.planName.getText().toString();

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
                            errorIntent();
                        }
                    } else {
                        errorIntent();
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
            startActivity(new Intent(getApplicationContext(), informationOfComplexPlan.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void errorIntent(){

        Keyword[] triggerArray = listTrigger.toArray(new Keyword[listTrigger.size()]);
        Keyword[] actionArray = listAction.toArray(new Keyword[listAction.size()]);

        errorMessage message = new errorMessage(triggerArray, actionArray);

        builderConfig.setTitle("안비서가 들어줄수 없는 부탁입니다:(").setIcon(R.drawable.anbisa_head_sadness).setNeutralButton("도움말", onNeutralClickListener);
        builderConfig.setPositiveButton("확인", onPositivClickListener);
        builderConfig.setMessage(message.wholeErrorMessage());

        AlertDialog alert = builderConfig.create();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = 20;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        alert.getWindow().setAttributes(params);
        alert.show();
    }

    DialogInterface.OnClickListener onNeutralClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Keyword[] triggerArray = listTrigger.toArray(new Keyword[listTrigger.size()]);
            Keyword[] actionArray = listAction.toArray(new Keyword[listAction.size()]);

            intent = new Intent(getApplicationContext(), MainGrammarError.class);
            intent.putExtra("planName", mPlanName);
            intent.putExtra("triggerArray", triggerArray);
            intent.putExtra("actionArray", actionArray);

            startActivity(intent);
        }
    };

    DialogInterface.OnClickListener onPositivClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            return ;
        }
    };


}
