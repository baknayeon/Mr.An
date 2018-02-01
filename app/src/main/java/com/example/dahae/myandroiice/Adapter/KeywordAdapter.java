package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class KeywordAdapter extends BaseAdapter{

    public List<Keyword> items  = new ArrayList<>();
    private Context context;

    public KeywordAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void addItem(Keyword item){
        items.add(item);
    }

    public void deleteItem(int position){
        items.remove(position);
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        KeywordView view = null;
        if(convertView == null){
            view = new KeywordView(context);
        } else {
            view = (KeywordView) convertView;
        }

        Keyword curItem = items.get(position);

        view.setKeyword(curItem.getKeyword());
        view.setKeywordInfo(curItem.getKeywordInfo());

        return view;
    }

}
