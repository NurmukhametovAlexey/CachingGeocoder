package ru.nurmukhametov.geocodingcacher.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

@Service
public class GeocodeRepositoryService {
    private final Logger logger = LoggerFactory.getLogger(GeocodeRepositoryService.class);

    private final GeocodeRepository geocodeRepository;

    @Autowired public GeocodeRepositoryService(GeocodeRepository geocodeRepository) {
        this.geocodeRepository = geocodeRepository;
    }


    public Geocode findGeocodeByAddress(String address) {
        Geocode geocode = geocodeRepository.findBySearchedAddress(address);
        if (geocode == null) {
            geocode = geocodeRepository.findByFullAddress(address)
                    .stream()
                    .findAny()
                    .orElse(null);
        }
        return geocode;
    }

    public Geocode findGeocodeByCoordinates(String coordinates) {
        return geocodeRepository.findByCoordinates(coordinates)
                .stream()
                .findAny()
                .orElse(null);
    }

    public Geocode saveGeocode(Geocode geocode) throws DatabaseException {
        try {
            return geocodeRepository.save(geocode);
        } catch (Exception e) {
            logger.error("Exception occurred: {}", e.getClass().getName());
             throw new DatabaseException("Error with saving geocode to database", e);

        }
    }
}
