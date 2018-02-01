package com.example.dahae.myandroiice.NewPlan.ErrorCheck;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dahae.myandroiice.Adapter.TreeArrayAdapter;
import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-02-04.
 */
public class errorPageOne extends Fragment {

    TextView textView;
    ScrollView scrollView;
    ImageButton button;
    ListView triggerListView;
    ListView actionListView;

    TreeArrayAdapter triggerAdapter;
    TreeArrayAdapter actionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.error_page_one, null);

        scrollView = (ScrollView) layoutView.findViewById(R.id.scrollView);
        textView = (TextView) layoutView.findViewById(R.id.textView);
        button= (ImageButton)layoutView.findViewById(R.id.button);
        triggerListView = (ListView)layoutView.findViewById(R.id.triggerListView);
        actionListView = (ListView)layoutView.findViewById(R.id.actionListView);

        MainGrammarError mainGrammarError = (MainGrammarError) getActivity();
        String errorMessage = new errorMessage(mainGrammarError.triggerArray, mainGrammarError.actionArray).wholeErrorMessage();
        textView.setText(errorMessage);

        triggerAdapter  = new TreeArrayAdapter(getActivity(), R.layout.item_tree, mainGrammarError.triggerArray);
        actionAdapter  = new TreeArrayAdapter(getActivity(), R.layout.item_tree, mainGrammarError.actionArray );
        triggerListView.setAdapter(triggerAdapter);
        actionListView.setAdapter(actionAdapter);
        show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainGrammarError) getActivity()).mViewPager.setCurrentItem(1);
            }
        });
        return layoutView;
    }

    public static errorPageOne init(int pageNumber) {
        errorPageOne fragment = new errorPageOne();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private void show(){
        MainGrammarError mainGrammarError = (MainGrammarError) getActivity();
        for(int i =0 ; i <mainGrammarError.triggerArray.length; i++)
            Log.d(MainActivity.TAG, "t: " + mainGrammarError.triggerArray[i].getKeyword());
        for(int i =0; i< mainGrammarError.actionArray.length; i++)
            Log.d(MainActivity.TAG, "a: " + mainGrammarError.actionArray[i].getKeyword());
    }

}
