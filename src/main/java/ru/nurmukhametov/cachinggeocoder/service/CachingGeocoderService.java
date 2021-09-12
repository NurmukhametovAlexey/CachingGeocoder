package ru.nurmukhametov.cachinggeocoder.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.cachinggeocoder.controller.dto.Query;
import ru.nurmukhametov.cachinggeocoder.controller.dto.QueryType;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.DatabaseException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;
import ru.nurmukhametov.cachinggeocoder.repository.GeocodeRepositoryService;
import ru.nurmukhametov.cachinggeocoder.service.outer.OuterGeocoder;

@Service
@AllArgsConstructor
public class CachingGeocoderService {

    private final Logger logger = LoggerFactory.getLogger(CachingGeocoderService.class);

    private final GeocodeRepositoryService geocodeRepositoryService;
    private final OuterGeocoder outerGeocoder;

    public Geocode findGeocode(Query query)
            throws DatabaseException, BadGeocoderRequestException, BadGeocoderResponseException {

        logger.debug("findGeocode method argument: {}", query);

        boolean isCoordinates = query.getQueryType().equals(QueryType.COORDINATES);

        Geocode geocode;

        if(isCoordinates) {
            logger.debug("its coordinates");
            geocode = geocodeRepositoryService.findGeocodeByCoordinates(query.getText());
        } else {
            logger.debug("its NOT coordinates");
            geocode = geocodeRepositoryService.findGeocodeByAddress(query.getText());
        }

        logger.debug("cache search returns: {}", geocode);

        if (geocode == null) {
            if (isCoordinates) {
                geocode = outerGeocoder.makeRequestByCoordinates(query.getText());
            } else {
                geocode = outerGeocoder.makeRequestByAddress(query.getText());
            }

            logger.debug("outer geocoder returns: {}", geocode);
        }

        geocodeRepositoryService.saveGeocode(geocode);

        return geocode;
    }
}
