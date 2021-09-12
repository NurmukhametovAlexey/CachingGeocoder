package ru.nurmukhametov.cachinggeocoder.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;

@ControllerAdvice
public class BadGeocoderResponseAdvice {

    @ResponseBody
    @ExceptionHandler(BadGeocoderResponseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ResultNotFoundHandler(BadGeocoderResponseException exception) {
        return exception.getMessage();
    }
}
