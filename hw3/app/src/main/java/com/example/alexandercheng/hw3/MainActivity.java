package com.example.alexandercheng.hw3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.*;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.Toast;
import android.widget.TextView;
import android.os.Handler;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.os.Message;
import android.provider.Settings;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ListView songListView;
    Boolean loop = false;
    GetSongResources getSongResources;
    TextView currentSong;
    TextView nextSong;
    TextView timeDone;
    TextView timeLeft;
    MediaPlayer mediaPlayer;
    ImageButton prev;
    ImageButton next;
    ImageButton playPause;
    int[] songFiles;
    int listPosition;
    SeekBar musicSeek;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listPosition = 0;
        songFiles = getSongResources.getSongResources();
        songListView = (ListView)findViewById(R.id.songList);
        currentSong = (TextView)findViewById(R.id.currentSong);
        nextSong = (TextView)findViewById(R.id.nextSong);
        timeDone = (TextView)findViewById(R.id.timeDone);
        timeLeft = (TextView)findViewById(R.id.timeLeft);
        prev = (ImageButton)findViewById(R.id.prev);
        playPause = (ImageButton)findViewById(R.id.playPause);
        next = (ImageButton)findViewById(R.id.next);
        musicSeek = (SeekBar)findViewById(R.id.seekBar);


        mediaPlayer = mediaPlayer.create(this, songFiles[0]);
        setLoop();
        mediaPlayer.start();
        startPlayProgressUpdater();
        changeSongDetails(currentSong, nextSong, listPosition, songListView, playPause);

        songListView.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        listPosition = position;
                        playSong(listPosition);
                        startPlayProgressUpdater();
                        changeSongDetails(currentSong, nextSong, listPosition, songListView, playPause);
                    }
                });

        playPause.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            playPause.setBackgroundResource(R.drawable.play);
                        } else {
                            mediaPlayer.start();
                            playPause.setBackgroundResource(R.drawable.pause);
                        }
                    }
                }
        );

        prev.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listPosition--;
                        if (listPosition < 0) {
                            listPosition = 0;
                        }
                        playSong(listPosition);
                        startPlayProgressUpdater();
                        changeSongDetails(currentSong, nextSong, listPosition, songListView, playPause);
                    }
                });
        next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listPosition++;
                        if (listPosition >= songListView.getCount()) {
                            listPosition = 0;
                        }
                        playSong(listPosition);
                        startPlayProgressUpdater();
                        changeSongDetails(currentSong, nextSong, listPosition, songListView, playPause);
                    }
                });
        enableContinuousPlay();


        ScheduledExecutorService myScheduledExecutorService = Executors.newScheduledThreadPool(1);

        myScheduledExecutorService.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        monitorHandler.sendMessage(monitorHandler.obtainMessage());
                    }
                },
                200, //initialDelay
                200, //delay
                TimeUnit.MILLISECONDS);

    }

    Handler monitorHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            mediaPlayerMonitor();
        }

    };

    private void mediaPlayerMonitor(){

            if(mediaPlayer.isPlaying()){

                int mediaPosition = mediaPlayer.getCurrentPosition()/1000;
                int mediaDuration = mediaPlayer.getDuration()/1000 - mediaPosition;
                timeDone.setText(String.valueOf(mediaPosition/600) + String.valueOf((mediaPosition%600)/60) + ":" + String.valueOf((mediaPosition%60)/10) + String.valueOf(mediaPosition%60%10));
                timeLeft.setText(String.valueOf(mediaDuration/600) + String.valueOf((mediaDuration%600)/60) + ":" + String.valueOf((mediaDuration%60)/10) + String.valueOf(mediaDuration%60%10));
            }
    }

    public void enableContinuousPlay(){
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (!loop) {

                    listPosition++;
                    if (listPosition >= songListView.getCount()) {
                        listPosition = 0;
                    }
                    playSong(listPosition);
                    startPlayProgressUpdater();
                    changeSongDetails(currentSong, nextSong, listPosition, songListView, playPause);
                }
            }
        });
    }


    public void playSong(int position){
        playPause.setBackgroundResource(R.drawable.pause);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(getBaseContext(), songFiles[position]);
        mediaPlayer.start();
        enableContinuousPlay();
    }

    private void setLoop(){
        if(loop) {
            mediaPlayer.setLooping(true);
        }
        else{
            mediaPlayer.setLooping(false);
        }
    }


    private void changeSongDetails(TextView currentSong, TextView nextSong, int position, ListView songListView, ImageButton playPause){
        playPause.setBackgroundResource(R.drawable.pause);
        musicSeek.setMax(mediaPlayer.getDuration());
        musicSeek.setProgress(0);
        musicSeek.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekChange(v);
                return false; }
        });


        currentSong.setText(songListView.getItemAtPosition(position).toString());
        if (position + 1 != songListView.getCount()) {
            nextSong.setText(songListView.getItemAtPosition(position + 1).toString());
        } else {
            nextSong.setText(songListView.getItemAtPosition(0).toString());
        }
    }

    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }

    public void startPlayProgressUpdater() {
        musicSeek.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
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
            //Toast.makeText(getApplicationContext(), "yo", Toast.LENGTH_SHORT).show();

            Intent getSettingsIntent = new Intent(this, SettingsActivity.class);

            final int result = 1;

            getSettingsIntent.putExtra("loop", loop);

            startActivityForResult(getSettingsIntent, result);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.hasExtra("data")) {
            loop = data.getExtras().getBoolean("data");
            setLoop();
        }

    }
}
