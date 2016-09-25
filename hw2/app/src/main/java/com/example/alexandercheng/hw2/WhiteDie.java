package com.example.alexandercheng.hw2;

import android.content.Context;
import android.util.AttributeSet;


import com.example.alexandercheng.hw2.Die;

/**
 * Created by alexandercheng on 9/14/15.
 */
class WhiteDie extends Die{

    public WhiteDie(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int roll(){
        super.roll();
        switch (this.num) {
            case 1:
                setBackgroundResource(R.drawable.white_die_1);
                break;
            case 2:
                setBackgroundResource(R.drawable.white_die_2);
                break;
            case 3:
                setBackgroundResource(R.drawable.white_die_3);
                break;
            case 4:
                setBackgroundResource(R.drawable.white_die_4);
                break;
            case 5:
                setBackgroundResource(R.drawable.white_die_5);
                break;
            case 6:
                setBackgroundResource(R.drawable.white_die_6);
                break;
        }
        return num;
    }

}
