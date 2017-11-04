package com.mobcent.codetool.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getDateString(Date date) {
        if (date == null) {
            return "";
        }
        return getDateString(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getNowDateString() {
        Date date = new Date();
        return getDateString(date);
    }

    public static String getDateString(Date date, String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        return sdf.format(date);
    }
    
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }
}
