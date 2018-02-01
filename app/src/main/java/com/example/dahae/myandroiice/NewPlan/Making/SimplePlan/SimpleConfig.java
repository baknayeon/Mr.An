package com.example.dahae.myandroiice.NewPlan.Making.SimplePlan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dahae.myandroiice.R;

public class SimpleConfig extends Fragment {

    EditText planName;

    public static SimpleConfig init(int pageNumber) {
        SimpleConfig fragment2 = new SimpleConfig();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment2.setArguments(args);
        return fragment2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.new_simple_plan_config, null);
        planName = (EditText) view.findViewById(R.id.planNameInput);

        return view;
    }

}
