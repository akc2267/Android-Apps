package com.example.alexandercheng.hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected Toast mainToast;
    RedDie redDie1;
    RedDie redDie2;
    RedDie redDie3;
    WhiteDie whiteDie1;
    WhiteDie whiteDie2;
    int highDefender;
    int lowDefender;
    int highAttacker;
    int lowAttacker;
    int attackNum;
    int defenseNum;
    Button startButton;
    EditText attackEditText;
    EditText defenseEditText;
    TextView attackText;
    TextView defenseText;

    private ProgressBar attackBar = null;
    private ProgressBar defenseBar = null;

    protected void doToast(String message) {
        if(this.mainToast != null) {
            this.mainToast.cancel();
        }
        this.mainToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        this.mainToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disableDice();

        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                attackEditText = (EditText) findViewById(R.id.AttackField);
                defenseEditText = (EditText) findViewById(R.id.DefenseField);
                attackBar = (ProgressBar) findViewById(R.id.attackBar);
                defenseBar = (ProgressBar) findViewById(R.id.defenseBar);
                attackText = (TextView) findViewById(R.id.attackText);
                defenseText = (TextView) findViewById(R.id.defenseText);



                if (attackEditText.getText().toString().equals("") || defenseEditText.getText().toString().equals("") || Integer.parseInt(attackEditText.getText().toString()) < 1 || Integer.parseInt(defenseEditText.getText().toString()) < 1) {
                    doToast("Enter a proper number");
                } else {
                    attackNum =  Integer.parseInt(attackEditText.getText().toString());
                    defenseNum = Integer.parseInt(defenseEditText.getText().toString());
                    enableDice();
                    attackBar.setVisibility(View.VISIBLE);
                    attackBar.setMax(attackNum);
                    defenseBar.setVisibility(View.VISIBLE);
                    defenseBar.setMax(defenseNum);


                    attackBar.setProgress(attackNum);
                    defenseBar.setProgress(defenseNum);
                    attackText.setText("Attacking Soldiers: " + attackNum);
                    defenseText.setText("Defending Soldiers: " + defenseNum);
                }
            }
        });
    }


    protected void enableDice(){
        redDie1.setBackgroundResource(R.drawable.red_die_6);
        redDie2.setBackgroundResource(R.drawable.red_die_6);
        redDie3.setBackgroundResource(R.drawable.red_die_6);
        whiteDie1.setBackgroundResource(R.drawable.white_die_6);
        whiteDie2.setBackgroundResource(R.drawable.white_die_6);
        redDie1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollAllDice();
            }
        });

        redDie2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollAllDice();
            }
        });

        redDie3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollAllDice();
            }
        });

        whiteDie1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollAllDice();
            }
        });

        whiteDie2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rollAllDice();
            }
        });
    }

    protected void disableDice(){
        redDie1 =(RedDie)findViewById(R.id.RedDie1);
        redDie1.setOnClickListener(null);
        redDie1.setBackgroundResource(R.drawable.red_die_6_disabled);
        redDie2 =(RedDie)findViewById(R.id.RedDie2);
        redDie2.setOnClickListener(null);
        redDie2.setBackgroundResource(R.drawable.red_die_6_disabled);
        redDie3 =(RedDie)findViewById(R.id.RedDie3);
        redDie3.setOnClickListener(null);
        redDie3.setBackgroundResource(R.drawable.red_die_6_disabled);
        whiteDie1 =(WhiteDie)findViewById(R.id.WhiteDie1);
        whiteDie1.setOnClickListener(null);
        whiteDie1.setBackgroundResource(R.drawable.white_die_6_disabled);
        whiteDie2 =(WhiteDie)findViewById(R.id.WhiteDie2);
        whiteDie2.setOnClickListener(null);
        whiteDie2.setBackgroundResource(R.drawable.white_die_6_disabled);


    }

    protected void rollAllDice(){
        redDie1.roll();
        if(attackNum > 1) {
            redDie2.roll();
        }
        else{
            redDie2.setNum(0);
            redDie2.setBackgroundResource(R.drawable.red_die_6_disabled);
        }
        if(attackNum > 2) {
            redDie3.roll();
        }
        else{
            redDie3.setNum(0);
            redDie3.setBackgroundResource(R.drawable.red_die_6_disabled);
        }
        whiteDie1.roll();
        if(defenseNum > 1) {
            whiteDie2.roll();
        }
        else{
            whiteDie2.setNum(0);
            whiteDie2.setBackgroundResource(R.drawable.white_die_6_disabled);
        }

        if(whiteDie1.getNum() > whiteDie2.getNum()){
            highDefender = whiteDie1.getNum();
            lowDefender = whiteDie2.getNum();
        }
        else{
            highDefender = whiteDie2.getNum();
            lowDefender = whiteDie1.getNum();
        }

        if(redDie1.getNum() > redDie2.getNum()){
            highAttacker = redDie1.getNum();
            lowAttacker = redDie2.getNum();
            if(redDie3.getNum() > redDie1.getNum()){
                highAttacker = redDie3.getNum();
                lowAttacker = redDie1.getNum();
            }
            else if (redDie3.getNum() > redDie2.getNum()){
                lowAttacker = redDie3.getNum();
            }
        }
        else {
            highAttacker = redDie2.getNum();
            lowAttacker = redDie1.getNum();
            if (redDie3.getNum() > redDie2.getNum()) {
                highAttacker = redDie3.getNum();
                lowAttacker = redDie2.getNum();
            } else if (redDie3.getNum() > redDie1.getNum()) {
                lowAttacker = redDie3.getNum();
            }
        }

        if(highAttacker > highDefender){
            attackNum--;
            doToast("attacker loses a soldier");
        }
        else{
            defenseNum--;
            doToast("defender loses a soldier");
        }
        if(lowAttacker > lowDefender){
            attackNum--;
            doToast("attacker loses a soldier");
        }
        else{
            defenseNum--;
            doToast("defender loses a soldier");
        }


        attackBar.setProgress(attackNum);
        defenseBar.setProgress(defenseNum);
        attackText.setText("Attacking Soldiers: " + attackNum);
        defenseText.setText("Defending Soldiers: " + defenseNum);

        if(defenseNum <= 0 || attackNum <= 0){
            disableDice();
            doToast("Game is Over");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
