package com.example.dahae.myandroiice.NewPlan.ErrorCheck;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-02-04.
 */
public class errorPageThree extends Fragment {

    ScrollView scrollView;
    ImageButton button;

    String solution;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.error_page_three, null);

        scrollView = (ScrollView) layoutView.findViewById(R.id.scrollView);
        button= (ImageButton)layoutView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          ((MainGrammarError) getActivity()).mViewPager.setCurrentItem(1);
                                      }
                                  }
        );
        return layoutView;
    }

    public static errorPageThree init(int pageNumber) {
        errorPageThree fragment = new errorPageThree();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

}
