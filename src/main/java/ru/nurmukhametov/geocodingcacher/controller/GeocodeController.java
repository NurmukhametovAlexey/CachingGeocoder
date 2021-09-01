package ru.nurmukhametov.geocodingcacher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.service.CachedGeocodingService;

@RestController
public class GeocodeController {

    private final Logger logger = LoggerFactory.getLogger(GeocodeController.class);

    private final CachedGeocodingService geocodingService;

    @Autowired
    public GeocodeController(CachedGeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/geocode")
    public ModelAndView showGeocode(@ModelAttribute Geocode geocode) {

        logger.debug("GetMapping '/geocode', ModelAttribute geocode: {}", geocode);

        ModelAndView modelAndView = new ModelAndView("/geocode");
        if (geocode != null) {
            modelAndView.addObject("geocode", geocode);
        }
        return modelAndView;
    }

    @PostMapping("/geocode")
    public ModelAndView getGeocode(@RequestParam(value = "query") String query, RedirectAttributes attributes) {
        logger.debug("PostMapping '/geocode', Query: {}", query);

        ModelAndView modelAndView = new ModelAndView();
        try {
            Geocode geocode =  geocodingService.findGeocode(query);
            modelAndView.setViewName("redirect:/geocode");
            attributes.addFlashAttribute("geocode", geocode);
        } catch (Exception e) {
            logger.error("{} in controller. Stack trace: {}", e.getClass().getSimpleName(), e.getStackTrace());
            modelAndView.setViewName("redirect:/error");
            attributes.addFlashAttribute("message", e.getMessage());
            //modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }
}
