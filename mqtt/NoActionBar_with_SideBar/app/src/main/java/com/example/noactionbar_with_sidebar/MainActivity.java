package com.example.noactionbar_with_sidebar;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    boolean mainIsVisible = true;
    ScreenChangeThread m_screenChangeThread;

    //몇초 지나면 메인 화면?
    int to_main_sec = 8;
    //반응 없을때 1초에 1씩 증가
    int screenCount  = 0;

    public static Integer logItemIndex = 1;

    ImageButton side1st;
    ImageButton side2nd;
    ImageButton side3rd;

    @Override
    protected void onDestroy() {
        m_screenChangeThread.cancel();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_screenChangeThread = new ScreenChangeThread(this);
        m_screenChangeThread.start();
    }

    @Override
    protected void onStop() {
        if (!mainIsVisible) {
            findViewById(R.id.content_main).setVisibility(View.VISIBLE);
            mainIsVisible = true;
        }
        super.onStop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        screenCount = 0;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    private class ScreenChangeThread extends Thread
    {
        Activity activity;
        boolean running;

        ScreenChangeThread(Activity activity)
        {
            this.activity = activity;
            running = true;
        }

        public void run() {
            while (running) {
                try {
                    Thread.sleep(1000);
                    if(screenCount >= to_main_sec && !mainIsVisible) {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                findViewById(R.id.content_main).setVisibility(View.VISIBLE);
//                                mainIsVisible = true;
//                            }
//                        });
                        screenCount = 0;
                    }
                    else if(mainIsVisible) {
                        //아무것도 하지않음
                        //super.onBackPressed();
                    }
                    else {
                        screenCount ++;
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
    public void onBackPressed() {
        if(mainIsVisible){
            super.onBackPressed();
        }
        else {
            findViewById(R.id.content_main).setVisibility(View.VISIBLE);
            mainIsVisible = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ImageButton main1st = findViewById(R.id.mainBtnLiveMonitoring);
        ImageButton main2nd = findViewById(R.id.mainBtnLogMonitoring);
        ImageButton main3rd = findViewById(R.id.mainBtnCorrectionMonitoring);

        side1st = findViewById(R.id.side_to_live_monitoring);
        side2nd = findViewById(R.id.side_to_log_monitoring);
        side3rd = findViewById(R.id.side_to_correction_monitoring);


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_view, new Fragment1st());
        fragmentTransaction.commit();


        View flMain = findViewById(R.id.content_main);
        flMain.setVisibility(View.VISIBLE);

        main1st.setOnClickListener(v -> {
            side1st.setBackgroundResource(R.color.sideSelected);
            side2nd.setBackgroundResource(R.color.sideBack);
            side3rd.setBackgroundResource(R.color.sideBack);
            mainIsVisible = false;
            to1stFragment();
            flMain.setVisibility(View.GONE);
        });
        main2nd.setOnClickListener(v -> {
            side1st.setBackgroundResource(R.color.sideBack);
            side2nd.setBackgroundResource(R.color.sideSelected);
            side3rd.setBackgroundResource(R.color.sideBack);
            mainIsVisible = false;
            to2ndFragment();
            flMain.setVisibility(View.GONE);
        });
        main3rd.setOnClickListener(v -> {
            side1st.setBackgroundResource(R.color.sideBack);
            side2nd.setBackgroundResource(R.color.sideBack);
            side3rd.setBackgroundResource(R.color.sideSelected);
            mainIsVisible = false;
            to3rdFragment();
            flMain.setVisibility(View.GONE);
        });

        side1st.setOnClickListener(v -> {
            to1stFragment();
            side1st.setBackgroundResource(R.color.sideSelected);
            side2nd.setBackgroundResource(R.color.sideBack);
            side3rd.setBackgroundResource(R.color.sideBack);
        });
        side2nd.setOnClickListener(v -> {
            to2ndFragment();
            side1st.setBackgroundResource(R.color.sideBack);
            side2nd.setBackgroundResource(R.color.sideSelected);
            side3rd.setBackgroundResource(R.color.sideBack);
        });
        side3rd.setOnClickListener(v -> {
            to3rdFragment();
            side1st.setBackgroundResource(R.color.sideBack);
            side2nd.setBackgroundResource(R.color.sideBack);
            side3rd.setBackgroundResource(R.color.sideSelected);
        });
    }

    public void to1stFragment() {
        Fragment fr = new Fragment1st();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void to2ndFragment() {
        Fragment fr = new Fragment2nd();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void to3rdFragment() {
        Fragment fr = new Fragment3rd();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void to4thFragment() {
        Fragment fr = new Fragment4th();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_view, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}