package ru.nurmukhametov.geocodingcacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

@Service
public class CacheSearchImpl implements CacheSearch {

    private final Logger logger = LoggerFactory.getLogger(CacheSearchImpl.class);

    private final GeocodeRepositoryService geocodeRepositoryService;

    @Autowired
    public CacheSearchImpl(GeocodeRepositoryService geocodeRepositoryService) {
        this.geocodeRepositoryService = geocodeRepositoryService;
    }

    @Override
    public Geocode search(String addressOrCoordinates) {
        Geocode result = geocodeRepositoryService.getAddressByCoordinates(addressOrCoordinates);
        if (result == null) {
            result = geocodeRepositoryService.getCoordinatesByAddress(addressOrCoordinates);
        }
        logger.debug("Returning result: {}", (result != null)? result.toString() : null);
        return result;
    }
}
