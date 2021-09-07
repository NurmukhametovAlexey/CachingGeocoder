package ru.nurmukhametov.geocodingcacher.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.controller.dto.Query;
import ru.nurmukhametov.geocodingcacher.controller.dto.QueryType;
import ru.nurmukhametov.geocodingcacher.exception.BadGeocoderRequestException;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.exception.ResultsNotFoundException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepositoryService;
import ru.nurmukhametov.geocodingcacher.service.outer.OuterGeocoder;

@Service
@AllArgsConstructor
public class CachedGeocodingService {

    private final Logger logger = LoggerFactory.getLogger(CachedGeocodingService.class);

    private final GeocodeRepositoryService geocodeRepositoryService;
    private final OuterGeocoder outerGeocoder;

    public Geocode findGeocode(Query query)
            throws DatabaseException, BadGeocoderRequestException, ResultsNotFoundException {

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
                geocode = outerGeocoder.makeHttpRequestByCoordinates(query.getText());
            } else {
                geocode = outerGeocoder.makeHttpRequestByAddress(query.getText());
            }

            logger.debug("outer geocoder returns: {}", geocode);
        }

        geocodeRepositoryService.saveGeocode(geocode);

        return geocode;
    }
}
