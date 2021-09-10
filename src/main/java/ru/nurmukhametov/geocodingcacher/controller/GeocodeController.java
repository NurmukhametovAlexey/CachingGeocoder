package ru.nurmukhametov.geocodingcacher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nurmukhametov.geocodingcacher.controller.dto.GeocodeResponse;
import ru.nurmukhametov.geocodingcacher.controller.dto.Query;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderResponseException;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
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
    public GeocodeResponse getGeocode(@RequestParam String addressOrCoordinates)
            throws DatabaseException, BadGeocoderRequestException, BadGeocoderResponseException {

        logger.debug("GetMapping /geocode/{}", addressOrCoordinates);

        Query query = new Query(addressOrCoordinates);

        logger.debug("Formed query: {}", query);

        Geocode geocode = geocodingService.findGeocode(query);

        return new GeocodeResponse(query, geocode);
    }
}
