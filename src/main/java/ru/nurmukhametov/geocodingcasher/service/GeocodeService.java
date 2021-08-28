package ru.nurmukhametov.geocodingcasher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nurmukhametov.geocodingcasher.model.Geocode;
import ru.nurmukhametov.geocodingcasher.repository.GeocodeRepository;

@Service
public class GeocodeService {
    private final GeocodeRepository geocodeRepository;

    @Autowired public GeocodeService(GeocodeRepository geocodeRepository) {
        this.geocodeRepository = geocodeRepository;
    }

    public String getCoordinatesByAddress(String address) {
        Geocode geocode = geocodeRepository.findByAddress(address);
        return (geocode != null)? geocode.getCoordinates() : null;
    }

    public String getAddressByCoordinates(String coordinates) {

        Geocode geocode = geocodeRepository.findByCoordinates(coordinates);
        return (geocode != null)? geocode.getAddress() : null;
    }

    public Geocode saveGeocode(Geocode geocode) {
        return geocodeRepository.save(geocode);
    }
}
