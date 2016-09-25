package com.example.alexandercheng.hw1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    ImageButton imgButton;
    Button resetButton;
    Button colorSwitchButton;
    boolean resetBool = false;
    boolean red = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton =(ImageButton)findViewById(R.id.imageButton);
        imgButton.setOnClickListener(new View.OnClickListener() {
            int one = 0;
            int two = 0;
            int three = 0;
            int four = 0;
            int five = 0;
            int six = 0;

            @Override
            public void onClick(View v) {
                if (resetBool) {
                    one = 0;
                    two = 0;
                    three = 0;
                    four = 0;
                    five = 0;
                    six = 0;
                    resetBool = false;
                }


                int dice_num = (int) (6 * Math.random() + 1);
                // Toast.makeText(getApplicationContext(), "dice is" + dice_num, Toast.LENGTH_LONG).show();
                if(red){
                    switch (dice_num) {
                        case 1:
                            imgButton.setBackgroundResource(R.drawable.red_die_1);
                            one++;
                            break;
                        case 2:
                            imgButton.setBackgroundResource(R.drawable.red_die_2);
                            two++;
                            break;
                        case 3:
                            imgButton.setBackgroundResource(R.drawable.red_die_3);
                            three++;
                            break;
                        case 4:
                            imgButton.setBackgroundResource(R.drawable.red_die_4);
                            four++;
                            break;
                        case 5:
                            imgButton.setBackgroundResource(R.drawable.red_die_5);
                            five++;
                            break;
                        case 6:
                            imgButton.setBackgroundResource(R.drawable.red_die_6);
                            six++;
                            break;
                    }
                }
                else {
                    switch (dice_num) {
                        case 1:
                            imgButton.setBackgroundResource(R.drawable.white_die_1);
                            one++;
                            break;
                        case 2:
                            imgButton.setBackgroundResource(R.drawable.white_die_2);
                            two++;
                            break;
                        case 3:
                            imgButton.setBackgroundResource(R.drawable.white_die_3);
                            three++;
                            break;
                        case 4:
                            imgButton.setBackgroundResource(R.drawable.white_die_4);
                            four++;
                            break;
                        case 5:
                            imgButton.setBackgroundResource(R.drawable.white_die_5);
                            five++;
                            break;
                        case 6:
                            imgButton.setBackgroundResource(R.drawable.white_die_6);
                            six++;
                            break;
                    }
                }
                TextView tv = (TextView) findViewById(R.id.mytextview);
                tv.setText("one = " + one +
                        "\ntwo = " + two +
                        "\nthree = " + three +
                        "\nfour = " + four +
                        "\nfive = " + five +
                        "\nsix = " + six);
            }
        });

        resetButton = (Button)findViewById(R.id.reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tv1 = (TextView) findViewById(R.id.mytextview);
                tv1.setText("one = 0\ntwo = 0\nthree = 0\nfour = 0\nfive = 0\nsix = 0");
                resetBool = true;
            }
        });

        colorSwitchButton = (Button)findViewById(R.id.colorButton);
        colorSwitchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                red = !red;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
