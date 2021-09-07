package ru.nurmukhametov.geocodingcacher.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nurmukhametov.geocodingcacher.exception.ResultsNotFoundException;

@ControllerAdvice
public class ResultsNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ResultsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ResultNotFoundHandler(ResultsNotFoundException exception) {
        return exception.getMessage();
    }
}
