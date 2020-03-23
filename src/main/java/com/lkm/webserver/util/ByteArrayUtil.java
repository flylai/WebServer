package com.lkm.webserver.util;

public class ByteArrayUtil {
    public static int indexof(byte[] array, String patten, int startPosition) {
        int arrLen = array.length;
        int pattenLen = patten.length();
        if (startPosition > arrLen || pattenLen > arrLen) {
            return -1;
        }
        char firstChar = patten.charAt(0);
        for (int i = startPosition; i < arrLen - pattenLen; i++) {
            if (array[i] == firstChar) {
                boolean found = true;
                for (int j = 1; j < pattenLen; j++) {
                    if (patten.charAt(j) != array[i + j]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int indexof(byte[] array, String patten) {
        return indexof(array, patten, 0);
    }
}
