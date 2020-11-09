package com.peter.android.mymusicapplication;

import android.content.Intent;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onTerminate() {
        super.onTerminate();
        Intent myService = new Intent(this, PlayerService.class);
        stopService(myService);
    }
}
