package ru.nurmukhametov.geocodingcacher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcacher.model.Geocode;

@Service
public class CacheSearchImpl implements CacheSearch {

    private final GeocodeService geocodeService;

    @Autowired
    public CacheSearchImpl(GeocodeService geocodeService) {
        this.geocodeService = geocodeService;
    }

    @Override
    public Geocode search(String addressOrCoordinates) {
        Geocode result = geocodeService.getAddressByCoordinates(addressOrCoordinates);
        if (result == null) {
            result = geocodeService.getCoordinatesByAddress(addressOrCoordinates);
        }
        return result;
    }
}
