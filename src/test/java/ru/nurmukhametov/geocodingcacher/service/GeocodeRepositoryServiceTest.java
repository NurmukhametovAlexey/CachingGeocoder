package ru.nurmukhametov.geocodingcacher.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GeocodeRepositoryServiceTest {

    @Mock
    private GeocodeRepository geocodeRepository;

    @Test
    void getCoordinatesByAddress() {
        //given
        String address = "address";
        //when
        geocodeRepository.findByAddress(address);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findByAddress(address);
    }

    @Test
    void getAddressByCoordinates() {
        //given
        String coordinates = "coordinates";
        //when
        geocodeRepository.findByCoordinates(coordinates);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findByCoordinates(coordinates);
    }

    @Test
    void saveGeocode() {
        //given
        Geocode geocode = new Geocode();
        //when
        geocodeRepository.save(geocode);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).save(geocode);
    }
}