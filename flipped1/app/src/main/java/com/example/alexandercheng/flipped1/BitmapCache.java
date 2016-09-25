package com.example.alexandercheng.flipped1;

public class BitmapCache {
    private static BitmapCache instance = null;
    android.graphics.Bitmap m_bmc;
    protected BitmapCache() {
    }
    public static BitmapCache getInstance() {
        if (instance == null) {
            instance = new BitmapCache();
        }
        return instance;
    }

    public void setBitmap(android.graphics.Bitmap bmc) {
        instance.m_bmc = bmc;
    }

    public android.graphics.Bitmap getBitmap() {
        return instance.m_bmc;
    }
}