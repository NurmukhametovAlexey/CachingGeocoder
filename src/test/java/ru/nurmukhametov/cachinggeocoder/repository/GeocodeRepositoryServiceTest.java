package ru.nurmukhametov.cachinggeocoder.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nurmukhametov.cachinggeocoder.exception.DatabaseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class GeocodeRepositoryServiceTest {

    @Mock private GeocodeRepository geocodeRepository;

    private GeocodeRepositoryService underTest;

    @BeforeEach
    void setUp() {
        underTest = new GeocodeRepositoryService(geocodeRepository);
    }

    @Test
    void findGeocodeByAddress_IfFoundBySearchedAddressShouldNotCallAnythingElse() {
        //given
        String address = "searched address";
        Geocode expectedGeocode = new Geocode();
        BDDMockito.given(geocodeRepository.findBySearchedAddress(address)).willReturn(expectedGeocode);
        //when
        Geocode foundGeocode = underTest.findGeocodeByAddress(address);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findBySearchedAddress(address);
        Mockito.verify(geocodeRepository, Mockito.never()).findByFullAddress(address);
        assertThat(foundGeocode).isEqualTo(expectedGeocode);
    }

    @Test
    void findGeocodeByAddress_IfNotFoundBySearchedAddressShouldSearchByFullAddress() {
        //given
        String address = "full address";
        Geocode expectedGeocode = new Geocode();
        List<Geocode> returnedList = List.of(expectedGeocode);
        BDDMockito.given(geocodeRepository.findBySearchedAddress(address)).willReturn(null);
        BDDMockito.given(geocodeRepository.findByFullAddress(address)).willReturn(returnedList);
        //when
        Geocode foundGeocode = underTest.findGeocodeByAddress(address);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findBySearchedAddress(address);
        Mockito.verify(geocodeRepository, Mockito.times(1)).findByFullAddress(address);
        assertThat(foundGeocode).isEqualTo(expectedGeocode);
    }

    @Test
    void findGeocodeByCoordinates() {
        //given
        String coordinates = "coordinates";
        Geocode expectedGeocode = new Geocode();
        List<Geocode> returnedList = List.of(expectedGeocode);
        BDDMockito.given(geocodeRepository.findByCoordinates(coordinates)).willReturn(returnedList);
        //when
        Geocode foundGeocode = underTest.findGeocodeByCoordinates(coordinates);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).findByCoordinates(coordinates);
        assertThat(foundGeocode).isEqualTo(expectedGeocode);
    }

    @Test
    void saveGeocode() throws DatabaseException {
        //given
        Geocode geocodeToSave = new Geocode();
        BDDMockito.given(geocodeRepository.save(geocodeToSave)).willReturn(geocodeToSave);
        //when
        Geocode savedGeocode = underTest.saveGeocode(geocodeToSave);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).save(geocodeToSave);
        assertThat(savedGeocode).isEqualTo(geocodeToSave);
    }

    @Test
    void saveGeocode_ifExceptionOccursShouldThrowDatabaseException() {
        //given
        Geocode geocodeToSave = new Geocode();
        BDDMockito.given(geocodeRepository.save(geocodeToSave)).willThrow(RuntimeException.class);
        //when
        assertThatThrownBy(() -> underTest.saveGeocode(geocodeToSave))
                .isInstanceOf(DatabaseException.class);
        //then
        Mockito.verify(geocodeRepository, Mockito.times(1)).save(geocodeToSave);
    }
}