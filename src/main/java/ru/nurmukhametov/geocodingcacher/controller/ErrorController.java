package ru.nurmukhametov.geocodingcacher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorController {

    private final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping("/error")
    public ModelAndView errorPage(@ModelAttribute Exception e) {

        logger.debug("errorPage, exception: {}", e.getClass().getSimpleName());

        ModelAndView modelAndView = new ModelAndView("/error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
