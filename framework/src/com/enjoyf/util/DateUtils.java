package com.enjoyf.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
    // 短日期格式
    public static String DATE_FORMAT = "yyyy-MM-dd";

    // 长日期格式
    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
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
    
    public static long getLongFromDate(String date) throws ParseException{
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt2 = sdf.parse(date);
        return dt2.getTime();
    }
    
    /**
     * 将长整型数字转换为日期格式的字符串
     * 
     * @param time
     * @param format
     * @return
     */
    public static String convert2String(long time, String format) {
        if (time > 0l) {
            if (StringUtils.isBlank(format))
                format = TIME_FORMAT;
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date date = new Date(time);
            return sf.format(date);
        }
        return "";
    }
    
    public static void main(String[] args) throws ParseException {
        String date1 = "2013-08-12 00:00:00";
//        String date2 = "2012-06-18 23:59:59";
        System.out.println(getLongFromDate(date1));
//        System.out.println(getLongFromDate(date2));
        
//        Date date =new Date(1373871830000l);
//        System.out.println(date);
    }
}
