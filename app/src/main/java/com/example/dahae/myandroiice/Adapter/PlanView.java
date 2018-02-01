package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class PlanView extends LinearLayout {

    public TextView name;
    public CheckBox checkBox;
    public ImageButton button;

    public PlanView(Context context) {
        super(context);
        init(context);
    }

    public void init (Context context){

        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi.inflate(R.layout.item_plans, this, true);

        name = (TextView)findViewById(R.id.planItem);
        checkBox = (CheckBox) findViewById(R.id.cb_box);
        button = (ImageButton) findViewById(R.id.imageButton);
    }
}
