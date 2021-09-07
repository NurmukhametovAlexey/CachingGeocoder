package ru.nurmukhametov.geocodingcacher.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;

@ControllerAdvice
public class BadGeocoderRequestAdvice {

    @ResponseBody
    @ExceptionHandler(BadGeocoderRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badGeocoderRequestExceptionHandler(BadGeocoderRequestException exception) {
        return exception.getMessage();
    }
}
