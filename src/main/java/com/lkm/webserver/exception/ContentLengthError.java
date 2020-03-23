package com.lkm.webserver.exception;

public class ContentLengthError extends Exception {
    public ContentLengthError(int totalLength, int receivedLength) {
        super("Content length error, total length" + totalLength + ", received length " + receivedLength);
    }

    public ContentLengthError() {
        super("Content length error");
    }
}
