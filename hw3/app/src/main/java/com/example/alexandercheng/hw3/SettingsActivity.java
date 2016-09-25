package com.example.alexandercheng.hw3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ToggleButton;
import android.widget.CompoundButton;

/**
 * Created by alexandercheng on 9/23/15.
 */
public class SettingsActivity  extends Activity {

    Button Cancel;
    Button OK;
    ToggleButton loopButton;
    Boolean loop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        Intent activityThatCalled = getIntent();

        boolean previousActivity = activityThatCalled.getExtras().getBoolean("loop");


        Cancel = (Button)findViewById(R.id.Cancel);
        OK = (Button)findViewById(R.id.OK);
        loopButton = (ToggleButton)findViewById(R.id.Loop);
        loopButton.setChecked(previousActivity);

        loopButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loop = true;
                }
                else{
                    loop = false;
                }
            }
        });

        Cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exit(false);
                    }
                });

        OK.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exit(true);
                    }
                });


    }


    public void exit(boolean ok){
        Intent returntoScoreScreenIntent = new Intent(this, MainActivity.class);

        final int result = 1;

        if(ok) {
            returntoScoreScreenIntent.putExtra("data", loop);
        }

        setResult(result, returntoScoreScreenIntent);
        finish();
    }
}
