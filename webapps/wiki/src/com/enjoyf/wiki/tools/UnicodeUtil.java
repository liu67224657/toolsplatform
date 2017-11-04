package com.enjoyf.wiki.tools;

public final class UnicodeUtil {
    public static String StringToUnicode(String str) {
        char[] utfBytes = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        String temp = "0000";
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 3) {
                hexB = temp.substring(0, 4 - hexB.length()) + hexB;
            }
            sb.append("\\u" + hexB);
        }
        return sb.toString();
    }
    
    public static String StringToUnicodeWithoutU(String str) {
        char[] utfBytes = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        String temp = "0000";
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 3) {
                hexB = temp.substring(0, 4 - hexB.length()) + hexB;
            }
            sb.append(hexB);
        }
        return sb.toString();
    }

    public static String UnicodeToString(String theString) {
        if (theString == null) {
            return null;
        }

        if (!theString.startsWith("\\u"))
            return theString;

        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    //   Read   the   xxxx   
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    public static void main(String[] args) {
    	System.out.println(UnicodeUtil.UnicodeToString("\u7ade\u6280\u573a\u7d2b\u5361\u9635\u5bb9\u63a8\u8350"));
//        System.out.println(UnicodeUtil.StringToUnicodeWithoutU("大家好，我叫唐筱希，今年2岁了，希望大家喜欢我"));
    }
}
