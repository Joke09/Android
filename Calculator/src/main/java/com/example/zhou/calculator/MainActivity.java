package com.example.zhou.calculator;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

class MyDouble{
    public double value;
    public static int precision;
    MyDouble()
    {
        value = 0;
    }
    @Override
    public String toString()
    {
        if((Math.abs(value) - Double.valueOf(Math.abs(value)).longValue()) < Math.pow(0.1, precision))
            return Long.valueOf(Double.valueOf(value).longValue()).toString();
        else
        {
            int i;
            for(i = precision; i > 0; i--)
                if(Math.round(value * Math.pow(10, i)) % 10 != 0)
                    break;
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(i);
            return nf.format(value);
        }
    }
}

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private MyDouble dLast = new MyDouble();
    private MyDouble dNow = new MyDouble();
    private MyDouble dResult = new MyDouble();
    private boolean bClear = false;
    private boolean bOper = false;
    private enum Operation {
        ADD, SUB, MUL, DIV,
    }
    private Operation eOper = Operation.ADD;
    private short sPoint = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      calculator button
        MyDouble.precision = 11;
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.button_0).setOnClickListener(this);
        findViewById(R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);
        findViewById(R.id.button_5).setOnClickListener(this);
        findViewById(R.id.button_6).setOnClickListener(this);
        findViewById(R.id.button_7).setOnClickListener(this);
        findViewById(R.id.button_8).setOnClickListener(this);
        findViewById(R.id.button_9).setOnClickListener(this);
        findViewById(R.id.button_point).setOnClickListener(this);
        findViewById(R.id.button_add).setOnClickListener(this);
        findViewById(R.id.button_sub).setOnClickListener(this);
        findViewById(R.id.button_mul).setOnClickListener(this);
        findViewById(R.id.button_div).setOnClickListener(this);
        findViewById(R.id.button_equ).setOnClickListener(this);
        findViewById(R.id.button_point).setOnClickListener(this);
        findViewById(R.id.button_backspace).setOnClickListener(this);
        findViewById(R.id.button_clean).setOnClickListener(this);
        findViewById(R.id.button_ce).setOnClickListener(this);
        findViewById(R.id.button_sign).setOnClickListener(this);
        findViewById(R.id.button_percent).setOnClickListener(this);
        findViewById(R.id.button_1divx).setOnClickListener(this);
        findViewById(R.id.button_square).setOnClickListener(this);
    }

    private void handleNumber(int num)
    {
        if(bClear)
        {
            dNow.value = 0;
            bClear = false;
            sPoint = -1;
        }
        if(sPoint == -1)dNow.value = dNow.value * 10 + num;
        else
        {
            sPoint++;
            dNow.value = dNow.value + num * Math.pow(0.1, sPoint);
        }
        textView.setText(dNow.toString());
    }
    private void getResult()
    {
        switch (eOper)
        {
            case ADD:
                dResult.value = dLast.value + dNow.value;
                break;
            case SUB:
                dResult.value = dLast.value - dNow.value;
                break;
            case MUL:
                dResult.value = dLast.value * dNow.value;
                break;
            case DIV:
                dResult.value = dLast.value / dNow.value;
                break;
            default:
                break;
        }
        dLast.value = dResult.value;
        textView.setText(dResult.toString());
        bClear = true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_0:
                handleNumber(0);
                break;
            case R.id.button_1:
                handleNumber(1);
                break;
            case R.id.button_2:
                handleNumber(2);
                break;
            case R.id.button_3:
                handleNumber(3);
                break;
            case R.id.button_4:
                handleNumber(4);
                break;
            case R.id.button_5:
                handleNumber(5);
                break;
            case R.id.button_6:
                handleNumber(6);
                break;
            case R.id.button_7:
                handleNumber(7);
                break;
            case R.id.button_8:
                handleNumber(8);
                break;
            case R.id.button_9:
                handleNumber(9);
                break;
            case R.id.button_add:
                Toast.makeText(this, "+", Toast.LENGTH_SHORT).show();
                if(bOper)
                    getResult();
                else if(!bClear)
                    dLast.value = dNow.value;
                eOper = Operation.ADD;
                bOper = true;
                bClear = true;
                break;
            case R.id.button_sub:
                Toast.makeText(this, "-", Toast.LENGTH_SHORT).show();
                if(bOper)
                    getResult();
                else if(!bClear)
                    dLast.value = dNow.value;
                eOper = Operation.SUB;
                bOper = true;
                bClear = true;
                break;
            case R.id.button_mul:
                Toast.makeText(this, "×", Toast.LENGTH_SHORT).show();
                if(bOper)
                    getResult();
                else if(!bClear)
                    dLast.value = dNow.value;
                eOper = Operation.MUL;
                bOper = true;
                bClear = true;
                break;
            case R.id.button_div:
                Toast.makeText(this, "÷", Toast.LENGTH_SHORT).show();
                if(bOper)
                    getResult();
                else if(!bClear)
                    dLast.value = dNow.value;
                eOper = Operation.DIV;
                bOper = true;
                bClear = true;
                break;
            case R.id.button_equ:
                Toast.makeText(this, "=", Toast.LENGTH_SHORT).show();
                bOper = false;
                getResult();
                break;
            case R.id.button_point:
                if(sPoint != -1)break;
                sPoint = 0;
                String str = dNow.toString() + ".";
                textView.setText(str);
                break;
            case R.id.button_backspace:
                if(bClear)handleNumber(0);
                if(sPoint == -1)
                    dNow.value = (long) (dNow.value / 10);
                else if(sPoint == 0)
                    sPoint = -1;
                else
                {
                    long lVaule = ((long) (dNow.value * Math.pow(10, sPoint))) / 10;
                    sPoint--;
                    dNow.value = lVaule / Math.pow(10, sPoint);
                }
                textView.setText(dNow.toString());
                break;
            case R.id.button_clean:
                dNow.value = 0;
                dResult.value = 0;
                dLast.value = 0;
                sPoint = -1;
                bOper = false;
                bClear = false;
                textView.setText(dNow.toString());
                break;
            case R.id.button_ce:
                if(bClear)handleNumber(0);
                bClear = false;
                dNow.value = 0;
                sPoint = -1;
                textView.setText(dNow.toString());
                break;
            case R.id.button_sign:
                if(bClear)handleNumber(0);
                bClear = false;
                dNow.value = -1 * dNow.value;
                textView.setText(dNow.toString());
                break;
            case R.id.button_percent:
                if(bClear)handleNumber(0);
                bClear = false;
                dNow.value = dNow.value / 100;
                textView.setText(dNow.toString());
                break;
            case R.id.button_1divx:
                if(bClear)handleNumber(0);
                bClear = false;
                dNow.value = 1 / dNow.value;
                textView.setText(dNow.toString());
                break;
            case R.id.button_square:
                if(bClear)handleNumber(0);
                bClear = false;
                dNow.value = Math.sqrt(dNow.value);
                textView.setText(dNow.toString());
                break;
            default:
                break;
        }
    }
}
