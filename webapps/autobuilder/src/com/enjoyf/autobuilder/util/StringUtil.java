package com.enjoyf.autobuilder.util;

import java.text.MessageFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static Random rand = null;
    private static char[] numAndLetters = null;
    private static Object initLock = new Object();

    public static String removeLastCharacter(String oldString, String flag) {
        if (oldString == null)
            return null;

        if (oldString.endsWith(flag))
            return oldString.substring(0, oldString.length() - flag.length());
        else
            return oldString;
    }

    public static String removeStartCharacter(String oldString, String flag) {
        if (oldString == null)
            return null;

        if (oldString.startsWith(flag))
            return oldString.substring(flag.length(), oldString.length());
        else
            return oldString;
    }

    /**
     * 浜х敓婵�椿鐮�
     * 
     * @author WeiYY(2012-5-4)
     * @param length
     * @return
     */
    public static final String generateKey(int length) {
        if (length < 1)
            return null;
        if (rand == null) {
            synchronized (initLock) {
                rand = new Random();
                numAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
            }
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numAndLetters[rand.nextInt(61)];
        }
        return new String(randBuffer);
    }

    /**
     * 鏇挎崲String瀛楃涓�
     * 
     * @param source
     * @param parameter
     * @return
     */
    public static String changeParameter(String source, String[] parameter) {
        MessageFormat form = new MessageFormat(source);
        return form.format(parameter);
    }

    /**
     * 鎴彇绗竴涓鍙峰拰绗簩涓鍙蜂腑闂寸殑瀛楃涓�
     * 
     * @param str
     * @param beginFlag
     * @param endFlag
     * @return
     */
    public static String getStringBetweenFlag(String str, String beginFlag, String endFlag) {
        int position = str.indexOf(beginFlag);
        if (position > 0) {
            String temp = str.substring(position + beginFlag.length(), str.length());
            int position1 = temp.indexOf(endFlag);
            if (position1 > 0) {
                temp = temp.substring(0, position1);
                return temp;
            } else {
                return str;
            }
        } else {
            return str;
        }
    }

    /**
     * 鑾峰緱涓�釜瀛楃涓查灏鹃儴鍒�
     * @param str
     * @param beginFlag 寮�ご鐨勬爣蹇�
     * @param endFlag 缁撳熬鐨勬爣蹇�
     * @return
     */
    public static String[] getHeadAndEndString(String str, String beginFlag, String endFlag) {
        int position = str.indexOf(beginFlag);
        int position1 = str.indexOf(endFlag);
        if (position > 0) {
            String head = str.substring(0, position);
            String end = str.substring(position1 + endFlag.length(), str.length());
            return new String[] { head, end };
        } else {
            return new String[] { str, "" };
        }

    }

     public static String replace(String sval, String replacePattern, String destVal, int caseIgnore) {
        return regReplace(sval, replacePattern, destVal, caseIgnore);
    }

     public static String regReplace(String sval, String replacePattern, String destRep, int caseIgnore) {
        if (sval != null && !sval.equals("")) {
            Pattern _pattern = Pattern.compile(replacePattern, caseIgnore);
            Matcher _matcher = _pattern.matcher(sval);
            StringBuffer _strBuf = new StringBuffer();

            boolean _findFlag = _matcher.find();

            while (_findFlag) {
                _matcher.appendReplacement(_strBuf, destRep);
                _findFlag = _matcher.find();
            }
            _matcher.appendTail(_strBuf);

            return _strBuf.toString();
        } else {
            return "";
        }
    }
}
