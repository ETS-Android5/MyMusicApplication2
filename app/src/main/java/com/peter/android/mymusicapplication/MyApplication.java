package com.peter.android.mymusicapplication;

import android.app.Application;
import android.content.Intent;

public class MyApplication extends Application {
    @Override
    public void onTerminate() {
        super.onTerminate();
        Intent myService = new Intent(this, PlayerService.class);
        stopService(myService);
    }
}
