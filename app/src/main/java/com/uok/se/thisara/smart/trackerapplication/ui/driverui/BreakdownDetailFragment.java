package com.uok.se.thisara.smart.trackerapplication.ui.driverui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uok.se.thisara.smart.trackerapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BreakdownDetailFragment extends Fragment {


    public BreakdownDetailFragment() {
        // Required empty public constructor
    }

    public static BreakdownDetailFragment newInstance() {
        BreakdownDetailFragment fragment = new BreakdownDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breakdown_detail, container, false);
    }

}
