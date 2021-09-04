package ru.nurmukhametov.geocodingcacher.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepositoryService;
import ru.nurmukhametov.geocodingcacher.service.outer.OuterGeocoder;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CachedGeocodingService {

    private final Logger logger = LoggerFactory.getLogger(CachedGeocodingService.class);

    private final GeocodeRepositoryService geocodeRepositoryService;
    private final OuterGeocoder outerGeocoder;

    public Geocode findGeocode(String addressOrCoordinates)
            throws DatabaseException, BadGeocoderRequestException {

        logger.debug("findGeocode method argument: {}", addressOrCoordinates);

        Geocode geocode = null;

        boolean isCoordinates = isCoordinates(addressOrCoordinates);

        if(isCoordinates) {
            logger.debug("its coordinates");
            geocode = geocodeRepositoryService.findGeocodeByCoordinates(addressOrCoordinates);
        } else {
            logger.debug("its NOT coordinates");
            geocode = geocodeRepositoryService.findGeocodeByAddress(addressOrCoordinates);
        }

        logger.debug("cache search returns: {}", geocode);

        if (geocode == null) {
            if (isCoordinates) {
                geocode = outerGeocoder.makeHttpRequestByCoordinates(addressOrCoordinates);
            } else {
                geocode = outerGeocoder.makeHttpRequestByAddress(addressOrCoordinates);
            }

            logger.debug("outer geocoder returns: {}", geocode);
        }

        geocodeRepositoryService.saveGeocode(geocode);

        return geocode;
    }

    private boolean isCoordinates(String addressOrCoordinates) {
        return Pattern.matches("[0-9]{1,2}(\\.[0-9]*)?[\\,\\;\\s]+[0-9]{1,2}(\\.[0-9]*)?", addressOrCoordinates);
    }
}
