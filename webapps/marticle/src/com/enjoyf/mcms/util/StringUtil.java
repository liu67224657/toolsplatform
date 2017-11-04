/**
 * CopyRight 2007 Fivewh.com
 */
package com.enjoyf.mcms.util;


import com.google.common.base.Strings;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Garrison
 */
public class StringUtil {
    private static final Pattern WHITESPACE_PAT = Pattern.compile("\\s+");
    private static final Pattern HTML_TAG_PAT = Pattern.compile("<(.|\n)*?>");

    private static final char[] hexChar = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static boolean isEmpty(String sval) {
        return (sval == null || sval.trim().length() < 1);
    }

    /**
     * Returns true if the sval is null or empty
     */
    public static boolean isEmpty(String sval, boolean trim) {
        if (sval == null) {
            return true;
        }

        if (trim) {
            sval = sval.trim();
        }

        return sval.length() == 0;
    }

    public static String nullToblank(String sval) {
        return nullToblank(sval, "");
    }

    public static String appendZore(long l, int length) {
        String appendStr = multiStrings("0", length) + l;

        return appendStr.substring(appendStr.length() - length);
    }

    public static String appendZore(int l, int length) {
        String appendStr = multiStrings("0", length) + l;

        return appendStr.substring(appendStr.length() - length);
    }

    public static String appendZore(String s, int length) {
        String appendStr = multiStrings("0", length) + s;

        return appendStr.substring(appendStr.length() - length);
    }

    private static String multiStrings(String s, int length) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < length; i++) {
            strBuf.append(s);
        }

        return strBuf.toString();
    }

    public static String nullToblank(String sval, String defaultVal) {
        if (sval == null || sval.equals("")) {
            if (defaultVal == null) {
                return "";
            } else {
                return defaultVal;
            }
        } else {
            return sval.trim();
        }
    }

    public static boolean isInCharPool(String sval, String charPool) {
        return isInCharPool(sval, charPool, true);
    }

    public static boolean isInCharPool(String sval, String charPool, boolean caseNotIgnore) {
        if (sval == null || "".equals(sval)) {
            return true;
        }

        if (charPool == null || "".equals(charPool)) {
            return false;
        }

        String _srcStr = sval;
        String _charPool = charPool;

        if (!caseNotIgnore) {
            _srcStr = _srcStr.toUpperCase();
            _charPool = _charPool.toUpperCase();
        }

        for (int i = 0; i < _srcStr.length(); i++) {
            if (_charPool.indexOf(_srcStr.charAt(i)) < 0) {
                return false;
            }
        }

        return true;
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

    public static String replace(String sval, String replacePattern, String destVal, int caseIgnore) {
        return regReplace(sval, replacePattern, destVal, caseIgnore);
    }

    public static List<Long> listStringToLong(List<String> ls) {
        List<Long> ll = new ArrayList<Long>();

        for (String s : ls) {
            ll.add(Long.parseLong(s));
        }

        return ll;
    }

    public static long ipToLong(String ip) {
        String[] ips = ip.split("[\\.]");

        return 256 * 256 * 256 * Long.parseLong(ips[0]) + 256 * 256 * Long.parseLong(ips[1])
                + 256 * Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
    }

    public static String removeWhiteSpace(String s) {
        if (s == null) {
            return s;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return new String(sb);
    }

    public static boolean isLetter(char c) {
        int k = 0x80;

        return (c / k) == 0;
    }

    public static int length(String s) {
        char[] chars = s.toCharArray();
        int len = 2;

        for (char charCode : chars) {

            if (!isLetter(charCode)) {
                len += 2;
            } else {
                len += 1;
            }
        }

        return len / 2;
    }

    public static String subString(String s, int length) {
        StringBuffer sb = new StringBuffer();

        char[] chars = s.toCharArray();
        int requireSubString = length * 2;

        int idx = 0;
        for (char charCode : chars) {
            if (!isLetter(charCode)) {
                idx += 2;
            } else {
                idx += 1;
            }
            if (requireSubString < idx) {
                break;
            }

            sb.append(charCode);
        }
        return sb.toString();
    }


    /**
     * calculate the total number of bytes will be used when the given string s is to be encoded using UTF-8
     *
     * @param s string to be estimated
     * @return number of bytes
     */
    public static int getUTF8Size(String s) {
        int size = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            size += getUTF8Size(ch);
        }

        return size;
    }


    /**
     * calculate the number of bytes will be used when the given character ch is to be encoded using UTF-8
     *
     * @param ch char to be estimated
     * @return number of bytes
     */
    public static int getUTF8Size(char ch) {
        int size;

        if (ch < '\u0080') {            // 0000 - 007F
            size = 1;
        } else if (ch < '\u0800') {    // 0080 - 07FF
            size = 2;
        } else if ('\uD800' <= ch && ch < '\uE000') {    // INVALID SESSION, D800 - DFFF, so assume max
            size = 3;
        } else {                    // 0800 - FFFF, escept D800 - DFFF
            size = 3;
        }

        return size;
    }

    /**
     * Returns a String which is truncated to maxLength, if necessary, and
     * appendString is appended, only if the string length is greater than
     * maxLength.
     *
     * @param s            the String to truncate
     * @param maxLength    The length to truncate s
     * @param appendString The String to append, if s is truncated.
     * @return the possibly truncated string.
     */
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

    public static String removeHTMLTags(String strInput) {
        if (Strings.isNullOrEmpty(strInput)) {
            return "";
        }
        return HTML_TAG_PAT.matcher(strInput).replaceAll("");
    }


    public static String removeWhitespace(String s) {
        return WHITESPACE_PAT.matcher(s).replaceAll("");
    }

    public static String[] splitString(String s, String tokenz) {
        return s.split("[" + tokenz + "]");
    }

    public static Set<String> splitStringToSet(String s, String tokenz) {
        Set<String> returnValue = new HashSet<String>();

        String[] arrayString = splitString(s, tokenz);
        for (String str : arrayString) {
            returnValue.add(str);
        }

        return returnValue;
    }

    /**
     * 替换字符串中的空格、换行、等
     *
     * @param str
     * @return
     */
    public static String trimString(String str) {
        //替换所有特殊字符
        String regEx = "[\\s*|\t|\r|\n]{2,}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.replaceAll("").trim();
    }


    public static boolean isChar(char c) {
        int cCode = c;
        return cCode > 65 && cCode <= 122;
    }

    public static boolean isChinese(char c) {
        int cCode = c;
        return cCode > 122;
    }

    //将URL中文部分编码
    public static String getUTF8Url(String url) {
        // 匹配的字符串的正则表达式
        String reg_charset = "([\u4e00-\u9fa5]+)";

        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(url);
        while (m.find()) {
            String china = null;
            try {
                china = URLEncoder.encode(m.group(1), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url = url.replace(m.group(1), china);
        }
        return url;
    }


}
