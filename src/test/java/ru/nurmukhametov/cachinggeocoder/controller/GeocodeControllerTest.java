package ru.nurmukhametov.cachinggeocoder.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nurmukhametov.cachinggeocoder.controller.dto.GeocodeResponse;
import ru.nurmukhametov.cachinggeocoder.controller.dto.Query;
import ru.nurmukhametov.cachinggeocoder.controller.dto.QueryType;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderRequestException;
import ru.nurmukhametov.cachinggeocoder.exception.BadGeocoderResponseException;
import ru.nurmukhametov.cachinggeocoder.exception.DatabaseException;
import ru.nurmukhametov.cachinggeocoder.model.Geocode;
import ru.nurmukhametov.cachinggeocoder.service.CachingGeocoderService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class GeocodeControllerTest {

    @Mock
    private CachingGeocoderService geocodingService;

    private GeocodeController underTest;

    @BeforeEach
    void setUp() {
        underTest = new GeocodeController(geocodingService);
    }

    @Test
    void getGeocode_WhenGivenAddress()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String address = "address";
        Query query = new Query(address);
        Geocode geocode = new Geocode();
        geocode.setCoordinates("55 33");
        geocode.setFullAddress("full address");
        GeocodeResponse expectedGeocodeResponse = new GeocodeResponse(query, geocode);
        BDDMockito.given(geocodingService.findGeocode(query)).willReturn(geocode);
        //when
        GeocodeResponse resultGeocodeResponse = underTest.getGeocode(address);
        //then
        assertThat(query.getQueryType()).isEqualTo(QueryType.ADDRESS);
        assertThat(expectedGeocodeResponse).usingRecursiveComparison().isEqualTo(resultGeocodeResponse);
        Mockito.verify(geocodingService, Mockito.times(1)).findGeocode(query);
    }

    @Test
    void getGeocode_WhenGivenCoordinates()
            throws BadGeocoderRequestException, BadGeocoderResponseException, DatabaseException {
        //given
        String coordinates = "55 33";
        Query query = new Query(coordinates);
        Geocode geocode = new Geocode();
        geocode.setCoordinates(coordinates);
        geocode.setFullAddress("full address");
        GeocodeResponse expectedGeocodeResponse = new GeocodeResponse(query, geocode);
        BDDMockito.given(geocodingService.findGeocode(query)).willReturn(geocode);
        //when
        GeocodeResponse resultGeocodeResponse = underTest.getGeocode(coordinates);
        //then
        assertThat(query.getQueryType()).isEqualTo(QueryType.COORDINATES);
        assertThat(expectedGeocodeResponse).usingRecursiveComparison().isEqualTo(resultGeocodeResponse);
        Mockito.verify(geocodingService, Mockito.times(1)).findGeocode(query);
    }
}