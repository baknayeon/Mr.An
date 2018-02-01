package com.example.dahae.myandroiice.NewPlan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan.NewComplexPlan;
import com.example.dahae.myandroiice.NewPlan.Making.SimplePlan.NewSimplePlan;
import com.example.dahae.myandroiice.R;

public class NewPlanMenu extends AppCompatActivity {

    Button complex, simple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplan_making);

        complex = (Button) findViewById(R.id.ButtonComplex);
        simple = (Button) findViewById(R.id.ButtonSimple);

        complex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewComplexPlan.class);
                startActivity(intent);
                finish();
            }
        });

        simple.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(getApplicationContext(), NewSimplePlan.class);
                  startActivity(intent);
                  finish();
              }
          }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        }
        else if (id == R.id.action_cancel){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
            //만들던 플랜 취소! -> 한꺼번에 저장 안하다가 저장 버튼 누르면 저장해도 되고, 누르는대로 저장하다가 X눌렀을때 지우는 방법도???
        }
        return super.onOptionsItemSelected(item);
    }


}
