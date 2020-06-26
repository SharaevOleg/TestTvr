package com.tvrtest.tverskoi2.utils;

public class Utils {
    public static String firstUpperCaseInWord(String string) {
        if (string == null || string.isEmpty()) {
            return string;
        }
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }
}
