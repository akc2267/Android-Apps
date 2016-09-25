package com.example.alexandercheng.hw5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements URLFetch.Callback {
    static public String AppName = "RedFetch";
    private ProgressBar progressBar;
    // Reddit json search will return up to 100 records, 25 by default
    // Can only display a subset of those returned
    protected final int maxRedditRecords = 100;
    protected DynamicAdapter redditRecordAdapter = null;
    protected SwipeDetector swipeDetector;
    protected List<RedditRecord> redditRecordList = new LinkedList<RedditRecord>();
    protected ListView list;
    URL searchURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/4th of the available memory for this memory cache.
        BitmapCache.cacheSize = maxMemory / 4;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        BitmapCache.maxW = size.x;
        BitmapCache.maxH = size.y;

        final EditText editText = (EditText) findViewById(R.id.searchTerm);
        list = (ListView) findViewById(R.id.ListView);
        redditRecordAdapter = new DynamicAdapter(this, R.layout.mylistlayout, redditRecordList);



        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if(editText.getText().toString().equals("") || editText.getText().length() > 30 || editText.getText().toString().contains(" ")){
                        Toast.makeText(getBaseContext(),"enter a proper search term", Toast.LENGTH_LONG).show();
                    }
                    else {

                        String ABC = "/r/aww/search.json?q=" + editText.getText() + "&sort=hot&limit=100";
                        try {
                            searchURL = new URL("https", "www.reddit.com", ABC);
                            Log.d("url", searchURL.toString());
                            fetchStart(searchURL);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                    // XXX write me
                }

                // XXX write me
                return false;
            }
        });

        // XXX Much initialiation remains.
        // Your reddit searchers should be constructed along these lines.
        // Consruct a string called ABC that substitutes your search term for the %s
        // ABC = "/r/aww/search.json?q=%s&sort=hot&limit=100"
        // URL searchURL = new URL("https", "www.reddit.com", ABC);
        // Also see
        // https://www.reddit.com/dev/api#GET_search
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void fetchStart(URL url){
        redditRecordAdapter.clear();
        new URLFetch(this, url, true);

    }


    @Override
    public void fetchComplete(String result) {
        JSONObject jO = null;
        try {
            jO = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("jsonMAIN", jO.toString());
        try {
            if( jO.isNull("data") ) {
                return;
            }
            jO = jO.getJSONObject("data");
            JSONArray jA = jO.getJSONArray("children");
            int jsonIndex=0;
            while( jsonIndex < jA.length() ) {
                jO = jA.getJSONObject(jsonIndex);
                if( jO.isNull("data") ){
                    jsonIndex++;continue;
                }
                jO = jO.getJSONObject("data");
// Make sure there is a thumbnail and an image url
// Most thumbnails I see are jpg or png, but some urls are to imgur, which
// contain html
                if( jO.isNull("thumbnail")
                        || (!jO.getString("thumbnail").endsWith("jpg")
                        && !jO.getString("thumbnail").endsWith("png"))
                        || jO.isNull("url")
                        || (!jO.getString("url").endsWith("jpg") && !jO.getString("url").endsWith("png"))
                        || jO.isNull("title")) {
                    jsonIndex++;continue;
                }
                String title = jO.getString("title");
                String thumbnailURL = jO.getString("thumbnail");
                String imageURL = jO.getString("url");
                RedditRecord redRecord = new RedditRecord();
                redRecord.title = title;
                redRecord.thumbnailURL = new URL(thumbnailURL);
                redRecord.imageURL = new URL(imageURL);
                redditRecordList.add(redRecord);


               // Bitmap bm = getBitmapFromURL(imageURL);
               // BitmapCache.getInstance().setBitmap(title, bm);

                // Update loop index
                jsonIndex++;
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        redditRecordAdapter = new DynamicAdapter(this, R.layout.mylistlayout, redditRecordList);

        list.setAdapter(redditRecordAdapter);

        swipeDetector = new SwipeDetector();
        list.setOnTouchListener(swipeDetector);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> list,
                                            View row,
                                            int index,
                                            long rowID) {
                        if (swipeDetector.swipeDetected()) {
                            if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                                redditRecordAdapter.remove(redditRecordAdapter.getItem(index));
                            }
                        } else {
                            Intent getSettingsIntent = new Intent(getApplicationContext(), OnePost.class);

                            final int result = 1;

                            getSettingsIntent.putExtra("imageURL", redditRecordList.get(index).imageURL.toString());
                            getSettingsIntent.putExtra("title", redditRecordList.get(index).title);


                            startActivityForResult(getSettingsIntent, result);
                        }

                        return;

                    }

                });

    }

    @Override
    public void fetchCancel(String url) {

    }


}
