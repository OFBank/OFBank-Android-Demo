package com.minersdk.demo;

import android.app.Application;

import com.ofbank.work.manager.MinerSDK;

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //SDK初始化
        MinerSDK.init("WOA7RYHRnmcNR6FOg7M28nF2jEGzgv3S", "1278167415222313", this);
        MinerSDK.setDebugModel(BuildConfig.DEBUG);
    }
}
