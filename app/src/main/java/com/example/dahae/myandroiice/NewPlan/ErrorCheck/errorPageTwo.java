package com.example.dahae.myandroiice.NewPlan.ErrorCheck;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-02-04.
 */
public class errorPageTwo extends Fragment {

    TextView textView;
    ScrollView scrollView;
    ImageButton button;
    ImageButton button2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.error_page_two, null);

        scrollView = (ScrollView) layoutView.findViewById(R.id.scrollView);
        textView = (TextView) layoutView.findViewById(R.id.textView);
        button = (ImageButton)layoutView.findViewById(R.id.button);
        button2 = (ImageButton)layoutView.findViewById(R.id.button2);


        MainGrammarError mainGrammarError = (MainGrammarError) getActivity();
        String solutionMessage = new errorMessage(mainGrammarError.triggerArray, mainGrammarError.actionArray).wholeSolutionMessage();
        textView.setText(solutionMessage);
        button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  ((MainGrammarError) getActivity()).mViewPager.setCurrentItem(0);
              }
          }
        );
        button2.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         ((MainGrammarError) getActivity()).mViewPager.setCurrentItem(2);
                     }
                 }
        );
        return layoutView;
    }

    public static errorPageTwo init(int pageNumber) {
        errorPageTwo fragment = new errorPageTwo();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
