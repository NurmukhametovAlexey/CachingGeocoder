package ru.nurmukhametov.geocodingcacher.exception;

public class BadGeocoderRequestException extends Exception {
    public BadGeocoderRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
