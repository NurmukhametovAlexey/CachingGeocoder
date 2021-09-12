package ru.nurmukhametov.cachinggeocoder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nurmukhametov.cachinggeocoder.controller.dto.GeocodeResponse;
import ru.nurmukhametov.cachinggeocoder.controller.dto.Query;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.exception.DatabaseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;
import ru.nurmukhametov.cachinggeocoder.service.CachingGeocoderService;

@RestController
public class GeocodeController {

    private final Logger logger = LoggerFactory.getLogger(GeocodeController.class);

    private final CachingGeocoderService geocodingService;

    @Autowired
    public GeocodeController(CachingGeocoderService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/geocode")
    public GeocodeResponse getGeocode(@RequestParam String addressOrCoordinates)
            throws DatabaseException, BadGeocoderRequestException, BadGeocoderResponseException {

        logger.debug("GetMapping /geocode/{}", addressOrCoordinates);

        Query query = new Query(addressOrCoordinates);

        logger.debug("Formed query: {}", query);

        Geocode geocode = geocodingService.findGeocode(query);

        return new GeocodeResponse(query, geocode);
    }
}
