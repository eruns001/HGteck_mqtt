package com.example.noactionbar_with_sidebar;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class LogItem extends ConstraintLayout//  implements View.OnClickListener
{
    String ch = "";
    float _value = 0;
    View view = null;
    //Button button = null;


    public LogItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init(context);
    }

    public LogItem(Context context)
    {
        super(context);

        init(context);
    }

    private void init(Context context)
    {
        LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.log_item,this,true);

        //button = view.findViewById(R.id.button_log);
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

    public void changeBackClick(){
        view.findViewById(R.id.log_item_layout).setBackgroundResource(R.color.logItemColorSelected);
    }
    public void changeBackUnClick(){
        view.findViewById(R.id.log_item_layout).setBackgroundResource(R.color.logItemColorUnSelected);
    }
}
