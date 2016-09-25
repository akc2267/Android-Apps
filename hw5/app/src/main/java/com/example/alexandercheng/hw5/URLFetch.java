package com.example.alexandercheng.hw5;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class URLFetch implements RateLimit.RateLimitCallback {
    public interface Callback {
        void fetchStart(URL url);
        void fetchComplete(String result);
        void fetchCancel(String url);
    }
    protected RateLimit rateLimit;
    protected Callback callback = null;
    protected URL url;
    protected AsyncDownloader asyncDownloader = new AsyncDownloader();

    public URLFetch(Callback callback, URL url, boolean front) {
        this.callback = callback;
        this.url = url;
        if( front ) {
            RateLimit.getInstance().addFront(this);
        } else {
            RateLimit.getInstance().add(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return url.equals(((URLFetch)obj).url);
    }

    public void rateLimitReady() {
        // XXX write me: execute asyncDownloader
        Log.d("urlRLC", url.toString());
        asyncDownloader.execute(url);
    }

    public class AsyncDownloader extends AsyncTask<URL, Integer, String> {
        // XXX Write me

        String result = "";

        @Override
        protected String doInBackground(URL... urls) {
            //int count = fetchRecords.length;
            URL url = urls[0];
            result = url.toString();

            if(result.endsWith("jpg") || result.endsWith("png")){
                Bitmap bm;
                try{
                    bm = getBitmapFromURL(result);
                }
                catch(OutOfMemoryError e){
                    bm = BitmapCache.errorImageBitmap;
                }
                BitmapCache.getInstance().setBitmap(result, bm);
                return result;
            }

            //establish URL connection
            try {
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestProperty("User-Agent", "android:edu.utexas.cs371m.akc2267.redfetch:v1.0 (by /u/akc2267)");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //read from URL
            InputStream is = null;
            try {
                is = url.openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject jO = new JSONObject(jsonText);
                try {
                    jO = new JSONObject(url.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                result = jO.toString();
                Log.d("json", jO.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //System.out.println(bitmapKey);
            //BitmapCache.getInstance().setBitmap(bitmap);
            callback.fetchComplete(result);

        }

        //helper method to build string from URL
        //from http://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }


        //helper method to convert image URL to bitmap
        //from http://stackoverflow.com/questions/8992964/android-load-from-url-to-bitmap
        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "android:edu.utexas.cs371m.akc2267.redfetch:v1.0 (by /u/akc2267)");
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                Log.d("bitmap", "success");
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        // Note:
        // At some point in this code you will open a network
        // connection.  That code will look something like this.
        //  urlConn = (HttpURLConnection) url.openConnection();
        // Once you open the connection, you MUST seet the User-Agent,
        // which is an identifying string for your app.  This is what
        // you should use
        //  urlConn.setRequestProperty("User-Agent",
        // "android:edu.utexas.cs371m.YOURID.redfetch:v1.0 (by
        // /u/YOURREDDITID)");
        // In the above code you should substitute something identifying like your CS
        // username or eid for YOURID, and if you have a reddit ID, use
        // it for YOURREDDITID.  If not, use YOURID again.
        // If you don't set User-Agent properly, you will lose a lot of points.
    }

}
