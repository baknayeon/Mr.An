package com.example.dahae.myandroiice.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dahae.myandroiice.Information.Anbisa.infomationOfAnbisa;
import com.example.dahae.myandroiice.Information.ComplexPlan.informationOfComplexPlan;
import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-02-03.
 */
public class menuOfInformation extends AppCompatActivity {

    Button buttonAnbisa, buttonComplex, buttonSimple, buttonGrammer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_main);

        buttonAnbisa = (Button)findViewById(R.id.button);
        buttonComplex = (Button)findViewById(R.id.button2);
        buttonSimple = (Button)findViewById(R.id.button3);
        buttonGrammer = (Button)findViewById(R.id.button4);

        buttonAnbisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), infomationOfAnbisa.class));
            }
        });

        buttonComplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), informationOfComplexPlan.class));
            }
        });

        buttonGrammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buttonSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        buttonGrammer.setText("부탁만들기\n" + "규칙");
        buttonGrammer.setTextSize(18);

        buttonSimple.setText("간단한 부탁\n" + "하는방법");
        buttonSimple.setTextSize(15);

        buttonComplex.setText("복잡한 부탁\n" + "하는방법");
        buttonComplex.setTextSize(15);

    }
}
