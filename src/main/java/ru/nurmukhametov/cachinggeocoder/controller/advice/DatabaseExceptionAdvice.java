package ru.nurmukhametov.cachinggeocoder.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nurmukhametov.cachinggeocoder.exception.DatabaseException;

@ControllerAdvice
public class DatabaseExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String databaseExceptionHandler(DatabaseException exception) {
        return exception.getMessage();
    }
}
