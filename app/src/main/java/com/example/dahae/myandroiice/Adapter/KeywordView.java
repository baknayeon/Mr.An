package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

public class KeywordView extends LinearLayout {

    TextView keyword;
    TextView keywordInfo;

    public KeywordView(Context context) {
        super(context);
        init(context);
    }

    public KeywordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init (Context context){
        LayoutInflater inflater2 = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater2.inflate(R.layout.item_keyword, this, true);
        keyword = (TextView) findViewById(R.id.Keyword);
        keywordInfo = (TextView) findViewById(R.id.KeywordInfo);
    }

   public void setKeyword(String keyword) {
       this.keyword.setText(keyword);
   }
    public void setKeywordInfo(String keywordInfo) {
        this.keywordInfo.setText(keywordInfo);
    }

}
