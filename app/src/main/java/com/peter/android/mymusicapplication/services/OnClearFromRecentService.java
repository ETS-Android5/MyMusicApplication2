package com.peter.android.mymusicapplication.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class OnClearFromRecentService extends Service {

    private static final String ACTION_CLOSE_SELF = "OnClearFromRecentService_CloseSelF";
    Thread closeServiceThread;

    public static void startActionClose(Context appContext) {
        Intent intent = new Intent(appContext, OnClearFromRecentService.class);
        intent.setAction(ACTION_CLOSE_SELF);
        appContext.startService(intent);
    }

    public static void startActionOpen(Context appContext) {
        Intent intent = new Intent(appContext, OnClearFromRecentService.class);
        appContext.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//		Log.d("ClearFromRecentService", "Service Started");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            final String action = intent.getAction();
            if (action != null && action.equals(ACTION_CLOSE_SELF)) {
//					Log.d("ClearFromRecentService", "ACTION_CLOSE_SELF");
                killMySelf();
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//	        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
//			Log.d("ClearFromRecentService", "APP Terminated From Recent Apps");
        Intent myService = new Intent(this, PlayerService.class);
        stopService(myService);
        android.os.Process.killProcess(android.os.Process.myPid());
        this.stopSelf();
    }

    private void killMySelf() {

        if (closeServiceThread == null) {
            closeServiceThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Intent stopService = new Intent(getApplicationContext(), OnClearFromRecentService.class);
                        getApplicationContext().stopService(stopService);
                    } catch (Exception e) {
                        // no one interested we already killing it
                    }
                    closeServiceThread = null;
                }
            });

            closeServiceThread.start();
        }
    }

}
