package com.junhyeoklee.paik_s_cookingsecretbook;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this,"ca-app-pub-1438205576140129~8897661096");

    }
}
