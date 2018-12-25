package com.uok.se.thisara.smart.trackerapplication.ui.driverui;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.uok.se.thisara.smart.trackerapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelDurationFragment extends Fragment {


    BarChart barChart;

    public TravelDurationFragment() {
        // Required empty public constructor
    }

    public static TravelDurationFragment newInstance() {
        TravelDurationFragment fragment = new TravelDurationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView =  inflater.inflate(R.layout.fragment_travel_duration, container, false);
        barChart = fragmentView.findViewById(R.id.barchartDuration);

        createBarChart();
        return fragmentView;
    }

    private void createBarChart() {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xl = barChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);
        xl.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }


        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

        });
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true
        barChart.getAxisRight().setEnabled(false);

        //data
        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset
        // (0.46 + 0.02) * 2 + 0.04 = 1.00 -> interval per "group"

        int startYear = 1;
        int endYear = 10;


        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();


        for (int i = startYear; i < endYear; i++) {
            yVals1.add(new BarEntry(i, 0.4f));
        }

        for (int i = startYear; i < endYear; i++) {
            yVals2.add(new BarEntry(i, 0.7f));
        }

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 45f));
        entries.add(new BarEntry(2f, 62f));
        entries.add(new BarEntry(3f, 43f));
        // gap of 2f
        entries.add(new BarEntry(5f, 22f));
        entries.add(new BarEntry(6f, 60f));

        List<BarEntry> yVList = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 45f));
        entries.add(new BarEntry(2f, 62f));
        entries.add(new BarEntry(3f, 43f));
        // gap of 2f
        entries.add(new BarEntry(5f, 22f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "Distance");

        BarDataSet set1, set2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            //set = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)barChart.getData().getDataSetByIndex(1);
            set.setValues(entries);
            set2.setValues(yVList);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            // create 2 datasets with different types
            set = new BarDataSet(entries, "Up");
            set.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVList, "Down");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList();
            dataSets.add(set);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            barChart.setData(data);
        }

        barChart.animateXY(2000, 2000);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinValue(startYear);
        barChart.groupBars(startYear, groupSpace, barSpace);
        barChart.invalidate();
    }
}
