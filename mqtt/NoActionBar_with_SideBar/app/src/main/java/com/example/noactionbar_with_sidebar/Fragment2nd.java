package com.example.noactionbar_with_sidebar;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;

import static com.example.noactionbar_with_sidebar.MainActivity.logItemIndex;

public class Fragment2nd extends Fragment {


    static boolean boolExpedient = true;
    final float zoom1 = 0.9f;
    final float zoom2 = 1.0f;

    static LogItem logItemNow;

    GridLayout layout;
    private LineChart chart;

    static ArrayList<Entry> dataList = new ArrayList<>();
    static ArrayList<ArrayList> dataListArray = new ArrayList<ArrayList>(){{
        for(Integer i = 1; i <= 30; i++){
            add(new ArrayList<>());
        }
    }};
    static ArrayList<LogItem> logItems = new ArrayList<LogItem>(30);

    ScrollView scrollView;

    //logItem 하나하나 index
    static Integer dataListLength = 0;
    //보고있는 logItem의 index
    static Integer logItemNum = 1;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layout = view.findViewById(R.id.layout_correction);
        chart = view.findViewById(R.id.chart);

        //init
        dataListArray.set(logItemNum-1, dataList);
        dataList = dataListArray.get(logItemIndex-1);
        dataListLength = dataList.size();
        logItemNum = logItemIndex;
        setArray();
        scrollView = (ScrollView) view.findViewById(R.id.scrollview_chart);

        //우측 logItemList
        for(Integer i = 1; i <= 30; i++){
            //testLI
            LogItem logItem = new LogItem(getActivity());
            final Integer logValue = i;

            logItem.setChString("ch"+i);
            logItem.setValue(i+30);
            if(logItemIndex == i){
                logItemNow = logItem;
                logItemNow.changeBackClick();
            }
            logItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logItemNow.changeBackUnClick();
                    dataListArray.set(logItemNum-1, dataList);
                    dataList = dataListArray.get(logValue-1);
                    logItemNum = logValue;
                    logItemIndex = logValue;
                    logItemNow = logItem;
                    logItemNow.changeBackClick();
                    dataListLength = dataList.size();
                    Log.d("ARARARARA", "onClick: "+ logValue.toString());
                    setArray();
                    if(boolExpedient){
                        chart.zoom(zoom1, 1, 10, 10);
                    }
                    else {
                        chart.zoom(zoom2, 1, 10, 10);
                    }
                    boolExpedient = !boolExpedient;
                    chart.notifyDataSetChanged();
                }
            });

            //add child
            layout.addView(logItem);
        }

        scrollView.post(new Runnable() {
            public void run() {
                scrollView.scrollTo(0, logItemNow.getTop());
            }
        });


        FloatingActionButton fab = view.findViewById(R.id.button_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float val = (float) (Math.random() * (30 / 2f)) + 50;
                setData(val);
                dataListLength += 1;
                if(boolExpedient){
                    chart.zoom(zoom1, 1, 10, 10);
                }
                else {
                    chart.zoom(zoom2, 1, 10, 10);
                }
                boolExpedient = !boolExpedient;
                chart.notifyDataSetChanged();
            }
        });


        chart.setDoubleTapToZoomEnabled(false);
        //lineChart.setPinchZoom(true);
        chart.setScaleXEnabled(false);
        chart.setScaleYEnabled(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setClipValuesToContent(true);
        chart.setHighlightPerDragEnabled(true);
        //lineChart.
        //lineChart.setClipValuesToContent(true);


        //실행내용
    }

    private void setArray(){
        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(dataList);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(dataList, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            Legend l = chart.getLegend();
            l.setTextColor(Color.BLUE);

            // create a data object with the data sets
            LineData data = new LineData(set1);//, set2, set3
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }

    private void setData(float item) {

        dataList.add(new Entry(dataList.size(), item));
        Log.d("setData", "setData: "+ dataList);

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(dataList);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(dataList, "DataSet 1");

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            Legend l = chart.getLegend();
            l.setTextColor(Color.BLUE);

            // create a data object with the data sets
            LineData data = new LineData(set1);//, set2, set3
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }

}

