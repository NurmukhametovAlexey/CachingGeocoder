package ru.nurmukhametov.cachinggeocoder.exception;

public class BadGeocoderRequestException extends Exception {
    public BadGeocoderRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadGeocoderRequestException(String message) {
        super(message);
    }
}
