package com.example.alexandercheng.hw4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TGrid gameGrid;
    TGrid tetTGrid;
    TGridView gameGridView;
    TGridView tetView;
    TetrominoBuilder tetBuilder;
    Tetromino tet;
    Tetromino tet2;
    Button resetButton;
    boolean gameOver;
    int rows;
    TextView rowsView;
    int level;
    TextView levelView;
    int score;
    TextView scoreView;
    boolean levelUp;
    double delay;

    MediaPlayer mediaPlayer;

    GestureDetectorCompat gestures;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadActivity();
    }



    protected void loadActivity() {

        gameGridView = (TGridView) findViewById(R.id.tgridview);
        tetView = (TGridView)findViewById(R.id.nextTet);
        rowsView = (TextView) findViewById(R.id.rows);
        levelView = (TextView) findViewById(R.id.level);
        scoreView = (TextView) findViewById(R.id.score);
        resetButton = (Button) findViewById(R.id.reset);


        gameGrid = new TGrid(10, 22);
        tetTGrid = new TGrid(4,6);
        levelUp = false;
        gameOver = false;
        delay = 1000.00;
        rows = 0;
        rowsView.setText("" + rows);
        level = 1;
        levelView.setText("" + level);
        score = 0;
        scoreView.setText("" + score);


        tet = tetBuilder.Random();
        tet2 = tetBuilder.Random();
        tet.insertIntoGrid(4, 0, gameGrid);
        tet2.insertIntoGrid(0, 2, tetTGrid);
        tetView.addTGrid(tetTGrid);
        tetView.invalidate();

        gameGridView.addTGrid(gameGrid);
        gameGridView.invalidate();
        tetView.addTGrid(tetTGrid);
        tetView.invalidate();


        this.gestures = new GestureDetectorCompat(this, new GestureListener(this));





        final ScheduledExecutorService myScheduledExecutorService = Executors.newScheduledThreadPool(1);

        myScheduledExecutorService.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        monitorHandler.sendMessage(monitorHandler.obtainMessage());
                    }
                },
                1000, //initialDelay
                ((long) delay), //delay
                TimeUnit.MILLISECONDS);

        resetButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        resetButton.setText("RESET");
                        myScheduledExecutorService.shutdown();
                                loadActivity();
                    }
                });

    }


    Handler monitorHandler = new Handler() {

        @Override
            public void handleMessage(Message msg) {
            if (!gameOver) {
                if (!tet.shiftDown()) {
                    while (gameGrid.getFirstFullRow() != -1) {
                        gameGrid.deleteRow(gameGrid.getFirstFullRow());
                        rows++;
                        score += level;
                        scoreView.setText("" + score);
                        rowsView.setText("" + rows);
                        if (rows % 5 == 0 && rows != 0) {
                            level++;
                            levelUpDelay();
                            levelView.setText("" + level);
                        }
                    }
                    tet = tet2;
                    tet2.removeFromGrid();
                    tet2 = tetBuilder.Random();

                    if (!tet.insertIntoGrid(4, 0, gameGrid)) {
                        gameOver = true;
                    }
                }
                tet2.insertIntoGrid(0, 2, tetTGrid);
                tetView.addTGrid(tetTGrid);
                tetView.invalidate();;
                gameGridView.invalidate();
            }
            else{
                resetButton.setText("GAME OVER, restart?");
                gameOverSound();
            }
        }

    };

    protected void gameOverSound(){
        mediaPlayer = mediaPlayer.create(getBaseContext(),  R.raw.button_4);

        mediaPlayer.setLooping(false);
        mediaPlayer.start();
    }




    @Override
    public boolean onTouchEvent(MotionEvent event){
        //FIXME uncomment this when you are ready!
        this.gestures.onTouchEvent(event);
        return super.onTouchEvent(event);
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

            Intent getSettingsIntent = new Intent(this, SettingsActivity.class);

            final int result = 1;

            getSettingsIntent.putExtra("levelUp", levelUp);

            startActivityForResult(getSettingsIntent, result);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.hasExtra("data")) {
            levelUp = data.getExtras().getBoolean("data");
            if(levelUp){
                level++;
                levelUpDelay();
                levelView.setText("" + level);
            }
            levelUp = false;
        }

    }

    protected void levelUpDelay(){
        delay = .8*delay;
        Log.d("msg", ""+(long)delay);
    }
}
