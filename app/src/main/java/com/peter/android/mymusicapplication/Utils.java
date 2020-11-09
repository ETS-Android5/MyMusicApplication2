package com.peter.android.mymusicapplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ", Locale.ENGLISH);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String getReadableFormat(Date publishedAt) {
        return DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US).format(publishedAt);
    }
}
