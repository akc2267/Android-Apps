package com.example.alexandercheng.hw4;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.net.ContentHandler;


/**
 * Created by cody on 9/23/15.
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    protected MainActivity parent;

    MediaPlayer mediaPlayer;

    public GestureListener(MainActivity parent) {
        this.parent = parent;
    }

    //Whatever you do, make sure that this function always returns true!!
    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    //FIXME you should probably add some things here
    //FIXME Android Studio's Generate functionality should be useful...
    //FIXME Also http://developer.android.com/reference/android/view/GestureDetector.SimpleOnGestureListener.html

    //FIXME Hint: start with the long press gesture, it is the most simple

    //FIXME to modify the properties of the red ball, call the following methods on this.parent.sphereView.sphere
    //FIXME           this.parent.sphereView.sphere.setxVelocity(double);
    //FIXME           this.parent.sphereView.sphere.setyVelocity(double)
    //FIXME           this.parent.sphereView.sphere.getxVelocity()
    //FIXME           this.parent.sphereView.sphere.getyVelocity()
    //FIXME look in the PhysicsSphere.java file for more API information
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d("DEBUG_TAG", "onFling: " + event1.toString() + event2.toString());
        if((event2.getRawY()-event1.getRawY()) > 100) {
            parent.tet.zoomDown();
            parent.gameGridView.invalidate();
            mediaPlayer = mediaPlayer.create(parent.getBaseContext(),  R.raw.button_09);

            mediaPlayer.setLooping(false);
            mediaPlayer.start();

        }
        if((event2.getRawY()-event1.getRawY()) < -100){
            parent.tet.rotateClockwise();
            parent.gameGridView.invalidate();
            mediaPlayer = mediaPlayer.create(parent.getBaseContext(),  R.raw.button_8);

            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
        if((event2.getRawX()-event1.getRawX()) > 100){
            parent.tet.shiftRight();
            parent.gameGridView.invalidate();
            mediaPlayer = mediaPlayer.create(parent.getBaseContext(),  R.raw.button_10);

            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
        if((event2.getRawX()-event1.getRawX()) < -100){
            parent.tet.shiftLeft();
            parent.gameGridView.invalidate();
            mediaPlayer = mediaPlayer.create(parent.getBaseContext(),  R.raw.button_10);

            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }
        return true;
    }




}
