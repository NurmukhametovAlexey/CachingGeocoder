package ru.nurmukhametov.geocodingcacher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nurmukhametov.geocodingcacher.exception.DatabaseException;
import ru.nurmukhametov.geocodingcacher.model.Geocode;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepository;
import ru.nurmukhametov.geocodingcacher.repository.GeocodeRepositoryService;

@ExtendWith(MockitoExtension.class)
class GeocodeRepositoryServiceTest {

    @Mock
    private GeocodeRepository geocodeRepository;

    private GeocodeRepositoryService underTest;

    @BeforeEach
    void setup() {
        underTest = new GeocodeRepositoryService(geocodeRepository);
    }

    @Test
    void getCoordinatesByFullAddress() {
        //given
        String address = "address";
        //when
        underTest.findGeocodeByAddress(address);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findByFullAddress(address);
    }

    @Test
    void getAddressByCoordinates() {
        //given
        String coordinates = "coordinates";
        //when
        underTest.findGeocodeByCoordinates(coordinates);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findByCoordinates(coordinates);
    }

    @Test
    void saveGeocode() throws DatabaseException {
        //given
        Geocode geocode = new Geocode();
        //when
        underTest.saveGeocode(geocode);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).save(geocode);
    }
}