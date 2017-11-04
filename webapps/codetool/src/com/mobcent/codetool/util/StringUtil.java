package com.mobcent.codetool.util;

public class StringUtil {
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
}
