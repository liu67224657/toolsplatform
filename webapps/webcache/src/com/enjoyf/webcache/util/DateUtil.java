package com.enjoyf.webcache.util;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    // 短日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";

    // 长日期格式
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将日期格式的字符串转换为长整型
     * 
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        try {
            if (StringUtils.isNotBlank(date)) {
                if (StringUtils.isBlank(format))
                    format = TIME_FORMAT;
                SimpleDateFormat sf = new SimpleDateFormat(format);
                return sf.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
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
    
    public static String convert2String(long time){
        return convert2String(time, "yyyyMMdd");
    }

    /**
     * 获取当前系统的日期
     * 
     * @return
     */
    public static long curTimeMillis() {
        return System.currentTimeMillis();
    }

}
