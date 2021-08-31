package ru.nurmukhametov.geocodingcacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepository;

@Service
public class GeocodeRepositoryService {
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

    public Geocode saveGeocode(Geocode geocode) {
        return geocodeRepository.save(geocode);
    }
}