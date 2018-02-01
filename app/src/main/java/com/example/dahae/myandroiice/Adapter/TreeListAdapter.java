package com.example.dahae.myandroiice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dahae.myandroiice.R;

import java.util.ArrayList;

/**
 * Created by b_newyork on 2016-01-25.
 */
public class TreeListAdapter extends ArrayAdapter<Keyword> {

    ArrayList<Keyword> treeList;
    Context context;

    public TreeListAdapter(Context context, int textViewResourceId, ArrayList<Keyword> treeList) {
        super(context, textViewResourceId, treeList);
        this.context = context;
        this.treeList = treeList;

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

        if(!treeList.isEmpty()) {
            Keyword trees = treeList.get(position);
            holder.name.setText(trees.getKeyword());
            holder.name.setTag(trees);
//            if(trees.getKeywordInfo() != null) {
//                holder.info.setText(trees.getKeywordInfo());
//                holder.info.setTag(trees);
//            }
        }
        return convertView;
    }
}
