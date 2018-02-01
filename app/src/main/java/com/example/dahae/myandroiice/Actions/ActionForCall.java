package com.example.dahae.myandroiice.Actions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;
import com.example.dahae.myandroiice.Adapter.ContactList;

public class ActionForCall extends AppCompatActivity {

    TextView textView;
    EditText editText;

    public static final int SUCCESS = 1;
    public static final String SELECTED_PHONE = "selectedphone";
    public static final String SELECTED_NAME = "selectedname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trigger_phonenum);
        textView = (TextView) findViewById(R.id.textViewSyntaxAction);
        editText = (EditText) findViewById(R.id.editText1);

    }

    public void onButton1Clicked(View v) {
        // 입력상자에 입력한 전화번호를 가져옴
        String data = editText.getText().toString();

        Log.d(MainActivity.TAG, "Call Num " + data);

        Intent intent = getIntent();
        intent.setAction("Call");
        intent.putExtra("mActionInfo", data);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void onButton2Clicked(View v) {
        showContactlist();
    }

    private void showContactlist() {
        Intent intent = new Intent(this,
                ContactList.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivityForResult(intent, 10001);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001) {
            if (resultCode == SUCCESS) {
                editText.setText(data.getStringExtra(SELECTED_PHONE));
                textView.setText(data.getStringExtra(SELECTED_NAME));
            } else {
                editText.setText("");
            }
        }
    }

}
