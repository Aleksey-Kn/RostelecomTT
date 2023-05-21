package ru.rostelecom.test.exceptions;

public class LongLinkNotFoundException extends RuntimeException {
    public LongLinkNotFoundException(final String url) {
        super("Not found url: " + url);
    }
}
