package com.example.noactionbar_with_sidebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CorrectionItem extends ConstraintLayout
{
    String ch = "";
    float _value = 0;
    View view = null;
    private Integer _type = 3;

    private void changeType(Integer type){
        _type = type;
    }

    public CorrectionItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init(context);
    }

    public CorrectionItem(Context context)
    {
        super(context);

        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.correction_item,this,true);

        TextView textView = view.findViewById(R.id.ch);
    }

    public void setChString(String str)
    {
        ch = str;
        TextView textView = view.findViewById(R.id.ch);
        textView.setText(ch);

    }

    public void setValue(float value)
    {
        _value = value;
        TextView textView = view.findViewById(R.id.value);
        textView.setText(_value+"°C");

    }
    public void changeView(Context context)
    {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ConstraintLayout frame = (ConstraintLayout) findViewById(R.id.constraintLayout_item) ;
        if (frame.getChildCount() > 0) {
            // FrameLayout에서 뷰 삭제.
            frame.removeViewAt(0);
        }
        if(_type == 1){
            view = inflater.inflate(R.layout.correction_item,this,true);
            changeType(3);
        }
        if(_type == 3){
            view = inflater.inflate(R.layout.live_item,this,true);
            changeType(1);
        }
    }


}
