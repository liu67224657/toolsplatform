package com.enjoyf.util;

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
     * 产生激活码
     *
     * @param length
     * @return
     * @author WeiYY(2012-5-4)
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
     * 替换String字符串
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
     * 截取第一个符号和第二个符号中间的字符串
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
     * 获得一个字符串首尾部分
     *
     * @param str
     * @param beginFlag 开头的标志
     * @param endFlag   结尾的标志
     * @return
     */
    public static String[] getHeadAndEndString(String str, String beginFlag, String endFlag) {
        int position = str.indexOf(beginFlag);
        int position1 = str.indexOf(endFlag);
        if (position > 0) {
            String head = str.substring(0, position);
            String end = str.substring(position1 + endFlag.length(), str.length());
            return new String[]{head, end};
        } else {
            return new String[]{str, ""};
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

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            int chr = str.charAt(i);
            if (chr < 48 || chr > 57)
                return false;
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 拼接前后的html
     *
     * @param headEnd
     * @param sb
     * @return
     */
    public static String getReplaceString(String[] headEnd, String str) {
        if (headEnd != null && headEnd.length == 2) {
            return headEnd[0] + str + headEnd[1];
        } else {
            return str;
        }
    }

    public static String truncate(String s, int maxLength, String appendString) {
        if (s != null && s.length() > maxLength) {
            if (appendString == null) {
                appendString = "...";
            }
            return new String(s.substring(0, maxLength) + appendString);
        } else {
            return new String(s);
        }
    }

    public static String truncate(String s, int maxLength) {
        return truncate(s, maxLength, null);
    }


}
