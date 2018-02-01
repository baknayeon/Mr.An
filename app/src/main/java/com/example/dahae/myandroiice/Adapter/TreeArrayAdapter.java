package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-01-25.
 */
public class TreeArrayAdapter extends ArrayAdapter<Keyword> {

    Keyword[] treeArray;
    Context context;

    public TreeArrayAdapter(Context context, int textViewResourceId, Keyword[] treeArray) {
        super(context, textViewResourceId, treeArray);
        this.context = context;
        this.treeArray = treeArray;

    }

    private class ViewHolder {
        TextView name;
        TextView info;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.item_tree, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.treeItem);
            holder.info = (TextView) convertView.findViewById(R.id.treeItemInfo);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(treeArray.length != 0) {
            holder.name.setText(treeArray[position].keyword);
            holder.name.setTag(treeArray);
//            if(treeArray[position].keywordInfo != null) {
//                holder.info.setText(treeArray[position].keywordInfo);
//                holder.info.setTag(treeArray);
//            }
        }
        return convertView;
    }
}
