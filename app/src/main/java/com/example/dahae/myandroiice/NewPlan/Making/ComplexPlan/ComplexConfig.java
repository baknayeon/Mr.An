package com.example.dahae.myandroiice.NewPlan.Making.ComplexPlan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.dahae.myandroiice.R;

public class ComplexConfig extends Fragment {

    EditText planName;
    ListView triggerListView, actionListView;

    public static ComplexConfig init(int pageNumber) {
        ComplexConfig fragment = new ComplexConfig();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_complex_plan_config, null);
        planName = (EditText) view.findViewById(R.id.planNameInput);

        triggerListView = (ListView) view.findViewById(R.id.expandableListView);
        actionListView = (ListView) view.findViewById(R.id.actionListView);

        return view;
    }
}
