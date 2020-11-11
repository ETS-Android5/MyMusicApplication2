package com.peter.android.mymusicapplication.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
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
}
