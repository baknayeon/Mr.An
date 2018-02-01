package com.example.dahae.myandroiice.Triggers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.ContactList;
import com.example.dahae.myandroiice.R;

public class TriggerForPhoneReception extends Activity {

    EditText phonNumText;
    TextView nameText;
    Button butSave;
    Button butlist;

    public static final int SUCCESS = 1;
    public static final String SELECTED_PHONE = "selectedphone";
    public static final String SELECTED_NAME = "selectedname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_phonenum);

        phonNumText = (EditText) findViewById(R.id.editText1);
        nameText = (TextView) findViewById(R.id.textViewSyntaxAction);
        butSave = (Button) findViewById(R.id.buttonSave);
        butlist = (Button) findViewById(R.id.buttonIntent);

        butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = phonNumText.getText().toString();

                Intent intent = getIntent();
                intent.setAction("PhoneReception");
                intent.putExtra("mTriggerInfo", data);
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        butlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactlist();
            }
        });


    }

    private void showContactlist() {
        Intent intent = new Intent(this,
                ContactList.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivityForResult(intent, 10001);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            if (resultCode == SUCCESS) {
                phonNumText.setText(data.getStringExtra(SELECTED_PHONE));
                nameText.setText(data.getStringExtra(SELECTED_NAME));
            } else {
                phonNumText.setText("");
            }
        }
    }
}