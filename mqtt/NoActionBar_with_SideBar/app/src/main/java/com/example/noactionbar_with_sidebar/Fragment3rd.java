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
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;

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

public class Fragment3rd extends Fragment {

    ArrayList<String> strList = new ArrayList();
    ArrayList<Integer> data = new ArrayList();
    ArrayList<ArrayList<Integer>> dataLists = new ArrayList();
    Integer _count = 0;
    Integer _count_val = 0;

    private class AutoEndTread extends Thread{
        Activity activity;
        HorizontalScrollView scrollView;
        boolean running;

        AutoEndTread(Activity activity, HorizontalScrollView horizontalScrollView)
        {
            this.activity = activity;
            this.scrollView = horizontalScrollView;
            running = true;
        }
        public void run() {
            while (running) {
                try {
                    Thread.sleep(10);
                    _count++;
                    if(_count >= 10){
                        scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        _count = 0;
                    }
                } catch (Exception ignore) {
                }
            }
        }
        public void cancel() {
            running = false;
        }
    }


    private class AutoAdd extends Thread{
        Activity activity;
        LineView lineView ;
        boolean running;
        AutoAdd(Activity activity, LineView lineView){
            this.activity = activity;
            this.lineView = lineView;
            running = true;
        }
        public void run() {
            while (running) {
                try {
                    Thread.sleep(10);
                    _count_val++;
                    if(_count_val >= 10){
                        _count_val = 0;
                    }
                } catch (Exception ignore) {
                }
            }
        }
        public void cancel() {
            running = false;
        }
    }



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_correction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");
        strList.add("f");
        strList.add("g");
        strList.add("h");
        data.add(8);
        data.add(4);
        data.add(8);
        data.add(4);
        data.add(4);
        data.add(8);
        data.add(4);
        data.add(4);
        strList.add("a");
        strList.add("b");
        strList.add("c");
        strList.add("d");
        strList.add("e");
        strList.add("f");
        strList.add("g");
        strList.add("h");
        data.add(8);
        data.add(4);
        data.add(8);
        data.add(4);
        data.add(4);
        data.add(8);
        data.add(4);
        data.add(4);

        dataLists.add(data);

        GridLayout layout = view.findViewById(R.id.layout_log);

        LineView lineView = view.findViewById(R.id.line_view);
        lineView.setDrawDotLine(false);
        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
        lineView.setBottomTextList(strList);
        lineView.setColorArray(new int[]{Color.GREEN});//,Color.GREEN,Color.GRAY,Color.CYAN
        lineView.setDataList(dataLists); //or lineView.setFloatDataList(floatDataLists)


        for(Integer i = 1; i <= 30; i++){
            //testLI
            LogItem logItem = new LogItem(getActivity());

            logItem.setChString("ch"+i);
            logItem.setValue(i+30);

            //add child
            layout.addView(logItem);
        }
        HorizontalScrollView horizontalScrollView = view.findViewById(R.id.graph_scroll);
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect rect = new Rect(horizontalScrollView.getLeft(), horizontalScrollView.getRight(),
                        horizontalScrollView.getTop(), horizontalScrollView.getBottom());
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        _count = 0;
                        break;
                    case MotionEvent.ACTION_MOVE  :
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            _count = 0;
                        }
                        break;
                    case MotionEvent.ACTION_UP   :
                        break;
                }
                return false;
            }
        });

        AutoEndTread autoEndTread = new AutoEndTread(getActivity(), horizontalScrollView);
        autoEndTread.start();
        //실행내용
    }

}
