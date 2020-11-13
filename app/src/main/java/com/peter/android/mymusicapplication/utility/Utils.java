package com.peter.android.mymusicapplication.utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.peter.android.mymusicapplication.broadcast.InternetBroadCastReceiver;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static volatile boolean isConnected = false;
    public static InternetBroadCastReceiver receiver = new InternetBroadCastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            isConnected = isNetworkAvailable(context);
        }
    };

    public static Date getDate(String date) {
//                                                          2020-11-08T20:09:26.681745+00:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZ", Locale.ENGLISH);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getReadableFormat(Date publishedAt) {
        return DateFormat.getDateTimeInstance().format(publishedAt);
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    return (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) || (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) || (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
                }
            } else {
                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("isNetworkAvailable", "" + e.getMessage());
                }
            }
        }
        return false;
    }

    public static Bitmap getFromVector(Context context, int id) {

        try {
            Drawable drawable = ContextCompat.getDrawable(context, id);
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }

    public static BigDecimal round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }
}
