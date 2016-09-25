package com.example.alexandercheng.hw5;

import android.os.Handler;
import java.util.concurrent.ConcurrentLinkedDeque;

// This is a Singleton class that makes sure the app as a whole does not
// fetch more than a URL every 2 seconds.  It is a bit fancier than the version
// I distributed in class because it will "save up" two seconds so that a 
// new request after 2 seconds of inactivity will immediately fetch.
public class RateLimit {
    public interface RateLimitCallback {
        void rateLimitReady();
    }
    private static RateLimit rateLimit = null;
    protected Handler handler;
    protected Runnable rateLimitRequest;
    protected final int rateLimitMillis = 2000; // 2 sec
    protected boolean okToRun;
    protected ConcurrentLinkedDeque<RateLimitCallback> rateLimitCallbacks;

    private RateLimit() {
        handler  = new Handler();
        rateLimitCallbacks = new ConcurrentLinkedDeque<RateLimitCallback>();
        rateLimitRequest = new Runnable() {
            @Override
            public void run() {
                okToRun = true;
                runIfOk();
                handler.postDelayed(this, rateLimitMillis);
            }
        };
        handler.postDelayed(rateLimitRequest, rateLimitMillis);
    }
    protected void runIfOk() {
        if( okToRun && !rateLimitCallbacks.isEmpty() ) {
            okToRun = false;
            RateLimitCallback rlc = rateLimitCallbacks.pop();
            rlc.rateLimitReady();
        }
    }

    // See https://en.wikipedia.org/wiki/Double-checked_locking
    // To understand this idiom
    private static class RateLimitHolder {
        public static final RateLimit rateLimit = new RateLimit();
    }

    public static RateLimit getInstance() {
       return RateLimitHolder.rateLimit;
    }

    public void add(RateLimitCallback rlc) {
        if( !rateLimitCallbacks.contains( rlc ) ) {
            rateLimitCallbacks.add(rlc);
        }
        runIfOk();
    }
    // Add to front of queue, for important fetches
    public void addFront(RateLimitCallback rlc) {
        if( !rateLimitCallbacks.contains( rlc )) {
            rateLimitCallbacks.push(rlc);
        }
        runIfOk();
    }
}

