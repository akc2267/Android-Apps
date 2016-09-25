package com.example.alexandercheng.hw2;


import android.content.Context;
import android.widget.ImageButton;
import java.lang.Math;
import android.util.AttributeSet;


/**
 * Created by alexandercheng on 9/14/15.
 */
public abstract class Die extends ImageButton{

    protected int num;

    public Die(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public int roll(){
        this.num = (int)(Math.random() * 6 + 1);
       return num;
    }

    public int getNum(){
        return num;
    }

    public void setNum(int desiredNum){
        this.num = desiredNum;
    }


}
