package com.example.alexandercheng.hw4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by alexandercheng on 9/23/15.
 */
public class SettingsActivity extends Activity {

    Button Cancel;
    Button OK;
    ToggleButton levelUpButton;
    Boolean levelUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        Intent activityThatCalled = getIntent();

        boolean previousActivity = activityThatCalled.getExtras().getBoolean("levelUp");



        Cancel = (Button)findViewById(R.id.Cancel);
        OK = (Button)findViewById(R.id.OK);
        levelUpButton = (ToggleButton)findViewById(R.id.levelUp);
        levelUpButton.setChecked(previousActivity);

        levelUpButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    levelUp = true;
                }
                else{
                    levelUp = false;
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
            returntoScoreScreenIntent.putExtra("data", levelUp);
        }

        setResult(result, returntoScoreScreenIntent);
        finish();
    }
}
