package ru.nurmukhametov.geocodingcacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

@Service
public class CachedGeocodingService {

    private final Logger logger = LoggerFactory.getLogger(CachedGeocodingService.class);

    private final CacheService cacheService;
    private final OuterGeocoder outerGeocoder;

    @Autowired
    public CachedGeocodingService(CacheService cacheService, OuterGeocoder outerGeocoder) {
        this.cacheService = cacheService;
        this.outerGeocoder = outerGeocoder;
    }

    public Geocode findGeocode(String addressOrCoordinates)
            throws DatabaseException, BadGeocoderRequestException {

        logger.debug("findGeocode method argument: {}", addressOrCoordinates);

        Geocode geocode = cacheService.cacheSearch(addressOrCoordinates);

        logger.debug("cache search returns: {}", geocode);

        if(geocode == null) {
            geocode = outerGeocoder.makeHttpRequest(addressOrCoordinates);
            logger.debug("outer geocoder returns: {}", geocode);
            cacheService.cacheSave(geocode);
        }

        return geocode;
    }
}
