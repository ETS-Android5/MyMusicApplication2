package com.peter.android.mymusicapplication.appliction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.multidex.MultiDexApplication;

import com.peter.android.mymusicapplication.services.PlayerService;
import com.peter.android.mymusicapplication.utility.Utils;

public class MyApplication extends MultiDexApplication {
    @SuppressLint("StaticFieldLeak")
    private static Context context; // context may come handy in some situations when you can't call getApplication Context

    public static Context getAppContext() {
        return MyApplication.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        initConnectivityStatus();
    }

    private void initConnectivityStatus() {

        if (Utils.isNetworkAvailable(this)) {
            Utils.isConnected = true;
        }
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        Utils.receiver.register(this, intentFilter);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Intent myService = new Intent(this, PlayerService.class);
        stopService(myService);
        Utils.receiver.unregister(getApplicationContext());
    }
}
