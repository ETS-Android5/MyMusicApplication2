package com.peter.android.mymusicapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public abstract class InternetBroadCastReceiver extends BroadcastReceiver {

    public boolean registered = false;

    public boolean isRegistered() {
        return registered;
    }


    public Intent register(Context context, IntentFilter filter){
        try{

            return !isRegistered()?context.registerReceiver(this,filter):null;


        }finally {
            registered = true;
        }
    }

    private boolean unregisterInternal(Context context){
        try{
            context.unregisterReceiver(this);
            registered= false;

        }catch (IllegalArgumentException e){
            //if some how (really not a possible situation) already not registered ignore it
        }
        return true;
    }

    public boolean unregister(Context context){
        return isRegistered()&&unregisterInternal(context);
    }
}
