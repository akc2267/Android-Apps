package com.example.alexandercheng.flipped1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class URLFetch implements RateLimit.RateLimitCallback {
    public interface FetchCallback {
        void fetchStart() throws MalformedURLException;

        void fetchComplete(Bitmap result);

        void fetchCancel(Bitmap result);
    }

    protected RateLimit rateLimit;
    protected FetchCallback fetchCallback = null;
    protected URL url;
    protected AsyncDownloader asyncDownloader;

    public URLFetch(FetchCallback fetchCallback, URL url) {
        // XXX WRITE ME
        this.fetchCallback = fetchCallback;
        this.url = url;
        this.asyncDownloader = new AsyncDownloader();
        rateLimit.getInstance().add(this);
    }

    // This routine is correct --witchel.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return url.equals(((URLFetch) obj).url);
    }

    public void rateLimitReady() {
        //write
        //System.out.println(url);

        asyncDownloader.execute(url);
    }

    // AsyncDownloader should extend AsyncTask.
    // It takes a URL and returns a Bitmap
    public class AsyncDownloader extends AsyncTask<URL, Integer, Bitmap> {
        // XXX WRITE ME

        // Do not change this function for the flipped classroom activity
        // Interface says we take an array of URLs, but we only process one.
        @Override
        protected Bitmap doInBackground(URL... urls) {
            //int count = fetchRecords.length;
            URL url = urls[0];
            HttpURLConnection urlConn = null;
            String result = null;

            int h = 535;
            int w = 535;
            Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bm);

            Paint darkPaint = new Paint();
            darkPaint.setColor(Color.rgb(50, 50, 50));

            Paint lightPaint = new Paint();
            lightPaint.setColor(Color.rgb(200, 200, 200));

            canvas.drawCircle(w / 2, h / 2, h / 2, darkPaint);
            canvas.drawCircle(w / 2, h / 2, h / 4, lightPaint);

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            System.out.println(bitmap);
            //BitmapCache.getInstance().setBitmap(bitmap);
            fetchCallback.fetchComplete(bitmap);
        }
    }
}
