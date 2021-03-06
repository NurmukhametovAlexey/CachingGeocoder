package ru.nurmukhametov.cachinggeocoder.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nurmukhametov.cachinggeocoder.controller.dto.Query;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.exception.DatabaseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;
import ru.nurmukhametov.cachinggeocoder.repository.GeocodeRepositoryService;
import ru.nurmukhametov.cachinggeocoder.service.outer.OuterGeocoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CachingGeocoderServiceTest {

    @Mock
    private GeocodeRepositoryService geocodeRepositoryService;
    @Mock
    private OuterGeocoder outerGeocoder;

    private CachingGeocoderService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CachingGeocoderService(geocodeRepositoryService, outerGeocoder);
    }

    @Test
    void findGeocode_WhenGivenAddressAndFoundInCache()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String address = "address";
        Query query = new Query(address);
        Geocode expectedGeocode = new Geocode();
        BDDMockito.given(geocodeRepositoryService.findGeocodeByAddress(query.getText())).willReturn(expectedGeocode);
        //when
        Geocode resultGeocode = underTest.findGeocode(query);
        //then
        assertThat(resultGeocode).isEqualTo(expectedGeocode);
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).findGeocodeByAddress(query.getText());
        Mockito.verify(geocodeRepositoryService, Mockito.never()).findGeocodeByCoordinates(Mockito.anyString());
        Mockito.verify(outerGeocoder, Mockito.never()).makeRequestByCoordinates(Mockito.anyString());
        Mockito.verify(outerGeocoder, Mockito.never()).makeRequestByAddress(Mockito.anyString());
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).saveGeocode(resultGeocode);
    }

    @Test
    void findGeocode_WhenGivenAddressAndNotFoundInCache()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String address = "address";
        Query query = new Query(address);
        Geocode expectedGeocode = new Geocode();
        BDDMockito.given(geocodeRepositoryService.findGeocodeByAddress(query.getText())).willReturn(null);
        BDDMockito.given(outerGeocoder.makeRequestByAddress(query.getText())).willReturn(expectedGeocode);
        //when
        Geocode resultGeocode = underTest.findGeocode(query);
        //then
        assertThat(resultGeocode).isEqualTo(expectedGeocode);
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).findGeocodeByAddress(query.getText());
        Mockito.verify(geocodeRepositoryService, Mockito.never()).findGeocodeByCoordinates(Mockito.anyString());
        Mockito.verify(outerGeocoder, Mockito.never()).makeRequestByCoordinates(Mockito.anyString());
        Mockito.verify(outerGeocoder, Mockito.times(1)).makeRequestByAddress(query.getText());
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).saveGeocode(resultGeocode);
    }

    @Test
    void findGeocode_WhenGivenCoordinatesAndFoundInCache()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String coordinates = "55 33";
        Query query = new Query(coordinates);
        Geocode expectedGeocode = new Geocode();
        BDDMockito.given(geocodeRepositoryService.findGeocodeByCoordinates(query.getText())).willReturn(expectedGeocode);
        //when
        Geocode resultGeocode = underTest.findGeocode(query);
        //then
        assertThat(resultGeocode).isEqualTo(expectedGeocode);
        Mockito.verify(geocodeRepositoryService, Mockito.never()).findGeocodeByAddress(Mockito.anyString());
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).findGeocodeByCoordinates(query.getText());
        Mockito.verify(outerGeocoder, Mockito.never()).makeRequestByCoordinates(Mockito.anyString());
        Mockito.verify(outerGeocoder, Mockito.never()).makeRequestByAddress(Mockito.anyString());
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).saveGeocode(resultGeocode);
    }

    @Test
    void findGeocode_WhenGivenCoordinatesAndNotFoundInCache()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String coordinates = "55 33";
        Query query = new Query(coordinates);
        Geocode expectedGeocode = new Geocode();
        BDDMockito.given(geocodeRepositoryService.findGeocodeByCoordinates(query.getText())).willReturn(null);
        BDDMockito.given(outerGeocoder.makeRequestByCoordinates(query.getText())).willReturn(expectedGeocode);
        //when
        Geocode resultGeocode = underTest.findGeocode(query);
        //then
        assertThat(resultGeocode).isEqualTo(expectedGeocode);
        Mockito.verify(geocodeRepositoryService, Mockito.never()).findGeocodeByAddress(Mockito.anyString());
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).findGeocodeByCoordinates(query.getText());
        Mockito.verify(outerGeocoder, Mockito.times(1)).makeRequestByCoordinates(query.getText());
        Mockito.verify(outerGeocoder, Mockito.never()).makeRequestByAddress(Mockito.anyString());
        Mockito.verify(geocodeRepositoryService, Mockito.times(1)).saveGeocode(resultGeocode);
    }

}