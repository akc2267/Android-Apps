package com.example.alexandercheng.flipped1;

import android.graphics.Bitmap;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;

import java.net.MalformedURLException;
import java.net.URL;

// XXX WRITME I feel like this class should implement something...
public class MainActivity extends Activity implements URLFetch.FetchCallback{
    public String AppName = "RedFetch";
    ImageView resultBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.searchTerm);

        resultBM = (ImageView)findViewById(R.id.imageView);


        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(getApplicationContext(), "hey", Toast.LENGTH_SHORT).show();
                    doFetch();
                    // XXX write me
                }
                // XXX write me
                return false;
            }
        });
    }

    protected void doFetch() {
        // XXX WRITE ME
        try {
            fetchStart();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // XXX WRITE ME
    // Figure out what other code needs to be written

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

    @Override
    public void fetchStart() throws MalformedURLException {

        URL fakeURL = new URL("https://www.facebook.com/");
        new URLFetch(this, fakeURL);

        //Bitmap result = BitmapCache.getInstance().getBitmap();
        //System.out.println(result);

    }

    @Override
    public void fetchComplete(Bitmap result) {
        resultBM.setImageBitmap(result);
    }

    @Override
    public void fetchCancel(Bitmap result) {

    }
}