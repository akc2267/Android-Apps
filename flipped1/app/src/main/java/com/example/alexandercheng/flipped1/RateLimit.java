package com.example.alexandercheng.flipped1;

import android.os.Handler;
import java.util.concurrent.ConcurrentLinkedDeque;

// Singleton class
public class RateLimit {
    public interface RateLimitCallback {
        void rateLimitReady();
    }
    private static RateLimit rateLimit = null;
    protected Handler handler;
    protected Runnable rateLimitRequest;
    protected final int rateLimitMillis = 2000; // 2 sec
    protected ConcurrentLinkedDeque<RateLimitCallback> rateLimitCallbacks;

    private RateLimit() {
        handler  = new Handler();
        rateLimitCallbacks = new ConcurrentLinkedDeque<RateLimitCallback>();
        rateLimitRequest = new Runnable() {
            @Override
            public void run() {
                if( !rateLimitCallbacks.isEmpty() ) {
                    RateLimitCallback rlc = rateLimitCallbacks.pop();
                    rlc.rateLimitReady();
                }
                handler.postDelayed(this, rateLimitMillis);
            }
        };
        handler.postDelayed(rateLimitRequest, rateLimitMillis);
    }

    // See https://en.wikipedia.org/wiki/Double-checked_locking
    // https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
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
    }
    // Add to front of queue, for important fetches
    public void addFront(RateLimitCallback rlc) {
        if( !rateLimitCallbacks.contains( rlc )) {
            rateLimitCallbacks.push(rlc);
        }
    }
}
