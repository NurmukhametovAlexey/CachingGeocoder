package ru.nurmukhametov.geocodingcacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

@Service
public class CacheServiceImpl implements CacheService {

    private final Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    private final GeocodeRepositoryService geocodeRepositoryService;

    @Autowired
    public CacheServiceImpl(GeocodeRepositoryService geocodeRepositoryService) {
        this.geocodeRepositoryService = geocodeRepositoryService;
    }

    @Override
    public Geocode cacheSearch(String addressOrCoordinates) {
        Geocode result = geocodeRepositoryService.getAddressByCoordinates(addressOrCoordinates);
        if (result == null) {
            result = geocodeRepositoryService.getCoordinatesByAddress(addressOrCoordinates);
        }
        logger.debug("Returning result: {}", (result != null)? result.toString() : null);
        return result;
    }

    @Override
    public Geocode cacheSave(Geocode geocode) throws DatabaseException {
        logger.debug("Saving in cache: {}", geocode);
        return geocodeRepositoryService.saveGeocode(geocode);
    }


}
