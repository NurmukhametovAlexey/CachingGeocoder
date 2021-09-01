package ru.nurmukhametov.geocodingcacher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepository;

@Service
public class GeocodeRepositoryService {
    private final Logger logger = LoggerFactory.getLogger(GeocodeRepositoryService.class);

    private final GeocodeRepository geocodeRepository;

    @Autowired public GeocodeRepositoryService(GeocodeRepository geocodeRepository) {
        this.geocodeRepository = geocodeRepository;
    }

    public Geocode getCoordinatesByAddress(String address) {
        return geocodeRepository.findByAddress(address);
    }

    public Geocode getAddressByCoordinates(String coordinates) {
        return geocodeRepository.findByCoordinates(coordinates);
    }

    public Geocode saveGeocode(Geocode geocode) throws DatabaseException {
        try {
            return geocodeRepository.save(geocode);
        } catch (Exception e) {
            logger.error("Exception occured: {}", e.getClass().getName());
            DatabaseException exception = new DatabaseException();
            exception.initCause(e);
            throw exception;
        }
    }
}
