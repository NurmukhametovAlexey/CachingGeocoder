package ru.nurmukhametov.cachinggeocoder.exception;

public class BadGeocoderResponseException extends Exception {
    public BadGeocoderResponseException(String message) {
        super(message);
    }

    public BadGeocoderResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
