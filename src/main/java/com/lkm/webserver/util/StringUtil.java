package com.lkm.webserver.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static List<String> split(String str, String patten, int limit) {
        List<String> result = new ArrayList<>(8);
        int count = 0;
        int startPosition = 0;
        int endPosition = 0;
        int patternLen = patten.length();
        while (true) {
            endPosition = str.indexOf(patten, startPosition);
            if (endPosition != -1) {
                result.add(str.substring(startPosition, endPosition));
                startPosition = endPosition + patternLen;
                count++;
            } else {
                break;
            }
            if (count >= limit - 1) {
                break;
            }
        }
        if (endPosition < str.length()) {
            result.add(str.substring(startPosition));
        }
        return result;
    }

    public static List<String> split(String str, String patten) {
        return split(str, patten, Short.MAX_VALUE);
    }
}
