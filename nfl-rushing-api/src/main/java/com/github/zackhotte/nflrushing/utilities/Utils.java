package com.github.zackhotte.nflrushing.utilities;

public class Utils {
    private Utils() {}

    public static String removeSuffix(String text, char suffix) {
        if (text.charAt(text.length() - 1) == suffix) {
            text = text.substring(0, text.length() - 1);
        }

        return text;
    }
}
